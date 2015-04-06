package io;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import br.com.casadocodigo.livraria.produtos.Produto;

public class Exportador {
	public void paraCSV(List<Produto> produtos) throws IOException {
		PrintStream ps = new PrintStream("produtos.csv");
		ps.println("Nome, Descricao, Valor, ISBN");

		for (Produto p : produtos) {
			ps.println(String.format("%s, %s, %s, %s",
					p.getNome(),
					p.getDescricao(),
					p.getValor(),
					p.getIsbn()));
		}

		ps.close();
	}
}