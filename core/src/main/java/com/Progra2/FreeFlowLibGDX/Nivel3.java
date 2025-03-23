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
public class Nivel3 extends Nivel{
    public Nivel3 (int sizeGrid, double tiempoLimite, Usuario jugador, FlowFreeGame FlowFree ){
        super(sizeGrid, tiempoLimite, jugador, FlowFree,80);
        this.grid= new int[sizeGrid][sizeGrid];
        this.numeroNivel=3;
        
    }

    @Override
    public void inicializar() {
        this.calcularOffsets();
         System.out.println("Inicializando nivel 3");
        for (int i = 0; i < sizeGrid; i++) {
            for (int j = 0; j < sizeGrid; j++) {
                grid[i][j] = 0; 
            }
        }
        
        puntos.add(new Punto(0, 4 , Color.PINK));
        puntos.add(new Punto(2, 5, Color.PINK));
        puntos.add(new Punto(2, 1, Color.ORANGE));
        puntos.add(new Punto(3, 5, Color.ORANGE));
        puntos.add(new Punto(5, 1, Color.GREEN));
        puntos.add(new Punto(5, 5, Color.GREEN));
        puntos.add(new Punto(2, 4, Color.PURPLE));
        puntos.add(new Punto(5, 0, Color.PURPLE));
        puntos.add(new Punto(4, 1, Color.CYAN));
        puntos.add(new Punto(4, 3, Color.CYAN));
        
        for(Punto punto : puntos){
            grid[punto.getFila()][punto.getCol()] = 1; //significa que contiene un punto
            gridPuntos[punto.getFila()][punto.getCol()] = punto; //colocar el punto donde debe estar
            System.out.println("punto en " + punto.getFila() + "," + punto.getCol());

        }

        //Debug
        for (int i = 0; i < sizeGrid; i++) {
        for (int j = 0; j < sizeGrid; j++) {
            System.out.print(grid[i][j] + " ");
        }
        System.out.println();
        }
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
        texturaOrange.dispose();
        texturaGreen.dispose();
        texturaPurple.dispose();
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