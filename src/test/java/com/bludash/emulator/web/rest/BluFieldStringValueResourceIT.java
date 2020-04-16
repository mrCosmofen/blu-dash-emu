package com.bludash.emulator.web.rest;

import com.bludash.emulator.EmulatorApp;
import com.bludash.emulator.domain.BluFieldStringValue;
import com.bludash.emulator.repository.BluFieldStringValueRepository;

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
 * Integration tests for the {@link BluFieldStringValueResource} REST controller.
 */
@SpringBootTest(classes = EmulatorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BluFieldStringValueResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private BluFieldStringValueRepository bluFieldStringValueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBluFieldStringValueMockMvc;

    private BluFieldStringValue bluFieldStringValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluFieldStringValue createEntity(EntityManager em) {
        BluFieldStringValue bluFieldStringValue = new BluFieldStringValue()
            .value(DEFAULT_VALUE);
        return bluFieldStringValue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluFieldStringValue createUpdatedEntity(EntityManager em) {
        BluFieldStringValue bluFieldStringValue = new BluFieldStringValue()
            .value(UPDATED_VALUE);
        return bluFieldStringValue;
    }

    @BeforeEach
    public void initTest() {
        bluFieldStringValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createBluFieldStringValue() throws Exception {
        int databaseSizeBeforeCreate = bluFieldStringValueRepository.findAll().size();

        // Create the BluFieldStringValue
        restBluFieldStringValueMockMvc.perform(post("/api/blu-field-string-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldStringValue)))
            .andExpect(status().isCreated());

        // Validate the BluFieldStringValue in the database
        List<BluFieldStringValue> bluFieldStringValueList = bluFieldStringValueRepository.findAll();
        assertThat(bluFieldStringValueList).hasSize(databaseSizeBeforeCreate + 1);
        BluFieldStringValue testBluFieldStringValue = bluFieldStringValueList.get(bluFieldStringValueList.size() - 1);
        assertThat(testBluFieldStringValue.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createBluFieldStringValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bluFieldStringValueRepository.findAll().size();

        // Create the BluFieldStringValue with an existing ID
        bluFieldStringValue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBluFieldStringValueMockMvc.perform(post("/api/blu-field-string-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldStringValue)))
            .andExpect(status().isBadRequest());

        // Validate the BluFieldStringValue in the database
        List<BluFieldStringValue> bluFieldStringValueList = bluFieldStringValueRepository.findAll();
        assertThat(bluFieldStringValueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBluFieldStringValues() throws Exception {
        // Initialize the database
        bluFieldStringValueRepository.saveAndFlush(bluFieldStringValue);

        // Get all the bluFieldStringValueList
        restBluFieldStringValueMockMvc.perform(get("/api/blu-field-string-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bluFieldStringValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getBluFieldStringValue() throws Exception {
        // Initialize the database
        bluFieldStringValueRepository.saveAndFlush(bluFieldStringValue);

        // Get the bluFieldStringValue
        restBluFieldStringValueMockMvc.perform(get("/api/blu-field-string-values/{id}", bluFieldStringValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bluFieldStringValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingBluFieldStringValue() throws Exception {
        // Get the bluFieldStringValue
        restBluFieldStringValueMockMvc.perform(get("/api/blu-field-string-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBluFieldStringValue() throws Exception {
        // Initialize the database
        bluFieldStringValueRepository.saveAndFlush(bluFieldStringValue);

        int databaseSizeBeforeUpdate = bluFieldStringValueRepository.findAll().size();

        // Update the bluFieldStringValue
        BluFieldStringValue updatedBluFieldStringValue = bluFieldStringValueRepository.findById(bluFieldStringValue.getId()).get();
        // Disconnect from session so that the updates on updatedBluFieldStringValue are not directly saved in db
        em.detach(updatedBluFieldStringValue);
        updatedBluFieldStringValue
            .value(UPDATED_VALUE);

        restBluFieldStringValueMockMvc.perform(put("/api/blu-field-string-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBluFieldStringValue)))
            .andExpect(status().isOk());

        // Validate the BluFieldStringValue in the database
        List<BluFieldStringValue> bluFieldStringValueList = bluFieldStringValueRepository.findAll();
        assertThat(bluFieldStringValueList).hasSize(databaseSizeBeforeUpdate);
        BluFieldStringValue testBluFieldStringValue = bluFieldStringValueList.get(bluFieldStringValueList.size() - 1);
        assertThat(testBluFieldStringValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingBluFieldStringValue() throws Exception {
        int databaseSizeBeforeUpdate = bluFieldStringValueRepository.findAll().size();

        // Create the BluFieldStringValue

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBluFieldStringValueMockMvc.perform(put("/api/blu-field-string-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldStringValue)))
            .andExpect(status().isBadRequest());

        // Validate the BluFieldStringValue in the database
        List<BluFieldStringValue> bluFieldStringValueList = bluFieldStringValueRepository.findAll();
        assertThat(bluFieldStringValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBluFieldStringValue() throws Exception {
        // Initialize the database
        bluFieldStringValueRepository.saveAndFlush(bluFieldStringValue);

        int databaseSizeBeforeDelete = bluFieldStringValueRepository.findAll().size();

        // Delete the bluFieldStringValue
        restBluFieldStringValueMockMvc.perform(delete("/api/blu-field-string-values/{id}", bluFieldStringValue.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BluFieldStringValue> bluFieldStringValueList = bluFieldStringValueRepository.findAll();
        assertThat(bluFieldStringValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
