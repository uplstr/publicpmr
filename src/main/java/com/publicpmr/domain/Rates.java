package com.publicpmr.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.publicpmr.domain.enumeration.BankName;

/**
 * A Rates.
 */
@Entity
@Table(name = "rates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bank_system_name", nullable = false)
    private BankName bankSystemName;

    @ManyToOne
    @JsonIgnoreProperties("rates")
    private Banks bank;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "rates_curs",
               joinColumns = @JoinColumn(name = "rates_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "curs_id", referencedColumnName = "id"))
    private Set<Curs> curs = new HashSet<>();

    @ManyToMany(mappedBy = "rates")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Exchange> exchanges = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankName getBankSystemName() {
        return bankSystemName;
    }

    public Rates bankSystemName(BankName bankSystemName) {
        this.bankSystemName = bankSystemName;
        return this;
    }

    public void setBankSystemName(BankName bankSystemName) {
        this.bankSystemName = bankSystemName;
    }

    public Banks getBank() {
        return bank;
    }

    public Rates bank(Banks banks) {
        this.bank = banks;
        return this;
    }

    public void setBank(Banks banks) {
        this.bank = banks;
    }

    public Set<Curs> getCurs() {
        return curs;
    }

    public Rates curs(Set<Curs> curs) {
        this.curs = curs;
        return this;
    }

    public Rates addCurs(Curs curs) {
        this.curs.add(curs);
        curs.getRates().add(this);
        return this;
    }

    public Rates removeCurs(Curs curs) {
        this.curs.remove(curs);
        curs.getRates().remove(this);
        return this;
    }

    public void setCurs(Set<Curs> curs) {
        this.curs = curs;
    }

    public Set<Exchange> getExchanges() {
        return exchanges;
    }

    public Rates exchanges(Set<Exchange> exchanges) {
        this.exchanges = exchanges;
        return this;
    }

    public Rates addExchange(Exchange exchange) {
        this.exchanges.add(exchange);
        exchange.getRates().add(this);
        return this;
    }

    public Rates removeExchange(Exchange exchange) {
        this.exchanges.remove(exchange);
        exchange.getRates().remove(this);
        return this;
    }

    public void setExchanges(Set<Exchange> exchanges) {
        this.exchanges = exchanges;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rates)) {
            return false;
        }
        return id != null && id.equals(((Rates) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rates{" +
            "id=" + getId() +
            ", bankSystemName='" + getBankSystemName() + "'" +
            "}";
    }
}
