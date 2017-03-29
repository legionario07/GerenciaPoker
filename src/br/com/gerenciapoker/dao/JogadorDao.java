package br.com.gerenciapoker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Jogador;

public class JogadorDao implements IDao {

	private static Connection con = null;

	@Override
	public Integer save(EntidadeDominio entidade) {

		if (!(entidade instanceof Jogador)) {
			return 0;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO JOGADOR ");
		sql.append("(NOME) VALUES (?) ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, jogador.getNome().toUpperCase());
			pstm.executeUpdate();

			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				jogador.setId(rs.getInt(1));
			}
			rs.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jogador.getId();
	}

	@Override
	public void edite(EntidadeDominio entidade) {
		if (!(entidade instanceof Jogador)) {
			return;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE JOGADOR SET ");
		sql.append("NOME = ? WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setString(1, jogador.getNome().toUpperCase());
			pstm.setInt(2, jogador.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(EntidadeDominio entidade) {
		if (!(entidade instanceof Jogador)) {
			return;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM JOGADOR WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, jogador.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public EntidadeDominio find(EntidadeDominio entidade) {
		if (!(entidade instanceof Jogador)) {
			return null;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM JOGADOR WHERE ID=?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, jogador.getId());
			
			ResultSet rSet = pstm.executeQuery();

			if(rSet.next()){
				jogador = new Jogador();
				jogador.setId(rSet.getInt("id"));
				jogador.setNome(rSet.getString("nome"));
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jogador;
	}

	@Override
	public List<EntidadeDominio> findAll() {
		
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM JOGADOR ORDER BY ID ASC");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			
			ResultSet rSet = pstm.executeQuery();

			while(rSet.next()){
				Jogador jogador = new Jogador();
				jogador.setId(rSet.getInt("id"));
				jogador.setNome(rSet.getString("nome"));
				
				lista.add(jogador);
			}
			
			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public EntidadeDominio findByNome(EntidadeDominio entidade) {
		if (!(entidade instanceof Jogador)) {
			return null;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM JOGADOR WHERE NOME=?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setString(1, jogador.getNome());
			
			ResultSet rSet = pstm.executeQuery();

			if(rSet.next()){
				jogador = new Jogador();
				jogador.setId(rSet.getInt("id"));
				jogador.setNome(rSet.getString("nome"));
			}
			
			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jogador;
	}

}
