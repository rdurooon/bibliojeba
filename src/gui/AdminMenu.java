package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import gui.admin.formLivro;

public class AdminMenu {
    public AdminMenu(){
        JFrame telaPrincipal = new JFrame("Tela principal (ADMIN)");
        telaPrincipal.setSize(400,400);
        telaPrincipal.setResizable(false);
        telaPrincipal.setLocationRelativeTo(null);
        telaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnLivro = new JButton("Livros");
        painelPrincipal.add(btnLivro);

        btnLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new formLivro();
            }
        });

        telaPrincipal.add(painelPrincipal);
        telaPrincipal.setVisible(true);
    }
}
