package com.publicpmr.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.publicpmr.domain.Taxi} entity.
 */
public class TaxiDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String title;

    @NotNull
    private Long phone;

    @NotNull
    private Float active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Float getActive() {
        return active;
    }

    public void setActive(Float active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaxiDTO taxiDTO = (TaxiDTO) o;
        if (taxiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taxiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaxiDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", phone=" + getPhone() +
            ", active=" + getActive() +
            "}";
    }
}
