package com.martinsoftdev.stats;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicStatsLambdaTest
{
    private static APIGatewayProxyRequestEvent requestEvent = null;
    private static Gson gson = new Gson();

    @BeforeAll
    public static void initialize()
    {
        String requestJson = "{\"resource\":\"/basicStats\",\"path\":\"/basicStats\",\"httpMethod\":\"POST\",\"headers\":{\"Accept\":\"*/*\",\"Accept-Encoding\":\"gzip, deflate, br\",\"Content-Type\":\"application/json\",\"Host\":\"vp9i9mkfx7.execute-api.us-east-1.amazonaws.com\",\"Postman-Token\":\"f89b10e4-042d-4d2e-9fb2-d4cfeb8e4957\",\"User-Agent\":\"PostmanRuntime/7.26.5\",\"X-Amzn-Trace-Id\":\"Root\\u003d1-5fa77abe-0af4303e12446ccb068f808d\",\"X-Forwarded-For\":\"24.128.248.210\",\"X-Forwarded-Port\":\"443\",\"X-Forwarded-Proto\":\"https\"},\"multiValueHeaders\":{\"Accept\":[\"*/*\"],\"Accept-Encoding\":[\"gzip, deflate, br\"],\"Content-Type\":[\"application/json\"],\"Host\":[\"vp9i9mkfx7.execute-api.us-east-1.amazonaws.com\"],\"Postman-Token\":[\"f89b10e4-042d-4d2e-9fb2-d4cfeb8e4957\"],\"User-Agent\":[\"PostmanRuntime/7.26.5\"],\"X-Amzn-Trace-Id\":[\"Root\\u003d1-5fa77abe-0af4303e12446ccb068f808d\"],\"X-Forwarded-For\":[\"24.128.248.210\"],\"X-Forwarded-Port\":[\"443\"],\"X-Forwarded-Proto\":[\"https\"]},\"requestContext\":{\"accountId\":\"699449947657\",\"stage\":\"default\",\"resourceId\":\"sowpl2\",\"requestId\":\"74e27c46-659f-42d4-a53b-9bd81cd51767\",\"identity\":{\"sourceIp\":\"24.128.248.210\",\"userAgent\":\"PostmanRuntime/7.26.5\"},\"resourcePath\":\"/basicStats\",\"httpMethod\":\"POST\",\"apiId\":\"vp9i9mkfx7\",\"path\":\"/default/basicStats\"},\"body\":\"{ \\\"data\\\": [1,12,10,2,3,1,4,10,12,12,12]}\",\"isBase64Encoded\":false}";
        requestEvent = gson.fromJson(requestJson,APIGatewayProxyRequestEvent.class );

    }

    @Test
    public void test3Numbers()
    {
        List<Double> data = Arrays.asList(1.0,12.0,10.0);

        Set<Double> expectedMode = new HashSet<>();

        BasicStats stats = mockRequest(data);

        Assertions.assertEquals(7.6666, stats.getMean(),0.0001,"Incorrect Mean");
        Assertions.assertEquals(10, stats.getMedian(), "Incorrect Median");
        Assertions.assertEquals(expectedMode, stats.getMode(), "Incorrect Mode");

    }

    @Test
    public void test2Numbers()
    {
        List<Double> data = Arrays.asList(12.0,10.0);

        Set<Double> expectedMode = new HashSet<>();

        BasicStats stats = mockRequest(data);

        Assertions.assertEquals(11, stats.getMean(),0.0001,"Incorrect Mean");
        Assertions.assertEquals(11, stats.getMedian(), "Incorrect Median");
        Assertions.assertEquals(expectedMode, stats.getMode(), "Incorrect Mode");

    }

    @Test
    public void test1Number()
    {
        List<Double> data = Arrays.asList(10.0);

        Set<Double> expectedMode = new HashSet<>();

        BasicStats stats = mockRequest(data);

        Assertions.assertEquals(10, stats.getMean(),0.0001,"Incorrect Mean");
        Assertions.assertEquals(10, stats.getMedian(), "Incorrect Median");
        Assertions.assertEquals(expectedMode, stats.getMode(), "Incorrect Mode");

    }

    @Test
    public void testSingleMode()
    {
        List<Double> data = Arrays.asList(10.0,15.0,10.0,20.0);

        Set<Double> expectedMode = new HashSet<>();
        expectedMode.add(10.0);

        BasicStats stats = mockRequest(data);

        Assertions.assertEquals(13.75, stats.getMean(),0.0001,"Incorrect Mean");
        Assertions.assertEquals(12.5, stats.getMedian(), "Incorrect Median");
        Assertions.assertEquals(expectedMode, stats.getMode(), "Incorrect Mode");

    }

    @Test
    public void testBiModal()
    {
        List<Double> data = Arrays.asList(10.0,15.0,10.0,20.0,15.0);

        Set<Double> expectedMode = new HashSet<>();
        expectedMode.add(10.0);
        expectedMode.add(15.0);

        BasicStats stats = mockRequest(data);

        Assertions.assertEquals(14, stats.getMean(),0.0001,"Incorrect Mean");
        Assertions.assertEquals(15.0, stats.getMedian(), "Incorrect Median");
        Assertions.assertEquals(expectedMode, stats.getMode(), "Incorrect Mode");

    }

    @Test
    public void testTriModal()
    {
        List<Double> data = Arrays.asList(10.0,15.0,10.0,20.0,15.0,20.0);

        Set<Double> expectedMode = new HashSet<>();
        expectedMode.add(10.0);
        expectedMode.add(15.0);
        expectedMode.add(20.0);

        BasicStats stats = mockRequest(data);

        Assertions.assertEquals(15, stats.getMean(),0.0001,"Incorrect Mean");
        Assertions.assertEquals(15.0, stats.getMedian(), "Incorrect Median");
        Assertions.assertEquals(expectedMode, stats.getMode(), "Incorrect Mode");

    }
    
    private BasicStats mockRequest(List<Double> inputData)
    {
        BasicStatsLambda lambda = new BasicStatsLambda();

        BasicStatsRequest request = new BasicStatsRequest();
        request.setData(inputData);
        requestEvent.setHttpMethod("POST");

        requestEvent.setBody(gson.toJson(request));

        APIGatewayProxyResponseEvent response = lambda.handleRequest(requestEvent, new MockContext());

        ApiResponse apiResponse = gson.fromJson(response.getBody(), ApiResponse.class);
        BasicStats stats = gson.fromJson(gson.toJsonTree(apiResponse.getData()), BasicStats.class);

        Assertions.assertEquals(200, apiResponse.getStatusCode());
        
        return stats;
    }

    @Test
    public void testInvalidMethod()
    {
        BasicStatsLambda lambda = new BasicStatsLambda();
        requestEvent.setHttpMethod("GET");

        APIGatewayProxyResponseEvent response = lambda.handleRequest(requestEvent, new MockContext());

        ApiResponse apiResponse = gson.fromJson(response.getBody(), ApiResponse.class);

        Assertions.assertEquals(405, apiResponse.getStatusCode());

    }

    @Test
    public void testInvalidJsonBody()
    {
        BasicStatsLambda lambda = new BasicStatsLambda();
        requestEvent.setHttpMethod("POST");
        requestEvent.setBody("{badjson}");


        APIGatewayProxyResponseEvent response = lambda.handleRequest(requestEvent, new MockContext());

        ApiResponse apiResponse = gson.fromJson(response.getBody(), ApiResponse.class);

        Assertions.assertEquals(500, apiResponse.getStatusCode());

    }


}
