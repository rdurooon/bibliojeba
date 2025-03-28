package gui;

import javax.swing.*;

import gui.client.formEmprestimo;

import java.awt.*;
import java.awt.event.*;

public class ClientMenu {
    public ClientMenu(){
        JFrame telaPrincipal = new JFrame("Tela principal");
        telaPrincipal.setSize(400,400);
        telaPrincipal.setResizable(false);
        telaPrincipal.setLocationRelativeTo(null);
        telaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10,10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnEmprestimo = new JButton("Fazer empréstimo");
        JButton btnDesemprestimo = new JButton("Devolver livro");
        btnPanel.add(btnEmprestimo);
        btnPanel.add(btnDesemprestimo);
        
        btnEmprestimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                formEmprestimo.fazerEmprestimo();
                telaPrincipal.dispose();
            }
        });
        
        btnDesemprestimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                formEmprestimo.desfazerEmprestimo();
                telaPrincipal.dispose();
            }
        });

        painelPrincipal.add(btnPanel);
        telaPrincipal.add(painelPrincipal);
        telaPrincipal.setVisible(true);
    }
}
