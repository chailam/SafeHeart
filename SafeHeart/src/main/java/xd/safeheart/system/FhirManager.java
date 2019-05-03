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

import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
            this.practitioner = this.createModelPractitoner(targetPractitioner);
            
            // get all encounter for that practitioner
            Bundle encBundle = this.client.search()
                    .forResource(org.hl7.fhir.dstu3.model.Encounter.class)
                    .where(org.hl7.fhir.dstu3.model.Encounter.PARTICIPANT.hasId(id))
                    .returnBundle(Bundle.class)
                    .execute();
            
            encBundle.getEntry().forEach((entry) -> {
                // within each entry is a resource
                this.insertPatientAndEncounter(entry);
            });
            
            // keep going to next page
            while (encBundle.getLink(Bundle.LINK_NEXT) != null) {
                // load next page
                encBundle = client.loadPage().next(encBundle).execute();
                encBundle.getEntry().forEach((entry) -> {
                // within each entry is a resource
                    this.insertPatientAndEncounter(entry);
                });
            }
            
            // maybe use url $everything http://build.fhir.org/patient-operation-everything.html
            
            //System.out.println(this.encounterMap.get("9323").getId());
                    
            return true;
        }
    }
    
    private void insertPatientAndEncounter(Bundle.BundleEntryComponent entry)
    {
        String entryId;
        // get id of entry
        entryId = entry.getResource().getIdElement().getIdPart();
        org.hl7.fhir.dstu3.model.Encounter targetEncounter;
        // get encounter by id
        targetEncounter = this.searchEncounterById(entry.getResource().getIdElement().getIdPart());
        org.hl7.fhir.dstu3.model.Patient encPatient;
        // get patient by url
        encPatient = client.read()
                .resource(org.hl7.fhir.dstu3.model.Patient.class)
                .withUrl(serverBaseUrl + targetEncounter.getSubject().getReference())
                .execute(); 
        xd.safeheart.model.Patient modelPatient;
        // create model patient
        modelPatient = this.createModelPatient(encPatient);
        
        // put into patientmap
        this.patientMap.put(Integer.toString(modelPatient.getId()), modelPatient);

        // put into encountermap
        this.encounterMap.put(entryId, 
                new xd.safeheart.model.Encounter(Integer.parseInt(entryId),
                        modelPatient,
                        this.practitioner
                )
        );
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
    
    private xd.safeheart.model.Practitioner createModelPractitoner(org.hl7.fhir.dstu3.model.Practitioner prac)
    {
        org.hl7.fhir.dstu3.model.HumanName name = prac.hasName() ? this.getOfficialName(prac.getName()) : null;
        String familyName = prac.hasName() && name != null ? name.getFamily() : "null";
        String givenName = prac.hasName() && name != null ? name.getGivenAsSingleString() : "null";
        return new xd.safeheart.model.Practitioner(Integer.parseInt(prac.getIdElement().getIdPart()), 
                    familyName,
                    givenName,
                    prac.hasBirthDate() ? this.calculateAgeFromDate(prac.getBirthDate()) : -1,
                    prac.hasGender() ? prac.getGender().getDefinition() : "null"
            );
    }
    
    private xd.safeheart.model.Patient createModelPatient(org.hl7.fhir.dstu3.model.Patient pat)
    {
        org.hl7.fhir.dstu3.model.HumanName name = pat.hasName() ? this.getOfficialName(pat.getName()) : null;
        String familyName = pat.hasName() && name != null ? name.getFamily() : "null";
        String givenName = pat.hasName() && name != null ? name.getGivenAsSingleString() : "null";
        return new xd.safeheart.model.Patient(Integer.parseInt(pat.getIdElement().getIdPart()),
                        familyName,
                        givenName,
                        pat.hasBirthDate() ? this.calculateAgeFromDate(pat.getBirthDate()) : -1, 
                        pat.hasGender() ? pat.getGender().getDefinition() : "null"
        );
    }
    
    private org.hl7.fhir.dstu3.model.HumanName getOfficialName (List<org.hl7.fhir.dstu3.model.HumanName> lname)
    {
        for (HumanName h : lname)
        {
            if (h.getUse() == org.hl7.fhir.dstu3.model.HumanName.NameUse.OFFICIAL)
            {
                return h;
            }
        }
        return null;
    }
    
    /*
    Calculate age from Date
    https://stackoverflow.com/questions/1116123/how-do-i-calculate-someones-age-in-java
    https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate
    */
    private int calculateAgeFromDate(Date dobDate)
    {
        LocalDate now = LocalDate.now();
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dobLocalDate = dobDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        return Period.between(dobLocalDate, now).getYears();
    }
}
