package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BluFieldValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BluFieldValue.class);
        BluFieldValue bluFieldValue1 = new BluFieldValue();
        bluFieldValue1.setId(1L);
        BluFieldValue bluFieldValue2 = new BluFieldValue();
        bluFieldValue2.setId(bluFieldValue1.getId());
        assertThat(bluFieldValue1).isEqualTo(bluFieldValue2);
        bluFieldValue2.setId(2L);
        assertThat(bluFieldValue1).isNotEqualTo(bluFieldValue2);
        bluFieldValue1.setId(null);
        assertThat(bluFieldValue1).isNotEqualTo(bluFieldValue2);
    }
}
