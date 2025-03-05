/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

/**
 *
 * @author chung
 */
public class PantallaPrincipal implements Screen{
    Music music;
    FreeFlow FreeFlow;
    Texture FotoFondo;
    Texture texturaSalir;
    Texture texturaLogin;
    Texture texturaCrear;
    Texture TituloFreeFlow;
    Boton botonCrear; 
    Boton botonLogin;
    Boton botonSalir;
    ArrayList<Boton> botones;
    static float y, grosorPantalla, alturaPantalla, botonGrosor, botonAltura;
    
    public PantallaPrincipal(FreeFlow FreeFlow){
        this.FreeFlow = FreeFlow;
        
        grosorPantalla = Gdx.graphics.getWidth();
        alturaPantalla = Gdx.graphics.getHeight();
        texturaSalir = new Texture("botonsalir.png");
        float botonGrosor = texturaSalir.getWidth()/2;
        float botonAltura = texturaSalir.getHeight()/2;
        float botonY = alturaPantalla-150;
        float margen=20;
        
        FotoFondo = new Texture("FotoFondo.png");
        TituloFreeFlow = new Texture("FreeFlowTitulo.png");
        botonCrear = new Boton(new Texture("botoncrear.png"), 0,0,botonGrosor, botonAltura,true,botonY);
        botonLogin = new Boton(new Texture("botonlogin.png"),0,0,botonGrosor,botonAltura,true,botonY - (botonAltura + margen));
        botonSalir = new Boton(new Texture("botonsalir.png"),0,0,botonGrosor,botonAltura,true,botonY - 2 * (botonAltura + margen));
        botones = new ArrayList();
        botones.add(botonCrear);
        botones.add(botonLogin);
        botones.add(botonSalir);
        
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
        float grosorTextura =301 ;
        float alturaTextura=40;
        float x = (grosorPantalla - grosorTextura)/2;
        y = alturaPantalla - alturaTextura-40; 
        FreeFlow.batch.begin();
        
        FreeFlow.batch.draw(FotoFondo, 0, 0, grosorPantalla, alturaPantalla); 
        FreeFlow.batch.draw(TituloFreeFlow,x,y, grosorTextura,alturaTextura);
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
                 if(boton == botonSalir){
                     System.out.println("Boton salir");
                     Gdx.app.exit();
                 }
                 else if(boton == botonLogin){
                     System.out.println("Boton login");
                     FreeFlow.setScreen(new PantallaLogin(FreeFlow));
                 }
                 else if(boton == botonCrear){
                     System.out.println("Boton crear");
                 }
             }
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
        TituloFreeFlow.dispose();
        for (Boton boton : botones) {
            boton.dispose();
        }
        music.dispose();
    }
    
    
    
}
