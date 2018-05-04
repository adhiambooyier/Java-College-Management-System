/*
 *  Student Number : 17260
 *  Student Name : Mirza Usman
 */
package userinterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import utilities.DbConnection;
import utilities.Utils;

/**
 *
 * @author user
 */
public class ViewStudents extends javax.swing.JFrame {

    String courseCode;
    Connection conn = DbConnection.getConnection();

    /**
     * Creates new form ViewStudents
     */
    public ViewStudents(String courseCode) {
        initComponents();
        this.courseCode = courseCode;

        try {
            lblCourse.setText(courseCode);
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT `task_title` FROM `academic_calendar` WHERE `code`=?");
            preparedStatement.setString(1, this.courseCode);
            ResultSet rs = preparedStatement.executeQuery();
            Vector<String> columns = new Vector<String>();
            columns.add("studentID");
            while (rs.next()) {
                columns.add(rs.getString("task_title"));
            }

            Vector<Vector<String>> rows = new Vector<>();
            preparedStatement = conn.prepareStatement("SELECT `studentID` FROM `student_courses` WHERE `courseCode`= ?");
            preparedStatement.setString(1, this.courseCode);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                String studentID = rs.getString("studentID");
                row.add(studentID);

                for (int i = 0; i < columns.size() - 1; i++) {
                    preparedStatement = conn.prepareStatement("SELECT `score` FROM `student_grades` WHERE `courseCode` = ? AND `studentID` = ? AND `taskTitle` = ?");
                    preparedStatement.setString(1, this.courseCode);
                    preparedStatement.setString(2, studentID);
                    preparedStatement.setString(3, columns.get(i + 1));
                    ResultSet rsScore = preparedStatement.executeQuery();
                    if (rsScore.next()) {
                        row.add(rsScore.getString("score"));
                    }
                }
                rows.add(row);
            }
            DefaultTableModel tableModel = new DefaultTableModel(rows, columns);
            tableModel.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent tme) {
                    if (tme.getColumn() != 0) {

                        DefaultTableModel dtm = (DefaultTableModel) tme.getSource();
                        String value = (String) dtm.getValueAt(tme.getFirstRow(), tme.getColumn());
                        String studentID = (String) dtm.getValueAt(tme.getFirstRow(), 0);
                        String taskTitle = columns.get(tme.getColumn());
                        try {
                            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `student_grades` WHERE `studentID` = ? AND `taskTitle` = ?");
                            preparedStatement.setInt(1, Integer.parseInt(studentID));
                            preparedStatement.setString(2, taskTitle);
                            ResultSet rs = preparedStatement.executeQuery();
                            rs.last();
                            int rowCount = rs.getRow();
                            if (rowCount < 1) {
                                preparedStatement = conn.prepareStatement("INSERT INTO `student_grades` (`courseCode`, `studentID`, `taskTitle`, `score`) VALUES (?,?,?,?)");
                                preparedStatement.setString(1, courseCode);
                                preparedStatement.setInt(2, Integer.parseInt(studentID));
                                preparedStatement.setString(3, taskTitle);
                                preparedStatement.setInt(4, Integer.parseInt(value));
                                preparedStatement.executeUpdate();
                            } else {
                                preparedStatement = conn.prepareStatement("UPDATE `student_grades` SET `score`= ? WHERE `studentID` = ? AND `taskTitle` = ?");
                                preparedStatement.setString(1, value);
                                preparedStatement.setInt(2, Integer.parseInt(studentID));
                                preparedStatement.setString(3, taskTitle);
                                preparedStatement.executeUpdate();
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(ViewStudents.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            tblStudents.setModel(tableModel);
        } catch (SQLException ex) {
            Logger.getLogger(ViewStudents.class.getName()).log(Level.SEVERE, null, ex);

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
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
        lblMessage = new javax.swing.JLabel();
        lblCourse = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("MANAGE GRADES FOR");

        jLabel2.setText("Search");

        tblStudents.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblStudents);

        jLabel3.setText("Click cell to make or update an entry");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(77, 77, 77)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(43, 43, 43)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(lblCourse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(ViewStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCourse;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
