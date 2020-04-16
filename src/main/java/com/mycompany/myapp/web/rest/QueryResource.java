package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Query;
import com.mycompany.myapp.repository.QueryRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Query}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QueryResource {

    private final Logger log = LoggerFactory.getLogger(QueryResource.class);

    private static final String ENTITY_NAME = "query";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QueryRepository queryRepository;

    public QueryResource(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    /**
     * {@code POST  /queries} : Create a new query.
     *
     * @param query the query to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new query, or with status {@code 400 (Bad Request)} if the query has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/queries")
    public ResponseEntity<Query> createQuery(@RequestBody Query query) throws URISyntaxException {
        log.debug("REST request to save Query : {}", query);
        if (query.getId() != null) {
            throw new BadRequestAlertException("A new query cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Query result = queryRepository.save(query);
        return ResponseEntity.created(new URI("/api/queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /queries} : Updates an existing query.
     *
     * @param query the query to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated query,
     * or with status {@code 400 (Bad Request)} if the query is not valid,
     * or with status {@code 500 (Internal Server Error)} if the query couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/queries")
    public ResponseEntity<Query> updateQuery(@RequestBody Query query) throws URISyntaxException {
        log.debug("REST request to update Query : {}", query);
        if (query.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Query result = queryRepository.save(query);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, query.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /queries} : get all the queries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of queries in body.
     */
    @GetMapping("/queries")
    public List<Query> getAllQueries() {
        log.debug("REST request to get all Queries");
        return queryRepository.findAll();
    }

    /**
     * {@code GET  /queries/:id} : get the "id" query.
     *
     * @param id the id of the query to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the query, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/queries/{id}")
    public ResponseEntity<Query> getQuery(@PathVariable Long id) {
        log.debug("REST request to get Query : {}", id);
        Optional<Query> query = queryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(query);
    }

    /**
     * {@code DELETE  /queries/:id} : delete the "id" query.
     *
     * @param id the id of the query to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/queries/{id}")
    public ResponseEntity<Void> deleteQuery(@PathVariable Long id) {
        log.debug("REST request to delete Query : {}", id);
        queryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
