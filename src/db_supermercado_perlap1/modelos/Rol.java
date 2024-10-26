/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_supermercado_perlap1.modelos;

public class Rol {
    private int idRol;
    private String rolDelPersonal;

    // Constructor
    public Rol(int idRol, String rolDelPersonal) {
        this.idRol = idRol;
        this.rolDelPersonal = rolDelPersonal;
    }

    // Getters y setters
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRolDelPersonal() {
        return rolDelPersonal;
    }

    public void setRolDelPersonal(String rolDelPersonal) {
        this.rolDelPersonal = rolDelPersonal;
    }
}
