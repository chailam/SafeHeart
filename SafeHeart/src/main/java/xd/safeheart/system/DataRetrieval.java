/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package xd.safeheart.system;

import xd.safeheart.model.*;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import org.hl7.fhir.dstu3.model.*;

import java.util.HashMap;
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


/**
 * It retrieves data from the FHIR server, and stores it here in the form of model (package).
 * @author Chai Lam
 * @author Aik Han
 */
public class DataRetrieval implements AbstractDataRetrieval{
    private final String serverBaseUrl;
    private final FhirContext ctx;
    private final IGenericClient client;
    private xd.safeheart.model.Practitioner practitioner;
    private final HashMap <String, xd.safeheart.model.Patient> patientMap;
    private final HashMap <String, xd.safeheart.model.Encounter> encounterMap;
    private final HashMap <String, xd.safeheart.model.Observation> choObsMap;
    private final HashMap <String, ArrayList<xd.safeheart.model.Observation>> bloodDiasObsMap;
    private final HashMap <String, ArrayList<xd.safeheart.model.Observation>> bloodSysObsMap;
    private final HashMap <String, xd.safeheart.model.Observation> tobacObsMap;
    
    
    public DataRetrieval(String inputUrl) {
        //super();
        serverBaseUrl = inputUrl;
        patientMap = new HashMap<>();
        encounterMap = new HashMap<>();
        choObsMap = new HashMap<>();
        bloodDiasObsMap = new HashMap<>();
        bloodSysObsMap = new HashMap<>();
        tobacObsMap = new HashMap<>();
        
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
    
    /**
     * Gets all Patients of a Practitioner by its id, then store all the data
     * @param id
     * @return boolean to denote it has successfully retrieve data or not
     */
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
    
    /**
     * Function done for every entry to get the encounter and the patient related, and store them
     */
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
       
    /**
     * Simply read the Practitioner by id
     */
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
    
    /**
     * Simply read the Encounter by id
     */
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
    
    /**
     * Simply read the Observation by id
     */
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
    
    /**
     * Creates a Practitioner based from our model, from the fhir hapi's model
     */
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
    
    /**
     * Creates a Patient based from our model, from the fhir hapi's model
     */
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
    
    /**
     * Gets the official(NameUse.OFFICIAL) HumanName out of the list
     */
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
    
    /**
     * Calculate age from Date
     * Sources:
     * https://stackoverflow.com/questions/1116123/how-do-i-calculate-someones-age-in-java
     * https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate
    */
    private int calculateAgeFromDate(Date dobDate)
    {
        LocalDate now = LocalDate.now();
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dobLocalDate = dobDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        return Period.between(dobLocalDate, now).getYears();
    }
    
    // getters
    
    public xd.safeheart.model.Practitioner getPractitioner(){
        return this.practitioner;
    }
    
    /**
     * Gets only the latest Observation by Patient model
     * "2093-3" for "Total Cholesterol", "72166-2" for "Tobacco smoking status NHIS"
     */
    public xd.safeheart.model.Observation getRecentObsByPat(xd.safeheart.model.Patient p, String codeStr)
    {
        xd.safeheart.model.Observation output = null;
        Bundle obsBundle = this.client.search()
                    .forResource(org.hl7.fhir.dstu3.model.Observation.class)
                    .where(org.hl7.fhir.dstu3.model.Observation.SUBJECT.hasId(Integer.toString(p.getId())))
                    // search for code
                    .and(new TokenClientParam("code").exactly().code(codeStr))
                    .sort().ascending(org.hl7.fhir.dstu3.model.Observation.DATE)
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
            // current page entries
            for (BundleEntryComponent entry : obsBundle.getEntry())
            {
                // within each entry is a resource
                org.hl7.fhir.dstu3.model.Observation o;
                // get diagreport by id
                o = this.searchObservationById(entry.getResource().getIdElement().getIdPart());
                try {
                    switch (codeStr)
                    {
                        case "2093-3":
                            output = new xd.safeheart.model.Observation(
                                Integer.parseInt(o.getIdElement().getIdPart()),
                                o.getCode().getText(),
                                o.getValueQuantity().getUnit(),
                                p,
                                o.getValueQuantity().getValue().toString()
                            );
                            // store in data
                            this.choObsMap.put(Integer.toString(p.getId()), output);
                            break;
                        case "72166-2":
                            output = new xd.safeheart.model.Observation(
                                Integer.parseInt(o.getIdElement().getIdPart()),
                                o.getCode().getText(),
                                p,
                                o.getValueCodeableConcept().getText()
                            );
                            // store in data
                            this.tobacObsMap.put(Integer.toString(p.getId()), output);
                            break;
                            
                        default:
                            output = null;
                            break;
                    }
                    } catch (FHIRException ex) {
                    Logger.getLogger(DataRetrieval.class.getName()).log(Level.SEVERE, null, ex);
                }
                // found, break loop
                if(output != null)
                {
                    break;
                }
            }
        // keep going to next page while output not found
        } while (obsBundle.getLink(Bundle.LINK_NEXT) != null && output == null);
        
        return output;
    }
    
