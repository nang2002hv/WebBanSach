package com.example.KiemTraCuoiKyWeb.Controller;

public class OrderDetail {
	private int id,cid,pid;
	private int quantity;
	private long price;
	
	public OrderDetail() {

	}
	public OrderDetail(int cid, int pid, int quantity, long price) {
		
		this.cid = cid;
		this.pid = pid;
		this.quantity = quantity;
		this.price = price;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getId() {
		return id;
	}
}
