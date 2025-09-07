# 🚀 uploadServerless

A Java-based serverless file upload service leveraging AWS Lambda, Amazon S3, and Spring Boot. Designed for cloud-native deployments, this project enables seamless, scalable, and secure file uploads to S3 buckets via RESTful API endpoints, with support for local development and AWS Lambda serverless environments.

---

## ✨ Features

- ⚡ **Serverless Uploads**: Upload files directly to Amazon S3 using a REST API.
- ☕ **Spring Boot Application**: Standard Spring Boot REST service with easy configuration.
- 🪄 **AWS Lambda Handler**: Deployable as a Lambda function using AWS Serverless Java Container.
- 📦 **Multipart File Support**: Handles large files and multipart data.
- 🌐 **CORS Enabled**: Supports cross-origin requests for web clients.
- 🛠️ **Configurable via Properties**: AWS credentials and bucket configuration via environment or properties file.

---

## 🏗️ Architecture

- ⚙️ **Spring Boot REST Controller**: Handles `/upload` endpoint for file uploads.
- 💼 **Service Layer**: Handles business logic and AWS S3 API interaction.
- 🛫 **AWS Lambda Handler**: Bridges AWS Lambda requests to Spring Boot application.
- 🔑 **Config Layer**: Injects AWS credentials and region.

---

### 🗂️ Project Structure

```
src/
├── main/
│   └── java/com/serverless/bucket/
│       ├── BucketApplication.java              # Spring Boot main entrypoint
│       ├── controller/BucketController.java    # REST Controller for file uploads
│       ├── service/BucketService.java          # S3 upload logic
│       ├── handler/AwsLambda.java              # Lambda handler for serverless deployment
│       └── config/AwsConfig.java               # AWS SDK configuration
└── test/
    └── java/com/serverless/bucket/BucketApplicationTests.java
```

---

## 🚦 Getting Started

### 🧰 Prerequisites

- Java 11+ (preferably 17+)
- Maven or Gradle
- AWS Account with S3 and Lambda permissions

### 📝 Configuration

Set the following properties (e.g., in `application.properties` or as environment variables):

```
bucket-name=your-s3-bucket-name
access-key=YOUR_AWS_ACCESS_KEY
secret-key=YOUR_AWS_SECRET_KEY
region=us-east-1
```

> ⚠️ **Warning:** Never commit secrets to version control. Use environment variables or AWS Secrets Manager in production.

---

## 🏃‍♂️ Local Development

1. **Clone the repo**
   ```sh
   git clone https://github.com/strivedi4u/uploadServerless.git
   cd uploadServerless
   ```
2. **Configure AWS credentials** in `application.properties` or via environment.
3. **Run locally**
   ```sh
   ./mvnw spring-boot:run
   ```
4. **Test file upload**
   ```sh
   curl -F "file=@/path/to/your/file.txt" http://localhost:8080/upload
   ```

---

## 📡 API

### Upload Endpoint

- **POST** `/upload`
- **Form Data:** `file` (multipart/form-data)
- **Response:** `200 OK` with upload confirmation

#### Example Request

```bash
curl -X POST http://localhost:8080/upload \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/your/file.txt"
```

---

## ☁️ AWS Lambda Deployment

### How It Works

- Uses `AwsLambda.java` (implements `RequestStreamHandler`) as the entry point for AWS Lambda.
- Uses the AWS Serverless Java Container (`SpringBootLambdaContainerHandler`) to adapt HTTP requests.

### 🚚 Steps

1. **Build a Fat JAR**

   ```sh
   ./mvnw package
   # Target: target/uploadServerless-<version>.jar
   ```

2. **Create Lambda Function**
   - Runtime: Java 11/17
   - Handler: `com.serverless.bucket.handler.AwsLambda::handleRequest`
   - Upload the built JAR.

3. **Set Environment Variables**
   - `bucket-name`, `access-key`, `secret-key`, `region`

4. **Configure API Gateway**
   - Connect API Gateway (HTTP API or REST API) to your Lambda for `/upload`.

---

## 🛡️ Advanced Usage

- 🪣 **Custom Bucket/Region:** Override bucket name or region per environment.
- 🔒 **Security:**
  - Use IAM roles for Lambda rather than static credentials.
  - Implement file type/size validation in `BucketService`.
- 📊 **Monitoring:**
  - Integrate with CloudWatch for logs and alerts.
- 🧪 **Testing:**
  - Use `BucketApplicationTests.java` for integration tests.
- 🤖 **CI/CD:** Automate deployment using GitHub Actions, AWS SAM, or Serverless Framework.

---

## 💡 Example: Upload Service Logic

```java
@Service
public class BucketService {
    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }
    // ... conversion helper
}
```

---

## 🛠️ Troubleshooting

- ❌ **Credentials Error:** Ensure environment variables are set or IAM role is attached.
- 🚫 **File Not Uploading:** Check S3 bucket permissions and CORS configuration.

---

## 🤝 Contributing

Pull requests and feature requests are welcome! Please open issues for bugs and suggestions.

---

## 📄 License

This project currently does not specify a license. Please contact the author for usage permissions.

---

## 👤 Author

- [strivedi4u](https://github.com/strivedi4u)

---

## 🔗 References

- [AWS S3 Java SDK](https://docs.aws.amazon.com/sdk-for-java/)
- [AWS Lambda Java](https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [AWS Serverless Java Container](https://github.com/awslabs/aws-serverless-java-container)
