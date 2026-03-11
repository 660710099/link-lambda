package cloud.lambda.link;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

import software.amazon.awssdk.regions.Region;

public class LambdaHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    DynamoDatabase<QRCodeMetadata> db = new DynamoDatabase<>(QRCodeMetadata.class, Region.US_EAST_1);

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent input, Context context) {
        Map<String, String> parameter = input.getPathParameters();

        if (parameter == null) {
            return APIGatewayV2HTTPResponse.builder()
                .withStatusCode(400)
                .withBody("Unspecified redirect ID!")
                .build();

        }
        
        String uuid = parameter.get("uuid");

        if (uuid == null || uuid.isEmpty()) {
            return APIGatewayV2HTTPResponse.builder()
                .withStatusCode(400)
                .withBody("Invalid redirect ID!")
                .build();
        }

        QRCodeMetadata metadata = db.getItemById(uuid);

        if (metadata == null) {
            return APIGatewayV2HTTPResponse.builder()
                .withStatusCode(400)
                .withBody("Don't Have this redirect ID!")
                .build();
        }

        metadata.setCount(metadata.getCount() + 1);
        db.updateItem(metadata);

        String redirectUrl = metadata.getOriginalURL();

        return APIGatewayV2HTTPResponse.builder()
            .withStatusCode(302)
            .withHeaders(Map.of("Location", redirectUrl))
            .build();
    }
}
