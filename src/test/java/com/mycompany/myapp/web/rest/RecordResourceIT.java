package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.EmulatorApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Record;
import com.mycompany.myapp.repository.RecordRepository;

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
 * Integration tests for the {@link RecordResource} REST controller.
 */
@SpringBootTest(classes = { EmulatorApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class RecordResourceIT {

    private static final Integer DEFAULT_RECORD_ID = 1;
    private static final Integer UPDATED_RECORD_ID = 2;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecordMockMvc;

    private Record record;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Record createEntity(EntityManager em) {
        Record record = new Record()
            .recordId(DEFAULT_RECORD_ID);
        return record;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Record createUpdatedEntity(EntityManager em) {
        Record record = new Record()
            .recordId(UPDATED_RECORD_ID);
        return record;
    }

    @BeforeEach
    public void initTest() {
        record = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecord() throws Exception {
        int databaseSizeBeforeCreate = recordRepository.findAll().size();

        // Create the Record
        restRecordMockMvc.perform(post("/api/records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(record)))
            .andExpect(status().isCreated());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeCreate + 1);
        Record testRecord = recordList.get(recordList.size() - 1);
        assertThat(testRecord.getRecordId()).isEqualTo(DEFAULT_RECORD_ID);
    }

    @Test
    @Transactional
    public void createRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recordRepository.findAll().size();

        // Create the Record with an existing ID
        record.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecordMockMvc.perform(post("/api/records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(record)))
            .andExpect(status().isBadRequest());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRecords() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        // Get all the recordList
        restRecordMockMvc.perform(get("/api/records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(record.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordId").value(hasItem(DEFAULT_RECORD_ID)));
    }
    
    @Test
    @Transactional
    public void getRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        // Get the record
        restRecordMockMvc.perform(get("/api/records/{id}", record.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(record.getId().intValue()))
            .andExpect(jsonPath("$.recordId").value(DEFAULT_RECORD_ID));
    }

    @Test
    @Transactional
    public void getNonExistingRecord() throws Exception {
        // Get the record
        restRecordMockMvc.perform(get("/api/records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        int databaseSizeBeforeUpdate = recordRepository.findAll().size();

        // Update the record
        Record updatedRecord = recordRepository.findById(record.getId()).get();
        // Disconnect from session so that the updates on updatedRecord are not directly saved in db
        em.detach(updatedRecord);
        updatedRecord
            .recordId(UPDATED_RECORD_ID);

        restRecordMockMvc.perform(put("/api/records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecord)))
            .andExpect(status().isOk());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeUpdate);
        Record testRecord = recordList.get(recordList.size() - 1);
        assertThat(testRecord.getRecordId()).isEqualTo(UPDATED_RECORD_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingRecord() throws Exception {
        int databaseSizeBeforeUpdate = recordRepository.findAll().size();

        // Create the Record

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecordMockMvc.perform(put("/api/records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(record)))
            .andExpect(status().isBadRequest());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        int databaseSizeBeforeDelete = recordRepository.findAll().size();

        // Delete the record
        restRecordMockMvc.perform(delete("/api/records/{id}", record.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
