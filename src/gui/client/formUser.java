package gui.client;

import javax.swing.*;

import gui.ClientMenu;

import java.awt.*;
import java.awt.event.*;

public class formUser {
    public void mudarDados(){
        JFrame mainTelaMudarDados = new JFrame();
        mainTelaMudarDados.setSize(400,200);
        mainTelaMudarDados.setResizable(false);
        mainTelaMudarDados.setLocationRelativeTo(null);
        mainTelaMudarDados.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainTelaMudarDados.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
                new ClientMenu();
            };
        });

        mainTelaMudarDados.setVisible(true);
    }

    public void mudarSenha(){
        JFrame mainTelaMudarSenha = new JFrame();
        mainTelaMudarSenha.setSize(400,200);
        mainTelaMudarSenha.setResizable(false);
        mainTelaMudarSenha.setLocationRelativeTo(null);
        mainTelaMudarSenha.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainTelaMudarSenha.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
                new ClientMenu();
            };
        });

        mainTelaMudarSenha.setVisible(true);
    }

    public void deletarConta(){
        JFrame mainTelaDeletarConta = new JFrame();
        mainTelaDeletarConta.setSize(400,200);
        mainTelaDeletarConta.setResizable(false);
        mainTelaDeletarConta.setLocationRelativeTo(null);
        mainTelaDeletarConta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainTelaDeletarConta.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
                new ClientMenu();
            };
        });

        mainTelaDeletarConta.setVisible(true);
    }
}
