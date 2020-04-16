package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BluFieldValue;
import com.mycompany.myapp.repository.BluFieldValueRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BluFieldValue}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BluFieldValueResource {

    private final Logger log = LoggerFactory.getLogger(BluFieldValueResource.class);

    private static final String ENTITY_NAME = "bluFieldValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BluFieldValueRepository bluFieldValueRepository;

    public BluFieldValueResource(BluFieldValueRepository bluFieldValueRepository) {
        this.bluFieldValueRepository = bluFieldValueRepository;
    }

    /**
     * {@code POST  /blu-field-values} : Create a new bluFieldValue.
     *
     * @param bluFieldValue the bluFieldValue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bluFieldValue, or with status {@code 400 (Bad Request)} if the bluFieldValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blu-field-values")
    public ResponseEntity<BluFieldValue> createBluFieldValue(@RequestBody BluFieldValue bluFieldValue) throws URISyntaxException {
        log.debug("REST request to save BluFieldValue : {}", bluFieldValue);
        if (bluFieldValue.getId() != null) {
            throw new BadRequestAlertException("A new bluFieldValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BluFieldValue result = bluFieldValueRepository.save(bluFieldValue);
        return ResponseEntity.created(new URI("/api/blu-field-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blu-field-values} : Updates an existing bluFieldValue.
     *
     * @param bluFieldValue the bluFieldValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bluFieldValue,
     * or with status {@code 400 (Bad Request)} if the bluFieldValue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bluFieldValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blu-field-values")
    public ResponseEntity<BluFieldValue> updateBluFieldValue(@RequestBody BluFieldValue bluFieldValue) throws URISyntaxException {
        log.debug("REST request to update BluFieldValue : {}", bluFieldValue);
        if (bluFieldValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BluFieldValue result = bluFieldValueRepository.save(bluFieldValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bluFieldValue.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blu-field-values} : get all the bluFieldValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bluFieldValues in body.
     */
    @GetMapping("/blu-field-values")
    public List<BluFieldValue> getAllBluFieldValues() {
        log.debug("REST request to get all BluFieldValues");
        return bluFieldValueRepository.findAll();
    }

    /**
     * {@code GET  /blu-field-values/:id} : get the "id" bluFieldValue.
     *
     * @param id the id of the bluFieldValue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bluFieldValue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blu-field-values/{id}")
    public ResponseEntity<BluFieldValue> getBluFieldValue(@PathVariable Long id) {
        log.debug("REST request to get BluFieldValue : {}", id);
        Optional<BluFieldValue> bluFieldValue = bluFieldValueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bluFieldValue);
    }

    /**
     * {@code DELETE  /blu-field-values/:id} : delete the "id" bluFieldValue.
     *
     * @param id the id of the bluFieldValue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blu-field-values/{id}")
    public ResponseEntity<Void> deleteBluFieldValue(@PathVariable Long id) {
        log.debug("REST request to delete BluFieldValue : {}", id);
        bluFieldValueRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
