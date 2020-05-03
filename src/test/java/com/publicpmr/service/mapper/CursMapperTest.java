package com.publicpmr.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CursMapperTest {

    private CursMapper cursMapper;

    @BeforeEach
    public void setUp() {
        cursMapper = new CursMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(cursMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cursMapper.fromId(null)).isNull();
    }
}
