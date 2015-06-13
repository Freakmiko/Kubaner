package Kubaner.Logic;

public class Break implements TimeLineMember {

	private int length;
	
	public Break(int length){
		this.length=length;
	}
	
	@Override
	public int getLength() {		
		return length;
	}
	
	public void setLength(int length){
		this.length=length;
	}

}
