package br.com.gerenciapoker.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.gerenciapoker.dao.ColocacaoDao;
import br.com.gerenciapoker.dao.JogadorDao;
import br.com.gerenciapoker.dao.JogadorPartidaDao;
import br.com.gerenciapoker.dao.LocalDao;
import br.com.gerenciapoker.dao.PartidaDao;
import br.com.gerenciapoker.dao.PremiacaoDao;
import br.com.gerenciapoker.dominio.Colocacao;
import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Jogador;
import br.com.gerenciapoker.dominio.Local;
import br.com.gerenciapoker.dominio.Partida;
import br.com.gerenciapoker.util.JSFUtil;

@ManagedBean(name = "MBPartida")
@ViewScoped
public class PartidaBean implements IBean {

	private Partida partida;
	private List<EntidadeDominio> partidas;
	private PartidaDao partidaDao;
	private List<EntidadeDominio> jogadores;
	private Jogador jogador;
	private List<EntidadeDominio> colocacoes;
	private List<EntidadeDominio> locais;
	private Integer totalPartidas;
	private String data;
	

	public PartidaBean() {
		partida = new Partida();
		partidas = new ArrayList<EntidadeDominio>();
		jogador = new Jogador();

		jogadores = new ArrayList<EntidadeDominio>();
		jogadores = new JogadorDao().findAll();

		colocacoes = new ArrayList<EntidadeDominio>();
		colocacoes = new ColocacaoDao().findAll();

		locais = new ArrayList<EntidadeDominio>();
		locais = new LocalDao().findAll();

		partidaDao = new PartidaDao();

		findAll();
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public List<EntidadeDominio> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<EntidadeDominio> partidas) {
		this.partidas = partidas;
	}

	public PartidaDao getPartidaDao() {
		return partidaDao;
	}

	public void setPartidaDao(PartidaDao partidaDao) {
		this.partidaDao = partidaDao;
	}

	public List<EntidadeDominio> getJogadores() {
		return jogadores;
	}

	public void setJogadores(List<EntidadeDominio> jogadores) {
		this.jogadores = jogadores;
	}

	public List<EntidadeDominio> getColocacoes() {
		return colocacoes;
	}

