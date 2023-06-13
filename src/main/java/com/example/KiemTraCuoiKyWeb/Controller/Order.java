package com.example.KiemTraCuoiKyWeb.Controller;

public class Order {
	private int id;
	private String date;
	private int cid;
	private long totalmoney;
	public Order() {
		
	}
	public Order(int id, String date, int cid, long totalmoney) {
		this.id = id;
		this.date = date;
		this.cid = cid;
		this.totalmoney = totalmoney;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public long getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(long totalmoney) {
		this.totalmoney = totalmoney;
	}
	public int getId() {
		return id;
	}
}
