/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

/**
 *
 * @author chung
 */
public class PantallaLogin implements Screen{
    private FreeFlow FreeFlow;
    private Texture fondo;
    private Boton botonRegresar;
    private Texture tituloLogin;
    static float grosorPantalla,alturaPantalla,y;
    private ArrayList<Boton> botones;
    
    public PantallaLogin(FreeFlow FreeFlow) {
        this.FreeFlow = FreeFlow;
        fondo = new Texture("FotoFondo.png");
        botonRegresar = new Boton(new Texture("botonBack.png"), 10, Gdx.graphics.getHeight() - 60, 50, 50,false , 0);
        tituloLogin = new Texture("login.png");
        
        botones = new ArrayList<>();
        botones.add(botonRegresar);
    }
    
    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        dibujar();
        logica();
    }
    
    public void dibujar(){
        grosorPantalla = Gdx.graphics.getWidth();
        alturaPantalla = Gdx.graphics.getHeight();
        float grosorTextura = tituloLogin.getWidth();
        float alturaTextura = tituloLogin.getHeight();
        float x = (grosorPantalla -grosorTextura)/2;
        y = (alturaPantalla-alturaTextura)-40;
        
        FreeFlow.batch.begin();
        FreeFlow.batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        FreeFlow.batch.draw(tituloLogin, x, y, grosorTextura, alturaTextura);
        for(Boton boton : botones){
            boton.dibujar(FreeFlow.batch);
        }
        
        FreeFlow.batch.end();
    }
    
    public void logica(){
        
        if(Gdx.input.isTouched()){
         float tocadoX = Gdx.input.getX();
         float tocadoY = Gdx.graphics.getHeight() -Gdx.input.getY();
         
         for(Boton boton : botones){
             if(boton.esTocado(tocadoX, tocadoY)){
                 if(boton == botonRegresar){
                     System.out.println("Boton tocado");
                     FreeFlow.setScreen(new PantallaPrincipal(FreeFlow));
                 }
             }
         }
        
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
    
}
