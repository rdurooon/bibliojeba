package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import classes.Usuario;
import dbconnect.UserDao;

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
                    loginField.requestFocus();
                    return;
                }

                UserDao userEntry = new UserDao();
                
                if(!userEntry.joinService(login, senha)){
                    JOptionPane.showMessageDialog(null, "Login e/ou senha incorretos!", "Tentativa de login", 0);
                    errorMsgLogin.setText("Você já está cadastrado?");
                    return;
                }

                if(userEntry.getUserType(login) == 1 || userEntry.getUserType(login) == 2){
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

        loginField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    passwordField.requestFocus();
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    loginBtn.requestFocus();
                }
            }
        });

        mainFrame.add(cadFrame);
        mainFrame.setVisible(true);
    }

    public static void Cadastrar(){
        JFrame cadFrame = new JFrame("Tela de cadastro");
        cadFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cadFrame.setSize(550, 550);
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

        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel userNameLabel = new JLabel("Nome de usuário: (para seu login)");
        userNamePanel.add(userNameLabel);
        JTextField userNameField = new JTextField(20);
        userNamePanel.add(userNameField);
        dadosPessoaisPanel.add(userNamePanel);

        JPanel nomePanel = new JPanel();
        nomePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel nomeLabel = new JLabel("Nome completo:");
        nomePanel.add(nomeLabel);
        JTextField nomeField = new JTextField(20);
        nomePanel.add(nomeField);
        dadosPessoaisPanel.add(nomePanel);

        JPanel numeroCelPanel = new JPanel();
        numeroCelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel numeroCelLabel = new JLabel("Nº celular:");
        numeroCelPanel.add(numeroCelLabel);

        JFormattedTextField numeroCelField = null;

        try {
            MaskFormatter formatoNumeroCel = new MaskFormatter("(##)#####-####");
            formatoNumeroCel.setPlaceholderCharacter('_');
            numeroCelField = new JFormattedTextField(formatoNumeroCel);
            numeroCelField.setColumns(15);
            numeroCelPanel.add(numeroCelField);
        } catch (ParseException e) {
            System.out.println("Erro ao formatar Nº Celular | " + e);
            numeroCelField = new JFormattedTextField(15);
            numeroCelPanel.add(numeroCelField);
        }
        dadosPessoaisPanel.add(numeroCelPanel);

        numeroCelPanel.add(numeroCelField);
        dadosPessoaisPanel.add(numeroCelPanel);
        
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

        JPanel enderecoPanel = new JPanel();
        enderecoPanel.setLayout(new BoxLayout(enderecoPanel, BoxLayout.Y_AXIS));
        enderecoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Dados de Endereço", 
            TitledBorder.LEFT, TitledBorder.TOP));

        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel enderecoLabel = new JLabel("Endereço:");
        addressPanel.add(enderecoLabel);
        JTextField enderecoField = new JTextField(20);
        addressPanel.add(enderecoField);
        enderecoPanel.add(addressPanel);


        JPanel bairroPanel = new JPanel();
        bairroPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel bairroLabel = new JLabel("Bairro:");
        bairroPanel.add(bairroLabel);
        JTextField bairroField = new JTextField(20);
        bairroPanel.add(bairroField);
        enderecoPanel.add(bairroPanel);

        JPanel cidadePanel = new JPanel();
        cidadePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel cidadeLabel = new JLabel("Cidade:");
        cidadePanel.add(cidadeLabel);
        JTextField cidadeField = new JTextField(20);
        cidadePanel.add(cidadeField);
        enderecoPanel.add(cidadePanel);

        JPanel estadoPanel = new JPanel();
        estadoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel estadoLabel = new JLabel("Estado:");
        estadoPanel.add(estadoLabel);
        String[] estados = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
        JComboBox<String> estadoField = new JComboBox<>(estados); 
        estadoPanel.add(estadoField);
        enderecoPanel.add(estadoPanel);

        JPanel cepPanel = new JPanel();
        cepPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel cepLabel = new JLabel("CEP:");
        cepPanel.add(cepLabel);
        JFormattedTextField cepField = null;

        try {
            cepField = new JFormattedTextField(new MaskFormatter("#####-###"));
            cepField.setColumns(10);
            cepPanel.add(cepField);
        } catch (ParseException e) {
            System.out.println("Erro ao formatar CEP | " + e);
            cepField = new JFormattedTextField(20);
            cepPanel.add(cepField);
        }
        enderecoPanel.add(cepPanel);

        cadPanel.add(enderecoPanel);
        cadPanel.add(Box.createRigidArea(new Dimension(0,10)));

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cadBtn = new JButton("Cadastrar");
        btnPanel.add(cadBtn);
        cadPanel.add(btnPanel);

        JFormattedTextField fcepField = cepField;
        JFormattedTextField fnumeroCelField = numeroCelField;
        cadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                UserDao account = new UserDao();
                String username = userNameField.getText().trim();
                String nome = nomeField.getText().trim();
                String email = emailField.getText();
                String senha = new String(senhaField.getPassword()).trim();
                String endereco = enderecoField.getText().trim();
                String numeroCel = fnumeroCelField.getText().trim();
                String bairro = bairroField.getText().trim();
                String cidade = cidadeField.getText().trim();
                String estado = estadoField.getSelectedItem().toString();
                String cep = fcepField.getText();
                
                if(username.isBlank() || nome.isEmpty() || email.isEmpty() || senha.isEmpty() || endereco.isEmpty() || numeroCel.trim().replace("-","").replace("(", "").replace(")", "").replace("_", "").isEmpty() || bairro.isEmpty() || cidade.isEmpty() || cep.trim().replace("-", "").isEmpty()){
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos!");
                    return;
                }
                
                if(numeroCel.length() < 11 || numeroCel.length() > 15 || !numeroCel.matches("\\(\\d{2}\\)\\d{5}-\\d{4}")){
                    JOptionPane.showMessageDialog(null, "N° de celular invalido! Insira outro número.");
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

                Usuario novoUsuario = new Usuario(0, nome, username, email, senha, numeroCel, endereco, bairro, cidade, estado, cep, 3);
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

        cadPanel.add(Box.createVerticalGlue());

        cadFrame.add(cadPanel);
        cadFrame.setVisible(true);
    }
}
