package br.com.gerenciapoker.test;

import java.util.ArrayList;
import java.util.List;

import br.com.gerenciapoker.dao.JogadorDao;
import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Jogador;

public class JogadorTEST {

	public Jogador jogador;
	
	public void save(){
		jogador = new Jogador("Teste");
		
		jogador.setId(new JogadorDao().save(jogador));
		System.out.println(jogador.getId());
	}
	
	public void edite(){
		jogador = new Jogador(23, "Teste2");
		
		new JogadorDao().edite(jogador);
	}
	
	public void delete(){
		jogador = new Jogador(23);
		
		new JogadorDao().delete(jogador);
	}
	
	public void find(){
		jogador = new Jogador(22);
		
		jogador = (Jogador) new JogadorDao().find(jogador);
		
	System.out.println("Localizando pelo Id\n");
	System.out.println(jogador.getId());
	System.out.println(jogador.getNome());
	}
	
	public void findByNome(){
		jogador = new Jogador("Paulinho");
		
		jogador = (Jogador) new JogadorDao().findByNome(jogador);
		
	System.out.println("\nLocalizando pelo Nome\n");
	System.out.println(jogador.getId());
	System.out.println(jogador.getNome());
	}
	
	public void findAll(){
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		lista = new JogadorDao().findAll();
		for(EntidadeDominio e : lista){
			if(e instanceof Jogador){
				Jogador jogador = (Jogador) e;
				System.out.println(jogador.getId());
				System.out.println(jogador.getNome());
			}
		}
	}
	
	public static void main(String[] args) {
		JogadorTEST jogadorTESTE = new JogadorTEST();
		//jogadorTESTE.save();
		//jogadorTESTE.edite();
		//jogadorTESTE.delete();
		//jogadorTESTE.find();
		//jogadorTESTE.findByNome();
		jogadorTESTE.findAll();
	}
}
