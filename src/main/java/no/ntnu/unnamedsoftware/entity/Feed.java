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
@Table(name = "feed")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feed.findAll", query = "SELECT f FROM Feed f")
    , @NamedQuery(name = "Feed.findByFeedId", query = "SELECT f FROM Feed f WHERE f.feedId = :feedId")
    , @NamedQuery(name = "Feed.findByType", query = "SELECT f FROM Feed f WHERE f.type = :type")
    , @NamedQuery(name = "Feed.findByMessage", query = "SELECT f FROM Feed f WHERE f.message = :message")})
public class Feed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "feed_id")
    private Long feedId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(name = "message")
    private String message;
    @JoinColumn(name = "russ_id", referencedColumnName = "russ_id")
    @ManyToOne(optional = false)
    private Russ russId;
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    @ManyToOne
    private School schoolId;
    @JoinColumn(name = "zone_id", referencedColumnName = "zone_id")
    @ManyToOne
    private Zone zoneId;

    public Feed() {
    }

    public Feed(Long feedId) {
        this.feedId = feedId;
    }

    public Feed(Long feedId, String type, String message) {
        this.feedId = feedId;
        this.type = type;
        this.message = message;
    }

    public Long getFeedId() {
        return feedId;
    }

    public void setFeedId(Long feedId) {
        this.feedId = feedId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Russ getRussId() {
        return russId;
    }

    public void setRussId(Russ russId) {
        this.russId = russId;
    }

    public School getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(School schoolId) {
        this.schoolId = schoolId;
    }

    public Zone getZoneId() {
        return zoneId;
    }

    public void setZoneId(Zone zoneId) {
        this.zoneId = zoneId;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feedId != null ? feedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feed)) {
            return false;
        }
        Feed other = (Feed) object;
        if ((this.feedId == null && other.feedId != null) || (this.feedId != null && !this.feedId.equals(other.feedId))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "no.ntnu.unnamedsoftware.entity.Feed[ feedId=" + feedId + " ]";
    }
    
}
