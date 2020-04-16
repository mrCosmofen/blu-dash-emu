package com.bludash.emulator.web.rest;

import com.bludash.emulator.domain.BluFieldNumberValue;
import com.bludash.emulator.repository.BluFieldNumberValueRepository;
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
 * REST controller for managing {@link com.bludash.emulator.domain.BluFieldNumberValue}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BluFieldNumberValueResource {

    private final Logger log = LoggerFactory.getLogger(BluFieldNumberValueResource.class);

    private static final String ENTITY_NAME = "bluFieldNumberValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BluFieldNumberValueRepository bluFieldNumberValueRepository;

    public BluFieldNumberValueResource(BluFieldNumberValueRepository bluFieldNumberValueRepository) {
        this.bluFieldNumberValueRepository = bluFieldNumberValueRepository;
    }

    /**
     * {@code POST  /blu-field-number-values} : Create a new bluFieldNumberValue.
     *
     * @param bluFieldNumberValue the bluFieldNumberValue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bluFieldNumberValue, or with status {@code 400 (Bad Request)} if the bluFieldNumberValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blu-field-number-values")
    public ResponseEntity<BluFieldNumberValue> createBluFieldNumberValue(@RequestBody BluFieldNumberValue bluFieldNumberValue) throws URISyntaxException {
        log.debug("REST request to save BluFieldNumberValue : {}", bluFieldNumberValue);
        if (bluFieldNumberValue.getId() != null) {
            throw new BadRequestAlertException("A new bluFieldNumberValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BluFieldNumberValue result = bluFieldNumberValueRepository.save(bluFieldNumberValue);
        return ResponseEntity.created(new URI("/api/blu-field-number-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blu-field-number-values} : Updates an existing bluFieldNumberValue.
     *
     * @param bluFieldNumberValue the bluFieldNumberValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bluFieldNumberValue,
     * or with status {@code 400 (Bad Request)} if the bluFieldNumberValue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bluFieldNumberValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blu-field-number-values")
    public ResponseEntity<BluFieldNumberValue> updateBluFieldNumberValue(@RequestBody BluFieldNumberValue bluFieldNumberValue) throws URISyntaxException {
        log.debug("REST request to update BluFieldNumberValue : {}", bluFieldNumberValue);
        if (bluFieldNumberValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BluFieldNumberValue result = bluFieldNumberValueRepository.save(bluFieldNumberValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bluFieldNumberValue.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blu-field-number-values} : get all the bluFieldNumberValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bluFieldNumberValues in body.
     */
    @GetMapping("/blu-field-number-values")
    public List<BluFieldNumberValue> getAllBluFieldNumberValues() {
        log.debug("REST request to get all BluFieldNumberValues");
        return bluFieldNumberValueRepository.findAll();
    }

    /**
     * {@code GET  /blu-field-number-values/:id} : get the "id" bluFieldNumberValue.
     *
     * @param id the id of the bluFieldNumberValue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bluFieldNumberValue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blu-field-number-values/{id}")
    public ResponseEntity<BluFieldNumberValue> getBluFieldNumberValue(@PathVariable Long id) {
        log.debug("REST request to get BluFieldNumberValue : {}", id);
        Optional<BluFieldNumberValue> bluFieldNumberValue = bluFieldNumberValueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bluFieldNumberValue);
    }

    /**
     * {@code DELETE  /blu-field-number-values/:id} : delete the "id" bluFieldNumberValue.
     *
     * @param id the id of the bluFieldNumberValue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blu-field-number-values/{id}")
    public ResponseEntity<Void> deleteBluFieldNumberValue(@PathVariable Long id) {
        log.debug("REST request to delete BluFieldNumberValue : {}", id);
        bluFieldNumberValueRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
