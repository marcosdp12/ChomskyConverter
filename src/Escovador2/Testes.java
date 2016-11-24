package Escovador2;

import static org.junit.Assert.*;

import org.junit.Test;

public class Testes {

	@Test
	public void seAdicionaRegraEntaoRemoveLetraMaiusculasDaListaDeLetrasNaoUsadas() {
		ChomskyConverter converter = new ChomskyConverter();
		assertEquals(26,converter.CodigosLetrasMaiusculas.size());
		converter.addInitialRule("A", "a");
		assertEquals(25,converter.CodigosLetrasMaiusculas.size());
		assertEquals(false,converter.CodigosLetrasMaiusculas.contains(65));
	}
	@Test
	public void verificaSeUmNovoEstadoEhCriadoCorretamente() {
		ChomskyConverter converter = new ChomskyConverter();
		converter.addInitialRule("A", "ab");
		converter.addInitialRule("B", "b");
		converter.addInitialRule("C", "bB");
		//converter.printRules();
		assertEquals("D", "" + converter.getNewState());
	}
	@Test
	public void verificaSubstituirTerminais() {
		ChomskyConverter converter = new ChomskyConverter();
		converter.addInitialRule("A", "BCDE");
		converter.addInitialRule("B", "aDE");
		converter.addInitialRule("C", "acbDE");
		converter.addInitialRule("D", "c");
		converter.addInitialRule("E", "d");
		converter.addInitialRule("F", "acADE");
		
		//converter.printRules();
		//System.out.println("AFTER:");
		converter.substituirTerminais();
		//converter.printRules();
		String expected = "A -> BCDE\nB -> FDE\nC -> FGHDE\nD -> c"+
		"\nE -> d\nF -> a\nG -> c\nH -> b\n";
		//assertEquals(expected, converter.printRules());
	}
	@Test
	public void teste1() {
		ChomskyConverter converter = new ChomskyConverter();
		converter.addInitialRule("A", "aB");
		converter.addInitialRule("B", "b");
		converter.addInitialRule("B", "baA");
		converter.convertGrammartoChomsky();
		//converter.printRules();
		
	}
	
	@Test
	public void verificaSubstituteLargeRules() {
		ChomskyConverter converter = new ChomskyConverter();
		converter.addInitialRule("A", "BCDE");
		converter.addInitialRule("B", "aDE");
		converter.addInitialRule("C", "acbDE");
		converter.addInitialRule("D", "mMJ");
		converter.addInitialRule("E", "iJK");
		converter.addInitialRule("F", "daHK");
		converter.addInitialRule("G", "ABd");
		converter.addInitialRule("H", "JKl");
		converter.addInitialRule("I", "LkMN");
		converter.addInitialRule("J", "nMJ");
		converter.addInitialRule("K", "IKJ");
		converter.addInitialRule("L", "STKI");
		converter.addInitialRule("M", "MNTH");
		converter.addInitialRule("N", "MNOP");
		converter.addInitialRule("O", "HFT");
		converter.addInitialRule("P", "JKT");
		converter.addInitialRule("Q", "FDG");
		converter.addInitialRule("R", "JHD");
		converter.addInitialRule("S", "aeIJ");
		converter.addInitialRule("T", "aB");
		converter.addInitialRule("U", "IOk");
		
		converter.convertGrammartoChomsky();
		
	}
	@Test
	public void lerGramaticaDeArquivo() {
		ChomskyConverter converter = new ChomskyConverter();
	    converter.lerGramaticaDeArquivo("GramaticaG1.txt");
	    //converter.printRules();
	    converter.convertGrammartoChomsky();
	    converter.printRules();
	    
		
	}
	

	

}
