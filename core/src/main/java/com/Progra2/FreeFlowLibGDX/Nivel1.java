/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;


import com.Progra2.flowfree.flowfreegame.FlowFreeGame;
import com.Progra2.flowfree.model.Usuario;
import com.badlogic.gdx.graphics.Color;

/**
 *
 * @author chung
 */
public class Nivel1 extends Nivel {
   
    
    public Nivel1(int sizeGrid, float tiempoLimite, Usuario jugador, FlowFreeGame FlowFree) {
        super(sizeGrid, tiempoLimite, jugador,FlowFree,90);
        this.grid = new int[sizeGrid][sizeGrid];
       
    }

    @Override
    public void inicializar() {
        this.calcularOffsets();
        System.out.println("Inciando el nivel 1");
        //el grid se resettea
        for (int i = 0; i < sizeGrid; i++) {
            for (int j = 0; j < sizeGrid; j++) {
                grid[i][j] = 0; 
            }
        }
        
        puntos.add(new Punto(0, 0, Color.PINK));
        puntos.add(new Punto(1, 4, Color.PINK));
        puntos.add(new Punto(1, 3, Color.ORANGE));
        puntos.add(new Punto(2, 0, Color.ORANGE));
        puntos.add(new Punto(2, 1, Color.GREEN));
        puntos.add(new Punto(2, 4, Color.GREEN));
        puntos.add(new Punto(4, 0, Color.YELLOW));
        puntos.add(new Punto(3, 3, Color.YELLOW));
        puntos.add(new Punto(4, 1, Color.CYAN));
        puntos.add(new Punto(3, 4, Color.CYAN));
        
        for(Punto punto : puntos){
            grid[punto.getFila()][punto.getCol()] = 1; //significa que contiene un punto
            gridPuntos[punto.getFila()][punto.getCol()] = punto; //colocar el punto donde debe estar
            System.out.println("punto en " + punto.getFila() + "," + punto.getCol());

        }

        //Debug
        imprimirGrid();
        iniciarHiloTiempo();
        iniciarHiloColisiones();
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    
    @Override
    public void disposeNivel() {
        renderer.dispose();
        batch.dispose();
        texturaPink.dispose();
        texturaCyan.dispose();
        texturaYellow.dispose();
        texturaGreen.dispose();
        texturaOrange.dispose();
        detenerHiloTiempo();
        detenerHiloColisiones();
        music.dispose();
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    
    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float f, float f1) {
        return false;
    }
    
    
    
}
