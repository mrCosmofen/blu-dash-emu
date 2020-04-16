package com.bludash.emulator.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bludash.emulator.web.rest.TestUtil;

public class BluFormDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BluFormData.class);
        BluFormData bluFormData1 = new BluFormData();
        bluFormData1.setId(1L);
        BluFormData bluFormData2 = new BluFormData();
        bluFormData2.setId(bluFormData1.getId());
        assertThat(bluFormData1).isEqualTo(bluFormData2);
        bluFormData2.setId(2L);
        assertThat(bluFormData1).isNotEqualTo(bluFormData2);
        bluFormData1.setId(null);
        assertThat(bluFormData1).isNotEqualTo(bluFormData2);
    }
}
