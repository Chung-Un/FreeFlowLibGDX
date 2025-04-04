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
public class Nivel5 extends Nivel {
    public Nivel5(int sizeGrid, double tiempoLimite, Usuario jugador, FlowFreeGame FlowFree){
        super(sizeGrid, tiempoLimite, jugador, FlowFree,45);
        this.grid= new int[sizeGrid][sizeGrid];
        this.numeroNivel=5;
        
    }

    @Override
    public void inicializar() {
        this.calcularOffsets();
        for (int i = 0; i < sizeGrid; i++) {
            for (int j = 0; j < sizeGrid; j++) {
                grid[i][j] = 0; 
            }
        }
        
        puntos.add(new Punto(0, 0 , Color.PINK));
        puntos.add(new Punto(1, 1, Color.PINK));
        puntos.add(new Punto(9, 8, Color.ORANGE));
        puntos.add(new Punto(8, 2, Color.ORANGE));
        puntos.add(new Punto(2, 0, Color.GREEN));
        puntos.add(new Punto(1, 8, Color.GREEN));
        puntos.add(new Punto(0, 1, Color.PURPLE));
        puntos.add(new Punto(6, 9, Color.PURPLE));
        puntos.add(new Punto(3, 0, Color.CYAN));
        puntos.add(new Punto(8, 1, Color.CYAN));
        puntos.add(new Punto(9,5,Color.YELLOW));
        puntos.add(new Punto(8,8,Color.YELLOW));
        puntos.add(new Punto(4,0,Color.RED));
        puntos.add(new Punto(8,6,Color.RED));
        puntos.add(new Punto(4,6,Color.BLUE));
        puntos.add(new Punto(8,5,Color.BLUE));
        puntos.add(new Punto(9,4,Color.SALMON));
        puntos.add(new Punto(4,5,Color.SALMON));
        
        
        
        for(Punto punto : puntos){
            grid[punto.getFila()][punto.getCol()] = 1; //significa que contiene un punto
            gridPuntos[punto.getFila()][punto.getCol()] = punto; //colocar el punto donde debe estar
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
        texturaSalmon.dispose();
        texturaBlue.dispose();
        texturaRed.dispose();
        detenerHiloTiempo();
        detenerHiloColisiones();
        musicNivel.stop();
        musicNivel.dispose();
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