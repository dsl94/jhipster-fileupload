package com.ferrum.fileupload.web.rest;

import com.ferrum.fileupload.FileuploadApp;

import com.ferrum.fileupload.domain.Candidate;
import com.ferrum.fileupload.repository.CandidateRepository;
import com.ferrum.fileupload.service.CandidateService;
import com.ferrum.fileupload.service.dto.CandidateDTO;
import com.ferrum.fileupload.service.mapper.CandidateMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CandidateResource REST controller.
 *
 * @see CandidateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileuploadApp.class)
public class CandidateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CV_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CV_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CV_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CV_FILE_CONTENT_TYPE = "image/png";

    @Inject
    private CandidateRepository candidateRepository;

    @Inject
    private CandidateMapper candidateMapper;

    @Inject
    private CandidateService candidateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCandidateMockMvc;

    private Candidate candidate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CandidateResource candidateResource = new CandidateResource();
        ReflectionTestUtils.setField(candidateResource, "candidateService", candidateService);
        this.restCandidateMockMvc = MockMvcBuilders.standaloneSetup(candidateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createEntity(EntityManager em) {
        Candidate candidate = new Candidate()
                .name(DEFAULT_NAME)
                .cvFile(DEFAULT_CV_FILE)
                .cvFileContentType(DEFAULT_CV_FILE_CONTENT_TYPE);
        return candidate;
    }

    @Before
    public void initTest() {
        candidate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidate() throws Exception {
        int databaseSizeBeforeCreate = candidateRepository.findAll().size();

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.candidateToCandidateDTO(candidate);

        restCandidateMockMvc.perform(post("/api/candidates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
                .andExpect(status().isCreated());

        // Validate the Candidate in the database
        List<Candidate> candidates = candidateRepository.findAll();
        assertThat(candidates).hasSize(databaseSizeBeforeCreate + 1);
        Candidate testCandidate = candidates.get(candidates.size() - 1);
        assertThat(testCandidate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCandidate.getCvFile()).isEqualTo(DEFAULT_CV_FILE);
        assertThat(testCandidate.getCvFileContentType()).isEqualTo(DEFAULT_CV_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCandidates() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidates
        restCandidateMockMvc.perform(get("/api/candidates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].cvFileContentType").value(hasItem(DEFAULT_CV_FILE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].cvFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_CV_FILE))));
    }

    @Test
    @Transactional
    public void getCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get the candidate
        restCandidateMockMvc.perform(get("/api/candidates/{id}", candidate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.cvFileContentType").value(DEFAULT_CV_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.cvFile").value(Base64Utils.encodeToString(DEFAULT_CV_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingCandidate() throws Exception {
        // Get the candidate
        restCandidateMockMvc.perform(get("/api/candidates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate
        Candidate updatedCandidate = candidateRepository.findOne(candidate.getId());
        updatedCandidate
                .name(UPDATED_NAME)
                .cvFile(UPDATED_CV_FILE)
                .cvFileContentType(UPDATED_CV_FILE_CONTENT_TYPE);
        CandidateDTO candidateDTO = candidateMapper.candidateToCandidateDTO(updatedCandidate);

        restCandidateMockMvc.perform(put("/api/candidates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
                .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidates = candidateRepository.findAll();
        assertThat(candidates).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidates.get(candidates.size() - 1);
        assertThat(testCandidate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCandidate.getCvFile()).isEqualTo(UPDATED_CV_FILE);
        assertThat(testCandidate.getCvFileContentType()).isEqualTo(UPDATED_CV_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);
        int databaseSizeBeforeDelete = candidateRepository.findAll().size();

        // Get the candidate
        restCandidateMockMvc.perform(delete("/api/candidates/{id}", candidate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Candidate> candidates = candidateRepository.findAll();
        assertThat(candidates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
