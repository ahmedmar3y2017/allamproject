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
        @NamedQuery(name = "Maintable.findAll", query = "SELECT m FROM Maintable m"),
        @NamedQuery(name = "Maintable.findById", query = "SELECT m FROM Maintable m WHERE m.id = :id"),
        @NamedQuery(name = "Maintable.findByDate", query = "SELECT m FROM Maintable m WHERE m.date = :date"),
        @NamedQuery(name = "Maintable.findByPolesa", query = "SELECT m FROM Maintable m WHERE m.polesa = :polesa"),
        @NamedQuery(name = "Maintable.findByCarNumber", query = "SELECT m FROM Maintable m WHERE m.carNumber = :carNumber"),
        @NamedQuery(name = "Maintable.findByAmount", query = "SELECT m FROM Maintable m WHERE m.amount = :amount"),
        @NamedQuery(name = "Maintable.findByNowlon", query = "SELECT m FROM Maintable m WHERE m.nowlon = :nowlon"),
        @NamedQuery(name = "Maintable.findByOhda", query = "SELECT m FROM Maintable m WHERE m.ohda = :ohda"),
        @NamedQuery(name = "Maintable.findByMezan", query = "SELECT m FROM Maintable m WHERE m.mezan = :mezan"),
        @NamedQuery(name = "Maintable.findByOffice", query = "SELECT m FROM Maintable m WHERE m.office = :office"),
        @NamedQuery(name = "Maintable.findByTotal", query = "SELECT m FROM Maintable m WHERE m.total = :total"),
        @NamedQuery(name = "Maintable.findByType", query = "SELECT m FROM Maintable m WHERE m.type = :type"),
        @NamedQuery(name = "Maintable.findByCityFrom", query = "SELECT m FROM Maintable m WHERE m.cityFrom = :cityFrom"),
        @NamedQuery(name = "Maintable.findByCityTo", query = "SELECT m FROM Maintable m WHERE m.cityTo = :cityTo")})
// add to all Entities
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class Maintable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private String polesa;
    private String carNumber;
    private Double amount;
    private Double nowlon;
    private Double ohda;
    //    private Double added;
    private Double mezan;
    //    private Double discount;
    private Double office;
    private Double agz;
    private Double total;
    private Double safy;
    private String type;
    private String cityFrom;
    private String cityTo;
    @JoinColumn(name = "clientsid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clients clientsid;

    public Maintable() {
    }


    public Maintable(Date date, String polesa, String carNumber, Double amount, Double nowlon, Double ohda, Double mezan, Double office, Double total, String type, String cityFrom, String cityTo, double agz, double safy, Clients clientsid) {
        this.date = date;
        this.polesa = polesa;
        this.carNumber = carNumber;
        this.amount = amount;
        this.nowlon = nowlon;
        this.ohda = ohda;
//        this.added = added;
        this.mezan = mezan;
//        this.discount = discount;
        this.office = office;
        this.total = total;
        this.type = type;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.clientsid = clientsid;
        this.safy = safy;
        this.agz = agz;
    }

    public Double getAgz() {
        return agz;
    }

    public void setAgz(Double agz) {
        this.agz = agz;
    }

    public Double getSafy() {
        return safy;
    }

    public void setSafy(Double safy) {
        this.safy = safy;
    }

    public Maintable(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPolesa() {
        return polesa;
    }

    public void setPolesa(String polesa) {
        this.polesa = polesa;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getNowlon() {
        return nowlon;
    }

    public void setNowlon(Double nowlon) {
        this.nowlon = nowlon;
    }

    public Double getOhda() {
        return ohda;
    }

    public void setOhda(Double ohda) {
        this.ohda = ohda;
    }


    public Double getMezan() {
        return mezan;
    }

    public void setMezan(Double mezan) {
        this.mezan = mezan;
    }


    public Double getOffice() {
        return office;
    }

    public void setOffice(Double office) {
        this.office = office;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public Clients getClientsid() {
        return clientsid;
    }

    public void setClientsid(Clients clientsid) {
        this.clientsid = clientsid;
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
        if (!(object instanceof Maintable)) {
            return false;
        }
        Maintable other = (Maintable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Maintable[ id=" + id + " ]";
    }

}
