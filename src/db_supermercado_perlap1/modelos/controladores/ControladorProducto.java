/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_supermercado_perlap1.modelos.controladores;

import db_supermercado_perlap1.DBConnection;
import db_supermercado_perlap1.modelos.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControladorProducto {

    Connection conn = DBConnection.getConnection();

    // Método para agregar un producto, recibe un objeto Producto como parámetro
    public boolean addProducto(Producto producto) {
        String sql = "INSERT INTO Producto (codigo, codigo_de_barras, nombre, descripcion, id_categoria, imagen, precio_normal, precio_promocion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer los valores en el PreparedStatement
            stmt.setString(1, producto.getCodigo());
            stmt.setString(2, producto.getCodigoDeBarras());
            stmt.setString(3, producto.getNombre());
            stmt.setString(4, producto.getDescripcion());
            stmt.setInt(5, producto.getIdCategoria());
            stmt.setString(6, producto.getImagen());
            stmt.setDouble(7, producto.getPrecioNormal());
            stmt.setDouble(8, producto.getPrecioPromocion());

            // Ejecutar la inserción
            int filasInsertadas = stmt.executeUpdate();

            // Verificar si la inserción fue exitosa
            return filasInsertadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar el producto: " + e.getMessage());
            e.printStackTrace();  // Imprimir el stack trace para más detalles
            return false;
        }
    }
}
