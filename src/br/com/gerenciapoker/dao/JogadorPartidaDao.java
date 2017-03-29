package br.com.gerenciapoker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.gerenciapoker.dominio.Despesa;
import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Jogador;
import br.com.gerenciapoker.dominio.Partida;
import br.com.gerenciapoker.dominio.Premiacao;

public class JogadorPartidaDao implements IDao {

	private static Connection con = null;

	@Override
	public Integer save(EntidadeDominio entidade) {

		if (!(entidade instanceof Partida)) {
			return 0;
		}

		Partida partida = new Partida();
		partida = (Partida) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO JOGADOR_PARTIDA ");
		sql.append("(ENTRADA, REBUY, ADD_ON, JOGADOR_ID, PARTIDA_ID, PREMIACAO_ID) VALUES (?, ?, ?, ?, ?, ?) ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstm.setBigDecimal(1, partida.getJogadores().get(0).getDespesa().getEntrada());
			pstm.setBigDecimal(2, partida.getJogadores().get(0).getDespesa().getRebuy());
			pstm.setBigDecimal(3, partida.getJogadores().get(0).getDespesa().getAdd_on());
			pstm.setInt(4, partida.getJogadores().get(0).getId());
			pstm.setInt(5, partida.getId());
			pstm.setInt(6, partida.getJogadores().get(0).getPremiacao().getId());

			pstm.executeUpdate();

			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				partida.setId(rs.getInt(1));
			}
			rs.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return partida.getId();
	}

	@Override
	public void edite(EntidadeDominio entidade) {
		if (!(entidade instanceof Partida)) {
			return;
		}

		Partida partida = new Partida();
		partida = (Partida) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE JOGADOR_PARTIDA SET ");
		sql.append("ENTRADA=?, REBUY=?, ADD_ON=?, JOGADOR_ID=?, PARTIDA_ID=?, PREMIACAO_ID=? WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setBigDecimal(1, partida.getJogadores().get(0).getDespesa().getEntrada());
			pstm.setBigDecimal(2, partida.getJogadores().get(0).getDespesa().getRebuy());
			pstm.setBigDecimal(3, partida.getJogadores().get(0).getDespesa().getAdd_on());
			pstm.setInt(4, partida.getJogadores().get(0).getId());
			pstm.setInt(5, partida.getId());
			pstm.setInt(6, partida.getJogadores().get(0).getPremiacao().getId());
			pstm.setInt(7, partida.getJogadores().get(0).getDespesa().getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(EntidadeDominio entidade) {
		if (!(entidade instanceof Partida)) {
			return;
		}

		Partida partida = new Partida();
		partida = (Partida) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM JOGADOR_PARTIDA WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, partida.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public EntidadeDominio find(EntidadeDominio entidade) {
		if (!(entidade instanceof Partida)) {
			return null;
		}

		Partida partida = new Partida();
		partida = (Partida) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM JOGADOR_PARTIDA WHERE ID=?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, partida.getId());

			ResultSet rSet = pstm.executeQuery();

			if (rSet.next()) {
				partida = new Partida();

				Jogador jogador = new Jogador();
				jogador.setId(rSet.getInt("jogador_id"));
				jogador = (Jogador) new JogadorDao().find(jogador);

				Despesa despesa = new Despesa();
				despesa.setEntrada(rSet.getBigDecimal("entrada"));
				despesa.setRebuy(rSet.getBigDecimal("rebuy"));
				despesa.setAdd_on(rSet.getBigDecimal("add_on"));
				despesa.setId(rSet.getInt("id"));

				Premiacao premiacao = new Premiacao();
				premiacao.setId(rSet.getInt("premiacao_id"));
				premiacao = (Premiacao) new PremiacaoDao().find(premiacao);

				jogador.setPremiacao(premiacao);
				jogador.setDespesa(despesa);

				List<Jogador> jogadores = new ArrayList<Jogador>();
				jogadores.add(jogador);

				partida.setId(rSet.getInt("partida_id"));
				partida = (Partida) new PartidaDao().find(partida);

				partida.setJogadores(jogadores);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return partida;
	}

	@Override
	public List<EntidadeDominio> findAll() {

		List<EntidadeDominio> partidas = new ArrayList<EntidadeDominio>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM JOGADOR_PARTIDA group by partida_id");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());

			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {
				Partida partida = new Partida();

				partida.setId(rSet.getInt("partida_id"));
				partidas.add(findByPartida(partida));

			}
			
			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return partidas;
	}
	
	public EntidadeDominio findByPartida(EntidadeDominio entidade) {
		if (!(entidade instanceof Partida)) {
			return null;
		}

		Partida partida = new Partida();
		partida = (Partida) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM JOGADOR_PARTIDA WHERE PARTIDA_ID=?");

		if(con==null)
			con = ConnectionFactory.getConnection();
		
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, partida.getId());

			ResultSet rSet = pstm.executeQuery();
			
			partida = new Partida();
			
			while(rSet.next()) {
				
				if(rSet.isFirst()){
				
				partida.setId(rSet.getInt("partida_id"));
				partida = (Partida) new PartidaDao().find(partida);
				}
				
				Jogador jogador = new Jogador();
				jogador.setId(rSet.getInt("jogador_id"));
				jogador = (Jogador) new JogadorDao().find(jogador);

				Despesa despesa = new Despesa();
				despesa.setEntrada(rSet.getBigDecimal("entrada"));
				despesa.setRebuy(rSet.getBigDecimal("rebuy"));
				despesa.setAdd_on(rSet.getBigDecimal("add_on"));
				despesa.setId(rSet.getInt("id"));

				Premiacao premiacao = new Premiacao();
				premiacao.setId(rSet.getInt("premiacao_id"));
				premiacao = (Premiacao) new PremiacaoDao().find(premiacao);

				jogador.setPremiacao(premiacao);
				jogador.setDespesa(despesa);
				
				partida.getJogadores().add(jogador);

			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return partida;
	}
	
	public void deleteByPartida(EntidadeDominio entidade) {
		if (!(entidade instanceof Partida)) {
			return;
		}

		Partida partida = new Partida();
		partida = (Partida) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM JOGADOR_PARTIDA WHERE PARTIDA_ID = ? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, partida.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void editeByJogador(EntidadeDominio entidade) {
		if (!(entidade instanceof Partida)) {
			return;
		}

		Partida partida = new Partida();
		partida = (Partida) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE JOGADOR_PARTIDA SET ");
		sql.append("ENTRADA=?, REBUY=?, ADD_ON=?, JOGADOR_ID=?, PARTIDA_ID=?, PREMIACAO_ID=? WHERE JOGADOR_ID=? AND PARTIDA_ID = ?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setBigDecimal(1, partida.getJogadores().get(0).getDespesa().getEntrada());
			pstm.setBigDecimal(2, partida.getJogadores().get(0).getDespesa().getRebuy());
			pstm.setBigDecimal(3, partida.getJogadores().get(0).getDespesa().getAdd_on());
			pstm.setInt(4, partida.getJogadores().get(0).getId());
			pstm.setInt(5, partida.getId());
			pstm.setInt(6, partida.getJogadores().get(0).getPremiacao().getId());
			pstm.setInt(7, partida.getJogadores().get(0).getId());
			pstm.setInt(8, partida.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	

}
