import Client.BundleGetter;
import Processors.AverageResponseTimer;
import Processors.LastNameProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class MainTest {


    private BundleGetter bundleGetter;
    private String lnFile;


    @BeforeEach
    void setUp() throws Exception {
        lnFile = "data/last_names.txt";
        bundleGetter = new BundleGetter("http://hapi.fhir.org/baseR4");
    }

    @Test
    void testAverageResponseTimer() throws Exception {
        new LastNameProcessor(lnFile, bundleGetter);
        AverageResponseTimer averageResponseTimer = new AverageResponseTimer(lnFile, bundleGetter);
        assertNotNull(averageResponseTimer);

        // First set of runs
        long avgRespTime1 = averageResponseTimer.getAverageRun();
        long avgRespTime2 = averageResponseTimer.getAverageRun();

        // Verify that the average response times are calculated correctly
        assertTrue(avgRespTime1 > 0);
        assertTrue(avgRespTime2 > 0);

        // No cache
        long avgRespTime3 = averageResponseTimer.getAverageRunNoCache();

        // Verify that the cache removal has affected the response time calculation
        assertTrue(avgRespTime3 > 0);
        assertNotEquals(avgRespTime1, avgRespTime3);
        assertNotEquals(avgRespTime2, avgRespTime3);

    }


}
