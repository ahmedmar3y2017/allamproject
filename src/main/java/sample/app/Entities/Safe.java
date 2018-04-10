/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.app.Entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ahmed mar3y
 */
@Entity
@Table(catalog = "allam", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Safe.findAll", query = "SELECT s FROM Safe s"),
        @NamedQuery(name = "Safe.findById", query = "SELECT s FROM Safe s WHERE s.id = :id"),
        @NamedQuery(name = "Safe.findByType", query = "SELECT s FROM Safe s WHERE s.type = :type"),
        @NamedQuery(name = "Safe.findByNotes", query = "SELECT s FROM Safe s WHERE s.notes = :notes"),
        @NamedQuery(name = "Safe.findByDate", query = "SELECT s FROM Safe s WHERE s.date = :date")})
// add to all Entities
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class Safe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    private String type;
    private String notes;
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "usersid", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users usersid;

    public Safe() {
    }

    public Safe(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Users getUsersid() {
        return usersid;
    }

    public void setUsersid(Users usersid) {
        this.usersid = usersid;
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
        if (!(object instanceof Safe)) {
            return false;
        }
        Safe other = (Safe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Safe[ id=" + id + " ]";
    }

}
