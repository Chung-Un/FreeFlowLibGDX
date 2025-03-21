/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.flowfree.flowfreegame;

import com.badlogic.gdx.Game;
import com.Progra2.flowfree.screens.MenuScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 *
 * @author Nadiesda Fuentes
 */
public class FlowFreeGame extends Game {
    public SpriteBatch batch;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MenuScreen(this)); // Iniciar en el menú
    }
    
    @Override
    public void render(){
        super.render();
    }
}
