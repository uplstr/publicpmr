package com.publicpmr.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.publicpmr.web.rest.TestUtil;

public class CursDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CursDTO.class);
        CursDTO cursDTO1 = new CursDTO();
        cursDTO1.setId(1L);
        CursDTO cursDTO2 = new CursDTO();
        assertThat(cursDTO1).isNotEqualTo(cursDTO2);
        cursDTO2.setId(cursDTO1.getId());
        assertThat(cursDTO1).isEqualTo(cursDTO2);
        cursDTO2.setId(2L);
        assertThat(cursDTO1).isNotEqualTo(cursDTO2);
        cursDTO1.setId(null);
        assertThat(cursDTO1).isNotEqualTo(cursDTO2);
    }
}
