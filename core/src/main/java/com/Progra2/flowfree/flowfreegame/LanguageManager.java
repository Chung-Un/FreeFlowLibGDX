package com.Progra2.flowfree.flowfreegame;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LanguageManager {

    public static LanguageManager languageManager;
    private Map<String, Map<String, String>> languages;
    private String currentLanguage;
    private final String[] supportedLanguages = {"es", "en", "fr"};
    
    public LanguageManager() {
        languages = new HashMap<>();
        currentLanguage = "es"; // idioma por defecto
        loadLanguages();
    }
    
    public static LanguageManager getInstance() {
        if (languageManager == null) {
            languageManager = new LanguageManager();
        }
        return languageManager;
    }

private void loadLanguages() {
    for (String lang : supportedLanguages) {
        try {
            FileHandle fileHandle = Gdx.files.internal("languages/" + lang + ".txt");
            if (!fileHandle.exists()) {
                //no se encontro ningun idioma
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
    
    public void reloadLanguages() {
        languages.clear(); 
        loadLanguages();   
    }
    
    public String getText(String key) {
        Map<String, String> currentTranslations = languages.get(currentLanguage);
        if (currentTranslations != null && currentTranslations.containsKey(key)) {
            return currentTranslations.get(key);
        }
        return key; // devolver la clave si no se encuentra traduccion
    }
    
    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }
    
    public String getCurrentLanguage() {
        return currentLanguage;
    }

    // obtener el nombre del idioma a partir del código
    public String getLanguageName(String code) {
        Map<String, String> names = new HashMap<>();
        names.put("es", "Español");
        names.put("en", "English");
        names.put("fr", "Français");
        return names.getOrDefault(code, code);
    }
}
