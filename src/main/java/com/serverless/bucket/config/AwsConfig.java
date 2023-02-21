package com.serverless.bucket.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AwsConfig {
        @Value("${access-key}")
        private String accessKey;
        @Value("${secret-key}")
        private String accessSecret;
        @Value("${region}")
        private String region;

        public AmazonS3 s3Client() {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
            System.out.println("Welcome to AWS Application");
            return AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(region).build();
        }
    }
