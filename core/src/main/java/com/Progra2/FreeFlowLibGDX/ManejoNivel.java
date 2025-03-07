/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import java.util.ArrayList;

/**
 *
 * @author chung
 */
public class ManejoNivel {
    private ArrayList<Nivel> niveles;
    private int nivelActual;
    private Jugador jugador;
    
    public ManejoNivel(Jugador jugador){
        this.jugador = jugador;
        niveles = new ArrayList();
        niveles.add(new Nivel1(5,45,jugador));
        nivelActual=0;
    }
    
    public Nivel getNivelActual(){
        return niveles.get(nivelActual);
    }
    
    public void avanzarNivel(){
        if (nivelActual < niveles.size() - 1) {
            nivelActual++;
        } else {
            //todos los niveles ya se completaron
        }
    }
    
    public void reiniciarNivel() {
        getNivelActual().reiniciarNivel();
    }
}
