package sample.app.Entities;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(catalog = "allam", schema = "")
@XmlRootElement
// add to all Entities
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate

public class mandob implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    private Date date;
    private double ohda;
    private double masrof;
    private double rest;

    public mandob() {
    }


    public mandob(Date date, double ohda, double masrof, double rest) {
        this.date = date;
        this.ohda = ohda;
        this.masrof = masrof;
        this.rest = rest;
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

    public double getOhda() {
        return ohda;
    }

    public void setOhda(double ohda) {
        this.ohda = ohda;
    }

    public double getMasrof() {
        return masrof;
    }

    public void setMasrof(double masrof) {
        this.masrof = masrof;
    }

    public double getRest() {
        return rest;
    }

    public void setRest(double rest) {
        this.rest = rest;
    }
}