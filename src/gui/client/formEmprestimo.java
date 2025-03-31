package gui.client;
import gui.ClientMenu;
import gui.Login;
import classes.Emprestimo;
import classes.Livro;
import dbconnect.BookDao;
import dbconnect.EmprestimoDao;

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
                new ClientMenu();
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
        mainPainelEmprestimo.add(btnPanel, BorderLayout.SOUTH);

        telaFazerEmprestimo.add(mainPainelEmprestimo);
        telaFazerEmprestimo.setVisible(true);

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
    
    public static void desfazerEmprestimo(){
        JFrame telaDesfazerEmprestimo = new JFrame("Devolver livro");
        telaDesfazerEmprestimo.setSize(400,400);
        telaDesfazerEmprestimo.setResizable(false);
        telaDesfazerEmprestimo.setLocationRelativeTo(null);
        telaDesfazerEmprestimo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        telaDesfazerEmprestimo.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                new ClientMenu();
            }
        });
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
