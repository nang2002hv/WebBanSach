package com.example.KiemTraCuoiKyWeb.Controller;

import java.util.ArrayList;
import java.util.List;


public class Cart {
	private List<Item> items;

	public Cart() {
		items = new ArrayList<>();
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public Item getItemById(int id) {
		for(Item i: items) {
			if(i.getProduct().getId() == id) {
				return i;
			}
		}
		return null;
	}
	
	public int getQuantityByID(int id) {
		return getItemById(id).getQuantity();
	}
	
	//them vao gio
	public void addItem (Item t) {
		//co trong gio
		if(getItemById(t.getProduct().getId()) != null) {
			Item i = getItemById(t.getProduct().getId());
			i.setQuantity(i.getQuantity() + t.getQuantity());
			
		} else {
			//chua co
			items.add(t);
		}
	}
	//bo di 1 san pham
	public void removeItem(int id) {
		if(getItemById(id) != null) {
			items.remove(getItemById(id));
		}
	}
	
	public String getTotalMoney() {
		long t = 0;
		for(Item i : items)
			t += i.getQuantity()*i.getPrice();
		return formatMoney(t);
	}
	
	public Item update(int idPro, int qty) {
		Item i = getItemById(idPro);
		i.setQuantity(qty);
		return i;
	}
	
	public static String formatMoney(long money) {
	    String moneyString = String.valueOf(money);
	    int moneyLength = moneyString.length();
	    StringBuilder formattedMoney = new StringBuilder();
	    
	    int count = 0;
	    for (int i = moneyLength - 1; i >= 0; i--) {
	        formattedMoney.append(moneyString.charAt(i));
	        count++;
	        if (count == 3 && i != 0) {
	            formattedMoney.append('.');
	            count = 0;
	        }
	    }
	    
	    return formattedMoney.reverse().toString() + " đ";
	}
	
	public void clear() {
		items.clear();
	}
	
	public int getCount() {
		return items.size();
	} 
}
