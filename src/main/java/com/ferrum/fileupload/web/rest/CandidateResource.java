package com.ferrum.fileupload.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ferrum.fileupload.service.CandidateService;
import com.ferrum.fileupload.web.rest.util.HeaderUtil;
import com.ferrum.fileupload.service.dto.CandidateDTO;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Candidate.
 */
@RestController
@RequestMapping("/api")
public class CandidateResource {

    private final Logger log = LoggerFactory.getLogger(CandidateResource.class);

    @Inject
    private CandidateService candidateService;

    /**
     * POST  /candidates : Create a new candidate.
     *
     * @param candidateDTO the candidateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateDTO, or with status 400 (Bad Request) if the candidate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidates")
    @Timed
    public ResponseEntity<CandidateDTO> createCandidate(@RequestBody CandidateDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to save Candidate : {}", candidateDTO);
        if (candidateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("candidate", "idexists", "A new candidate cannot already have an ID")).body(null);
        }
        byte[] file = candidateDTO.getCvFile();
        CandidateDTO result = candidateService.save(candidateDTO);
        if(file != null) {
            try {
                FileUtils.writeByteArrayToFile(new File("/Users/Nemanja/Documents/uploads/" + result.getId() + ".pdf"), file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.created(new URI("/api/candidates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("candidate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidates : Updates an existing candidate.
     *
     * @param candidateDTO the candidateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateDTO,
     * or with status 400 (Bad Request) if the candidateDTO is not valid,
     * or with status 500 (Internal Server Error) if the candidateDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidates")
    @Timed
    public ResponseEntity<CandidateDTO> updateCandidate(@RequestBody CandidateDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to update Candidate : {}", candidateDTO);
        if (candidateDTO.getId() == null) {
            return createCandidate(candidateDTO);
        }
        if(candidateDTO.getCvFile() != null) {
            Path existing = Paths.get("/Users/Nemanja/Documents/uploads/" + candidateDTO.getId() + ".pdf");
            if (Files.exists(existing)) {
                try {
                    Files.delete(existing);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileUtils.writeByteArrayToFile(new File("/Users/Nemanja/Documents/uploads/" + candidateDTO.getId() + ".pdf"), candidateDTO.getCvFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CandidateDTO result = candidateService.save(candidateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("candidate", candidateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidates : get all the candidates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidates in body
     */
    @GetMapping("/candidates")
    @Timed
    public List<CandidateDTO> getAllCandidates() {
        log.debug("REST request to get all Candidates");
        return candidateService.findAll();
    }

    /**
     * GET  /candidates/:id : get the "id" candidate.
     *
     * @param id the id of the candidateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/candidates/{id}")
    @Timed
    public ResponseEntity<CandidateDTO> getCandidate(@PathVariable Long id) {
        log.debug("REST request to get Candidate : {}", id);
        CandidateDTO candidateDTO = candidateService.findOne(id);
        return Optional.ofNullable(candidateDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /candidates/:id : delete the "id" candidate.
     *
     * @param id the id of the candidateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidates/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        log.debug("REST request to delete Candidate : {}", id);
        candidateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("candidate", id.toString())).build();
    }

}
