package com.Progra2.flowfree.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class AvatarManager {
    private static final String AVATARS_FOLDER = "avatars";
    private static final String[] DEFAULT_AVATARS = { 
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
    
    public static File[] obtenerArchivosAvatar() {
        File folder = new File(AVATARS_FOLDER);
        return folder.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".png") || 
            name.toLowerCase().endsWith(".jpg") || 
            name.toLowerCase().endsWith(".jpeg"));
    }

}
