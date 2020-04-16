package com.bludash.emulator.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bludash.emulator.web.rest.TestUtil;

public class BluFormTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BluForm.class);
        BluForm bluForm1 = new BluForm();
        bluForm1.setId(1L);
        BluForm bluForm2 = new BluForm();
        bluForm2.setId(bluForm1.getId());
        assertThat(bluForm1).isEqualTo(bluForm2);
        bluForm2.setId(2L);
        assertThat(bluForm1).isNotEqualTo(bluForm2);
        bluForm1.setId(null);
        assertThat(bluForm1).isNotEqualTo(bluForm2);
    }
}
