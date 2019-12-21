package com.publicpmr.service.mapper;

import com.publicpmr.domain.*;
import com.publicpmr.service.dto.TaxiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Taxi} and its DTO {@link TaxiDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaxiMapper extends EntityMapper<TaxiDTO, Taxi> {



    default Taxi fromId(Long id) {
        if (id == null) {
            return null;
        }
        Taxi taxi = new Taxi();
        taxi.setId(id);
        return taxi;
    }
}
