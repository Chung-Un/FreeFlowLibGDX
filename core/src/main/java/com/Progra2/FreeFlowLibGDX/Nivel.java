/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;

/**
 *
 * @author Nadiesda Fuentes
 */
public abstract class Nivel{
    protected Jugador jugador;
    protected int sizeGrid;
    protected float tiempoRestante;
    protected boolean nivelCompletado;
    protected ArrayList<Punto> puntos;
    protected ArrayList<Conexion> conexiones;
    private Thread hiloTiempo;
    private Thread hiloConexiones;
    private boolean verificandoConexiones;
    private float y;
    private FreeFlow FlowFree;
    protected Stage stage;
    
    
    public Nivel(int sizeGrid, float tiempoLimite, Jugador jugador){
        this.sizeGrid = sizeGrid;  
        this.tiempoRestante = tiempoLimite;
        this.nivelCompletado = false;
        this.puntos = new ArrayList();
        this.conexiones = new ArrayList();
        this.verificandoConexiones = false;
        this.jugador = jugador;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); 

    }
    
    public abstract void inicializar();
    public abstract void actualizar(float delta);
    public abstract void dibujar();
    public abstract boolean verificarCompletacion();
    public abstract void reiniciarNivel();
    
    
    protected boolean todosPuntosConectados() {
        for (Punto punto : puntos) {
            if (!punto.estaConectado()) {
                return false;
            }
        }
        return true;
    }
    
    public void iniciarHiloTiempo(){
        hiloTiempo = new Thread(() ->{
            while(tiempoRestante > 0 && !nivelCompletado)
                try{
                    Thread.sleep(1000);
                    tiempoRestante--;
                } catch( InterruptedException e){
                    System.out.println("Hilo de tiempo interrumpido");
                    break;
                }
            
                    if(tiempoRestante<=0){
                        nivelCompletado = false;
                    }
        });
        hiloTiempo.start();
    }
    
    public void iniciarHiloVerificacionConexiones(){
        if(hiloConexiones==null || !hiloConexiones.isAlive()){
            verificandoConexiones = true;
            hiloConexiones =  new Thread(()->{
                while(verificandoConexiones){
                    if(verificarConexionesComplejas()){
                        nivelCompletado = true;
                        break;
                    }
                    try{
                        Thread.sleep(500);
                    } catch (InterruptedException e){
                        System.out.println("hilo conexiones interrumpido");
                        break;
                    }
                }
                
            });
            hiloConexiones.start();
        }
    }
    
    public void detenerHiloVerificacion() {
        verificandoConexiones = false;
        if (hiloConexiones != null && hiloConexiones.isAlive()) {
            hiloConexiones.interrupt(); 
        }
    }

    private boolean verificarConexionesComplejas() {
        return todosPuntosConectados();
    }
    public void detenerHiloTiempo(){
        if(hiloTiempo!=null && hiloTiempo.isAlive()){
            hiloTiempo.interrupt();
        }
    }
    
    public void registrarCompletado(int nivelJugado){
        if(nivelCompletado){
            int tiempoJugado = (int) tiempoRestante;
        }
    }
    
    public Stage getStage(){
        return stage;
    }
   
}
