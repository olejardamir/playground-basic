package Client;

import Interceptors.StopwatchTimer;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class BundleGetter {

    private IGenericClient client;
    private StopwatchTimer stopwatchTimer = new StopwatchTimer();
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

    public long getLNTimedCallNoCache(String lastName) {
        this.client
                .search()
                .forResource("Patient")
                .where(Patient.FAMILY.matches().value(lastName))
                .returnBundle(Bundle.class)
                .withAdditionalHeader("Cache-Control", "no-cache, no-store, must-revalidate")
                .withAdditionalHeader("Pragma", "no-cache")
                .withAdditionalHeader("Expires", "0")
                .execute();
        return stopwatchTimer.getRequestTime();
    }


}
