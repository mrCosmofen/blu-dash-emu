package com.bludash.emulator.web.rest;

import com.bludash.emulator.EmulatorApp;
import com.bludash.emulator.domain.BluField;
import com.bludash.emulator.repository.BluFieldRepository;

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

import com.bludash.emulator.domain.enumeration.BluFieldType;
/**
 * Integration tests for the {@link BluFieldResource} REST controller.
 */
@SpringBootTest(classes = EmulatorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BluFieldResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final BluFieldType DEFAULT_DATA_TYPE = BluFieldType.ENUM;
    private static final BluFieldType UPDATED_DATA_TYPE = BluFieldType.NUMBER;

    private static final String DEFAULT_DATA_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_DATA_FORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_VALUES = "AAAAAAAAAA";
    private static final String UPDATED_VALUES = "BBBBBBBBBB";

    @Autowired
    private BluFieldRepository bluFieldRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBluFieldMockMvc;

    private BluField bluField;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluField createEntity(EntityManager em) {
        BluField bluField = new BluField()
            .key(DEFAULT_KEY)
            .label(DEFAULT_LABEL)
            .dataType(DEFAULT_DATA_TYPE)
            .dataFormat(DEFAULT_DATA_FORMAT)
            .values(DEFAULT_VALUES);
        return bluField;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluField createUpdatedEntity(EntityManager em) {
        BluField bluField = new BluField()
            .key(UPDATED_KEY)
            .label(UPDATED_LABEL)
            .dataType(UPDATED_DATA_TYPE)
            .dataFormat(UPDATED_DATA_FORMAT)
            .values(UPDATED_VALUES);
        return bluField;
    }

    @BeforeEach
    public void initTest() {
        bluField = createEntity(em);
    }

    @Test
    @Transactional
    public void createBluField() throws Exception {
        int databaseSizeBeforeCreate = bluFieldRepository.findAll().size();

        // Create the BluField
        restBluFieldMockMvc.perform(post("/api/blu-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluField)))
            .andExpect(status().isCreated());

        // Validate the BluField in the database
        List<BluField> bluFieldList = bluFieldRepository.findAll();
        assertThat(bluFieldList).hasSize(databaseSizeBeforeCreate + 1);
        BluField testBluField = bluFieldList.get(bluFieldList.size() - 1);
        assertThat(testBluField.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testBluField.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testBluField.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testBluField.getDataFormat()).isEqualTo(DEFAULT_DATA_FORMAT);
        assertThat(testBluField.getValues()).isEqualTo(DEFAULT_VALUES);
    }

    @Test
    @Transactional
    public void createBluFieldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bluFieldRepository.findAll().size();

        // Create the BluField with an existing ID
        bluField.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBluFieldMockMvc.perform(post("/api/blu-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluField)))
            .andExpect(status().isBadRequest());

        // Validate the BluField in the database
        List<BluField> bluFieldList = bluFieldRepository.findAll();
        assertThat(bluFieldList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBluFields() throws Exception {
        // Initialize the database
        bluFieldRepository.saveAndFlush(bluField);

        // Get all the bluFieldList
        restBluFieldMockMvc.perform(get("/api/blu-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bluField.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dataFormat").value(hasItem(DEFAULT_DATA_FORMAT)))
            .andExpect(jsonPath("$.[*].values").value(hasItem(DEFAULT_VALUES)));
    }
    
    @Test
    @Transactional
    public void getBluField() throws Exception {
        // Initialize the database
        bluFieldRepository.saveAndFlush(bluField);

        // Get the bluField
        restBluFieldMockMvc.perform(get("/api/blu-fields/{id}", bluField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bluField.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.dataFormat").value(DEFAULT_DATA_FORMAT))
            .andExpect(jsonPath("$.values").value(DEFAULT_VALUES));
    }

    @Test
    @Transactional
    public void getNonExistingBluField() throws Exception {
        // Get the bluField
        restBluFieldMockMvc.perform(get("/api/blu-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBluField() throws Exception {
        // Initialize the database
        bluFieldRepository.saveAndFlush(bluField);

        int databaseSizeBeforeUpdate = bluFieldRepository.findAll().size();

        // Update the bluField
        BluField updatedBluField = bluFieldRepository.findById(bluField.getId()).get();
        // Disconnect from session so that the updates on updatedBluField are not directly saved in db
        em.detach(updatedBluField);
        updatedBluField
            .key(UPDATED_KEY)
            .label(UPDATED_LABEL)
            .dataType(UPDATED_DATA_TYPE)
            .dataFormat(UPDATED_DATA_FORMAT)
            .values(UPDATED_VALUES);

        restBluFieldMockMvc.perform(put("/api/blu-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBluField)))
            .andExpect(status().isOk());

        // Validate the BluField in the database
        List<BluField> bluFieldList = bluFieldRepository.findAll();
        assertThat(bluFieldList).hasSize(databaseSizeBeforeUpdate);
        BluField testBluField = bluFieldList.get(bluFieldList.size() - 1);
        assertThat(testBluField.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testBluField.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testBluField.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testBluField.getDataFormat()).isEqualTo(UPDATED_DATA_FORMAT);
        assertThat(testBluField.getValues()).isEqualTo(UPDATED_VALUES);
    }

    @Test
    @Transactional
    public void updateNonExistingBluField() throws Exception {
        int databaseSizeBeforeUpdate = bluFieldRepository.findAll().size();

        // Create the BluField

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBluFieldMockMvc.perform(put("/api/blu-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluField)))
            .andExpect(status().isBadRequest());

        // Validate the BluField in the database
        List<BluField> bluFieldList = bluFieldRepository.findAll();
        assertThat(bluFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBluField() throws Exception {
        // Initialize the database
        bluFieldRepository.saveAndFlush(bluField);

        int databaseSizeBeforeDelete = bluFieldRepository.findAll().size();

        // Delete the bluField
        restBluFieldMockMvc.perform(delete("/api/blu-fields/{id}", bluField.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BluField> bluFieldList = bluFieldRepository.findAll();
        assertThat(bluFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
