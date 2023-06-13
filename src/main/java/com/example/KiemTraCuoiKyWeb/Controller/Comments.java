package com.example.KiemTraCuoiKyWeb.Controller;

public class Comments {
	private int id, id_user_comments,star,id_product_comment;
	private String comment,user;
	public Comments() {
		comment=user ="";
		id = id_product_comment = id_user_comments = 0;
	}
	public Comments(int id, int id_user_comments, int star, int id_product_comment, String comment, String user) {
		this.id = id;
		this.id_user_comments = id_user_comments;
		this.star = star;
		this.id_product_comment = id_product_comment;
		this.comment = comment;
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_user_comments() {
		return id_user_comments;
	}
	public void setId_user_comments(int id_user_comments) {
		this.id_user_comments = id_user_comments;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public int getId_product_comment() {
		return id_product_comment;
	}
	public void setId_product_comment(int id_product_comment) {
		this.id_product_comment = id_product_comment;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
}
