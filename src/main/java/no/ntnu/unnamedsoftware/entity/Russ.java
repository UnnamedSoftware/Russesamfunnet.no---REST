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
@Table(name = "russ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Russ.findAll", query = "SELECT r FROM Russ r")
    , @NamedQuery(name = "Russ.findByRussId", query = "SELECT r FROM Russ r WHERE r.russId = :russId")
    , @NamedQuery(name = "Russ.findByRussStatus", query = "SELECT r FROM Russ r WHERE r.russStatus = :russStatus")
    , @NamedQuery(name = "Russ.findByFirstName", query = "SELECT r FROM Russ r WHERE r.firstName = :firstName")
    , @NamedQuery(name = "Russ.findByLastName", query = "SELECT r FROM Russ r WHERE r.lastName = :lastName")
    , @NamedQuery(name = "Russ.findByEmail", query = "SELECT r FROM Russ r WHERE r.email = :email")
    , @NamedQuery(name = "Russ.findByRussPassword", query = "SELECT r FROM Russ r WHERE r.russPassword = :russPassword")
    , @NamedQuery(name = "Russ.findByRussPasswordSalt", query = "SELECT r FROM Russ r WHERE r.russPasswordSalt = :russPasswordSalt")
    , @NamedQuery(name = "Russ.findByProfilePicture", query = "SELECT r FROM Russ r WHERE r.profilePicture = :profilePicture")
    , @NamedQuery(name = "Russ.findByRussCard", query = "SELECT r FROM Russ r WHERE r.russCard = :russCard")
    , @NamedQuery(name = "Russ.findByRussRole", query = "SELECT r FROM Russ r WHERE r.russRole = :russRole")
    , @NamedQuery(name = "Russ.findByRussYear", query = "SELECT r FROM Russ r WHERE r.russYear = :russYear")})
public class Russ implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "russ_id")
    private Long russId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "russ_status")
    private String russStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "last_name")
    private String lastName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "russ_password")
    private String russPassword;
    @Size(max = 255)
    @Column(name = "russ_password_salt")
    private String russPasswordSalt;
    @Size(max = 255)
    @Column(name = "profile_picture")
    private String profilePicture;
    @Size(max = 255)
    @Column(name = "russ_card")
    private String russCard;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "russ_role")
    private String russRole;
    @Basic(optional = false)
    @NotNull
    @Column(name = "russ_year")
    private int russYear;
    
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "russId")
    private List<Feed> feedList;
    */
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    @ManyToOne(optional = false)
    private School schoolId;
    
    
    
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "russId")
    private List<Completed> completedList;
    
    @OneToMany(mappedBy = "witnessId2")
    private List<Completed> completedList1;
    
    
    @OneToMany(mappedBy = "witnessId1")
    private List<Completed> completedList2;
    */
    
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "russId")
    private List<Scoreboard> scoreboardList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "russId")
    private List<Tags> tagsList;
*/ 
    
    public Russ() {
    }

    public Russ(Long russId) {
        this.russId = russId;
    }

    public Russ(Long russId, String russStatus, String firstName, String lastName, String email, String russPassword, String russRole, int russYear) {
        this.russId = russId;
        this.russStatus = russStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.russPassword = russPassword;
        this.russRole = russRole;
        this.russYear = russYear;
    }

    public Long getRussId() {
        return russId;
    }

    public void setRussId(Long russId) {
        this.russId = russId;
    }

    public String getRussStatus() {
        return russStatus;
    }

    public void setRussStatus(String russStatus) {
        this.russStatus = russStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRussPassword() {
        return russPassword;
    }

    public void setRussPassword(String russPassword) {
        this.russPassword = russPassword;
    }

    public String getRussPasswordSalt() {
        return russPasswordSalt;
    }

    public void setRussPasswordSalt(String russPasswordSalt) {
        this.russPasswordSalt = russPasswordSalt;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getRussCard() {
        return russCard;
    }

    public void setRussCard(String russCard) {
        this.russCard = russCard;
    }

    public String getRussRole() {
        return russRole;
    }

    public void setRussRole(String russRole) {
        this.russRole = russRole;
    }

    public int getRussYear() {
        return russYear;
    }

    public void setRussYear(int russYear) {
        this.russYear = russYear;
    }

    /*
    @XmlTransient
    public List<Feed> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<Feed> feedList) {
        this.feedList = feedList;
    }
    */

    public School getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(School schoolId) {
        this.schoolId = schoolId;
    }
    
/*
    @XmlTransient
    public List<Completed> getCompletedList() {
        return completedList;
    }

    public void setCompletedList(List<Completed> completedList) {
        this.completedList = completedList;
    }

    @XmlTransient
    public List<Completed> getCompletedList1() {
        return completedList1;
    }

    public void setCompletedList1(List<Completed> completedList1) {
        this.completedList1 = completedList1;
    }

    @XmlTransient
    public List<Completed> getCompletedList2() {
        return completedList2;
    }

    public void setCompletedList2(List<Completed> completedList2) {
        this.completedList2 = completedList2;
    }
*/
    /*
    @XmlTransient
    public List<Scoreboard> getScoreboardList() {
        return scoreboardList;
    }

    public void setScoreboardList(List<Scoreboard> scoreboardList) {
        this.scoreboardList = scoreboardList;
    }

    @XmlTransient
    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }
    */

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (russId != null ? russId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Russ)) {
            return false;
        }
        Russ other = (Russ) object;
        if ((this.russId == null && other.russId != null) || (this.russId != null && !this.russId.equals(other.russId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "no.ntnu.unnamedsoftware.entity.Russ[ russId=" + russId + " ]";
    }
    
}
