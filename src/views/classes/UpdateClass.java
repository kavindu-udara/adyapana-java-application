/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views.classes;

import includes.MysqlConnection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author kavindu
 */
public class UpdateClass extends javax.swing.JDialog {

    private Runnable reloadTableAction;
    private int classId;
    private HashMap<String, String> subjectHashMap = new HashMap<>();
    private HashMap<String, String> teacherHashMap = new HashMap<>();
    private HashMap<String, String> timeslotsHashMap = new HashMap<>();

    /**
     * Creates new form UpdateClass
     */
    public UpdateClass(java.awt.Frame parent, boolean modal, int classId, Runnable reloadTableAction) {
        super(parent, modal);
        initComponents();
        this.reloadTableAction = reloadTableAction;
        this.classId = classId;
        loadSubjectComboBoxData();
        loadTimeSlotsComboBoxData();
        fillFields();
    }

    private void loadSubjectComboBoxData() {
        try {
            ResultSet resultSet = MysqlConnection.executeSearch("SELECT * FROM `subjects`");
            Vector vector = new Vector();
            vector.add("Select");
            while (resultSet.next()) {
                subjectHashMap.put(resultSet.getString("name"), resultSet.getString("id"));
                vector.add(resultSet.getString("name"));
            }
            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(vector);
            subjectsComboBox.setModel(comboBoxModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTimeSlotsComboBoxData() {
        try {
            ResultSet resultSet = MysqlConnection.executeSearch("SELECT * FROM `timeslots`");
            Vector vector = new Vector();
            vector.add("Select");
            while (resultSet.next()) {
                timeslotsHashMap.put(resultSet.getString("started_at") + "-" + resultSet.getString("end_at"), resultSet.getString("id"));
                vector.add(resultSet.getString("started_at") + "-" + resultSet.getString("end_at"));
            }
            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(vector);
            timeSlotsComboBox.setModel(comboBoxModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTeacherComboBoxData(int subjectId) {
        try {
            ResultSet resultSet = MysqlConnection.executeSearch("SELECT * FROM `teacher_has_subject` WHERE `subject_id`='" + subjectId + "' ");
            Vector vector = new Vector();
            vector.add("Select");
            while (resultSet.next()) {

                ResultSet techerResultSet = MysqlConnection.executeSearch("SELECT * FROM `teachers` WHERE `id`='" + resultSet.getString("teacher_id") + "' ");
                if (techerResultSet.next()) {
                    teacherHashMap.put(techerResultSet.getString("name"), techerResultSet.getString("id"));
                    vector.add(techerResultSet.getString("name"));
                }
            }
            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(vector);
            teachersComboBox.setModel(comboBoxModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetFields() {
        teachersComboBox.setSelectedItem("Select");
        subjectsComboBox.setSelectedItem("Select");
        timeSlotsComboBox.setSelectedItem("Select");
    }

    private void fillFields() {
        try {
            ResultSet classResultSet = MysqlConnection.executeSearch("SELECT * FROM `classes` WHERE `id`='" + classId + "' ");
            if (classResultSet.next()) {
                ResultSet subjectResultSet = MysqlConnection.executeSearch("SELECT * FROM `subjects` WHERE `id`='" + classResultSet.getString("subject_id") + "' ");
                if (subjectResultSet.next()) {
                    subjectsComboBox.setSelectedItem(subjectResultSet.getString("name"));
                    loadTeacherComboBoxData(subjectResultSet.getInt("id"));
                }

                ResultSet teacherResultSet = MysqlConnection.executeSearch("SELECT * FROM `teachers` WHERE `id`='" + classResultSet.getString("teacher_id") + "' ");
                if (teacherResultSet.next()) {
                    teachersComboBox.setSelectedItem(teacherResultSet.getString("name"));
                }

                ResultSet timeslotsResultSet = MysqlConnection.executeSearch("SELECT * FROM `timeslots` WHERE `id`='" + classResultSet.getString("timeslots_id") + "' ");
                if (timeslotsResultSet.next()) {
                    timeSlotsComboBox.setSelectedItem(timeslotsResultSet.getString("started_at") + "-" + timeslotsResultSet.getString("end_at"));
                }
                nameField.setText(classResultSet.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        resetButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        subjectsComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        teachersComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();
        timeSlotsComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Class");
        setResizable(false);

        resetButton.setBackground(new java.awt.Color(255, 204, 204));
        resetButton.setText("Reset");
        resetButton.setOpaque(false);
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Select Subject");

        subjectsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        subjectsComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                subjectsComboBoxItemStateChanged(evt);
            }
        });

        jLabel2.setText("Select Teacher");

        jLabel3.setText("Time Slot");

        updateButton.setBackground(new java.awt.Color(255, 204, 204));
        updateButton.setText("Update");
        updateButton.setOpaque(false);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        timeSlotsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(37, 37, 37))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(80, 80, 80)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(subjectsComboBox, 0, 137, Short.MAX_VALUE)
                            .addComponent(teachersComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(timeSlotsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameField))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(subjectsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(teachersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(timeSlotsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(updateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetButton)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        resetFields();
    }//GEN-LAST:event_resetButtonActionPerformed

    private boolean checkIsNameAlreadyExists(String name) {
        try {
            ResultSet resultSet = MysqlConnection.executeSearch("SELECT * FROM `classes` WHERE `name`='" + name + "' ");
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        String subjectString = String.valueOf(subjectsComboBox.getSelectedItem());
        String teacherString = String.valueOf(teachersComboBox.getSelectedItem());
        String timeSlotString = String.valueOf(timeSlotsComboBox.getSelectedItem());
        String classNameString = nameField.getText();

        if (subjectString.equals("Select") || teacherString.equals("Select") || timeSlotString.equals("Select") || classNameString.equals("")) {
            JOptionPane.showMessageDialog(this, "All fields are required");
            return;
        }

        try {
            String subId = subjectHashMap.get(subjectString);
            String teacherId = teacherHashMap.get(teacherString);
            String timeslotId = timeslotsHashMap.get(timeSlotString);

            ResultSet checkResultSet = MysqlConnection.executeSearch("SELECT * FROM `classes` WHERE `subject_id`='" + subId + "' AND `teacher_id`='" + teacherId + "' AND `timeslots_id`='" + timeslotId + "' ");
            if (!checkResultSet.next()) {
                if (checkIsNameAlreadyExists(classNameString)) {
                    JOptionPane.showMessageDialog(this, "Class name is already exists !");
                    return;
                }
                MysqlConnection.executeIUD("UPDATE `classes` SET `timeslots_id`='" + timeslotId + "', `subject_id`='" + subId + "', `teacher_id`='" + teacherId + "' WHERE `id`='" + classId + "', `name`='" + classNameString + "' ");
                JOptionPane.showMessageDialog(this, "Update suceess");
                reloadTableAction.run();
                resetFields();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Already Exists !");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Update failed : " + ex.getMessage());
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void subjectsComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_subjectsComboBoxItemStateChanged
        // TODO add your handling code here:

        String subjectIdString = String.valueOf(subjectsComboBox.getSelectedItem());
        if (!subjectIdString.equals("Select")) {
            int subjectId = Integer.parseInt(subjectHashMap.get(subjectIdString));
            loadTeacherComboBoxData(subjectId);
        }
    }//GEN-LAST:event_subjectsComboBoxItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton resetButton;
    private javax.swing.JComboBox<String> subjectsComboBox;
    private javax.swing.JComboBox<String> teachersComboBox;
    private javax.swing.JComboBox<String> timeSlotsComboBox;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
