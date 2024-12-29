pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = credentials('AWS_ACCOUNT_ID')
        AWS_DEFAULT_REGION = 'us-east-1'
        IMAGE_REPO_NAME = 'notification-service'
        IMAGE_TAG = "${BUILD_NUMBER}"
        REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
        SONAR_PROJECT_KEY = 'notification-service'
        EKS_CLUSTER_NAME = 'your-eks-cluster'
        NAMESPACE = 'notification'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_TOKEN = credentials('SONAR_TOKEN')
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.host.url=http://your-sonarqube-url:9000 \
                        -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${IMAGE_REPO_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Push to ECR') {
            steps {
                script {
                    sh "aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
                    sh "docker tag ${IMAGE_REPO_NAME}:${IMAGE_TAG} ${REPOSITORY_URI}:${IMAGE_TAG}"
                    sh "docker push ${REPOSITORY_URI}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to EKS') {
            steps {
                script {
                    // Configure kubectl
                    sh "aws eks update-kubeconfig --region ${AWS_DEFAULT_REGION} --name ${EKS_CLUSTER_NAME}"

                    // Create namespace if it doesn't exist
                    sh "kubectl create namespace ${NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -"

                    // Update deployment image
                    sh """
                        kubectl set image deployment/notification-service \
                        notification-service=${REPOSITORY_URI}:${IMAGE_TAG} \
                        -n ${NAMESPACE} --record
                    """

                    // Verify deployment
                    sh """
                        kubectl rollout status deployment/notification-service \
                        -n ${NAMESPACE} --timeout=300s
                    """
                }
            }
        }
    }

    post {
        always {
            // Clean up Docker images
            sh "docker rmi ${IMAGE_REPO_NAME}:${IMAGE_TAG}"
            sh "docker rmi ${REPOSITORY_URI}:${IMAGE_TAG}"
        }
        failure {
            // Rollback on failure
            script {
                sh """
                    kubectl rollout undo deployment/notification-service \
                    -n ${NAMESPACE}
                """
            }
        }
    }
}