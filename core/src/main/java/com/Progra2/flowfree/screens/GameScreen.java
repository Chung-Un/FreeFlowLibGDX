/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.flowfree.screens;

import com.Progra2.FreeFlowLibGDX.FreeFlow;
import com.Progra2.FreeFlowLibGDX.PantallaMapa;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.Progra2.flowfree.flowfreegame.FlowFreeGame;
import com.Progra2.flowfree.model.Usuario;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * @author Nadiesda Fuentes
 */
public class GameScreen implements Screen {
    private FlowFreeGame game;
    private FlowFreeGame fw;
    private Stage stage;
    private Skin skin;
    private Usuario usuario;
    private Label statsLabel;

    public GameScreen(FlowFreeGame game, Usuario usuario) {
        this.game = game;
        this.usuario = usuario;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Título
        Label titleLabel = new Label("Juego - Flow Free", skin);
        titleLabel.setFontScale(2);

        // Mostrar estadísticas del usuario
        statsLabel = new Label(
            "Niveles completados: " + usuario.getNivelesCompletados() + "\n" +
            "Tiempo jugado: " + usuario.getTiempoJugado() + "s\n" +
            "Tiempo promedio por nivel: " + usuario.getTiempoPromedioPorNivel() + "s\n" +
            "Puntuación General: " + usuario.getPuntuacionGeneral(), 
            skin
        );

        Button backButton = new TextButton("Volver al Menú", skin);
        Button playButton = new TextButton("Jugar", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PantallaMapa(fw,usuario));
            }
        });
        
        Table table = new Table();
        table.setFillParent(true);
        table.add(titleLabel).padBottom(20).row();
        table.add(statsLabel).padBottom(20).row();
        table.add(playButton).row();
        table.add(backButton).row();

        stage.addActor(table);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}


