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
import javax.persistence.FetchType;
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
@Table(name = "scoreboard")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Scoreboard.findAll", query = "SELECT s FROM Scoreboard s")
    , @NamedQuery(name = "Scoreboard.findByScoreboardId", query = "SELECT s FROM Scoreboard s WHERE s.scoreboardId = :scoreboardId")
    , @NamedQuery(name = "Scoreboard.findByPoints", query = "SELECT s FROM Scoreboard s WHERE s.points = :points")})
public class Scoreboard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "scoreboard_id")
    private Integer scoreboardId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "points")
    private int points;
    @JoinColumn(name = "russ_id", referencedColumnName = "russ_id")
    @ManyToOne
    private Russ russId;

    public Scoreboard() {
    }

    public Scoreboard(Integer scoreboardId) {
        this.scoreboardId = scoreboardId;
    }

    public Scoreboard(Integer scoreboardId, int points) {
        this.scoreboardId = scoreboardId;
        this.points = points;
    }

    public Integer getScoreboardId() {
        return scoreboardId;
    }

    public void setScoreboardId(Integer scoreboardId) {
        this.scoreboardId = scoreboardId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
        hash += (scoreboardId != null ? scoreboardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scoreboard)) {
            return false;
        }
        Scoreboard other = (Scoreboard) object;
        if ((this.scoreboardId == null && other.scoreboardId != null) || (this.scoreboardId != null && !this.scoreboardId.equals(other.scoreboardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.glutenfreesoftware.russesamfunnet.entity.Scoreboard[ scoreboardId=" + scoreboardId + " ]";
    }
    
}
