package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class QueryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Query.class);
        Query query1 = new Query();
        query1.setId(1L);
        Query query2 = new Query();
        query2.setId(query1.getId());
        assertThat(query1).isEqualTo(query2);
        query2.setId(2L);
        assertThat(query1).isNotEqualTo(query2);
        query1.setId(null);
        assertThat(query1).isNotEqualTo(query2);
    }
}
