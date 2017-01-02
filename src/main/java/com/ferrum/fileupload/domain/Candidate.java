package com.ferrum.fileupload.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Candidate.
 */
@Entity
@Table(name = "candidate")
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "cv_file")
    private byte[] cvFile;

    @Column(name = "cv_file_content_type")
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

    public Candidate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getCvFile() {
        return cvFile;
    }

    public Candidate cvFile(byte[] cvFile) {
        this.cvFile = cvFile;
        return this;
    }

    public void setCvFile(byte[] cvFile) {
        this.cvFile = cvFile;
    }

    public String getCvFileContentType() {
        return cvFileContentType;
    }

    public Candidate cvFileContentType(String cvFileContentType) {
        this.cvFileContentType = cvFileContentType;
        return this;
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
        Candidate candidate = (Candidate) o;
        if(candidate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, candidate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", cvFile='" + cvFile + "'" +
            ", cvFileContentType='" + cvFileContentType + "'" +
            '}';
    }
}
