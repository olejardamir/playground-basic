public class SampleClient {

    public static void main(String[] theArgs) throws Exception {
        BundleGetter bundleGetter = new BundleGetter("http://hapi.fhir.org/baseR4");
        String lnFile = "data/last_names.txt";
//        new Task2A(lnFile, bundleGetter);
        Task2BC task2BC = new Task2BC(lnFile, bundleGetter);
        long avgRespTimeA = task2BC.getAverageRun();
        Thread.sleep(5000);
        long avgRespTime1 = task2BC.getAverageRun();
        Thread.sleep(5000);
        long avgRespTime2 = task2BC.getAverageRun();
        Thread.sleep(5000);

        bundleGetter.removeCache();
        long avgRespTime3 = task2BC.getAverageRun();

        System.out.println("Average response time for each request: " + avgRespTimeA);
        System.out.println(avgRespTime1);
        System.out.println(avgRespTime2);
        System.out.println(avgRespTime3);


    }








}
