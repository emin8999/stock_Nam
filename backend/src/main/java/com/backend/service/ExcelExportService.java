package com.backend.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.backend.dto.MaterialMovementDTO;
import com.backend.dto.ProfitLossDTO;
import com.backend.dto.StockSnapshotDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private final StockService stockService;

    public ExportResult export(String type, LocalDate from, LocalDate to) {
        return switch (type.toLowerCase()) {
            case "stock" -> exportStockSnapshot();
            case "movements" -> exportMovements();
            case "profit-loss" -> exportProfitLoss(from, to);
            default -> throw new IllegalArgumentException("Unknown export type: " + type);
        };
    }

    private ExportResult exportStockSnapshot() {
        List<StockSnapshotDTO> rows = stockService.getStockSnapshot();
        byte[] data = buildWorkbook("Stock", sheet -> {
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Code");
            header.createCell(1).setCellValue("Material");
            header.createCell(2).setCellValue("On hand (kg)");
            header.createCell(3).setCellValue("Avg price (AZN/kg)");
            header.createCell(4).setCellValue("Inventory value (AZN)");
            header.createCell(5).setCellValue("Min level (kg)");
            header.createCell(6).setCellValue("Status");

            int r = 1;
            for (StockSnapshotDTO dto : rows) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(dto.getCode());
                row.createCell(1).setCellValue(dto.getName());
                row.createCell(2).setCellValue(toDouble(dto.getOnHandKg()));
                row.createCell(3).setCellValue(toDouble(dto.getAvgCostPerKg()));
                row.createCell(4).setCellValue(toDouble(dto.getInventoryValue()));
                row.createCell(5).setCellValue(toDouble(dto.getMinLevel()));
                row.createCell(6).setCellValue(dto.getStatus());
            }
        });
        return new ExportResult("stock_snapshot.xlsx", data);
    }

    private ExportResult exportMovements() {
        List<MaterialMovementDTO> rows = stockService.getMaterialMovements();
        byte[] data = buildWorkbook("Movements", sheet -> {
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Code");
            header.createCell(1).setCellValue("Material");
            header.createCell(2).setCellValue("Incoming (kg)");
            header.createCell(3).setCellValue("Incoming (AZN)");
            header.createCell(4).setCellValue("Issued (kg)");
            header.createCell(5).setCellValue("Issued (AZN)");

            int r = 1;
            for (MaterialMovementDTO dto : rows) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(dto.getCode());
                row.createCell(1).setCellValue(dto.getName());
                row.createCell(2).setCellValue(toDouble(dto.getInQty()));
                row.createCell(3).setCellValue(toDouble(dto.getInSum()));
                row.createCell(4).setCellValue(toDouble(dto.getOutQty()));
                row.createCell(5).setCellValue(toDouble(dto.getOutSum()));
            }
        });
        return new ExportResult("material_movements.xlsx", data);
    }

    private ExportResult exportProfitLoss(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Period 'from' and 'to' are required for profit-loss export");
        }
        ProfitLossDTO pl = stockService.getProfitLoss(from, to);
        byte[] data = buildWorkbook("P&L", sheet -> {
            Row period = sheet.createRow(0);
            period.createCell(0).setCellValue("Period");
            period.createCell(1).setCellValue(from + " - " + to);

            String[][] rows = {
                {"Metric", "Amount (AZN)"},
                {"Revenue", formatNumber(pl.getRevenue())},
                {"COGS (issues)", formatNumber(pl.getCogs())},
                {"Gross profit", formatNumber(pl.getGrossProfit())},
                {"Expenses", formatNumber(pl.getExpenses())},
                {"Net profit", formatNumber(pl.getNetProfit())}
            };

            for (int i = 0; i < rows.length; i++) {
                Row row = sheet.createRow(i + 2);
                row.createCell(0).setCellValue(rows[i][0]);
                row.createCell(1).setCellValue(rows[i][1]);
            }
        });
        return new ExportResult(String.format("profit_loss_%s_%s.xlsx", from, to), data);
    }

    private byte[] buildWorkbook(String sheetName, Consumer<Sheet> sheetBuilder) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);
            sheetBuilder.accept(sheet);
            autoSize(sheet);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to build Excel export", e);
        }
    }

    private void autoSize(Sheet sheet) {
        Row header = sheet.getRow(0);
        if (header == null) {
            return;
        }
        int cells = header.getLastCellNum();
        for (int i = 0; i < cells; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private double toDouble(BigDecimal value) {
        return value == null ? 0d : value.doubleValue();
    }

    private String formatNumber(BigDecimal value) {
        return value == null ? "0" : value.stripTrailingZeros().toPlainString();
    }

    public record ExportResult(String filename, byte[] data) { }
}
