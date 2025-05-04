package gui.admin;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import dbconnect.UserDao;
import gui.Login;
import gui.MainMenu;
import classes.Usuario;

public class formUsuarios {
    private JFrame mainFrame;
    private JTable tabelaUsuarios;
    private DefaultTableModel tableModel;
    private JTextField txtBuscar;

    public formUsuarios(){
        mainFrame = new JFrame("Gerenciamento dos usuários");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        JPanel mainPainel = new JPanel(new BorderLayout(10, 10));
        mainPainel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel buscarLabel = new JLabel("Buscar por username:");
        txtBuscar = new JTextField(20);
        
        topPanel.add(buscarLabel);
        topPanel.add(txtBuscar);
        mainPainel.add(topPanel, BorderLayout.NORTH);

        String[] colunas = {"ID","Username","Nome","Email","Tipo de usuário"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaUsuarios = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        mainPainel.add(scrollPane, BorderLayout.CENTER);
        

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAlterarDados = new JButton("Alterar dados");
        JButton btnAlterarSenha = new JButton("Alterar senha");
        JButton btnDeletarConta = new JButton("Deletar senha");
        JButton btnVoltar = new JButton("Voltar");
        btnPanel.add(btnAlterarDados);
        btnPanel.add(btnAlterarSenha);
        btnPanel.add(btnDeletarConta);
        btnPanel.add(btnVoltar);

        mainPainel.add(btnPanel, BorderLayout.SOUTH);

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                mainFrame.dispose();
                new MainMenu(Login.userType);
            }
        });

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e){
                buscarAoDigitar();
            }
            public void removeUpdate(DocumentEvent e){
                buscarAoDigitar();
            }
            public void changedUpdate(DocumentEvent e){
                buscarAoDigitar();
            }
        });

        carregarUsuarios();

        mainFrame.add(mainPainel);
        mainFrame.setVisible(true);
    }

    public void carregarUsuarios(){
        tableModel.setRowCount(0);
        List<Usuario> usuarios = new UserDao().listarTodosUsuarios();
        for (Usuario user : usuarios){
            tableModel.addRow(new Object[]{
                user.getId(),
                user.getUsername(),
                user.getNome(),
                user.getEmail(),
                user.getIdTipoUsuario()
            });
        }
    }

    public void buscarAoDigitar(){
        String usernameBusca = txtBuscar.getText().trim();
        tableModel.setRowCount(0);

        if(usernameBusca.isEmpty()){
            carregarUsuarios();
            return;
        }

        List<Usuario> result = new UserDao().buscarPorUsernamePrefixo(usernameBusca);
        for(Usuario user : result){
            tableModel.addRow(new Object[]{
                user.getId(),
                user.getUsername(),
                user.getNome(),
                user.getEmail(),
                user.getIdTipoUsuario()
            });
        }
    }
}
