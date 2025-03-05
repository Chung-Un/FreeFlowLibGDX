/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author chung
 */
public class Boton {
    private Texture texture;
    private float x,y,grosor,altura;
    private boolean centrado;
    
    public Boton(Texture texture, float x, float y, float grosor, float altura, boolean centrado, float yCentrado) {
        this.texture = texture;
        this.x = x;
        this.y = yCentrado; 
        this.grosor = grosor;
        this.altura = altura;
        this.centrado = centrado;
    }
    
    public void dibujar(SpriteBatch batch) {
        if (centrado) {
            float botonGrosor = texture.getWidth() / 2;
            float botonAltura = texture.getHeight() / 2;
            float botonX = (Gdx.graphics.getWidth() - botonGrosor) / 2; 
            float botonY = y - botonAltura; 

            batch.draw(texture, botonX, botonY, botonGrosor, botonAltura);
        } else {
            batch.draw(texture, x, y, grosor, altura);
        }
    }
    
     public boolean esTocado(float tocadoX, float tocadoY) {
        if (centrado) {
            float botonGrosor = texture.getWidth() / 2;
            float botonAltura = texture.getHeight() / 2;
            float botonX = (Gdx.graphics.getWidth() - botonGrosor) / 2;
            float botonY = y - botonAltura;

            return tocadoX >= botonX && tocadoX <= botonX + botonGrosor &&
                   tocadoY >= botonY && tocadoY <= botonY + botonAltura;
        } else {
            return tocadoX >= x && tocadoX <= x + grosor &&
                   tocadoY >= y && tocadoY <= y + altura;
        }
    }
    
    public void dispose(){
        texture.dispose();
    }
    
}
