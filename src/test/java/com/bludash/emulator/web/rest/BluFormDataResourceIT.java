package com.bludash.emulator.web.rest;

import com.bludash.emulator.EmulatorApp;
import com.bludash.emulator.domain.BluFormData;
import com.bludash.emulator.repository.BluFormDataRepository;

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
 * Integration tests for the {@link BluFormDataResource} REST controller.
 */
@SpringBootTest(classes = EmulatorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BluFormDataResourceIT {

    private static final Long DEFAULT_RETRIEVED = 1L;
    private static final Long UPDATED_RETRIEVED = 2L;

    @Autowired
    private BluFormDataRepository bluFormDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBluFormDataMockMvc;

    private BluFormData bluFormData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluFormData createEntity(EntityManager em) {
        BluFormData bluFormData = new BluFormData()
            .retrieved(DEFAULT_RETRIEVED);
        return bluFormData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluFormData createUpdatedEntity(EntityManager em) {
        BluFormData bluFormData = new BluFormData()
            .retrieved(UPDATED_RETRIEVED);
        return bluFormData;
    }

    @BeforeEach
    public void initTest() {
        bluFormData = createEntity(em);
    }

    @Test
    @Transactional
    public void createBluFormData() throws Exception {
        int databaseSizeBeforeCreate = bluFormDataRepository.findAll().size();

        // Create the BluFormData
        restBluFormDataMockMvc.perform(post("/api/blu-form-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFormData)))
            .andExpect(status().isCreated());

        // Validate the BluFormData in the database
        List<BluFormData> bluFormDataList = bluFormDataRepository.findAll();
        assertThat(bluFormDataList).hasSize(databaseSizeBeforeCreate + 1);
        BluFormData testBluFormData = bluFormDataList.get(bluFormDataList.size() - 1);
        assertThat(testBluFormData.getRetrieved()).isEqualTo(DEFAULT_RETRIEVED);
    }

    @Test
    @Transactional
    public void createBluFormDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bluFormDataRepository.findAll().size();

        // Create the BluFormData with an existing ID
        bluFormData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBluFormDataMockMvc.perform(post("/api/blu-form-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFormData)))
            .andExpect(status().isBadRequest());

        // Validate the BluFormData in the database
        List<BluFormData> bluFormDataList = bluFormDataRepository.findAll();
        assertThat(bluFormDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBluFormData() throws Exception {
        // Initialize the database
        bluFormDataRepository.saveAndFlush(bluFormData);

        // Get all the bluFormDataList
        restBluFormDataMockMvc.perform(get("/api/blu-form-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bluFormData.getId().intValue())))
            .andExpect(jsonPath("$.[*].retrieved").value(hasItem(DEFAULT_RETRIEVED.intValue())));
    }
    
    @Test
    @Transactional
    public void getBluFormData() throws Exception {
        // Initialize the database
        bluFormDataRepository.saveAndFlush(bluFormData);

        // Get the bluFormData
        restBluFormDataMockMvc.perform(get("/api/blu-form-data/{id}", bluFormData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bluFormData.getId().intValue()))
            .andExpect(jsonPath("$.retrieved").value(DEFAULT_RETRIEVED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBluFormData() throws Exception {
        // Get the bluFormData
        restBluFormDataMockMvc.perform(get("/api/blu-form-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBluFormData() throws Exception {
        // Initialize the database
        bluFormDataRepository.saveAndFlush(bluFormData);

        int databaseSizeBeforeUpdate = bluFormDataRepository.findAll().size();

        // Update the bluFormData
        BluFormData updatedBluFormData = bluFormDataRepository.findById(bluFormData.getId()).get();
        // Disconnect from session so that the updates on updatedBluFormData are not directly saved in db
        em.detach(updatedBluFormData);
        updatedBluFormData
            .retrieved(UPDATED_RETRIEVED);

        restBluFormDataMockMvc.perform(put("/api/blu-form-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBluFormData)))
            .andExpect(status().isOk());

        // Validate the BluFormData in the database
        List<BluFormData> bluFormDataList = bluFormDataRepository.findAll();
        assertThat(bluFormDataList).hasSize(databaseSizeBeforeUpdate);
        BluFormData testBluFormData = bluFormDataList.get(bluFormDataList.size() - 1);
        assertThat(testBluFormData.getRetrieved()).isEqualTo(UPDATED_RETRIEVED);
    }

    @Test
    @Transactional
    public void updateNonExistingBluFormData() throws Exception {
        int databaseSizeBeforeUpdate = bluFormDataRepository.findAll().size();

        // Create the BluFormData

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBluFormDataMockMvc.perform(put("/api/blu-form-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFormData)))
            .andExpect(status().isBadRequest());

        // Validate the BluFormData in the database
        List<BluFormData> bluFormDataList = bluFormDataRepository.findAll();
        assertThat(bluFormDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBluFormData() throws Exception {
        // Initialize the database
        bluFormDataRepository.saveAndFlush(bluFormData);

        int databaseSizeBeforeDelete = bluFormDataRepository.findAll().size();

        // Delete the bluFormData
        restBluFormDataMockMvc.perform(delete("/api/blu-form-data/{id}", bluFormData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BluFormData> bluFormDataList = bluFormDataRepository.findAll();
        assertThat(bluFormDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
