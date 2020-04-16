package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.EmulatorApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.DataModel;
import com.mycompany.myapp.repository.DataModelRepository;

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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Status;
/**
 * Integration tests for the {@link DataModelResource} REST controller.
 */
@SpringBootTest(classes = { EmulatorApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class DataModelResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_DATA_FORMAT = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_LENGTH = 1;
    private static final Integer UPDATED_MAX_LENGTH = 2;

    private static final Integer DEFAULT_PRECISION = 1;
    private static final Integer UPDATED_PRECISION = 2;

    private static final Status DEFAULT_VALUES = Status.WITHDRAWN;
    private static final Status UPDATED_VALUES = Status.CARRIER_REJECTED;

    @Autowired
    private DataModelRepository dataModelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataModelMockMvc;

    private DataModel dataModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataModel createEntity(EntityManager em) {
        DataModel dataModel = new DataModel()
            .key(DEFAULT_KEY)
            .label(DEFAULT_LABEL)
            .dataFormat(DEFAULT_DATA_FORMAT)
            .maxLength(DEFAULT_MAX_LENGTH)
            .precision(DEFAULT_PRECISION)
            .values(DEFAULT_VALUES);
        return dataModel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataModel createUpdatedEntity(EntityManager em) {
        DataModel dataModel = new DataModel()
            .key(UPDATED_KEY)
            .label(UPDATED_LABEL)
            .dataFormat(UPDATED_DATA_FORMAT)
            .maxLength(UPDATED_MAX_LENGTH)
            .precision(UPDATED_PRECISION)
            .values(UPDATED_VALUES);
        return dataModel;
    }

    @BeforeEach
    public void initTest() {
        dataModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataModel() throws Exception {
        int databaseSizeBeforeCreate = dataModelRepository.findAll().size();

        // Create the DataModel
        restDataModelMockMvc.perform(post("/api/data-models").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataModel)))
            .andExpect(status().isCreated());

        // Validate the DataModel in the database
        List<DataModel> dataModelList = dataModelRepository.findAll();
        assertThat(dataModelList).hasSize(databaseSizeBeforeCreate + 1);
        DataModel testDataModel = dataModelList.get(dataModelList.size() - 1);
        assertThat(testDataModel.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testDataModel.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testDataModel.getDataFormat()).isEqualTo(DEFAULT_DATA_FORMAT);
        assertThat(testDataModel.getMaxLength()).isEqualTo(DEFAULT_MAX_LENGTH);
        assertThat(testDataModel.getPrecision()).isEqualTo(DEFAULT_PRECISION);
        assertThat(testDataModel.getValues()).isEqualTo(DEFAULT_VALUES);
    }

    @Test
    @Transactional
    public void createDataModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataModelRepository.findAll().size();

        // Create the DataModel with an existing ID
        dataModel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataModelMockMvc.perform(post("/api/data-models").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataModel)))
            .andExpect(status().isBadRequest());

        // Validate the DataModel in the database
        List<DataModel> dataModelList = dataModelRepository.findAll();
        assertThat(dataModelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDataModels() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        // Get all the dataModelList
        restDataModelMockMvc.perform(get("/api/data-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].dataFormat").value(hasItem(DEFAULT_DATA_FORMAT)))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)))
            .andExpect(jsonPath("$.[*].precision").value(hasItem(DEFAULT_PRECISION)))
            .andExpect(jsonPath("$.[*].values").value(hasItem(DEFAULT_VALUES.toString())));
    }
    
    @Test
    @Transactional
    public void getDataModel() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        // Get the dataModel
        restDataModelMockMvc.perform(get("/api/data-models/{id}", dataModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataModel.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.dataFormat").value(DEFAULT_DATA_FORMAT))
            .andExpect(jsonPath("$.maxLength").value(DEFAULT_MAX_LENGTH))
            .andExpect(jsonPath("$.precision").value(DEFAULT_PRECISION))
            .andExpect(jsonPath("$.values").value(DEFAULT_VALUES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataModel() throws Exception {
        // Get the dataModel
        restDataModelMockMvc.perform(get("/api/data-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataModel() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        int databaseSizeBeforeUpdate = dataModelRepository.findAll().size();

        // Update the dataModel
        DataModel updatedDataModel = dataModelRepository.findById(dataModel.getId()).get();
        // Disconnect from session so that the updates on updatedDataModel are not directly saved in db
        em.detach(updatedDataModel);
        updatedDataModel
            .key(UPDATED_KEY)
            .label(UPDATED_LABEL)
            .dataFormat(UPDATED_DATA_FORMAT)
            .maxLength(UPDATED_MAX_LENGTH)
            .precision(UPDATED_PRECISION)
            .values(UPDATED_VALUES);

        restDataModelMockMvc.perform(put("/api/data-models").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataModel)))
            .andExpect(status().isOk());

        // Validate the DataModel in the database
        List<DataModel> dataModelList = dataModelRepository.findAll();
        assertThat(dataModelList).hasSize(databaseSizeBeforeUpdate);
        DataModel testDataModel = dataModelList.get(dataModelList.size() - 1);
        assertThat(testDataModel.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testDataModel.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testDataModel.getDataFormat()).isEqualTo(UPDATED_DATA_FORMAT);
        assertThat(testDataModel.getMaxLength()).isEqualTo(UPDATED_MAX_LENGTH);
        assertThat(testDataModel.getPrecision()).isEqualTo(UPDATED_PRECISION);
        assertThat(testDataModel.getValues()).isEqualTo(UPDATED_VALUES);
    }

    @Test
    @Transactional
    public void updateNonExistingDataModel() throws Exception {
        int databaseSizeBeforeUpdate = dataModelRepository.findAll().size();

        // Create the DataModel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataModelMockMvc.perform(put("/api/data-models").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataModel)))
            .andExpect(status().isBadRequest());

        // Validate the DataModel in the database
        List<DataModel> dataModelList = dataModelRepository.findAll();
        assertThat(dataModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataModel() throws Exception {
        // Initialize the database
        dataModelRepository.saveAndFlush(dataModel);

        int databaseSizeBeforeDelete = dataModelRepository.findAll().size();

        // Delete the dataModel
        restDataModelMockMvc.perform(delete("/api/data-models/{id}", dataModel.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataModel> dataModelList = dataModelRepository.findAll();
        assertThat(dataModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
