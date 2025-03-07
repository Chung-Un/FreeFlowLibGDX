/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

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
    private ManejoNivel manejoNivel;
    private FreeFlow FlowFree;
    private SpriteBatch batch;
    
    public PantallaJuego(FreeFlow FlowFree, Usuario jugador){
        manejoNivel = new ManejoNivel(jugador);
        nivelActual = manejoNivel.getNivelActual();
        nivelActual.inicializar();
        batch = new SpriteBatch();
    }

   
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        nivelActual.actualizar(delta);
        nivelActual.dibujar();
        batch.end();
        
        if(nivelActual.verificarCompletacion()){
            manejoNivel.avanzarNivel();
            nivelActual = manejoNivel.getNivelActual();
            nivelActual.inicializar();
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
