package br.com.gerenciapoker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.gerenciapoker.dominio.Colocacao;
import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Premiacao;

public class PremiacaoDao implements IDao {

	private static Connection con = null;

	@Override
	public Integer save(EntidadeDominio entidade) {

		if (!(entidade instanceof Premiacao)) {
			return 0;
		}

		Premiacao premiacao = new Premiacao();
		premiacao = (Premiacao) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO PREMIACAO ");
		sql.append("(VALOR, COLOCACAO_ID) VALUES (?, ?) ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstm.setBigDecimal(1, premiacao.getValor());
			pstm.setInt(2, premiacao.getColocacao().getId());
			pstm.executeUpdate();

			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				premiacao.setId(rs.getInt(1));
			}
			rs.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return premiacao.getId();
	}

	@Override
	public void edite(EntidadeDominio entidade) {
		if (!(entidade instanceof Premiacao)) {
			return;
		}

		Premiacao premiacao = new Premiacao();
		premiacao = (Premiacao) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE PREMIACAO SET ");
		sql.append("VALOR = ?, COLOCACAO_ID = ? WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setBigDecimal(1, premiacao.getValor());
			pstm.setInt(2, premiacao.getColocacao().getId());
			pstm.setInt(3, premiacao.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(EntidadeDominio entidade) {
		if (!(entidade instanceof Premiacao)) {
			return;
		}

		Premiacao premiacao = new Premiacao();
		premiacao = (Premiacao) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM PREMIACAO WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, premiacao.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public EntidadeDominio find(EntidadeDominio entidade) {
		if (!(entidade instanceof Premiacao)) {
			return null;
		}

		Premiacao premiacao = new Premiacao();
		premiacao = (Premiacao) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM PREMIACAO WHERE ID=?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, premiacao.getId());
			
			ResultSet rSet = pstm.executeQuery();

			if(rSet.next()){
				premiacao = new Premiacao();
				premiacao.setId(rSet.getInt("id"));
				premiacao.setValor(rSet.getBigDecimal("valor"));
				
				Colocacao c = new Colocacao();
				c.setId(rSet.getInt("colocacao_id"));
				
				c= (Colocacao) new ColocacaoDao().find(c);
				premiacao.setColocacao(c);
				
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return premiacao;
	}

	@Override
	public List<EntidadeDominio> findAll() {
		
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM PREMIACAO ORDER BY ID ASC");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			
			ResultSet rSet = pstm.executeQuery();

			while(rSet.next()){
				Premiacao premiacao = new Premiacao();
				premiacao.setId(rSet.getInt("id"));
				premiacao.setValor(rSet.getBigDecimal("valor"));
				
				Colocacao c = new Colocacao();
				c.setId(rSet.getInt("colocacao_id"));
				
				c= (Colocacao) new ColocacaoDao().find(c);
				premiacao.setColocacao(c);
				
				lista.add(premiacao);
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
