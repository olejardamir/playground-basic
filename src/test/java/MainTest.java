import Client.BundleGetter;
import Processors.AverageResponseTimer;
import Processors.LastNameProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MainTest {


    private BundleGetter bundleGetter;
    private String lnFile = "data/last_names.txt";


    @BeforeEach
    void setUp() throws Exception {
        bundleGetter = new BundleGetter("http://hapi.fhir.org/baseR4");
    }

    @Test
    void testAverageResponseTimer() throws Exception {
        new LastNameProcessor(lnFile, bundleGetter);
        AverageResponseTimer averageResponseTimer = new AverageResponseTimer(lnFile, bundleGetter);
        assertNotNull(averageResponseTimer);

        // First set of runs
        long avgRespTimeA = averageResponseTimer.getAverageRun();
        long avgRespTime1 = averageResponseTimer.getAverageRun();
        long avgRespTime2 = averageResponseTimer.getAverageRun();

        // Verify that the average response times are calculated correctly
        assertTrue(avgRespTimeA > 0);
        assertTrue(avgRespTime1 > 0);
        assertTrue(avgRespTime2 > 0);

        // Second set of runs after removing cache
        bundleGetter.removeCache();
        averageResponseTimer = new AverageResponseTimer(lnFile, bundleGetter);
        long avgRespTime3 = averageResponseTimer.getAverageRun();

        // Verify that the cache removal has affected the response time calculation
        assertTrue(avgRespTime3 > 0);
        assertNotEquals(avgRespTimeA, avgRespTime3);
        assertNotEquals(avgRespTime1, avgRespTime3);
        assertNotEquals(avgRespTime2, avgRespTime3);


        // Verify "you should expect to see loop 2 with a shorter average response time than loop 1 and 3"
//        assertTrue(avgRespTime2 <= avgRespTime1);
//        assertTrue(avgRespTime2 <= avgRespTime3);

    }


}
