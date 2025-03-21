/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.flowfree.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.Progra2.flowfree.flowfreegame.FlowFreeGame;
import com.Progra2.flowfree.model.Usuario;

/**
 *
 * @author Nadiesda Fuentes
 */
public class MenuScreen implements Screen {
    private FlowFreeGame game;
    private Stage stage;
    private Skin skin;
    private TextField userField, passField;

    public MenuScreen(FlowFreeGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Cargar Skin (puedes usar "uiskin.json" de libGDX o crear uno personalizado)
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Crear elementos UI
        Label titleLabel = new Label("Flow Free", skin);
        titleLabel.setFontScale(2);
        userField = new TextField("", skin);
        passField = new TextField("", skin);
        passField.setPasswordMode(true);
        passField.setPasswordCharacter('*');

        Button loginButton = new TextButton("Iniciar Sesion", skin);
        Button registerButton = new TextButton("Registrarse", skin);
        
        
        // Layout con Table
        Table table = new Table();
        table.setFillParent(true);
        table.add(titleLabel).padBottom(20).row();
        table.add(new Label("Usuario:", skin)).left();
        table.add(userField).width(200).padBottom(10).row();
        table.add(new Label("Contrasena", skin)).left();
        table.add(passField).width(200).padBottom(20).row();
        table.add(loginButton).colspan(2).padBottom(10).row();
        table.add(registerButton).colspan(2).row();
        
        
        stage.addActor(table);

        // Eventos de los botones
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                String usuario = userField.getText();
                String password = passField.getText();
                Usuario user = Usuario.cargarDatos(usuario);
                if (user != null && user.verificarPassword(password)) {
                    game.setScreen(new GameScreen(game, user)); 
                } else {
                    System.out.println("Usuario o contraseña incorrectos");
                }
                
            }
        });

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new RegisterScreen(game));
            }
        });
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

