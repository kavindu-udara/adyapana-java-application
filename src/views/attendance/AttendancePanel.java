/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.attendance;

import includes.MysqlConnection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kavindu
 */
public class AttendancePanel extends javax.swing.JPanel {

    private AttendancePanel attendancePanel = this;

    private HashMap<String, String> classesHashMap = new HashMap<>();

    /**
     * Creates new form AttendancePanel
     */
    public AttendancePanel() {
        initComponents();
        loadClassesComboBoxData();
        setDateDetails();
    }

    public static String getTodayDate() {
        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        return dateformat.format(date);
    }

    private void setDateDetails() {
        String todayDate = getTodayDate();
        dateLabel.setText(todayDate);
    }

    private void loadClassesComboBoxData() {
        try {
            ResultSet resultSet = MysqlConnection.executeSearch("SELECT * FROM `classes` ");
            Vector vector = new Vector();
            vector.add("Select");
            while (resultSet.next()) {
                classesHashMap.put(resultSet.getString("name"), resultSet.getString("id"));
                vector.add(resultSet.getString("name"));
            }
            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(vector);
            classesComboBox.setModel(comboBoxModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerDate() {
        try {
            String todayDate = getTodayDate();
            ResultSet checkDateResultSet = MysqlConnection.executeSearch("SELECT * FROM `dates` WHERE `date`='" + todayDate + "' ");
            if (!checkDateResultSet.next()) {
                MysqlConnection.executeIUD("INSERT INTO `dates`(`date`) VALUES ('" + todayDate + "') ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeUser(int studentId) {
        String classString = String.valueOf(classesComboBox.getSelectedItem());
        if (!classString.equals("Select")) {
            registerDate();
            try {
                String todayDate = getTodayDate();
                ResultSet dateResultSet = MysqlConnection.executeSearch("SELECT * FROM `dates` WHERE `date`='" + todayDate + "' ");
                if (dateResultSet.next()) {
                    int classId = Integer.parseInt(classesHashMap.get(classString));
                    ResultSet checResultSet = MysqlConnection.executeSearch("SELECT * FROM `attendance` WHERE `class_id`='" + classId + "' AND `student_id`='" + studentId + "' AND `dates_id`='" + dateResultSet.getString("id") + "' ");

                    if (!checResultSet.next()) {
                        MysqlConnection.executeIUD("INSERT INTO `attendance`(`class_id`, `student_id`, `dates_id`) VALUES ('" + classId + "', '" + studentId + "', '" + dateResultSet.getString("id") + "') ");
                        JOptionPane.showMessageDialog(this, "created");
                        loadTableData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Already Exists !");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadTableData() {
        String classString = String.valueOf(classesComboBox.getSelectedItem());
        DefaultTableModel tableModel = (DefaultTableModel) attendaceTable.getModel();
        tableModel.setRowCount(0);

        if (!classString.equals("Select")) {
            try {
                String classId = classesHashMap.get(classString);
                ResultSet resultSet = MysqlConnection.executeSearch("SELECT * FROM `attendance` INNER JOIN `classes` ON `attendance`.`class_id`=`classes`.`id` INNER JOIN `students` ON `attendance`.`student_id`=`students`.`id` INNER JOIN `dates` ON `attendance`.`dates_id`=`dates`.`id` WHERE `attendance`.`class_id`='" + classId + "';");
                while (resultSet.next()) {
                    Vector vector = new Vector();
                    vector.add(resultSet.getString("id"));
                    vector.add(resultSet.getString("classes.name"));
                    vector.add(resultSet.getString("students.name"));

                    tableModel.addRow(vector);
                }
                attendaceTable.setModel(tableModel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        attendaceTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        classesComboBox = new javax.swing.JComboBox<>();
        dateLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        attendaceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "class", "student"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(attendaceTable);

        jLabel1.setText("Select class");

        classesComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                classesComboBoxItemStateChanged(evt);
            }
        });

        dateLabel.setText("date");

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setText("add student");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Today : ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(classesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(43, 43, 43)
                        .addComponent(dateLabel)
                        .addGap(72, 72, 72)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(classesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateLabel)
                    .addComponent(jLabel2)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void classesComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_classesComboBoxItemStateChanged
        // TODO add your handling code here:

        loadTableData();
    }//GEN-LAST:event_classesComboBoxItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        String classString = String.valueOf(classesComboBox.getSelectedItem());

        if (!classString.equals("Select")) {
            int classId = Integer.parseInt(classesHashMap.get(classString));
            try {
                ResultSet classResultSet = MysqlConnection.executeSearch("SELECT * FROM `classes` WHERE `id`='" + classId + "' ");
                if (classResultSet.next()) {
                    new StudentSelector(null, true, classResultSet.getInt("subject_id"), attendancePanel).setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a class first !");
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable attendaceTable;
    private javax.swing.JComboBox<String> classesComboBox;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
