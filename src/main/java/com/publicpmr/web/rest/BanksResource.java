package com.publicpmr.web.rest;

import com.publicpmr.service.BanksService;
import com.publicpmr.web.rest.errors.BadRequestAlertException;
import com.publicpmr.service.dto.BanksDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.publicpmr.domain.Banks}.
 */
@RestController
@RequestMapping("/api")
public class BanksResource {

    private final Logger log = LoggerFactory.getLogger(BanksResource.class);

    private static final String ENTITY_NAME = "banks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BanksService banksService;

    public BanksResource(BanksService banksService) {
        this.banksService = banksService;
    }

    /**
     * {@code POST  /banks} : Create a new banks.
     *
     * @param banksDTO the banksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new banksDTO, or with status {@code 400 (Bad Request)} if the banks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/banks")
    public ResponseEntity<BanksDTO> createBanks(@Valid @RequestBody BanksDTO banksDTO) throws URISyntaxException {
        log.debug("REST request to save Banks : {}", banksDTO);
        if (banksDTO.getId() != null) {
            throw new BadRequestAlertException("A new banks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BanksDTO result = banksService.save(banksDTO);
        return ResponseEntity.created(new URI("/api/banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banks} : Updates an existing banks.
     *
     * @param banksDTO the banksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banksDTO,
     * or with status {@code 400 (Bad Request)} if the banksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the banksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banks")
    public ResponseEntity<BanksDTO> updateBanks(@Valid @RequestBody BanksDTO banksDTO) throws URISyntaxException {
        log.debug("REST request to update Banks : {}", banksDTO);
        if (banksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BanksDTO result = banksService.save(banksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /banks} : get all the banks.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of banks in body.
     */
    @GetMapping("/banks")
    public List<BanksDTO> getAllBanks(@RequestParam(required = false) String filter) {
        if ("rates-is-null".equals(filter)) {
            log.debug("REST request to get all Bankss where rates is null");
            return banksService.findAllWhereRatesIsNull();
        }
        log.debug("REST request to get all Banks");
        return banksService.findAll();
    }

    /**
     * {@code GET  /banks/:id} : get the "id" banks.
     *
     * @param id the id of the banksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the banksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banks/{id}")
    public ResponseEntity<BanksDTO> getBanks(@PathVariable Long id) {
        log.debug("REST request to get Banks : {}", id);
        Optional<BanksDTO> banksDTO = banksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(banksDTO);
    }

    /**
     * {@code DELETE  /banks/:id} : delete the "id" banks.
     *
     * @param id the id of the banksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banks/{id}")
    public ResponseEntity<Void> deleteBanks(@PathVariable Long id) {
        log.debug("REST request to delete Banks : {}", id);
        banksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
