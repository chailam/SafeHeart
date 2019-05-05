/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package xd.safeheart.view;

import xd.safeheart.model.*;
import java.util.ArrayList;

/**
 * User Interface of the program, by using JFrame
 * @author Chai Lam
 * @author Aik Han
 */
public class View extends javax.swing.JFrame {

    ///// My declaration of variable
    private final javax.swing.JList<Patient> selectedPatientJList = new javax.swing.JList<>();
    private final javax.swing.JTable detailJTable = new javax.swing.JTable();
    private final ArrayList <Patient> selectedPatient;
    private final ArrayList <Observation> selectedObs; 
    private final CheckBoxListCellRenderer chkBoxRenderer;
    
    /**
     * Creates new form simpleGUI
     */
    public View() {
        this.selectedObs = new ArrayList<>();
        this.selectedPatient = new ArrayList<>();
        this.chkBoxRenderer = new CheckBoxListCellRenderer();
        initComponents();  
        /////Set the text for clinician name
        ///// Insert the manual code
        //jListInitialize();
        jTableInitialize();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        patientPane = new javax.swing.JScrollPane();
        detailPane = new javax.swing.JScrollPane();
        showButton = new javax.swing.JButton();
        pracNameText = new javax.swing.JLabel();
        pracIdText = new javax.swing.JTextField();
        initButton = new javax.swing.JButton();
        pracLabel = new javax.swing.JLabel();
        displayText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FIT3077  Assignment 2");

        detailPane.setAutoscrolls(true);

        showButton.setText("Show");
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButtonActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(patientPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(detailPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(showButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(showButton)
                    .addComponent(patientPane, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(detailPane, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(detailPane, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(patientPane)
                        .addGap(18, 18, 18)
                        .addComponent(showButton)))
                .addContainerGap())
        );

        pracNameText.setText("Practitioner:");

        initButton.setText("Initialise");

        pracLabel.setText("Practitioner ID:");

        displayText.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(pracNameText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(displayText, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(183, 183, 183)
                .addComponent(pracLabel)
                .addGap(18, 18, 18)
                .addComponent(pracIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(initButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(displayText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pracNameText)
                        .addComponent(pracIdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(initButton)
                        .addComponent(pracLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ///// Action for the button "start"
    private void showButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showButtonActionPerformed
//        getSelected();
//        jTableInitialize();
    }//GEN-LAST:event_showButtonActionPerformed
    
//    private void getSelected(){
//        List<Patient> patientList = selectedPatientJList.getSelectedValuesList();
//        System.out.println("clicked");
//        for (Patient p : patientList){
//            int pID = p.getId();
//            System.out.println(pID);
//        }
//        
//        System.out.println(this.choObsMap.size());
//        for (HashMap.Entry<String, Observation> entry : choObsMap.entrySet()) {
//             System.out.println(entry.getValue());
//        }
//
//        ///// Clear all the data in list
//        selectedPatient.clear();
//        selectedObs.clear();
//
//        for (Patient p : patientList){
//            int pID = p.getId();
//             for (HashMap.Entry<String, Observation> entry : choObsMap.entrySet()) {
//                    if(entry.getValue().getPatient().getId() == pID){
//                        System.out.println(entry.getValue());
//                        System.out.println(pID);
//                        selectedPatient.add(p);
//                        selectedObs.add(entry.getValue());
//                    }
//                }
//        } 
//    }
    
    
//    public void updateView(ArrayList <Observation> o){
//        this.selectedObs = o;
//        jTableInitialize();
//    }
    
    
    // Reset the Model for list to show the value
//    private void jListInitialize(){
//        patientPane.setViewportView(selectedPatientJList);
//        DefaultListModel<Patient> listModel = new DefaultListModel<>();
//        for (HashMap.Entry<String, Patient> entry : patientMap.entrySet()) {
//            listModel.addElement(entry.getValue());
//        }
//
//        selectedPatientJList.setModel(listModel);   
//        ///// Make the list become Checkbox
//        selectedPatientJList.setCellRenderer(new CheckBoxListCellRenderer());
//    }
    
    ///// Reset the Model for table to show the value
    private void jTableInitialize(){
        detailPane.setViewportView(detailJTable);
        TableModel tableModel = new TableModel(selectedPatient, selectedObs);
        detailJTable.setModel(tableModel);
    }
    
    // Clears selected patients and observations
    public void clearSelected()
    {
        this.selectedPatient.clear();
        this.selectedObs.clear();
    }
    
    // getters of UI element
    
    public CheckBoxListCellRenderer getChkBoxRenderer()
    {
        return this.chkBoxRenderer;
    }

    public javax.swing.JTextField getPracIdText()
    {
        return this.pracIdText;
    }
    
    public javax.swing.JLabel getPracNameText()
    {
        return this.pracNameText;
    }
    
    public javax.swing.JLabel getDisplayText()
    {
        return this.displayText;
    }
    
    public javax.swing.JScrollPane getPatientPane()
    {
        return this.patientPane;
    }
    
    public javax.swing.JScrollPane getDetailPane()
    {
        return this.detailPane;
    }
    
    public javax.swing.JTable getDetailJTable()
    {
        return this.detailJTable;
    }
    
    public javax.swing.JButton getShowButton()
    {
        return this.showButton;
    }
    
    public javax.swing.JButton getInitButton()
    {
        return this.initButton;
    }
    
    public javax.swing.JList<Patient> getSelectedPatientJList()
    {
        return this.selectedPatientJList;
    }
    
    public ArrayList<Patient> getSelectedPatient()
    {
        return this.selectedPatient;
    }
    
    public ArrayList<Observation> getSelectedObs()
    {
        return this.selectedObs;
    }
    
    /**
     * @param args the command line arguments
     */
    public void start() {
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
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new simpleGUI().setVisible(true);
//            }
//        });    
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new simpleGUI().setVisible(true);
//            }
//        });    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane detailPane;
    private javax.swing.JLabel displayText;
    private javax.swing.JButton initButton;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane patientPane;
    private javax.swing.JTextField pracIdText;
    private javax.swing.JLabel pracLabel;
    private javax.swing.JLabel pracNameText;
    private javax.swing.JButton showButton;
    // End of variables declaration//GEN-END:variables
   
}
