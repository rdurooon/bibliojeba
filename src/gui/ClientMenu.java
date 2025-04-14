package gui;

import javax.swing.*;
import javax.swing.border.Border;

import dbconnect.EmprestimoDao;
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
        
        JPanel logedUserPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel logedUserTxt = new JLabel("Logado como: " + Login.userName);
        logedUserPanel.add(logedUserTxt);
        painelPrincipal.add(logedUserPanel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(2,1,10,10));

        JPanel livroPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        livroPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Livros"));
        JButton btnEmprestimo = new JButton("Fazer empréstimo");
        JButton btnDesemprestimo = new JButton("Devolver livro");
        livroPanel.add(btnEmprestimo);
        livroPanel.add(btnDesemprestimo);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        userPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Usuário"));
        JButton btnMudarDados = new JButton("Mudar dados da conta");
        JButton btnMudarEndereco = new JButton("Mudar senha");
        JButton btnDeletarConta = new JButton("Deletar conta");
        userPanel.add(btnMudarDados);
        userPanel.add(btnMudarEndereco);
        userPanel.add(btnDeletarConta);
        
        btnPanel.add(livroPanel);
        btnPanel.add(userPanel);
        painelPrincipal.add(btnPanel, BorderLayout.CENTER);

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
                EmprestimoDao emprestimoDao = new EmprestimoDao();
                if(emprestimoDao.buscarLivrosEmprestados(Login.userId).isEmpty()){
                    JOptionPane.showMessageDialog(null, "Você não possui nenhum livro emprestado :)");
                    return;
                }

                formEmprestimo.desfazerEmprestimo();
                telaPrincipal.dispose();
            }
        });

        JPanel sairPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSair = new JButton("Sair");
        sairPanel.add(btnSair);
        painelPrincipal.add(btnSair, BorderLayout.SOUTH);

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(JOptionPane.showConfirmDialog(null, "Deseja sair?", "Confirmar saida", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    telaPrincipal.dispose();
                    Login.userId = 0;
                    Login.userName = "";
                    new Login();
                }
            }
        });

        telaPrincipal.add(painelPrincipal);
        telaPrincipal.setVisible(true);
    }
}
