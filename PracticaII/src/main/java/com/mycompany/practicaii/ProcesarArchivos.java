package com.mycompany.practicaII;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProcesarArchivos {

    public static List<String> leerArchivosReporte(String directorioRaiz) {
        List<String> contenidosReporte = new ArrayList<>();
        File dirRaiz = new File(directorioRaiz);
        
        if (dirRaiz.exists() && dirRaiz.isDirectory()) {
            File[] archivoMes = dirRaiz.listFiles(File::isDirectory);
            
            if (archivoMes != null) {
                for (File arcMes : archivoMes) {
                    File[] archivos = arcMes.listFiles((directorio, nombre) -> nombre.endsWith(".txt"));
                    
                    if (archivos != null) {
                        for (File archivo : archivos) {
                            try {
                                List<String> lineas = Files.readAllLines(Paths.get(archivo.getPath()));
                                contenidosReporte.add(String.join("\n", lineas));
                            } catch (IOException e) {
                                System.out.println("Error al leer el archivo: " + archivo.getPath());
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("El directorio raíz no existe: " + directorioRaiz);
        }
        
        return contenidosReporte;
    }
}
