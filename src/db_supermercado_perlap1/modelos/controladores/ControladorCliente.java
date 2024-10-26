/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package db_supermercado_perlap1.modelos.controladores;

import db_supermercado_perlap1.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorCliente {
    
    private Connection conn;

    public ControladorCliente() {
        this.conn = DBConnection.getConnection();
    }

    // Método para agregar un nuevo cliente
    public void agregarCliente(String nombre, String direccion, String telefono, String correoElectronico) {
        String sql = "INSERT INTO Cliente (nombre, direccion, telefono, correo_electronico) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, direccion);
            pstmt.setString(3, telefono);
            pstmt.setString(4, correoElectronico);
            pstmt.executeUpdate();
            System.out.println("Cliente agregado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar el cliente: " + e.getMessage());
        }
    }

    // Método para listar todos los clientes
    public List<String> listarClientes() {
        List<String> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String correoElectronico = rs.getString("correo_electronico");
                String clienteInfo = String.format("ID: %d, Nombre: %s, Dirección: %s, Teléfono: %s, Correo: %s",
                        idCliente, nombre, direccion, telefono, correoElectronico);
                clientes.add(clienteInfo);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return clientes;
    }
}