package com.finsurge.task49.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.finsurge.task49.entity.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{ }

