package com.bludash.emulator.web.rest;

import com.bludash.emulator.EmulatorApp;
import com.bludash.emulator.domain.BluFieldNumberValue;
import com.bludash.emulator.repository.BluFieldNumberValueRepository;

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
 * Integration tests for the {@link BluFieldNumberValueResource} REST controller.
 */
@SpringBootTest(classes = EmulatorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BluFieldNumberValueResourceIT {

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;

    @Autowired
    private BluFieldNumberValueRepository bluFieldNumberValueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBluFieldNumberValueMockMvc;

    private BluFieldNumberValue bluFieldNumberValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluFieldNumberValue createEntity(EntityManager em) {
        BluFieldNumberValue bluFieldNumberValue = new BluFieldNumberValue()
            .value(DEFAULT_VALUE);
        return bluFieldNumberValue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluFieldNumberValue createUpdatedEntity(EntityManager em) {
        BluFieldNumberValue bluFieldNumberValue = new BluFieldNumberValue()
            .value(UPDATED_VALUE);
        return bluFieldNumberValue;
    }

    @BeforeEach
    public void initTest() {
        bluFieldNumberValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createBluFieldNumberValue() throws Exception {
        int databaseSizeBeforeCreate = bluFieldNumberValueRepository.findAll().size();

        // Create the BluFieldNumberValue
        restBluFieldNumberValueMockMvc.perform(post("/api/blu-field-number-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldNumberValue)))
            .andExpect(status().isCreated());

        // Validate the BluFieldNumberValue in the database
        List<BluFieldNumberValue> bluFieldNumberValueList = bluFieldNumberValueRepository.findAll();
        assertThat(bluFieldNumberValueList).hasSize(databaseSizeBeforeCreate + 1);
        BluFieldNumberValue testBluFieldNumberValue = bluFieldNumberValueList.get(bluFieldNumberValueList.size() - 1);
        assertThat(testBluFieldNumberValue.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createBluFieldNumberValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bluFieldNumberValueRepository.findAll().size();

        // Create the BluFieldNumberValue with an existing ID
        bluFieldNumberValue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBluFieldNumberValueMockMvc.perform(post("/api/blu-field-number-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldNumberValue)))
            .andExpect(status().isBadRequest());

        // Validate the BluFieldNumberValue in the database
        List<BluFieldNumberValue> bluFieldNumberValueList = bluFieldNumberValueRepository.findAll();
        assertThat(bluFieldNumberValueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBluFieldNumberValues() throws Exception {
        // Initialize the database
        bluFieldNumberValueRepository.saveAndFlush(bluFieldNumberValue);

        // Get all the bluFieldNumberValueList
        restBluFieldNumberValueMockMvc.perform(get("/api/blu-field-number-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bluFieldNumberValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getBluFieldNumberValue() throws Exception {
        // Initialize the database
        bluFieldNumberValueRepository.saveAndFlush(bluFieldNumberValue);

        // Get the bluFieldNumberValue
        restBluFieldNumberValueMockMvc.perform(get("/api/blu-field-number-values/{id}", bluFieldNumberValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bluFieldNumberValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBluFieldNumberValue() throws Exception {
        // Get the bluFieldNumberValue
        restBluFieldNumberValueMockMvc.perform(get("/api/blu-field-number-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBluFieldNumberValue() throws Exception {
        // Initialize the database
        bluFieldNumberValueRepository.saveAndFlush(bluFieldNumberValue);

        int databaseSizeBeforeUpdate = bluFieldNumberValueRepository.findAll().size();

        // Update the bluFieldNumberValue
        BluFieldNumberValue updatedBluFieldNumberValue = bluFieldNumberValueRepository.findById(bluFieldNumberValue.getId()).get();
        // Disconnect from session so that the updates on updatedBluFieldNumberValue are not directly saved in db
        em.detach(updatedBluFieldNumberValue);
        updatedBluFieldNumberValue
            .value(UPDATED_VALUE);

        restBluFieldNumberValueMockMvc.perform(put("/api/blu-field-number-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBluFieldNumberValue)))
            .andExpect(status().isOk());

        // Validate the BluFieldNumberValue in the database
        List<BluFieldNumberValue> bluFieldNumberValueList = bluFieldNumberValueRepository.findAll();
        assertThat(bluFieldNumberValueList).hasSize(databaseSizeBeforeUpdate);
        BluFieldNumberValue testBluFieldNumberValue = bluFieldNumberValueList.get(bluFieldNumberValueList.size() - 1);
        assertThat(testBluFieldNumberValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingBluFieldNumberValue() throws Exception {
        int databaseSizeBeforeUpdate = bluFieldNumberValueRepository.findAll().size();

        // Create the BluFieldNumberValue

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBluFieldNumberValueMockMvc.perform(put("/api/blu-field-number-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldNumberValue)))
            .andExpect(status().isBadRequest());

        // Validate the BluFieldNumberValue in the database
        List<BluFieldNumberValue> bluFieldNumberValueList = bluFieldNumberValueRepository.findAll();
        assertThat(bluFieldNumberValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBluFieldNumberValue() throws Exception {
        // Initialize the database
        bluFieldNumberValueRepository.saveAndFlush(bluFieldNumberValue);

        int databaseSizeBeforeDelete = bluFieldNumberValueRepository.findAll().size();

        // Delete the bluFieldNumberValue
        restBluFieldNumberValueMockMvc.perform(delete("/api/blu-field-number-values/{id}", bluFieldNumberValue.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BluFieldNumberValue> bluFieldNumberValueList = bluFieldNumberValueRepository.findAll();
        assertThat(bluFieldNumberValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
