
package progracs.practica2prueba;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// creamos la clase Hilo que hereda de Thread y procesara archivos y directorios en segundo plano
public class Hilo extends Thread {
    private final File subdir; // atributo que es el directorio que será procesado por este hilo
    private final String directorioRaiz; // el directorio donde guardarmemos el reporte final

    // Constructor para iniciar subdir y directorioRaiz
    Hilo(File subdir, String directorioRaiz) {
        this.subdir = subdir;
        this.directorioRaiz = directorioRaiz;
    }

    // Método run que se ejecuta cuando inicia el hilo
    @Override
    public void run() {
        // el BufferedWriter para escribir en el archivo reporte_hallazgos.txt(reporte final)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directorioRaiz + "/reporte_hallazgos.txt", true))) {
            // comparamos si el nombre del subdirectorio hace match con el formato mes_año
            if (subdir.getName().matches("\\w+_\\d{4}")) {
                // .split nos ayuda a dividir el nombre con un guion bajo
                String[] partes = subdir.getName().split("_");
                String mes = partes[0];
                String año = partes[1];
                // Inicia el String con la primer letra en mayuscula seguido del año
                writer.write(mes.substring(0, 1).toUpperCase() + mes.substring(1) + " " + año + "\n");
                // Llamamos al metodo para procecesar el contenido
                procesarDirectorio(subdir, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Creamos el metodo para procesar los directorios y subdirectorios
    private void procesarDirectorio(File directorio, BufferedWriter writer) {
        //Guardamos los subdirectorios y archivos
        File[] archivosYSubdirectorios = directorio.listFiles(); 

        if (archivosYSubdirectorios != null) {
           
            // Recorremos los archivos y subdirectorios
            for (File archivo : archivosYSubdirectorios) {
                 // Si es un direcotrio entra en el metodo
                if (archivo.isDirectory()) {   
                    procesarDirectorio(archivo, writer);
                } else if (archivo.isFile() && archivo.getName().matches("reporte_\\d{2}_\\d{2}_\\d{4}\\.txt")) {
                    // nos hace match nuevamente con el nombre del archivo y el formato solicitado
                    procesarArchivoTexto(archivo, writer);
                }
            }
        }
    }

    // Método para precesar el archivo .txt especifico
    private void procesarArchivoTexto(File archivo, BufferedWriter writer) {
        // leemos el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            String fechaReporte = archivo.getName().substring(8, 10) + "/" + archivo.getName().substring(11, 13) + "/" + archivo.getName().substring(14, 18);
            String topico = "";
            //Creamos una lista para almacenar los hallazgos 
            List<String> hallazgos = new ArrayList<>(); 
            boolean enHallazgos = false; //Nos inidica si esta en hallazgos

            // Leer el archivo linea por linea comparando lo solicitado
            while ((linea = reader.readLine()) != null) {
                //Si encuentra topico procede a leer la linea 
                if (linea.startsWith("# Topico del reporte")) {
                    topico = reader.readLine().substring(2);
                    //inicia hallazgos
                } else if (linea.startsWith("# Hallazgos")) {
                    enHallazgos = true;
                    //termina los hallazgos
                } else if (linea.startsWith("#") && enHallazgos) {
                    enHallazgos = false; 
                    //agrega los hallazgos a linea
                } else if (enHallazgos && linea.startsWith("- ")) {
                    hallazgos.add(linea);
                }
            }

            // Escribimos el reporte
            writer.write("# Reporte del " + fechaReporte + "\n");
            writer.write("## " + topico + "\n");
            for (String hallazgo : hallazgos) {
                writer.write(hallazgo + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
