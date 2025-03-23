package com.Progra2.flowfree.model;

import com.Progra2.flowfree.screens.AvatarSelectionScreen;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class AvatarManager {
    private static final String AVATARS_FOLDER = "avatars";
    public static final String USER_AVATARS_FOLDER = "user_avatars";
    private static final String[] DEFAULT_AVATARS = { 
        "defaultAvatar.png",
        "avatar1.png", 
        "avatar2.png", 
        "avatar3.png", 
        "avatar4.png",
        "avatar5.png"
    };
    
    public static void inicializarAvatares() {
        File avatarsFolder = new File(AVATARS_FOLDER);
        if (!avatarsFolder.exists()) {
            avatarsFolder.mkdirs();
        }
        
        File userAvatarsFolder = new File(USER_AVATARS_FOLDER);
        if (!userAvatarsFolder.exists()) {
            userAvatarsFolder.mkdirs();
        }
        
        // Copiar avatares predeterminados desde los assets
        copiarAvataresPredeterminados();
    }

    private static void copiarAvataresPredeterminados() {
        for (String avatarName : DEFAULT_AVATARS) {
            File avatarFile = new File(AVATARS_FOLDER + File.separator + avatarName);
            
            // Si el archivo ya existe, no lo sobreescribimos
            if (!avatarFile.exists()) {
                try {
                    // Obtener el archivo desde los assets
                    FileHandle assetFile = Gdx.files.internal("assets/avatars/" + avatarName);
                    
                    if (assetFile.exists()) {
                        // Copiar el archivo desde assets a la carpeta de avatares
                        byte[] bytes = assetFile.readBytes();
                        try (FileOutputStream fos = new FileOutputStream(avatarFile)) {
                            fos.write(bytes);
                        }
                    } else {
                        System.out.println("Avatar predeterminado no encontrado: " + avatarName);
                    }
                } catch (IOException e) {
                    System.err.println("Error al copiar avatar predeterminado: " + avatarName);
                    e.printStackTrace();
                }
            }
        }
    }

    public static String[] getAvataresPredeterminados() {
        return DEFAULT_AVATARS;
    }
    
    public static String getRutaAvatares() {
        return AVATARS_FOLDER;
    }
    
    public static String guardarAvatarPersonalizado(File file) {
        try {
            File userAvatarFolder = new File(USER_AVATARS_FOLDER + File.separator + 
            AvatarSelectionScreen.usuarioActual.getNombreUsuario());
            if (!userAvatarFolder.exists()) {
                userAvatarFolder.mkdirs();
            }

            String newFileName = "custom_" + System.currentTimeMillis() + "." + getFileExtension(file);
            File destination = new File(userAvatarFolder.getAbsolutePath() + File.separator + newFileName);
            
            java.nio.file.Files.copy(file.toPath(), destination.toPath());

            return destination.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Error al guardar el avatar personalizado: " + e.getMessage());
            return null;
        }
    }
    
    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastDotIndex = name.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return ""; 
        }
        return name.substring(lastDotIndex + 1);
    }
    
    public static File[] obtenerArchivosAvatar() {
        File folder = new File(AVATARS_FOLDER);
        return folder.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".png") || 
            name.toLowerCase().endsWith(".jpg") || 
            name.toLowerCase().endsWith(".jpeg"));
    }
    
    public static File[] obtenerAvatarPersonalizado(String username) {
        File userAvatarFolder = new File(USER_AVATARS_FOLDER + File.separator + username);
        if (userAvatarFolder.exists()) {
            return userAvatarFolder.listFiles((dir, name) -> 
                name.toLowerCase().endsWith(".png") || 
                name.toLowerCase().endsWith(".jpg") || 
                name.toLowerCase().endsWith(".jpeg"));
        }
        return new File[0];
    }
}
