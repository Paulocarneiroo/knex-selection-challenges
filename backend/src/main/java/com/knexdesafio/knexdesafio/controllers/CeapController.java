package com.knexdesafio.knexdesafio.controllers;

import com.knexdesafio.knexdesafio.services.CeapImportService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class CeapController {

    private CeapImportService service;

    public CeapController(CeapImportService service) {
        this.service = service;
    }

    @PostMapping("/upload-ceap")
    public ResponseEntity<?> uploadCeap(@RequestParam("file") MultipartFile file) {
        try {
            service.importCsv(file.getInputStream());
            return ResponseEntity.ok("Importação realizada com sucesso");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Erro ao processar arquivo");
        }catch (CsvValidationException e){
            return ResponseEntity.badRequest().body("Erro de validação do csv");
        }
    }
}

