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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kristian
 */
@Entity
@Table(name = "completed")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Completed.findAll", query = "SELECT c FROM Completed c")
    , @NamedQuery(name = "Completed.findByCompletedId", query = "SELECT c FROM Completed c WHERE c.completedId = :completedId")})
public class Completed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "completed_id")
    private Integer completedId;
    @JoinColumn(name = "knot_id", referencedColumnName = "knot_id")
    @ManyToOne
    private Knots knotId;
    @JoinColumn(name = "russ_id", referencedColumnName = "russ_id")
    @ManyToOne
    private Russ russId;
    @JoinColumn(name = "witness_id1", referencedColumnName = "russ_id")
    @ManyToOne
    private Russ witnessId1;
    @JoinColumn(name = "witness_id2", referencedColumnName = "russ_id")
    @ManyToOne
    private Russ witnessId2;

    public Completed() {
    }

    public Completed(Integer completedId) {
        this.completedId = completedId;
    }

    public Integer getCompletedId() {
        return completedId;
    }

    public void setCompletedId(Integer completedId) {
        this.completedId = completedId;
    }

    public Knots getKnotId() {
        return knotId;
    }

    public void setKnotId(Knots knotId) {
        this.knotId = knotId;
    }

    public Russ getRussId() {
        return russId;
    }

    public void setRussId(Russ russId) {
        this.russId = russId;
    }

    public Russ getWitnessId1() {
        return witnessId1;
    }

    public void setWitnessId1(Russ witnessId1) {
        this.witnessId1 = witnessId1;
    }

    public Russ getWitnessId2() {
        return witnessId2;
    }

    public void setWitnessId2(Russ witnessId2) {
        this.witnessId2 = witnessId2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (completedId != null ? completedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Completed)) {
            return false;
        }
        Completed other = (Completed) object;
        if ((this.completedId == null && other.completedId != null) || (this.completedId != null && !this.completedId.equals(other.completedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.glutenfreesoftware.russesamfunnet.entity.Completed[ completedId=" + completedId + " ]";
    }
    
}
