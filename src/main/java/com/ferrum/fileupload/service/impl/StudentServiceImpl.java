package com.ferrum.fileupload.service.impl;

import com.ferrum.fileupload.service.StudentService;
import com.ferrum.fileupload.domain.Student;
import com.ferrum.fileupload.repository.StudentRepository;
import com.ferrum.fileupload.service.dto.StudentDTO;
import com.ferrum.fileupload.service.mapper.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Student.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService{

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Inject
    private StudentRepository studentRepository;

    @Inject
    private StudentMapper studentMapper;

    /**
     * Save a student.
     *
     * @param studentDTO the entity to save
     * @return the persisted entity
     */
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Request to save Student : {}", studentDTO);
        studentDTO.setCvFile(null);
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        student = studentRepository.save(student);
        StudentDTO result = studentMapper.studentToStudentDTO(student);
        return result;
    }

    /**
     *  Get all the students.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StudentDTO> findAll() {
        log.debug("Request to get all Students");
        List<StudentDTO> result = studentRepository.findAll().stream()
            .map(studentMapper::studentToStudentDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one student by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public StudentDTO findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        Student student = studentRepository.findOne(id);
        StudentDTO studentDTO = studentMapper.studentToStudentDTO(student);
        return studentDTO;
    }

    /**
     *  Delete the  student by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.delete(id);
    }
}
