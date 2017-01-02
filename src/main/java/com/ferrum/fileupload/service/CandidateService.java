package com.ferrum.fileupload.service;

import com.ferrum.fileupload.service.dto.CandidateDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Candidate.
 */
public interface CandidateService {

    /**
     * Save a candidate.
     *
     * @param candidateDTO the entity to save
     * @return the persisted entity
     */
    CandidateDTO save(CandidateDTO candidateDTO);

    /**
     *  Get all the candidates.
     *  
     *  @return the list of entities
     */
    List<CandidateDTO> findAll();

    /**
     *  Get the "id" candidate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CandidateDTO findOne(Long id);

    /**
     *  Delete the "id" candidate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
