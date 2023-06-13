package com.example.KiemTraCuoiKyWeb.Controller;


public class Loi {
	private String error_tieu_de, error_tac_gia,error_ngay_xuat_ban,error_user_name,error_trung_lap;

	public Loi() {
		error_user_name = error_tieu_de = error_tac_gia = error_trung_lap = error_user_name= error_ngay_xuat_ban ="";
	}

	public Loi(String error_tieu_de, String error_tac_gia, String error_ngay_xuat_ban,String error_user_name,String error_trung_lap) {
		this.error_user_name = error_user_name;
		this.error_tieu_de = error_tieu_de;
		this.error_tac_gia = error_tac_gia;
		this.error_ngay_xuat_ban = error_ngay_xuat_ban;
		this.error_trung_lap = error_trung_lap;
	}

	public String getError_tieu_de() {
		return error_tieu_de;
	}

	public void setError_tieu_de(String error_tieu_de) {
		this.error_tieu_de = error_tieu_de;
	}

	public String getError_tac_gia() {
		return error_tac_gia;
	}

	public void setError_tac_gia(String error_tac_gia) {
		this.error_tac_gia = error_tac_gia;
	}

	public String getError_ngay_xuat_ban() {
		return error_ngay_xuat_ban;
	}

	public void setError_ngay_xuat_ban(String error_ngay_xuat_ban) {
		this.error_ngay_xuat_ban = error_ngay_xuat_ban;
	}

	public String getError_user_name() {
		return error_user_name;
	}

	public void setError_user_name(String error_user_name) {
		this.error_user_name = error_user_name;
	}

	public String getError_trung_lap() {
		return error_trung_lap;
	}

	public void setError_trung_lap(String error_trung_lap) {
		this.error_trung_lap = error_trung_lap;
	}
	
	
	
}
