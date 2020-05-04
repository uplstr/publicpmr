package com.publicpmr.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.publicpmr.domain.enumeration.BankName;

/**
 * A Banks.
 */
@Entity
@Table(name = "banks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Banks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "system_name", nullable = false)
    private BankName systemName;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Banks title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BankName getSystemName() {
        return systemName;
    }

    public Banks systemName(BankName systemName) {
        this.systemName = systemName;
        return this;
    }

    public void setSystemName(BankName systemName) {
        this.systemName = systemName;
    }

    public Boolean isStatus() {
        return status;
    }

    public Banks status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banks)) {
            return false;
        }
        return id != null && id.equals(((Banks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Banks{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", systemName='" + getSystemName() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
