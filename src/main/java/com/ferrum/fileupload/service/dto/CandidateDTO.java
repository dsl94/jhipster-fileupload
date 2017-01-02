package com.ferrum.fileupload.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;


/**
 * A DTO for the Candidate entity.
 */
public class CandidateDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private byte[] cvFile;

    private String cvFileContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public byte[] getCvFile() {
        return cvFile;
    }

    public void setCvFile(byte[] cvFile) {
        this.cvFile = cvFile;
    }

    public String getCvFileContentType() {
        return cvFileContentType;
    }

    public void setCvFileContentType(String cvFileContentType) {
        this.cvFileContentType = cvFileContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CandidateDTO candidateDTO = (CandidateDTO) o;

        if ( ! Objects.equals(id, candidateDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CandidateDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", cvFile='" + cvFile + "'" +
            '}';
    }
}
