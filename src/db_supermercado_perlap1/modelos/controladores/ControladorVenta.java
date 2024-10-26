package db_supermercado_perlap1.modelos.controladores;

import db_supermercado_perlap1.DBConnection;
import db_supermercado_perlap1.modelos.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;

public class ControladorVenta {

    private Connection conn;
    private PDFGenerator pdfGenerator;
    private ExcelGenerator excelGenerator;

    public ControladorVenta() {
        this.conn = DBConnection.getConnection();
        pdfGenerator = new PDFGenerator();
        pdfGenerator.open();

        excelGenerator = new ExcelGenerator();
        excelGenerator.open(); // Abre el Excel al iniciar el controlador

        if (conn == null) {
            String msg = "Error: La conexión a la base de datos es nula.";
            System.out.println(msg);
            pdfGenerator.addText(msg);
            closeDocuments(); // Cierra el PDF y el Excel si no hay conexión
        }
    }

    public void realizarVenta(Venta venta) {
        if (!existeUsuario(venta.getIdUsuario())) {
            String msg = "Error: El usuario con ID " + venta.getIdUsuario() + " no existe.";
            System.out.println(msg);
            pdfGenerator.addText(msg);
            closeDocuments(); // Cierra el PDF y el Excel si el usuario no existe
            return;
        }

        String sql = "INSERT INTO Venta (id_cliente, id_usuario, fecha_hora, id_metodo_de_pago, cantidad, precio) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, venta.getIdCliente());
            stmt.setInt(2, venta.getIdUsuario());
            stmt.setTimestamp(3, new java.sql.Timestamp(venta.getFechaHora().getTime()));
            stmt.setInt(4, venta.getIdMetodoDePago());
            stmt.setInt(5, venta.getCantidad()); // Agregar cantidad a la consulta
            stmt.setDouble(6, venta.getPrecio()); // Agregar precio a la consulta

            // Establecer el precio en el PDFGenerator
            pdfGenerator.setPrecio(venta.getPrecio()); // Asegúrate de que la clase Venta tenga un método getPrecio()

            int filasAfectadas = stmt.executeUpdate();
            String msg = (filasAfectadas > 0) ? "Venta registrada exitosamente." : "No se pudo registrar la venta.";
            System.out.println(msg);
            pdfGenerator.addText(msg); // Agrega mensaje de éxito o error

            // Agregar precio al PDF
            pdfGenerator.addPrecio(); // Agregar el precio al PDF

            // Llama al método para registrar todas las ventas después de registrar una nueva
            registrarVentas();
        } catch (SQLException e) {
            String msg = "Error al realizar venta: " + e.getMessage();
            System.out.println(msg);
            pdfGenerator.addText(msg);
        } finally {
            closeDocuments(); // Cierra el PDF y el Excel al finalizar la operación
        }
    }

    public boolean existeUsuario(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            String msg = "Error al verificar existencia del usuario: " + e.getMessage();
            System.out.println(msg);
            pdfGenerator.addText(msg);
        }
        return false;
    }

    public void registrarVentas() {
        String sql = "SELECT * FROM Venta"; // Asegúrate de que la tabla y columnas sean correctas
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            pdfGenerator.addText("Registro de Ventas:\n");

            // Agregar encabezados a Excel
            String[] headers = {"ID Cliente", "ID Usuario", "Cantidad", "Precio", "Fecha y Hora", "Método de Pago"};
            excelGenerator.addHeader(headers);

            // Crear tabla para mostrar los registros
            PdfPTable table = new PdfPTable(6); // Número de columnas
            table.setWidths(new float[]{1, 1, 1, 1, 2, 1}); // Ajustar ancho de columnas

            // Agregar encabezados de columna a PDF
            table.addCell("ID Cliente");
            table.addCell("ID Usuario");
            table.addCell("Cantidad");
            table.addCell("Precio");
            table.addCell("Fecha y Hora");
            table.addCell("Método de Pago");

            while (rs.next()) {
                // Recuperar valores de cada columna
                int idCliente = rs.getInt("id_cliente");
                int idUsuario = rs.getInt("id_usuario");
                int cantidad = rs.getInt("cantidad"); // Obtener la cantidad
                double precio = rs.getDouble("precio"); // Obtener el precio
                java.sql.Timestamp fechaHora = rs.getTimestamp("fecha_hora");
                int idMetodoDePago = rs.getInt("id_metodo_de_pago");

                // Agregar filas a la tabla de PDF
                table.addCell(String.valueOf(idCliente));
                table.addCell(String.valueOf(idUsuario));
                table.addCell(String.valueOf(cantidad)); // Agregar cantidad a la tabla
                table.addCell(String.valueOf(precio)); // Agregar precio a la tabla
                table.addCell(fechaHora.toString());
                table.addCell(String.valueOf(idMetodoDePago));

                // Agregar fila a Excel
                String[] rowData = {String.valueOf(idCliente), String.valueOf(idUsuario), String.valueOf(cantidad), String.valueOf(precio), fechaHora.toString(), String.valueOf(idMetodoDePago)};
                excelGenerator.addRow(rowData);
            }

            // Agregar la tabla al PDF
            pdfGenerator.addTable(table);
        } catch (SQLException | DocumentException e) {
            String msg = "Error al recuperar ventas: " + e.getMessage();
            System.out.println(msg);
            pdfGenerator.addText(msg);
        }
    }

    public void closeDocuments() {
        closePDF();
        excelGenerator.close();
        System.out.println("Excel generado correctamente en " + ExcelGenerator.FILE_PATH);
    }

    public void closePDF() {
        pdfGenerator.close();
        System.out.println("PDF generado correctamente en " + PDFGenerator.FILE_PATH);
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
