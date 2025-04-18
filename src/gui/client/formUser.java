package gui.client;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

import classes.Livro;
import classes.Usuario;
import dbconnect.EmprestimoDao;
import dbconnect.UserDao;
import gui.MainMenu;
import gui.Login;

public class formUser {
    public void mudarDados(){
        JFrame mainTelaMudarDados = new JFrame();
        mainTelaMudarDados.setSize(550,550);
        mainTelaMudarDados.setResizable(false);
        mainTelaMudarDados.setLocationRelativeTo(null);
        mainTelaMudarDados.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainTelaMudarDados.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
                new MainMenu(Login.userType);
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

        Usuario user = new UserDao().getUser(Login.userName);

        nomeField.setText(user.getNome());
        numeroCelField.setText(user.getNum_cel()); 
        cpfField.setText(user.getCpf());
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
                String endereco = enderecoField.getText().trim();
                String bairro = bairroField.getText().trim();
                String cidade = cidadeField.getText().trim();
                String estado = estadoField.getSelectedItem().toString();
                String cep = fcepField.getText();
                
                if(nome.isEmpty() || numCel.replace("(","").replace(")","").replace("-","").isEmpty() || cpf.replace(".","").replace("-","").isEmpty() || endereco.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty() || cep.replace("-","").isEmpty()){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos ou cancele as alterações!");
                    return;
                }

                Usuario attUser = new Usuario(nome, cpf, numCel, endereco, bairro, cidade, estado, cep);
                Usuario oldUser = new Usuario(user.getNome(), user.getCpf(), user.getNum_cel(), user.getEndereco(), user.getBairro(), user.getCidade(), user.getEstado(), user.getCep());

                if(attUser.getNome().equals(oldUser.getNome()) &&attUser.getCpf().equals(oldUser.getCpf()) && attUser.getNum_cel().equals(oldUser.getNum_cel()) && attUser.getEndereco().equals(oldUser.getEndereco()) && attUser.getBairro().equals(oldUser.getBairro()) && attUser.getCidade().equals(oldUser.getCidade()) && attUser.getEstado().equals(oldUser.getEstado()) && attUser.getCep().equals(oldUser.getCep())){
                    JOptionPane.showMessageDialog(null, "Os dados são idênticos! Altere-os ou cancele as mudanças.");
                    return;
                }

                if(JOptionPane.showConfirmDialog(null, "Deseja mesmo realizar as alterações?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }

                if(!new UserDao().changeData(attUser)){
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

    public void mudarSenha(){
        JFrame mainTelaMudarSenha = new JFrame();
        mainTelaMudarSenha.setSize(400,200);
        mainTelaMudarSenha.setResizable(false);
        mainTelaMudarSenha.setLocationRelativeTo(null);
        mainTelaMudarSenha.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainTelaMudarSenha.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
                new MainMenu(Login.userType);
            };
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel senhaPanel = new JPanel();
        senhaPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel senhaLabel = new JLabel("Senha atual:");
        senhaPanel.add(senhaLabel);
        JPasswordField senhaField = new JPasswordField(20);
        senhaPanel.add(senhaField);

        JPanel senhaNovaPanel = new JPanel();
        senhaNovaPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
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
            public void actionPerformed(ActionEvent e){
                Usuario user = new UserDao().getUser(Login.userName);
                String password = new String(senhaField.getPassword());
                String newPassword = new String(senhaNovaField.getPassword());

                if(!password.equals(user.getPassword())){
                    JOptionPane.showMessageDialog(null, "A sua senha atual não coincide! Tente colocar sua senha correta.");
                    return;
                }

                if(newPassword.equals(password)){
                    JOptionPane.showMessageDialog(null, "As senhas são idênticas! Tente outra senha.");
                    return;
                }

                if(JOptionPane.showConfirmDialog(null, "Deseja mesmo alterar senha?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }

                if(new UserDao().changePassword(Login.userName, password, newPassword)){
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
            public void actionPerformed(ActionEvent e){
                mainTelaMudarSenha.dispose();
            }
        });

        mainTelaMudarSenha.add(mainPanel);
        mainTelaMudarSenha.setVisible(true);
    }

    public void deletarConta(){
        JFrame mainTelaDeletarConta = new JFrame();
        mainTelaDeletarConta.setSize(300,175);
        mainTelaDeletarConta.setResizable(false);
        mainTelaDeletarConta.setLocationRelativeTo(null);
        mainTelaDeletarConta.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel senhaPanel = new JPanel();
        senhaPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel senhaLabel = new JLabel("Insira sua senha:");
        senhaPanel.add(senhaLabel);
        JPasswordField senhaField = new JPasswordField(17);
        senhaPanel.add(senhaField);

        mainPanel.add(senhaPanel);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnCancelar = new JButton("Cancelar");
        btnPanel.add(btnConfirmar);
        btnPanel.add(btnCancelar);

        mainPanel.add(btnPanel);

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Usuario user = new UserDao().getUser(Login.userName);
                String password = new String(senhaField.getPassword());

                if(!user.getPassword().equals(password)){
                    JOptionPane.showMessageDialog(null, "Senha incorreta! Insira sua senha corretamente.");
                    return;
                }

                if(JOptionPane.showConfirmDialog(null, "Deseja mesmo deletar a conta?", "Confirmação de deletar conta", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }

                EmprestimoDao emprestimoDao = new EmprestimoDao();
                UserDao userDao = new UserDao();

                List<Livro> livrosDevolver = emprestimoDao.buscarLivrosEmprestados(Login.userId);
                emprestimoDao.devolverLivros(livrosDevolver, Login.userId);
                
                if(!userDao.deleteAccount(Login.userId)){
                    JOptionPane.showMessageDialog(null, "Falha ao deletar conta! Tente novamente.");
                    return;
                }

                JOptionPane.showMessageDialog(null, "Obrigado por ter sido nosso usuário\nEsperamo que tenha gostado de nosso sistema ;)");
                mainTelaDeletarConta.dispose();
                new Login();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                mainTelaDeletarConta.dispose();
                new MainMenu(Login.userType);
            }
        });

        mainTelaDeletarConta.add(mainPanel);
        mainTelaDeletarConta.setVisible(true);
    }
}
