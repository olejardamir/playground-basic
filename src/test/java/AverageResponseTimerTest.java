import Client.BundleGetter;
import Processors.AverageResponseTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AverageResponseTimerTest {

    private BundleGetter bundleGetter;
    private AverageResponseTimer averageResponseTimer;

    @BeforeEach
    void setUp() {
        bundleGetter = mock(BundleGetter.class);
    }

    @Test
    void testGetAverageRun() throws Exception {
        String[] lastNames = {"Smith", "Johnson", "Doe"};
        when(bundleGetter.getLNTimedCall("Smith")).thenReturn(100L);
        when(bundleGetter.getLNTimedCall("Johnson")).thenReturn(150L);
        when(bundleGetter.getLNTimedCall("Doe")).thenReturn(200L);
        averageResponseTimer = new AverageResponseTimer("testFile.txt", bundleGetter);
        long averageRun = averageResponseTimer.getAverageRun();
        System.out.println("Actual Average Run: " + averageRun);
        assertEquals(125, averageRun, "Expected average response time of 125, but got " + averageRun);
    }


    @Test
    void testGetAverageRun_NoLastNames() throws Exception {
        String[] lastNames = {};
        averageResponseTimer = new AverageResponseTimer("testFile.txt", bundleGetter);
        long averageRun = averageResponseTimer.getAverageRun();
        assertEquals(0, averageRun);
    }
}
