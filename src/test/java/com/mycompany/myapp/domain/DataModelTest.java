package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DataModelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataModel.class);
        DataModel dataModel1 = new DataModel();
        dataModel1.setId(1L);
        DataModel dataModel2 = new DataModel();
        dataModel2.setId(dataModel1.getId());
        assertThat(dataModel1).isEqualTo(dataModel2);
        dataModel2.setId(2L);
        assertThat(dataModel1).isNotEqualTo(dataModel2);
        dataModel1.setId(null);
        assertThat(dataModel1).isNotEqualTo(dataModel2);
    }
}
