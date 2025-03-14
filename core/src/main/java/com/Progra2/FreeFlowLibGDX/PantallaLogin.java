/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 * @author chung
 */
public class PantallaLogin implements Screen{
    private Stage stage;
    private Skin skin;
    private TextField fieldUser, fieldPassword;
    private ImageButton btnLogin;
    private ImageButton btnRegresar;
    private FreeFlow FreeFlow;
    private Texture fondo;
    private Boton botonRegresar;
    private Texture tituloLogin;
    static float grosorPantalla,alturaPantalla,y;
    protected String user,password;
    
    public PantallaLogin(FreeFlow FreeFlow) {
        this.FreeFlow = FreeFlow;
        stage = new Stage(new ScreenViewport());
        skin = new Skin (Gdx.files.internal("uiskin.json"));
        
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        fondo = new Texture("FotoFondo.png");
        Texture texturaLogin = new Texture("login.png");
        Texture texturaBtnLogin = new Texture("botonlogin.png");
        Texture texturaRegresar = new Texture("botonBack.png");
        
        ImageButton.ImageButtonStyle botonLoginStyle = new ImageButton.ImageButtonStyle();
        botonLoginStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturaBtnLogin));
        
        ImageButton.ImageButtonStyle botonRegresarStyle = new ImageButton.ImageButtonStyle();
        botonRegresarStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturaRegresar));
        
        btnLogin = new ImageButton(botonLoginStyle);
        btnRegresar = new ImageButton(botonRegresarStyle);
        
        fieldUser = new TextField("" ,skin);
        fieldPassword = new TextField("", skin);
        fieldPassword.setPasswordMode(true);
        fieldPassword.setPasswordCharacter('*');
        
        table.top().center();
        table.add(new Label("Username: ", skin)).padTop(180).padRight(30);
        table.add(fieldUser).width(200).padTop(180).padBottom(30).row();
        table.add(new Label("Password: ",skin)).padRight(30);
        table.add(fieldPassword).width(200).row();
        table.add(btnLogin).colspan(2).width(150).row();
        table.add(btnRegresar).colspan(2).width(50);
        
              
        tituloLogin = new Texture("login.png");
        
        btnLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("login boton");
                user = fieldUser.getText();
                password = fieldPassword.getText();
                fieldUser.setText("");
                fieldPassword.setText("");
            }
        });

        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FreeFlow.setScreen(new PantallaPrincipal(FreeFlow));
            }
        });
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        dibujar();
        stage.act();
        stage.draw();
    }
    
    public void dibujar(){
        grosorPantalla = Gdx.graphics.getWidth();
        alturaPantalla = Gdx.graphics.getHeight();
        float grosorTextura = tituloLogin.getWidth()/2;
        float alturaTextura = tituloLogin.getHeight()/2;
        float x = (grosorPantalla -grosorTextura)/2;
        y = (alturaPantalla-alturaTextura)-40;
        
        FreeFlow.batch.begin();
        FreeFlow.batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        FreeFlow.batch.draw(tituloLogin, x, y, grosorTextura, alturaTextura);
        FreeFlow.batch.end();
    }
    
    
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    
}
