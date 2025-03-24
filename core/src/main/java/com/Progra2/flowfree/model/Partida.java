package com.Progra2.flowfree.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Nadiesda Fuentes
 */
public class Partida implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int nivel;
    private long tiempo;
    private boolean completado;
    private Date fecha;
    
    public Partida(int nivel, long tiempo, boolean completado) {
        this.nivel = nivel;
        this.tiempo = tiempo;
        this.completado = completado;
        this.fecha = new Date(); 
    }
    
    public Partida(int nivel, long tiempo, boolean completado, Date fecha) {
        this.nivel = nivel;
        this.tiempo = tiempo;
        this.completado = completado;
        this.fecha = fecha;
    }
    

    public int getNivel() {
        return nivel;
    }
    

    public long getTiempo() {
        return tiempo;
    }
    
    public boolean isCompletado() {
        return completado;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    @Override
    public String toString() {
        return "Nivel: " + nivel + 
               ", Fecha: " + fecha + 
               ", Tiempo: " + tiempo + "s" + 
               ", Completado: " + (completado ? "Sí" : "No");
    }
}