	public void setColocacoes(List<EntidadeDominio> colocacoes) {
		this.colocacoes = colocacoes;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public List<EntidadeDominio> getLocais() {
		return locais;
	}

	public void setLocais(List<EntidadeDominio> locais) {
		this.locais = locais;
	}

	public Integer getTotalPartidas() {
		return totalPartidas;
	}

	public void setTotalPartidas(Integer totalPartidas) {
		this.totalPartidas = totalPartidas;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void findAll() {
		partidas = new JogadorPartidaDao().findAll();
		totalPartidas = partidas.size();
	}

	public void execute() {

		partida.setParticipacao(partida.getJogadores().size());
		partida.setLocal((Local) new LocalDao().find(partida.getLocal()));

		if (partida.getId() == null) {
			save();
		} else {
			edite();
		}
		
		partida = new Partida();

	}

	@Override
	public void save() {

		if (!valide()) {
			JSFUtil.adicionarMensagemErro("Partida invalida");
			return;
		}

		partida.setId(partidaDao.save(partida));

		// verifica se a Partida foi armazenada no BD
		if (partida.getId() == null || partida.getId() < 1) {
			JSFUtil.adicionarMensagemErro("Não foi possível executar a operação");
			return;
		} else {
			for (Jogador e : partida.getJogadores()) {

				Partida p = new Partida();
				p = partida;
				p.setJogadores(new ArrayList<Jogador>());
				p.getJogadores().add(0, e);

				// Se nao houve erro salva a Premiação no BD
				e.getPremiacao().setId(new PremiacaoDao().save(e.getPremiacao()));

				// verifica se a Premiacao foi armazenada no BD
				if (e.getPremiacao().getId() == null) {
					JSFUtil.adicionarMensagemErro("Não foi possível executar a operação");
					return;
				} else {
					// Se nao houve erro salva os jogadores da partida
					// no BD
					new JogadorPartidaDao().save(p);
					// verifica se a Partida foi armazenada no BD
				}
			}
		}

		JSFUtil.adicionarMensagemSucesso("Salvo com sucesso");
		findAll();

	}

	@Override
	public void delete() {

		// deletando todas as jogadores_partidas
		new JogadorPartidaDao().deleteByPartida(partida);

		// Deletando todas as premiações
		for (Jogador j : partida.getJogadores()) {
			new PremiacaoDao().delete(j.getPremiacao());
		}

		// deletando a partida
		partidaDao.delete(partida);
		JSFUtil.adicionarMensagemSucesso("Excluido com sucesso");
		clear();
	}

	@Override
	public void edite() {

		// Editando a Partida no BD
		if (!valide()) {
			JSFUtil.adicionarMensagemErro("Partida invalida");
			return;
		}
		partidaDao.edite(partida);

		for (Jogador e : partida.getJogadores()) {

			Partida p = new Partida();
			p = partida;
			p.setJogadores(new ArrayList<Jogador>());
			p.getJogadores().add(0, e);

			if (e.getPremiacao().getId() == null) {
				e.getPremiacao().setId(new PremiacaoDao().save(e.getPremiacao()));
			} else {
				new PremiacaoDao().edite(e.getPremiacao());
			}

			new JogadorPartidaDao().edite(p);
		}

		JSFUtil.adicionarMensagemSucesso("Editado com sucesso");
		
		findAll();

	}

	@Override
	public void clear() {
		partida = new Partida();
		jogador = new Jogador();
		findAll();

	}

	/**
	 * 
	 * @return true se tudo foi validado corretamente
	 */
	private boolean valide() {

		if (partida.getData() == null)
			return false;
		if (partida.getParticipacao() < 0)
			return false;

		return true;
	}

	public void inserirJogador() {

		if (!validarInserirJogador()) {
			JSFUtil.adicionarMensagemErro("Jogador não preenchido corretamente");
			return;
		}

		// localizando a colocação selecionado e add à premiação
		jogador.getPremiacao().setColocacao((Colocacao) new ColocacaoDao().find(jogador.getPremiacao().getColocacao()));

		Jogador j = new Jogador();
		j = jogador;

		// clicado em add
		if (jogador.getNome() == null) {

			j = (Jogador) new JogadorDao().find(jogador);
			jogador.setNome(j.getNome());

		} else {
			// clicado em Editar
			j = (Jogador) new JogadorDao().find(jogador);
			jogador.setNome(j.getNome());

			// removendo o jogador da lista de jogadores e colocando o jogador
			// editado
			List<Jogador> jogadoresBackup = new ArrayList<Jogador>();

			jogadoresBackup = partida.getJogadores();

			for (int i = 0; i < jogadoresBackup.size(); i++) {
				if (jogadoresBackup.get(i).getId() == jogador.getId()) {
					jogadoresBackup.remove(i);
					jogadoresBackup.add(jogador);
				}
			}

			partida.setJogadores(new ArrayList<Jogador>());
			partida.setJogadores(jogadoresBackup);

			jogador = new Jogador();

			return;
		}

		partida.getJogadores().add(jogador);

		jogador = new Jogador();
	}

	/**
	 * Remove um jogador inserido na partida
	 */
	public void removerJogadorInserido() {

		List<Jogador> jogadoresBackup = new ArrayList<Jogador>();

		jogadoresBackup = partida.getJogadores();

		for (int i = 0; i < jogadoresBackup.size(); i++) {
			if (jogadoresBackup.get(i).getId() == jogador.getId()) {
				jogadoresBackup.remove(i);
			}
		}

		partida.setJogadores(new ArrayList<Jogador>());
		partida.setJogadores(jogadoresBackup);

		jogador = new Jogador();
	}

	/**
	 * Valida o jogador antes de inseri-lo na partida
	 * 
	 * @return
	 */
	private boolean validarInserirJogador() {

		if (jogador.getId() == null)
			return false;
		if (jogador.getPremiacao().getColocacao().getId() == null)
			return false;
		if (jogador.getDespesa().getEntrada().compareTo(new BigDecimal("0.0")) != 1)
			return false;

		return true;
	}

}