    /**
     * Gets all instance of that Observation by Patient model, sorted by date
     * "55284-4" for "Blood Pressure":
     * "8462-4" for "Diastolic Blood Pressure", "8480-6" for "Systolic Blood Pressure"
     */
    public ArrayList<ArrayList<xd.safeheart.model.Observation>> getAllHistoricObsByPat(xd.safeheart.model.Patient p, String codeStr)
    {
        ArrayList<ArrayList<xd.safeheart.model.Observation>> output = new ArrayList<>();
        output.add(new ArrayList<>());
        output.add(new ArrayList<>());
        Bundle obsBundle = this.client.search()
                    .forResource(org.hl7.fhir.dstu3.model.Observation.class)
                    .where(org.hl7.fhir.dstu3.model.Observation.SUBJECT.hasId(Integer.toString(p.getId())))
                    // search for code
                    .and(new TokenClientParam("code").exactly().code(codeStr))   
                    .sort().ascending(org.hl7.fhir.dstu3.model.Observation.DATE)
                    .returnBundle(Bundle.class)
                    .execute();

        boolean firstPage = true;
        do {
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
                xd.safeheart.model.Observation model;
                try {
                        switch (codeStr)
                        {
                            case "55284-4":
                                // loop through all component
                                for(org.hl7.fhir.dstu3.model.Observation.ObservationComponentComponent c: o.getComponent())
                                {
                                    // if diastolic
                                    if (c.getCode().getCodingFirstRep().getCode().equals("8462-4"))
                                    {
                                        model = new xd.safeheart.model.Observation(
                                            Integer.parseInt(o.getIdElement().getIdPart()),
                                            c.getCode().getText(),
                                            c.getValueQuantity().getUnit(),
                                            p,
                                            c.getValueQuantity().getValue().toString(),
                                            // date to localdate
                                            o.getIssued().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                                        );
                                        // add to first list
                                        output.get(0).add(model);
                                        if (this.bloodDiasObsMap.get(Integer.toString(p.getId())) == null)
                                        {
                                            this.bloodDiasObsMap.put(Integer.toString(p.getId()), new ArrayList<>());
                                        }
                                        // store in data
                                        this.bloodDiasObsMap.get(Integer.toString(p.getId())).add(model);
                                    }
                                    // if systolic
                                    else if (c.getCode().getCodingFirstRep().getCode().equals("8480-6"))
                                    {
                                        model = new xd.safeheart.model.Observation(
                                            Integer.parseInt(o.getIdElement().getIdPart()),
                                            c.getCode().getText(),
                                            c.getValueQuantity().getUnit(),
                                            p,
                                            c.getValueQuantity().getValue().toString(),
                                            // date to localdate
                                            o.getIssued().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                                        );
                                        // add to second list
                                        output.get(1).add(model);
                                        if (this.bloodSysObsMap.get(Integer.toString(p.getId())) == null)
                                        {
                                            this.bloodSysObsMap.put(Integer.toString(p.getId()), new ArrayList<>());
                                        }
                                        // store in data
                                        this.bloodSysObsMap.get(Integer.toString(p.getId())).add(model);
                                    }
                                }
                                break;
                            default:
                                model = null;
                                break;
                        }
                    } catch (FHIRException ex) {
                    Logger.getLogger(DataRetrieval.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        // keep going to next page    
        } while (obsBundle.getLink(Bundle.LINK_NEXT) != null);
        
        return output;
    }
            
    /**
     * Updates all the Observation in the hashmap
     */
    public void reGetObs() {
        this.updateObsMap(choObsMap);
        this.updateObsListMap(bloodDiasObsMap);
        this.updateObsListMap(bloodSysObsMap);
        this.updateObsMap(tobacObsMap);
    }
    
    /**
     * Updates all in observation maps
     * @param map 
     */
    private void updateObsMap(HashMap<String, xd.safeheart.model.Observation> map)
    {
        // update all observation in the hashmap
        for (HashMap.Entry<String, xd.safeheart.model.Observation> entry : map.entrySet()) {
            org.hl7.fhir.dstu3.model.Observation o = this.searchObservationById(Integer.toString(entry.getValue().getID()));
            try {
                map.put(entry.getKey(), new xd.safeheart.model.Observation(
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
    
    /**
     * Updates all observations in maps containing list of observations
     * @param map 
     */
    private void updateObsListMap(HashMap<String, ArrayList<xd.safeheart.model.Observation>> map)
    {
        // update all observation in the hashmap
        for (HashMap.Entry<String, ArrayList<xd.safeheart.model.Observation>> entry : map.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++)
            {
                org.hl7.fhir.dstu3.model.Observation targetObs = this.searchObservationById(Integer.toString(entry.getValue().get(i).getID()));
                try {
                    entry.getValue().set(i, new xd.safeheart.model.Observation(
                            Integer.parseInt(targetObs.getIdElement().getIdPart()),
                            targetObs.getCode().getText(),
                            targetObs.getValueQuantity().getUnit(),
                            entry.getValue().get(i).getPatient(),
                            targetObs.getValueQuantity().getValue().toString()
                    ));
                } catch (FHIRException ex) {
                    Logger.getLogger(DataRetrieval.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public HashMap <String,xd.safeheart.model.Patient> getPatientMap(){
        return this.patientMap;
    }
    
    public HashMap<String, xd.safeheart.model.Observation> getChoObsMap() {
        return choObsMap;
    }

    public HashMap<String, ArrayList<xd.safeheart.model.Observation>> getBloodDiasObsMap() {
        return bloodDiasObsMap;
    }

    public HashMap<String, ArrayList<xd.safeheart.model.Observation>> getBloodSysObsMap() {
        return bloodSysObsMap;
    }

    public HashMap<String, xd.safeheart.model.Observation> getTobacObsMap() {
        return tobacObsMap;
    }
}
