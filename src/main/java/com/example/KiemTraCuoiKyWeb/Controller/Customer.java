package com.example.KiemTraCuoiKyWeb.Controller;

public class Customer {
	private int id;
	private String user_name,user,password,email,diachi,vaitro;
	public Customer() {
		this.id = 0;
		this.user = this.user_name = this.password = this.email = this.diachi = this.vaitro = "";
	}
	public Customer(int id, String user_name, String user, String password, String email, String diachi,
			String vaitro) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.user = user;
		this.password = password;
		this.email = email;
		this.diachi = diachi;
		this.vaitro = vaitro;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}
	public String getVaitro() {
		return vaitro;
	}
	public void setVaitro(String vaitro) {
		this.vaitro = vaitro;
	}
	
	
}
