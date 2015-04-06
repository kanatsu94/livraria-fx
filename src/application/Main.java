package application;

import io.Exportador;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import br.com.casadocodigo.livraria.produtos.Produto;
import dao.ProdutoDAO;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		// LISTA DE ITENS QUE SERA USADA PARA PREENCHER A TABELA
		ObservableList<Produto> produtos = new ProdutoDAO().lista();

		// API STREAM DO JAVA 8, PERMITE SOMAR TODOS OS VALORES EM UMA COLECAO
		// COM O METODO SUM():
		double valorToral = produtos.stream().mapToDouble(Produto::getValor)
				.sum();

		Group group = new Group();
		Scene scene = new Scene(group, 690, 510);

		Button button = new Button("Exportar CSV");
		TableView tableView = new TableView(produtos);
		final VBox vbox = new VBox(tableView);

		// LABEL
		Label label = new Label("Listagem de Livros");
		Label progresso = new Label();
		Label labelFooter = new Label(String.format(
				"Você tem R$%.2f em estoque, com um total de %d produtos.",
				valorToral, produtos.size()));

		// SET ID
		progresso.setId("label-progresso");
		vbox.setId("vbox");
		label.setId("label-listagem");
		labelFooter.setId("label-footer");

		scene.getStylesheets().add(
				getClass().getResource("application.css").toExternalForm());

		// ADICIONA UMA ACAO AO CLICAR NO BOTAO, UTILIZANDO UMA EXPRESSAO
		// LAMBDA.
		button.setOnAction(event -> {

			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					exportaEmCSV(produtos);
					return null;
				};
			};

			task.setOnRunning(e -> progresso.setText("exportando..."));
			task.setOnSucceeded(e -> progresso.setText("concluído!"));

			new Thread(task).start();
		});

		// DECLARANDO COLUNAS
		TableColumn<Produto, String> nomeColumn = criaColuna("Nome", 180, "nome");
		TableColumn<Produto, String> descColumn = criaColuna("Descrição", 230, "descricao");
		TableColumn<Produto, String> valorColumn = criaColuna("Valor", 60, "valor");
		TableColumn<Produto, String> isbnColumn = criaColuna("ISBN", 180, "isbn");

		tableView.getColumns().addAll(nomeColumn, descColumn, valorColumn,
				isbnColumn);

		group.getChildren().addAll(label, vbox, button, progresso, labelFooter);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Sistema da livraria com Java FX");
		primaryStage.show();
	}

	private TableColumn<Produto, String> criaColuna(String nome, int width, String atributo) {
		TableColumn<Produto, String> nomeColumn = new TableColumn<Produto, String>(nome);
		nomeColumn.setMinWidth(width);
		nomeColumn.setCellValueFactory(new PropertyValueFactory<Produto, String>(atributo));
		return nomeColumn;
	}

	private void exportaEmCSV(ObservableList<Produto> produtos) {
		try {
			new Exportador().paraCSV(produtos);
		} catch (IOException e) {
			System.out.println("Erro ao exportar: " + e);
		}
	}

	public static void main(String[] args) {

		launch(args);
	}
}
