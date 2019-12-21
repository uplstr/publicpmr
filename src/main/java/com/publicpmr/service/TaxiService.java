package com.publicpmr.service;

import com.publicpmr.domain.Taxi;
import com.publicpmr.repository.TaxiRepository;
import com.publicpmr.service.dto.TaxiDTO;
import com.publicpmr.service.mapper.TaxiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Taxi}.
 */
@Service
@Transactional
public class TaxiService {

    private final Logger log = LoggerFactory.getLogger(TaxiService.class);

    private final TaxiRepository taxiRepository;

    private final TaxiMapper taxiMapper;

    public TaxiService(TaxiRepository taxiRepository, TaxiMapper taxiMapper) {
        this.taxiRepository = taxiRepository;
        this.taxiMapper = taxiMapper;
    }

    /**
     * Save a taxi.
     *
     * @param taxiDTO the entity to save.
     * @return the persisted entity.
     */
    public TaxiDTO save(TaxiDTO taxiDTO) {
        log.debug("Request to save Taxi : {}", taxiDTO);
        Taxi taxi = taxiMapper.toEntity(taxiDTO);
        taxi = taxiRepository.save(taxi);
        return taxiMapper.toDto(taxi);
    }

    /**
     * Get all the taxis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Taxis");
        return taxiRepository.findAll(pageable)
            .map(taxiMapper::toDto);
    }


    /**
     * Get one taxi by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaxiDTO> findOne(Long id) {
        log.debug("Request to get Taxi : {}", id);
        return taxiRepository.findById(id)
            .map(taxiMapper::toDto);
    }

    /**
     * Delete the taxi by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxi : {}", id);
        taxiRepository.deleteById(id);
    }
}
