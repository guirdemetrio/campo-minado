package br.com.grd.cm;

import br.com.grd.cm.modelo.Tabuleiro;
import br.com.grd.cm.visao.TabuleiroConsole;

public class Aplicacao {
	
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);
		
		
		
	}

}
