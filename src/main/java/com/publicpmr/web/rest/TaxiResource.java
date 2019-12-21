package com.publicpmr.web.rest;

import com.publicpmr.service.TaxiService;
import com.publicpmr.web.rest.errors.BadRequestAlertException;
import com.publicpmr.service.dto.TaxiDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.publicpmr.domain.Taxi}.
 */
@RestController
@RequestMapping("/api")
public class TaxiResource {

    private final Logger log = LoggerFactory.getLogger(TaxiResource.class);

    private static final String ENTITY_NAME = "taxi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxiService taxiService;

    public TaxiResource(TaxiService taxiService) {
        this.taxiService = taxiService;
    }

    /**
     * {@code POST  /taxis} : Create a new taxi.
     *
     * @param taxiDTO the taxiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxiDTO, or with status {@code 400 (Bad Request)} if the taxi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taxis")
    public ResponseEntity<TaxiDTO> createTaxi(@Valid @RequestBody TaxiDTO taxiDTO) throws URISyntaxException {
        log.debug("REST request to save Taxi : {}", taxiDTO);
        if (taxiDTO.getId() != null) {
            throw new BadRequestAlertException("A new taxi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxiDTO result = taxiService.save(taxiDTO);
        return ResponseEntity.created(new URI("/api/taxis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taxis} : Updates an existing taxi.
     *
     * @param taxiDTO the taxiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxiDTO,
     * or with status {@code 400 (Bad Request)} if the taxiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taxis")
    public ResponseEntity<TaxiDTO> updateTaxi(@Valid @RequestBody TaxiDTO taxiDTO) throws URISyntaxException {
        log.debug("REST request to update Taxi : {}", taxiDTO);
        if (taxiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxiDTO result = taxiService.save(taxiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /taxis} : get all the taxis.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxis in body.
     */
    @GetMapping("/taxis")
    public ResponseEntity<List<TaxiDTO>> getAllTaxis(Pageable pageable) {
        log.debug("REST request to get a page of Taxis");
        Page<TaxiDTO> page = taxiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /taxis/:id} : get the "id" taxi.
     *
     * @param id the id of the taxiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taxis/{id}")
    public ResponseEntity<TaxiDTO> getTaxi(@PathVariable Long id) {
        log.debug("REST request to get Taxi : {}", id);
        Optional<TaxiDTO> taxiDTO = taxiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxiDTO);
    }

    /**
     * {@code DELETE  /taxis/:id} : delete the "id" taxi.
     *
     * @param id the id of the taxiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taxis/{id}")
    public ResponseEntity<Void> deleteTaxi(@PathVariable Long id) {
        log.debug("REST request to delete Taxi : {}", id);
        taxiService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
