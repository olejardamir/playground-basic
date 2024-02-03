import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Client.BundleGetter;
import Processors.AverageResponseTimer;
import Processors.LastNameProcessor;

public class MainRun {

    private static final Logger logger = LoggerFactory.getLogger(MainRun.class);

    public static void main(String[] theArgs) throws Exception {
        BundleGetter bundleGetter = new BundleGetter("http://hapi.fhir.org/baseR4");
        String lnFile = "data/last_names.txt";
        new LastNameProcessor(lnFile, bundleGetter);
        AverageResponseTimer averageResponseTimer = new AverageResponseTimer(lnFile, bundleGetter);
        long avgRespTimeA = averageResponseTimer.getAverageRun();
        long avgRespTime1 = averageResponseTimer.getAverageRun();
        long avgRespTime2 = averageResponseTimer.getAverageRun();

        Thread.sleep(10000); //INSERT THE MILLISECONDS DEPENDING ON YOUR DEFINITION OF "ENOUGH TIME".
        long avgRespTime3 = averageResponseTimer.getAverageRunNoCache();

        logger.info("Average response time for each request: {}", avgRespTimeA);
        logger.info("Is average response time for loop 2 shorter than loop 1 and loop 3: {}", (avgRespTime2 < avgRespTime1 && avgRespTime2 < avgRespTime3));
    }
}
