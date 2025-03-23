/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.flowfree.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.Progra2.flowfree.flowfreegame.FlowFreeGame;
import com.Progra2.flowfree.flowfreegame.LanguageManager;
import static com.Progra2.flowfree.flowfreegame.LanguageManager.languageManager;
import com.Progra2.flowfree.model.Usuario;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import javax.swing.JOptionPane;

/**
 *
 * @author Nadiesda Fuentes
 */
public class MenuScreen implements Screen {
    private FlowFreeGame game;
    private Stage stage;
    private Skin skin;
    private TextField userField, passField;
    private Label titleLabel,labelUsuario,labelPassword;
    private TextButton loginButton, registerButton, btnIdiomas,btnSalir;
    private Texture texturaFondo;
    private Image imgFondo;

    public MenuScreen(FlowFreeGame game) {
        languageManager= new LanguageManager();
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Cargar Skin (puedes usar "uiskin.json" de libGDX o crear uno personalizado)
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Texture texturaFondo = new Texture(Gdx.files.internal("FotoFondo.png")); 
        Image imgFondo = new Image(texturaFondo);
        imgFondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        Texture texturaTitulo = new Texture(Gdx.files.internal("flowfree.png"));
        Image imgTitulo = new Image(texturaTitulo);
        imgTitulo.setSize(250,40);
        imgTitulo.setPosition(((Gdx.graphics.getWidth())-imgTitulo.getWidth())/2, 
                Gdx.graphics.getHeight()- imgTitulo.getHeight()-50);
        
        stage.addActor(imgFondo);
        stage.addActor(imgTitulo);
        
        
        // Crear elementos UI

        userField = new TextField("", skin);
        passField = new TextField("", skin);
        passField.setPasswordMode(true);
        passField.setPasswordCharacter('*');
        labelUsuario = new Label(languageManager.getText("usuario")+":",skin);
        labelPassword = new Label(languageManager.getText("password") + ":",skin);

        loginButton = new TextButton(languageManager.getText("iniciar_sesion"), skin);
        registerButton = new TextButton(languageManager.getText("registrar"), skin);
        btnSalir = new TextButton(languageManager.getText("salir"),skin);
        btnIdiomas = new TextButton(languageManager.getText("settings_idioma"),skin);
        
        texturaFondo = new Texture(Gdx.files.internal("FotoFondo.png")); 
        imgFondo = new Image(texturaFondo);
        imgFondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        
        // Layout con Table
        Table table = new Table();
        table.setFillParent(true);
        table.add(labelUsuario).padTop(80).left();
        table.add(userField).padTop(80).width(200).padBottom(10).row();
        table.add(labelPassword).left().padBottom(50);
        table.add(passField).width(200).padBottom(50).row();
        table.add(loginButton).colspan(2).padBottom(10).row();
        table.add(registerButton).colspan(2).padBottom(10).row();
        table.add(btnIdiomas).colspan(2).padBottom(10).row();
        table.add(btnSalir).colspan(2).padBottom(10).row();
       
        
        
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
                    JOptionPane.showMessageDialog(null, languageManager.getText("datos_incorrectos"),"Fail",JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new RegisterScreen(game));
            }
        });
        
        btnIdiomas.clearListeners();
         btnIdiomas.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked!"); // Debug statement
                String[] opciones  = new String[]{"Spanish","English", "French"};
                int opcion = JOptionPane.showOptionDialog(null, languageManager.getText("settings_idioma"), " ", JOptionPane.DEFAULT_OPTION
                , JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                
                switch (opcion) {
                    case 0:
                        languageManager.setLanguage("es");
                        refreshUI();
                        break;
                    case 1:
                        languageManager.setLanguage("en");
                        refreshUI();
                        break;
                    case 2:
                        languageManager.setLanguage("fr");
                        refreshUI();
                        break;
                    default:
                        break;
                }
            }
        });
         
        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.exit(0);
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
    
     private void refreshUI() {
        btnSalir.setText(languageManager.getText("salir"));
        labelPassword.setText(languageManager.getText("password"));
        labelUsuario.setText(languageManager.getText("usuario"));
        loginButton.setText(languageManager.getText("iniciar_sesion"));
        registerButton.setText(languageManager.getText("registrar"));
        btnIdiomas.setText(languageManager.getText("settings_idioma"));
    }
}

