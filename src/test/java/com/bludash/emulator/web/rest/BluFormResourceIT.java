package com.bludash.emulator.web.rest;

import com.bludash.emulator.EmulatorApp;
import com.bludash.emulator.domain.BluForm;
import com.bludash.emulator.repository.BluFormRepository;

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
 * Integration tests for the {@link BluFormResource} REST controller.
 */
@SpringBootTest(classes = EmulatorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BluFormResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_KEY = "BBBBBBBBBB";

    private static final Long DEFAULT_POLLING_INTERVAL = 1L;
    private static final Long UPDATED_POLLING_INTERVAL = 2L;

    private static final Long DEFAULT_MODIFIED = 1L;
    private static final Long UPDATED_MODIFIED = 2L;

    @Autowired
    private BluFormRepository bluFormRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBluFormMockMvc;

    private BluForm bluForm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluForm createEntity(EntityManager em) {
        BluForm bluForm = new BluForm()
            .key(DEFAULT_KEY)
            .label(DEFAULT_LABEL)
            .category(DEFAULT_CATEGORY)
            .productKey(DEFAULT_PRODUCT_KEY)
            .pollingInterval(DEFAULT_POLLING_INTERVAL)
            .modified(DEFAULT_MODIFIED);
        return bluForm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BluForm createUpdatedEntity(EntityManager em) {
        BluForm bluForm = new BluForm()
            .key(UPDATED_KEY)
            .label(UPDATED_LABEL)
            .category(UPDATED_CATEGORY)
            .productKey(UPDATED_PRODUCT_KEY)
            .pollingInterval(UPDATED_POLLING_INTERVAL)
            .modified(UPDATED_MODIFIED);
        return bluForm;
    }

    @BeforeEach
    public void initTest() {
        bluForm = createEntity(em);
    }

    @Test
    @Transactional
    public void createBluForm() throws Exception {
        int databaseSizeBeforeCreate = bluFormRepository.findAll().size();

        // Create the BluForm
        restBluFormMockMvc.perform(post("/api/blu-forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluForm)))
            .andExpect(status().isCreated());

        // Validate the BluForm in the database
        List<BluForm> bluFormList = bluFormRepository.findAll();
        assertThat(bluFormList).hasSize(databaseSizeBeforeCreate + 1);
        BluForm testBluForm = bluFormList.get(bluFormList.size() - 1);
        assertThat(testBluForm.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testBluForm.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testBluForm.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testBluForm.getProductKey()).isEqualTo(DEFAULT_PRODUCT_KEY);
        assertThat(testBluForm.getPollingInterval()).isEqualTo(DEFAULT_POLLING_INTERVAL);
        assertThat(testBluForm.getModified()).isEqualTo(DEFAULT_MODIFIED);
    }

    @Test
    @Transactional
    public void createBluFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bluFormRepository.findAll().size();

        // Create the BluForm with an existing ID
        bluForm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBluFormMockMvc.perform(post("/api/blu-forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluForm)))
            .andExpect(status().isBadRequest());

        // Validate the BluForm in the database
        List<BluForm> bluFormList = bluFormRepository.findAll();
        assertThat(bluFormList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBluForms() throws Exception {
        // Initialize the database
        bluFormRepository.saveAndFlush(bluForm);

        // Get all the bluFormList
        restBluFormMockMvc.perform(get("/api/blu-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bluForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].productKey").value(hasItem(DEFAULT_PRODUCT_KEY)))
            .andExpect(jsonPath("$.[*].pollingInterval").value(hasItem(DEFAULT_POLLING_INTERVAL.intValue())))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.intValue())));
    }
    
    @Test
    @Transactional
    public void getBluForm() throws Exception {
        // Initialize the database
        bluFormRepository.saveAndFlush(bluForm);

        // Get the bluForm
        restBluFormMockMvc.perform(get("/api/blu-forms/{id}", bluForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bluForm.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.productKey").value(DEFAULT_PRODUCT_KEY))
            .andExpect(jsonPath("$.pollingInterval").value(DEFAULT_POLLING_INTERVAL.intValue()))
            .andExpect(jsonPath("$.modified").value(DEFAULT_MODIFIED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBluForm() throws Exception {
        // Get the bluForm
        restBluFormMockMvc.perform(get("/api/blu-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBluForm() throws Exception {
        // Initialize the database
        bluFormRepository.saveAndFlush(bluForm);

        int databaseSizeBeforeUpdate = bluFormRepository.findAll().size();

        // Update the bluForm
        BluForm updatedBluForm = bluFormRepository.findById(bluForm.getId()).get();
        // Disconnect from session so that the updates on updatedBluForm are not directly saved in db
        em.detach(updatedBluForm);
        updatedBluForm
            .key(UPDATED_KEY)
            .label(UPDATED_LABEL)
            .category(UPDATED_CATEGORY)
            .productKey(UPDATED_PRODUCT_KEY)
            .pollingInterval(UPDATED_POLLING_INTERVAL)
            .modified(UPDATED_MODIFIED);

        restBluFormMockMvc.perform(put("/api/blu-forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBluForm)))
            .andExpect(status().isOk());

        // Validate the BluForm in the database
        List<BluForm> bluFormList = bluFormRepository.findAll();
        assertThat(bluFormList).hasSize(databaseSizeBeforeUpdate);
        BluForm testBluForm = bluFormList.get(bluFormList.size() - 1);
        assertThat(testBluForm.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testBluForm.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testBluForm.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testBluForm.getProductKey()).isEqualTo(UPDATED_PRODUCT_KEY);
        assertThat(testBluForm.getPollingInterval()).isEqualTo(UPDATED_POLLING_INTERVAL);
        assertThat(testBluForm.getModified()).isEqualTo(UPDATED_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingBluForm() throws Exception {
        int databaseSizeBeforeUpdate = bluFormRepository.findAll().size();

        // Create the BluForm

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBluFormMockMvc.perform(put("/api/blu-forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bluForm)))
            .andExpect(status().isBadRequest());

        // Validate the BluForm in the database
        List<BluForm> bluFormList = bluFormRepository.findAll();
        assertThat(bluFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBluForm() throws Exception {
        // Initialize the database
        bluFormRepository.saveAndFlush(bluForm);

        int databaseSizeBeforeDelete = bluFormRepository.findAll().size();

        // Delete the bluForm
        restBluFormMockMvc.perform(delete("/api/blu-forms/{id}", bluForm.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BluForm> bluFormList = bluFormRepository.findAll();
        assertThat(bluFormList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
