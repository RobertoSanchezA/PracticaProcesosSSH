package com.company;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Session session = null;
        ChannelExec channel = null;
        Scanner scan = new Scanner(System.in);

            System.out.println("Introduce los siguientes datos:");
            System.out.println("Nombre de usuario");
            String username = scan.nextLine();

            System.out.println("Contraseña");
            String password = scan.nextLine();

            System.out.println("HOST");
            String host = scan.nextLine();

            System.out.println("Puerto");
            int port = scan.nextInt();

            boolean end = false;

            while (!end) {
                System.out.println("Nombre del archivo de registro a buscar: ");
                String logFile = scan.next();

                try {
                    System.out.println("Configurando session...");
                    session = new JSch().getSession(username, host, port);
                    session.setPassword(password);
                    session.setConfig("StrictHostKeyChecking", "no");

                    System.out.println("Conectando");
                    session.connect();

                    channel = (ChannelExec) session.openChannel("exec");

                    channel.setCommand("find /var/log " + logFile);

                    ByteArrayOutputStream response = new ByteArrayOutputStream();
                    channel.setOutputStream(response);

                    channel.connect();

                    while (channel.isConnected()) {
                        Thread.sleep(100);
                    }

                    String responseString = response.toString();

                    System.out.println(responseString);

                    System.out.println("¿Buscar otro fichero? S/N");
                    String repeat = scan.nextLine();
                    if (repeat.equals("n") || (repeat.equals("N"))) {
                        end = true;
                    }
                } catch (JSchException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (session != null){
                        session.disconnect();
                    }
                    if(channel != null) {
                        channel.disconnect();
                    }
                }
            }
    }
}
