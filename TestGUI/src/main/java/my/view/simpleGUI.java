/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.view;

import my.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.table.TableColumn;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;

public class simpleGUI extends javax.swing.JFrame {

    ///// My declaration of variable
    private javax.swing.JList<Person> jList1 = new javax.swing.JList<>();
    private javax.swing.JTable jTable1 = new javax.swing.JTable();
    private javax.swing.JTable jTable2 = new javax.swing.JTable();
    private ArrayList <Person> lUser;
    private ArrayList <Ob> lOb;
    private ArrayList <Ob> tobacOb;
    private ArrayList <ArrayList<Ob>> diasBlood;
    private ArrayList <ArrayList<Ob>> sysBlood;
    private ArrayList <Ob> selectedObs = new ArrayList<>(); /// The selected person and its data in checkbox
    private ArrayList <ArrayList<Ob>> selectedDiasBlood = new ArrayList<>(); /// The selected person and its data in checkbox
    private ArrayList <ArrayList<Ob>> selectedSysBlood = new ArrayList<>(); /// The selected person and its data in checkbox
    private ArrayList <Ob> selectedTobacOb = new ArrayList<>(); /// The selected person and its data in checkbox
    private String sysAlert = "";
    private String diasAlert = "";
    private LineChart chart;
    private HashMap <String, LineChart> chartMap = new HashMap<>();
    
    /**
     * Creates new form simpleGUI
     */
    public simpleGUI(ArrayList<Person> p, ArrayList<Ob> o, ArrayList <ArrayList<Ob>> diasBlood,ArrayList <ArrayList<Ob>> sysBlood,ArrayList <Ob> tobacOb) {
        this.lOb = o;
        this.lUser = p;
        this.diasBlood = diasBlood;
        this.sysBlood = sysBlood;
        this.tobacOb = tobacOb;
        initComponents();  
        /////Set the text for clinician name
        jLabel2.setText("Clinician name");
        ///// Insert the manual code
        jListInitialize();
        jGraphInitialize();
        start();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FIT3077  Assignment 2");

        jScrollPane2.setAutoscrolls(true);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Alert for Selected Patient (systolic > 180 ): ");

        jLabel5.setText("Alert for Selected Patient (diastolic > 120 ): ");

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(40, 40, 40)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                        .addComponent(jScrollPane2))
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)))
                .addGap(26, 26, 26))
        );

        jLabel1.setText("Hi !");

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ///// Action for the button "start"
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        getSelected();
        jTableInitialize();
        jTable2Initialize();
        jGraphInitialize();
        bloodPressureAlert();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void getSelected(){
        int[] indices = jList1.getSelectedIndices();
        List<Person> personList = jList1.getSelectedValuesList();
        ///// Clear all the data in list
        selectedObs.clear();
        selectedDiasBlood.clear();
        selectedSysBlood.clear();
        selectedTobacOb.clear();
        if (chartMap.size() > 0){
        for (HashMap.Entry<String, LineChart> entry : chartMap.entrySet()){
            entry.getValue().setVisible(false);
            entry.getValue().dispose();
        }
        }
        
        
        System.out.println("Selected; ");

        for (Person p : personList){
            int pID = p.getId();
            for (Ob o : lOb){
                if(o.getPatient().getId() == pID){
                    selectedObs.add(o);
                    System.out.println("Cho: "+ o.getValue() + " id: " + p.getId());
                }     
            }
            
            for (Ob o : tobacOb){
                if(o.getPatient().getId() == pID){
                    selectedTobacOb.add(o);
                    System.out.println("Tobac: "+ o.getValue() + " id: " + p.getId());
                }     
            }

            for (ArrayList<Ob> o : diasBlood){
                if (o.size() > 0) {
                    if(o.get(0).getPatient().getId() == pID){
                        selectedDiasBlood.add(o);
                        System.out.println("Dias: "+o.get(0).getPatient().getId());
                    }    
                }
            }
            
             for (ArrayList<Ob> o : sysBlood){
                 if (o.size() > 0) {
                    if(o.get(0).getPatient().getId() == pID){
                        selectedSysBlood.add(o);
                        System.out.println("Sys: "+o.get(0).getPatient().getId());
                    }     
                 }
            }
        } 
    }
    
    
    public void updateView(ArrayList<Ob> o){
        this.lOb = o;
        System.out.println("View: "+ this.lOb);
        getSelected();
        jTableInitialize();
    }
    
    
    ///// Reset the Model for list to show the value
    private void jListInitialize(){
        jScrollPane1.setViewportView(jList1);
        DefaultListModel<Person> listModel = new DefaultListModel<>();
        for (Person p : lUser) {
            listModel.addElement(p);
	}
        jList1.setModel(listModel);   
        ///// Make the list become Checkbox
        jList1.setCellRenderer(new CheckBoxListCellRenderer());
    }
    
    ///// Reset the Model for table to show the value
    private void jTableInitialize(){
        jScrollPane2.setViewportView(jTable1);
        //System.out.println(jTable1.getRowCount());
        TableModel1 tableModel1 = new TableModel1(selectedObs);
        jTable1.setModel(tableModel1);
    }

    private void jTable2Initialize(){
        jScrollPane3.setViewportView(jTable2);
        TableModel2 tableModel2 = new TableModel2(selectedTobacOb);
        jTable2.setModel(tableModel2);

    }
    
    private void jGraphInitialize(){
        
        for (int i = 0; i < this.selectedDiasBlood.size(); i++){
            ArrayList<Ob> sysBL = selectedSysBlood.get(i);
            ArrayList<Ob> diaBL = selectedDiasBlood.get(i);
            Person p = sysBL.get(0).getPatient();
            chart = new LineChart(p.getId()+p.getFamilyName()+p.getGivenName(),diaBL,sysBL);
            chart.setSize(800, 400);
            chart.setVisible(true);
            chartMap.put(Integer.toString(p.getId()),chart);
        }
    }
    
    private void bloodPressureAlert(){
        sysAlert = "";
        diasAlert = "";
        for (int i = 0; i < this.selectedDiasBlood.size(); i++){
            ArrayList<Ob> sysBL = selectedSysBlood.get(i);
            ArrayList<Ob> diaBL = selectedDiasBlood.get(i);
            for (int j = 0; j < sysBL.size();j++){
                if (Integer.parseInt(sysBL.get(j).getValue()) > 180 ){
                    sysAlert = sysAlert + Integer.toString(sysBL.get(j).getPatient().getId()) + sysBL.get(j).getPatient().getFamilyName() + sysBL.get(j).getPatient().getGivenName() + ", ";
                    jLabel4.setText(sysAlert);
                 }
                if( Integer.parseInt(diaBL.get(j).getValue()) > 120){
                    diasAlert = diasAlert + Integer.toString(sysBL.get(j).getPatient().getId()) + sysBL.get(j).getPatient().getFamilyName() + sysBL.get(j).getPatient().getGivenName() + ", ";
                    jLabel6.setText(diasAlert);
                }
                break;
                
            }
        }
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
            java.util.logging.Logger.getLogger(simpleGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(simpleGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(simpleGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(simpleGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new simpleGUI().setVisible(true);
//            }
//        });    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
   
}
