package com.Progra2.flowfree.flowfreegame;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LanguageManager {

    private static LanguageManager instance;
    private Map<String, Map<String, String>> languages;
    private String currentLanguage;
    private final String[] supportedLanguages = {"es", "en", "fr"};
    
    private LanguageManager() {
        languages = new HashMap<>();
        currentLanguage = "es"; // Idioma por defecto
        loadLanguages();
    }
    
    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

private void loadLanguages() {
    for (String lang : supportedLanguages) {
        try {
            // Para libGDX, usa esto:
            FileHandle fileHandle = Gdx.files.internal("languages/" + lang + ".txt");
            if (!fileHandle.exists()) {
                System.err.println("No se encontró el archivo de idioma: " + lang);
                continue;
            }
            
            BufferedReader reader = new BufferedReader(fileHandle.reader("UTF-8"));
            Map<String, String> translations = new HashMap<>();
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || !line.contains("=")) continue;
                
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    translations.put(parts[0].trim(), parts[1].trim());
                }
            }
            
            languages.put(lang, translations);
            reader.close();
        } catch (IOException e) {
            System.err.println("Error al cargar el idioma " + lang + ": " + e.getMessage());
        }
    }
}
    
    public void setLanguage(String language) {
        if (languages.containsKey(language)) {
            currentLanguage = language;
            
           
        }
    }
    
    public String getText(String key) {
        Map<String, String> currentTranslations = languages.get(currentLanguage);
        if (currentTranslations != null && currentTranslations.containsKey(key)) {
            return currentTranslations.get(key);
        }
        return key; // Devolver la clave si no se encuentra traducción
    }
    
    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }
    
    public String getCurrentLanguage() {
        return currentLanguage;
    }

    // Método para obtener el nombre del idioma a partir del código
    public String getLanguageName(String code) {
        Map<String, String> names = new HashMap<>();
        names.put("es", "Español");
        names.put("en", "English");
        names.put("fr", "Français");
        return names.getOrDefault(code, code);
    }
}
