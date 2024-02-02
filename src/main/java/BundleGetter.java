import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class BundleGetter {

    private IGenericClient client;
    private final StopwatchTimer stopwatchTimer = new StopwatchTimer();
    private final String url;


    public BundleGetter(String url){
        this.url = url;
        FhirContext fhirContext = FhirContext.forR4();
        this.client = fhirContext.newRestfulGenericClient(this.url);
        this.client.registerInterceptor(new LoggingInterceptor(false));
        client.registerInterceptor(stopwatchTimer);
    }

    public Bundle getBundle() {
        return this.client
                .search()
                .forResource("Patient")
                .returnBundle(Bundle.class)
                .execute();
    }


    public long getLNTimedCall(String lastName) {
         this.client
                .search()
                .forResource("Patient")
                .where(Patient.FAMILY.matches().value(lastName))
                .returnBundle(Bundle.class)
                .execute();
        return stopwatchTimer.getRequestTime();
    }

    public void removeCache(){
        //noinspection unused httpClient is not redundant!
        CloseableHttpClient httpClient = HttpClients.custom()
                .addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {
                    // Disable caching
                    request.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    request.setHeader("Pragma", "no-cache");
                    request.setHeader("Expires", "0");
                })
                .addInterceptorFirst((HttpResponseInterceptor) (response, context) -> {
                    // Disable caching for responses
                    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    response.setHeader("Pragma", "no-cache");
                    response.setHeader("Expires", "0");
                })
                .disableCookieManagement()   // no cookies
                .build();

        FhirContext fhirContext = FhirContext.forR4();
        this.client = fhirContext.newRestfulGenericClient(this.url);
        this.client.registerInterceptor(new LoggingInterceptor(false));
        client.registerInterceptor(stopwatchTimer);
    }

}
