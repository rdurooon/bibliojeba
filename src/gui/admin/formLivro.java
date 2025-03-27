package gui.admin;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import classes.Autor;
import classes.Editora;
import classes.Genero;
import classes.Livro;
import dbconnect.BookDao;

import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class formLivro {
    private JFrame mainFrame;
    private JTable tabelaLivros;
    private static DefaultTableModel modeloTabela;
    
        public formLivro(){
            mainFrame = new JFrame("Gerenciamento de Livros");
            mainFrame.setSize(600,400);
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrame.setResizable(false);
            mainFrame.setLocationRelativeTo(null);
        
            JPanel mainPainel = new JPanel(new BorderLayout(10,10));
            mainPainel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
            modeloTabela = new DefaultTableModel(new String[]{"Título","Gênero","Autor","Editora"}, 0);
            tabelaLivros = new JTable(modeloTabela);
            JScrollPane scrollPane = new JScrollPane(tabelaLivros);
            mainPainel.add(scrollPane, BorderLayout.CENTER);
    
            carregarLivros();
    
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
            JButton btnAdicionar = new JButton("Adicionar livro");
            JButton btnModificar = new JButton("Modificar livro");
            JButton btnRemover = new JButton("Remover livro");
            btnPanel.add(btnAdicionar);
            btnPanel.add(btnModificar);
            btnPanel.add(btnRemover);
    
            mainPainel.add(btnPanel, BorderLayout .SOUTH);
    
            btnAdicionar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    adicionarLivro();
                }
            });

            btnModificar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    modificarLivro();
                }
            });
    
            mainFrame.add(mainPainel);
            mainFrame.setVisible(true);
        }
    
        private void carregarLivros(){
                modeloTabela.setRowCount(0);
                List<Livro> livros = new BookDao().selectAllBooks();
                for(Livro livro: livros){
                    modeloTabela.addRow(new Object[]{
                    livro.getTitulo(),
                    livro.getGenero().getNome(),
                    livro.getAutor().getNome(),
                    livro.getEditora().getNome()
                });
            }
        }
    
        public void adicionarLivro(){
            JFrame mainFrameAddLivro = new JFrame("Adicionar livro");
            mainFrameAddLivro.setSize(400,400);
            mainFrameAddLivro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrameAddLivro.setResizable(false);
            mainFrameAddLivro.setLocationRelativeTo(null);
    
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

            JPanel generoPanel = new JPanel();
            generoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
            JLabel generoLabel = new JLabel("Gênero do livro:");
            generoPanel.add(generoLabel);
            JTextField generoField = new JTextField(26);
            generoPanel.add(generoField);
            addLivroPanel.add(generoPanel);
    
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
                    String genero = generoField.getText().trim();
                    String autor = autorField.getText().trim();
                    String editora = editoraField.getText().trim();
                    BookDao bookDao = new BookDao();
                    if(titulo.isEmpty() || genero.isEmpty() || autor.isEmpty() || editora.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                        return;
                    }
                    
                    Livro livro = new Livro(titulo, new Genero(genero), new Autor(autor), new Editora(editora));
                    if(bookDao.existBook(titulo)){
                        Livro livroExistente = bookDao.selectBook(titulo);
                        if(livro.getGenero().getNome().equals(livroExistente.getGenero().getNome()) && livro.getAutor().getNome().equals(livroExistente.getAutor().getNome()) && livro.getEditora().getNome().equals(livroExistente.getEditora().getNome())){
                            JOptionPane.showMessageDialog(null, "Esse livro já está cadastrado!");
                            return;
    
                        }
                    }
    
                    if(!bookDao.insertBook(livro)){
                        JOptionPane.showMessageDialog(null, "Erro ao adicionar livro! Tente novamente.");
                        return;
                    }
    
                    JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");
                    carregarLivros();
                }
            });
            
            mainPainel.add(addLivroPanel);
            mainFrameAddLivro.add(mainPainel);
            mainFrameAddLivro.setVisible(true);
        }
        
    static boolean isProcessingEntry = false;
    static String tituloOriginal = "";
    public void modificarLivro(){
        JFrame mainFrameModLivro = new JFrame("Alterar livro");
        mainFrameModLivro.setSize(400,400);
        mainFrameModLivro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrameModLivro.setResizable(false);
        mainFrameModLivro.setLocationRelativeTo(null);

        JPanel mainModPainel = new JPanel();
        mainModPainel.setLayout(new BoxLayout(mainModPainel, BoxLayout.Y_AXIS));
        mainModPainel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel loginTxt = new JLabel("ALTERAR LIVRO");
        loginTxt.setFont(new Font("Arial", Font.BOLD, 18));
        loginTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainModPainel.add(loginTxt);
        mainModPainel.add(Box.createRigidArea(new Dimension(0,10)));

        JPanel addLivroPanel = new JPanel();
        addLivroPanel.setLayout(new BoxLayout(addLivroPanel, BoxLayout.Y_AXIS));
        addLivroPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Alterar livro", TitledBorder.LEFT, TitledBorder.TOP)); 

        JPanel tituloPanel = new JPanel();
        tituloPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel tituloLabel = new JLabel("Título do livro:");
        tituloPanel.add(tituloLabel);
        JTextField tituloField = new JTextField(26);
        tituloPanel.add(tituloField);
        addLivroPanel.add(tituloPanel);

        JPanel generoPanel = new JPanel();
        generoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        JLabel generoLabel = new JLabel("Gênero do livro:");
        generoPanel.add(generoLabel);
        JTextField generoField = new JTextField(26);
        generoPanel.add(generoField);
        addLivroPanel.add(generoPanel);

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

        JPanel modificarPanel = new JPanel();
        modificarPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));
        JButton btnModificar = new JButton("Alterar");
        modificarPanel.add(btnModificar);
        addLivroPanel.add(modificarPanel);

        tituloField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    isProcessingEntry = true;
                    String titulo = tituloField.getText().trim();

                    if(titulo.isEmpty()){
                        generoField.setText("");
                        autorField.setText("");
                        editoraField.setText("");
                        tituloOriginal = "";
                        isProcessingEntry = false;
                        return;
                    }

                    Livro livro = new BookDao().selectBook(titulo);

                    if(livro == null){
                        JOptionPane.showMessageDialog(null, "Livro não encontrado!");
                        isProcessingEntry = false;
                        return;
                    }

                    tituloOriginal = livro.getTitulo();

                    generoField.setText(livro.getGenero().getNome());
                    autorField.setText(livro.getAutor().getNome());
                    editoraField.setText(livro.getEditora().getNome());
                    isProcessingEntry = false;
                }
            }
        });

        tituloField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e){
                if(isProcessingEntry) return;

                isProcessingEntry = true;
                String titulo = tituloField.getText().trim();

                if(titulo.isEmpty()){
                    generoField.setText("");
                    autorField.setText("");
                    editoraField.setText("");
                    tituloOriginal = "";
                    isProcessingEntry = false;
                    return;
                }

                Livro livro = new BookDao().selectBook(titulo);
                if(livro == null){
                    JOptionPane.showMessageDialog(null, "Livro não encontrado!");
                    isProcessingEntry = false;
                    return;
                }

                tituloOriginal = livro.getTitulo();

                generoField.setText(livro.getGenero().getNome());
                autorField.setText(livro.getAutor().getNome());
                editoraField.setText(livro.getEditora().getNome());
                isProcessingEntry = false;
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                isProcessingEntry = true;
                String titulo = tituloField.getText().trim();
                String genero = generoField.getText().trim();
                String autor = autorField.getText().trim();
                String editora = editoraField.getText().trim();
                BookDao bookDao = new BookDao();
                
                if(titulo.isEmpty() || genero.isEmpty() || autor.isEmpty() || editora.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    isProcessingEntry = false;
                    return;
                }
                
                Livro livro = new Livro(titulo, new Genero(genero), new Autor(autor), new Editora(editora));
                Livro livroSelecionado = bookDao.selectBook(tituloOriginal);

                if(livroSelecionado == null){
                    isProcessingEntry = false;
                    return;
                }
                
                if(livro.getTitulo().equals(livroSelecionado.getTitulo()) && livro.getGenero().getNome().equals(livroSelecionado.getGenero().getNome()) && livro.getAutor().getNome().equals(livroSelecionado.getAutor().getNome()) && livro.getEditora().getNome().equals(livroSelecionado.getEditora().getNome())){
                    JOptionPane.showMessageDialog(null, "Nenhuma alteração feita.");
                    isProcessingEntry = false;
                    return;
                }
                String[] confirmarAlteracao = {"Confirmar","Cancelar"};

                if(JOptionPane.showOptionDialog(null, "Deseja confirmar as alterações?", "Confirmar alteração", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, confirmarAlteracao, confirmarAlteracao[1]) == 1){
                    isProcessingEntry = false;
                    return;
                }

                livro = new Livro(livroSelecionado.getId(), titulo, new Genero(genero), new Autor(autor), new Editora(editora));

                if(!bookDao.alterBook(livro)){
                    JOptionPane.showMessageDialog(null, "Erro ao tentar alterar livro! Tente novamente.");
                    isProcessingEntry = false;
                    return; 
                }

                JOptionPane.showMessageDialog(null, "Livro alterado com sucesso!");
                tituloOriginal = titulo;
                carregarLivros();
                isProcessingEntry = false;
                }
            });

        mainModPainel.add(addLivroPanel);
        mainFrameModLivro.add(mainModPainel);
        mainFrameModLivro.setVisible(true);
        }
}
