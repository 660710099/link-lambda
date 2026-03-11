package cloud.lambda.link;

import java.time.Instant;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class QRCodeMetadata {
    private String QRCodeID;
    private String originalURL;
    private String s3PathURL;
    private Instant date;
    private int count;

    @DynamoDbPartitionKey
    public String getQRCodeID() {
        return this.QRCodeID;
    }
    
    public void setQRCodeID(String QRCodeID) {
        this.QRCodeID = QRCodeID;
    }

    public String getOriginalURL() {
        return this.originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getS3PathURL() {
        return this.s3PathURL;
    }

    public void setS3PathURL(String s3PathURL) {
        this.s3PathURL = s3PathURL;
    }

    public Instant getDate() {
        return this.date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
}
