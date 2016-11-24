package Escovador2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ChomskyConverter {
	long startTime;
	long duracao;
	long stopTime;
	List <Integer> CodigosLetrasMaiusculas = new ArrayList <Integer> ();
	List <String> terminaisSubstituiveis = new ArrayList <String> ();
	List <Rule> initialRules = new ArrayList <Rule>();
	List <Rule> newRules = new ArrayList <Rule>();
	List <Rule> newRulesUpper = new ArrayList <Rule>();
	static int indice;
	String nomeArquivo ="";
	
	ChomskyConverter(){
		indice = 0;
		for(int i=65; i<91; i++){
			CodigosLetrasMaiusculas.add(i);
		}
	}
	
	public void lerGramaticaDeArquivo(String file){
		nomeArquivo=file;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String [] rule = line.split(" ");
		    	addInitialRule(rule[0], rule[1]);
		    }
		    br.close();
		}
		catch (Exception e){
			System.out.println("Há algum problema na entrada.");
		}
	}
	
	String getNewState(){
		if(CodigosLetrasMaiusculas.isEmpty()==true){
			for(int i=65; i<91; i++){
				CodigosLetrasMaiusculas.add(i);
			}
			indice++;
		}
		int i = CodigosLetrasMaiusculas.get(0);
		CodigosLetrasMaiusculas.remove(0);
		String caracter = "" + (char) i;
		if(indice!=0){
			return caracter + Integer.toString(indice);
		}
		return caracter;
	}
	
	String printRules(){
		String rules="";
		for(Rule rule: initialRules){
			//System.out.println(rule.getLeftPart()+" -> "+rule.getRightPart());
			rules = rules + rule.getLeftPart()+" -> "+rule.getRightPart() +"\r\n";
		}
		
		for(Rule rule: newRules){
			//System.out.println(rule.getLeftPart()+" -> "+rule.getRightPart());
			rules = rules + rule.getLeftPart()+" -> "+rule.getRightPart() +"\r\n";
		}
		for(Rule rule: newRulesUpper){
			//System.out.println(rule.getLeftPart()+" -> "+rule.getRightPart());
			rules = rules + rule.getLeftPart()+" -> "+rule.getRightPart() +"\r\n";
		}
		return rules;
	}
	
	void printGramaticaChomskyNaTela(){
		System.out.println(printRules());
	}
	
	void addInitialRule(String lpart, String rpart){
		
		if(lpart.length()==1){
			Integer k = lpart.charAt(0) - '0' + 48;
			int j = CodigosLetrasMaiusculas.indexOf(k);
			if(j>-1){
				CodigosLetrasMaiusculas.remove(j); 
			}
		}
		
		if(rpart.length()>1){
			for(int i=0; i<rpart.length();i++){
				if(rpart.charAt(i)>= 'a' && rpart.charAt(i)<= 'z'){
					if(terminaisSubstituiveis.contains(""+rpart.charAt(i))==false){
						terminaisSubstituiveis.add(""+rpart.charAt(i));			
					}
				}
			}
		}
		initialRules.add(new Rule(lpart,rpart)); 
	}
	//A->aB, entao A->XB e X->a
	void substituirTerminais(){
		for(String terminal: terminaisSubstituiveis){
			String terminal_lpart = getNewState();
			newRules.add(new Rule(terminal_lpart, terminal));
			for(Rule rule:initialRules){
				rule.changeLowerCaseToUpperCase(terminal, terminal_lpart);				
			}
		}
	}
	
	String criaNovaRegraSeElaNaoExiste(String rpart){
		for(Rule rule: newRulesUpper){
			if(rule.getRightPart().equals(rpart))
				return rule.getLeftPart();
		}
		String newState = getNewState();
		newRulesUpper.add(new Rule(newState, rpart));
		for(Rule r:initialRules){
			if(r.getRightPart().contains(rpart)&&r.getRightPart().length()>2){
				r.changeRPart(rpart, newState);
			}
		}
		return newState;
	}
	
	int numOfUpperLetters(String str){
		int qtd = 0;
		for(int i=0; i<str.length(); i++){
			if(str.charAt(i)>='A'&&str.charAt(i)<='Z'){
				qtd++;
			}
		}
		return qtd;
	}
	
	String finalTwoStates(String str){
		String aux ="";
		int qtd = 0;
		int i = str.length()-1;;
		while(qtd<2){
			aux = str.charAt(i) + aux;
			if(str.charAt(i)>='A'&&str.charAt(i)<='Z'){
				qtd++;
			}
			i--;
		}
		return aux;
	}
	
	void eliminateLargeRules(){
		for(Rule rule:initialRules){
			String rpart = rule.getRightPart();
			if(numOfUpperLetters(rpart)>2){
				while(numOfUpperLetters(rpart)>2){
					String aux = finalTwoStates(rpart);
					rpart = rpart.replaceAll(aux,criaNovaRegraSeElaNaoExiste(aux));
				}
			}
		}
	}
	
	void convertGrammartoChomsky(){
		startTime = System.currentTimeMillis();
		substituirTerminais();
		eliminateLargeRules();
		stopTime = System.currentTimeMillis();
		duracao = stopTime - startTime;
	}
	
	void printTempoExecucao(){
		System.out.println("Tempo de duracao: " + duracao + " ms");
	}
	
	public void printGramaticaChomskyEmArquivoTXT(){
		try{
			FileWriter fw = new FileWriter( "SaidaChomskyPara_"+nomeArquivo);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(printRules());
			pw.close();
		} catch (IOException e){
			System.out.println("ERRO!");
		}
		
	}
	
	public static void main (String [] args){
		ChomskyConverter converter = new ChomskyConverter();
	    converter.lerGramaticaDeArquivo("exemploEntrada.txt");
	    converter.convertGrammartoChomsky();
	    converter.printGramaticaChomskyEmArquivoTXT();
	    //converter.printGramaticaChomskyNaTela();
	    converter.printTempoExecucao();
	}

	
}
