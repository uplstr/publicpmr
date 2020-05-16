package com.publicpmr.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.publicpmr.domain.enumeration.BankName;

/**
 * A DTO for the {@link com.publicpmr.domain.Rates} entity.
 */
public class RatesDTO implements Serializable {

    private Long id;

    @NotNull
    private BankName bankSystemName;


    private Long bankId;

    private Set<CursDTO> curs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankName getBankSystemName() {
        return bankSystemName;
    }

    public void setBankSystemName(BankName bankSystemName) {
        this.bankSystemName = bankSystemName;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long banksId) {
        this.bankId = banksId;
    }

    public Set<CursDTO> getCurs() {
        return curs;
    }

    public void setCurs(Set<CursDTO> curs) {
        this.curs = curs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RatesDTO ratesDTO = (RatesDTO) o;
        if (ratesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ratesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RatesDTO{" +
            "id=" + getId() +
            ", bankSystemName='" + getBankSystemName() + "'" +
            ", bank=" + getBankId() +
            "}";
    }
}
