package com.bludash.emulator.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bludash.emulator.web.rest.TestUtil;

public class BluFieldStringValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BluFieldStringValue.class);
        BluFieldStringValue bluFieldStringValue1 = new BluFieldStringValue();
        bluFieldStringValue1.setId(1L);
        BluFieldStringValue bluFieldStringValue2 = new BluFieldStringValue();
        bluFieldStringValue2.setId(bluFieldStringValue1.getId());
        assertThat(bluFieldStringValue1).isEqualTo(bluFieldStringValue2);
        bluFieldStringValue2.setId(2L);
        assertThat(bluFieldStringValue1).isNotEqualTo(bluFieldStringValue2);
        bluFieldStringValue1.setId(null);
        assertThat(bluFieldStringValue1).isNotEqualTo(bluFieldStringValue2);
    }
}
