package com.ferrum.fileupload.service.impl;

import com.ferrum.fileupload.service.CandidateService;
import com.ferrum.fileupload.domain.Candidate;
import com.ferrum.fileupload.repository.CandidateRepository;
import com.ferrum.fileupload.service.dto.CandidateDTO;
import com.ferrum.fileupload.service.mapper.CandidateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Candidate.
 */
@Service
@Transactional
public class CandidateServiceImpl implements CandidateService{

    private final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);

    @Inject
    private CandidateRepository candidateRepository;

    @Inject
    private CandidateMapper candidateMapper;

    /**
     * Save a candidate.
     *
     * @param candidateDTO the entity to save
     * @return the persisted entity
     */
    public CandidateDTO save(CandidateDTO candidateDTO) {
        log.debug("Request to save Candidate : {}", candidateDTO);
        candidateDTO.setCvFile(null);
        Candidate candidate = candidateMapper.candidateDTOToCandidate(candidateDTO);
        candidate = candidateRepository.save(candidate);
        CandidateDTO result = candidateMapper.candidateToCandidateDTO(candidate);
        return result;
    }

    /**
     *  Get all the candidates.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CandidateDTO> findAll() {
        log.debug("Request to get all Candidates");
        List<CandidateDTO> result = candidateRepository.findAll().stream()
            .map(candidateMapper::candidateToCandidateDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one candidate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CandidateDTO findOne(Long id) {
        log.debug("Request to get Candidate : {}", id);
        Candidate candidate = candidateRepository.findOne(id);
        CandidateDTO candidateDTO = candidateMapper.candidateToCandidateDTO(candidate);
        return candidateDTO;
    }

    /**
     *  Delete the  candidate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Candidate : {}", id);
        candidateRepository.delete(id);
    }
}
