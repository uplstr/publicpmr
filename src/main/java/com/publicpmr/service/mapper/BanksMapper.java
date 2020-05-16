package com.publicpmr.service.mapper;

import com.publicpmr.domain.*;
import com.publicpmr.service.dto.BanksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Banks} and its DTO {@link BanksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BanksMapper extends EntityMapper<BanksDTO, Banks> {



    default Banks fromId(Long id) {
        if (id == null) {
            return null;
        }
        Banks banks = new Banks();
        banks.setId(id);
        return banks;
    }
}
