package com.bludash.emulator.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bludash.emulator.web.rest.TestUtil;

public class BluFieldNumberValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BluFieldNumberValue.class);
        BluFieldNumberValue bluFieldNumberValue1 = new BluFieldNumberValue();
        bluFieldNumberValue1.setId(1L);
        BluFieldNumberValue bluFieldNumberValue2 = new BluFieldNumberValue();
        bluFieldNumberValue2.setId(bluFieldNumberValue1.getId());
        assertThat(bluFieldNumberValue1).isEqualTo(bluFieldNumberValue2);
        bluFieldNumberValue2.setId(2L);
        assertThat(bluFieldNumberValue1).isNotEqualTo(bluFieldNumberValue2);
        bluFieldNumberValue1.setId(null);
        assertThat(bluFieldNumberValue1).isNotEqualTo(bluFieldNumberValue2);
    }
}
