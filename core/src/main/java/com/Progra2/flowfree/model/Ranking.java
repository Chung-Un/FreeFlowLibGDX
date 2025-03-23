/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Progra2.flowfree.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Nadiesda Fuentes
 */
public class Ranking {
 public static List<Usuario> obtenerRanking() {
        File carpeta = new File("usuarios/");
        List<Usuario> usuarios = new ArrayList<>();

        for (File archivo : carpeta.listFiles()) {
            Usuario usuario = Usuario.cargarDatos(archivo.getName().replace(".dat", ""));
            if (usuario != null) {
                usuarios.add(usuario);
            }
        }

        usuarios.sort((u1, u2) -> {
            double tiempoPromedio1 = u1.getTiempoPromedioPorNivel();
            double tiempoPromedio2 = u2.getTiempoPromedioPorNivel();
            return Double.compare(tiempoPromedio1, tiempoPromedio2);
        });
        return usuarios;
    }
}
