package com.company;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Session session = null;
        ChannelExec channel = null;
        Scanner scan = new Scanner(System.in);
        try{
            System.out.println("Introduce los siguientes datos:");
            System.out.println("Nombre de usuario");
            String username = scan.nextLine();

            System.out.println("Contraseña");
            String password = scan.nextLine();

            System.out.println("HOST");
            String host = scan.nextLine();

            System.out.println("Puerto");
            int port = scan.nextInt();

            session.setConfig("StrictHostKeyChecking", "no");


            System.out.println("Configurando session...");
            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            System.out.println("Conectando");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");


        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
