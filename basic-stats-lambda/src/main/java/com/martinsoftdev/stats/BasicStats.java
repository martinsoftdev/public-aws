package com.martinsoftdev.stats;

import java.util.List;
import java.util.Set;

public class BasicStats
{
    private Double mean = null;
    private Double median = null;
    private Set<Double> mode = null;

    public Double getMean()
    {
        return mean;
    }

    public void setMean(Double mean)
    {
        this.mean = mean;
    }

    public Double getMedian()
    {
        return median;
    }

    public void setMedian(Double median)
    {
        this.median = median;
    }

    public Set<Double> getMode()
    {
        return mode;
    }

    public void setMode(Set<Double> mode)
    {
        this.mode = mode;
    }
}
