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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class AvatarSelectionScreen implements Screen {
    private Stage stage;
    public static Usuario usuarioActual;
    private Array<Texture> avatarTextures;
    private FlowFreeGame game;
    private BitmapFont font; 
    private Skin skin;
    
    public AvatarSelectionScreen(Usuario usuario, FlowFreeGame game) {
        MenuScreen.musicMain.setVolume(usuario.getVolumenMusica());
        MenuScreen.musicMain.play();
        this.game = game;
        this.usuarioActual = usuario;
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont(Gdx.files.internal("default.fnt"));
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
        
        Label.LabelStyle labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font; 
        labelStyle.fontColor = Color.WHITE; 
        
        TextButton.TextButtonStyle btnStyle = skin.get(TextButton.TextButtonStyle.class);
        btnStyle.font = font; 
       
        Label titleLabel = new Label(languageManager.getText("avatar_seleccion"), labelStyle);
        mainTable.add(titleLabel).colspan(3).pad(10);
        mainTable.row();
        
        cargarTexturas();
        
        //grid de avatares
        Table avatarGrid = new Table();
        avatarGrid.pack();
        avatarGrid.setHeight(Gdx.graphics.getHeight()-200);
        ScrollPane scrollPane = new ScrollPane(avatarGrid,skin);
        scrollPane.setScrollingDisabled(false, true); 
        scrollPane.setFadeScrollBars(false); 
        scrollPane.setScrollbarsOnTop(true);
        mainTable.add(scrollPane).colspan(3).pad(20).fill().expand();
        mainTable.row();
        
        int index = 0;
        
        File[] avatarFiles = AvatarManager.obtenerArchivosAvatar();
        for (File avatarFile : avatarFiles) {
            final String avatarName = avatarFile.getName();
            
            //crear imagen del avatar
            Image avatarImage = new Image(avatarTextures.get(index));
            avatarImage.setSize(100, 100);
            
            //listener para seleccion
            avatarImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    usuarioActual.setAvatar(avatarName);
                    JOptionPane.showMessageDialog(null, languageManager.getText("avatar_seleccionadoExito"),
                    languageManager.getText("avatar_seleccionado"),JOptionPane.INFORMATION_MESSAGE);
                }
            });
            avatarGrid.add(avatarImage).size(100, 100).pad(10);
            
            //nueva fila cada 3 avatares
            if ((index + 1) % 3 == 0) {
                avatarGrid.row();
            }
            
            index++;
        }
        
        //mostrar avatar personalizado si hay
        File[] archivosAvataresPersonalizados = new File(AvatarManager.USER_AVATARS_FOLDER 
        + File.separator + usuarioActual.getNombreUsuario()).listFiles(
        (dir, name) -> name.toLowerCase().endsWith(".png") ||
                   name.toLowerCase().endsWith(".jpg") ||
                   name.toLowerCase().endsWith(".jpeg")
        );

        if (archivosAvataresPersonalizados != null) {
        for (File avatarPersonalizado : archivosAvataresPersonalizados) {
            final String avatarPersonalizadoNombre = avatarPersonalizado.getName();

            Texture customAvatarTexture = new Texture(Gdx.files.absolute(avatarPersonalizado.getAbsolutePath()));
            avatarTextures.add(customAvatarTexture); // Add texture to avatarTextures

            Image customAvatarImage = new Image(customAvatarTexture);
            customAvatarImage.setSize(100, 100);

            customAvatarImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    usuarioActual.setAvatar(avatarPersonalizadoNombre); 
                    usuarioActual.setCustomAvatarPath(avatarPersonalizado.getAbsolutePath()); 

                    JOptionPane.showMessageDialog(null, languageManager.getText("avatar_seleccionadoExito"),
                            languageManager.getText("avatar_seleccionado"), JOptionPane.INFORMATION_MESSAGE);
                }
            });

            avatarGrid.add(customAvatarImage).size(100, 100).pad(10);

            if ((index + 1) % 3 == 0) {
                avatarGrid.row();
            }

            index++;
        }
        }
        
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

                    String nombrenuevoFile = AvatarManager.guardarAvatarPersonalizado(fileSeleccionada);
                    if (nombrenuevoFile != null) {
                        usuarioActual.setAvatar(nombrenuevoFile);
                        usuarioActual.setCustomAvatarPath(nombrenuevoFile);
                        cargarTexturas(); 

                        JOptionPane.showMessageDialog(null,
                            languageManager.getText("avatar_subido_exito"), "Avatar", JOptionPane.INFORMATION_MESSAGE);
                            cargarTexturas();
                    } else {
                        JOptionPane.showMessageDialog(null,
                            languageManager.getText("error_subir_avatar"), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, languageManager.getText("avatar_no_seleccionado"), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        TextButton backButton = new TextButton(languageManager.getText("atras"), btnStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, usuarioActual));
            }
        });
        
        Table btnTable = new Table();
        btnTable.bottom().padBottom(30);
        btnTable.add(btnSubir).center().pad(10);
        btnTable.add(backButton).center().pad(10);
        btnTable.row();
        
        mainTable.add(btnTable).colspan(3).pad(10).expandX().bottom();
    }
    
    private void cargarTexturas() {
        
        avatarTextures = new Array<>();
        //cargar avatares predeterminados
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
        
        File[] customAvatarFiles = new File(AvatarManager.USER_AVATARS_FOLDER 
        + File.separator + usuarioActual.getNombreUsuario()).listFiles(
        (dir, name) -> name.toLowerCase().endsWith(".png") ||
                   name.toLowerCase().endsWith(".jpg") ||
                   name.toLowerCase().endsWith(".jpeg")
        );

    if (customAvatarFiles != null) {
        for (File customAvatarFile : customAvatarFiles) {
            try {
                Texture texture = new Texture(Gdx.files.absolute(customAvatarFile.getAbsolutePath()));
                avatarTextures.add(texture);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        MenuScreen.musicMain.pause();
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