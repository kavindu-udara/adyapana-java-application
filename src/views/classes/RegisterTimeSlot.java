/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views.classes;

import includes.MysqlConnection;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

/**
 *
 * @author kavindu
 */
public class RegisterTimeSlot extends javax.swing.JDialog {

    private Runnable successAction;

    /**
     * Creates new form RegisterTimeSlot
     */
    public RegisterTimeSlot(java.awt.Frame parent, boolean modal, Runnable successAction) {
        super(parent, modal);
        initComponents();
        this.successAction = successAction;
        timePicker1.setEditor(startAtField);
        timePicker2.setEditor(finishAtField);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timePicker1 = new raven.datetime.component.time.TimePicker();
        timePicker2 = new raven.datetime.component.time.TimePicker();
        jLabel1 = new javax.swing.JLabel();
        startAtField = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        finishAtField = new javax.swing.JFormattedTextField();
        createButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("create timeslot");
        setResizable(false);

        jLabel1.setText("Start at");

        jLabel2.setText("Finish at");

        createButton.setBackground(new java.awt.Color(255, 204, 204));
        createButton.setText("create");
        createButton.setOpaque(false);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startAtField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(finishAtField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(startAtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(finishAtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(createButton)
                .addGap(43, 43, 43))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        // TODO add your handling code here:
        if (timePicker1.isTimeSelected() && timePicker2.isTimeSelected()) {
            String startTimeString = timePicker1.getSelectedTimeAsString();
            String endTimeString = timePicker2.getSelectedTimeAsString();

            DateTimeFormatter time24Hours = DateTimeFormatter.ofPattern("hh:mm a");

            LocalTime startTimeFormatted = LocalTime.parse(startTimeString, time24Hours);
            LocalTime endTimeFormatted = LocalTime.parse(endTimeString, time24Hours);

            int startTimeHour = Integer.parseInt(String.valueOf(startTimeFormatted).split(":")[0]);
            int endTimeHour = Integer.parseInt(String.valueOf(endTimeFormatted).split(":")[0]);

            if (startTimeHour < endTimeHour) {
                try {
                    ResultSet checkResultSet = MysqlConnection.executeSearch("SELECT * FROM `timeslots` WHERE `started_at`='" + String.valueOf(startTimeFormatted) + "' AND `end_at`='" + String.valueOf(endTimeFormatted) + "' ");
                    if (checkResultSet.next()) {
                        JOptionPane.showMessageDialog(this, "timeslot already registered");
                    } else {
                        MysqlConnection.executeIUD("INSERT INTO `timeslots`(`started_at`, `end_at`) VALUES ('" + String.valueOf(startTimeFormatted) + "', '" + String.valueOf(endTimeFormatted) + "') ");
                        JOptionPane.showMessageDialog(this, "Created");
                        successAction.run();
                        dispose();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "please enter a right time range");
            }

        }
    }//GEN-LAST:event_createButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createButton;
    private javax.swing.JFormattedTextField finishAtField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JFormattedTextField startAtField;
    private raven.datetime.component.time.TimePicker timePicker1;
    private raven.datetime.component.time.TimePicker timePicker2;
    // End of variables declaration//GEN-END:variables
}
