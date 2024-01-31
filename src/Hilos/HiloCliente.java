package Hilos;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;

public class HiloCliente implements Runnable {
    private BufferedReader br;
    private JTextArea ta;

    public HiloCliente(BufferedReader br, JTextArea ta) {
        this.br = br;
        this.ta = ta;
    }

    @Override
    public void run() {
        String mensaje;
        try {
            while((mensaje = br.readLine())!=null) {
                ta.append(mensaje + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al leer mensajes del servidor");
        }
    }
}
