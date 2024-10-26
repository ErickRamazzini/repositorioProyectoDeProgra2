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

public class ControladorUsuario {
    
    private Connection conn;

    public ControladorUsuario() {
        this.conn = DBConnection.getConnection();
    }

    // Método para agregar un nuevo usuario
    public void agregarUsuario(String nombre, int idRol, String contraseña) {
        String query = "INSERT INTO Usuario (nombre, id_rol, contraseña) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, idRol);
            pstmt.setString(3, contraseña);
            pstmt.executeUpdate();
            System.out.println("Usuario agregado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar usuario: " + e.getMessage());
        }
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(int idUsuario) {
        String query = "DELETE FROM Usuario WHERE id_usuario = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idUsuario);
            pstmt.executeUpdate();
            System.out.println("Usuario eliminado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    // Método para mostrar roles disponibles
    public void mostrarRoles() {
        String query = "SELECT * FROM Rol";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_rol") + ", Rol: " + rs.getString("rol_del_personal"));
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar roles: " + e.getMessage());
        }
    }

    // Método para mostrar la lista de usuarios
    public void mostrarUsuarios() {
        String query = "SELECT u.id_usuario, u.nombre, r.rol_del_personal FROM Usuario u JOIN Rol r ON u.id_rol = r.id_rol";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("=== Lista de Usuarios ===");
            System.out.println("ID Usuario | Nombre | Rol");
            while (rs.next()) {
                System.out.println(rs.getInt("id_usuario") + " | " + rs.getString("nombre") + " | " + rs.getString("rol_del_personal"));
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar usuarios: " + e.getMessage());
        }
    }
}