package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca {
	/*
	 * La logica è che parto direttamente dalla lettera iniziale della parola che sto verificando se esiste nella board
	 * Scorro una per una le parole del dizionario, anzichè cercare le combinazioni sulla board e poi verificare che vi siano in esso
	 * L'algoritmo è abbastanza efficiente date le dimensioni ridotte e i tagli (pruning) sull'albero di ricerca qualora nelle lettere
	 * adiacenti non vi sia la lettera che sta alla posizione (livello) della parola
	 */
	
	public List<Pos> trovaParola(String parola, Board board) {
		for (Pos p : board.getPositions()) {
			 if (board.getCellValueProperty(p).get().charAt(0) == parola.charAt(0)) { // serve il .get() perchè essendo una StringProperty non si ottiene immediatamente il valore 
				 // potenziale inizio della parola nella board. Inizio la ricorsione
				 List <Pos> percorso = new ArrayList<>();
				 percorso.add(p);
				 
				 if (cerca(parola, 1, percorso, board)) { // Avvio quindi la ricorsione
					 return percorso;
				 }
			 }
		}
		return null;
	}

	private boolean cerca(String parola, int livello, List<Pos> percorso, Board board) {
		// caso terminale
		if (livello == parola.length()) {
			return true;
		} 
		Pos ultima = percorso.get(percorso.size() - 1);
		List<Pos> adiacenti = board.getAdjacencies(ultima);
		for (Pos p : adiacenti) {
			if (!percorso.contains(p) && //non ho già usato la lettera alla Pos p
					(parola.charAt(livello) == board.getCellValueProperty(p).get().charAt(0))) { // se la lettera che sto cercando coincide con una delle adiacenti
				// faccio la ricorsione
				percorso.add(p);
				if (cerca(parola, livello+1, percorso, board))
					return true;
				percorso.remove(percorso.size()-1); //backtracking
			}
		}
		 
		return false;
	}
}
