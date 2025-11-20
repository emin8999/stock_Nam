package com.backend.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.service.ExcelExportService;
import com.backend.service.ExcelExportService.ExportResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/excel-table")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExcelExportController {

    private final ExcelExportService excelExportService;

    @GetMapping
    public ResponseEntity<byte[]> export(
        @RequestParam String type,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        ExportResult result = excelExportService.export(type, from, to);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.filename() + "\"");

        return new ResponseEntity<>(result.data(), headers, HttpStatus.OK);
    }
}
