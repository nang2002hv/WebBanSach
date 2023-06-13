package com.example.KiemTraCuoiKyWeb.Controller;

public class Item {
	private Product product;
	private int quantity;
	private long price;
	public Item() {
		
	}
	public Item(Product product, int quantity, long price) {

		this.product = product;
		this.quantity = quantity;
		this.price = price;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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
}
