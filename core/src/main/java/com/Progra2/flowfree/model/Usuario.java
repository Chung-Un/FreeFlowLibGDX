package com.Progra2.flowfree.model;

import static com.Progra2.flowfree.flowfreegame.LanguageManager.languageManager;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JOptionPane;
import java.util.Date;
import java.text.SimpleDateFormat;

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
    private String avatarPersonalizadoDireccion;
    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimaSesion;
    public int nivelesCompletados;
    private long tiempoJugado;
    private List<String> historialPartidasTexto; 
    private List<Partida> historialPartidas;
    private Map<String, String> preferencias;
    private String avatar;
    private int puntuacionGeneral;
    private float volumenMusica;
    public ArrayList<Double> tiemposPorNivel;
    public int partidasTotales;
    private Date ultimaFechaLogin; 

    public Usuario(String nombreUsuario, String password, String nombreCompleto) {
        this.idUsuario = UUID.randomUUID().toString();
        this.nombreUsuario = nombreUsuario;
        this.passwordHash = hashPassword(password); 
        this.nombreCompleto = nombreCompleto;
        this.fechaRegistro = LocalDateTime.now();
        this.ultimaSesion = fechaRegistro;
        this.nivelesCompletados = 0;
        this.tiempoJugado = 0;
        this.historialPartidasTexto = new ArrayList<>();
        this.historialPartidas = new ArrayList<>(); 
        this.preferencias = new HashMap<>();
        this.avatar = "default.png";
        this.avatarPersonalizadoDireccion=null;
        this.puntuacionGeneral = 0;
        this.volumenMusica = 1f;
        this.tiemposPorNivel = new ArrayList<>();
        this.partidasTotales = 0;
        this.ultimaFechaLogin = new Date(); 
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
        this.ultimaSesion = LocalDateTime.now();
        this.ultimaFechaLogin = new Date(); // Actualizar la fecha de última sesión

        // Registrar en el historial como texto (para compatibilidad)
        this.historialPartidasTexto.add("Fecha: " + ultimaSesion
                + ", Niveles: " + nuevosNiveles
                + ", Tiempo: " + tiempoSesion + "s"
                + ", Tiempo Promedio por Nivel: " + getTiempoPromedioPorNivel() + "s");

        guardarDatos();
    }

    public void registrarPartida(int nivel, long tiempo, boolean completado) {
        Partida nuevaPartida = new Partida(nivel, tiempo, completado);
        this.historialPartidas.add(nuevaPartida);
        this.partidasTotales++;
        this.ultimaFechaLogin = new Date(); // Actualizar fecha de última sesión
        
        if (completado) {
            this.nivelesCompletados++;
        }
        
        guardarDatos();
    }

    // Método para cambiar la contraseña del usuario
    public boolean cambiarPassword(String passwordActual, String nuevaPassword) {
        // Verificar que la contraseña actual sea correcta
        if (!verificarPassword(passwordActual)) {
            return false; 
        }
        
        // Actualizar la contraseña con el nuevo hash
        this.passwordHash = hashPassword(nuevaPassword);
        
        // Guardar los cambios
        guardarDatos();
        
        return true; // Cambio exitoso
    }

    // Método para obtener el tiempo promedio por nivel
    public double getTiempoPromedioPorNivel() {
        if (nivelesCompletados == 0) {
            return 0;
        }
        return (double) getTiemposPorNivelTotal() / nivelesCompletados;
    }

    // Método para establecer preferencias del usuario
    public void setPreferencia(String clave, String valor) {
        this.preferencias.put(clave, valor);
        guardarDatos();
    }
    
    public double getTiemposPorNivelTotal(){
        double tiempoTotal=0;
        for(double tiempo : tiemposPorNivel){
            tiempoTotal+=tiempo;
        }
        return tiempoTotal;
    }
    
    // Método para registrar inicio de sesión
    public void registrarInicioSesion() {
        this.ultimaFechaLogin = new Date();
        guardarDatos();
    }
    
    // Método para establecer avatar
    public void setAvatar(String avatarPath) {
        this.avatar = avatarPath;
        this.avatarPersonalizadoDireccion=null;
        guardarDatos();
    }

    public File getAvatarFile(){
        File avatarFile = new File("avatars/" +this.avatar);
        if (avatarFile.exists()){
            return avatarFile;
        }
        return new File("avatar/defaultAvatar.png");
    }

    public String getAvatarFileDireccion() {
        File avatarFile = new File("avatars/" + this.avatar);
        
        if(avatarPersonalizadoDireccion!=null && new File(avatarPersonalizadoDireccion).exists()){
          return avatarPersonalizadoDireccion;
        }
        else if (avatarFile.exists()) {
            return "avatars/" + this.avatar; 
        }
        return "avatars/defaultAvatar.png"; 
    }

    // Método para eliminar usuario
    public void eliminarCuenta() {
        File archivo = new File("usuarios/" + nombreUsuario + ".dat");
        if (archivo.exists()) {
            archivo.delete();
        }
        
        //borrar su avatar personalizado si tiene uno
        if (avatarPersonalizadoDireccion != null) {
            File archivoAvatarPersonalizado = new File(avatarPersonalizadoDireccion);
            if (archivoAvatarPersonalizado.exists()) {
                archivoAvatarPersonalizado.delete();
            }
        }
        
        JOptionPane.showMessageDialog(null, nombreUsuario + " "+languageManager.getText("usuario_borrado"));
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
    
    public void setCustomAvatarPath(String avatarPersonalizadoDireccion) {
        this.avatarPersonalizadoDireccion = avatarPersonalizadoDireccion;
        guardarDatos();
    }
    
    public void setVolumenMusica(float volumenMusica){
        this.volumenMusica = volumenMusica;
        guardarDatos();
    }
    
    public String getCustomAvatarPath() {
        return avatarPersonalizadoDireccion;
    }
    
    // Getters para estadísticas
    
    public String getNombreUsuario(){
        return nombreUsuario;
    }
    
    public float getVolumenMusica(){
        return volumenMusica;
    }
    
    public int getNivelesCompletados() {
        return nivelesCompletados;
    }

    public long getTiempoJugado() {
        return tiempoJugado;
    }

    public int getPuntuacionGeneral() {
        return puntuacionGeneral;
    }

    
    public List<String> getHistorialPartidasTexto() {
        return historialPartidasTexto;
    }
    
    
    public List<Partida> getHistorialPartidas() {
        return historialPartidas;
    }
    
    
    public Date getUltimaFechaLogin() {
        return ultimaFechaLogin;
    }
    
    public void setTiemposPorNivel(ArrayList<Double> tiemposPorNivel) {
        this.tiemposPorNivel = tiemposPorNivel;
    }
}
