import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import org.apache.commons.io.FileUtils;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Task2A {

    private final String url;

    public Task2A(String fileLocation, String url) throws Exception{
        this.url = url;
        File lastNamesFile = new File(fileLocation);
        if(lastNamesFile.exists()) FileUtils.forceDelete(lastNamesFile); //we need a fresh, new file
        Bundle response = getBundleData();
        List<String> lastNamesAll = getAllLastnames(response);
        List<String> lastNames = get20Lastnames(lastNamesAll);
        saveLNamesToFile(lastNamesFile, lastNames);
    }



    private void saveLNamesToFile(File lastNamesFile, List<String> lastNames) throws Exception {
        FileUtils.writeLines(lastNamesFile, lastNames);
    }

    private List<String> get20Lastnames(List<String> lastNamesAll) {
        List<String> selectedLastNames = lastNamesAll.stream()
                .limit(20)
                .collect(Collectors.toList());
        return selectedLastNames;
    }

    private List<String> getAllLastnames(Bundle response) {
        List<String> lastNamesAll = response.getEntry().stream()
                .map(entry -> ((Patient) entry.getResource()).getName())
                .flatMap(List::stream)
                .filter(HumanName::hasFamily)
                .map(HumanName::getFamily)
//                .distinct()
                .limit(200)
                .collect(Collectors.toList());

        Collections.shuffle(lastNamesAll);
        return lastNamesAll;
    }

    private Bundle getBundleData() {
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient(this.url);
        client.registerInterceptor(new LoggingInterceptor(false));

        return client
                .search()
                .forResource("Patient")
                .returnBundle(Bundle.class)
                .execute();
    }
}
