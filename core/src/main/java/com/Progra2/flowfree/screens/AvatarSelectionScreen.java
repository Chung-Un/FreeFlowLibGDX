package com.Progra2.flowfree.screens;

import java.io.File;

import com.Progra2.flowfree.model.AvatarManager;
import com.Progra2.flowfree.model.Usuario;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class AvatarSelectionScreen implements Screen {
    private Stage stage;
    private Usuario usuarioActual;
    private Array<Texture> avatarTextures;
    private Screen previousScreen;
    
    public AvatarSelectionScreen(Usuario usuario, Screen previousScreen) {
        this.usuarioActual = usuario;
        this.previousScreen = previousScreen;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        // Inicializar la pantalla
        inicializarUI();
    }
    
    private void inicializarUI() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        // Título
        Label titleLabel = new Label("Selecciona tu Avatar", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mainTable.add(titleLabel).colspan(3).pad(20);
        mainTable.row();
        
        // Cargar las texturas de los avatares
        cargarTexturas();
        
        // Crear grid de avatares
        Table avatarGrid = new Table();
        int index = 0;
        
        File[] avatarFiles = AvatarManager.obtenerArchivosAvatar();
        for (File avatarFile : avatarFiles) {
            final String avatarName = avatarFile.getName();
            
            // Crear imagen del avatar
            Image avatarImage = new Image(avatarTextures.get(index));
            avatarImage.setSize(100, 100);
            
            // Añadir listener para selección
            avatarImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    usuarioActual.setAvatar(avatarName);
                    System.out.println("Avatar seleccionado: " + avatarName);
                    // Opcional: cerrar la pantalla o mostrar confirmación
                }
            });
            avatarGrid.add(avatarImage).size(100, 100).pad(10);
            
            // Nueva fila cada 3 avatares
            if ((index + 1) % 3 == 0) {
                avatarGrid.row();
            }
            
            index++;
        }
        
        mainTable.add(avatarGrid).colspan(3).pad(20);
        mainTable.row();
        
        // Botón para volver
        TextButton backButton = new TextButton("Volver", new TextButton.TextButtonStyle());
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Volver a la pantalla anterior
                if (previousScreen != null) {
                    // Aquí necesitas una referencia a tu Game para cambiar de pantalla
                    // Por ejemplo: game.setScreen(previousScreen);
                }
            }
        });
        mainTable.add(backButton).pad(20);
    }
    
    private void cargarTexturas() {
        avatarTextures = new Array<>();
        
        File[] avatarFiles = AvatarManager.obtenerArchivosAvatar();
        for (File avatarFile : avatarFiles) {
            try {
                Texture texture = new Texture(Gdx.files.absolute(avatarFile.getAbsolutePath()));
                avatarTextures.add(texture);
            } catch (Exception e) {
                System.err.println("Error al cargar textura: " + avatarFile.getName());
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(delta);
        stage.draw();
    }
    @Override
    public void dispose() {
        stage.dispose();
        for (Texture texture : avatarTextures) {
            texture.dispose();
        }
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void hide() {
    }
    
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
}