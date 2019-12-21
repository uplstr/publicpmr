package com.publicpmr.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.publicpmr.web.rest.TestUtil;

public class TaxiDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxiDTO.class);
        TaxiDTO taxiDTO1 = new TaxiDTO();
        taxiDTO1.setId(1L);
        TaxiDTO taxiDTO2 = new TaxiDTO();
        assertThat(taxiDTO1).isNotEqualTo(taxiDTO2);
        taxiDTO2.setId(taxiDTO1.getId());
        assertThat(taxiDTO1).isEqualTo(taxiDTO2);
        taxiDTO2.setId(2L);
        assertThat(taxiDTO1).isNotEqualTo(taxiDTO2);
        taxiDTO1.setId(null);
        assertThat(taxiDTO1).isNotEqualTo(taxiDTO2);
    }
}
