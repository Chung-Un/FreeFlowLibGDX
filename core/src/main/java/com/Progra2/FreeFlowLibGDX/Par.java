/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import java.util.Objects;

/**
 *
 * @author chung
 */
public class Par {
    public final int col;
    public final int fila;
    
    public Par(int col, int fila){
        this.col = col;
        this.fila = fila;
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(o==null || getClass() !=  o .getClass()) return false;
        Par par = (Par) o;
        return col == par.col && fila == par.fila;
    }
    
    @Override
    public int hashCode(){
        return 31*fila*col; //31, numero impar primo para evitar valores hash repetidos
    }
}
