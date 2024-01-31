package Hilos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class HiloServidor implements Runnable {
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    List<PrintWriter> mensajes;

    public HiloServidor(Socket socket, PrintWriter pw, List<PrintWriter> mensajes) {
        this.socket = socket;
        this.pw = pw;
        this.mensajes = mensajes;

        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error recibiendo InputStream del cliente");
            throw new RuntimeException(e);
        }
    }

    private void muestraMensajes(List<PrintWriter> mensajes, String mensaje) {
        for(PrintWriter mj: mensajes) {
            mj.println(": " + mensaje);
        }
    }

    @Override
    public void run() {
        String mensaje;

        try {
            while((mensaje = br.readLine())!=null) {
                System.out.println("Mensaje recibido: " + mensaje);
                muestraMensajes(mensajes, mensaje);
            }
            socket.close();
        } catch(IOException e) {
            System.err.println("Error leyendo mensaje del cliente");
        }
    }
}
