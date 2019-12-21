package com.publicpmr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.publicpmr.web.rest.TestUtil;

public class TaxiTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taxi.class);
        Taxi taxi1 = new Taxi();
        taxi1.setId(1L);
        Taxi taxi2 = new Taxi();
        taxi2.setId(taxi1.getId());
        assertThat(taxi1).isEqualTo(taxi2);
        taxi2.setId(2L);
        assertThat(taxi1).isNotEqualTo(taxi2);
        taxi1.setId(null);
        assertThat(taxi1).isNotEqualTo(taxi2);
    }
}
