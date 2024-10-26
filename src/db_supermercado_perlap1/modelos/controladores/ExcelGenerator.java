package db_supermercado_perlap1.modelos.controladores;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGenerator {

    private Workbook workbook;
    private Sheet sheet;
    private double precio; // Campo para almacenar el precio
    static final String FILE_PATH = "Salida_Consola_ControladorVenta.xlsx";

    public ExcelGenerator() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Ventas");
    }

    public void open() {
        // Aquí no se necesita abrir como en PDF, pero se podría preparar el libro si es necesario.
        System.out.println("Excel preparado para generar: " + FILE_PATH);
    }

    public void close() {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Excel generado correctamente en " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Error al cerrar el archivo Excel: " + e.getMessage());
        }
    }

    public void addText(String text) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        Cell cell = row.createCell(0);
        cell.setCellValue(text);
    }

    public void addHeader(String[] headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    public void addRow(String[] rowData) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        for (int i = 0; i < rowData.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rowData[i]);
        }
    }

    // Método para establecer el precio
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método para obtener el precio
    public double getPrecio() {
        return precio;
    }

    // Método para agregar el precio en una fila
    public void addPrecio() {
        addText("Precio: " + precio);
    }
}
