package com.publicpmr.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Exchange.
 */
@Entity
@Table(name = "exchange")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exchange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "exchange_rates",
               joinColumns = @JoinColumn(name = "exchange_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "rates_id", referencedColumnName = "id"))
    private Set<Rates> rates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Exchange date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<Rates> getRates() {
        return rates;
    }

    public Exchange rates(Set<Rates> rates) {
        this.rates = rates;
        return this;
    }

    public Exchange addRates(Rates rates) {
        this.rates.add(rates);
        rates.getExchanges().add(this);
        return this;
    }

    public Exchange removeRates(Rates rates) {
        this.rates.remove(rates);
        rates.getExchanges().remove(this);
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
        if (!(o instanceof Exchange)) {
            return false;
        }
        return id != null && id.equals(((Exchange) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Exchange{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
