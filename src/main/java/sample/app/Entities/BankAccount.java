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
@Table(name = "bank_account", catalog = "allam", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "BankAccount.findAll", query = "SELECT b FROM BankAccount b"),
        @NamedQuery(name = "BankAccount.findById", query = "SELECT b FROM BankAccount b WHERE b.id = :id"),
        @NamedQuery(name = "BankAccount.findByType", query = "SELECT b FROM BankAccount b WHERE b.type = :type"),
        @NamedQuery(name = "BankAccount.findByMoney", query = "SELECT b FROM BankAccount b WHERE b.money = :money"),
        @NamedQuery(name = "BankAccount.findByDate", query = "SELECT b FROM BankAccount b WHERE b.date = :date")})
// add to all Entities
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double money;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String notes;
    @JoinColumn(name = "bankid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Bank bankid;

    public BankAccount() {
    }


    public BankAccount(String type, Double money, Date date, String notes, Bank bankid) {
        this.type = type;
        this.money = money;
        this.date = date;
        this.notes = notes;
        this.bankid = bankid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BankAccount(Integer id) {
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Bank getBankid() {
        return bankid;
    }

    public void setBankid(Bank bankid) {
        this.bankid = bankid;
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
        if (!(object instanceof BankAccount)) {
            return false;
        }
        BankAccount other = (BankAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.BankAccount[ id=" + id + " ]";
    }

}
