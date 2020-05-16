package com.publicpmr.web.rest;

import com.publicpmr.service.RatesService;
import com.publicpmr.web.rest.errors.BadRequestAlertException;
import com.publicpmr.service.dto.RatesDTO;

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
 * REST controller for managing {@link com.publicpmr.domain.Rates}.
 */
@RestController
@RequestMapping("/api")
public class RatesResource {

    private final Logger log = LoggerFactory.getLogger(RatesResource.class);

    private static final String ENTITY_NAME = "rates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatesService ratesService;

    public RatesResource(RatesService ratesService) {
        this.ratesService = ratesService;
    }

    /**
     * {@code POST  /rates} : Create a new rates.
     *
     * @param ratesDTO the ratesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratesDTO, or with status {@code 400 (Bad Request)} if the rates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rates")
    public ResponseEntity<RatesDTO> createRates(@Valid @RequestBody RatesDTO ratesDTO) throws URISyntaxException {
        log.debug("REST request to save Rates : {}", ratesDTO);
        if (ratesDTO.getId() != null) {
            throw new BadRequestAlertException("A new rates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatesDTO result = ratesService.save(ratesDTO);
        return ResponseEntity.created(new URI("/api/rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rates} : Updates an existing rates.
     *
     * @param ratesDTO the ratesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratesDTO,
     * or with status {@code 400 (Bad Request)} if the ratesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rates")
    public ResponseEntity<RatesDTO> updateRates(@Valid @RequestBody RatesDTO ratesDTO) throws URISyntaxException {
        log.debug("REST request to update Rates : {}", ratesDTO);
        if (ratesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RatesDTO result = ratesService.save(ratesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rates} : get all the rates.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rates in body.
     */
    @GetMapping("/rates")
    public ResponseEntity<List<RatesDTO>> getAllRates(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Rates");
        Page<RatesDTO> page;
        if (eagerload) {
            page = ratesService.findAllWithEagerRelationships(pageable);
        } else {
            page = ratesService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rates/:id} : get the "id" rates.
     *
     * @param id the id of the ratesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rates/{id}")
    public ResponseEntity<RatesDTO> getRates(@PathVariable Long id) {
        log.debug("REST request to get Rates : {}", id);
        Optional<RatesDTO> ratesDTO = ratesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratesDTO);
    }

    /**
     * {@code DELETE  /rates/:id} : delete the "id" rates.
     *
     * @param id the id of the ratesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rates/{id}")
    public ResponseEntity<Void> deleteRates(@PathVariable Long id) {
        log.debug("REST request to delete Rates : {}", id);
        ratesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
