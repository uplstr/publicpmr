package com.publicpmr.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.publicpmr.web.rest.TestUtil;

public class RatesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatesDTO.class);
        RatesDTO ratesDTO1 = new RatesDTO();
        ratesDTO1.setId(1L);
        RatesDTO ratesDTO2 = new RatesDTO();
        assertThat(ratesDTO1).isNotEqualTo(ratesDTO2);
        ratesDTO2.setId(ratesDTO1.getId());
        assertThat(ratesDTO1).isEqualTo(ratesDTO2);
        ratesDTO2.setId(2L);
        assertThat(ratesDTO1).isNotEqualTo(ratesDTO2);
        ratesDTO1.setId(null);
        assertThat(ratesDTO1).isNotEqualTo(ratesDTO2);
    }
}
