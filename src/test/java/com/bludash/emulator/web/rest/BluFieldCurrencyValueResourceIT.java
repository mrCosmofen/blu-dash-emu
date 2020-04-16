package com.bludash.emulator.web.rest;

import com.bludash.emulator.EmulatorApp;
import com.bludash.emulator.domain.BluFieldCurrencyValue;
import com.bludash.emulator.repository.BluFieldCurrencyValueRepository;

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
 * Integration tests for the {@link BluFieldCurrencyValueResource} REST controller.
 */
@SpringBootTest(classes = EmulatorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BluFieldCurrencyValueResourceIT {

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    @Autowired
    private BluFieldCurrencyValueRepository bluFieldCurrencyValueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBluFieldCurrencyValueMockMvc;

    private BluFieldCurrencyValue bluFieldCurrencyValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluFieldCurrencyValue createEntity(EntityManager em) {
        BluFieldCurrencyValue bluFieldCurrencyValue = new BluFieldCurrencyValue()
            .value(DEFAULT_VALUE)
            .currency(DEFAULT_CURRENCY);
        return bluFieldCurrencyValue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluFieldCurrencyValue createUpdatedEntity(EntityManager em) {
        BluFieldCurrencyValue bluFieldCurrencyValue = new BluFieldCurrencyValue()
            .value(UPDATED_VALUE)
            .currency(UPDATED_CURRENCY);
        return bluFieldCurrencyValue;
    }

    @BeforeEach
    public void initTest() {
        bluFieldCurrencyValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createBluFieldCurrencyValue() throws Exception {
        int databaseSizeBeforeCreate = bluFieldCurrencyValueRepository.findAll().size();

        // Create the BluFieldCurrencyValue
        restBluFieldCurrencyValueMockMvc.perform(post("/api/blu-field-currency-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldCurrencyValue)))
            .andExpect(status().isCreated());

        // Validate the BluFieldCurrencyValue in the database
        List<BluFieldCurrencyValue> bluFieldCurrencyValueList = bluFieldCurrencyValueRepository.findAll();
        assertThat(bluFieldCurrencyValueList).hasSize(databaseSizeBeforeCreate + 1);
        BluFieldCurrencyValue testBluFieldCurrencyValue = bluFieldCurrencyValueList.get(bluFieldCurrencyValueList.size() - 1);
        assertThat(testBluFieldCurrencyValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testBluFieldCurrencyValue.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    public void createBluFieldCurrencyValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bluFieldCurrencyValueRepository.findAll().size();

        // Create the BluFieldCurrencyValue with an existing ID
        bluFieldCurrencyValue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBluFieldCurrencyValueMockMvc.perform(post("/api/blu-field-currency-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldCurrencyValue)))
            .andExpect(status().isBadRequest());

        // Validate the BluFieldCurrencyValue in the database
        List<BluFieldCurrencyValue> bluFieldCurrencyValueList = bluFieldCurrencyValueRepository.findAll();
        assertThat(bluFieldCurrencyValueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBluFieldCurrencyValues() throws Exception {
        // Initialize the database
        bluFieldCurrencyValueRepository.saveAndFlush(bluFieldCurrencyValue);

        // Get all the bluFieldCurrencyValueList
        restBluFieldCurrencyValueMockMvc.perform(get("/api/blu-field-currency-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bluFieldCurrencyValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)));
    }
    
    @Test
    @Transactional
    public void getBluFieldCurrencyValue() throws Exception {
        // Initialize the database
        bluFieldCurrencyValueRepository.saveAndFlush(bluFieldCurrencyValue);

        // Get the bluFieldCurrencyValue
        restBluFieldCurrencyValueMockMvc.perform(get("/api/blu-field-currency-values/{id}", bluFieldCurrencyValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bluFieldCurrencyValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY));
    }

    @Test
    @Transactional
    public void getNonExistingBluFieldCurrencyValue() throws Exception {
        // Get the bluFieldCurrencyValue
        restBluFieldCurrencyValueMockMvc.perform(get("/api/blu-field-currency-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBluFieldCurrencyValue() throws Exception {
        // Initialize the database
        bluFieldCurrencyValueRepository.saveAndFlush(bluFieldCurrencyValue);

        int databaseSizeBeforeUpdate = bluFieldCurrencyValueRepository.findAll().size();

        // Update the bluFieldCurrencyValue
        BluFieldCurrencyValue updatedBluFieldCurrencyValue = bluFieldCurrencyValueRepository.findById(bluFieldCurrencyValue.getId()).get();
        // Disconnect from session so that the updates on updatedBluFieldCurrencyValue are not directly saved in db
        em.detach(updatedBluFieldCurrencyValue);
        updatedBluFieldCurrencyValue
            .value(UPDATED_VALUE)
            .currency(UPDATED_CURRENCY);

        restBluFieldCurrencyValueMockMvc.perform(put("/api/blu-field-currency-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBluFieldCurrencyValue)))
            .andExpect(status().isOk());

        // Validate the BluFieldCurrencyValue in the database
        List<BluFieldCurrencyValue> bluFieldCurrencyValueList = bluFieldCurrencyValueRepository.findAll();
        assertThat(bluFieldCurrencyValueList).hasSize(databaseSizeBeforeUpdate);
        BluFieldCurrencyValue testBluFieldCurrencyValue = bluFieldCurrencyValueList.get(bluFieldCurrencyValueList.size() - 1);
        assertThat(testBluFieldCurrencyValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testBluFieldCurrencyValue.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void updateNonExistingBluFieldCurrencyValue() throws Exception {
        int databaseSizeBeforeUpdate = bluFieldCurrencyValueRepository.findAll().size();

        // Create the BluFieldCurrencyValue

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBluFieldCurrencyValueMockMvc.perform(put("/api/blu-field-currency-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluFieldCurrencyValue)))
            .andExpect(status().isBadRequest());

        // Validate the BluFieldCurrencyValue in the database
        List<BluFieldCurrencyValue> bluFieldCurrencyValueList = bluFieldCurrencyValueRepository.findAll();
        assertThat(bluFieldCurrencyValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBluFieldCurrencyValue() throws Exception {
        // Initialize the database
        bluFieldCurrencyValueRepository.saveAndFlush(bluFieldCurrencyValue);

        int databaseSizeBeforeDelete = bluFieldCurrencyValueRepository.findAll().size();

        // Delete the bluFieldCurrencyValue
        restBluFieldCurrencyValueMockMvc.perform(delete("/api/blu-field-currency-values/{id}", bluFieldCurrencyValue.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BluFieldCurrencyValue> bluFieldCurrencyValueList = bluFieldCurrencyValueRepository.findAll();
        assertThat(bluFieldCurrencyValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
