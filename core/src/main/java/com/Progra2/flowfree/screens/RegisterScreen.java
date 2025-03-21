/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.flowfree.screens;

import java.io.File;

import com.Progra2.flowfree.flowfreegame.FlowFreeGame;
import com.Progra2.flowfree.model.Usuario;
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

/**
 *
 * @author Nadiesda Fuentes
 */
public class RegisterScreen implements Screen {
    private FlowFreeGame game;
    private Stage stage;
    private Skin skin;
    private TextField userField, passField, nameField;
    private Label messageLabel;

    public RegisterScreen(FlowFreeGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Crear elementos UI
        Label titleLabel = new Label("Registro de Usuario", skin);
        titleLabel.setFontScale(2);

        nameField = new TextField("", skin);
        userField = new TextField("", skin);
        passField = new TextField("", skin);
        passField.setPasswordMode(true);
        passField.setPasswordCharacter('*');

        Button registerButton = new TextButton("Registrarse", skin);
        Button backButton = new TextButton("Volver", skin);

        messageLabel = new Label("", skin);

        // Tabla para organizar los elementos
        Table table = new Table();
        table.setFillParent(true);
        table.add(titleLabel).padBottom(20).row();
        table.add(new Label("Nombre Completo:", skin)).left();
        table.add(nameField).width(200).padBottom(10).row();
        table.add(new Label("Usuario:", skin)).left();
        table.add(userField).width(200).padBottom(10).row();
        table.add(new Label("Contrasena:", skin)).left();
        table.add(passField).width(200).padBottom(20).row();
        table.add(registerButton).colspan(2).padBottom(10).row();
        table.add(backButton).colspan(2).row();
        table.add(messageLabel).colspan(2).padTop(10).row();

        stage.addActor(table);

        // Acción del botón "Registrarse"
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                registrarUsuario();
            }
        });

        // Acción del botón "Volver"
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
    }

    // Método para registrar un usuario
    private void registrarUsuario() {
        String nombreCompleto = nameField.getText();
        String nombreUsuario = userField.getText();
        String password = passField.getText();

        // Validar que los campos no estén vacíos
        if (nombreCompleto.isEmpty() || nombreUsuario.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Todos los campos son obligatorios.");
            return;
        }

        // Verificar si el usuario ya existe
        File userFile = new File("usuarios/" + nombreUsuario + ".dat");
        if (userFile.exists()) {
            messageLabel.setText("El usuario ya existe. Elige otro nombre.");
            return;
        }
       
        //Avatar
        File avatarsFolder = new File("avatars");
        if (!avatarsFolder.exists()) {
            avatarsFolder.mkdirs();
        }
    
        // Crear y guardar el nuevo usuario
        Usuario nuevoUsuario = new Usuario(nombreUsuario, password, nombreCompleto);
        nuevoUsuario.guardarDatos();
        messageLabel.setText("Registro exitoso. Volviendo al menú...");
        
        // Esperar un momento antes de volver al menú
        Gdx.app.postRunnable(() -> game.setScreen(new MenuScreen(game)));
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
