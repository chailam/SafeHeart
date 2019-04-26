/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package XD.SafeHeart.System;

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
    private final String serverBaseUrl;
    private final FhirContext ctx;
    private final IGenericClient client;
    public FhirManager(String inputUrl) {
        serverBaseUrl = inputUrl;
        
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
            System.out.println("pat Family name: " + responsePatient.getName().get(0).getFamily());
            // PROBLEM: how to get all enounters that has patient?
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
        }
    }
        
    public void searchPractitionerById(String id) throws IOException {
        try {
            /* Not bundle because it's unique response
             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
            */
            Practitioner responsePractitoner = this.client.read()
                    .resource(Practitioner.class)
                    .withId(id)
                    .execute();
            System.out.println("prac Family name: " + responsePractitoner.getName().get(0).getFamily());
            System.out.println("prac Family name: " + responsePractitoner.getName().get(0).getFamily());
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
        }
    }
    
    public void searchEncounterById(String id) throws IOException {
        try {
            /* Not bundle because it's unique response
             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
            */
            Encounter responseEncounter = this.client.read()
                    .resource(Encounter.class)
                    .withId(id)
                    .execute();
            
            // Might be more than one practicioners
            List<Encounter.EncounterParticipantComponent> practicionerComps = responseEncounter.getParticipant();
            for (Encounter.EncounterParticipantComponent component : practicionerComps)
            {
                Reference practicionerRef = component.getIndividual();
                // relative string url of reference
                System.out.println("enc prac reference: " + practicionerRef.getReference());
            }
            Reference patientRef = responseEncounter.getSubject();
            // relative string url of reference
            System.out.println("enc patient reference: " + patientRef.getReference());
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
        }
    }
    
    public void searchDiagReportById(String id) throws IOException {
        try {
            /* Not bundle because it's unique response
             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
            */
            DiagnosticReport responseDiagReport = this.client.read()
                    .resource(DiagnosticReport.class)
                    .withId(id)
                    .execute();
            
            System.out.println("Diagreport patient reference: " + responseDiagReport.getSubject().getReference());
            System.out.println("Diagreport encounter reference: " + responseDiagReport.getContext().getReference());
            String display = String.format("Diagreport observer '%s' reference: ", responseDiagReport.getResult().get(0).getDisplay());
            // getresult returns list of references to all observers
            System.out.println(display + responseDiagReport.getResult().get(0).getReference());
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
        }
    }
    
    public void searchObservationById(String id) throws IOException {
        try {
            /* Not bundle because it's unique response
             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
            */
            Observation responseObservation = this.client.read()
                    .resource(Observation.class)
                    .withId(id)
                    .execute();
            System.out.println("Observation patient reference: " + responseObservation.getSubject().getReference());
            System.out.println("Observation encounter reference: " + responseObservation.getContext().getReference());
            // refer to observation structure in server
            System.out.format("Observation '%s' value: %s %s", responseObservation.getCode().getText(), responseObservation.getValueQuantity().getValue(), responseObservation.getValueQuantity().getUnit());
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
        }
    }
}
