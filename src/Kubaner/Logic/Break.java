package Kubaner.Logic;

import java.io.Serializable;

public class Break implements TimeLineMember, Serializable {

	private int length;
	
	public Break(int length){
		this.length=length;
	}
	
	@Override
	public int getLength() {		
		return length;
	}
	
	@Override
	public void setLength(int length){
		this.length=length;
	}

}
