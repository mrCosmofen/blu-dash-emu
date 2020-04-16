package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BluField;
import com.mycompany.myapp.repository.BluFieldRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BluField}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BluFieldResource {

    private final Logger log = LoggerFactory.getLogger(BluFieldResource.class);

    private static final String ENTITY_NAME = "bluField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BluFieldRepository bluFieldRepository;

    public BluFieldResource(BluFieldRepository bluFieldRepository) {
        this.bluFieldRepository = bluFieldRepository;
    }

    /**
     * {@code POST  /blu-fields} : Create a new bluField.
     *
     * @param bluField the bluField to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bluField, or with status {@code 400 (Bad Request)} if the bluField has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blu-fields")
    public ResponseEntity<BluField> createBluField(@RequestBody BluField bluField) throws URISyntaxException {
        log.debug("REST request to save BluField : {}", bluField);
        if (bluField.getId() != null) {
            throw new BadRequestAlertException("A new bluField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BluField result = bluFieldRepository.save(bluField);
        return ResponseEntity.created(new URI("/api/blu-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blu-fields} : Updates an existing bluField.
     *
     * @param bluField the bluField to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bluField,
     * or with status {@code 400 (Bad Request)} if the bluField is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bluField couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blu-fields")
    public ResponseEntity<BluField> updateBluField(@RequestBody BluField bluField) throws URISyntaxException {
        log.debug("REST request to update BluField : {}", bluField);
        if (bluField.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BluField result = bluFieldRepository.save(bluField);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bluField.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blu-fields} : get all the bluFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bluFields in body.
     */
    @GetMapping("/blu-fields")
    public List<BluField> getAllBluFields() {
        log.debug("REST request to get all BluFields");
        return bluFieldRepository.findAll();
    }

    /**
     * {@code GET  /blu-fields/:id} : get the "id" bluField.
     *
     * @param id the id of the bluField to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bluField, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blu-fields/{id}")
    public ResponseEntity<BluField> getBluField(@PathVariable Long id) {
        log.debug("REST request to get BluField : {}", id);
        Optional<BluField> bluField = bluFieldRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bluField);
    }

    /**
     * {@code DELETE  /blu-fields/:id} : delete the "id" bluField.
     *
     * @param id the id of the bluField to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blu-fields/{id}")
    public ResponseEntity<Void> deleteBluField(@PathVariable Long id) {
        log.debug("REST request to delete BluField : {}", id);
        bluFieldRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
