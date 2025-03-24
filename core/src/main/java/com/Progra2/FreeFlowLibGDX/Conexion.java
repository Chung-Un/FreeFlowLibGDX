/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author chung
 */
public class Conexion {
    private Punto punto1, punto2;
    private Color color;
    private List <int[]> path;
 
    public Conexion(Punto punto1, Punto punto2, List<int[]> path) {
    this.punto1 = punto1;
    this.punto2 = punto2;
    this.color = punto1.getColor();
    this.path = new ArrayList<>(path);
}

    public List<int[]> getPath() {
        return path;
    }
    
    public Color getColor() {
        return color;
    }

    public Punto getPunto1() {
        return punto1;
    }

    public Punto getPunto2() {
        return punto2;
    }
}