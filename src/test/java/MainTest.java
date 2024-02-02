import Client.BundleGetter;
import Processors.AverageResponseTimer;
import Processors.LastNameProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class MainTest {

    private BundleGetter mockBundleGetter;
    private LastNameProcessor mockLastNameProcessor;
    private AverageResponseTimer mockAverageResponseTimer;

    @BeforeEach
    void setUp() throws Exception {
        mockBundleGetter = mock(BundleGetter.class);
        mockLastNameProcessor = mock(LastNameProcessor.class);
        mockAverageResponseTimer = mock(AverageResponseTimer.class);
    }

    @Test
    void testMain() throws Exception {

    }
}
