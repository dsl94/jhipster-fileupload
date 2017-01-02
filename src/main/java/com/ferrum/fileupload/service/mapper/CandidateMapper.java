package com.ferrum.fileupload.service.mapper;

import com.ferrum.fileupload.domain.*;
import com.ferrum.fileupload.service.dto.CandidateDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Candidate and its DTO CandidateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CandidateMapper {

    CandidateDTO candidateToCandidateDTO(Candidate candidate);

    List<CandidateDTO> candidatesToCandidateDTOs(List<Candidate> candidates);

    Candidate candidateDTOToCandidate(CandidateDTO candidateDTO);

    List<Candidate> candidateDTOsToCandidates(List<CandidateDTO> candidateDTOs);
}
