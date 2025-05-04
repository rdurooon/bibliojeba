package gui;

import javax.swing.*;

import dbconnect.EmprestimoDao;
import gui.admin.formLivro;
import gui.client.formEmprestimo;
import gui.client.formUser;

import java.awt.*;
import java.awt.event.*;

public class MainMenu {
    public MainMenu(int userType){
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
        btnPanel.add(livroPanel);

        if(userType == 3){
            JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            userPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Usuário"));
            JButton btnMudarDados = new JButton("Mudar dados da conta");
            JButton btnMudarSenha = new JButton("Mudar senha");
            JButton btnDeletarConta = new JButton("Deletar conta");
            userPanel.add(btnMudarDados);
            userPanel.add(btnMudarSenha);
            userPanel.add(btnDeletarConta);
            btnPanel.add(userPanel);

            btnMudarDados.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    new formUser().mudarDados();
                    telaPrincipal.dispose();
                }
            });
    
            btnMudarSenha.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    new formUser().mudarSenha();
                    telaPrincipal.dispose();
                }
            });
    
            btnDeletarConta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    new formUser().deletarConta();
                    telaPrincipal.dispose();
                }
            });
        }

        if(userType == 1 || userType == 2){
            JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            adminPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), "Administração"));
            JButton btnLivro = new JButton("Livros");
            adminPanel.add(btnLivro);
            btnPanel.add(adminPanel);

            btnLivro.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    telaPrincipal.dispose();
                    new formLivro();
                }
            });


        }

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
                if(Login.userType == 3){
                    EmprestimoDao emprestimoDao = new EmprestimoDao();
                    if(emprestimoDao.buscarLivrosEmprestados(Login.userId).isEmpty()){
                        JOptionPane.showMessageDialog(null, "Você não possui nenhum livro emprestado :)");
                        return;
                    }
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
