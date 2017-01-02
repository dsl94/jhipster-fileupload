package com.ferrum.fileupload.service;

import com.ferrum.fileupload.service.dto.StudentDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Student.
 */
public interface StudentService {

    /**
     * Save a student.
     *
     * @param studentDTO the entity to save
     * @return the persisted entity
     */
    StudentDTO save(StudentDTO studentDTO);

    /**
     *  Get all the students.
     *  
     *  @return the list of entities
     */
    List<StudentDTO> findAll();

    /**
     *  Get the "id" student.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentDTO findOne(Long id);

    /**
     *  Delete the "id" student.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
