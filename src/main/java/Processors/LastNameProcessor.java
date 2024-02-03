package Processors;

import org.apache.commons.io.FileUtils;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import Client.BundleGetter;

public class LastNameProcessor {


    public LastNameProcessor(String fileLocation, BundleGetter bundleGetter) throws Exception{
        File lastNamesFile = new File(fileLocation);
        if(lastNamesFile.exists()) FileUtils.forceDelete(lastNamesFile); //we need a fresh, new file
        Bundle response = bundleGetter.getBundle();
        List<String> lastNamesAll = getAllLastnames(response);
        Collections.shuffle(lastNamesAll); //we need them unsorted
        List<String> lastNames = get20Lastnames(lastNamesAll);
        saveLNamesToFile(lastNamesFile, lastNames);
    }



    public void saveLNamesToFile(File lastNamesFile, List<String> lastNames) throws Exception {
        Path path = lastNamesFile.toPath();
        Files.write(path, lastNames, StandardCharsets.UTF_8);
    }

    public List<String> get20Lastnames(List<String> lastNamesAll) {
        return new ArrayList<>(lastNamesAll);
    }

    public List<String> getAllLastnames(Bundle response) {
        return response.getEntry().stream()
                .map(entry -> ((Patient) entry.getResource()).getName())
                .flatMap(List::stream)
                .filter(HumanName::hasFamily)
                .map(HumanName::getFamily)
                .limit(200)
                .collect(Collectors.toList());
    }
}
