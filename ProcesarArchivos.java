package progracs.practica2prueba;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcesarArchivos {
    public final String directorioRaiz; 

    // Constructor para iniciar directorio raíz
    public ProcesarArchivos(String directorioRaiz) {
        this.directorioRaiz = directorioRaiz;
    }

    // Método para iniciar el procesamiento de archivos y directorios
    public void iniciarProcesamiento() {
        //Creamos un objeto File para el directorio raiz
        File raiz = new File(directorioRaiz); 
  
        //Obtenemos los subdirectorios con el patron solicitado
        File[] subdirectorios = raiz.listFiles((dir, name) -> new File(dir, name).isDirectory() && name.matches("\\w+_\\d{4}"));

        if (subdirectorios != null) {
            //Creamos una lista para almacenar los hilos
            List<Hilo> hilos = new ArrayList<>(); 
            //Iniciamos los hilos por cada subdirectorio
            for (File subdir : subdirectorios) {
                Hilo hilo = new Hilo(subdir, directorioRaiz);
                hilos.add(hilo);
                hilo.start();
            }
            // .join espera a que todos los hilos terminen
            for (Hilo hilo : hilos) {
                try {
                    hilo.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Reporte creado satisfactoriamente en el directorio: " + directorioRaiz);
    }
}
