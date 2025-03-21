/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.flowfree.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 *
 * @author Nadiesda Fuentes
 */
public class Usuario implements Serializable {

   private static final long serialVersionUID = 1L;
    private String idUsuario;
    private String nombreUsuario;
    private String passwordHash; 
    private String nombreCompleto;
    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimaSesion;
    public int nivelesCompletados;
    private long tiempoJugado;
    private List<String> historialPartidas;
    private Map<String, String> preferencias;
    private String avatar;
    private int puntuacionGeneral;
    private List<String> amigos;
    public ArrayList<Double> tiemposPorNivel;

    

    public Usuario(String nombreUsuario, String password, String nombreCompleto) {
        this.idUsuario = UUID.randomUUID().toString();
        this.nombreUsuario = nombreUsuario;
        this.passwordHash = hashPassword(password); 
        this.nombreCompleto = nombreCompleto;
        this.fechaRegistro = LocalDateTime.now();
        this.ultimaSesion = fechaRegistro;
        this.nivelesCompletados = 0;
        this.tiempoJugado = 0;
        this.historialPartidas = new ArrayList<>();
        this.preferencias = new HashMap<>();
        this.avatar = "default.png";
        this.puntuacionGeneral = 0;
        this.amigos = new ArrayList<>();
        this.tiemposPorNivel =new ArrayList<>();
        for( int i=0; i<5 ; i++){
            tiemposPorNivel.add(0.0);
        }
    }

    // Método para cifrar la contraseña con SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al cifrar contraseña", e);
        }
    }

    // Método para verificar la contraseña ingresada por el usuario
    public boolean verificarPassword(String passwordIngresada) {
        return this.passwordHash.equals(hashPassword(passwordIngresada));
    }

    // Actualiza estadísticas y registra la partida
    public void actualizarEstadisticas(int nuevosNiveles, long tiempoSesion) {
        this.nivelesCompletados += nuevosNiveles;
        this.tiempoJugado += tiempoSesion;
//        this.puntuacionGeneral += puntosObtenidos;
        this.ultimaSesion = LocalDateTime.now();

        // Registrar en el historial
        this.historialPartidas.add("Fecha: " + ultimaSesion
                + ", Niveles: " + nuevosNiveles
                + ", Tiempo: " + tiempoSesion + "s"
//                + ", Puntos: " + puntosObtenidos
                + ", Tiempo Promedio por Nivel: " + getTiempoPromedioPorNivel() + "s");

        guardarDatos();
    }

// Método para obtener el tiempo promedio por nivel
    public double getTiempoPromedioPorNivel() {
        if (nivelesCompletados == 0) {
            return 0; // Evitar división por cero
        }
        return (double) tiempoJugado / nivelesCompletados;
    }

    // Método para establecer preferencias del usuario
    public void setPreferencia(String clave, String valor) {
        this.preferencias.put(clave, valor);
        guardarDatos();
    }

    // Método para establecer avatar
    public void setAvatar(String avatarPath) {
        this.avatar = avatarPath;
        guardarDatos();
    }

    public File getAvatarFile(){
        File avatarFile = new File("avatars/" +this.avatar);
        if (avatarFile.exists()){
            return avatarFile;
        }
        return new File("avatar/default.png");
    }

    // Método para agregar amigos
    public void agregarAmigo(String nombreAmigo) {
        if (!amigos.contains(nombreAmigo)) {
            amigos.add(nombreAmigo);
            guardarDatos();
        }
    }

    // Método para eliminar usuario
    public void eliminarCuenta() {
        File archivo = new File("usuarios/" + nombreUsuario + ".dat");
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    public void guardarDatos() {
        try {
            // Crear la carpeta "usuarios" si no existe
            File carpeta = new File("usuarios");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            // Guardar el archivo en la carpeta "usuarios"
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios/" + nombreUsuario + ".dat"))) {
                oos.writeObject(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Usuario cargarDatos(String nombreUsuario) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("usuarios/" + nombreUsuario + ".dat"))) {
            return (Usuario) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    // Getters para estadísticas
    public int getNivelesCompletados() {
        return nivelesCompletados;
    }

    public long getTiempoJugado() {
        return tiempoJugado;
    }

    public int getPuntuacionGeneral() {
        return puntuacionGeneral;
    }

    public List<String> getHistorialPartidas() {
        return historialPartidas;
    }

    public List<String> getAmigos() {
        return amigos;
    }
    
    public void setTiemposPorNivel(ArrayList<Double> tiemposPorNivel) {
        this.tiemposPorNivel = tiemposPorNivel;
    }
}