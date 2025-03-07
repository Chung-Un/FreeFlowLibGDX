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
    private int x,y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    private Color color;
    private boolean conectado;

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }
    
    public Punto(int x, int y, Color color){
        this.x = x;
        this.y =y;
        this.color = color;
        this.conectado = false;
    }
    
    public boolean estaConectado(){
        return conectado;
    }
    
    public Color getColor(){
        return color;
    }
   
}
