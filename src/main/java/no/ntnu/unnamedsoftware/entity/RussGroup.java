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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kristian
 */
@Entity
@Table(name = "russ_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RussGroup.findAll", query = "SELECT r FROM RussGroup r")
    , @NamedQuery(name = "RussGroup.findByRussGroupId", query = "SELECT r FROM RussGroup r WHERE r.russGroupId = :russGroupId")})
public class RussGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "russ_group_id")
    private Long russGroupId;
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    @ManyToOne(optional = false)
    private Group groupId;
    @JoinColumn(name = "russ_id", referencedColumnName = "russ_id")
    @ManyToOne(optional = false)
    private Russ russId;

    public RussGroup() {
    }

    public RussGroup(Long russGroupId) {
        this.russGroupId = russGroupId;
    }

    public Long getRussGroupId() {
        return russGroupId;
    }

    public void setRussGroupId(Long russGroupId) {
        this.russGroupId = russGroupId;
    }

    public Group getGroupId() {
        return groupId;
    }

    public void setGroupId(Group groupId) {
        this.groupId = groupId;
    }

    public Russ getRussId() {
        return russId;
    }

    public void setRussId(Russ russId) {
        this.russId = russId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (russGroupId != null ? russGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RussGroup)) {
            return false;
        }
        RussGroup other = (RussGroup) object;
        if ((this.russGroupId == null && other.russGroupId != null) || (this.russGroupId != null && !this.russGroupId.equals(other.russGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "autogenerering.RussGroup[ russGroupId=" + russGroupId + " ]";
    }
    
}
