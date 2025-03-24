package gui;

import javax.swing.*;

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

        JLabel helloWorld = new JLabel("Hello World!");
        painelPrincipal.add(helloWorld);
        telaPrincipal.add(painelPrincipal);
        telaPrincipal.setVisible(true);
    }
}
