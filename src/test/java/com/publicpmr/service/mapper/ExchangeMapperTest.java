package com.publicpmr.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ExchangeMapperTest {

    private ExchangeMapper exchangeMapper;

    @BeforeEach
    public void setUp() {
        exchangeMapper = new ExchangeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(exchangeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(exchangeMapper.fromId(null)).isNull();
    }
}
