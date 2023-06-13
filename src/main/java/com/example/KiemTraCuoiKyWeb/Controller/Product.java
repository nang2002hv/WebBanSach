package com.example.KiemTraCuoiKyWeb.Controller;

public class Product {
	private int id, so_trang, so_luong_ban, so_luong_sach;
	private long gia;
	private String tieu_de, danh_muc, nha_xuat_ban, tac_gia, ngay_phat_hanh, mo_ta;

	public Product() {

	}

	public Product(int id, String danh_muc, String nha_xuat_ban, String tieu_de,  String tac_gia, String ngay_phat_hanh,
			int so_trang, int so_luong_ban, int so_luong_sach, long gia, String mo_ta) {
		this.id = id;
		this.tieu_de = tieu_de;
		this.danh_muc = danh_muc;
		this.nha_xuat_ban = nha_xuat_ban;
		this.tac_gia = tac_gia;
		this.ngay_phat_hanh = ngay_phat_hanh;
		this.so_trang = so_trang;
		this.so_luong_ban = so_luong_ban;
		this.so_luong_sach = so_luong_sach;
		this.gia = gia;
		this.mo_ta = mo_ta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSo_trang() {
		return so_trang;
	}

	public void setSo_trang(int so_trang) {
		this.so_trang = so_trang;
	}

	public int getSo_luong_ban() {
		return so_luong_ban;
	}

	public void setSo_luong_ban(int so_luong_ban) {
		this.so_luong_ban = so_luong_ban;
	}

	public int getSo_luong_sach() {
		return so_luong_sach;
	}

	public void setSo_luong_sach(int so_luong_sach) {
		this.so_luong_sach = so_luong_sach;
	}

	public String getGia() {
		return formatMoney(gia);
	}

	public void setGia(long gia) {
		this.gia = gia;
	}

	public String getTieu_de() {
		return tieu_de;
	}

	public void setTieu_de(String tieu_de) {
		this.tieu_de = tieu_de;
	}

	public String getDanh_muc() {
		return danh_muc;
	}

	public void setDanh_muc(String danh_muc) {
		this.danh_muc = danh_muc;
	}

	public String getNha_xuat_ban() {
		return nha_xuat_ban;
	}

	public void setNha_xuat_ban(String nha_xuat_ban) {
		this.nha_xuat_ban = nha_xuat_ban;
	}

	public String getTac_gia() {
		return tac_gia;
	}

	public void setTac_gia(String tac_gia) {
		this.tac_gia = tac_gia;
	}

	public String getNgay_phat_hanh() {
		return ngay_phat_hanh;
	}

	public void setNgay_phat_hanh(String ngay_phat_hanh) {
		this.ngay_phat_hanh = ngay_phat_hanh;
	}

	public String getMo_ta() {
		return mo_ta;
	}

	public void setMo_ta(String mo_ta) {
		this.mo_ta = mo_ta;
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
	
	//Chuyển đổi định dạng tiền ngược lại
	public static long parseMoney(String formattedMoney) {
	    String cleanMoney = formattedMoney.replace(" đ", "").replace(".", "");

	    try {
	        return Long.parseLong(cleanMoney);
	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("Invalid formatted money: " + formattedMoney);
	    }
	}

}
