package com.bludash.emulator.web.rest;

import com.bludash.emulator.EmulatorApp;
import com.bludash.emulator.domain.DataSet;
import com.bludash.emulator.repository.DataSetRepository;

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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DataSetResource} REST controller.
 */
@SpringBootTest(classes = EmulatorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DataSetResourceIT {

    private static final UUID DEFAULT_KEY = UUID.randomUUID();
    private static final UUID UPDATED_KEY = UUID.randomUUID();

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_LANDING_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANDING_PAGE = "BBBBBBBBBB";

    @Autowired
    private DataSetRepository dataSetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataSetMockMvc;

    private DataSet dataSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataSet createEntity(EntityManager em) {
        DataSet dataSet = new DataSet()
            .key(DEFAULT_KEY)
            .label(DEFAULT_LABEL)
            .landingPage(DEFAULT_LANDING_PAGE);
        return dataSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataSet createUpdatedEntity(EntityManager em) {
        DataSet dataSet = new DataSet()
            .key(UPDATED_KEY)
            .label(UPDATED_LABEL)
            .landingPage(UPDATED_LANDING_PAGE);
        return dataSet;
    }

    @BeforeEach
    public void initTest() {
        dataSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataSet() throws Exception {
        int databaseSizeBeforeCreate = dataSetRepository.findAll().size();

        // Create the DataSet
        restDataSetMockMvc.perform(post("/api/data-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataSet)))
            .andExpect(status().isCreated());

        // Validate the DataSet in the database
        List<DataSet> dataSetList = dataSetRepository.findAll();
        assertThat(dataSetList).hasSize(databaseSizeBeforeCreate + 1);
        DataSet testDataSet = dataSetList.get(dataSetList.size() - 1);
        assertThat(testDataSet.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testDataSet.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testDataSet.getLandingPage()).isEqualTo(DEFAULT_LANDING_PAGE);
    }

    @Test
    @Transactional
    public void createDataSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataSetRepository.findAll().size();

        // Create the DataSet with an existing ID
        dataSet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataSetMockMvc.perform(post("/api/data-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataSet)))
            .andExpect(status().isBadRequest());

        // Validate the DataSet in the database
        List<DataSet> dataSetList = dataSetRepository.findAll();
        assertThat(dataSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDataSets() throws Exception {
        // Initialize the database
        dataSetRepository.saveAndFlush(dataSet);

        // Get all the dataSetList
        restDataSetMockMvc.perform(get("/api/data-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].landingPage").value(hasItem(DEFAULT_LANDING_PAGE)));
    }
    
    @Test
    @Transactional
    public void getDataSet() throws Exception {
        // Initialize the database
        dataSetRepository.saveAndFlush(dataSet);

        // Get the dataSet
        restDataSetMockMvc.perform(get("/api/data-sets/{id}", dataSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataSet.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.landingPage").value(DEFAULT_LANDING_PAGE));
    }

    @Test
    @Transactional
    public void getNonExistingDataSet() throws Exception {
        // Get the dataSet
        restDataSetMockMvc.perform(get("/api/data-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataSet() throws Exception {
        // Initialize the database
        dataSetRepository.saveAndFlush(dataSet);

        int databaseSizeBeforeUpdate = dataSetRepository.findAll().size();

        // Update the dataSet
        DataSet updatedDataSet = dataSetRepository.findById(dataSet.getId()).get();
        // Disconnect from session so that the updates on updatedDataSet are not directly saved in db
        em.detach(updatedDataSet);
        updatedDataSet
            .key(UPDATED_KEY)
            .label(UPDATED_LABEL)
            .landingPage(UPDATED_LANDING_PAGE);

        restDataSetMockMvc.perform(put("/api/data-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataSet)))
            .andExpect(status().isOk());

        // Validate the DataSet in the database
        List<DataSet> dataSetList = dataSetRepository.findAll();
        assertThat(dataSetList).hasSize(databaseSizeBeforeUpdate);
        DataSet testDataSet = dataSetList.get(dataSetList.size() - 1);
        assertThat(testDataSet.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testDataSet.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testDataSet.getLandingPage()).isEqualTo(UPDATED_LANDING_PAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingDataSet() throws Exception {
        int databaseSizeBeforeUpdate = dataSetRepository.findAll().size();

        // Create the DataSet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataSetMockMvc.perform(put("/api/data-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataSet)))
            .andExpect(status().isBadRequest());

        // Validate the DataSet in the database
        List<DataSet> dataSetList = dataSetRepository.findAll();
        assertThat(dataSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataSet() throws Exception {
        // Initialize the database
        dataSetRepository.saveAndFlush(dataSet);

        int databaseSizeBeforeDelete = dataSetRepository.findAll().size();

        // Delete the dataSet
        restDataSetMockMvc.perform(delete("/api/data-sets/{id}", dataSet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataSet> dataSetList = dataSetRepository.findAll();
        assertThat(dataSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
