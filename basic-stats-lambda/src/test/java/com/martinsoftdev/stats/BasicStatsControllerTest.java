package com.martinsoftdev.stats;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class BasicStatsControllerTest
{
    private BasicStatsController controller = new BasicStatsController();

    @Test
    public void test3Numbers()
    {
        List<Double> data = Arrays.asList(1.0,12.0,10.0);

        Set<Double> expectedMode = new HashSet<>();

        BasicStats stats = controller.generateStats(data);

        Assertions.assertEquals(7.6666, stats.getMean(),0.0001,"Incorrect Mean");
        Assertions.assertEquals(10, stats.getMedian(), "Incorrect Median");
        Assertions.assertEquals(expectedMode, stats.getMode(), "Incorrect Mode");

    }

    @Test
    public void test2Numbers()
    {
        List<Double> data = Arrays.asList(12.0,10.0);

        Set<Double> expectedMode = new HashSet<>();

        BasicStats stats = controller.generateStats(data);

        Assertions.assertEquals(11, stats.getMean(),0.0001,"Incorrect Mean");
        Assertions.assertEquals(11, stats.getMedian(), "Incorrect Median");
        Assertions.assertEquals(expectedMode, stats.getMode(), "Incorrect Mode");

    }

    @Test
    public void test1Number()
    {
        List<Double> data = Arrays.asList(10.0);

        Set<Double> expectedMode = new HashSet<>();

        BasicStats stats = controller.generateStats(data);

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

        BasicStats stats = controller.generateStats(data);

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

        BasicStats stats = controller.generateStats(data);

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

        BasicStats stats = controller.generateStats(data);

        Assertions.assertEquals(15, stats.getMean(),0.0001,"Incorrect Mean");
        Assertions.assertEquals(15.0, stats.getMedian(), "Incorrect Median");
        Assertions.assertEquals(expectedMode, stats.getMode(), "Incorrect Mode");

    }

}
