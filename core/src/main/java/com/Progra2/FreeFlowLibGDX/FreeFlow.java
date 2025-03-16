package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FreeFlow extends Game {
    protected SpriteBatch batch;
    private Jugador jugador = new Jugador("juan","12345");//TESTTT BORRAR LUEGO

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new PantallaMapa(this,jugador));

    }

    @Override
    public void render() {
        super.render();
    }
    

    
}
