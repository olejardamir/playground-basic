import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SampleClient {

    public static void main(String[] theArgs) throws Exception {
//        new Task2A("data/last_names.txt", "http://hapi.fhir.org/baseR4");

        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        client.registerInterceptor(new LoggingInterceptor(false));

//        String[] lastNames = { "Meyer-Landrut", "Schumann" };
//        List<Bundle> responses = new ArrayList<>();
//        for (String lastName : lastNames) {
//            // Search for Patient resources with the current last name
//            Bundle response = client
//                    .search()
//                    .forResource("Patient")
//                    .where(Patient.FAMILY.matches().value(lastName))
//                    .returnBundle(Bundle.class)
//                    .execute();
//
//            // Add the response to the list
//            responses.add(response);
//        }
//        for(Bundle response: responses) {
//            List<String> lastNamesAll = response.getEntry().stream()
//                    .map(entry -> ((Patient) entry.getResource()).getName())
//                    .flatMap(List::stream)
//                    .filter(HumanName::hasFamily)
//                    .map(HumanName::getFamily)
//                    .collect(Collectors.toList());
//
//            System.out.println(lastNamesAll);
//        }
    }

}
