/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package XD.SafeHeart;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.client.IGenericClient;
import org.hl7.fhir.dstu3.model.*;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Aik Han Ng <angg0008@student.monash.edu>
 */
public class FhirManager {
    private final FhirContext ctx;
    private final IGenericClient client;
    public FhirManager(String serverBaseUrl) {
        // context - create this once, as it's an expensive operation
        // see http://hapifhir.io/doc_intro.html
        ctx = FhirContext.forDstu3();
        
        // increase timeouts since the server might be powered down
        // see http://hapifhir.io/doc_rest_client_http_config.html
        ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
        ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);
        
        // create the RESTful client to work with our FHIR server
        // see http://hapifhir.io/doc_rest_client.html
        client = ctx.newRestfulGenericClient(serverBaseUrl);
    }
    
    public void searchPatientById(String id) throws IOException {
        try {
            /* Not bundle because it's unique response
             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
            */
            // problem as name might collide with our patient
            Patient responsePatient = this.client.read()
                    .resource(Patient.class)
                    .withId(id)
                    .execute();
            System.out.println("Family name: " + responsePatient.getName().get(0).getFamily());
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
        }
    }
}
