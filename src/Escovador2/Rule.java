package Escovador2;

public class Rule {
	String _lpart;
	String _rpart;
	boolean terminou = false;
	
	Rule (String lpart, String rpart){
		_lpart = lpart;
		_rpart = rpart;
	}
	
	public void changeRPart(String str, String state){
		_rpart = _rpart.replaceAll(str, state);
	}
	
	public boolean isThereSequenceInRightPart(String sequence){
		return _rpart.contains(sequence);
	}
	
	public void changeLowerCaseToUpperCase(String lowerCase, String UpperCase){
		if(_rpart.length()>1){
			_rpart = _rpart.replaceAll(lowerCase, UpperCase);
		}
	}
	
	public String getRightPart(){
		return _rpart;
	}
	
	public String getLeftPart(){
		return _lpart;
	}
	
	void OK(){
		terminou = true;
	}
}
