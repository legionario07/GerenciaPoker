package br.com.gerenciapoker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Jogador;
import br.com.gerenciapoker.estatisticas.EstatisticaJogadorPontuacao;
import br.com.gerenciapoker.estatisticas.EstatisticaJogadorSaldo;

public class EstatisticasDao {

	private static Connection con = null;

	public List<EntidadeDominio> pesquisarPremiacao() {

		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select jogador.id, jogador.nome, sum(jogador_partida.entrada+jogador_partida.rebuy+jogador_partida.add_on) as entradas, ");
		sql.append("sum(premiacao.valor) as premiacao, ");
		sql.append("sum((premiacao.valor)-(jogador_partida.entrada+jogador_partida.rebuy+jogador_partida.add_on)) ");
		sql.append("as saldo from jogador_partida ");
		sql.append("inner join jogador on jogador.id = jogador_partida.jogador_id ");
		sql.append("inner join premiacao on premiacao.id = jogador_partida.premiacao_id ");
		sql.append("group by jogador.nome ");
		sql.append("order by saldo desc ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());

			ResultSet rSet = pstm.executeQuery();

			int i = 0;

			while (rSet.next()) {
				++i;

				EstatisticaJogadorSaldo ej = new EstatisticaJogadorSaldo();

				ej.setId(i);
				ej.getJogador().setId(rSet.getInt("jogador.id"));
				ej.setJogador((Jogador) new JogadorDao().find(ej.getJogador()));

				ej.setEntrada(rSet.getBigDecimal("entradas"));
				ej.setPremiacao(rSet.getBigDecimal("premiacao"));
				ej.setSaldo(rSet.getBigDecimal("saldo"));

				lista.add(ej);

			}

			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public EntidadeDominio pesquisarPontuacao(EntidadeDominio entidade) {

		if (!(entidade instanceof Jogador))
			return null;

		Jogador j = new Jogador();
		j = (Jogador) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("select partida.data, jogador.id as 'jogador_id', "
				+ "jogador.nome, colocacao.posicao, colocacao.pontos, partida.participacao from jogador_partida ");
		sql.append("inner join premiacao on premiacao.id = jogador_partida.premiacao_id ");
		sql.append("inner join colocacao on colocacao.id = premiacao.colocacao_id ");
		sql.append("inner join partida on partida.id = jogador_partida.partida_id ");
		sql.append("inner join jogador on jogador.id = jogador_partida.jogador_id ");
		sql.append("where jogador.id = ? ");
		sql.append("order by partida.data asc, colocacao.posicao asc;");

		EstatisticaJogadorPontuacao ep = new EstatisticaJogadorPontuacao();
		ep.setJogador(j);

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, j.getId());

			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {

				if (rSet.getString("posicao").equals("1") || rSet.getString("posicao").equals("2")
						|| rSet.getString("posicao").equals("3")) {
					ep.setPontosConquistados(
							ep.getPontosConquistados() + rSet.getInt("pontos") + rSet.getInt("participacao"));
				} else {
					ep.setPontosConquistados(ep.getPontosConquistados() + rSet.getInt("pontos"));
				}

			}

			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ep;
	}

	public Integer pesquisarPontosDeParticipacao(EntidadeDominio entidade) {

		if (!(entidade instanceof Jogador))
			return 0;

		Jogador j = new Jogador();
		j = (Jogador) entidade;

		Integer valor = null;

		StringBuffer sql = new StringBuffer();
		sql.append("select jogador.nome, count(jogador.id) as 'jogos' from jogador_partida ");
		sql.append("inner join jogador on jogador.id = jogador_partida.jogador_id ");
		sql.append("where jogador.id = ?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, j.getId());

			ResultSet rSet = pstm.executeQuery();

			if (rSet.next()) {
				valor = rSet.getInt("jogos");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return valor;
	}

	public Map<Integer, Integer> pesquisarPosicaoVsQuantidade(EntidadeDominio entidade) {

		if (!(entidade instanceof Jogador))
			return null;

		Jogador j = new Jogador();
		j = (Jogador) entidade;

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 0);
		map.put(2, 0);
		map.put(3, 0);
		map.put(4, 0);

		StringBuffer sql = new StringBuffer();
		sql.append("select  jogador.nome, colocacao.posicao, count(colocacao.posicao) as 'qtde' from jogador_partida ");
		sql.append("inner join premiacao on premiacao.id = jogador_partida.premiacao_id ");
		sql.append("inner join colocacao on colocacao.id = premiacao.colocacao_id ");
		sql.append("inner join jogador on jogador.id = jogador_partida.jogador_id ");
		sql.append("where jogador.id = ? and (colocacao.posicao = 1 or "
				+ "colocacao.posicao =2 or colocacao.posicao = 3 or colocacao.posicao = 4) ");
		sql.append("group by colocacao.posicao ");
		sql.append("order by colocacao.posicao asc; ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, j.getId());

			ResultSet rSet = pstm.executeQuery();

			Set<Integer> keys = map.keySet();

			while (rSet.next()) {

				for (Integer i : keys) {
					if (i == rSet.getInt("colocacao.posicao"))
						map.put(i, rSet.getInt("qtde"));
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;
	}

}
