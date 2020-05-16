package com.publicpmr.service;

import com.publicpmr.domain.Banks;
import com.publicpmr.repository.BanksRepository;
import com.publicpmr.service.dto.BanksDTO;
import com.publicpmr.service.mapper.BanksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Banks}.
 */
@Service
@Transactional
public class BanksService {

    private final Logger log = LoggerFactory.getLogger(BanksService.class);

    private final BanksRepository banksRepository;

    private final BanksMapper banksMapper;

    public BanksService(BanksRepository banksRepository, BanksMapper banksMapper) {
        this.banksRepository = banksRepository;
        this.banksMapper = banksMapper;
    }

    /**
     * Save a banks.
     *
     * @param banksDTO the entity to save.
     * @return the persisted entity.
     */
    public BanksDTO save(BanksDTO banksDTO) {
        log.debug("Request to save Banks : {}", banksDTO);
        Banks banks = banksMapper.toEntity(banksDTO);
        banks = banksRepository.save(banks);
        return banksMapper.toDto(banks);
    }

    /**
     * Get all the banks.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BanksDTO> findAll() {
        log.debug("Request to get all Banks");
        return banksRepository.findAll().stream()
            .map(banksMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one banks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BanksDTO> findOne(Long id) {
        log.debug("Request to get Banks : {}", id);
        return banksRepository.findById(id)
            .map(banksMapper::toDto);
    }

    /**
     * Delete the banks by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Banks : {}", id);
        banksRepository.deleteById(id);
    }
}
