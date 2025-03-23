package com.Progra2.flowfree.screens;

import com.Progra2.flowfree.flowfreegame.FlowFreeGame;
import static com.Progra2.flowfree.flowfreegame.LanguageManager.languageManager;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class AvatarSelectionScreen implements Screen {
    private Stage stage;
    public static Usuario usuarioActual;
    private Array<Texture> avatarTextures;
    private FlowFreeGame game;
    private BitmapFont font; 
    
    public AvatarSelectionScreen(Usuario usuario, FlowFreeGame game) {
        this.game = game;
        this.usuarioActual = usuario;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont(Gdx.files.internal("default.fnt"));
        // Inicializar la pantalla
        inicializarUI();
    }
    
    private void inicializarUI() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        
        Texture texturaFondo = new Texture(Gdx.files.internal("FotoFondo.png")); 
        Image imgFondo = new Image(texturaFondo);
        imgFondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        stage.addActor(imgFondo);
        
        stage.addActor(mainTable);
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font; 
        labelStyle.fontColor = Color.WHITE; 
        
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = font; 
        
        
        
        // Título
        Label titleLabel = new Label(languageManager.getText("avatar_seleccion"), labelStyle);
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
                    System.out.println(languageManager.getText("avatar_seleccionado") + avatarName);
                }
            });
            avatarGrid.add(avatarImage).size(100, 100).pad(10);
            
            // Nueva fila cada 3 avatares
            if ((index + 1) % 3 == 0) {
                avatarGrid.row();
            }
            
            index++;
        }
        
        // Mostrar avatar personalizado (si existe)
        File fileAvatarPersonalizado = AvatarManager.obtenerAvatarPersonalizado(usuarioActual.getNombreUsuario());
        if (fileAvatarPersonalizado != null) {
            final String avatarPersonalizadoNombre = fileAvatarPersonalizado.getName();
            
            Image customAvatarImage = new Image(avatarTextures.get(index));
            customAvatarImage.setSize(100, 100);
            
            customAvatarImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    usuarioActual.setAvatar(avatarPersonalizadoNombre); 
                    System.out.println(languageManager.getText("avatar_seleccionado") + avatarPersonalizadoNombre);
                }
            });
            avatarGrid.add(customAvatarImage).size(100, 100).pad(10);
            
            // Nueva fila si es necesario
            if ((index + 1) % 3 == 0) {
                avatarGrid.row();
            }
            
            index++;
        }
        
        mainTable.add(avatarGrid).colspan(3).pad(20);
        mainTable.row();
        
        TextButton btnSubir = new TextButton(languageManager.getText("avatar_subir"), btnStyle);
        btnSubir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle(languageManager.getText("avatar_seleccion"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Imagen", "jpg", "png", "jpeg"));
                int imagenSeleccionada = fileChooser.showOpenDialog(null);

                if (imagenSeleccionada == JFileChooser.APPROVE_OPTION) {
                    File fileSeleccionada = fileChooser.getSelectedFile();

                    String newFileName = AvatarManager.guardarAvatarPersonalizado(fileSeleccionada);
                    if (newFileName != null) {
                        usuarioActual.setAvatar(newFileName);
                        cargarTexturas(); 

                        JOptionPane.showMessageDialog(null,
                            languageManager.getText("avatar_subido_exito"), "Avatar", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                            languageManager.getText("error_subir_avatar"), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, languageManager.getText("avatar_no_seleccionado"), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mainTable.add(btnSubir).pad(10);
        
        // Botón para volver
        TextButton backButton = new TextButton(languageManager.getText("atras"), btnStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, usuarioActual));
            }
        });
        mainTable.add(backButton).pad(20);
    }
    
    private void cargarTexturas() {
        avatarTextures = new Array<>();
        
        // Cargar avatares predeterminados
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
        
        File customAvatarFile = AvatarManager.obtenerAvatarPersonalizado(usuarioActual.getNombreUsuario());
        if (customAvatarFile != null) {
            try {
                Texture texture = new Texture(Gdx.files.absolute(customAvatarFile.getAbsolutePath()));
                avatarTextures.add(texture);
            } catch (Exception e) {
                System.err.println("Error al cargar textura personalizada: " + customAvatarFile.getName());
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