package com.publicpmr.service.mapper;

import com.publicpmr.domain.*;
import com.publicpmr.service.dto.ExchangeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exchange} and its DTO {@link ExchangeDTO}.
 */
@Mapper(componentModel = "spring", uses = {RatesMapper.class})
public interface ExchangeMapper extends EntityMapper<ExchangeDTO, Exchange> {


    @Mapping(target = "removeRates", ignore = true)

    default Exchange fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exchange exchange = new Exchange();
        exchange.setId(id);
        return exchange;
    }
}
