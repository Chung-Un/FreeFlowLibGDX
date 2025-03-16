/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author chung
 */
public class Jugador implements Serializable{
    private String username, password;
    protected int nivelesCompletados;

    
    private Calendar fechaRegistro;
    private Calendar ultimaFechaIngreso;
    protected ArrayList<Double> tiemposPorNivel;
    
    public Jugador(String username, String password){
        this.username = username;
        this.password = password;
        fechaRegistro = Calendar.getInstance();
        nivelesCompletados = 0;
        ultimaFechaIngreso = Calendar.getInstance();
        tiemposPorNivel = new ArrayList();
        
        for (int i = 0; i < 5; i++) { 
            tiemposPorNivel.add(0.0);
        }
    }
    
    public void guardarUsuario(){}
    
    public void cambiarPassword(){}
    
    public void eliminarUsuario(){}
    
    public void actualizarUltimaFechaIngreso(){
        ultimaFechaIngreso = Calendar.getInstance();
    }
    
    public int getNivelesCompletados() {
        return nivelesCompletados;
    }
    
    }
