/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

/**
 *
 * @author chung
 */
public class Conexion {
    private Punto inicio, fin;

    public Punto getInicio() {
        return inicio;
    }

    public void setInicio(Punto inicio) {
        this.inicio = inicio;
    }

    public Punto getFin() {
        return fin;
    }

    public void setFin(Punto fin) {
        this.fin = fin;
    }
    
    public Conexion(Punto inicio, Punto fin){
        this.inicio = inicio;
        this.fin = fin;
    }
}
