/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_supermercado_perlap1.modelos.controladores;

import db_supermercado_perlap1.DBConnection;
import db_supermercado_perlap1.modelos.HistorialInventario;
import db_supermercado_perlap1.modelos.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControladorInventario {

    public void agregarInventario(HistorialInventario inventario) {
        String sql = "INSERT INTO Inventario (id_producto, cantidad, fecha_modificacion, movimiento_inventario) "
                + "VALUES (?, ?, ?, ?)";

        // Usar try-with-resources para asegurar que la conexión y el PreparedStatement se cierren
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inventario.getProducto().getIdProducto());
            stmt.setInt(2, inventario.getCantidad());
            stmt.setDate(3, new java.sql.Date(inventario.getFechaModificacion().getTime()));
            stmt.setString(4, inventario.getMovimientoInventario());

            int filasInsertadas = stmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Existencia agregada correctamente al inventario.");
            } else {
                System.out.println("No se pudo agregar el inventario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar inventario: " + e.getMessage());
            e.printStackTrace();  // Para detalles adicionales en caso de error
        }
    }

    public void quitarInventario(int idProducto, int cantidad) {
        String sql = "UPDATE Inventario SET cantidad = cantidad - ? WHERE id_producto = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, idProducto);

            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Inventario actualizado correctamente.");
            } else {
                System.out.println("No se encontró el producto con ID: " + idProducto);
            }
        } catch (SQLException e) {
            System.out.println("Error al quitar inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Otros métodos para gestionar el inventario
}
