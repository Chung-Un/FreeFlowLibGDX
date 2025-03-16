/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.graphics.Color;

/**
 *
 * @author chung
 */
public class Punto {
    private int col,fila;
    private Color color;
    protected boolean conectado;

    public Punto(int col, int fila, Color color){
        this.col = col;
        this.fila =fila;
        this.color = color;
        this.conectado = false;
    }
    
    
    public int getCol() {
        return col;
    }

    public int getFila() {
        return fila;
    }
    
    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }
   
    public boolean estaConectado(){
        return conectado;
    }
    
    public Color getColor(){
        return color;
    }
   
}
