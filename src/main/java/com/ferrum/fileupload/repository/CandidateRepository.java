package com.ferrum.fileupload.repository;

import com.ferrum.fileupload.domain.Candidate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Candidate entity.
 */
@SuppressWarnings("unused")
public interface CandidateRepository extends JpaRepository<Candidate,Long> {

}
