package br.com.gerenciapoker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Local;

public class LocalDao implements IDao {

	private static Connection con = null;

	@Override
	public Integer save(EntidadeDominio entidade) {

		if (!(entidade instanceof Local)) {
			return 0;
		}

		Local local = new Local();
		local = (Local) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO LOCAL ");
		sql.append("(LOCAL) VALUES (?) ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, local.getLocal().toUpperCase());
			pstm.executeUpdate();

			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				local.setId(rs.getInt(1));
			}
			rs.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return local.getId();
	}

	@Override
	public void edite(EntidadeDominio entidade) {
		if (!(entidade instanceof Local)) {
			return;
		}

		Local local = new Local();
		local = (Local) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE LOCAL SET ");
		sql.append("LOCAL = ? WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setString(1, local.getLocal().toUpperCase());
			pstm.setInt(2, local.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(EntidadeDominio entidade) {
		if (!(entidade instanceof Local)) {
			return;
		}

		Local local = new Local();
		local = (Local) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM LOCAL WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, local.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public EntidadeDominio find(EntidadeDominio entidade) {
		if (!(entidade instanceof Local)) {
			return null;
		}

		Local local = new Local();
		local = (Local) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM LOCAL WHERE ID=?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, local.getId());
			
			ResultSet rSet = pstm.executeQuery();

			if(rSet.next()){
				local = new Local();
				local.setId(rSet.getInt("id"));
				local.setLocal(rSet.getString("local"));
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return local;
	}

	@Override
	public List<EntidadeDominio> findAll() {
		
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM LOCAL ORDER BY ID ASC");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			
			ResultSet rSet = pstm.executeQuery();

			while(rSet.next()){
				Local local = new Local();
				local.setId(rSet.getInt("id"));
				local.setLocal(rSet.getString("local"));
				
				lista.add(local);
			}
			
			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public EntidadeDominio findByLocal(EntidadeDominio entidade) {
		if (!(entidade instanceof Local)) {
			return null;
		}

		Local local = new Local();
		local = (Local) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM LOCAL WHERE LOCAL=?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setString(1, local.getLocal());
			
			ResultSet rSet = pstm.executeQuery();

			if(rSet.next()){
				local = new Local();
				local.setId(rSet.getInt("id"));
				local.setLocal(rSet.getString("local"));
			}
			
			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return local;
	}

}
