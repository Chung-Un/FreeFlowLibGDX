package com.Progra2.flowfree.screens;

import static com.Progra2.flowfree.flowfreegame.LanguageManager.languageManager;
import com.Progra2.flowfree.flowfreegame.FlowFreeGame;
import com.Progra2.flowfree.model.Usuario;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

public class ChangePasswordScreen implements Screen {
    private FlowFreeGame game;
    private Stage stage;
    private Skin skin;
    private TextField currentPasswordField, newPasswordField, confirmPasswordField;
    private Label messageLabel;
    private Usuario usuarioActual;

    public ChangePasswordScreen(FlowFreeGame game, Usuario usuario) {
        this.game = game;
        this.usuarioActual = usuario;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        
        // Establecer música si es necesario
        if (MenuScreen.musicMain != null && !MenuScreen.musicMain.isPlaying()) {
            MenuScreen.musicMain.setVolume(usuarioActual.getVolumenMusica());
            MenuScreen.musicMain.play();
        }
        
        // Fondo
        Texture texturaFondo = new Texture(Gdx.files.internal("FotoFondo.png")); 
        Image imgFondo = new Image(texturaFondo);
        imgFondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(imgFondo);
        
        // Crear elementos UI
        Label titleLabel = new Label(languageManager.getText("cambiar_password") != null ? 
                languageManager.getText("cambiar_password") : "Cambiar Contraseña", skin);
        titleLabel.setFontScale(2);

        currentPasswordField = new TextField("", skin);
        currentPasswordField.setPasswordMode(true);
        currentPasswordField.setPasswordCharacter('*');
        
        newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');
        
        confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');

        Button changeButton = new TextButton(languageManager.getText("cambiar") != null ? 
                languageManager.getText("cambiar") : "Cambiar", skin);
        Button backButton = new TextButton(languageManager.getText("atras"), skin);

        messageLabel = new Label("", skin);
        messageLabel.setWrap(true);

        // Tabla para organizar los elementos
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(titleLabel).padBottom(20).row();
        
        table.add(new Label(languageManager.getText("password_actual") != null ? 
                languageManager.getText("password_actual") : "Contraseña Actual:", skin)).left().padBottom(5).row();
        table.add(currentPasswordField).width(200).padBottom(15).row();
        
        table.add(new Label(languageManager.getText("password_nueva") != null ? 
                languageManager.getText("password_nueva") : "Nueva Contraseña:", skin)).left().padBottom(5).row();
        table.add(newPasswordField).width(200).padBottom(15).row();
        
        table.add(new Label(languageManager.getText("confirmar_password") != null ? 
                languageManager.getText("confirmar_password") : "Confirmar Contraseña:", skin)).left().padBottom(5).row();
        table.add(confirmPasswordField).width(200).padBottom(20).row();
        
        table.add(changeButton).padBottom(10).row();
        table.add(backButton).padBottom(15).row();
        table.add(messageLabel).width(300).center().row();

        stage.addActor(table);

        // Acción del botón "Cambiar"
        changeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cambiarPassword();
            }
        });

        // Acción del botón "Volver" - MODIFICADO para usar MenuScreen en lugar de ProfileScreen
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game, usuarioActual));
            }
        });
    }

    // Método para cambiar la contraseña
    private void cambiarPassword() {
        String passwordActual = currentPasswordField.getText();
        String nuevaPassword = newPasswordField.getText();
        String confirmarPassword = confirmPasswordField.getText();

        // Validar que los campos no estén vacíos
        if (passwordActual.isEmpty() || nuevaPassword.isEmpty() || confirmarPassword.isEmpty()) {
            messageLabel.setText(languageManager.getText("campos"));
            return;
        }

        // Verificar que las nuevas contraseñas coincidan
        if (!nuevaPassword.equals(confirmarPassword)) {
            messageLabel.setText(languageManager.getText("password_no_coincide") != null ? 
                    languageManager.getText("password_no_coincide") : "Las contraseñas no coinciden");
            return;
        }
        
        // Cambiar la contraseña usando el método de Usuario
        boolean cambioExitoso = usuarioActual.cambiarPassword(passwordActual, nuevaPassword);
        
        if (cambioExitoso) {
            messageLabel.setText(languageManager.getText("password_cambiada") != null ? 
                    languageManager.getText("password_cambiada") : "Contraseña cambiada exitosamente");
            
            // Limpiar campos
            currentPasswordField.setText("");
            newPasswordField.setText("");
            confirmPasswordField.setText("");
            
            // MODIFICADO: volver a MenuScreen después de un retraso en lugar de ProfileScreen
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new MenuScreen(game, usuarioActual));
                }
            });
        } else {
            messageLabel.setText(languageManager.getText("password_actual_incorrecta") != null ? 
                    languageManager.getText("password_actual_incorrecta") : "Contraseña actual incorrecta");
        }
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