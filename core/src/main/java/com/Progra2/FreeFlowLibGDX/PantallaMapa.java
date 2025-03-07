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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;

/**
 *
 * @author chung
 */
public class PantallaMapa implements Screen{
    private Texture texturaFondo;
    private Texture texturaTitulo;
    private FreeFlow FlowFree;
    private Skin skin;
    private Stage stage;
    private static float y;
    private ImageButton btnNivel1;
    private ImageButton btnNivel2;
    private ImageButton btnNivel3;
    private ImageButton btnNivel4;
    private ImageButton btnNivel5;
    private ImageButton btnRegresar;
    private Texture texturanivelCerrado;
    private Texture texturanivelAbierto;
    private Texture texturaRegresar;
    private Jugador usuario = new Jugador("juan","12345");//TESTTT BORRAR LUEGO
    private ArrayList<ImageButton> botonesNiveles;
    private Music music;
    
    public PantallaMapa(FreeFlow FlowFree){
        this.FlowFree = FlowFree;
        stage = new Stage(new ScreenViewport());
        
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        texturanivelCerrado = new Texture("candadoCerrado.png");
        texturanivelAbierto = new Texture("candadoAbierto.png");
        texturaRegresar = new Texture("botonBack.png");
        
        ImageButton.ImageButtonStyle nivelCerradoBtnStyle = new ImageButton.ImageButtonStyle();
        nivelCerradoBtnStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturanivelCerrado));
        
        ImageButton.ImageButtonStyle nivelAbiertoBtnStyle = new ImageButton.ImageButtonStyle();
        nivelAbiertoBtnStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturanivelAbierto));
        
        ImageButton.ImageButtonStyle btnRegresarStyle= new ImageButton.ImageButtonStyle();
        btnRegresarStyle.imageUp = new TextureRegionDrawable(new TextureRegion(texturaRegresar));
        
        btnNivel1 = new ImageButton(nivelAbiertoBtnStyle);
        btnNivel2 = new ImageButton(nivelCerradoBtnStyle);
        btnNivel3 = new ImageButton(nivelCerradoBtnStyle);
        btnNivel4 = new ImageButton(nivelCerradoBtnStyle);
        btnNivel5 = new ImageButton(nivelCerradoBtnStyle);
        btnRegresar = new ImageButton(btnRegresarStyle);
        botonesNiveles = new ArrayList();
        btnNivel2.setDisabled(true);
        btnNivel3.setDisabled(true);
        btnNivel4.setDisabled(true);
        btnNivel5.setDisabled(true);
        botonesNiveles.add(btnNivel1);
        botonesNiveles.add(btnNivel2);
        botonesNiveles.add(btnNivel3);
        botonesNiveles.add(btnNivel4);
        botonesNiveles.add(btnNivel5);
        
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        
        if(usuario.getNivelesCompletados()>0){
            for(int i =0 ; i< usuario.getNivelesCompletados() && i < botonesNiveles.size(); i++){
                botonesNiveles.get(i).setStyle(nivelAbiertoBtnStyle);
                botonesNiveles.get(i).setDisabled(false);
            }
        }
        
        float sizeBotones=150;
        table.left().top();
        table.add(btnNivel1).size(sizeBotones).padTop(150).padRight(10);
        table.add(btnNivel2).size(sizeBotones).padTop(150).padRight(10);
        table.add(btnNivel3).size(sizeBotones).padTop(150).padRight(10);
        table.add(btnNivel4).size(sizeBotones).padTop(150).padRight(10);
        table.add(btnNivel5).size(sizeBotones).padTop(150).padRight(10);
        table.row();
        table.add(new Label("Nivel 1",skin)).padRight(10);
        table.add(new Label("Nivel 2",skin)).padRight(10);
        table.add(new Label("Nivel 3",skin)).padRight(10);
        table.add(new Label("Nivel 4",skin)).padRight(10);
        table.add(new Label("Nivel 5",skin)).padRight(10);
        table.row().center();
        table.add(btnRegresar).size(70).padTop(80).colspan(5).align(Align.center);
        
        texturaFondo = new Texture("FotoFondo.png");
        texturaTitulo = new Texture("flowfree.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("MainMusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
        
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FlowFree.setScreen(new PantallaPrincipal(FlowFree));
            }
        });
        
        btnNivel1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FlowFree.setScreen(new PantallaJuego(FlowFree,usuario));
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
        float grosorPantalla = Gdx.graphics.getWidth();
        float alturaPantalla = Gdx.graphics.getHeight();
        float grosorTextura = texturaTitulo.getWidth()/2;
        float alturaTextura = texturaTitulo.getHeight()/2;
        float x = (grosorPantalla -grosorTextura)/2;
        y = (alturaPantalla-alturaTextura)-40;
        
        FlowFree.batch.begin();
        FlowFree.batch.draw(texturaFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        FlowFree.batch.draw(texturaTitulo, x, y, grosorTextura, alturaTextura);
        FlowFree.batch.end();
    }
    
    @Override
    public void resize(int grosor, int altura) {
        stage.getViewport().update(grosor, altura, true);
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
        texturaTitulo.dispose();
        texturaFondo.dispose();
        for (ImageButton boton : botonesNiveles) {
            boton.remove();
        }
        music.dispose();
    }
    }
    

