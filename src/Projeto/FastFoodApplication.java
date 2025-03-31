
package Projeto;

import Projeto.dao.CardapioDAO;
import Projeto.dao.UsuarioDAO;
import Projeto.dao.impl.CardapioDAOImpl;
import Projeto.dao.impl.UsuarioDAOImpl;
import Projeto.exception.ProdutoNaoEncontradoException;
import Projeto.model.Bebida;
import Projeto.model.ItemPedido;
import Projeto.model.Lanche;
import Projeto.model.Produto;
import Projeto.model.Usuario;
import java.io.PrintStream;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FastFoodApplication extends Application {
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    private Usuario usuarioLogado;
    private Stage primaryStage;
    private ObservableList<Produto> observableCardapio = FXCollections.observableArrayList();
    private TableView<Produto> tabelaProdutos;
    private ObservableList<ItemPedido> carrinho = FXCollections.observableArrayList();

    public FastFoodApplication() {
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("FastFood Manager");
        this.mostrarTelaLogin();
    }

    private void mostrarTelaLogin() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap((double)10.0F);
        grid.setVgap((double)10.0F);
        grid.setPadding(new Insets((double)25.0F, (double)25.0F, (double)25.0F, (double)25.0F));
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 0);
        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 0);
        Label senhaLabel = new Label("Senha:");
        grid.add(senhaLabel, 0, 1);
        PasswordField senhaPasswordField = new PasswordField();
        grid.add(senhaPasswordField, 1, 1);
        Button loginButton = new Button("Login");
        Button cadastrarButton = new Button("Cadastrar");
        grid.add(loginButton, 0, 2);
        grid.add(cadastrarButton, 1, 2);
        Label mensagemLabel = new Label("");
        grid.add(mensagemLabel, 0, 3, 2, 1);
        loginButton.setOnAction((e) -> {
            String email = emailTextField.getText();
            String senha = senhaPasswordField.getText();

            try {
                Usuario usuario = this.usuarioDAO.buscarUsuarioPorEmail(email);
                if (usuario != null && usuario.getSenha().equals(senha)) {
                    this.usuarioLogado = usuario;
                    mensagemLabel.setText("Login bem-sucedido!");
                    this.mostrarTelaPrincipal();
                } else {
                    mensagemLabel.setText("Email ou senha incorretos.");
                }
            } catch (SQLException ex) {
                mensagemLabel.setText("Erro ao conectar com o banco.");
                ex.printStackTrace();
            }

        });
        cadastrarButton.setOnAction((e) -> this.mostrarTelaCadastro());
        Scene scene = new Scene(grid, (double)300.0F, (double)275.0F);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private void mostrarTelaCadastro() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap((double)10.0F);
        grid.setVgap((double)10.0F);
        grid.setPadding(new Insets((double)25.0F, (double)25.0F, (double)25.0F, (double)25.0F));
        Label nomeLabel = new Label("Nome:");
        grid.add(nomeLabel, 0, 0);
        TextField nomeTextField = new TextField();
        grid.add(nomeTextField, 1, 0);
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 1);
        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 1);
        Label senhaLabel = new Label("Senha:");
        grid.add(senhaLabel, 0, 2);
        PasswordField senhaPasswordField = new PasswordField();
        grid.add(senhaPasswordField, 1, 2);
        Button cadastrarButton = new Button("Cadastrar");
        Button voltarButton = new Button("Voltar");
        grid.add(cadastrarButton, 0, 3);
        grid.add(voltarButton, 1, 3);
        Label mensagemLabel = new Label("");
        grid.add(mensagemLabel, 0, 4, 2, 1);
        cadastrarButton.setOnAction((e) -> {
            String nome = nomeTextField.getText();
            String email = emailTextField.getText();
            String senha = senhaPasswordField.getText();

            try {
                if (!this.usuarioDAO.emailJaCadastrado(email)) {
                    Usuario novoUsuario = new Usuario(nome, email, senha);
                    if (this.usuarioDAO.cadastrarUsuario(novoUsuario)) {
                        mensagemLabel.setText("Cadastro realizado com sucesso!");
                        this.mostrarTelaLogin();
                    } else {
                        mensagemLabel.setText("Erro ao realizar o cadastro.");
                    }
                } else {
                    mensagemLabel.setText("Este email já está cadastrado.");
                }
            } catch (SQLException ex) {
                mensagemLabel.setText("Erro ao conectar com o banco.");
                ex.printStackTrace();
            }

        });
        voltarButton.setOnAction((e) -> this.mostrarTelaLogin());
        Scene scene = new Scene(grid, (double)300.0F, (double)350.0F);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private void mostrarTelaPrincipal() {
        Label boasVindasLabel = new Label("Bem-vindo, " + this.usuarioLogado.getNome() + "!");
        this.inicializarTabela();
        this.carregarDadosDoBanco();
        VBox root = new VBox((double)10.0F, new Node[]{boasVindasLabel, this.tabelaProdutos, this.criarMenuInferior()});
        root.setPadding(new Insets((double)10.0F));
        Scene scene = new Scene(root, (double)600.0F, (double)400.0F);
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("FastFood Manager - Principal");
        this.primaryStage.show();
    }

    private void inicializarTabela() {
        this.tabelaProdutos = new TableView(this.observableCardapio);
        TableColumn<Produto, Boolean> selecionarCol = new TableColumn("Selecionar");
        selecionarCol.setMinWidth((double)100.0F);
        selecionarCol.setCellValueFactory((param) -> Bindings.createBooleanBinding(() -> false, new Observable[0]));
        selecionarCol.setCellFactory((col) -> new TableCell<Produto, Boolean>() {
            private final CheckBox checkBox = new CheckBox();

            {
                this.checkBox.setOnAction((event) -> {
                    Produto produto = (Produto)this.getTableView().getItems().get(this.getIndex());
                    if (this.checkBox.isSelected()) {
                        FastFoodApplication.this.adicionarAoCarrinho(produto);
                    } else {
                        FastFoodApplication.this.removerDoCarrinho(produto);
                    }

                });
            }

            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    this.setGraphic((Node)null);
                } else {
                    this.setGraphic(this.checkBox);
                }

            }
        });
        TableColumn<Produto, String> nomeCol = new TableColumn("Nome");
        nomeCol.setCellValueFactory(new PropertyValueFactory("nome"));
        TableColumn<Produto, Double> precoCol = new TableColumn("Preço");
        precoCol.setCellValueFactory(new PropertyValueFactory("preco"));
        TableColumn<Produto, Boolean> veganoCol = new TableColumn("Vegano");
        veganoCol.setCellValueFactory((data) -> {
            Produto produto = (Produto)data.getValue();
            return produto instanceof Lanche ? ((Lanche)produto).veganoProperty() : new SimpleBooleanProperty(false);
        });
        TableColumn<Produto, Boolean> alcoolicaCol = new TableColumn("Alcoólica");
        alcoolicaCol.setCellValueFactory((data) -> {
            Produto produto = (Produto)data.getValue();
            return produto instanceof Bebida ? ((Bebida)produto).alcoolicaProperty() : new SimpleBooleanProperty(false);
        });
        this.tabelaProdutos.getColumns().addAll(new TableColumn[]{selecionarCol, nomeCol, precoCol, veganoCol, alcoolicaCol});
    }

    private void carregarDadosDoBanco() {
        CardapioDAO cardapioDAO = new CardapioDAOImpl();

        try {
            this.observableCardapio.clear();
            this.observableCardapio.addAll(cardapioDAO.listar());
            System.out.println("\n--- Dados carregados do banco para a ObservableList ---");

            for(Produto produto : this.observableCardapio) {
                System.out.println(produto);
            }

            System.out.println("-------------------------------------------------------\n");
        } catch (SQLException e) {
            System.err.println("Erro ao carregar dados do banco: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Tamanho da observableCardapio: " + this.observableCardapio.size());
    }

    private void removerProdutoSelecionado() {
        Produto produtoSelecionado = (Produto)this.tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            Alert alertConfirmacao = new Alert(AlertType.CONFIRMATION);
            alertConfirmacao.setTitle("Confirmar Remoção");
            alertConfirmacao.setHeaderText("Remover Produto");
            alertConfirmacao.setContentText("Tem certeza que deseja remover o produto: " + produtoSelecionado.getNome() + "?");
            alertConfirmacao.showAndWait().ifPresent((response) -> {
                if (response == ButtonType.OK) {
                    CardapioDAO cardapioDAO = new CardapioDAOImpl();

                    try {
                        cardapioDAO.remover(produtoSelecionado.getId());
                        this.observableCardapio.remove(produtoSelecionado);
                        Alert simpleAlert = new Alert(AlertType.INFORMATION);
                        simpleAlert.setTitle("Remoção");
                        simpleAlert.setContentText("Removido!");
                        simpleAlert.showAndWait();
                    } catch (Exception dbEx) {
                        System.err.println("Erro ao remover produto do banco de dados: " + dbEx.getMessage());
                        dbEx.printStackTrace();
                        this.mostrarAlerta("Erro ao Remover", "Ocorreu um erro ao tentar remover o produto: " + dbEx.getMessage());
                    }
                }

            });
        } else {
            this.mostrarAlerta("Nenhum Produto Selecionado", "Por favor, selecione um produto na tabela para remover.");
        }

    }

    private HBox criarMenuInferior() {
        HBox menuInferior = new HBox((double)10.0F);
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");
        TextField precoField = new TextField();
        precoField.setPromptText("Preço");
        ComboBox<String> tipoCombo = new ComboBox(FXCollections.observableArrayList(new String[]{"Lanche", "Bebida", "Outro"}));
        tipoCombo.setPromptText("Tipo");
        CheckBox veganoCheck = new CheckBox("Vegano");
        CheckBox alcoolicaCheck = new CheckBox("Alcoólica");
        Button adicionarButton = new Button("Adicionar");
        Button buscarButton = new Button("Buscar Nome");
        TextField buscarNomeField = new TextField();
        buscarNomeField.setPromptText("Buscar Nome");
        Button irParaCarrinhoButton = new Button("Ir para o Carrinho");
        irParaCarrinhoButton.setOnAction((e) -> this.mostrarTelaCarrinho());
        CardapioDAO cardapioDAO = new CardapioDAOImpl();
        Button removerButton = new Button("Remover");
        removerButton.setOnAction((e) -> this.removerProdutoSelecionado());
        adicionarButton.setOnAction((e) -> {
            String nome = nomeField.getText();
            String precoTexto = precoField.getText();
            String tipoSelecionado = (String)tipoCombo.getValue();
            boolean isVegano = veganoCheck.isSelected();
            boolean isAlcoolica = alcoolicaCheck.isSelected();
            if (nome != null && !nome.isEmpty() && tipoSelecionado != null && !tipoSelecionado.isEmpty() && precoTexto != null && !precoTexto.isEmpty()) {
                try {
                    double preco = Double.parseDouble(precoTexto);
                    Produto novoProduto = null;
                    switch (tipoSelecionado) {
                        case "Lanche":
                            novoProduto = new Lanche(nome, preco, isVegano);
                            novoProduto.setTipo(tipoSelecionado);
                            break;
                        case "Bebida":
                            novoProduto = new Bebida(nome, preco, isAlcoolica);
                            novoProduto.setTipo(tipoSelecionado);
                            break;
                        case "Outro":
                            novoProduto = new Produto(nome, preco);
                            novoProduto.setTipo(tipoSelecionado);
                    }

                    if (novoProduto != null) {
                        System.out.println("Objeto novo Produto criado: " + String.valueOf(novoProduto));

                        try {
                            cardapioDAO.adicionar(novoProduto);
                            this.mostrarAlerta("Sucesso", "Produto '" + nome + "' adicionado ao cardápio.");
                            this.carregarDadosDoBanco();
                            nomeField.clear();
                            precoField.clear();
                            tipoCombo.setValue(null);
                            veganoCheck.setSelected(false);
                            alcoolicaCheck.setSelected(false);
                        } catch (SQLException ex) {
                            this.mostrarAlerta("Erro ao Adicionar", "Ocorreu um erro ao adicionar o produto: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                } catch (NumberFormatException var19) {
                    this.mostrarAlerta("Erro", "Preço inválido. Digite um número.");
                }
            } else {
                this.mostrarAlerta("Erro", "Por favor, preencha nome, preço e tipo.");
            }

        });
        buscarButton.setOnAction((e) -> {
            String nomeBusca = buscarNomeField.getText();
            if (nomeBusca != null && !nomeBusca.isEmpty()) {
                try {
                    Produto produtoEncontrado = cardapioDAO.buscar(nomeBusca);
                    this.observableCardapio.clear();
                    this.observableCardapio.add(produtoEncontrado);
                } catch (ProdutoNaoEncontradoException ex) {
                    this.mostrarAlerta("Produto Não Encontrado", ex.getMessage());
                    this.carregarDadosDoBanco();
                } catch (SQLException ex) {
                    this.mostrarAlerta("Erro ao Buscar", "Ocorreu um erro ao buscar o produto: " + ex.getMessage());
                    ex.printStackTrace();
                    this.carregarDadosDoBanco();
                }
            } else {
                this.carregarDadosDoBanco();
            }

        });
        menuInferior.getChildren().addAll(new Node[]{nomeField, precoField, tipoCombo, veganoCheck, alcoolicaCheck, adicionarButton, buscarNomeField, buscarButton, removerButton, irParaCarrinhoButton});
        return menuInferior;
    }

    private void mostrarTelaCarrinho() {
        Stage carrinhoStage = new Stage();
        carrinhoStage.setTitle("Carrinho de Compras");
        TableView<ItemPedido> tabelaCarrinho = new TableView(this.carrinho);
        Label valorTotalLabel = new Label("Valor Total: R$ 0.00");
        TableColumn<ItemPedido, String> nomeCol = new TableColumn("Nome");
        nomeCol.setCellValueFactory((cellData) -> Bindings.select(((ItemPedido)cellData.getValue()).getProduto(), new String[]{"nome"}));
        TableColumn<ItemPedido, Double> precoUnitarioCol = new TableColumn("Preço Unitário");
        precoUnitarioCol.setCellValueFactory((cellData) -> ((ItemPedido)cellData.getValue()).precoUnitarioProperty().asObject());
        TableColumn<ItemPedido, Integer> quantidadeCol = new TableColumn("Quantidade");
        quantidadeCol.setCellValueFactory((cellData) -> ((ItemPedido)cellData.getValue()).quantidadeProperty().asObject());
        TableColumn<ItemPedido, Double> precoTotalItemCol = new TableColumn("Preço Total do Item");
        precoTotalItemCol.setCellValueFactory((cellData) -> ((ItemPedido)cellData.getValue()).precoUnitarioProperty().multiply(((ItemPedido)cellData.getValue()).quantidadeProperty()).asObject());
        TableColumn<ItemPedido, ItemPedido> acoesCol = new TableColumn("Ações");
        acoesCol.setCellFactory((param) -> new TableCell<ItemPedido, ItemPedido>() {
            final Button aumentarButton = new Button("+");
            final Button diminuirButton = new Button("-");
            final HBox buttonsBox;

            {
                this.buttonsBox = new HBox((double)5.0F, new Node[]{this.diminuirButton, this.aumentarButton});
                this.aumentarButton.setOnAction((event) -> {
                    ItemPedido item = (ItemPedido)this.getTableView().getItems().get(this.getIndex());
                    item.setQuantidade(item.getQuantidade() + 1);
                    FastFoodApplication.this.atualizarValorTotalCarrinho(valorTotalLabel);
                    tabelaCarrinho.refresh();
                });
                this.diminuirButton.setOnAction((event) -> {
                    ItemPedido item = (ItemPedido)this.getTableView().getItems().get(this.getIndex());
                    if (item.getQuantidade() > 1) {
                        item.setQuantidade(item.getQuantidade() - 1);
                        FastFoodApplication.this.atualizarValorTotalCarrinho(valorTotalLabel);
                        tabelaCarrinho.refresh();
                    } else {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Remover Item");
                        alert.setHeaderText((String)null);
                        alert.setContentText("Deseja remover " + item.getProduto().getNome() + " do carrinho?");
                        alert.showAndWait().ifPresent((response) -> {
                            if (response == ButtonType.OK) {
                                FastFoodApplication.this.carrinho.remove(item);
                                FastFoodApplication.this.atualizarValorTotalCarrinho(valorTotalLabel);
                                tabelaCarrinho.refresh();
                            }

                        });
                    }

                });
                this.buttonsBox.setAlignment(Pos.CENTER);
            }

            protected void updateItem(ItemPedido item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    this.setGraphic((Node)null);
                } else {
                    this.setGraphic(this.buttonsBox);
                }

            }
        });
        tabelaCarrinho.getColumns().addAll(new TableColumn[]{nomeCol, precoUnitarioCol, quantidadeCol, precoTotalItemCol, acoesCol});
        valorTotalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        this.atualizarValorTotalCarrinho(valorTotalLabel);
        Button adicionarMaisButton = new Button("Adicionar Mais Produtos");
        adicionarMaisButton.setOnAction((e) -> carrinhoStage.close());
        Button cancelarCompraButton = new Button("Cancelar Compra");
        cancelarCompraButton.setOnAction((e) -> {
            this.carrinho.clear();
            this.atualizarValorTotalCarrinho(valorTotalLabel);
            this.mostrarAlerta("Compra Cancelada", "Sua compra foi cancelada.");
            carrinhoStage.close();
        });
        Button finalizarCompraButton = new Button("Finalizar Compra");
        finalizarCompraButton.setOnAction((e) -> this.finalizarCompra());
        HBox botoesBox = new HBox((double)10.0F, new Node[]{adicionarMaisButton, cancelarCompraButton, finalizarCompraButton});
        botoesBox.setAlignment(Pos.CENTER);
        VBox layout = new VBox((double)10.0F, new Node[]{tabelaCarrinho, valorTotalLabel, botoesBox});
        layout.setPadding(new Insets((double)10.0F));
        Scene scene = new Scene(layout, (double)700.0F, (double)400.0F);
        carrinhoStage.setScene(scene);
        carrinhoStage.showAndWait();
    }

    private void adicionarAoCarrinho(Produto produto) {
        boolean encontrado = false;

        for(ItemPedido item : this.carrinho) {
            if (item.getProdutoId() == produto.getId()) {
                item.setQuantidade(item.getQuantidade() + 1);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            ItemPedido novoItem = new ItemPedido(produto.getId(), 1, produto.getPreco());
            novoItem.setProduto(produto);
            this.carrinho.add(novoItem);
        }

        this.atualizarValorTotalCarrinho();
        PrintStream var10000 = System.out;
        String var10001 = produto.getNome();
        var10000.println("Projeto.model.Produto adicionado ao carrinho: " + var10001 + ", Carrinho: " + String.valueOf(this.carrinho));
    }

    private void removerDoCarrinho(Produto produto) {
        this.carrinho.removeIf((item) -> item.getProdutoId() == produto.getId());
        this.atualizarValorTotalCarrinho();
        PrintStream var10000 = System.out;
        String var10001 = produto.getNome();
        var10000.println("Projeto.model.Produto removido do carrinho: " + var10001 + ", Carrinho: " + String.valueOf(this.carrinho));
    }

    private void atualizarValorTotalCarrinho() {
        double total = (double)0.0F;

        for(ItemPedido item : this.carrinho) {
            total += (double)item.getQuantidade() * item.getPrecoUnitario();
        }

        PrintStream var10000 = System.out;
        Object[] var10002 = new Object[]{total};
        var10000.println("Valor Total do Carrinho: R$ " + String.format("%.2f", var10002));
    }

    private void atualizarValorTotalCarrinho(Label label) {
        double total = (double)0.0F;

        for(ItemPedido item : this.carrinho) {
            total += (double)item.getQuantidade() * item.getPrecoUnitario();
        }

        String valorFormatado = String.format("%.2f", total);
        label.setText("Valor Total: R$ " + valorFormatado);
    }

    private void finalizarCompra() {
        if (this.carrinho.isEmpty()) {
            this.mostrarAlerta("Carrinho Vazio", "Seu carrinho está vazio. Adicione produtos para finalizar a compra.");
        } else {
            StringBuilder relatorio = new StringBuilder("--- Relatório de Compra ---\n\n");
            double valorTotal = (double)0.0F;

            for(ItemPedido item : this.carrinho) {
                relatorio.append(item.getQuantidade()).append(" x ").append(item.getProduto().getNome()).append(" = R$ ").append(String.format("%.2f", (double)item.getQuantidade() * item.getPrecoUnitario())).append("\n");
                valorTotal += (double)item.getQuantidade() * item.getPrecoUnitario();
            }

            relatorio.append("\nValor Total da Compra: R$ ").append(String.format("%.2f", valorTotal));
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Compra Finalizada");
            alert.setHeaderText((String)null);
            alert.setContentText(relatorio.toString());
            alert.showAndWait();
            this.carrinho.clear();
            this.tabelaProdutos.refresh();
        }
    }

    public void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText((String)null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
