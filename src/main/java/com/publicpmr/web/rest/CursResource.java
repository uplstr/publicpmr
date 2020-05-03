package com.publicpmr.web.rest;

import com.publicpmr.service.CursService;
import com.publicpmr.web.rest.errors.BadRequestAlertException;
import com.publicpmr.service.dto.CursDTO;

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
 * REST controller for managing {@link com.publicpmr.domain.Curs}.
 */
@RestController
@RequestMapping("/api")
public class CursResource {

    private final Logger log = LoggerFactory.getLogger(CursResource.class);

    private static final String ENTITY_NAME = "curs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CursService cursService;

    public CursResource(CursService cursService) {
        this.cursService = cursService;
    }

    /**
     * {@code POST  /curs} : Create a new curs.
     *
     * @param cursDTO the cursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cursDTO, or with status {@code 400 (Bad Request)} if the curs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/curs")
    public ResponseEntity<CursDTO> createCurs(@Valid @RequestBody CursDTO cursDTO) throws URISyntaxException {
        log.debug("REST request to save Curs : {}", cursDTO);
        if (cursDTO.getId() != null) {
            throw new BadRequestAlertException("A new curs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CursDTO result = cursService.save(cursDTO);
        return ResponseEntity.created(new URI("/api/curs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /curs} : Updates an existing curs.
     *
     * @param cursDTO the cursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cursDTO,
     * or with status {@code 400 (Bad Request)} if the cursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/curs")
    public ResponseEntity<CursDTO> updateCurs(@Valid @RequestBody CursDTO cursDTO) throws URISyntaxException {
        log.debug("REST request to update Curs : {}", cursDTO);
        if (cursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CursDTO result = cursService.save(cursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cursDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /curs} : get all the curs.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of curs in body.
     */
    @GetMapping("/curs")
    public ResponseEntity<List<CursDTO>> getAllCurs(Pageable pageable) {
        log.debug("REST request to get a page of Curs");
        Page<CursDTO> page = cursService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /curs/:id} : get the "id" curs.
     *
     * @param id the id of the cursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/curs/{id}")
    public ResponseEntity<CursDTO> getCurs(@PathVariable Long id) {
        log.debug("REST request to get Curs : {}", id);
        Optional<CursDTO> cursDTO = cursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cursDTO);
    }

    /**
     * {@code DELETE  /curs/:id} : delete the "id" curs.
     *
     * @param id the id of the cursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/curs/{id}")
    public ResponseEntity<Void> deleteCurs(@PathVariable Long id) {
        log.debug("REST request to delete Curs : {}", id);
        cursService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
