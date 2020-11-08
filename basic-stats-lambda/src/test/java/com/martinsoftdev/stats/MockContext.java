package com.martinsoftdev.stats;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.io.IOException;

public class MockContext implements Context
{
    @Override
    public String getAwsRequestId()
    {
        return null;
    }

    @Override
    public String getLogGroupName()
    {
        return null;
    }

    @Override
    public String getLogStreamName()
    {
        return null;
    }

    @Override
    public String getFunctionName()
    {
        return null;
    }

    @Override
    public String getFunctionVersion()
    {
        return null;
    }

    @Override
    public String getInvokedFunctionArn()
    {
        return null;
    }

    @Override
    public CognitoIdentity getIdentity()
    {
        return null;
    }

    @Override
    public ClientContext getClientContext()
    {
        return null;
    }

    @Override
    public int getRemainingTimeInMillis()
    {
        return 0;
    }

    @Override
    public int getMemoryLimitInMB()
    {
        return 0;
    }

    @Override
    public LambdaLogger getLogger()
    {
        return new LambdaLogger()
        {
            @Override
            public void log(String s)
            {
                System.out.println(s);
            }

            @Override
            public void log(byte[] bytes)
            {
                try
                {
                    System.out.println();
                    System.out.write(bytes);
                    System.out.println();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
    }
}
