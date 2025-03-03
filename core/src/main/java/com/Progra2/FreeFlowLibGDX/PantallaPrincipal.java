/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author chung
 */
public class PantallaPrincipal implements Screen{
    
    FreeFlow FreeFlow;
    Texture FotoFondo;
    
    public PantallaPrincipal(FreeFlow FreeFlow){
        this.FreeFlow = FreeFlow;
        FotoFondo = new Texture("FotoFondo.png");
        
    }
    
    @Override
    public void show() {
        
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        FreeFlow.batch.begin();
        
        FreeFlow.batch.draw(FotoFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());   
        
        FreeFlow.batch.end();
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
        FotoFondo.dispose();
    }
    
}
