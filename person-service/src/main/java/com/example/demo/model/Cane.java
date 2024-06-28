package com.example.demo.model;

import lombok.Data;

@Data
public class Cane {
	
	private int nVolteVerso=0;
	
	public Cane(int nVolteVerso)
	{
		this.nVolteVerso=nVolteVerso;
	}
	
	
 public void Abbaia()
 {
	 for(int i=1;i<=nVolteVerso;i++)
	 {
		 System.out.println("Bau"+i);
	 }
 }
}
