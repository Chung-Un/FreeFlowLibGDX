/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author chung
 */
public class Nivel1 extends Nivel {
    private Texture texturaPink;
    private Texture texturaCyan;
    private Texture texturaOrange;
    private Texture texturaYellow;
    private Texture texturaGreen;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    
    public Nivel1(int sizeGrid, float tiempoLimite, Usuario jugador){
        super(sizeGrid,tiempoLimite, jugador);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        texturaPink = new Texture("pinkdot.png");
        texturaCyan = new Texture("cyandot.png");
        texturaOrange = new Texture("orangedot.png");
        texturaYellow = new Texture("yellowdot.png");
        texturaGreen = new Texture("greendot.png");
    }

    @Override
    public void inicializar() {
        puntos.add(new Punto(0,0, Color.PINK));
        puntos.add(new Punto(1,4, Color.PINK));
        puntos.add(new Punto(2,3, Color.ORANGE));
        puntos.add(new Punto(1,4, Color.ORANGE));
        puntos.add(new Punto(2,1, Color.GREEN));
        puntos.add(new Punto(2,4, Color.GREEN));
        puntos.add(new Punto(4,0, Color.YELLOW));
        puntos.add(new Punto(3,3, Color.YELLOW));
        puntos.add(new Punto(4,1,Color.CYAN));
        puntos.add(new Punto(3,4,Color.CYAN));
        
        iniciarHiloTiempo();
        iniciarHiloVerificacionConexiones();
    }

    @Override
    public void actualizar(float delta) {
        tiempoRestante-=delta;
        
        if(todosPuntosConectados()){
            nivelCompletado = true;
        }
        
        else if(tiempoRestante <= 0){
            nivelCompletado = false;
        }
        
        detenerHiloTiempo();
    }

    @Override
    public void dibujar() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        float sizeCelda = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())/sizeGrid;
        
        for(int i = 0; i< sizeGrid; i++){
            renderer.line(i*sizeCelda, 0, i * sizeCelda, Gdx.graphics.getHeight()); //lineas verticales
            renderer.line(0, i*sizeCelda,Gdx.graphics.getWidth(), i * sizeCelda); //lineas horizontales
        }
        
        renderer.end();
        
        batch.begin();
        
        float offsetX = (Gdx.graphics.getWidth() - sizeGrid * sizeCelda) / 2;
        float offsetY = (Gdx.graphics.getHeight() - sizeGrid * sizeCelda) / 2;

        for(Punto punto: puntos){
            if(punto.getColor().equals(Color.PINK)){
                batch.draw(texturaPink, offsetX + punto.getX() * sizeCelda, offsetY + punto.getY() * sizeCelda, sizeCelda * 0.8f, sizeCelda * 0.8f);
            }
            else if(punto.getColor().equals(Color.ORANGE)){
                batch.draw(texturaOrange, offsetX + punto.getX() * sizeCelda, offsetY + punto.getY() * sizeCelda, sizeCelda * 0.8f, sizeCelda * 0.8f);
            }
            else if(punto.getColor().equals(Color.CYAN)){
               batch.draw(texturaCyan, offsetX + punto.getX() * sizeCelda, offsetY + punto.getY() * sizeCelda, sizeCelda * 0.8f, sizeCelda * 0.8f); 
            }
            else if(punto.getColor().equals(Color.GREEN)){
                batch.draw(texturaGreen, offsetX + punto.getX() * sizeCelda, offsetY + punto.getY() * sizeCelda, sizeCelda * 0.8f, sizeCelda * 0.8f);
            }
            else if(punto.getColor().equals(Color.YELLOW)){
                batch.draw(texturaYellow, offsetX + punto.getX() * sizeCelda, offsetY + punto.getY() * sizeCelda, sizeCelda * 0.8f, sizeCelda * 0.8f);
            }
        }
        
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Conexion conexion : conexiones) {
            Punto inicio = conexion.getInicio();
            Punto fin = conexion.getFin();
            renderer.setColor(inicio.getColor());

            //calcular el centro de cada celda
            float x1 = inicio.getX() * sizeCelda + sizeCelda / 2;
            float y1 = inicio.getY() * sizeCelda + sizeCelda / 2;
            float x2 = fin.getX() * sizeCelda + sizeCelda / 2;
            float y2 = fin.getY() * sizeCelda + sizeCelda / 2;

            renderer.rectLine(x1, y1, x2, y2, 10); 
        }
        batch.end();
        renderer.end();
    }

    @Override
    public boolean verificarCompletacion() {
        return nivelCompletado;
    }

    @Override
    public void reiniciarNivel() {
        tiempoRestante = 45;
        nivelCompletado = false;
        puntos.clear();
        conexiones.clear();
        inicializar();
    }
    
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        texturaPink.dispose();
        texturaCyan.dispose();
        texturaYellow.dispose();
        texturaGreen.dispose();
        texturaOrange.dispose();
    }
    
    
}
