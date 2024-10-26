/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_supermercado_perlap1.modelos;


public class MetodoDePago {
    private int idMetodoDePago;
    private String categoriaDePago;

    // Constructor
    public MetodoDePago(int idMetodoDePago, String categoriaDePago) {
        this.idMetodoDePago = idMetodoDePago;
        this.categoriaDePago = categoriaDePago;
    }

    // Getters y setters

    public int getIdMetodoDePago() {
        return idMetodoDePago;
    }

    public void setIdMetodoDePago(int idMetodoDePago) {
        this.idMetodoDePago = idMetodoDePago;
    }

    public String getCategoriaDePago() {
        return categoriaDePago;
    }

    public void setCategoriaDePago(String categoriaDePago) {
        this.categoriaDePago = categoriaDePago;
    }
    
}