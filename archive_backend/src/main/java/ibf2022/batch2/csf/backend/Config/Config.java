package ibf2022.batch2.csf.backend.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class Config {

    @Value("${db.storage.key}")
    private String accessKey;

    @Value("${db.storage.secretkey}")
    private String secretKey;

    @Value("${db.storage.endpoint}")
    private String endPoint;

    @Value("${db.storage.endpoint.region}")
    private String endPointRegion;

    @Bean
    public AmazonS3 getS3Client(){
        BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);

        EndpointConfiguration epConfig = new EndpointConfiguration(endPoint.trim(),endPointRegion );

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(epConfig)
                .withCredentials(new AWSStaticCredentialsProvider(cred))
                .build();
    }


}
