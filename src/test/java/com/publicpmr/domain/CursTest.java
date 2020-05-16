package com.publicpmr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.publicpmr.web.rest.TestUtil;

public class CursTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Curs.class);
        Curs curs1 = new Curs();
        curs1.setId(1L);
        Curs curs2 = new Curs();
        curs2.setId(curs1.getId());
        assertThat(curs1).isEqualTo(curs2);
        curs2.setId(2L);
        assertThat(curs1).isNotEqualTo(curs2);
        curs1.setId(null);
        assertThat(curs1).isNotEqualTo(curs2);
    }
}
