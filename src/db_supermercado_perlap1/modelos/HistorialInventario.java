/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_supermercado_perlap1.modelos;

import java.util.Date;

public class HistorialInventario {
    private int idInventario;
    private Producto producto;
    private int cantidad;
    private Date fechaModificacion;
    private String movimientoInventario;

    // Constructor
    public HistorialInventario(int idInventario, Producto producto, int cantidad, Date fechaModificacion, String movimientoInventario) {
        this.idInventario = idInventario;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fechaModificacion = fechaModificacion;
        this.movimientoInventario = movimientoInventario;
    }

    // Getters y setters

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getMovimientoInventario() {
        return movimientoInventario;
    }

    public void setMovimientoInventario(String movimientoInventario) {
        this.movimientoInventario = movimientoInventario;
    }
    
}
