package com.bludash.emulator.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bludash.emulator.web.rest.TestUtil;

public class QueryDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QueryData.class);
        QueryData queryData1 = new QueryData();
        queryData1.setId(1L);
        QueryData queryData2 = new QueryData();
        queryData2.setId(queryData1.getId());
        assertThat(queryData1).isEqualTo(queryData2);
        queryData2.setId(2L);
        assertThat(queryData1).isNotEqualTo(queryData2);
        queryData1.setId(null);
        assertThat(queryData1).isNotEqualTo(queryData2);
    }
}
