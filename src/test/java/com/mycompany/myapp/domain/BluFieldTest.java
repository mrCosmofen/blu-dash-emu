package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BluFieldTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BluField.class);
        BluField bluField1 = new BluField();
        bluField1.setId(1L);
        BluField bluField2 = new BluField();
        bluField2.setId(bluField1.getId());
        assertThat(bluField1).isEqualTo(bluField2);
        bluField2.setId(2L);
        assertThat(bluField1).isNotEqualTo(bluField2);
        bluField1.setId(null);
        assertThat(bluField1).isNotEqualTo(bluField2);
    }
}
