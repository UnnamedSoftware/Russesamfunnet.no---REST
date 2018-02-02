/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.unnamedsoftware.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kristian
 */
@Entity
@Table(name = "knots")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Knots.findAll", query = "SELECT k FROM Knots k")
    , @NamedQuery(name = "Knots.findByKnotId", query = "SELECT k FROM Knots k WHERE k.knotId = :knotId")
    , @NamedQuery(name = "Knots.findByKnotName", query = "SELECT k FROM Knots k WHERE k.knotName = :knotName")
    , @NamedQuery(name = "Knots.findByKnotDetails", query = "SELECT k FROM Knots k WHERE k.knotDetails = :knotDetails")
    , @NamedQuery(name = "Knots.findByKnotPicture", query = "SELECT k FROM Knots k WHERE k.knotPicture = :knotPicture")})
public class Knots implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "knot_id")
    private Long knotId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "knot_name")
    private String knotName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "knot_details")
    private String knotDetails;
    @Size(max = 255)
    @Column(name = "knot_picture")
    private String knotPicture;
    
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "knotId")
    private List<Completed> completedList;
    */
    
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    @ManyToOne(optional = false)
    private School schoolId;

    public Knots() {
    }

    public Knots(Long knotId) {
        this.knotId = knotId;
    }

    public Knots(Long knotId, String knotName, String knotDetails) {
        this.knotId = knotId;
        this.knotName = knotName;
        this.knotDetails = knotDetails;
    }

    public Long getKnotId() {
        return knotId;
    }

    public void setKnotId(Long knotId) {
        this.knotId = knotId;
    }

    public String getKnotName() {
        return knotName;
    }

    public void setKnotName(String knotName) {
        this.knotName = knotName;
    }

    public String getKnotDetails() {
        return knotDetails;
    }

    public void setKnotDetails(String knotDetails) {
        this.knotDetails = knotDetails;
    }

    public String getKnotPicture() {
        return knotPicture;
    }

    public void setKnotPicture(String knotPicture) {
        this.knotPicture = knotPicture;
    }
/*
    @XmlTransient
    public List<Completed> getCompletedList() {
        return completedList;
    }

    public void setCompletedList(List<Completed> completedList) {
        this.completedList = completedList;
    }
*/
    public School getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(School schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (knotId != null ? knotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Knots)) {
            return false;
        }
        Knots other = (Knots) object;
        if ((this.knotId == null && other.knotId != null) || (this.knotId != null && !this.knotId.equals(other.knotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "no.ntnu.unnamedsoftware.entity.Knots[ knotId=" + knotId + " ]";
    }
    
}
