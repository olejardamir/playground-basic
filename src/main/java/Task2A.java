import org.apache.commons.io.FileUtils;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Task2A {


    public Task2A(String fileLocation, BundleGetter bundleGetter) throws Exception{
        File lastNamesFile = new File(fileLocation);
        if(lastNamesFile.exists()) FileUtils.forceDelete(lastNamesFile); //we need a fresh, new file
        Bundle response = bundleGetter.getBundle();
        List<String> lastNamesAll = getAllLastnames(response);
        Collections.shuffle(lastNamesAll); //we need them unsorted
        List<String> lastNames = get20Lastnames(lastNamesAll);
        saveLNamesToFile(lastNamesFile, lastNames);
    }



    private void saveLNamesToFile(File lastNamesFile, List<String> lastNames) throws Exception {
        FileUtils.writeLines(lastNamesFile, lastNames);
    }

    private List<String> get20Lastnames(List<String> lastNamesAll) {
        List<String> selectedLastNames = lastNamesAll.stream()

                .collect(Collectors.toList());
        return selectedLastNames;
    }

    List<String> getAllLastnames(Bundle response) {
        List<String> lastNamesAll = response.getEntry().stream()
                .map(entry -> ((Patient) entry.getResource()).getName())
                .flatMap(List::stream)
                .filter(HumanName::hasFamily)
                .map(HumanName::getFamily)
                .limit(200)
                .collect(Collectors.toList());
        return lastNamesAll;
    }
}
