package cloud.lambda.link;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDatabase<T> {
    final TableSchema<T> schema;
    DynamoDbEnhancedClient client;
    DynamoDbTable<T> QRCodeMetadataTable;
    public DynamoDatabase(Class<T> modal, Region region) {
        DynamoDbClient standardClient = DynamoDbClient.builder()
            .region(region)
            .build();
        schema = TableSchema.fromBean(modal);
        client = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(standardClient)
            .build();

        QRCodeMetadataTable = client.table("QRCodeMetadata", schema);
    }
    
    public T getItemById(String uuid) {
        return QRCodeMetadataTable.getItem(Key.builder().partitionValue(uuid).build());
    }

    public T updateItem(T item) {
        return QRCodeMetadataTable.updateItem(item);
    }
}
