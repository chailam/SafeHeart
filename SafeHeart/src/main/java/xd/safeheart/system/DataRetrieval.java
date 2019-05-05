/*
TODO: 1. You should add your FHIRManager code into here!!!!! (retrieve data from server & put it into class)
the list of the patient is lPatient and observation is lOb
*/
package xd.safeheart.system;

import xd.safeheart.model.*;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import org.hl7.fhir.dstu3.model.*;

import java.util.HashMap;
import java.util.Iterator;

import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hl7.fhir.dstu3.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.exceptions.FHIRException;

public class DataRetrieval extends AbstractDataRetrieval{
    private final String serverBaseUrl;
    private final FhirContext ctx;
    private final IGenericClient client;
    private xd.safeheart.model.Practitioner practitioner;
    private final HashMap<String, xd.safeheart.model.Encounter> encounterMap;
    //private final HashMap<String, xd.safeheart.model.DiagnosticReport> diagMap;
    
    
    public DataRetrieval(String inputUrl) {
        super();
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
        
        encounterMap = new HashMap<>();
        //diagMap = new HashMap<>();
    }
    
    public boolean populateDataByPractitionerId(String id) {
        System.out.println("Searching for Practitioner");
        org.hl7.fhir.dstu3.model.Practitioner targetPractitioner = this.searchPractitionerById(id);
        if(targetPractitioner == null){ 
            System.out.println("Searching for Practitioner FAILED");
            return false; 
        }
        else {
            System.out.println("Storing Practitioner");
            // Store practitioner
            this.practitioner = this.createModelPractitoner(targetPractitioner);
            
            System.out.println("Querying for all encounter of that practitioner");
            // get all encounter for that practitioner
            Bundle encBundle = this.client.search()
                    .forResource(org.hl7.fhir.dstu3.model.Encounter.class)
                    .where(org.hl7.fhir.dstu3.model.Encounter.PARTICIPANT.hasId(id))
                    .returnBundle(Bundle.class)
                    .execute();
            
            System.out.println("Generating all Patient and Encounter");
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
            
//            System.out.println("Querying for all DiagnosticReport");
//            this.queryDiagReport();
            
            // maybe use url $everything http://build.fhir.org/patient-operation-everything.html
            
            System.out.println("Data retrieved and populated");
                    
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
    
//    private void queryDiagReport()
//    {
//        // https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
//        // iterate encountermap
//        Iterator it = this.encounterMap.entrySet().iterator();
//        while (it.hasNext()) {
//            HashMap.Entry pair = (HashMap.Entry)it.next();
//            // code here
//            // get all diagReport for an Encounter
//            Bundle diagBundle = this.client.search()
//                    .forResource(org.hl7.fhir.dstu3.model.DiagnosticReport.class)
//                    .where(org.hl7.fhir.dstu3.model.DiagnosticReport.CONTEXT.hasId((String)pair.getKey()))
//                    .returnBundle(Bundle.class)
//                    .execute();
//            
//            diagBundle.getEntry().forEach((entry) -> {
//                // within each entry is a resource
//                this.insertDiagAndObserver(entry, (xd.safeheart.model.Encounter) pair.getValue());
//            });
//            
//            // keep going to next page
//            while (diagBundle.getLink(Bundle.LINK_NEXT) != null) {
//                // load next page
//                diagBundle = client.loadPage().next(diagBundle).execute();
//                diagBundle.getEntry().forEach((entry) -> {
//                // within each entry is a resource
//                    this.insertDiagAndObserver(entry, (xd.safeheart.model.Encounter) pair.getValue());
//                });
//            }
//            
//            // code end
//            it.remove(); // avoids a ConcurrentModificationException
//        }
//            
//    }
//    
//    private void insertDiagAndObserver(Bundle.BundleEntryComponent entry, xd.safeheart.model.Encounter enc)
//    {
//        String entryId;
//        // get id of entry
//        entryId = entry.getResource().getIdElement().getIdPart();
//        org.hl7.fhir.dstu3.model.DiagnosticReport targetDiag;
//        // get diagreport by id
//        targetDiag = this.searchDiagReportById(entry.getResource().getIdElement().getIdPart());
//        // insert into diag map
//        this.diagMap.put(entryId, new xd.safeheart.model.DiagnosticReport(Integer.parseInt(entryId), enc));
//        List<Reference> lResult = targetDiag.getResult();
//        // for every obs in diagreport
//        for (Reference r : lResult)
//        {
//            org.hl7.fhir.dstu3.model.Observation targetObs;
//            // get obs by url
//            targetObs = client.read()
//                    .resource(org.hl7.fhir.dstu3.model.Observation.class)
//                    .withUrl(serverBaseUrl + r.getReference())
//                    .execute(); 
//            String targetObsType = targetObs.getCode().getText();
//            if(targetObsType.equals("Total Cholesterol"))
//            {
//                org.hl7.fhir.dstu3.model.Patient obsPatient;
//                // get patient by url
//                obsPatient = client.read()
//                        .resource(org.hl7.fhir.dstu3.model.Patient.class)
//                        .withUrl(serverBaseUrl + targetObs.getSubject().getReference())
//                        .execute(); 
//                try {
//                    // create observer, then insert to the map
//                    choObsMap.put(targetObs.getIdBase(), new xd.safeheart.model.Observation(
//                            Integer.parseInt(targetObs.getIdElement().getIdPart()),
//                            targetObsType,
//                            targetObs.getValueQuantity().getUnit(),
//                            this.createModelPatient(obsPatient),
//                            targetObs.getValueQuantity().getValue().toString()
//                    ));
//                            } catch (FHIRException ex) {
//                    Logger.getLogger(DataRetrieval.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//    }
    
//    private org.hl7.fhir.dstu3.model.Resource searchResourceByUrl(String url) {
//        return client.search()
//                .byUrl(url)
//                .returnBundle(Bundle.class)
//                .execute();
//    }
//    
//    private org.hl7.fhir.dstu3.model.Patient searchPatientById(String id) {
//        try {
//            /* Not bundle because it's unique response
//             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
//            */
//            // problem as name might collide with our patient
//            return this.client.read()
//                    .resource(org.hl7.fhir.dstu3.model.Patient.class)
//                    .withId(id)
//                    .execute();
//            // PROBLEM: how to get all enounters that has patient?
//        } catch (Exception e) {
//            System.out.println("An error occurred trying to search:");
//            e.printStackTrace();
//            return null;
//        }
//    }
        
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
    
//    private org.hl7.fhir.dstu3.model.DiagnosticReport searchDiagReportById(String id) {
//        try {
//            /* Not bundle because it's unique response
//             * src: https://nhsconnect.github.io/gpconnect/foundations_use_case_read_a_patient.html
//            */
//            return this.client.read()
//                    .resource(org.hl7.fhir.dstu3.model.DiagnosticReport.class)
//                    .withId(id)
//                    .execute();
//            
////            System.out.println("Diagreport patient reference: " + responseDiagReport.getSubject().getReference());
////            System.out.println("Diagreport encounter reference: " + responseDiagReport.getContext().getReference());
////            String display = String.format("Diagreport observer '%s' reference: ", responseDiagReport.getResult().get(0).getDisplay());
////            // getresult returns list of references to all observers
////            System.out.println(display + responseDiagReport.getResult().get(0).getReference());
//        } catch (Exception e) {
//            System.out.println("An error occurred trying to search:");
//            e.printStackTrace();
//            return null;
//        }
//    }
    
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
        // practitioner has only one name
        org.hl7.fhir.dstu3.model.HumanName name = prac.hasName() ? prac.getName().get(0) : null;
        String familyName = name != null ? name.getFamily() : "null";
        String givenName = name != null ? name.getGivenAsSingleString() : "null";
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
        String familyName = name != null ? name.getFamily() : "null";
        String givenName = name != null ? name.getGivenAsSingleString() : "null";
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
    
    public xd.safeheart.model.Practitioner getPractitioner(){
        return this.practitioner;
    }
    
    public xd.safeheart.model.Observation getChoObsByPat(xd.safeheart.model.Patient p)
    {
        xd.safeheart.model.Observation output = null;
        Bundle obsBundle = this.client.search()
                    .forResource(org.hl7.fhir.dstu3.model.Observation.class)
                    .where(org.hl7.fhir.dstu3.model.Observation.SUBJECT.hasId(Integer.toString(p.getId())))
                    // search for total cholesterol code
                    .and(new TokenClientParam("code").exactly().code("2093-3"))
                    .sort().descending(org.hl7.fhir.dstu3.model.Observation.DATE)
                    .returnBundle(Bundle.class)
                    .execute();

        boolean firstPage = true;
        do {
            // found
            if(output != null)
            {
                break;
            }
            if(!firstPage)
            {
                // load next page
                obsBundle = client.loadPage().next(obsBundle).execute();
            }
            for (BundleEntryComponent entry : obsBundle.getEntry())
            {
                // within each entry is a resource
                org.hl7.fhir.dstu3.model.Observation o;
                // get diagreport by id
                o = this.searchObservationById(entry.getResource().getIdElement().getIdPart());
                try {
                        output = new xd.safeheart.model.Observation(
                                Integer.parseInt(o.getIdElement().getIdPart()),
                                o.getCode().getText(),
                                o.getValueQuantity().getUnit(),
                                p,
                                o.getValueQuantity().getValue().toString()
                        );
                    } catch (FHIRException ex) {
                    Logger.getLogger(DataRetrieval.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        // keep going to next page    
        } while (obsBundle.getLink(Bundle.LINK_NEXT) != null);
        
        if(output != null)
        {
            // store in data
            this.choObsMap.put(Integer.toString(output.getID()), output);
        }
        
        return output;
    }

    public void reGetObs() {
        // update all observation in the hashmap
        for (HashMap.Entry<String, xd.safeheart.model.Observation> entry : this.choObsMap.entrySet()) {
            org.hl7.fhir.dstu3.model.Observation o = this.searchObservationById(entry.getKey());
            try {
                this.choObsMap.put(entry.getKey(), new xd.safeheart.model.Observation(
                        Integer.parseInt(o.getIdElement().getIdPart()),
                        o.getCode().getText(),
                        o.getValueQuantity().getUnit(),
                        entry.getValue().getPatient(),
                        o.getValueQuantity().getValue().toString()
                ));
            } catch (FHIRException ex) {
                Logger.getLogger(DataRetrieval.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
