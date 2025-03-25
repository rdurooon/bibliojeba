package gui.admin;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import classes.Autor;
import classes.Editora;
import classes.Livro;
import dbconnect.BookDao;

import java.awt.*;
import java.awt.event.*;

public class formLivro {
    public formLivro(){
        JFrame mainFrame = new JFrame("Livros");
        mainFrame.setSize(400,400);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
    
        JPanel mainPainel = new JPanel();
        mainPainel.setLayout(new BoxLayout(mainPainel, BoxLayout.Y_AXIS));
        mainPainel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnAdicionar = new JButton("Adicionar livro");
        mainPainel.add(btnAdicionar);

        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                adicionarLivro();
            }
        });

        mainFrame.add(mainPainel);
        mainFrame.setVisible(true);
    }

    public static void adicionarLivro(){
        JFrame mainFrame = new JFrame("Adicionar livro");
        mainFrame.setSize(400,400);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        JPanel mainPainel = new JPanel();
        mainPainel.setLayout(new BoxLayout(mainPainel, BoxLayout.Y_AXIS));
        mainPainel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel loginTxt = new JLabel("ADICIONAR LIVRO");
        loginTxt.setFont(new Font("Arial", Font.BOLD, 18));
        loginTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPainel.add(loginTxt);
        mainPainel.add(Box.createRigidArea(new Dimension(0,10)));

        JPanel addLivroPanel = new JPanel();
        addLivroPanel.setLayout(new BoxLayout(addLivroPanel, BoxLayout.Y_AXIS));
        addLivroPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Add livro", TitledBorder.LEFT, TitledBorder.TOP)); 

        JPanel tituloPanel = new JPanel();
        tituloPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel tituloLabel = new JLabel("Título do livro:");
        tituloPanel.add(tituloLabel);
        JTextField tituloField = new JTextField(26);
        tituloPanel.add(tituloField);
        addLivroPanel.add(tituloPanel);

        JPanel autorPanel = new JPanel();
        autorPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel autorLabel = new JLabel("Autor do livro:");
        autorPanel.add(autorLabel);
        JTextField autorField = new JTextField(26);
        autorPanel.add(autorField);
        addLivroPanel.add(autorPanel);

        JPanel editoraPanel = new JPanel();
        editoraPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel editoraLabel = new JLabel("Editora do livro:");
        editoraPanel.add(editoraLabel);
        JTextField editoraField = new JTextField(26);
        editoraPanel.add(editoraField);
        addLivroPanel.add(editoraPanel);

        JPanel adicionarPanel = new JPanel();
        adicionarPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));
        JButton btnAdicionar = new JButton("Adicionar");
        adicionarPanel.add(btnAdicionar);
        addLivroPanel.add(adicionarPanel);

        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String titulo = tituloField.getText().trim();
                String autor = autorField.getText().trim();
                String editora = editoraField.getText().trim();

                if(titulo.isEmpty() || autor.isEmpty() || editora.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return;
                }

                
                Livro livro = new Livro(titulo, new Autor(autor), new Editora(editora));
                if(new BookDao().existBook(titulo)){
                    Livro livroExistente = new BookDao().selectBook(titulo);
                    if(livro.getAutor().getNome().equals(livroExistente.getAutor().getNome()) && livro.getEditora().getNome().equals(livroExistente.getEditora().getNome())){
                        JOptionPane.showMessageDialog(null, "Esse livro já está cadastrado!");
                        return;

                    }
                }

                if(!new BookDao().insertBook(livro)){
                    JOptionPane.showMessageDialog(null, "Erro ao adicionar livro! Tente novamente.");
                    return;
                }

                JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");
            }
        });

        mainPainel.add(addLivroPanel);
        mainFrame.add(mainPainel);
        mainFrame.setVisible(true);
    }
}
