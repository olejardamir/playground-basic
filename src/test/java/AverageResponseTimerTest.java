import Client.BundleGetter;
import Processors.AverageResponseTimer;
import Processors.LastNameProcessor;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AverageResponseTimerTest {

    private BundleGetter bundleGetter;
    private AverageResponseTimer averageResponseTimer;


    @BeforeEach
    void setUp() throws Exception {
        bundleGetter = mock(BundleGetter.class);
        Bundle bundle = new Bundle();
        Patient patient1 = new Patient();
        patient1.addName().setFamily("Smith");
        Patient patient2 = new Patient();
        patient2.addName().setFamily("Johnson");
        bundle.addEntry().setResource(patient1);
        bundle.addEntry().setResource(patient2);
        when(bundleGetter.getBundle()).thenReturn(bundle);
        LastNameProcessor lastNameProcessor = new LastNameProcessor("testFile.txt", bundleGetter);
        List<String> lastNames = Arrays.asList("Smith", "Johnson");
        File file = new File("testFile.txt");
        lastNameProcessor.saveLNamesToFile(file, lastNames);
    }

    @AfterEach
    void tearDown() {
        // Delete the test file after each test
        File file = new File("testFile.txt");
        if (file.exists()) {
            file.delete();
        }
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
