package br.com.gerenciapoker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Local;
import br.com.gerenciapoker.dominio.Partida;

public class PartidaDao implements IDao {

	private static Connection con = null;

	@Override
	public Integer save(EntidadeDominio entidade) {

		if (!(entidade instanceof Partida)) {
			return 0;
		}

		Partida partida = new Partida();
		partida = (Partida) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO PARTIDA ");
		sql.append("(DATA, LOCAL, VALOR_RANKING, PARTICIPACAO) VALUES (?, ?, ?, ?) ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd");
			String data = stf.format(partida.getData());

			pstm.setString(1, data);
			pstm.setInt(2, partida.getLocal().getId());
			pstm.setBigDecimal(3, partida.getPremiacaoRanking());
			pstm.setInt(4, partida.getJogadores().size());
			pstm.executeUpdate();

			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				partida.setId(rs.getInt(1));
			}

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
		sql.append("UPDATE PARTIDA SET ");
		sql.append("DATA=?, LOCAL=?, VALOR_RANKING=?, PARTICIPACAO=? WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd");
			String data = stf.format(partida.getData());

			pstm.setString(1, data);
			pstm.setInt(2, partida.getLocal().getId());
			pstm.setBigDecimal(3, partida.getPremiacaoRanking());
			pstm.setInt(4, partida.getJogadores().size());
			pstm.setInt(5, partida.getId());
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
		sql.append("DELETE FROM PARTIDA WHERE ID=? ");

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
		sql.append("SELECT * FROM PARTIDA WHERE ID=?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, partida.getId());

			ResultSet rSet = pstm.executeQuery();

			if (rSet.next()) {
				partida = new Partida();
				partida.setId(rSet.getInt("id"));

				partida.setData(rSet.getDate("data"));

				Local local = new Local(rSet.getInt("local"));
				local = (Local) new LocalDao().find(local);
				partida.setLocal(local);

				partida.setPremiacaoRanking(rSet.getBigDecimal("valor_ranking"));
				partida.setParticipacao(rSet.getInt("participacao"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return partida;
	}

	@Override
	public List<EntidadeDominio> findAll() {

		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM PARTIDA ORDER BY ID ASC");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());

			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {
				Partida partida = new Partida();
				partida.setId(rSet.getInt("id"));
				partida.setData(rSet.getDate("data"));

				Local local = new Local(rSet.getInt("local"));
				local = (Local) new LocalDao().find(local);
				partida.setLocal(local);

				partida.setPremiacaoRanking(rSet.getBigDecimal("valor_ranking"));
				partida.setParticipacao(rSet.getInt("participacao"));

				lista.add(partida);
			}

			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

}
