import Client.BundleGetter;
import Processors.LastNameProcessor;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class LastNameProcessorTest {
    private BundleGetter bundleGetter;
    private LastNameProcessor lastNameProcessor;

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
        lastNameProcessor = new LastNameProcessor("testFile.txt", bundleGetter);
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
    void testSaveLNamesToFile() throws Exception {
        List<String> lastNames = Arrays.asList("Smith", "Johnson");
        File file = new File("testFile.txt");
        lastNameProcessor.saveLNamesToFile(file, lastNames);
        assertTrue(file.exists());
        List<String> lines = Files.readAllLines(file.toPath());
        assertEquals(lastNames, lines);
    }

    @Test
    void testGetAllLastNames() {
        Bundle bundle = new Bundle();
        Patient patient1 = new Patient();
        patient1.addName().setFamily("Smith");
        Patient patient2 = new Patient();
        patient2.addName().setFamily("Johnson");
        bundle.addEntry().setResource(patient1);
        bundle.addEntry().setResource(patient2);

        List<String> expectedLastNames = Arrays.asList("Smith", "Johnson");
        List<String> actualLastNames = lastNameProcessor.getAllLastnames(bundle);
        assertEquals(expectedLastNames, actualLastNames);
    }

    @Test
    void testGet20LastNames() {
        List<String> allLastNames = Arrays.asList("Smith", "Johnson", "Brown", "Doe");
        List<String> last20Names = lastNameProcessor.get20Lastnames(allLastNames);
        assertEquals(4, last20Names.size());
        assertEquals(allLastNames, last20Names);
    }
}
