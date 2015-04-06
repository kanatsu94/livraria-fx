package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import br.com.casadocodigo.livraria.Autor;
import br.com.casadocodigo.livraria.produtos.LivroFisico;
import br.com.casadocodigo.livraria.produtos.Produto;
import factory.ConnectionFactory;

public class ProdutoDAO {
	public ObservableList<Produto> lista() {
		ObservableList<Produto> produtos = FXCollections.observableArrayList();
		PreparedStatement ps;
		try (Connection conn = new ConnectionFactory().getConnection()){
			ps = conn.prepareStatement("SELECT * FROM produtos");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				LivroFisico livro = new LivroFisico(new Autor());

				livro.setNome(resultSet.getString("nome"));
				livro.setDescricao(resultSet.getString("descricao"));
				livro.setValor(resultSet.getDouble("valor"));
				livro.setIsbn(resultSet.getString("isbn"));

				produtos.add(livro);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return produtos;
	}

	public void adiciona(Produto p) {
		PreparedStatement ps;
		try (Connection conn = new ConnectionFactory().getConnection()) {
			ps = conn.prepareStatement("INSERT INTO produtos"
					+ "(nome, descricao, valor, isbn) VALUES (?,?,?,?)");
			ps.setString(1, p.getNome());
			ps.setString(2, p.getDescricao());
			ps.setDouble(3, p.getValor());
			ps.setString(4, p.getIsbn());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
