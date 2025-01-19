# Notification Service 🚀

A **Spring Boot** microservice designed to manage notifications within a **fintech ecosystem**. 📨

## Overview

The **Notification Service** is responsible for:
- 📤 Sending notifications to users
- ⚙️ Managing notification preferences
- 🔗 Integrating with other microservices for user data


## Project Structure

```
src/main/java/com/fintech/notification/
├── config/                   # Configuration files
│   └── RestTemplateConfig.java
├── controller/               # REST controllers
│   └── NotificationController.java
├── dto/                      # Data Transfer Objects
│   ├── NotificationRequest.java
│   └── NotificationResponse.java
├── exception/                # Custom exceptions and handlers
│   └── NotificationException.java
├── model/                    # Domain entities
│   └── Notification.java
├── repository/               # Data repositories
│   └── NotificationRepository.java
├── service/                  # Business logic
│   ├── NotificationService.java
│   └── UserServiceClient.java
├── util/                     # Utility classes
│   └── NotificationUtils.java
└── NotificationApplication.java
```


## Tech Stack

- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Database**: PostgreSQL
- **Containerization**: Docker
- **Container Registry**: AWS ECR (Public)
- **Orchestration**: Kubernetes (EKS)
- **CI/CD**: Jenkins
- **Code Quality**: SonarQube

## CI/CD Pipeline

Our CI/CD pipeline ensures reliable and consistent deployments through the following stages:

![CI/CD Pipeline](/images/pipeline-diagram.png)

### Pipeline Stages

1. **Code Checkout**
    - Triggered by GitHub webhook
    - Fetches the latest code
    - Ensures a clean workspace

2. **Static Code Analysis** *(Currently Disabled)*
    - Uses SonarQube for code quality analysis
    - Checks for code smells, bugs, security vulnerabilities, and test coverage
    - Enforces quality gates with a 5-minute timeout

3. **Maven Build**
    - Compiles the Java source code
    - Packages the application into a JAR file
    - Validates project structure and dependencies

4. **Docker Build & Push**
    - Creates a Docker image
    - Authenticates with AWS ECR Public registry
    - Tags and pushes the image to the registry

5. **EKS Deployment**
    - Configures kubectl with cluster credentials
    - Creates or updates the `fintech` namespace
    - Applies Kubernetes manifests:
        - ConfigMaps
        - Secrets
        - Deployment
        - Service
    - Verifies deployment status

### Pipeline Cleanup
- Removes local Docker images
- Cleans the workspace
- Ensures a clean state for future builds

## Deployment

The service is deployed to AWS EKS using Kubernetes manifests located in the `k8s/` directory:
- `configmap.yaml`: Environment variables and configurations
- `secrets.yaml`: Sensitive data (credentials, tokens)
- `deployment.yaml`: Pod specifications and container settings
- `service.yaml`: Service exposure and networking

## Getting Started

1. **Prerequisites**
    - Java 17+
    - Maven
    - Docker
    - AWS CLI
    - kubectl

2. **Local Development**
   ```bash
   # Build the project
   mvn clean package

   # Run locally
   mvn spring-boot:run
   ```

3. **Docker Build**
   ```bash
   docker build -t user-service .
   ```

4. **Deploy to Kubernetes**
   ```bash
   # Apply manifests
   kubectl apply -f k8s/ -n fintech
   ```

## Monitoring

The service includes monitoring integration for:
- Application metrics
- Performance monitoring
- Error tracking
- Resource utilization

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Contributors

| Name | Role | GitHub |
|------|------|--------|
| Zakariae Azarkan | DevOps Engineer | [@zachary013](https://github.com/zachary013) |
| El Mahdi Id Lahcen | Frontend Developer | [@goalaphx](https://github.com/goalaphx) |
| Hodaifa | Cloud Architect | [@hodaifa-ech](https://github.com/hodaifa-ech) |
| Khalil El Houssine | Backend Developer | [@khalilh2002](https://github.com/khalilh2002) |
| Mohamed Amine BAHASSOU | ML Engineer | [@Medamine-Bahassou](https://github.com/Medamine-Bahassou) |

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.