package com.publicpmr.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.publicpmr.domain.enumeration.CurrencyName;

/**
 * A Curs.
 */
@Entity
@Table(name = "curs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Curs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "purchase", nullable = false)
    private Double purchase;

    @NotNull
    @Column(name = "sale", nullable = false)
    private Double sale;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyName currency;

    @ManyToMany(mappedBy = "curs")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Rates> rates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPurchase() {
        return purchase;
    }

    public Curs purchase(Double purchase) {
        this.purchase = purchase;
        return this;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }

    public Double getSale() {
        return sale;
    }

    public Curs sale(Double sale) {
        this.sale = sale;
        return this;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    public CurrencyName getCurrency() {
        return currency;
    }

    public Curs currency(CurrencyName currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(CurrencyName currency) {
        this.currency = currency;
    }

    public Set<Rates> getRates() {
        return rates;
    }

    public Curs rates(Set<Rates> rates) {
        this.rates = rates;
        return this;
    }

    public Curs addRates(Rates rates) {
        this.rates.add(rates);
        rates.getCurs().add(this);
        return this;
    }

    public Curs removeRates(Rates rates) {
        this.rates.remove(rates);
        rates.getCurs().remove(this);
        return this;
    }

    public void setRates(Set<Rates> rates) {
        this.rates = rates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Curs)) {
            return false;
        }
        return id != null && id.equals(((Curs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Curs{" +
            "id=" + getId() +
            ", purchase=" + getPurchase() +
            ", sale=" + getSale() +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
