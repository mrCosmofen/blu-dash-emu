package com.bludash.emulator.web.rest;

import com.bludash.emulator.domain.BluFormData;
import com.bludash.emulator.repository.BluFormDataRepository;
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
 * REST controller for managing {@link com.bludash.emulator.domain.BluFormData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BluFormDataResource {

    private final Logger log = LoggerFactory.getLogger(BluFormDataResource.class);

    private static final String ENTITY_NAME = "bluFormData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BluFormDataRepository bluFormDataRepository;

    public BluFormDataResource(BluFormDataRepository bluFormDataRepository) {
        this.bluFormDataRepository = bluFormDataRepository;
    }

    /**
     * {@code POST  /blu-form-data} : Create a new bluFormData.
     *
     * @param bluFormData the bluFormData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bluFormData, or with status {@code 400 (Bad Request)} if the bluFormData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blu-form-data")
    public ResponseEntity<BluFormData> createBluFormData(@RequestBody BluFormData bluFormData) throws URISyntaxException {
        log.debug("REST request to save BluFormData : {}", bluFormData);
        if (bluFormData.getId() != null) {
            throw new BadRequestAlertException("A new bluFormData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BluFormData result = bluFormDataRepository.save(bluFormData);
        return ResponseEntity.created(new URI("/api/blu-form-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blu-form-data} : Updates an existing bluFormData.
     *
     * @param bluFormData the bluFormData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bluFormData,
     * or with status {@code 400 (Bad Request)} if the bluFormData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bluFormData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blu-form-data")
    public ResponseEntity<BluFormData> updateBluFormData(@RequestBody BluFormData bluFormData) throws URISyntaxException {
        log.debug("REST request to update BluFormData : {}", bluFormData);
        if (bluFormData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BluFormData result = bluFormDataRepository.save(bluFormData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bluFormData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blu-form-data} : get all the bluFormData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bluFormData in body.
     */
    @GetMapping("/blu-form-data")
    public List<BluFormData> getAllBluFormData() {
        log.debug("REST request to get all BluFormData");
        return bluFormDataRepository.findAll();
    }

    /**
     * {@code GET  /blu-form-data/:id} : get the "id" bluFormData.
     *
     * @param id the id of the bluFormData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bluFormData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blu-form-data/{id}")
    public ResponseEntity<BluFormData> getBluFormData(@PathVariable Long id) {
        log.debug("REST request to get BluFormData : {}", id);
        Optional<BluFormData> bluFormData = bluFormDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bluFormData);
    }

    /**
     * {@code DELETE  /blu-form-data/:id} : delete the "id" bluFormData.
     *
     * @param id the id of the bluFormData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blu-form-data/{id}")
    public ResponseEntity<Void> deleteBluFormData(@PathVariable Long id) {
        log.debug("REST request to delete BluFormData : {}", id);
        bluFormDataRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
