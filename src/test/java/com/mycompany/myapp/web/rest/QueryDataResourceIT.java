package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.EmulatorApp;
import com.mycompany.myapp.domain.QueryData;
import com.mycompany.myapp.repository.QueryDataRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link QueryDataResource} REST controller.
 */
@SpringBootTest(classes = EmulatorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class QueryDataResourceIT {

    private static final String DEFAULT_RECORD_ID = "AAAAAAAAAA";
    private static final String UPDATED_RECORD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private QueryDataRepository queryDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQueryDataMockMvc;

    private QueryData queryData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QueryData createEntity(EntityManager em) {
        QueryData queryData = new QueryData()
            .recordId(DEFAULT_RECORD_ID)
            .value(DEFAULT_VALUE);
        return queryData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QueryData createUpdatedEntity(EntityManager em) {
        QueryData queryData = new QueryData()
            .recordId(UPDATED_RECORD_ID)
            .value(UPDATED_VALUE);
        return queryData;
    }

    @BeforeEach
    public void initTest() {
        queryData = createEntity(em);
    }

    @Test
    @Transactional
    public void createQueryData() throws Exception {
        int databaseSizeBeforeCreate = queryDataRepository.findAll().size();

        // Create the QueryData
        restQueryDataMockMvc.perform(post("/api/query-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(queryData)))
            .andExpect(status().isCreated());

        // Validate the QueryData in the database
        List<QueryData> queryDataList = queryDataRepository.findAll();
        assertThat(queryDataList).hasSize(databaseSizeBeforeCreate + 1);
        QueryData testQueryData = queryDataList.get(queryDataList.size() - 1);
        assertThat(testQueryData.getRecordId()).isEqualTo(DEFAULT_RECORD_ID);
        assertThat(testQueryData.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createQueryDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = queryDataRepository.findAll().size();

        // Create the QueryData with an existing ID
        queryData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQueryDataMockMvc.perform(post("/api/query-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(queryData)))
            .andExpect(status().isBadRequest());

        // Validate the QueryData in the database
        List<QueryData> queryDataList = queryDataRepository.findAll();
        assertThat(queryDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQueryData() throws Exception {
        // Initialize the database
        queryDataRepository.saveAndFlush(queryData);

        // Get all the queryDataList
        restQueryDataMockMvc.perform(get("/api/query-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queryData.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordId").value(hasItem(DEFAULT_RECORD_ID)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getQueryData() throws Exception {
        // Initialize the database
        queryDataRepository.saveAndFlush(queryData);

        // Get the queryData
        restQueryDataMockMvc.perform(get("/api/query-data/{id}", queryData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(queryData.getId().intValue()))
            .andExpect(jsonPath("$.recordId").value(DEFAULT_RECORD_ID))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingQueryData() throws Exception {
        // Get the queryData
        restQueryDataMockMvc.perform(get("/api/query-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQueryData() throws Exception {
        // Initialize the database
        queryDataRepository.saveAndFlush(queryData);

        int databaseSizeBeforeUpdate = queryDataRepository.findAll().size();

        // Update the queryData
        QueryData updatedQueryData = queryDataRepository.findById(queryData.getId()).get();
        // Disconnect from session so that the updates on updatedQueryData are not directly saved in db
        em.detach(updatedQueryData);
        updatedQueryData
            .recordId(UPDATED_RECORD_ID)
            .value(UPDATED_VALUE);

        restQueryDataMockMvc.perform(put("/api/query-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQueryData)))
            .andExpect(status().isOk());

        // Validate the QueryData in the database
        List<QueryData> queryDataList = queryDataRepository.findAll();
        assertThat(queryDataList).hasSize(databaseSizeBeforeUpdate);
        QueryData testQueryData = queryDataList.get(queryDataList.size() - 1);
        assertThat(testQueryData.getRecordId()).isEqualTo(UPDATED_RECORD_ID);
        assertThat(testQueryData.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingQueryData() throws Exception {
        int databaseSizeBeforeUpdate = queryDataRepository.findAll().size();

        // Create the QueryData

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQueryDataMockMvc.perform(put("/api/query-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(queryData)))
            .andExpect(status().isBadRequest());

        // Validate the QueryData in the database
        List<QueryData> queryDataList = queryDataRepository.findAll();
        assertThat(queryDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQueryData() throws Exception {
        // Initialize the database
        queryDataRepository.saveAndFlush(queryData);

        int databaseSizeBeforeDelete = queryDataRepository.findAll().size();

        // Delete the queryData
        restQueryDataMockMvc.perform(delete("/api/query-data/{id}", queryData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QueryData> queryDataList = queryDataRepository.findAll();
        assertThat(queryDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
