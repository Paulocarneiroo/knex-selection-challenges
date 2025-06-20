package com.knexdesafio.knexdesafio.services;

import com.knexdesafio.knexdesafio.entities.Deputy;
import com.knexdesafio.knexdesafio.entities.Expense;
import com.knexdesafio.knexdesafio.repositories.DeputyRepository;
import com.knexdesafio.knexdesafio.repositories.ExpenseRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CeapImportService {

    private DeputyRepository deputyRepository;
    private ExpenseRepository expenseRepository;

    public CeapImportService(DeputyRepository deputyRepository,
                             ExpenseRepository expenseRepository) {
        this.deputyRepository = deputyRepository;
        this.expenseRepository = expenseRepository;
    }

    public void importCsv(InputStream csvInputStream) throws IOException,
            CsvValidationException {
        try (Reader reader = new InputStreamReader(csvInputStream,
                StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(reader)) {

            String[] header = csvReader.readNext();
            if (header == null) {
                return;
            }

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                String name = getValueByHeader(header, line, "txNomeParlamentar");
                String uf = getValueByHeader(header, line, "sgUF");
                String cpf = getValueByHeader(header, line, "cpf");
                String party = getValueByHeader(header, line, "sgPartido");
                String supplier = getValueByHeader(header, line, "txtFornecedor");
                String netValue = getValueByHeader(header, line, "vlrLiquido");
                String emissionDate = getValueByHeader(header, line, "datEmissao");
                String urlDocument = getValueByHeader(header, line, "urlDocumento");

                if (uf == null || uf.trim().isEmpty()) {
                    continue;
                }

                Deputy deputy = deputyRepository.findByCpf(cpf)
                        .orElseGet(() -> {
                            Deputy d = new Deputy();
                            d.setName(name);
                            d.setUf(uf);
                            d.setCpf(cpf);
                            d.setParty(party);
                            return deputyRepository.save(d);
                        });

                Expense expense = new Expense();
                expense.setEmissionDate(LocalDate.parse(emissionDate,
                        DateTimeFormatter.ISO_DATE));
                expense.setSupplier(supplier);
                expense.setNetValue(new BigDecimal(netValue.
                        replace(",", ".")));
                expense.setUrlDocument(urlDocument);
                expense.setDeputy(deputy);

                expenseRepository.save(expense);
            }
        }
    }

    private String getValueByHeader(String[] header,
                                    String[] line,
                                    String ColumnName) {
        for (int i = 0; i < header.length; i++) {
            if (header[i].trim().equalsIgnoreCase(ColumnName.trim())) {
                return line[i];
            }
        }
        return null;
    }

}

