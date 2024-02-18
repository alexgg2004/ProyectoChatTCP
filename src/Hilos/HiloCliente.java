package Hilos;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HiloCliente implements Runnable {
    private BufferedReader br;
    private JTextArea ta;
    JTextArea ta2;
    List<String> nombreUsuarios = new ArrayList<>();

    public HiloCliente(BufferedReader br, JTextArea ta, JTextArea ta2) {
        this.br = br;
        this.ta = ta;
        this.ta2 = ta2;
    }

    @Override
    public void run() {
        String mensaje;
        try {
            while((mensaje = br.readLine())!=null) {
                if(mensaje.startsWith("/nick")) {
                    String nombre = mensaje.substring(5);
                    nombreUsuarios.add(nombre);
                    ta2.setText("");
                    for(int i=0; i<nombreUsuarios.size(); i++) {
                        ta2.append(nombreUsuarios.get(i) + "\n");
                    }
                } else {
                    ta.append(mensaje + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer mensajes del servidor");
        }
    }
}
