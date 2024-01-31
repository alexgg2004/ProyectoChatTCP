package Cliente;

import Hilos.HiloCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Cliente extends JFrame {
    private String nombreUsuario;
    private JPanel panel1;
    private JTextArea textArea1;
    private JTextField textField1;
    private JButton button1;
    private PrintWriter pw;
    private BufferedReader br;

    public static void main(String[] args) throws IOException {
        Cliente cliente = new Cliente();
        cliente.crearInterfazInicio();
        cliente.start("localhost", 6001, cliente);
    }

    private void crearInterfazInicio() {
        boolean entradaValida = false;
        while (!entradaValida) {
            nombreUsuario = JOptionPane.showInputDialog("Introduce tu nombre de usuario:");

            if (nombreUsuario == null) {
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres salir del programa?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            } else {
                entradaValida = true;
            }

            if(nombreUsuario.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debes ingresar un nombre de usuario. Inténtalo de nuevo.");
                nombreUsuario = JOptionPane.showInputDialog("Introduce tu nombre de usuario:");
            } else {
                entradaValida = true;
            }
        }
    }

    private void start(String host, int port, Cliente cliente) throws IOException {
        System.out.println("Conectado con " + host + ": " + port);

        Socket socket = new Socket(host, port);
        pw = new PrintWriter(socket.getOutputStream(), true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        crearInterfazChat(cliente);

        HiloCliente h = new HiloCliente(br, textArea1);
        Thread t = new Thread(h);
        t.start();
    }

    private void crearInterfazChat(Cliente cliente) {
        cliente.setContentPane(cliente.panel1);
        cliente.setTitle("Chat");
        cliente.setSize(875, 675);
        cliente.setVisible(true);
        cliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button1 = new JButton("Enviar");
    }

    public Cliente() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println(nombreUsuario + ": " + textField1.getText());
                textField1.setText("");
            }
        });
    }
}
