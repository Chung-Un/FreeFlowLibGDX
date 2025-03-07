/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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
    private Punto puntoSeleccionado;
    private Table table;
    private float sizeCelda;
    private float offSetX, offSetY;
    
    public Nivel1(int sizeGrid, float tiempoLimite, Jugador jugador){
        super(sizeGrid,tiempoLimite, jugador);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        table = new Table();
        table.center();
        stage.addActor(table);
        texturaPink = new Texture("pinkdot.png");
        texturaCyan = new Texture("cyandot.png");
        texturaOrange = new Texture("orangedot.png");
        texturaYellow = new Texture("yellowdot.png");
        texturaGreen = new Texture("greendot.png");
        sizeCelda = 100;
        offSetX = (Gdx.graphics.getWidth() - sizeCelda * 5) / 2;
        offSetY = (Gdx.graphics.getHeight() - sizeCelda * 5) / 2;


    }

    @Override
    public void inicializar() {
        puntos.add(new Punto(0,0, Color.PINK)); 
        puntos.add(new Punto(1,4, Color.PINK));
        puntos.add(new Punto(1,3, Color.ORANGE));
        puntos.add(new Punto(2,0, Color.ORANGE));
        puntos.add(new Punto(2,1, Color.GREEN));
        puntos.add(new Punto(2,4, Color.GREEN));
        puntos.add(new Punto(4,0, Color.YELLOW));
        puntos.add(new Punto(3,3, Color.YELLOW));
        puntos.add(new Punto(4,1,Color.CYAN));
        puntos.add(new Punto(3,4,Color.CYAN));
        
//        iniciarHiloTiempo();
//        iniciarHiloVerificacionConexiones();
    }
    
    public void crearDotBotones(){
        
      
        for (Punto punto : puntos){
            float x = offSetX + punto.getX() * sizeCelda;
            float y = offSetY + (sizeCelda * (sizeGrid - 1 - punto.getY())); 


            
        ImageButton.ImageButtonStyle btnDotStyle = new ImageButton.ImageButtonStyle();
        
        if(punto.getColor().equals(Color.PINK)){
            btnDotStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturaPink));
        }
        else if(punto.getColor().equals(Color.ORANGE)){
            btnDotStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturaOrange));
        }
        else if(punto.getColor().equals(Color.CYAN)){
           btnDotStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturaCyan));
        }
        else if(punto.getColor().equals(Color.GREEN)){
           btnDotStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturaGreen));
        }
        else if(punto.getColor().equals(Color.YELLOW)){
           btnDotStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturaYellow));
        }
        
        ImageButton btnDot = new ImageButton(btnDotStyle);
        btnDot.setSize(sizeCelda, sizeCelda);
        btnDot.setPosition(x,y);
        
        btnDot.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        seleccionDots(punto.getX(), punto.getY(), punto.getColor());
                    }
                });
        
        stage.addActor(btnDot);
        }
        
    }
    
    public void seleccionDots(int fila, int col, Color color){
        
        if(puntoSeleccionado == null){
            puntoSeleccionado = new Punto(fila, col, color);
            System.out.println("Seleccionado dot en row(" + fila + ") y col{" + col + ")");
        } else{
            System.out.println("Intenta conectar");
            //logica de conxeion
            puntoSeleccionado = null;
        }
    
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
        
        batch.begin();
        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        
        for( int i =0 ; i<=5 ; i++){
            renderer.line( offSetX + i * sizeCelda, offSetY, offSetX + i * sizeCelda, offSetY + sizeCelda * 5);
            renderer.line(offSetX, offSetY + i * sizeCelda,  offSetX + sizeCelda * 5, offSetY + i * sizeCelda);
        }
        
        renderer.end();
        
        renderer.begin();
        crearDotBotones();
        renderer.end();
        batch.end();

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
