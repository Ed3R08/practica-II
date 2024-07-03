package progracs.practica2prueba;

public class NewMain {

    // Método main para la ejecución del código
    public static void main(String[] args) {
        // Se crea una instancia de ProcesarArchivos con el path del directorio raíz
        ProcesarArchivos procesador = new ProcesarArchivos("C:\\Users\\pc\\Desktop\\Eder\\Sistemas\\Programacion cliente servidor\\bitacora");
        // Inicia el procesamiento de archivos y directorios
        procesador.iniciarProcesamiento();
    }
}
