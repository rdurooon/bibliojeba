package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import classes.TipoUsuario;
import classes.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import dbconnect.Dao;

public class Login {
    static JFrame mainFrame = new JFrame("Tela de login");
    public Login(){
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(330,330);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        JPanel cadFrame = new JPanel();
        cadFrame.setLayout(new BoxLayout(cadFrame, BoxLayout.Y_AXIS));
        cadFrame.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel loginTxt = new JLabel("LOGIN");
        loginTxt.setFont(new Font("Arial", Font.BOLD, 18));
        loginTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        cadFrame.add(loginTxt);
        cadFrame.add(Box.createRigidArea(new Dimension(0,10)));
        
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel loginLabel = new JLabel("Nome de usuário:");
        loginPanel.add(loginLabel);
        JTextField loginField = new JTextField(20); 
        loginPanel.add(loginField);
        cadFrame.add(loginPanel);

        cadFrame.add(Box.createRigidArea(new Dimension(0,10)));
        
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel passwordLabel = new JLabel("Senha:");
        passwordPanel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(20);
        passwordPanel.add(passwordField);
        cadFrame.add(passwordPanel);
        
        cadFrame.add(Box.createRigidArea(new Dimension(0,10)));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton loginBtn = new JButton("Entrar");
        JButton registerBtn = new JButton("Cadastrar-se");

        btnPanel.add(loginBtn);
        btnPanel.add(registerBtn);
        cadFrame.add(btnPanel);
        
        JLabel errorMsgLogin = new JLabel("");
        errorMsgLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorMsgLogin.setForeground(Color.RED);
        cadFrame.add(errorMsgLogin);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String login = loginField.getText();
                String senha = new String(passwordField.getPassword());

                if(login.isBlank() || senha.isBlank()){
                    JOptionPane.showMessageDialog(null, "Insira seu nome de usuário e/ou senha!");
                    return;
                }

                Dao userEntry = new Dao();
                
                if(!userEntry.joinService(login, senha)){
                    JOptionPane.showMessageDialog(null, "Login e/ou senha incorretos!", "Tentativa de login", 0);
                    errorMsgLogin.setText("Você já está cadastrado?");
                    return;
                }

                String userType = userEntry.getUserType(login);

                if(userType.equals("Administrador") || userType.equals("Bibliotecario")){
                    new AdminMenu();
                } else {
                    new MainMenu(); 
                }

                mainFrame.dispose();
            }
        });

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Cadastrar();
            }
        });


        mainFrame.add(cadFrame);
        mainFrame.setVisible(true);
    }

    public static void Cadastrar(){
        JFrame cadFrame = new JFrame("Tela de cadastro");
        cadFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cadFrame.setSize(350, 400);
        cadFrame.setResizable(false);
        cadFrame.setLocationRelativeTo(null);

        JPanel cadPanel = new JPanel();
        cadPanel.setLayout(new BoxLayout(cadPanel, BoxLayout.Y_AXIS));
        cadPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel cadastroTxt = new JLabel("CADASTRO");
        cadastroTxt.setFont(new Font("Arial", Font.BOLD, 18));
        cadastroTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        cadPanel.add(cadastroTxt);
        cadPanel.add(Box.createRigidArea(new Dimension(0,10)));

        JPanel dadosPessoaisPanel = new JPanel();
        dadosPessoaisPanel.setLayout(new BoxLayout(dadosPessoaisPanel, BoxLayout.Y_AXIS));
        dadosPessoaisPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Dados Pessoais", TitledBorder.LEFT, TitledBorder.TOP)); 

        JPanel nomePanel = new JPanel();
        nomePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel nomeLabel = new JLabel("Nome de usuário:");
        nomePanel.add(nomeLabel);
        JTextField nomeField = new JTextField(20);
        nomePanel.add(nomeField);
        dadosPessoaisPanel.add(nomePanel);
        
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel emailLabel = new JLabel("Email:");
        emailPanel.add(emailLabel);
        JTextField emailField = new JTextField(20);
        emailPanel.add(emailField);
        dadosPessoaisPanel.add(emailPanel);
        
        JPanel senhaPanel = new JPanel();
        senhaPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel senhaLabel = new JLabel("Senha:");
        senhaPanel.add(senhaLabel);
        JPasswordField senhaField = new JPasswordField(20);
        senhaPanel.add(senhaField);
        dadosPessoaisPanel.add(senhaPanel);
        
        cadPanel.add(dadosPessoaisPanel);
        cadPanel.add(Box.createRigidArea(new Dimension(0,10)));

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cadBtn = new JButton("Cadastrar");
        btnPanel.add(cadBtn);
        cadPanel.add(btnPanel);

        cadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Dao account = new Dao();
                String email = emailField.getText();
                String nome = nomeField.getText().trim();
                String senha = new String(senhaField.getPassword()).trim();
                
                if(email.isEmpty() || nome.isEmpty() || senha.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos!");
                    return;
                }
                
                if(account.existAccount(email)){
                    JOptionPane.showMessageDialog(null, "Usuário já cadastrado neste email. \nTente logar ou utilizar outro", "Conta existente", 0);
                    return;
                }

                String emailRegex = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$";
                Pattern padrao = Pattern.compile(emailRegex);
                Matcher match = padrao.matcher(email);
                
                if(!match.matches()){
                    JOptionPane.showMessageDialog(null, "Email invalido! Digite um email válido.");
                    return;
                }

                Usuario novoUsuario = new Usuario(0,email,senha,nome,new TipoUsuario(3, "Cliente"));
                if(account.createAccount(novoUsuario)){
                    JOptionPane.showMessageDialog(null, "Cadastro feito com sucesso!\nBem-vindo(a) " + nome);
                    new MainMenu();
                    cadFrame.dispose();
                    mainFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar-se\nTente novamente.", "Erro", 0);
                }

            }
        });

        cadFrame.add(cadPanel);
        cadFrame.setVisible(true);
    }
}
