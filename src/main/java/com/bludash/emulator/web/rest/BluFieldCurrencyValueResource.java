package com.bludash.emulator.web.rest;

import com.bludash.emulator.domain.BluFieldCurrencyValue;
import com.bludash.emulator.repository.BluFieldCurrencyValueRepository;
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
 * REST controller for managing {@link com.bludash.emulator.domain.BluFieldCurrencyValue}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BluFieldCurrencyValueResource {

    private final Logger log = LoggerFactory.getLogger(BluFieldCurrencyValueResource.class);

    private static final String ENTITY_NAME = "bluFieldCurrencyValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BluFieldCurrencyValueRepository bluFieldCurrencyValueRepository;

    public BluFieldCurrencyValueResource(BluFieldCurrencyValueRepository bluFieldCurrencyValueRepository) {
        this.bluFieldCurrencyValueRepository = bluFieldCurrencyValueRepository;
    }

    /**
     * {@code POST  /blu-field-currency-values} : Create a new bluFieldCurrencyValue.
     *
     * @param bluFieldCurrencyValue the bluFieldCurrencyValue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bluFieldCurrencyValue, or with status {@code 400 (Bad Request)} if the bluFieldCurrencyValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blu-field-currency-values")
    public ResponseEntity<BluFieldCurrencyValue> createBluFieldCurrencyValue(@RequestBody BluFieldCurrencyValue bluFieldCurrencyValue) throws URISyntaxException {
        log.debug("REST request to save BluFieldCurrencyValue : {}", bluFieldCurrencyValue);
        if (bluFieldCurrencyValue.getId() != null) {
            throw new BadRequestAlertException("A new bluFieldCurrencyValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BluFieldCurrencyValue result = bluFieldCurrencyValueRepository.save(bluFieldCurrencyValue);
        return ResponseEntity.created(new URI("/api/blu-field-currency-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blu-field-currency-values} : Updates an existing bluFieldCurrencyValue.
     *
     * @param bluFieldCurrencyValue the bluFieldCurrencyValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bluFieldCurrencyValue,
     * or with status {@code 400 (Bad Request)} if the bluFieldCurrencyValue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bluFieldCurrencyValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blu-field-currency-values")
    public ResponseEntity<BluFieldCurrencyValue> updateBluFieldCurrencyValue(@RequestBody BluFieldCurrencyValue bluFieldCurrencyValue) throws URISyntaxException {
        log.debug("REST request to update BluFieldCurrencyValue : {}", bluFieldCurrencyValue);
        if (bluFieldCurrencyValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BluFieldCurrencyValue result = bluFieldCurrencyValueRepository.save(bluFieldCurrencyValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bluFieldCurrencyValue.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blu-field-currency-values} : get all the bluFieldCurrencyValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bluFieldCurrencyValues in body.
     */
    @GetMapping("/blu-field-currency-values")
    public List<BluFieldCurrencyValue> getAllBluFieldCurrencyValues() {
        log.debug("REST request to get all BluFieldCurrencyValues");
        return bluFieldCurrencyValueRepository.findAll();
    }

    /**
     * {@code GET  /blu-field-currency-values/:id} : get the "id" bluFieldCurrencyValue.
     *
     * @param id the id of the bluFieldCurrencyValue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bluFieldCurrencyValue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blu-field-currency-values/{id}")
    public ResponseEntity<BluFieldCurrencyValue> getBluFieldCurrencyValue(@PathVariable Long id) {
        log.debug("REST request to get BluFieldCurrencyValue : {}", id);
        Optional<BluFieldCurrencyValue> bluFieldCurrencyValue = bluFieldCurrencyValueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bluFieldCurrencyValue);
    }

    /**
     * {@code DELETE  /blu-field-currency-values/:id} : delete the "id" bluFieldCurrencyValue.
     *
     * @param id the id of the bluFieldCurrencyValue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blu-field-currency-values/{id}")
    public ResponseEntity<Void> deleteBluFieldCurrencyValue(@PathVariable Long id) {
        log.debug("REST request to delete BluFieldCurrencyValue : {}", id);
        bluFieldCurrencyValueRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
