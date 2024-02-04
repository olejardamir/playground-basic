import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Client.BundleGetter;
import Processors.AverageResponseTimer;
import Processors.LastNameProcessor;


//NOTE: Caching is documented here: https://hapifhir.io/hapi-fhir/docs/server_jpa/configuration.html
// Per document, we can disable it globally (on server) or per request
// Since I do not have access to "http://hapi.fhir.org/baseR4", I can do it only per request.

public class MainRun {

    private static final Logger logger = LoggerFactory.getLogger(MainRun.class);

    public static void main(String[] theArgs) throws Exception {
        try {
            logger.info("Connecting to baseR4 test server...");
            BundleGetter bundleGetter = new BundleGetter("http://hapi.fhir.org/baseR4");
            logger.info("Saving random 20 lastnames to a file.");
            String lnFile = "data/last_names.txt";
            new LastNameProcessor(lnFile, bundleGetter);
            AverageResponseTimer averageResponseTimer = new AverageResponseTimer(lnFile, bundleGetter);
            logger.info("Calculating average process time...");
            long avgRespTimeA = averageResponseTimer.getAverageRun();
            logger.info("Calculating average process time for loop 1");
            long avgRespTime1 = averageResponseTimer.getAverageRun();
            logger.info("Calculating average process time for loop 2");
            long avgRespTime2 = averageResponseTimer.getAverageRun();


            logger.info("Waiting for cache to reset");
            Thread.sleep(10000); //INSERT THE MILLISECONDS DEPENDING ON YOUR DEFINITION OF "ENOUGH TIME".
            logger.info("Calculating average process time for loop 3");
            long avgRespTime3 = averageResponseTimer.getAverageRunNoCache();

            logger.info("Average response time for each request: {}", avgRespTimeA);
            boolean shorter = (avgRespTime2 < avgRespTime1 && avgRespTime2 < avgRespTime3);
            logger.info("Average response time is shorter for loop 2 than for loop 1 and loop 3: {}", (avgRespTime2 < avgRespTime1 && avgRespTime2 < avgRespTime3));
            if (!shorter) {
                logger.warn("Loop 2 is not shorter than 1 and 3, increase the waiting-time for cache to reset, or try disabling it on a server-side");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
