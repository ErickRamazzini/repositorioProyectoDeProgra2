package db_supermercado_perlap1.modelos.controladores;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFGenerator {

    private Document document;
    private double precio; // Campo para almacenar el precio
    static final String FILE_PATH = "Salida_Consola_ControladorVenta.pdf";

    public PDFGenerator() {
        document = new Document();
    }

    public void open() {
        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH));
            document.open();
        } catch (FileNotFoundException | DocumentException e) {
            System.out.println("Error al crear el documento PDF: " + e.getMessage());
        }
    }

    public void close() {
        if (document.isOpen()) {
            document.close();
        }
    }

    public void addText(String text) {
        try {
            Font font = new Font(Font.FontFamily.COURIER, 12);
            Paragraph paragraph = new Paragraph(text, font);
            document.add(paragraph);
        } catch (DocumentException e) {
            System.out.println("Error al escribir en el PDF: " + e.getMessage());
        }
    }

    public void addTable(PdfPTable table) {
        try {
            document.add(table);
        } catch (DocumentException e) {
            System.out.println("Error al agregar la tabla al PDF: " + e.getMessage());
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

    // Método para agregar el precio al documento PDF
    public void addPrecio() {
        addText("Precio: " + precio);
    }
}
