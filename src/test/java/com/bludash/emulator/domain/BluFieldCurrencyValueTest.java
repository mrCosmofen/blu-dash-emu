package com.bludash.emulator.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bludash.emulator.web.rest.TestUtil;

public class BluFieldCurrencyValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BluFieldCurrencyValue.class);
        BluFieldCurrencyValue bluFieldCurrencyValue1 = new BluFieldCurrencyValue();
        bluFieldCurrencyValue1.setId(1L);
        BluFieldCurrencyValue bluFieldCurrencyValue2 = new BluFieldCurrencyValue();
        bluFieldCurrencyValue2.setId(bluFieldCurrencyValue1.getId());
        assertThat(bluFieldCurrencyValue1).isEqualTo(bluFieldCurrencyValue2);
        bluFieldCurrencyValue2.setId(2L);
        assertThat(bluFieldCurrencyValue1).isNotEqualTo(bluFieldCurrencyValue2);
        bluFieldCurrencyValue1.setId(null);
        assertThat(bluFieldCurrencyValue1).isNotEqualTo(bluFieldCurrencyValue2);
    }
}
