import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

public class Task2BC {
    private String[] lastNames;
    private BundleGetter bundleGetter;
    public Task2BC(String lnFile, BundleGetter bundleGetter) throws Exception {
        this.lastNames = readLinesFromFile(lnFile);
        this.bundleGetter = bundleGetter;
    }

    private String[] readLinesFromFile(String filePath) throws Exception {
        List<String> linesList = FileUtils.readLines(new File(filePath), "UTF-8");
        return linesList.toArray(new String[0]);
    }

    public long getAverageRun() {
        long sum = 0;
        for (String lname : lastNames) {
            long time = bundleGetter.getLNTimedCall(lname);
            sum += time;
        }
        return sum / lastNames.length;
    }


}
