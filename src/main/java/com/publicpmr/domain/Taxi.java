package com.publicpmr.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Taxi.
 */
@Entity
@Table(name = "taxi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Taxi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "phone", nullable = false)
    private Long phone;

    @NotNull
    @Column(name = "active", nullable = false)
    private Float active;

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

    public Taxi title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPhone() {
        return phone;
    }

    public Taxi phone(Long phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Float getActive() {
        return active;
    }

    public Taxi active(Float active) {
        this.active = active;
        return this;
    }

    public void setActive(Float active) {
        this.active = active;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Taxi)) {
            return false;
        }
        return id != null && id.equals(((Taxi) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Taxi{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", phone=" + getPhone() +
            ", active=" + getActive() +
            "}";
    }
}
