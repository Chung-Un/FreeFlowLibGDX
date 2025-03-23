/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.flowfree.screens;

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
import static com.Progra2.flowfree.flowfreegame.LanguageManager.languageManager;
import com.Progra2.flowfree.model.Ranking;
import com.Progra2.flowfree.model.Usuario;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Nadiesda Fuentes
 */
public class GameScreen implements Screen {
    private FlowFreeGame game;
    private Stage stage;
    private Skin skin;
    private Usuario usuario;
    private Label statsLabel;
    private Image imgAvatar;
    private Texture texturaAvatar;
    private Slider volumenSlider;
    
    public GameScreen(FlowFreeGame game, Usuario usuario) {
        this.game = game;
        this.usuario = usuario;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        MenuScreen.musicMain.setVolume(usuario.getVolumenMusica());
        MenuScreen.musicMain.play();
        
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Título
        Label titleLabel = new Label("Flow Free", skin);
        titleLabel.setFontScale(2);

        // Mostrar estadísticas del usuario
        statsLabel = new Label(
            languageManager.getText("niveles_completados") + ": " + usuario.getNivelesCompletados() + "\n" +
            languageManager.getText("tiempo_jugado")+": " + usuario.getTiempoJugado() + "s\n" +
            languageManager.getText("tiempo_promedio")+": " + String.format("%.2f", usuario.getTiempoPromedioPorNivel()) + "s\n", 
            skin
        );
        
        texturaAvatar = new Texture(Gdx.files.internal(usuario.getAvatarFileDireccion()));
        imgAvatar = new Image(texturaAvatar);
        imgAvatar.setSize(100,100);

        Texture texturaFondo = new Texture(Gdx.files.internal("FotoFondo.png")); 
        Image imgFondo = new Image(texturaFondo);
        imgFondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        stage.addActor(imgFondo);
        
        Button backButton = new TextButton(languageManager.getText("atras"), skin);
        Button playButton = new TextButton(languageManager.getText("menu_jugar"), skin);
        backButton.clearListeners();
        playButton.clearListeners();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PantallaMapa(game,usuario));
            }
        });
        
        Button btnRanking = new TextButton(languageManager.getText("ranking"),skin);
        btnRanking.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                List<Usuario> usuariosRanked = Ranking.obtenerRanking();
                StringBuilder builder = new StringBuilder();
                
                for(int i=0; i<usuariosRanked.size();i++){
                    Usuario usuarioR = usuariosRanked.get(i);
                    builder.append((i+1))
                    .append(".")
                    .append(usuarioR.getNombreUsuario())
                    .append("-")
                    .append(languageManager.getText("tiempo_promedio"))
                    .append(": ")
                    .append(String.format("%.2f",usuarioR.getTiempoPromedioPorNivel()))
                    .append("s\n");
                    
                }
                
                JTextArea area = new JTextArea(builder.toString());
                area.setEditable(false);
                area.setFocusable(false); 
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                
                JScrollPane scrollPane = new JScrollPane(area);
                scrollPane.setPreferredSize( new Dimension( 350, 350 ) );
                JOptionPane.showMessageDialog(null, scrollPane, languageManager.getText("ranking"),  
                JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        Button btnAvatar= new TextButton(languageManager.getText("avatar_seleccion"),skin);
        btnAvatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               game.setScreen(new AvatarSelectionScreen(usuario,game));
            }
        });
        
        Button btnBorrar = new TextButton(languageManager.getText("borrar_usuario"),skin);
        btnBorrar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int confirmacion =JOptionPane.showConfirmDialog(null, languageManager.getText("seguro_borrar"),
                languageManager.getText("confirmacion"),JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                
                if(confirmacion==JOptionPane.YES_OPTION){
                    usuario.eliminarCuenta();
                    game.setScreen(new MenuScreen(game));
                }
            }
        });
        
        volumenSlider = new Slider(0,1,0.1f,false,skin);
        volumenSlider.setValue(usuario.getVolumenMusica());
        
        Label labelVolumen = new Label(languageManager.getText("volumen"),skin);
        Button btnGuardar = new TextButton(languageManager.getText("guardar_volumen"),skin);
       
        btnGuardar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                usuario.setVolumenMusica(volumenSlider.getValue());
                MenuScreen.musicMain.setVolume(volumenSlider.getValue());
            }
        });
        
        Table table = new Table();
        table.setFillParent(true);
        table.add(titleLabel).colspan(2).padBottom(10).row();
        table.add(imgAvatar).colspan(2).size(100, 100).padBottom(10).row(); 
        table.add(labelVolumen).colspan(2).padBottom(10).row(); 
        table.add(volumenSlider).colspan(2).padBottom(10).row();
        table.add(btnGuardar).colspan(2).padBottom(10).row(); 
        table.add(statsLabel).colspan(2).row(); 

        table.add(playButton).pad(10).width(150); 
        table.add(btnBorrar).pad(10).width(150).row(); 
        table.add(btnRanking).pad(10).width(150); 
        table.add(btnAvatar).pad(10).width(150).row();
        table.add(backButton).colspan(2).pad(10).width(150).row(); 
        
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

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
        texturaAvatar.dispose();
        MenuScreen.musicMain.pause();
        
    }
    
   
}


