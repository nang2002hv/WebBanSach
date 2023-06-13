package com.example.KiemTraCuoiKyWeb.Controller;

public class Issuer {
	private int id;
	private String name_issuer;
	
	public Issuer() {
		
	}

	public Issuer(int id, String name_issuer) {
		super();
		this.id = id;
		this.name_issuer = name_issuer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName_issuer() {
		return name_issuer;
	}

	public void setName_issuer(String name_issuer) {
		this.name_issuer = name_issuer;
	}
	
	
}
