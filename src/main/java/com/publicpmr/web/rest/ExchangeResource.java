package com.publicpmr.web.rest;

import com.publicpmr.service.ExchangeService;
import com.publicpmr.web.rest.errors.BadRequestAlertException;
import com.publicpmr.service.dto.ExchangeDTO;

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
 * REST controller for managing {@link com.publicpmr.domain.Exchange}.
 */
@RestController
@RequestMapping("/api")
public class ExchangeResource {

    private final Logger log = LoggerFactory.getLogger(ExchangeResource.class);

    private static final String ENTITY_NAME = "exchange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExchangeService exchangeService;

    public ExchangeResource(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    /**
     * {@code POST  /exchanges} : Create a new exchange.
     *
     * @param exchangeDTO the exchangeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exchangeDTO, or with status {@code 400 (Bad Request)} if the exchange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exchanges")
    public ResponseEntity<ExchangeDTO> createExchange(@Valid @RequestBody ExchangeDTO exchangeDTO) throws URISyntaxException {
        log.debug("REST request to save Exchange : {}", exchangeDTO);
        if (exchangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new exchange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExchangeDTO result = exchangeService.save(exchangeDTO);
        return ResponseEntity.created(new URI("/api/exchanges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exchanges} : Updates an existing exchange.
     *
     * @param exchangeDTO the exchangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exchangeDTO,
     * or with status {@code 400 (Bad Request)} if the exchangeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exchangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exchanges")
    public ResponseEntity<ExchangeDTO> updateExchange(@Valid @RequestBody ExchangeDTO exchangeDTO) throws URISyntaxException {
        log.debug("REST request to update Exchange : {}", exchangeDTO);
        if (exchangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExchangeDTO result = exchangeService.save(exchangeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exchangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exchanges} : get all the exchanges.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exchanges in body.
     */
    @GetMapping("/exchanges")
    public ResponseEntity<List<ExchangeDTO>> getAllExchanges(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Exchanges");
        Page<ExchangeDTO> page;
        if (eagerload) {
            page = exchangeService.findAllWithEagerRelationships(pageable);
        } else {
            page = exchangeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /exchanges/:id} : get the "id" exchange.
     *
     * @param id the id of the exchangeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exchangeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exchanges/{id}")
    public ResponseEntity<ExchangeDTO> getExchange(@PathVariable Long id) {
        log.debug("REST request to get Exchange : {}", id);
        Optional<ExchangeDTO> exchangeDTO = exchangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exchangeDTO);
    }

    /**
     * {@code DELETE  /exchanges/:id} : delete the "id" exchange.
     *
     * @param id the id of the exchangeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exchanges/{id}")
    public ResponseEntity<Void> deleteExchange(@PathVariable Long id) {
        log.debug("REST request to delete Exchange : {}", id);
        exchangeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
