package br.com.grd.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.grd.cm.excecao.ExplosaoException;

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean aberto;
	private boolean minado;
	private boolean marcado;
	
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_AZUL = "\u001B[34m";
	private static final String ANSI_AMARELO = "\u001B[33m";
	private static final String ANSI_VERMELHO = "\u001B[35m";
	private static final String ANSI_ROXO = "\u001B[35m";
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionrVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}	
	}
	
	void alternarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	boolean abrir() {
		if(!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				throw new ExplosaoException();
			}
			
			if(vizinhacaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
			return false;
		}
	}
	
	boolean vizinhacaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void minar() {
		minado = true;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	long minasNaVizinhaca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "x";
		}else if(aberto && minado) {
			return "*";
		}else if(aberto && minasNaVizinhaca() > 0) {
			if(minasNaVizinhaca() == 1) {
				return ANSI_AZUL + Long.toString(minasNaVizinhaca()) + ANSI_RESET;
			}else if(minasNaVizinhaca() == 2) {
				return ANSI_AMARELO + Long.toString(minasNaVizinhaca()) + ANSI_RESET;
			}else if(minasNaVizinhaca() == 3) {
				return ANSI_VERMELHO + Long.toString(minasNaVizinhaca()) + ANSI_RESET;
			}else {
				return ANSI_ROXO + Long.toString(minasNaVizinhaca()) + ANSI_RESET;
			}
		}else if(aberto) {
			return " ";
		}else {
			return "?";
		}
	}
	
	
}
