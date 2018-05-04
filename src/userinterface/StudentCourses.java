/*
 *  Student Number : 17260
 *  Student Name : Mirza Usman
 */
package userinterface;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "student_courses", catalog = "vgc_17260", schema = "")
@NamedQueries({
    @NamedQuery(name = "StudentCourses.findAll", query = "SELECT s FROM StudentCourses s")
    , @NamedQuery(name = "StudentCourses.findById", query = "SELECT s FROM StudentCourses s WHERE s.id = :id")
    , @NamedQuery(name = "StudentCourses.findByStudentID", query = "SELECT s FROM StudentCourses s WHERE s.studentID = :studentID")
    , @NamedQuery(name = "StudentCourses.findByCourseCode", query = "SELECT s FROM StudentCourses s WHERE s.courseCode = :courseCode")
    , @NamedQuery(name = "StudentCourses.findByFees", query = "SELECT s FROM StudentCourses s WHERE s.fees = :fees")})
public class StudentCourses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "studentID")
    private int studentID;
    @Basic(optional = false)
    @Column(name = "courseCode")
    private String courseCode;
    @Basic(optional = false)
    @Column(name = "fees")
    private double fees;

    public StudentCourses() {
    }

    public StudentCourses(Integer id) {
        this.id = id;
    }

    public StudentCourses(Integer id, int studentID, String courseCode, double fees) {
        this.id = id;
        this.studentID = studentID;
        this.courseCode = courseCode;
        this.fees = fees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentCourses)) {
            return false;
        }
        StudentCourses other = (StudentCourses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mizarusman.userinterface.StudentCourses[ id=" + id + " ]";
    }
    
}
