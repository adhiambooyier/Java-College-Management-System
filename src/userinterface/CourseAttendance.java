/*
 *  Student Number : 17260
 *  Student Name : Mirza Usman
 */
package userinterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import utilities.DbConnection;
import utilities.Utils;

/**
 *
 * @author user
 */
public class CourseAttendance extends javax.swing.JFrame {

    String userId;
    Connection conn = DbConnection.getConnection();
    boolean isCellChange = true;

    /**
     * Creates new form CourseAttendance
     */
    public CourseAttendance(String userId) {
        initComponents();
        this.userId = userId;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT `course1`, `course2`, `course3` FROM `faculty` WHERE `userID` = ?");
            preparedStatement.setString(1, this.userId);
            ResultSet rs = preparedStatement.executeQuery();

            Vector<String> courses = new Vector<String>();
            if (rs.next()) {
                courses.add(rs.getString("course1"));
                courses.add(rs.getString("course2"));
                courses.add(rs.getString("course3"));
                isCellChange = false;
                buildTable(rs.getString("course1"));
                isCellChange = true;
            }
            cbCourses.setModel(new javax.swing.DefaultComboBoxModel<>(courses));
            cbCourses.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    isCellChange = false;
                    buildTable(cbCourses.getSelectedItem().toString());
                    isCellChange = true;
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(CourseAttendance.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void buildTable(String courseCode) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT student_courses.studentID, students.fname, students.lname, student_courses.attendanceRecord FROM student_courses INNER JOIN students ON students.userID = student_courses.studentID WHERE student_courses.courseCode = ? ");
            preparedStatement.setString(1, courseCode);
            ResultSet rs = preparedStatement.executeQuery();
            // creates and displays the table
            DefaultTableModel dtm = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int column) {
                    switch (column) {
                        case 3:
                            return Integer.class;

                        case 4:
                            return Boolean.class;

                        default:
                            return String.class;
                    }
                }
            };

            dtm.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent tme) {
                    if (isCellChange) {
                        if (tme.getColumn() == 4) {
                            DefaultTableModel dtmI = (DefaultTableModel) tme.getSource();
                            Boolean inClass = (Boolean) dtmI.getValueAt(tme.getLastRow(), 4);
                            String studentID = (String) dtmI.getValueAt(tme.getFirstRow(), 0);
                            Integer attendanceRecord = Integer.parseInt((String) dtmI.getValueAt(tme.getFirstRow(), 3));

                            if (inClass) {
                                try {
                                    PreparedStatement preparedStat = conn.prepareStatement("UPDATE `student_courses` SET `attendanceRecord`=`attendanceRecord`+1 WHERE `studentID`=? AND `courseCode`=?");
                                    preparedStat.setString(1, studentID);
                                    preparedStat.setString(2, courseCode);
                                    preparedStat.executeUpdate();
                                    dtmI.setValueAt(String.valueOf(attendanceRecord + 1), tme.getFirstRow(), 3);
                                } catch (SQLException ex) {
                                    Logger.getLogger(CourseAttendance.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                try {
                                    PreparedStatement preparedStat = conn.prepareStatement("UPDATE `student_courses` SET `attendanceRecord`=`attendanceRecord`-1 WHERE `studentID`=? AND `courseCode`=?");
                                    preparedStat.setString(1, studentID);
                                    preparedStat.setString(2, courseCode);
                                    preparedStat.executeUpdate();
                                    dtmI.setValueAt(String.valueOf(attendanceRecord - 1), tme.getFirstRow(), 3);
                                } catch (SQLException ex) {
                                    Logger.getLogger(CourseAttendance.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            });

            dtm.addColumn("Student ID");
            dtm.addColumn("First Name");
            dtm.addColumn("Surname");
            dtm.addColumn("Attendance Record");
            dtm.addColumn("In Class");

            int rowIndex = 0;
            while (rs.next()) {
                dtm.addRow(new Object[0]);
                dtm.setValueAt(rs.getString("studentID"), rowIndex, 0);
                dtm.setValueAt(rs.getString("fname"), rowIndex, 1);
                dtm.setValueAt(rs.getString("lname"), rowIndex, 2);
                dtm.setValueAt(rs.getString("attendanceRecord"), rowIndex, 3);
                dtm.setValueAt(false, rowIndex, 4);
                rowIndex++;
            }
            tblAttendance.setModel(dtm);
        } catch (SQLException ex) {
            Logger.getLogger(CourseAttendance.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        cbCourses = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAttendance = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("COURSE ATTENDANCE FOR");

        cbCourses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblAttendance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblAttendance);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbCourses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCourses, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(CourseAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CourseAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CourseAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CourseAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbCourses;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAttendance;
    // End of variables declaration//GEN-END:variables
}
