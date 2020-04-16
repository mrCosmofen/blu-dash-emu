package com.bludash.emulator.web.rest;

import com.bludash.emulator.domain.BluFieldStringValue;
import com.bludash.emulator.repository.BluFieldStringValueRepository;
import com.bludash.emulator.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.bludash.emulator.domain.BluFieldStringValue}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BluFieldStringValueResource {

    private final Logger log = LoggerFactory.getLogger(BluFieldStringValueResource.class);

    private static final String ENTITY_NAME = "bluFieldStringValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BluFieldStringValueRepository bluFieldStringValueRepository;

    public BluFieldStringValueResource(BluFieldStringValueRepository bluFieldStringValueRepository) {
        this.bluFieldStringValueRepository = bluFieldStringValueRepository;
    }

    /**
     * {@code POST  /blu-field-string-values} : Create a new bluFieldStringValue.
     *
     * @param bluFieldStringValue the bluFieldStringValue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bluFieldStringValue, or with status {@code 400 (Bad Request)} if the bluFieldStringValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blu-field-string-values")
    public ResponseEntity<BluFieldStringValue> createBluFieldStringValue(@RequestBody BluFieldStringValue bluFieldStringValue) throws URISyntaxException {
        log.debug("REST request to save BluFieldStringValue : {}", bluFieldStringValue);
        if (bluFieldStringValue.getId() != null) {
            throw new BadRequestAlertException("A new bluFieldStringValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BluFieldStringValue result = bluFieldStringValueRepository.save(bluFieldStringValue);
        return ResponseEntity.created(new URI("/api/blu-field-string-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blu-field-string-values} : Updates an existing bluFieldStringValue.
     *
     * @param bluFieldStringValue the bluFieldStringValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bluFieldStringValue,
     * or with status {@code 400 (Bad Request)} if the bluFieldStringValue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bluFieldStringValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blu-field-string-values")
    public ResponseEntity<BluFieldStringValue> updateBluFieldStringValue(@RequestBody BluFieldStringValue bluFieldStringValue) throws URISyntaxException {
        log.debug("REST request to update BluFieldStringValue : {}", bluFieldStringValue);
        if (bluFieldStringValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BluFieldStringValue result = bluFieldStringValueRepository.save(bluFieldStringValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bluFieldStringValue.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blu-field-string-values} : get all the bluFieldStringValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bluFieldStringValues in body.
     */
    @GetMapping("/blu-field-string-values")
    public List<BluFieldStringValue> getAllBluFieldStringValues() {
        log.debug("REST request to get all BluFieldStringValues");
        return bluFieldStringValueRepository.findAll();
    }

    /**
     * {@code GET  /blu-field-string-values/:id} : get the "id" bluFieldStringValue.
     *
     * @param id the id of the bluFieldStringValue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bluFieldStringValue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blu-field-string-values/{id}")
    public ResponseEntity<BluFieldStringValue> getBluFieldStringValue(@PathVariable Long id) {
        log.debug("REST request to get BluFieldStringValue : {}", id);
        Optional<BluFieldStringValue> bluFieldStringValue = bluFieldStringValueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bluFieldStringValue);
    }

    /**
     * {@code DELETE  /blu-field-string-values/:id} : delete the "id" bluFieldStringValue.
     *
     * @param id the id of the bluFieldStringValue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blu-field-string-values/{id}")
    public ResponseEntity<Void> deleteBluFieldStringValue(@PathVariable Long id) {
        log.debug("REST request to delete BluFieldStringValue : {}", id);
        bluFieldStringValueRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
