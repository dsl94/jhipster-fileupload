package com.ferrum.fileupload.repository;

import com.ferrum.fileupload.domain.Student;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Student entity.
 */
@SuppressWarnings("unused")
public interface StudentRepository extends JpaRepository<Student,Long> {

}
