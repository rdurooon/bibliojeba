package gui.client;
import gui.MainMenu;
import gui.Login;
import classes.Livro;
import dbconnect.BookDao;
import dbconnect.EmprestimoDao;
import dbconnect.UserDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class formEmprestimo {
    private static JTable tabelaLivros;
    private static DefaultTableModel modeloTabela;
    
    public static void fazerEmprestimo(){
        JFrame telaFazerEmprestimo = new JFrame("Fazer empréstimo");
        telaFazerEmprestimo.setSize(400,400);
        telaFazerEmprestimo.setResizable(false);
        telaFazerEmprestimo.setLocationRelativeTo(null);
        telaFazerEmprestimo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        telaFazerEmprestimo.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                new MainMenu(Login.userType);
            }
        });

        JPanel mainPainelEmprestimo = new JPanel(new BorderLayout(10, 10));
        mainPainelEmprestimo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabela = new DefaultTableModel(new String[] { "Título", "Gênero", "Autor", "Editora", "Selecionar" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex){
                return columnIndex == 4 ? Boolean.class : String.class;
            }
        };

        tabelaLivros = new JTable(modeloTabela);
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaLivros);
        mainPainelEmprestimo.add(scrollPane, BorderLayout.CENTER);

        carregarLivros();

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnEmprestimo = new JButton("Realizar empréstimo");
        btnPanel.add(btnEmprestimo);
        JButton btnVoltar = new JButton("Voltar");
        btnPanel.add(btnVoltar);
        mainPainelEmprestimo.add(btnPanel, BorderLayout.SOUTH);

        telaFazerEmprestimo.add(mainPainelEmprestimo);
        telaFazerEmprestimo.setVisible(true);

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                telaFazerEmprestimo.dispose();
                new MainMenu(Login.userType);
            }
        });

        if(Login.userType == 3){

            btnEmprestimo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){ 
                    int idUsuario = Login.userId;
                    EmprestimoDao emprestimoDao = new EmprestimoDao();
                    int emprestimos = emprestimoDao.emprestimosUserCount(idUsuario);
                    if(emprestimos >= 5){
                        JOptionPane.showMessageDialog(null, "Você já atingiu o limite de 5 livros emprestados!");
                        return;
                    }
                    
                    List<Livro> livrosSelecionados = new ArrayList<>();
                    for(int i = 0; i < modeloTabela.getRowCount(); i++){
                        boolean selecionado = (boolean) modeloTabela.getValueAt(i, 4);
                        if(selecionado){
                            String titulo = (String) modeloTabela.getValueAt(i, 0);
                            Livro livro = new BookDao().selectBook(titulo);
                            livrosSelecionados.add(livro);
                        }
                    }
                    
                    if(livrosSelecionados == null || livrosSelecionados.size() < 1){
                        JOptionPane.showMessageDialog(null, "Selecione pelo menos um livro!");
                        return;
                    }
                    
                    if(livrosSelecionados.size() + emprestimos > 5){
                        JOptionPane.showMessageDialog(null, "Você só pode pegar no máximo " + (5 - emprestimos) + " livros!");
                        return;
                    }
    
                    if(emprestimoDao.fazerEmprestimo(livrosSelecionados, idUsuario)){
                        JOptionPane.showMessageDialog(null, "Emprestimo(s) realizado com sucesso!");
                        carregarLivros();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao realizar empréstimo.");
                    }
                }
            });
        }
        
        if(Login.userType == 1 || Login.userType == 2){
            btnEmprestimo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    JDialog caixaDialogo = new JDialog((Frame) null, "Escolher destinatário", true);
                    caixaDialogo.setLayout(new GridLayout(4,3));
                    caixaDialogo.setSize(300,175);
                    caixaDialogo.setLocationRelativeTo(null);

                    JPanel emprestimoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                    JLabel emprestimoTxt = new JLabel("Deseja fazer o emprestimo para quem?");
                    emprestimoPanel.add(emprestimoTxt);
                    
                    JPanel rbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                    JRadioButton rbParaMim = new JRadioButton("Para mim");
                    JRadioButton rbParaOutro = new JRadioButton("Para outra pessoa");
                    ButtonGroup btnGrupo = new ButtonGroup();
                    btnGrupo.add(rbParaMim);
                    btnGrupo.add(rbParaOutro);
                    rbPanel.add(rbParaMim);
                    rbPanel.add(rbParaOutro);
                    rbParaMim.setSelected(true);

                    JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JLabel usernameTxt = new JLabel("Username:");
                    JTextField usernameField = new JTextField(10);
                    usernameField.setEnabled(false);
                    usernameTxt.setForeground(Color.GRAY);
                    usernamePanel.add(usernameTxt);
                    usernamePanel.add(usernameField);

                    rbParaOutro.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e){
                            usernameTxt.setForeground(Color.BLACK);;
                            usernameField.setEnabled(true);
                        }
                    });
                    
                    rbParaMim.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e){
                            usernameTxt.setForeground(Color.GRAY);;
                            usernameField.setEnabled(false);
                        }
                    });

                    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JButton btnConfirmar = new JButton("Confirmar");
                    JButton btnCancelar = new JButton("Cancelar");
                    btnPanel.add(btnConfirmar);
                    btnPanel.add(btnCancelar);
                    
                    caixaDialogo.add(emprestimoPanel);
                    caixaDialogo.add(rbPanel);
                    caixaDialogo.add(usernamePanel);
                    caixaDialogo.add(btnPanel);

                    btnCancelar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e){
                            caixaDialogo.dispose();
                        }
                    });

                    btnConfirmar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e){
                            int idUser;

                            if(rbParaMim.isSelected()){
                                idUser = Login.userId;
                            } else {
                                String username = usernameField.getText().trim();
                                if(username.isEmpty()){
                                    JOptionPane.showMessageDialog(null, "Digite um username válido!");
                                    return;
                                }

                                idUser = new UserDao().getUserId(username);
                                if(idUser == -1){
                                    JOptionPane.showMessageDialog(null, "Usuário não encontrado! Tente novamente.");
                                    return;
                                }
                            }

                            EmprestimoDao emprestimoDao = new EmprestimoDao();
                            int emprestimos = emprestimoDao.emprestimosUserCount(idUser);
                            if(emprestimos >= 5){
                                JOptionPane.showMessageDialog(null, "Usuário já atingiu o limite de 5 livros emprestados!");
                                return;
                            }
                            
                            List<Livro> livrosSelecionados = new ArrayList<>();
                            for(int i = 0; i < modeloTabela.getRowCount(); i++){
                                boolean selecionado = (boolean) modeloTabela.getValueAt(i, 4);
                                if(selecionado){
                                    String titulo = (String) modeloTabela.getValueAt(i, 0);
                                    Livro livro = new BookDao().selectBook(titulo);
                                    livrosSelecionados.add(livro);
                                }
                            }

                            if(livrosSelecionados.isEmpty()){
                                JOptionPane.showMessageDialog(null, "Selecione pelo menos um livro!");
                                return;
                            }

                            if(!emprestimoDao.fazerEmprestimo(livrosSelecionados, idUser)){
                                JOptionPane.showMessageDialog(null, "Não foi possível realizar empréstimo! Tente novamente.");
                                return;
                            }

                            JOptionPane.showMessageDialog(null, "Emprestimo(s) realizado(s) com sucesso!");
                            carregarLivros();
                            caixaDialogo.dispose();
                        }
                    });

                    caixaDialogo.setVisible(true);
                }
            });
        }
    }
    
    public static void desfazerEmprestimo(){
        JFrame telaDesfazerEmprestimo = new JFrame("Devolver livro");
        telaDesfazerEmprestimo.setSize(400,400);
        telaDesfazerEmprestimo.setResizable(false);
        telaDesfazerEmprestimo.setLocationRelativeTo(null);
        telaDesfazerEmprestimo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        telaDesfazerEmprestimo.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                new MainMenu(Login.userType);
            }
        });

        JPanel mainPainelDevolucao = new JPanel(new BorderLayout(10,10));
        mainPainelDevolucao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultTableModel modeloDevolucao = new DefaultTableModel(new String[]{"Título","Selecionar"},0){
            @Override
            public boolean isCellEditable(int row, int column){
                return column == 1;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex){
                return columnIndex == 1 ? Boolean.class : String.class;
            }
        };

        JTable tabelaDevolucao = new JTable(modeloDevolucao);
        tabelaDevolucao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaDevolucao);
        mainPainelDevolucao.add(scrollPane, BorderLayout.CENTER);

        EmprestimoDao emprestimoDao = new EmprestimoDao();
        List<Livro> livrosEmprestimos = emprestimoDao.buscarLivrosEmprestados(Login.userId);
        for(Livro livro : livrosEmprestimos){
            modeloDevolucao.addRow(new Object[]{
                livro.getTitulo(),
                false
            });
        }

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnDevolver = new JButton("Devolver livro(s)");
        btnPanel.add(btnDevolver);
        JButton btnDevolverTodos = new JButton("Devolver tudo");
        btnPanel.add(btnDevolverTodos);
        JButton btnVoltar = new JButton("Voltar");
        btnPanel.add(btnVoltar);
        mainPainelDevolucao.add(btnPanel, BorderLayout.SOUTH);

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                telaDesfazerEmprestimo.dispose();
                new MainMenu(Login.userType);
            }
        });

        btnDevolverTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                List<Livro> livrosEmprestados = new ArrayList<>();
                for(int i = 0; i < modeloDevolucao.getRowCount(); i++){
                    String titulo = (String) modeloDevolucao.getValueAt(i, 0);
                    Livro livro = new BookDao().selectBook(titulo);
                    livrosEmprestados.add(livro);
                }

                if(emprestimoDao.devolverLivros(livrosEmprestados, Login.userId)){
                    JOptionPane.showMessageDialog(null, "Livro(s) devolvido(s) com sucesso!");
                    telaDesfazerEmprestimo.dispose();
                    new MainMenu(Login.userType);
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao tentar devolver todos os livros! Tente novamente.");
                    return;
                }
            }
        });

        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                List<Livro> livrosSelecionados = new ArrayList<>();
                for(int i = 0; i < modeloDevolucao.getRowCount(); i++){
                    boolean selecionado = (boolean) modeloDevolucao.getValueAt(i, 1);
                    if(selecionado){
                        String titulo = (String) modeloDevolucao.getValueAt(i, 0);
                        Livro livro = new BookDao().selectBook(titulo);
                        livrosSelecionados.add(livro);
                    } 
                }

                if(livrosSelecionados.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Selecione pelo menos um livro para ser devolvido!");
                    return;
                }

                if(emprestimoDao.devolverLivros(livrosSelecionados, Login.userId)){
                    JOptionPane.showMessageDialog(null, "Livro(s) devolvido(s) com sucesso!");

                    modeloDevolucao.setRowCount(0);

                    List<Livro> atualizarLivros = emprestimoDao.buscarLivrosEmprestados(Login.userId);
                    for(Livro livro : atualizarLivros){
                        modeloDevolucao.addRow(new Object[]{
                            livro.getTitulo(),
                            false
                        });
                    }

                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Falha ao tentar devolver livro(s)! Tente novamente.");
                    return;
                }
            }
        });

        telaDesfazerEmprestimo.add(mainPainelDevolucao);
        telaDesfazerEmprestimo.setVisible(true);
    }

    private static void carregarLivros() {
        modeloTabela.setRowCount(0);
        BookDao bookDao = new BookDao();
        List<Livro> livros = bookDao.selectAllAvailableBooks();
        for (Livro livro : livros) {
            modeloTabela.addRow(new Object[] {
                    livro.getTitulo(),
                    livro.getGenero().getNome(),
                    livro.getAutor().getNome(),
                    livro.getEditora().getNome(),
                    false
            });
        }
    }
}
