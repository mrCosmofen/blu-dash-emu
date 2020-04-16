package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DataModel;
import com.mycompany.myapp.repository.DataModelRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DataModel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DataModelResource {

    private final Logger log = LoggerFactory.getLogger(DataModelResource.class);

    private static final String ENTITY_NAME = "dataModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataModelRepository dataModelRepository;

    public DataModelResource(DataModelRepository dataModelRepository) {
        this.dataModelRepository = dataModelRepository;
    }

    /**
     * {@code POST  /data-models} : Create a new dataModel.
     *
     * @param dataModel the dataModel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataModel, or with status {@code 400 (Bad Request)} if the dataModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-models")
    public ResponseEntity<DataModel> createDataModel(@RequestBody DataModel dataModel) throws URISyntaxException {
        log.debug("REST request to save DataModel : {}", dataModel);
        if (dataModel.getId() != null) {
            throw new BadRequestAlertException("A new dataModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataModel result = dataModelRepository.save(dataModel);
        return ResponseEntity.created(new URI("/api/data-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-models} : Updates an existing dataModel.
     *
     * @param dataModel the dataModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataModel,
     * or with status {@code 400 (Bad Request)} if the dataModel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-models")
    public ResponseEntity<DataModel> updateDataModel(@RequestBody DataModel dataModel) throws URISyntaxException {
        log.debug("REST request to update DataModel : {}", dataModel);
        if (dataModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataModel result = dataModelRepository.save(dataModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataModel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-models} : get all the dataModels.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataModels in body.
     */
    @GetMapping("/data-models")
    public List<DataModel> getAllDataModels(@RequestParam(required = false) String filter) {
        if ("querydata-is-null".equals(filter)) {
            log.debug("REST request to get all DataModels where queryData is null");
            return StreamSupport
                .stream(dataModelRepository.findAll().spliterator(), false)
                .filter(dataModel -> dataModel.getQueryData() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all DataModels");
        return dataModelRepository.findAll();
    }

    /**
     * {@code GET  /data-models/:id} : get the "id" dataModel.
     *
     * @param id the id of the dataModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-models/{id}")
    public ResponseEntity<DataModel> getDataModel(@PathVariable Long id) {
        log.debug("REST request to get DataModel : {}", id);
        Optional<DataModel> dataModel = dataModelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dataModel);
    }

    /**
     * {@code DELETE  /data-models/:id} : delete the "id" dataModel.
     *
     * @param id the id of the dataModel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-models/{id}")
    public ResponseEntity<Void> deleteDataModel(@PathVariable Long id) {
        log.debug("REST request to delete DataModel : {}", id);
        dataModelRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
