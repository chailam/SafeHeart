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
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        // context - create this once, as it's an expensive operation
        // see http://hapifhir.io/doc_intro.html
        FhirContext ctx = FhirContext.forDstu3();
        String serverBaseUrl = "http://hapi-fhir.erc.monash.edu:8080/baseDstu3/";
        
        // increase timeouts since the server might be powered down
        // see http://hapifhir.io/doc_rest_client_http_config.html
        ctx.getRestfulClientFactory().setConnectTimeout(60 * 1000);
        ctx.getRestfulClientFactory().setSocketTimeout(60 * 1000);
        
        // create the RESTful client to work with our FHIR server
        // see http://hapifhir.io/doc_rest_client.html
        IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

        System.out.println("Press Enter to send to server: " + serverBaseUrl);
        System.in.read();

        try {
            // perform a search for Patients with last name 'Fhirman'
            // see http://hapifhir.io/doc_rest_client.html#SearchQuery_-_Type
            Bundle response = client.search()
                    .forResource(Patient.class)
                    .where(Patient.FAMILY.matches().values("Lebrón484"))
                    .returnBundle(Bundle.class)
                    .execute();

            System.out.println("Found " + response.getTotal()
                    + " Lebrón484 patients. Their logical IDs are:");
            response.getEntry().forEach((entry) -> {
                // within each entry is a resource - print its logical ID
                System.out.println(entry.getResource().getIdElement().getIdPart());
            });
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
        }

        System.out.println("Press Enter to end.");
        System.in.read();
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Monitor().setVisible(true);
//            }
//        });
    }
    
}
