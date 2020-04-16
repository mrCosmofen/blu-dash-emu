package com.bludash.emulator.web.rest;

import com.bludash.emulator.EmulatorApp;
import com.bludash.emulator.domain.Query;
import com.bludash.emulator.repository.QueryRepository;

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
 * Integration tests for the {@link QueryResource} REST controller.
 */
@SpringBootTest(classes = EmulatorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class QueryResourceIT {

    private static final UUID DEFAULT_KEY = UUID.randomUUID();
    private static final UUID UPDATED_KEY = UUID.randomUUID();

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQueryMockMvc;

    private Query query;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Query createEntity(EntityManager em) {
        Query query = new Query()
            .key(DEFAULT_KEY)
            .label(DEFAULT_LABEL);
        return query;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Query createUpdatedEntity(EntityManager em) {
        Query query = new Query()
            .key(UPDATED_KEY)
            .label(UPDATED_LABEL);
        return query;
    }

    @BeforeEach
    public void initTest() {
        query = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuery() throws Exception {
        int databaseSizeBeforeCreate = queryRepository.findAll().size();

        // Create the Query
        restQueryMockMvc.perform(post("/api/queries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(query)))
            .andExpect(status().isCreated());

        // Validate the Query in the database
        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeCreate + 1);
        Query testQuery = queryList.get(queryList.size() - 1);
        assertThat(testQuery.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testQuery.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createQueryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = queryRepository.findAll().size();

        // Create the Query with an existing ID
        query.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQueryMockMvc.perform(post("/api/queries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(query)))
            .andExpect(status().isBadRequest());

        // Validate the Query in the database
        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQueries() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList
        restQueryMockMvc.perform(get("/api/queries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(query.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }
    
    @Test
    @Transactional
    public void getQuery() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get the query
        restQueryMockMvc.perform(get("/api/queries/{id}", query.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(query.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    public void getNonExistingQuery() throws Exception {
        // Get the query
        restQueryMockMvc.perform(get("/api/queries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuery() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        int databaseSizeBeforeUpdate = queryRepository.findAll().size();

        // Update the query
        Query updatedQuery = queryRepository.findById(query.getId()).get();
        // Disconnect from session so that the updates on updatedQuery are not directly saved in db
        em.detach(updatedQuery);
        updatedQuery
            .key(UPDATED_KEY)
            .label(UPDATED_LABEL);

        restQueryMockMvc.perform(put("/api/queries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuery)))
            .andExpect(status().isOk());

        // Validate the Query in the database
        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeUpdate);
        Query testQuery = queryList.get(queryList.size() - 1);
        assertThat(testQuery.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testQuery.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingQuery() throws Exception {
        int databaseSizeBeforeUpdate = queryRepository.findAll().size();

        // Create the Query

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQueryMockMvc.perform(put("/api/queries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(query)))
            .andExpect(status().isBadRequest());

        // Validate the Query in the database
        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuery() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        int databaseSizeBeforeDelete = queryRepository.findAll().size();

        // Delete the query
        restQueryMockMvc.perform(delete("/api/queries/{id}", query.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
