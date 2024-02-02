import Client.BundleGetter;
import Processors.AverageResponseTimer;
import Processors.LastNameProcessor;

public class MainRun {

    public static void main(String[] theArgs) throws Exception {
        BundleGetter bundleGetter = new BundleGetter("http://hapi.fhir.org/baseR4");
        String lnFile = "data/last_names.txt";
        new LastNameProcessor(lnFile, bundleGetter);
        AverageResponseTimer averageResponseTimer = new AverageResponseTimer(lnFile, bundleGetter);
        long avgRespTimeA = averageResponseTimer.getAverageRun();
        long avgRespTime1 = averageResponseTimer.getAverageRun();
        long avgRespTime2 = averageResponseTimer.getAverageRun();

        bundleGetter.removeCache();
        averageResponseTimer = new AverageResponseTimer(lnFile, bundleGetter);
        long avgRespTime3 = averageResponseTimer.getAverageRun();

        System.out.println("Average response time for each request: " + avgRespTimeA);
        System.out.println(avgRespTime1);
        System.out.println(avgRespTime2);
        System.out.println(avgRespTime3);
    }
}
