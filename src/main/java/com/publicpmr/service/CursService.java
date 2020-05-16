package com.publicpmr.service;

import com.publicpmr.domain.Curs;
import com.publicpmr.repository.CursRepository;
import com.publicpmr.service.dto.CursDTO;
import com.publicpmr.service.mapper.CursMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Curs}.
 */
@Service
@Transactional
public class CursService {

    private final Logger log = LoggerFactory.getLogger(CursService.class);

    private final CursRepository cursRepository;

    private final CursMapper cursMapper;

    public CursService(CursRepository cursRepository, CursMapper cursMapper) {
        this.cursRepository = cursRepository;
        this.cursMapper = cursMapper;
    }

    /**
     * Save a curs.
     *
     * @param cursDTO the entity to save.
     * @return the persisted entity.
     */
    public CursDTO save(CursDTO cursDTO) {
        log.debug("Request to save Curs : {}", cursDTO);
        Curs curs = cursMapper.toEntity(cursDTO);
        curs = cursRepository.save(curs);
        return cursMapper.toDto(curs);
    }

    /**
     * Get all the curs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CursDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Curs");
        return cursRepository.findAll(pageable)
            .map(cursMapper::toDto);
    }


    /**
     * Get one curs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CursDTO> findOne(Long id) {
        log.debug("Request to get Curs : {}", id);
        return cursRepository.findById(id)
            .map(cursMapper::toDto);
    }

    /**
     * Delete the curs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Curs : {}", id);
        cursRepository.deleteById(id);
    }
}
