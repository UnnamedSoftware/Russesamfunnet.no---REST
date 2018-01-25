/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.unnamedsoftware.entity;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kristian
 */
@Entity
@Table(name = "zone")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zone.findAll", query = "SELECT z FROM Zone z")
    , @NamedQuery(name = "Zone.findByZoneId", query = "SELECT z FROM Zone z WHERE z.zoneId = :zoneId")})
public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "zone_id")
    private Integer zoneId;

    public Zone() {
    }

    public Zone(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zoneId != null ? zoneId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zone)) {
            return false;
        }
        Zone other = (Zone) object;
        if ((this.zoneId == null && other.zoneId != null) || (this.zoneId != null && !this.zoneId.equals(other.zoneId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "no.ntnu.unnamedsoftware.entity.Zone[ zoneId=" + zoneId + " ]";
    }
    
}
