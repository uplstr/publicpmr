package com.publicpmr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.publicpmr.web.rest.TestUtil;

public class BanksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banks.class);
        Banks banks1 = new Banks();
        banks1.setId(1L);
        Banks banks2 = new Banks();
        banks2.setId(banks1.getId());
        assertThat(banks1).isEqualTo(banks2);
        banks2.setId(2L);
        assertThat(banks1).isNotEqualTo(banks2);
        banks1.setId(null);
        assertThat(banks1).isNotEqualTo(banks2);
    }
}
