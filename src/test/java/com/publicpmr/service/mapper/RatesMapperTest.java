package com.publicpmr.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RatesMapperTest {

    private RatesMapper ratesMapper;

    @BeforeEach
    public void setUp() {
        ratesMapper = new RatesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(ratesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ratesMapper.fromId(null)).isNull();
    }
}
