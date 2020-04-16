package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.EmulatorApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.BluFieldValue;
import com.mycompany.myapp.repository.BluFieldValueRepository;

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

/**
 * Integration tests for the {@link BluFieldValueResource} REST controller.
 */
@SpringBootTest(classes = { EmulatorApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class BluFieldValueResourceIT {

    @Autowired
    private BluFieldValueRepository bluFieldValueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBluFieldValueMockMvc;

    private BluFieldValue bluFieldValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluFieldValue createEntity(EntityManager em) {
        BluFieldValue bluFieldValue = new BluFieldValue();
        return bluFieldValue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluFieldValue createUpdatedEntity(EntityManager em) {
        BluFieldValue bluFieldValue = new BluFieldValue();
        return bluFieldValue;
    }

    @BeforeEach
    public void initTest() {
        bluFieldValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createBluFieldValue() throws Exception {
        int databaseSizeBeforeCreate = bluFieldValueRepository.findAll().size();

        // Create the BluFieldValue
        restBluFieldValueMockMvc.perform(post("/api/blu-field-values").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldValue)))
            .andExpect(status().isCreated());

        // Validate the BluFieldValue in the database
        List<BluFieldValue> bluFieldValueList = bluFieldValueRepository.findAll();
        assertThat(bluFieldValueList).hasSize(databaseSizeBeforeCreate + 1);
        BluFieldValue testBluFieldValue = bluFieldValueList.get(bluFieldValueList.size() - 1);
    }

    @Test
    @Transactional
    public void createBluFieldValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bluFieldValueRepository.findAll().size();

        // Create the BluFieldValue with an existing ID
        bluFieldValue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBluFieldValueMockMvc.perform(post("/api/blu-field-values").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldValue)))
            .andExpect(status().isBadRequest());

        // Validate the BluFieldValue in the database
        List<BluFieldValue> bluFieldValueList = bluFieldValueRepository.findAll();
        assertThat(bluFieldValueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBluFieldValues() throws Exception {
        // Initialize the database
        bluFieldValueRepository.saveAndFlush(bluFieldValue);

        // Get all the bluFieldValueList
        restBluFieldValueMockMvc.perform(get("/api/blu-field-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bluFieldValue.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBluFieldValue() throws Exception {
        // Initialize the database
        bluFieldValueRepository.saveAndFlush(bluFieldValue);

        // Get the bluFieldValue
        restBluFieldValueMockMvc.perform(get("/api/blu-field-values/{id}", bluFieldValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bluFieldValue.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBluFieldValue() throws Exception {
        // Get the bluFieldValue
        restBluFieldValueMockMvc.perform(get("/api/blu-field-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBluFieldValue() throws Exception {
        // Initialize the database
        bluFieldValueRepository.saveAndFlush(bluFieldValue);

        int databaseSizeBeforeUpdate = bluFieldValueRepository.findAll().size();

        // Update the bluFieldValue
        BluFieldValue updatedBluFieldValue = bluFieldValueRepository.findById(bluFieldValue.getId()).get();
        // Disconnect from session so that the updates on updatedBluFieldValue are not directly saved in db
        em.detach(updatedBluFieldValue);

        restBluFieldValueMockMvc.perform(put("/api/blu-field-values").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBluFieldValue)))
            .andExpect(status().isOk());

        // Validate the BluFieldValue in the database
        List<BluFieldValue> bluFieldValueList = bluFieldValueRepository.findAll();
        assertThat(bluFieldValueList).hasSize(databaseSizeBeforeUpdate);
        BluFieldValue testBluFieldValue = bluFieldValueList.get(bluFieldValueList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBluFieldValue() throws Exception {
        int databaseSizeBeforeUpdate = bluFieldValueRepository.findAll().size();

        // Create the BluFieldValue

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBluFieldValueMockMvc.perform(put("/api/blu-field-values").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldValue)))
            .andExpect(status().isBadRequest());

        // Validate the BluFieldValue in the database
        List<BluFieldValue> bluFieldValueList = bluFieldValueRepository.findAll();
        assertThat(bluFieldValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBluFieldValue() throws Exception {
        // Initialize the database
        bluFieldValueRepository.saveAndFlush(bluFieldValue);

        int databaseSizeBeforeDelete = bluFieldValueRepository.findAll().size();

        // Delete the bluFieldValue
        restBluFieldValueMockMvc.perform(delete("/api/blu-field-values/{id}", bluFieldValue.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BluFieldValue> bluFieldValueList = bluFieldValueRepository.findAll();
        assertThat(bluFieldValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
