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
public class Nivel4 extends Nivel{
    
    public Nivel4(int sizeGrid, double tiempoLimite, Usuario jugador, FlowFreeGame FlowFree){
        super(sizeGrid, tiempoLimite, jugador, FlowFree,60);
        this.grid= new int[sizeGrid][sizeGrid];
        this.numeroNivel=4;
    }

    @Override
    public void inicializar() {
        this.calcularOffsets();
         System.out.println("Inicializando nivel 4");
        for (int i = 0; i < sizeGrid; i++) {
            for (int j = 0; j < sizeGrid; j++) {
                grid[i][j] = 0; 
            }
        }
        
        puntos.add(new Punto(7, 0 , Color.PINK));
        puntos.add(new Punto(6, 6, Color.PINK));
        puntos.add(new Punto(1, 6, Color.ORANGE));
        puntos.add(new Punto(4, 3, Color.ORANGE));
        puntos.add(new Punto(4, 6, Color.GREEN));
        puntos.add(new Punto(5, 4, Color.GREEN));
        puntos.add(new Punto(1, 3, Color.PURPLE));
        puntos.add(new Punto(5, 6, Color.PURPLE));
        puntos.add(new Punto(7, 7, Color.CYAN));
        puntos.add(new Punto(3, 5, Color.CYAN));
        puntos.add(new Punto(1,2,Color.YELLOW));
        puntos.add(new Punto(6,2,Color.YELLOW));
        puntos.add(new Punto(6,0,Color.RED));
        puntos.add(new Punto(2,5,Color.RED));
        
        
        
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
        musicNivel.setLooping(true);
        musicNivel.setVolume(0.5f);
        musicNivel.play();
    
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
        texturaYellow.dispose();
        texturaRed.dispose();
        detenerHiloTiempo();
        detenerHiloColisiones();
        musicNivel.dispose();
        pathActual=null;
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
