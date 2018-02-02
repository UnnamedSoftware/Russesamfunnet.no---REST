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
@Table(name = "school")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "School.findAll", query = "SELECT s FROM School s")
    , @NamedQuery(name = "School.findBySchoolId", query = "SELECT s FROM School s WHERE s.schoolId = :schoolId")
    , @NamedQuery(name = "School.findBySchoolName", query = "SELECT s FROM School s WHERE s.schoolName = :schoolName")
    , @NamedQuery(name = "School.findBySchoolStatus", query = "SELECT s FROM School s WHERE s.schoolStatus = :schoolStatus")
    , @NamedQuery(name = "School.findBySchoolLocation", query = "SELECT s FROM School s WHERE s.schoolLocation = :schoolLocation")
    , @NamedQuery(name = "School.findBySchoolMunicipality", query = "SELECT s FROM School s WHERE s.schoolMunicipality = :schoolMunicipality")
    , @NamedQuery(name = "School.findBySchoolCoordinates", query = "SELECT s FROM School s WHERE s.schoolCoordinates = :schoolCoordinates")})
public class School implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "school_id")
    private Long schoolId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "school_name")
    private String schoolName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "school_status")
    private String schoolStatus;
    
    @Size(max = 255)
    @Column(name = "school_location")
    private String schoolLocation;
    @Size(max = 255)
    @Column(name = "school_municipality")
    private String schoolMunicipality;
    @Size(max = 100)
    @Column(name = "school_coordinates")
    private String schoolCoordinates;
    
    
    /*
    @OneToMany(mappedBy = "schoolId")
    private List<Feed> feedList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schoolId")
    private List<Russ> russList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schoolId")
    private List<Knots> knotsList;
*/
    
    
    
    
    public School() {
    }

    public School(Long schoolId) {
        this.schoolId = schoolId;
    }

    public School(Long schoolId, String schoolName, String schoolLocation, String schoolMunicipality, String schoolCoordinates, String schoolStatus) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolLocation = schoolLocation;
        this.schoolMunicipality = schoolMunicipality;
        this.schoolCoordinates = schoolCoordinates;
        this.schoolStatus = schoolStatus;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolStatus() {
        return schoolStatus;
    }

    public void setSchoolStatus(String schoolStatus) {
        this.schoolStatus = schoolStatus;
    }
    
    public String getSchoolLocation() {
        return schoolLocation;
    }

    public void setSchoolLocation(String schoolLocation) {
        this.schoolLocation = schoolLocation;
    }

    public String getSchoolMunicipality() {
        return schoolMunicipality;
    }

    public void setSchoolMunicipality(String schoolMunicipality) {
        this.schoolMunicipality = schoolMunicipality;
    }

    public String getSchoolCoordinates() {
        return schoolCoordinates;
    }

    public void setSchoolCoordinates(String schoolCoordinates) {
        this.schoolCoordinates = schoolCoordinates;
    }

    /*
    @XmlTransient
    public List<Feed> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<Feed> feedList) {
        this.feedList = feedList;
    }

    @XmlTransient
    public List<Russ> getRussList() {
        return russList;
    }

    public void setRussList(List<Russ> russList) {
        this.russList = russList;
    }

    @XmlTransient
    public List<Knots> getKnotsList() {
        return knotsList;
    }

    public void setKnotsList(List<Knots> knotsList) {
        this.knotsList = knotsList;
    }
    */

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolId != null ? schoolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof School)) {
            return false;
        }
        School other = (School) object;
        if ((this.schoolId == null && other.schoolId != null) || (this.schoolId != null && !this.schoolId.equals(other.schoolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "no.ntnu.unnamedsoftware.entity.School[ schoolId=" + schoolId + " ]";
    }
    
}
