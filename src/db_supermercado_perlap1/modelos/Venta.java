package db_supermercado_perlap1.modelos;

import java.util.Date;


public class Venta {
    private int idCliente;
    private int idUsuario;
    private Date fechaHora;
    private int idMetodoDePago;
    private int cantidad;  // Nuevo atributo
    private double precio; // Nuevo atributo para el precio

    // Constructor modificado para incluir cantidad y precio
    public Venta(int idCliente, int idUsuario, Date fechaHora, int idMetodoDePago, int cantidad, double precio) {
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.fechaHora = fechaHora;
        this.idMetodoDePago = idMetodoDePago;
        this.cantidad = cantidad;  // Inicialización del nuevo atributo
        this.precio = precio;      // Inicialización del nuevo atributo
    }

    // Métodos get para todos los atributos
    public int getIdCliente() {
        return idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public int getIdMetodoDePago() {
        return idMetodoDePago;
    }

    public int getCantidad() {  // Nuevo método get
        return cantidad;
    }

    public double getPrecio() { // Nuevo método get para el precio
        return precio;
    }

    // Métodos set para todos los atributos
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setIdMetodoDePago(int idMetodoDePago) {
        this.idMetodoDePago = idMetodoDePago;
    }

    public void setCantidad(int cantidad) {  // Nuevo método set
        this.cantidad = cantidad;
    }

    public void setPrecio(double precio) { // Nuevo método set para el precio
        this.precio = precio;
    }

    // Métodos no implementados
    public String getNumeroFactura() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}