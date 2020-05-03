package com.publicpmr.service;

import com.publicpmr.domain.Exchange;
import com.publicpmr.repository.ExchangeRepository;
import com.publicpmr.service.dto.ExchangeDTO;
import com.publicpmr.service.mapper.ExchangeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Exchange}.
 */
@Service
@Transactional
public class ExchangeService {

    private final Logger log = LoggerFactory.getLogger(ExchangeService.class);

    private final ExchangeRepository exchangeRepository;

    private final ExchangeMapper exchangeMapper;

    public ExchangeService(ExchangeRepository exchangeRepository, ExchangeMapper exchangeMapper) {
        this.exchangeRepository = exchangeRepository;
        this.exchangeMapper = exchangeMapper;
    }

    /**
     * Save a exchange.
     *
     * @param exchangeDTO the entity to save.
     * @return the persisted entity.
     */
    public ExchangeDTO save(ExchangeDTO exchangeDTO) {
        log.debug("Request to save Exchange : {}", exchangeDTO);
        Exchange exchange = exchangeMapper.toEntity(exchangeDTO);
        exchange = exchangeRepository.save(exchange);
        return exchangeMapper.toDto(exchange);
    }

    /**
     * Get all the exchanges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExchangeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Exchanges");
        return exchangeRepository.findAll(pageable)
            .map(exchangeMapper::toDto);
    }

    /**
     * Get all the exchanges with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ExchangeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return exchangeRepository.findAllWithEagerRelationships(pageable).map(exchangeMapper::toDto);
    }
    

    /**
     * Get one exchange by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExchangeDTO> findOne(Long id) {
        log.debug("Request to get Exchange : {}", id);
        return exchangeRepository.findOneWithEagerRelationships(id)
            .map(exchangeMapper::toDto);
    }

    /**
     * Delete the exchange by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Exchange : {}", id);
        exchangeRepository.deleteById(id);
    }
}
