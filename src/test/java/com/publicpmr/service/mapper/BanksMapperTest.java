package com.publicpmr.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class BanksMapperTest {

    private BanksMapper banksMapper;

    @BeforeEach
    public void setUp() {
        banksMapper = new BanksMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(banksMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(banksMapper.fromId(null)).isNull();
    }
}
