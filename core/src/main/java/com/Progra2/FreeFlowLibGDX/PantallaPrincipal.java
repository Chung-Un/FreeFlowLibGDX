/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import static java.lang.System.exit;

/**
 *
 * @author chung
 */
public class PantallaPrincipal implements Screen{
    Music music;
    FreeFlow FreeFlow;
    Texture FotoFondo;
    Texture botonSalir;
    Texture botonLogin;
    Texture botonCrearPlayer;
    Texture TituloFreeFlow;
    static float y, grosorPantalla, alturaPantalla, botonGrosor, botonAltura;
    
    public PantallaPrincipal(FreeFlow FreeFlow){
        this.FreeFlow = FreeFlow;
        FotoFondo = new Texture("FotoFondo.png");
        TituloFreeFlow = new Texture("FreeFlowTitulo.png");
        botonSalir = new Texture("botonSalir.png"); 
        botonLogin = new Texture("botonLogin.png");
        botonCrearPlayer = new Texture("botoncrear.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("MainMusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }
    
    @Override
    public void show() {
        
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        dibujar();
        logica();
        
    }
    
    public void dibujar(){
        grosorPantalla = Gdx.graphics.getWidth();
        alturaPantalla = Gdx.graphics.getHeight();
        float grosorTextura =301 ;
        float alturaTextura=40;
        float x = (grosorPantalla - grosorTextura)/2;
        y = alturaPantalla - alturaTextura-40; //crear un margen arriba
        FreeFlow.batch.begin();
        
        FreeFlow.batch.draw(FotoFondo, 0, 0, grosorPantalla, alturaPantalla); 
        FreeFlow.batch.draw(TituloFreeFlow,x,y, grosorTextura,alturaTextura);
        iniciarBoton(botonLogin);
        iniciarBoton(botonCrearPlayer);
        iniciarBoton(botonSalir);
        
        FreeFlow.batch.end();
    }
    public void iniciarBoton(Texture texture){
        botonGrosor = texture.getWidth()/2;
        botonAltura = texture.getHeight()/2;
        float botonX = (grosorPantalla - botonGrosor)/2;
        float botonY = y-botonAltura - 40;
        y = botonY;
        
        FreeFlow.batch.draw(texture, botonX, botonY, botonGrosor,botonAltura);
       
    }
    
    public void logica(){
     if(Gdx.input.isTouched()){
         float tocadoX = Gdx.input.getX();
         float tocadoY = Gdx.graphics.getHeight() -Gdx.input.getY();
         
         float tempY = alturaPantalla - 40; 
         float loginY = tempY - botonLogin.getHeight() / 2 - 40;
         float crearY = loginY - botonCrearPlayer.getHeight() / 2 - 40;
         float salirY = crearY - botonSalir.getHeight() / 2 - 40; 
                 
         if (botonTocado(botonLogin, tocadoX, tocadoY, loginY)) {
        } else if (botonTocado(botonCrearPlayer, tocadoX, tocadoY, crearY)) {
        } else if (botonTocado(botonSalir, tocadoX, tocadoY, salirY)) {
            System.out.println("Boton salir tocado");
            Gdx.app.exit();
        }
    }
}
    
    public boolean botonTocado(Texture texture, float tocadoX, float tocadoY, float botonY){
        botonGrosor = texture.getWidth()/2;
        botonAltura = texture.getHeight()/2;
        float botonX = (grosorPantalla - botonGrosor)/2;
        
        return tocadoX >= botonX && tocadoX <= botonX + botonGrosor &&
           tocadoY >= botonY && tocadoY <= botonY + botonAltura;
    }
    
    @Override
    public void resize(int grosor, int altura) {
        grosorPantalla = grosor;
        alturaPantalla = altura;
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
