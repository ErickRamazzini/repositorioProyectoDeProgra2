/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package db_supermercado_perlap1;

import db_supermercado_perlap1.modelos.Cliente;
import db_supermercado_perlap1.modelos.HistorialInventario;
import db_supermercado_perlap1.modelos.MetodoDePago;
import db_supermercado_perlap1.modelos.Producto;
import db_supermercado_perlap1.modelos.Usuario;
import db_supermercado_perlap1.modelos.Venta;
import db_supermercado_perlap1.modelos.controladores.ControladorCliente;
import db_supermercado_perlap1.modelos.controladores.ControladorInventario;
import db_supermercado_perlap1.modelos.controladores.ControladorProducto;
import db_supermercado_perlap1.modelos.controladores.ControladorUsuario;
import db_supermercado_perlap1.modelos.controladores.ControladorVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

// Importaciones necesarias para Apache POI
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DBConnection dbConnection = new DBConnection();
        ControladorProducto controladorProducto = new ControladorProducto();
        int opcion;

        do {
            // Mostrar menú
            System.out.println("=== Menú ===");
            System.out.println("1. Agregar un producto");
            System.out.println("2. Existencia");
            System.out.println("3. Ventas");
            System.out.println("4. Cliente");
            System.out.println("5. Usuario");
            System.out.println("6. Detalle");
            System.out.println("7. Generar PDF");
            System.out.println("8. Generar Excel");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    agregarProducto(scanner, controladorProducto);
                    break;
                case 2:
                    mostrarExistencia(dbConnection.getConnection());
                    break;
                case 3:
                    manejarVentas(dbConnection.getConnection(), scanner);
                    break;
                case 4:
                    manejarClientes(scanner, dbConnection.getConnection());
                    break;
                case 5:
                    manejarUsuarios(scanner, dbConnection.getConnection());
                    break;
                case 6:
                    mostrarDetallesVentas(dbConnection.getConnection());
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    // Método para mostrar detalles de ventas
    private static void mostrarDetallesVentas(Connection conn) {
        String sql = "SELECT v.id_venta, v.id_cliente, v.id_usuario, v.fecha_hora FROM Venta v";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("=== Detalle de Ventas ===");
            while (rs.next()) {
                int idVenta = rs.getInt("id_venta");
                int idCliente = rs.getInt("id_cliente");
                int idUsuario = rs.getInt("id_usuario");
                Timestamp fechaHora = rs.getTimestamp("fecha_hora");

                System.out.printf("ID Venta: %d, ID Cliente: %d, ID Usuario: %d, Fecha: %s%n",
                        idVenta, idCliente, idUsuario, fechaHora);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los detalles de las ventas: " + e.getMessage());
        }
    }

    private static void manejarVentas(Connection conn, Scanner scanner) {
        System.out.println("=== Ventas ===");
        System.out.print("Ingrese el ID del producto para vender: ");
        int idProducto = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese la cantidad a vender: ");
        int cantidad = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese el ID del cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese el ID del usuario: ");
        int idUsuario = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese el ID del método de pago (1: Efectivo, 2: Tarjeta): ");
        int idMetodoDePago = Integer.parseInt(scanner.nextLine());

        // Validar el stock disponible
        int stock = verificarCantidadEnStock(conn, idProducto);
        if (stock < cantidad) {
            System.out.println("No hay suficiente stock para realizar esta venta. Stock disponible: " + stock);
            return;
        }

        // Obtener el precio del producto
        double precioUnitario = obtenerPrecioProducto(conn, idProducto);
        double totalVenta = precioUnitario * cantidad;

        // Aplicar recargo del 5% si el pago es con tarjeta
        if (idMetodoDePago == 2) {
            totalVenta *= 1.05;
        }

        // Calcular IVA (12%)
        double iva = totalVenta * 0.12;
        double totalConIva = totalVenta + iva;

        // Registrar la venta
        Venta venta = new Venta(idCliente, idUsuario, new java.util.Date(), idMetodoDePago, cantidad, iva);
        ControladorVenta controladorVenta = new ControladorVenta();
        controladorVenta.realizarVenta(venta);

        // Actualizar el inventario
        ControladorInventario controladorInventario = new ControladorInventario();
        controladorInventario.quitarInventario(idProducto, cantidad);

        System.out.printf("\n=== Factura ===%nCantidad: %d%nPrecio Unitario: %.2f%nTotal Venta: %.2f%nIVA: %.2f%nTotal a Pagar: %.2f%n",
                cantidad, precioUnitario, totalVenta, iva, totalConIva);
    }

    private static double obtenerPrecioProducto(Connection conn, int idProducto) {
        String sql = "SELECT precio_promocion FROM Producto WHERE id_producto = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("precio_promocion");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el precio del producto: " + e.getMessage());
        }
        return 0;
    }

    // Manejo de clientes
    private static void manejarClientes(Scanner scanner, Connection conn) {
        ControladorCliente controladorCliente = new ControladorCliente();
        System.out.println("=== Manejo de Clientes ===");
        System.out.println("1. Agregar un nuevo cliente");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                agregarCliente(scanner, controladorCliente);
                break;
            case 0:
                return;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }
    }

    private static void agregarCliente(Scanner scanner, ControladorCliente controladorCliente) {
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la dirección del cliente: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese el teléfono del cliente: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese el correo electrónico del cliente: ");
        String correoElectronico = scanner.nextLine();

        controladorCliente.agregarCliente(nombre, direccion, telefono, correoElectronico);
    }

    // Manejo de usuarios
    private static void manejarUsuarios(Scanner scanner, Connection conn) {
        ControladorUsuario controladorUsuario = new ControladorUsuario();
        int opcion;
        do {
            System.out.println("=== Manejo de Usuarios ===");
            System.out.println("1. Agregar usuario");
            System.out.println("2. Eliminar usuario");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    agregarUsuario(scanner, controladorUsuario);
                    break;
                case 2:
                    eliminarUsuario(scanner, controladorUsuario);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void agregarUsuario(Scanner scanner, ControladorUsuario controladorUsuario) {
        System.out.print("Ingrese el nombre del usuario: ");
        String nombre = scanner.nextLine();

        System.out.println("=== Roles disponibles ===");
        controladorUsuario.mostrarRoles();

        System.out.print("Seleccione el ID del rol: ");
        int idRol = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese la contraseña del usuario: ");
        String contraseña = scanner.nextLine();

        controladorUsuario.agregarUsuario(nombre, idRol, contraseña);
    }

    private static void eliminarUsuario(Scanner scanner, ControladorUsuario controladorUsuario) {
        controladorUsuario.mostrarUsuarios();
        System.out.print("Ingrese el ID del usuario a eliminar: ");
        int idUsuario = Integer.parseInt(scanner.nextLine());
        controladorUsuario.eliminarUsuario(idUsuario);
    }

    // Agregar producto
    private static void agregarProducto(Scanner scanner, ControladorProducto controladorProducto) {
        System.out.print("Ingrese el código del producto: ");
        String codigo = scanner.nextLine();
        System.out.print("Ingrese el código de barras: ");
        String codigoDeBarras = scanner.nextLine();
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la descripción del producto: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese el ID de la categoría: ");
        int idCategoria = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese la ruta de la imagen: ");
        String imagen = scanner.nextLine();
        System.out.print("Ingrese el precio normal: ");
        double precioNormal = Double.parseDouble(scanner.nextLine());
        System.out.print("Ingrese el precio de promoción: ");
        double precioPromocion = Double.parseDouble(scanner.nextLine());

        Producto producto = new Producto(0, codigo, codigoDeBarras, nombre, descripcion, idCategoria, imagen, precioNormal, precioPromocion);

        boolean productoGuardado = controladorProducto.addProducto(producto);
        if (productoGuardado) {
            System.out.println("Producto agregado correctamente.");
        } else {
            System.out.println("Error al agregar el producto.");
        }
    }

    // Verificar cantidad en stock
    private static int verificarCantidadEnStock(Connection conn, int idProducto) {
        String sql = "SELECT cantidad FROM Inventario WHERE id_producto = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt("cantidad") : 0;
        } catch (SQLException e) {
            System.out.println("Error al verificar la cantidad en stock: " + e.getMessage());
            return 0;
        }
    }

    private static void mostrarExistencia(Connection conn) {
        String sql = "SELECT p.id_producto, p.nombre, i.cantidad "
                + "FROM Producto p INNER JOIN Inventario i ON p.id_producto = i.id_producto";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("=== Existencia de Productos ===");
            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");

                System.out.printf("ID Producto: %d, Nombre: %s, Cantidad en Inventario: %d%n",
                        idProducto, nombre, cantidad);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la existencia de productos: " + e.getMessage());
        }
    }
}
