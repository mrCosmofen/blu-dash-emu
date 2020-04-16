package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BluForm;
import com.mycompany.myapp.repository.BluFormRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BluForm}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BluFormResource {

    private final Logger log = LoggerFactory.getLogger(BluFormResource.class);

    private static final String ENTITY_NAME = "bluForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BluFormRepository bluFormRepository;

    public BluFormResource(BluFormRepository bluFormRepository) {
        this.bluFormRepository = bluFormRepository;
    }

    /**
     * {@code POST  /blu-forms} : Create a new bluForm.
     *
     * @param bluForm the bluForm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bluForm, or with status {@code 400 (Bad Request)} if the bluForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blu-forms")
    public ResponseEntity<BluForm> createBluForm(@RequestBody BluForm bluForm) throws URISyntaxException {
        log.debug("REST request to save BluForm : {}", bluForm);
        if (bluForm.getId() != null) {
            throw new BadRequestAlertException("A new bluForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BluForm result = bluFormRepository.save(bluForm);
        return ResponseEntity.created(new URI("/api/blu-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blu-forms} : Updates an existing bluForm.
     *
     * @param bluForm the bluForm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bluForm,
     * or with status {@code 400 (Bad Request)} if the bluForm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bluForm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blu-forms")
    public ResponseEntity<BluForm> updateBluForm(@RequestBody BluForm bluForm) throws URISyntaxException {
        log.debug("REST request to update BluForm : {}", bluForm);
        if (bluForm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BluForm result = bluFormRepository.save(bluForm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bluForm.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blu-forms} : get all the bluForms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bluForms in body.
     */
    @GetMapping("/blu-forms")
    public List<BluForm> getAllBluForms() {
        log.debug("REST request to get all BluForms");
        return bluFormRepository.findAll();
    }

    /**
     * {@code GET  /blu-forms/:id} : get the "id" bluForm.
     *
     * @param id the id of the bluForm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bluForm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blu-forms/{id}")
    public ResponseEntity<BluForm> getBluForm(@PathVariable Long id) {
        log.debug("REST request to get BluForm : {}", id);
        Optional<BluForm> bluForm = bluFormRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bluForm);
    }

    /**
     * {@code DELETE  /blu-forms/:id} : delete the "id" bluForm.
     *
     * @param id the id of the bluForm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blu-forms/{id}")
    public ResponseEntity<Void> deleteBluForm(@PathVariable Long id) {
        log.debug("REST request to delete BluForm : {}", id);
        bluFormRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
