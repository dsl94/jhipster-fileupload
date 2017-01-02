package com.ferrum.fileupload.service.mapper;

import com.ferrum.fileupload.domain.*;
import com.ferrum.fileupload.service.dto.StudentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Student and its DTO StudentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudentMapper {

    StudentDTO studentToStudentDTO(Student student);

    List<StudentDTO> studentsToStudentDTOs(List<Student> students);

    Student studentDTOToStudent(StudentDTO studentDTO);

    List<Student> studentDTOsToStudents(List<StudentDTO> studentDTOs);
}
