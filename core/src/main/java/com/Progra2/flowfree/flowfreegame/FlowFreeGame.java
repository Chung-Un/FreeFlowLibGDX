/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.flowfree.flowfreegame;

import com.badlogic.gdx.Game;
import com.mijuego.flowfree.screens.MenuScreen;
/**
 *
 * @author Nadiesda Fuentes
 */
public class FlowFreeGame extends Game {
    @Override
    public void create() {
        this.setScreen(new MenuScreen(this)); // Iniciar en el menú
    }
}
