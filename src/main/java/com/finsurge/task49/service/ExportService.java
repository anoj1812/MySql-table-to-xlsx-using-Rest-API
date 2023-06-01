package com.finsurge.task49.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import com.finsurge.task49.entity.Student;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExportService implements StudentService{
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Student> listStudents;

    public ExportService(List<Student> listStudents) {
        this.listStudents = listStudents;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Students");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Student ID", style);
        createCell(row, 1, "Student Name", style);
        createCell(row, 2, "Student Dept", style);
        createCell(row, 3, "Student Clg", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }else if (value instanceof String) {
            cell.setCellValue((String) value);
        }else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Student student : listStudents) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, student.getStudentId(), style);
            createCell(row, columnCount++, student.getStudentName(), style);
            createCell(row, columnCount++, student.getStudentDept(), style);
            createCell(row, columnCount++, student.getStudentClg(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();



        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        FileOutputStream file=new FileOutputStream("< file name/path >.xlsx");

        workbook.write(file);
        workbook.close();


        outputStream.close();

    }
}

