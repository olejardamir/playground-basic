package Processors;

import Client.BundleGetter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static java.nio.charset.StandardCharsets.UTF_8;

public class AverageResponseTimer {
    private final String[] lastNames;
    private final BundleGetter bundleGetter;
    public AverageResponseTimer(String lnFile, BundleGetter bundleGetter) throws Exception {
        this.lastNames = readLinesFromFile(lnFile);
        this.bundleGetter = bundleGetter;
    }

    private String[] readLinesFromFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        List<String> linesList = Files.readAllLines(path, UTF_8);
        return linesList.toArray(new String[0]);
    }

    public long getAverageRun() {
        long sum = 0;
        for (String lastname : lastNames) {
            long time = bundleGetter.getLNTimedCall(lastname);
            sum += time;
        }
        return sum / lastNames.length;
    }

}
