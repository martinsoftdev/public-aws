package com.martinsoftdev.stats;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicStatsController
{
    public BasicStats generateStats(List<Double> data)
    {
        BasicStats stats = new BasicStats();

        if(data != null && data.size() > 0)
        {
            Collections.sort(data);

            Double median = null;
            int medianPos = (data.size() +1)/2;

            if(data.size()%2 == 0) // if the number of data points are even then median is average of the middle 2 points
            {
                median =  (data.get(medianPos-1)+data.get(medianPos))/2;
            }
            else
            {
                median = data.get(medianPos-1);
            }

            stats.setMedian(median);

            double total = 0;
            int modeCount = 2; // if no number appears twice then there is no mode so initialize this to 2
            int currentSequenceCount = 1;

            Set<Double> modeValue = new HashSet<>(); // there can be more than one mode

            double currentNumber = data.get(0);
            total = currentNumber;

            for (int c = 1; c < data.size(); c++)
            {
                currentNumber = data.get(c);

                if(currentNumber != data.get(c-1) )
                    currentSequenceCount = 1;
                else
                {
                    currentSequenceCount++;

                    if (currentSequenceCount == modeCount) // add number to mode if it matches mode count
                    {
                        modeValue.add(currentNumber);
                    }
                    else if (currentSequenceCount > modeCount) // new mode count
                    {
                        modeCount = currentSequenceCount;

                        if(modeValue.size() > 1) // number is new highest count so remove others if there are any
                        {
                            modeValue.clear();
                            modeValue.add(currentNumber);
                        }
                    }
                }

                total += currentNumber;
            }

            stats.setMode(modeValue);
            stats.setMean(total / data.size());
        }

        return stats;
    }
}
