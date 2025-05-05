package gui.admin;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import java.awt.event.*;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dbconnect.UserDao;
import gui.Login;
import gui.MainMenu;
import classes.Usuario;

public class formUsuarios {
    private JFrame mainFrame;
    private JTable tabelaUsuarios;
    private DefaultTableModel tableModel;
    private JTextField txtBuscar;

    public formUsuarios() {
        mainFrame = new JFrame("Gerenciamento dos usuários");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new MainMenu(Login.userType);
            }
        });

        JPanel mainPainel = new JPanel(new BorderLayout(10, 10));
        mainPainel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel buscarLabel = new JLabel("Buscar por username:");
        txtBuscar = new JTextField(20);

        topPanel.add(buscarLabel);
        topPanel.add(txtBuscar);
        mainPainel.add(topPanel, BorderLayout.NORTH);

        String[] colunas = { "ID", "Username", "Nome", "Email", "Tipo de usuário" };
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaUsuarios = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        mainPainel.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdicionarUsuario = new JButton("Adicionar novo usuário");
        JButton btnAlterarDados = new JButton("Alterar dados");
        JButton btnAlterarSenha = new JButton("Alterar senha");
        JButton btnDeletarConta = new JButton("Deletar senha");
        JButton btnVoltar = new JButton("Voltar");
        btnPanel.add(btnAdicionarUsuario);
        btnPanel.add(btnAlterarDados);
        btnPanel.add(btnAlterarSenha);
        btnPanel.add(btnDeletarConta);
        btnPanel.add(btnVoltar);

        mainPainel.add(btnPanel, BorderLayout.SOUTH);

        btnAlterarDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int linhaSelecionada = tabelaUsuarios.getSelectedRow();
                if(linhaSelecionada == -1){
                    JOptionPane.showMessageDialog(null, "Selecione algum usuário para modificar os dados!");
                    return;
                }
                int idSelecionado = Integer.parseInt(tabelaUsuarios.getValueAt(linhaSelecionada, 0).toString());
                mudarDados(idSelecionado);
            }
        });

        btnAdicionarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                adicionarUsuario();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                buscarAoDigitar();
            }

            public void removeUpdate(DocumentEvent e) {
                buscarAoDigitar();
            }

            public void changedUpdate(DocumentEvent e) {
                buscarAoDigitar();
            }
        });

        carregarUsuarios();

        mainFrame.add(mainPainel);
        mainFrame.setVisible(true);
    }

    public void carregarUsuarios() {
        tableModel.setRowCount(0);
        List<Usuario> usuarios = new UserDao().listarTodosUsuarios();
        for (Usuario user : usuarios) {
            tableModel.addRow(new Object[] {
                    user.getId(),
                    user.getUsername(),
                    user.getNome(),
                    user.getEmail(),
                    converterTipoUser(user.getIdTipoUsuario())
            });
        }
    }

    public void buscarAoDigitar() {
        String usernameBusca = txtBuscar.getText().trim();
        tableModel.setRowCount(0);

        if (usernameBusca.isEmpty()) {
            carregarUsuarios();
            return;
        }

        List<Usuario> result = new UserDao().buscarPorUsernamePrefixo(usernameBusca);
        for (Usuario user : result) {
            tableModel.addRow(new Object[] {
                    user.getId(),
                    user.getUsername(),
                    user.getNome(),
                    user.getEmail(),
                    converterTipoUser(user.getIdTipoUsuario())
            });
        }
    }

    private String converterTipoUser(int tipoUser) {
        switch (tipoUser) {
            case 1:
                return "Admin";
            case 2:
                return "Bibliotecario";
            case 3:
                return "Cliente";
            default:
                return "Cliente";
        }
    }

    public void adicionarUsuario(){
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

        JPanel cpfPanel = new JPanel();
        cpfPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel cpfLabel = new JLabel("Nº do CPF:");
        cpfPanel.add(cpfLabel);

        JFormattedTextField cpfField = null;

        try {
            MaskFormatter formatoCpf = new MaskFormatter("###.###.###-##");
            formatoCpf.setPlaceholderCharacter('_');
            cpfField = new JFormattedTextField(formatoCpf);
            cpfField.setColumns(15);
            cpfPanel.add(cpfField);
        } catch (ParseException e) {
            System.out.println("Erro ao formatar Nº Celular | " + e);
            cpfField = new JFormattedTextField(15);
            cpfPanel.add(cpfField);
        }
        dadosPessoaisPanel.add(cpfPanel);

        cpfPanel.add(cpfField);
        dadosPessoaisPanel.add(cpfPanel);
        
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
        JFormattedTextField fcpfField = cpfField;
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
                String cep = fcepField.getText().trim();
                String cpf = fcpfField.getText().trim();
                
                if(username.isBlank() || nome.isEmpty() || email.isEmpty() || senha.isEmpty() || endereco.isEmpty() || numeroCel.trim().replace("-","").replace("(", "").replace(")", "").replace("_", "").isEmpty() || bairro.isEmpty() || cidade.isEmpty() || cep.trim().replace("-", "").isEmpty() || cpf.replace("-","").replace(".","").isEmpty()){
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos!");
                    return;
                }
                
                if(!cpf.matches("\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")){
                    JOptionPane.showMessageDialog(null, "CPF inválido! Insira um CPF válido.");
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

                Usuario novoUsuario = new Usuario(nome, email, cpf, numeroCel, username, senha, endereco, bairro, cidade, estado, cep, 3);
                if(account.createAccount(novoUsuario)){
                    JOptionPane.showMessageDialog(null, "Cadastro feito com sucesso!\nBem-vindo(a) " + nome);
                    new MainMenu(3);
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

    public void mudarDados(int idUser){
            JFrame mainTelaMudarDados = new JFrame();
            mainTelaMudarDados.setSize(550,550);
            mainTelaMudarDados.setResizable(false);
            mainTelaMudarDados.setLocationRelativeTo(null);
            mainTelaMudarDados.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainTelaMudarDados.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e){
                    mainTelaMudarDados.dispose();
                };
            });

            JPanel mainPanelMudarDados = new JPanel();
            mainPanelMudarDados.setLayout(new BoxLayout(mainPanelMudarDados, BoxLayout.Y_AXIS));
            mainPanelMudarDados.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

            JPanel dadosPessoaisPanel = new JPanel();
            dadosPessoaisPanel.setLayout(new BoxLayout(dadosPessoaisPanel, BoxLayout.Y_AXIS));
            dadosPessoaisPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Dados Pessoais", TitledBorder.LEFT, TitledBorder.TOP)); 

            JPanel nomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,5));
            JLabel nomeLabel = new JLabel("Nome completo:");
            JTextField nomeField = new JTextField(20);
            nomePanel.add(nomeLabel);
            nomePanel.add(nomeField);
            dadosPessoaisPanel.add(nomePanel);

            JPanel numeroCelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,5));
            JLabel numeroCelLabel = new JLabel("Nº celular:");
            JFormattedTextField numeroCelField = null;
            try {
                MaskFormatter formatoNumeroCel = new MaskFormatter("(##)#####-####");
                formatoNumeroCel.setPlaceholderCharacter('_');
                numeroCelField = new JFormattedTextField(formatoNumeroCel);
                numeroCelField.setColumns(15);
            } catch (ParseException e) {
                numeroCelField = new JFormattedTextField(15);
            }
            numeroCelPanel.add(numeroCelLabel);
            numeroCelPanel.add(numeroCelField);
            dadosPessoaisPanel.add(numeroCelPanel);

            JPanel cpfPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,5));
            JLabel cpfLabel = new JLabel("Nº do CPF:");
            JFormattedTextField cpfField = null;
            try {
                MaskFormatter formatoCpf = new MaskFormatter("###.###.###-##");
                formatoCpf.setPlaceholderCharacter('_');
                cpfField = new JFormattedTextField(formatoCpf);
                cpfField.setColumns(15);
            } catch (ParseException e) {
                cpfField = new JFormattedTextField(15);
            }
            cpfPanel.add(cpfLabel);
            cpfPanel.add(cpfField);
            dadosPessoaisPanel.add(cpfPanel);

            JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,5));
            JLabel emailLabel = new JLabel("E-mail:");
            JTextField emailField = new JTextField(20);
            emailPanel.add(emailLabel);
            emailPanel.add(emailField);
            dadosPessoaisPanel.add(emailPanel);

            JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,5));
            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField(20);
            usernamePanel.add(usernameLabel);
            usernamePanel.add(usernameField);
            dadosPessoaisPanel.add(usernamePanel);

            JPanel tipoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,5));
            JLabel tipoLabel = new JLabel("Tipo de usuário:");
            String[] tiposUsuario = {"Cliente", "Bibliotecário", "Admin"};
            JComboBox<String> tipoComboBox = new JComboBox<>(tiposUsuario);
            tipoPanel.add(tipoLabel);
            tipoPanel.add(tipoComboBox);
            dadosPessoaisPanel.add(tipoPanel);

            mainPanelMudarDados.add(dadosPessoaisPanel);
            mainPanelMudarDados.add(Box.createRigidArea(new Dimension(0,10)));

            JPanel enderecoPanel = new JPanel();
            enderecoPanel.setLayout(new BoxLayout(enderecoPanel, BoxLayout.Y_AXIS));
            enderecoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Endereço", TitledBorder.LEFT, TitledBorder.TOP));

            JPanel endPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel enderecoLabel = new JLabel("Endereço:");
            JTextField enderecoField = new JTextField(20);
            endPanel.add(enderecoLabel);
            endPanel.add(enderecoField);
            enderecoPanel.add(endPanel);

            JPanel bairroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel bairroLabel = new JLabel("Bairro:");
            JTextField bairroField = new JTextField(20);
            bairroPanel.add(bairroLabel);
            bairroPanel.add(bairroField);
            enderecoPanel.add(bairroPanel);

            JPanel cidadePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel cidadeLabel = new JLabel("Cidade:");
            JTextField cidadeField = new JTextField(20);
            cidadePanel.add(cidadeLabel);
            cidadePanel.add(cidadeField);
            enderecoPanel.add(cidadePanel);

            JPanel estadoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel estadoLabel = new JLabel("Estado:");
            String[] estados = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
            JComboBox<String> estadoField = new JComboBox<>(estados); 
            estadoPanel.add(estadoLabel);
            estadoPanel.add(estadoField);
            enderecoPanel.add(estadoPanel);

            JPanel cepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel cepLabel = new JLabel("CEP:");
            JFormattedTextField cepField = null;
            try {
                cepField = new JFormattedTextField(new MaskFormatter("#####-###"));
                cepField.setColumns(10);
            } catch (ParseException e) {
                cepField = new JFormattedTextField(20);
            }
            cepPanel.add(cepLabel);
            cepPanel.add(cepField);
            enderecoPanel.add(cepPanel);

            mainPanelMudarDados.add(enderecoPanel);

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton btnSalvar = new JButton("Salvar alterações");
            JButton btnCancelar = new JButton("Cancelar");
            btnPanel.add(btnSalvar);
            btnPanel.add(btnCancelar);
            mainPanelMudarDados.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanelMudarDados.add(btnPanel);

            btnCancelar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    mainTelaMudarDados.dispose();
                }
            });

            Usuario user = new UserDao().getUserById(idUser);

            nomeField.setText(user.getNome());
            numeroCelField.setText(user.getNum_cel()); 
            cpfField.setText(user.getCpf());
            emailField.setText(user.getEmail());
            usernameField.setText(user.getUsername());
            switch (user.getIdTipoUsuario()) {
                case 3: 
                    tipoComboBox.setSelectedItem("Cliente");
                    break;
                case 2: 
                    tipoComboBox.setSelectedItem("Bibliotecário");
                    break;
                case 1: 
                    tipoComboBox.setSelectedItem("Admin");
                    break;
                default:
                    tipoComboBox.setSelectedItem("Cliente");
                    break;
            }
            enderecoField.setText(user.getEndereco());
            bairroField.setText(user.getBairro());
            cidadeField.setText(user.getCidade());
            estadoField.setSelectedItem(user.getEstado());
            cepField.setText(user.getCep());

            JFormattedTextField fcepField = cepField;
            JFormattedTextField fnumeroCelField = numeroCelField;
            JFormattedTextField fcpfField = cpfField;
            btnSalvar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    String nome = nomeField.getText().trim();
                    String numCel = fnumeroCelField.getText();
                    String cpf = fcpfField.getText();
                    String email = emailField.getText().trim();
                    String username = usernameField.getText().trim();
                    String tipoSelecionado = tipoComboBox.getSelectedItem().toString();
                    int tipoInt = switch (tipoSelecionado) {
                        case "Cliente" -> 3;
                        case "Bibliotecário" -> 2;
                        case "Admin" -> 1;
                        default -> 3;
                    };
                    String endereco = enderecoField.getText().trim();
                    String bairro = bairroField.getText().trim();
                    String cidade = cidadeField.getText().trim();
                    String estado = estadoField.getSelectedItem().toString();
                    String cep = fcepField.getText();
                    
                    if(nome.isEmpty() || numCel.replace("(","").replace(")","").replace("-","").isEmpty() || cpf.replace(".","").replace("-","").isEmpty() || email.isEmpty() || username.isEmpty() || endereco.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty() || cep.replace("-","").isEmpty()){
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos ou cancele as alterações!");
                        return;
                    }

                    Usuario attUser = new Usuario(nome, email, cpf, numCel, username, endereco, bairro, cidade, estado, cep, tipoInt);
                    Usuario oldUser = new Usuario(user.getNome(), user.getEmail(), user.getCpf(), user.getNum_cel(), user.getUsername(), user.getEndereco(), user.getBairro(), user.getCidade(), user.getEstado(), user.getCep(), user.getIdTipoUsuario());

                    if(attUser.getEmail().equals(oldUser.getEmail()) && attUser.getUsername().equals(oldUser.getUsername()) && attUser.getNome().equals(oldUser.getNome()) &&attUser.getCpf().equals(oldUser.getCpf()) && attUser.getNum_cel().equals(oldUser.getNum_cel()) && attUser.getEndereco().equals(oldUser.getEndereco()) && attUser.getBairro().equals(oldUser.getBairro()) && attUser.getCidade().equals(oldUser.getCidade()) && attUser.getEstado().equals(oldUser.getEstado()) && attUser.getCep().equals(oldUser.getCep())){
                        JOptionPane.showMessageDialog(null, "Os dados são idênticos! Altere-os ou cancele as mudanças.");
                        return;
                    }

                    if(JOptionPane.showConfirmDialog(null, "Deseja mesmo realizar as alterações?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                        return;
                    }

                    if(!new UserDao().changeData(attUser, idUser)){
                        JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar dados! Tente novamente.");
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
                    mainTelaMudarDados.dispose();
                }
            });

            mainTelaMudarDados.add(mainPanelMudarDados);
            mainTelaMudarDados.setVisible(true);
        }

    public void mudarSenha() {
        JFrame mainTelaMudarSenha = new JFrame();
        mainTelaMudarSenha.setSize(400, 200);
        mainTelaMudarSenha.setResizable(false);
        mainTelaMudarSenha.setLocationRelativeTo(null);
        mainTelaMudarSenha.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainTelaMudarSenha.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new MainMenu(Login.userType);
            };
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel senhaPanel = new JPanel();
        senhaPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel senhaLabel = new JLabel("Senha atual:");
        senhaPanel.add(senhaLabel);
        JPasswordField senhaField = new JPasswordField(20);
        senhaPanel.add(senhaField);

        JPanel senhaNovaPanel = new JPanel();
        senhaNovaPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel senhaNovaLabel = new JLabel("Nova senha:");
        senhaNovaPanel.add(senhaNovaLabel);
        JPasswordField senhaNovaField = new JPasswordField(20);
        senhaNovaPanel.add(senhaNovaField);

        mainPanel.add(senhaPanel);
        mainPanel.add(senhaNovaPanel);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton btnConfirmar = new JButton("Confirmar mudança");
        JButton btnCancelar = new JButton("Cancelar");
        btnPanel.add(btnConfirmar);
        btnPanel.add(btnCancelar);

        mainPanel.add(btnPanel);

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario user = new UserDao().getUser(Login.userName);
                String password = new String(senhaField.getPassword());
                String newPassword = new String(senhaNovaField.getPassword());

                if (!password.equals(user.getPassword())) {
                    JOptionPane.showMessageDialog(null,
                            "A sua senha atual não coincide! Tente colocar sua senha correta.");
                    return;
                }

                if (newPassword.equals(password)) {
                    JOptionPane.showMessageDialog(null, "As senhas são idênticas! Tente outra senha.");
                    return;
                }

                if (JOptionPane.showConfirmDialog(null, "Deseja mesmo alterar senha?", "Confirmação",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return;
                }

                if (new UserDao().changePassword(Login.userName, password, newPassword)) {
                    JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!");
                    mainTelaMudarSenha.dispose();
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Falha ao tentar alterar senha! Tente novamente.");
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainTelaMudarSenha.dispose();
            }
        });

        mainTelaMudarSenha.add(mainPanel);
        mainTelaMudarSenha.setVisible(true);
    }
}
