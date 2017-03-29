package br.com.gerenciapoker.test;

import java.sql.Connection;

import br.com.gerenciapoker.dao.ConnectionFactory;

public class TestaConexao {

	private static Connection con = null;
	
	public static void main(String[] args) {
		
		con = ConnectionFactory.getConnection();
		
		if(con==null){
			System.out.println("Houve erro");
		} else {
			System.out.println("Conectado com sucesso");
		}
		
	}

}
