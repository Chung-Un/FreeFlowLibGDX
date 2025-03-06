/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.FreeFlowLibGDX;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nadiesda Fuentes
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    // Datos de usuario requeridos
    private String nombreUsuario; 
    private String contrasena; 
    private String nombreCompleto;
    private LocalDate fechaRegistro;
    private LocalDateTime ultimaSesion;
    private int progreso;

    private String rutaAvatar; 

    // Estadísticas de juego
    private int tiempoTotalJugado; 
    private int nivelesCompletados;
    private int puntajeTotal;
    private List<Partida> historialPartidas;

    // Preferencias de juego
    private int volumen;
    private String idioma;
    private String configuracionControles;

    // Ranking
    private int rankingGeneral;

    // Constructor
    public Usuario(String nombreUsuario, String contrasena, String nombreCompleto) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena; // En un escenario real, usar hash seguro
        this.nombreCompleto = nombreCompleto;
        this.fechaRegistro = LocalDate.now();
        this.ultimaSesion = LocalDateTime.now();
        this.historialPartidas = new ArrayList<>();
        
        // Configuraciones predeterminadas
        this.volumen = 50;
        this.idioma = "es";
        this.configuracionControles = "default";
        this.rutaAvatar = "default_avatar.png";
    }

    // Métodos para registrar partida
    public void registrarPartida(Partida partida) {
        historialPartidas.add(partida);
        actualizarEstadisticas(partida);
    }

    private void actualizarEstadisticas(Partida partida) {
        tiempoTotalJugado += partida.getTiempoJugado();
        
        if (partida.isNivelCompletado()) {
            nivelesCompletados++;
            puntajeTotal += partida.getPuntaje();
        }
        
        // Actualizar última sesión
        ultimaSesion = LocalDateTime.now();
    }

    // Métodos de configuración
    public void cambiarAvatar(String rutaAvatar) {
        this.rutaAvatar = rutaAvatar;
    }

    public void configurarPreferencias(int volumen, String idioma, String controles) {
        this.volumen = volumen;
        this.idioma = idioma;
        this.configuracionControles = controles;
    }

    // Métodos para actualizar contraseña (con validaciones)
    public boolean cambiarContrasena(String contrasenaActual, String contrasenaNueva) {
        if (this.contrasena.equals(contrasenaActual)) {
            this.contrasena = contrasenaNueva;
            return true;
        }
        return false;
    }

    // Clase interna para representar partidas
    public static class Partida implements Serializable {
        private LocalDateTime fechaPartida;
        private int tiempoJugado; // en minutos
        private int puntaje;
        private int nivelJugado;
        private boolean nivelCompletado;

        public Partida(int nivelJugado, int puntaje, int tiempoJugado) {
            this.fechaPartida = LocalDateTime.now();
            this.nivelJugado = nivelJugado;
            this.puntaje = puntaje;
            this.tiempoJugado = tiempoJugado;
            this.nivelCompletado = puntaje > 0; // Ejemplo simple de completitud
        }

        // Getters
        public int getTiempoJugado() { return tiempoJugado; }
        public int getPuntaje() { return puntaje; }
        public boolean isNivelCompletado() { return nivelCompletado; }
    }

    // Getters y setters (se pueden generar automáticamente)
    public String getNombreUsuario() { return nombreUsuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public LocalDateTime getUltimaSesion() { return ultimaSesion; }
    public int getTiempoTotalJugado() { return tiempoTotalJugado; }
    public int getNivelesCompletados() { return nivelesCompletados; }
    public int getPuntajeTotal() { return puntajeTotal; }
    public List<Partida> getHistorialPartidas() { return historialPartidas; }
    public String getRutaAvatar() { return rutaAvatar; }
    public

}
