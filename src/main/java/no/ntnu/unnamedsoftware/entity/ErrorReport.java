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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kristian
 */
@Entity
@Table(name = "error_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ErrorReport.findAll", query = "SELECT e FROM ErrorReport e")
    , @NamedQuery(name = "ErrorReport.findByErrorId", query = "SELECT e FROM ErrorReport e WHERE e.errorId = :errorId")
    , @NamedQuery(name = "ErrorReport.findByErrorSubject", query = "SELECT e FROM ErrorReport e WHERE e.errorSubject = :errorSubject")
    , @NamedQuery(name = "ErrorReport.findByErrorMessage", query = "SELECT e FROM ErrorReport e WHERE e.errorMessage = :errorMessage")})
public class ErrorReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "error_id")
    private Long errorId;
    @Size(max = 2147483647)
    @Column(name = "error_subject")
    private String errorSubject;
    @Size(max = 2147483647)
    @Column(name = "error_message")
    private String errorMessage;
    @JoinColumn(name = "russ_id", referencedColumnName = "russ_id")
    @ManyToOne
    private Russ russId;

    public ErrorReport() {
    }

    public ErrorReport(Long errorId) {
        this.errorId = errorId;
    }

    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }

    public String getErrorSubject() {
        return errorSubject;
    }

    public void setErrorSubject(String errorSubject) {
        this.errorSubject = errorSubject;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
        hash += (errorId != null ? errorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ErrorReport)) {
            return false;
        }
        ErrorReport other = (ErrorReport) object;
        if ((this.errorId == null && other.errorId != null) || (this.errorId != null && !this.errorId.equals(other.errorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "autogenerering.ErrorReport[ errorId=" + errorId + " ]";
    }
    
}
