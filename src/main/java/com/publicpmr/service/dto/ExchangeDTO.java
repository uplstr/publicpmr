package com.publicpmr.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.publicpmr.domain.Exchange} entity.
 */
public class ExchangeDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime date;


    private Set<RatesDTO> rates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<RatesDTO> getRates() {
        return rates;
    }

    public void setRates(Set<RatesDTO> rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExchangeDTO exchangeDTO = (ExchangeDTO) o;
        if (exchangeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exchangeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExchangeDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
