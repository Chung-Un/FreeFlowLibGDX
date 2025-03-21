/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.Progra2.flowfree.flowfreegame.FlowFreeGame;
import com.Progra2.flowfree.model.Usuario;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author chung
 */
public class ManejoNivel {
    private static ArrayList<Nivel> niveles;
    private static int nivelActual;
    private Usuario jugador;
    private FlowFreeGame FlowFree;
    
    
    public ManejoNivel(Usuario jugador, FlowFreeGame FlowFree, int nivel){
        this.jugador = jugador;
        this.FlowFree = FlowFree;
        niveles = new ArrayList();
        niveles.add(new Nivel1(5,35,jugador,FlowFree));
        niveles.add(new Nivel2(6,45,jugador,FlowFree));
        niveles.add(new Nivel3(6,55,jugador,FlowFree));
        niveles.add(new Nivel4(8,60,jugador,FlowFree));
        niveles.add(new Nivel5(10,100,jugador,FlowFree));
        nivelActual=nivel;
    }
    
    public Nivel getNivelActual(){
        return niveles.get(nivelActual);
    }
    
    public  void avanzarNivel() {
    if (nivelActual < niveles.size() - 1) {
        nivelActual++;
        System.out.println("Advanced to Level " + nivelActual + " with Grid Size: " + getNivelActual().sizeGrid);
    } else {
        getNivelActual().todosNivelesCompletados = true;
        System.out.println("All levels completed.");
    }
}

    
    public void reiniciarNivel() {
        getNivelActual().reiniciarNivel();
    }
}
