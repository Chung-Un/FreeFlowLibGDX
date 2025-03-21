/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.Progra2.flowfree.flowfreegame.FlowFreeGame;
import com.Progra2.flowfree.model.Usuario;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author chung
 */
public class PantallaJuego implements Screen{
    private Nivel nivelActual;
    public static  ManejoNivel manejoNivel;
    private FlowFreeGame FlowFree;
    private SpriteBatch batch;
    protected static Conexion conexionHovered = null;
    
    public PantallaJuego(FlowFreeGame FlowFree, Usuario jugador, int nivel){
        this.FlowFree = FlowFree;
        manejoNivel = new ManejoNivel(jugador, FlowFree,nivel);
        nivelActual = manejoNivel.getNivelActual();
        nivelActual.inicializar();
        batch = new SpriteBatch();
    }

   
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        conexionHovered = Nivel.conexionHovered(mouseX, mouseY);
        nivelActual.actualizar(delta);
        nivelActual.dibujar();
        
        if(nivelActual.verificarCompletacion()){
            manejoNivel.avanzarNivel();
            nivelActual = manejoNivel.getNivelActual();
        }
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void show() {
    }
    
}
