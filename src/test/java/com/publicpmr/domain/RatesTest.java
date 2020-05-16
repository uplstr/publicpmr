package com.publicpmr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.publicpmr.web.rest.TestUtil;

public class RatesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rates.class);
        Rates rates1 = new Rates();
        rates1.setId(1L);
        Rates rates2 = new Rates();
        rates2.setId(rates1.getId());
        assertThat(rates1).isEqualTo(rates2);
        rates2.setId(2L);
        assertThat(rates1).isNotEqualTo(rates2);
        rates1.setId(null);
        assertThat(rates1).isNotEqualTo(rates2);
    }
}
