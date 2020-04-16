package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.QueryData;
import com.mycompany.myapp.repository.QueryDataRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.QueryData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QueryDataResource {

    private final Logger log = LoggerFactory.getLogger(QueryDataResource.class);

    private static final String ENTITY_NAME = "queryData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QueryDataRepository queryDataRepository;

    public QueryDataResource(QueryDataRepository queryDataRepository) {
        this.queryDataRepository = queryDataRepository;
    }

    /**
     * {@code POST  /query-data} : Create a new queryData.
     *
     * @param queryData the queryData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new queryData, or with status {@code 400 (Bad Request)} if the queryData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/query-data")
    public ResponseEntity<QueryData> createQueryData(@RequestBody QueryData queryData) throws URISyntaxException {
        log.debug("REST request to save QueryData : {}", queryData);
        if (queryData.getId() != null) {
            throw new BadRequestAlertException("A new queryData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QueryData result = queryDataRepository.save(queryData);
        return ResponseEntity.created(new URI("/api/query-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /query-data} : Updates an existing queryData.
     *
     * @param queryData the queryData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated queryData,
     * or with status {@code 400 (Bad Request)} if the queryData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the queryData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/query-data")
    public ResponseEntity<QueryData> updateQueryData(@RequestBody QueryData queryData) throws URISyntaxException {
        log.debug("REST request to update QueryData : {}", queryData);
        if (queryData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QueryData result = queryDataRepository.save(queryData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, queryData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /query-data} : get all the queryData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of queryData in body.
     */
    @GetMapping("/query-data")
    public List<QueryData> getAllQueryData() {
        log.debug("REST request to get all QueryData");
        return queryDataRepository.findAll();
    }

    /**
     * {@code GET  /query-data/:id} : get the "id" queryData.
     *
     * @param id the id of the queryData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the queryData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/query-data/{id}")
    public ResponseEntity<QueryData> getQueryData(@PathVariable Long id) {
        log.debug("REST request to get QueryData : {}", id);
        Optional<QueryData> queryData = queryDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(queryData);
    }

    /**
     * {@code DELETE  /query-data/:id} : delete the "id" queryData.
     *
     * @param id the id of the queryData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/query-data/{id}")
    public ResponseEntity<Void> deleteQueryData(@PathVariable Long id) {
        log.debug("REST request to delete QueryData : {}", id);
        queryDataRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
