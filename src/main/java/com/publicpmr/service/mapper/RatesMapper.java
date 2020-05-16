package com.publicpmr.service.mapper;

import com.publicpmr.domain.*;
import com.publicpmr.service.dto.RatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rates} and its DTO {@link RatesDTO}.
 */
@Mapper(componentModel = "spring", uses = {BanksMapper.class, CursMapper.class})
public interface RatesMapper extends EntityMapper<RatesDTO, Rates> {

    @Mapping(source = "bank.id", target = "bankId")
    RatesDTO toDto(Rates rates);

    @Mapping(source = "bankId", target = "bank")
    @Mapping(target = "removeCurs", ignore = true)
    @Mapping(target = "exchanges", ignore = true)
    @Mapping(target = "removeExchange", ignore = true)
    Rates toEntity(RatesDTO ratesDTO);

    default Rates fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rates rates = new Rates();
        rates.setId(id);
        return rates;
    }
}
