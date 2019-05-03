/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package xd.safeheart.system;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.client.IGenericClient;
import org.hl7.fhir.dstu3.model.*;
import xd.safeheart.model.*;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Aik Han Ng <angg0008@student.monash.edu>
 */
public class FhirManager {
    private final String serverBaseUrl;
    private final FhirContext ctx;
    private final IGenericClient client;
    private xd.safeheart.model.Practitioner practitioner;
    private HashMap<String, xd.safeheart.model.Encounter> encounterMap;
    private HashMap<String, xd.safeheart.model.Patient> patientMap;
    private HashMap<String, xd.safeheart.model.Observation> observationMap;
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
        
        //practitioner = null;
        
        encounterMap = new HashMap<String, xd.safeheart.model.Encounter>();
        patientMap = new HashMap<String, xd.safeheart.model.Patient>();
        observationMap = new HashMap<String, xd.safeheart.model.Observation>();
        
    }
    
    public boolean populateDataByPractitionerId(String id) {
        org.hl7.fhir.dstu3.model.Practitioner targetPractitioner = this.searchPractitionerById(id);
        if(targetPractitioner == null){ return false; }
        else {
            // Store practitioner
            System.out.println(targetPractitioner.getName().get(0).getGivenAsSingleString());
            this.practitioner = new xd.safeheart.model.Practitioner(id, 
                    targetPractitioner.getName().get(0).getFamilyAsSingleString(),
                    targetPractitioner.getName().get(0).getGivenAsSingleString()
            );
            
            Bundle encBundle = this.client.search()
                    .forResource(org.hl7.fhir.dstu3.model.Encounter.class)
                    .where(org.hl7.fhir.dstu3.model.Encounter.PARTICIPANT.hasId(id))
                    .returnBundle(Bundle.class)
                    .execute();
            
            System.out.println(encBundle.getTotal());
            
            encBundle.getEntry().forEach((entry) -> {
                // within each entry is a resource
                System.out.println(entry.getResource().getIdElement().getIdPart());
                System.out.println(this.searchEncounterById(entry.getResource().getIdElement().getIdPart()).getSubject().getReference());
                //this.encounterMap.put(entryId, this.searchEncounterById(id));
            });
            
            // maybe make this a method
            while (encBundle.getLink(Bundle.LINK_NEXT) != null) {
                System.out.println("next-page");
                // load next page
                encBundle = client.loadPage().next(encBundle).execute();
                encBundle.getEntry().forEach((entry) -> {
                // within each entry is a resource
                String entryId = entry.getResource().getIdElement().getIdPart();
                System.out.println(entryId);
                //this.encounterMap.put(entryId, this.searchEncounterById(entryId));
                System.out.println(this.searchEncounterById(entryId).getSubject().getReference());
            });
            }
            
            // maybe use url $everything http://build.fhir.org/patient-operation-everything.html
            
            //System.out.println(this.encounterMap.get("9323").getId());
                    
            return true;
        }
    }
    
    private org.hl7.fhir.dstu3.model.Resource searchResourceByUrl(String url) {
        return client.search()
                .byUrl(url)
                .returnBundle(Bundle.class)
                .execute();
    }
    
    private org.hl7.fhir.dstu3.model.Patient searchPatientById(String id) {
        try {
            /* Not bundle because it's unique response
             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
            */
            // problem as name might collide with our patient
            return this.client.read()
                    .resource(org.hl7.fhir.dstu3.model.Patient.class)
                    .withId(id)
                    .execute();
            // PROBLEM: how to get all enounters that has patient?
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
            return null;
        }
    }
        
    private org.hl7.fhir.dstu3.model.Practitioner searchPractitionerById(String id) {
        try {
            /* Not bundle because it's unique response
             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
            */
            // will cause error when bundle size (results) = 0
            return this.client.read()
                    .resource(org.hl7.fhir.dstu3.model.Practitioner.class)
                    .withId(id)
                    .execute();
//            System.out.println("prac Family name: " + responsePractitoner.getName().get(0).getFamily());
//            System.out.println("prac Family name: " + responsePractitoner.getName().get(0).getFamily());
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
            return null;
        }
    }
    
    private org.hl7.fhir.dstu3.model.Encounter searchEncounterById(String id) {
        try {
            /* Not bundle because it's unique response
             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
            */
            return this.client.read()
                    .resource(org.hl7.fhir.dstu3.model.Encounter.class)
                    .withId(id)
                    .execute();
            
//            // Might be more than one practicioners
//            List<org.hl7.fhir.dstu3.model.Encounter.EncounterParticipantComponent> practicionerComps = responseEncounter.getParticipant();
//            for (org.hl7.fhir.dstu3.model.Encounter.EncounterParticipantComponent component : practicionerComps)
//            {
//                Reference practicionerRef = component.getIndividual();
//                // relative string url of reference
//                System.out.println("enc prac reference: " + practicionerRef.getReference());
//            }
//            Reference patientRef = responseEncounter.getSubject();
//            // relative string url of reference
//            System.out.println("enc patient reference: " + patientRef.getReference());
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
            return null;
        }
    }
    
    private org.hl7.fhir.dstu3.model.DiagnosticReport searchDiagReportById(String id) {
        try {
            /* Not bundle because it's unique response
             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
            */
            return this.client.read()
                    .resource(org.hl7.fhir.dstu3.model.DiagnosticReport.class)
                    .withId(id)
                    .execute();
            
//            System.out.println("Diagreport patient reference: " + responseDiagReport.getSubject().getReference());
//            System.out.println("Diagreport encounter reference: " + responseDiagReport.getContext().getReference());
//            String display = String.format("Diagreport observer '%s' reference: ", responseDiagReport.getResult().get(0).getDisplay());
//            // getresult returns list of references to all observers
//            System.out.println(display + responseDiagReport.getResult().get(0).getReference());
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
            return null;
        }
    }
    
    private org.hl7.fhir.dstu3.model.Observation searchObservationById(String id) {
        try {
            /* Not bundle because it's unique response
             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
            */
            return this.client.read()
                    .resource(org.hl7.fhir.dstu3.model.Observation.class)
                    .withId(id)
                    .execute();
//            System.out.println("Observation patient reference: " + responseObservation.getSubject().getReference());
//            System.out.println("Observation encounter reference: " + responseObservation.getContext().getReference());
//            // refer to observation structure in server
//            System.out.format("Observation '%s' value: %s %s", responseObservation.getCode().getText(), responseObservation.getValueQuantity().getValue(), responseObservation.getValueQuantity().getUnit());
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
            return null;
        }
    }
}
