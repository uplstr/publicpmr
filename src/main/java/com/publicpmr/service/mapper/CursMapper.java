package com.publicpmr.service.mapper;

import com.publicpmr.domain.*;
import com.publicpmr.service.dto.CursDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Curs} and its DTO {@link CursDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CursMapper extends EntityMapper<CursDTO, Curs> {


    @Mapping(target = "rates", ignore = true)
    @Mapping(target = "removeRates", ignore = true)
    Curs toEntity(CursDTO cursDTO);

    default Curs fromId(Long id) {
        if (id == null) {
            return null;
        }
        Curs curs = new Curs();
        curs.setId(id);
        return curs;
    }
}
