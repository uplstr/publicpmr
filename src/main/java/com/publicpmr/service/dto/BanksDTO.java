package com.publicpmr.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.publicpmr.domain.enumeration.BankName;

/**
 * A DTO for the {@link com.publicpmr.domain.Banks} entity.
 */
public class BanksDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private BankName systemName;

    @NotNull
    private Boolean status;


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

    public BankName getSystemName() {
        return systemName;
    }

    public void setSystemName(BankName systemName) {
        this.systemName = systemName;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BanksDTO banksDTO = (BanksDTO) o;
        if (banksDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), banksDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BanksDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", systemName='" + getSystemName() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
