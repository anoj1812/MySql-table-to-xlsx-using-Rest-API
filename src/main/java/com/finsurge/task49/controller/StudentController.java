package com.finsurge.task49.controller;

import com.finsurge.task49.entity.Student;
import com.finsurge.task49.service.ExportService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.finsurge.task49.service.StudentServiceClass;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
    @Autowired private StudentServiceClass studentServiceClass;
    @Autowired private ExportService exportService;

    // Save operation
    @PostMapping("/students")
    public Student saveStudent(
            @RequestBody Student student)
    {
        return studentServiceClass.saveStudent(student);
    }

    // Read operation
    @GetMapping("/students")
    public List<Student> fetchStudentList()
    {
        return studentServiceClass.fetchStudentList();
    }

    // Update operation
    @PutMapping("/students/{id}")
    public Student
    updateStudent(@RequestBody Student student,
                  @PathVariable("id") Long studentId)
    {
        return studentServiceClass.updateStudent(
                student, studentId);
    }

    // Delete operation
    @DeleteMapping("/students/{id}")
    public String deleteStudentById(@PathVariable("id")
                                    Long studentId)
    {
        studentServiceClass.deleteStudentById(
                studentId);
        return "Deleted Successfully";
    }
    @GetMapping("/students/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("text/xls");
        DateFormat dateFormatter = new SimpleDateFormat("DDMMYYYY");
        String currentDate = dateFormatter.format(new Date());
        Date date = new Date();
        String strDateFormat = "HHMMSS";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String time=sdf.format(date);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=students_" + currentDate+"_"+time;
        response.setHeader(headerKey, headerValue);

        List<Student> listUsers = studentServiceClass.fetchStudentList();

        ExportService excelExporter = new ExportService(listUsers);

        excelExporter.export(response);
    }
}


