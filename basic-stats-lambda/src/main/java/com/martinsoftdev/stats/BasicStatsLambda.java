package com.martinsoftdev.stats;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

/*
Example request body:
{ "data": [1,12,10,2,3,1,4,10,12,12,12]}

Example response body:

{
    "statusCode": 200,
    "statusMsg": "OK",
    "data": {
        "mean": 7.181818181818182,
        "median": 10.0,
        "mode": [
            12.0
        ]
    }
}
 */
public class BasicStatsLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
    private Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context)
    {
        APIGatewayProxyResponseEvent apiGatewayResponse = new APIGatewayProxyResponseEvent();
        ApiResponse apiResponse = null;
        LambdaLogger logger = context.getLogger();

        try
        {
            // this service always produces application/json
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json");
            apiGatewayResponse.setHeaders(headers);

            String httpMethod = event.getHttpMethod();

            logger.log("Handling " + httpMethod + " request.");

            //only the POST method is allowed
            switch (httpMethod.toUpperCase())
            {
                case "POST":
                    apiResponse = handlePostRequest(event, context);
                    break;

                default:
                    apiResponse = handleMethodNotAllowed();

            }

            apiGatewayResponse.setStatusCode(apiResponse.getStatusCode());
            apiGatewayResponse.setBody(gson.toJson(apiResponse));
        }
        catch(Throwable e)
        {
            StringWriter swStackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(swStackTrace));
            logger.log("Failed to handle request." +swStackTrace);

            apiResponse = new ApiResponse();
            apiResponse.setStatusCode(500);
            apiResponse.setStatusMsg("An unexpected error occurred.");
            apiGatewayResponse.setStatusCode(apiResponse.getStatusCode());
            apiGatewayResponse.setBody(gson.toJson(apiResponse));
        }

        logger.log("Handled request. Status Code: " + apiResponse.getStatusCode());

        return apiGatewayResponse;
    }

    private ApiResponse handleMethodNotAllowed()
    {
        ApiResponse response = new ApiResponse();
        response.setStatusCode(405);
        response.setStatusMsg("Method Not Alllowed - only POST is valid");
        return response;
    }

    private ApiResponse handlePostRequest(APIGatewayProxyRequestEvent request, Context context)
    {
        ApiResponse response = new ApiResponse();
        String jsonRequest = request.getBody();
        BasicStatsRequest statsRequest = gson.fromJson(jsonRequest, BasicStatsRequest.class);

        BasicStatsController controller = new BasicStatsController();

        BasicStats stats = controller.generateStats(statsRequest.getData());

        response.setData(stats);

        response.setStatusCode(200);
        response.setStatusMsg("OK");
        return response;
    }

}
