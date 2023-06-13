package com.example.KiemTraCuoiKyWeb.Controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.KiemTraCuoiKyWeb.JDBC.BookDAO;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BookControoler {
	static int ktlogin = 0;
	
	BookDAO bookDAO = new BookDAO();
	
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
	
	@GetMapping("/client")
	public String getHello(Model model, HttpSession session) {
		List<Category> categoryes = bookDAO.getALLCategory();
		List<Product> products = bookDAO.getBigSale();
		session.setAttribute("categorys", categoryes);
		model.addAttribute("products",products);
		model.addAttribute("ktlogin",ktlogin);
		return "customer/trangchu";
	}
	
	@GetMapping("/sach_online")
	public String laptopHSX(Model model, HttpServletRequest request) throws IOException {
		model.addAttribute("ktlogin",ktlogin);
		String priceSet = "Tất cả";
		String brandSet = "Tất cả";
		String brand = request.getParameter("brand");
		String page = request.getParameter("page");
		String search = request.getParameter("search");
		List<Product> list = new ArrayList<>();
		String sql = "SELECT * FROM product,category,issuer where product.id_category = category.id and product.id_issuer = issuer.id";
		if (search != "" && search != null) {
			sql += " and tieu_de like " + "'%" + search + "%'";
		}
		if (brand != null && brand != "") {
			brandSet = brand;
			sql += " and name_category =" + "'" + brand + "'";
		} else {
			brandSet = "Tất cả";
		}
		if (page != null && page != "") {
			if (page.equals("5-10")) {
				priceSet = "50 trang - 100 trang";
			}
			if (page.equals("10-20")) {
				priceSet = "100 trang - 200 trang";
			}
			if (page.equals("20-30")) {
				priceSet = "200 trang - 300 trang";
			}
			if (page.equals("30-50")) {
				priceSet = "300 trang - 500 trang";
			}
			if (page.equals("over-50")) {
				priceSet = "Trên 500 trang";
			}
			String[] parts = page.split("-");
			String part1 = parts[0];
			String part2 = parts[1];
			if (part1.equals("over")) {
				sql += " and so_trang >= " + 500;
			} else {
				int min = Integer.valueOf(part1) * 10;
				int max = Integer.valueOf(part2) * 10;
				sql += " and so_trang >= " + min + " and so_trang <= " + max;
			}
		} else {
			priceSet = "Tất Cả";
		}
		System.out.println(sql);
		try {
			list = bookDAO.getProduct(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("brand", brand);
		model.addAttribute("page", page);
		model.addAttribute("brandSet", brandSet);
		model.addAttribute("pageSet", priceSet);
		model.addAttribute("products", list);
		model.addAttribute("isLogin", 2);
		return "customer/trangchu";
	}
	
	@GetMapping("/severlogin")
	public String getLogin(Model model) {
		model.addAttribute("ktlogin",ktlogin);
		model.addAttribute("isLogin",1);
		return "customer/trangchu";
	}
	
	@PostMapping("/ktlogin")
	public String getKTLogin(Model model, HttpServletRequest request, HttpSession session) {
		
		String name = request.getParameter("user_name");
		String password = request.getParameter("password");
		Customer customer = bookDAO.getLogin(name, password);
		
		if(customer != null) {
			if (customer.getVaitro().equals("ROLL_ADMIN")) {
				List<Product> products = bookDAO.getALLProduct();
				List<Category> categoryes = bookDAO.getALLCategory();
				List<Issuer> issuers = bookDAO.getAllIssuer();
				session.setAttribute("issuers", issuers);
				session.setAttribute("categorys", categoryes);
				model.addAttribute("products",products);
				return "admin/trangchu";
			} else {
				if(customer.getVaitro().equals("ROLL_MEMBER")) {
					ktlogin = 1;
					model.addAttribute("ktlogin",1);
					session.setAttribute("customer_login",customer);
					return "redirect:/sach_online";
				}
			}
		}
		return "redirect:/severlogin";
	}
	
	@GetMapping("/register")
	public String getRegister(Model model) {
		Loi loi = new Loi();
		model.addAttribute("loi",loi);
		model.addAttribute("ktlogin",ktlogin);
		model.addAttribute("isLogin",0);
		return "customer/trangchu";
	}
	
	@PostMapping("/ktregister")
	public String ktregister(Model model, Customer customer) {
		Loi loi = new Loi();
		if(customer.getUser_name().length() == 0) {
			loi.setError_user_name("Khong được để trống tên tài khoản");
			model.addAttribute("loi",loi);
			model.addAttribute("ktlogin",ktlogin);
			model.addAttribute("isLogin",0);
			return "customer/trangchu";
		}
		if(bookDAO.checkUserName(customer.getUser_name())) {
			loi.setError_user_name("Tài khoản đã có");
			model.addAttribute("loi",loi);
			model.addAttribute("ktlogin",ktlogin);
			model.addAttribute("isLogin",0);
			return "customer/trangchu";
		}
		bookDAO.InsertUser(customer);
		return "redirect:/severlogin";
	}
	
	@GetMapping("/checkout")
	public String checkout(Model model, HttpSession session) {
		session.removeAttribute("customer_login");
		ktlogin= 0;
		return "redirect:/severlogin";
	}
	
	
	
	@GetMapping("/shopping_cart")
	public String viewCart(Model model,HttpSession session,HttpServletRequest request) {
		Cart cart = null;
		Object o = session.getAttribute("cart");
		//có rồi
		if(o != null) {
			cart = (Cart)o;
		} else {
			cart = 	new Cart();
		}
		int num, id;
		try {
			num = 1;
			id= Integer.valueOf(request.getParameter("id"));
			System.out.println(id);
			Product product = bookDAO.getOneProduct(id);
			long price = parseMoney(product.getGia());
			
			Item t= new Item(product,num,price);
			cart.addItem(t);
			
		} catch (Exception e) {
			num = 1;
		}
		List<Item> list = cart.getItems();
		session.setAttribute("cart", cart);
		session.setAttribute("size", list.size());
		
		model.addAttribute("ktlogin", ktlogin);
		model.addAttribute("isLogin",4);
		return "customer/trangchu";
	}
	
	@GetMapping("/sach_detail") 
	public String sachDetail(HttpServletRequest request, Model model) {
		int id = Integer.valueOf(request.getParameter("id"));
		Product product = bookDAO.getOneProduct(id);
		List<Comments> comments = bookDAO.getComments_ID(id);
		model.addAttribute("product", product);
		model.addAttribute("comments", comments);
		model.addAttribute("ktlogin",ktlogin);
		model.addAttribute("isLogin",3);
		return "customer/trangchu";
		
		
	}
	
	@GetMapping("/process")
	public String viewCartprocess_1(Model model, HttpSession session, HttpServletRequest request) {
		Cart cart = null;
		Object o = session.getAttribute("cart");
		// có rồi
		if (o != null) {
			cart = (Cart) o;
		} else {
			cart = new Cart();
		}
		int num, id;
		try {
			num = Integer.valueOf(request.getParameter("num"));
			id = Integer.valueOf(request.getParameter("id"));
			if (num == -1 && (cart.getQuantityByID(id) <= 1)) {
				cart.removeItem(id);
			} else {
				Product product = bookDAO.getOneProduct(id);
				long price = parseMoney(product.getGia());
				Item t = new Item(product, num, price);
				cart.addItem(t);
			}
		} catch (Exception e) {
			num = 1;
		}
		List<Item> list = cart.getItems();
		session.setAttribute("cart", cart);
		session.setAttribute("size", list.size());
		
		model.addAttribute("ktlogin", ktlogin);
		model.addAttribute("isLogin", 4);
		return "customer/trangchu";
	}
	
	@PostMapping("/process1")
	public String viewCartprocess_2(Model model, HttpSession session, HttpServletRequest request) {
		Cart cart = null;
		System.out.println("hello");
		Object o = session.getAttribute("cart");
		// có rồi
		if (o != null) {
			cart = (Cart) o;
		} else {
			cart = new Cart();
		}
		int id = Integer.valueOf(request.getParameter("id"));
		cart.removeItem(id);
		List<Item> list = cart.getItems();
		session.setAttribute("cart", cart);
		session.setAttribute("size", list.size());
		
		model.addAttribute("ktlogin", ktlogin);
		model.addAttribute("isLogin", 4);
		return "customer/trangchu";
	}
	
	@GetMapping("/comments")
	public String Comments(Model model, HttpServletRequest request) {
		if(ktlogin != 0) {
			String comment_user = request.getParameter("comment");
			int star, id_user_comments, id_product_comment;
			id_user_comments = Integer.valueOf(request.getParameter("id_user_comments"));
			id_product_comment = Integer.valueOf(request.getParameter("id_product_comment"));
			try {
				star = Integer.valueOf(request.getParameter("star"));
				
			} catch (Exception e) {
				star = 1;
			}
			bookDAO.insertComment(star, id_user_comments, id_product_comment, comment_user);
			String redirectUrl = "redirect:/sach_detail?id=" + id_product_comment;
	        return redirectUrl;
		} 
		return "redirect:/severlogin";
	}
	
	@PostMapping("/thanhtoan")
	public String thanhtoan(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Cart cart = null;
		Object o = session.getAttribute("cart");
		if(o != null) {
			cart = (Cart)o;
		} else {
			cart = new Cart();
		}
		Customer acount = null;
		Object a = session.getAttribute("customer_login");
		if(a != null) {
			acount =(Customer)a;
			bookDAO.addOrder(acount, cart);
			session.removeAttribute("cart");
			session.removeAttribute("size");
		} else {
			return "redirect:/severlogin";
		}
		return "redirect:/client";
	}
	
	@GetMapping("/information")
	public String information (Model model, HttpSession session) {
		Customer customer = (Customer)session.getAttribute("customer_login");
		List<OrderManagement> orderManagements = bookDAO.getALLDonHangCustomer(customer.getUser_name());
		model.addAttribute("orderManagements", orderManagements);
		model.addAttribute("ktlogin", ktlogin);
		model.addAttribute("isLogin",5);
		return "customer/trangchu";
	}
	
	@GetMapping("/deleteordermanagement1")
	public String getDeleteOrderMAnagement1(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		bookDAO.deleteOrderManagement(Integer.valueOf(id));
		return "redirect:/information";	
	}
	
	@PostMapping("/getinformation")
	public String getinformation(Model model, Customer customer,HttpSession session) throws SQLException {
		Customer customer2 = (Customer)session.getAttribute("customer_login");
		customer.setVaitro(customer2.getVaitro());
		customer.setUser_name(customer2.getUser_name());
		customer.setId(customer2.getId());
		bookDAO.updateCustomer(customer, String.valueOf(customer2.getId()));
		session.setAttribute("customer_login", customer);
		return "redirect:/information";
	}
	
	@GetMapping("oneproduct")
	public String Oneproduct(Model model,HttpServletRequest request, Product product) {
		int id = Integer.valueOf(request.getParameter("id"));
		Product product1 = bookDAO.getOneProduct(id);
		model.addAttribute("product",product1);
		model.addAttribute("condition",0);
		model.addAttribute("kt",1);
		model.addAttribute("isadmin",2);
		return "admin/trangchu";
	}
	
	@GetMapping("/qlsp")
	public String qlsp(Model model,HttpServletRequest request) throws SQLException {
		List<Product> list = bookDAO.getALLProduct();
		model.addAttribute("isadmin", -1);
		model.addAttribute("products", list);
		return "admin/trangchu";
	}
	
	@GetMapping("/edit")
	public String editProduct(Model model, HttpServletRequest request) {
		int id = Integer.valueOf(request.getParameter("id"));
		Product product = bookDAO.getOneProduct(id);
		model.addAttribute("condition",1);
		model.addAttribute("kt",0);
		model.addAttribute("product",product);
		model.addAttribute("isadmin",2);
		return "admin/trangchu";
	}
	
	@PostMapping("oneproduct")
	public String Updateproduct(Model model,HttpServletRequest request) {
		int id = Integer.valueOf(request.getParameter("id"));
		String tieu_de = request.getParameter("tieu_de");
		String tac_gia = request.getParameter("tac_gia");
		String danh_muc = request.getParameter("category");
		String nha_xuat_ban = request.getParameter("issuer");
		String ngay_phat_hanh = request.getParameter("date");
		int so_trang = Integer.valueOf(request.getParameter("so_trang"));
		String mo_ta = request.getParameter("mo_ta");
		int so_luong_ban = Integer.valueOf(request.getParameter("so_luong_ban"));
		int so_luong_sach = Integer.valueOf(request.getParameter("so_luong_sach"));
		long gia = Long.valueOf(parseMoney(request.getParameter("gia"))); 
		Product product = new Product(id, danh_muc, nha_xuat_ban, tieu_de, tac_gia, ngay_phat_hanh, so_trang, so_luong_ban, so_luong_sach, gia, mo_ta);
		bookDAO.updateProduct(product);
		model.addAttribute("product",product);
		model.addAttribute("condition",0);
		model.addAttribute("kt",1);
		model.addAttribute("isadmin",2);
		return "admin/trangchu";
	}
	
	@GetMapping("/productnew")
	public String addBook(Model model) {
		Product product = new Product();
		Loi error = new Loi();
		model.addAttribute("loi",error);
		model.addAttribute("product",product);
		model.addAttribute("isadmin",1);
		return "admin/trangchu";
	}
	
	@GetMapping("/deleteproduct1")
	public String Deleteproduct(Model model,HttpServletRequest request, Product product) {
		int id = Integer.valueOf(request.getParameter("id"));
		Product product1 = bookDAO.getOneProduct(id);
		model.addAttribute("product",product1);
		model.addAttribute("condition",0);
		model.addAttribute("kt",1);
		model.addAttribute("isadmin",3);
		return "admin/trangchu";
	}
	
	@GetMapping("/deleteproduct")
	public String deleteBook(Model model,HttpServletRequest request) {
		String id = request.getParameter("id");
		bookDAO.deleteBook(id);
		List<Product> list = bookDAO.getALLProduct();
		model.addAttribute("isadmin", -1);
		model.addAttribute("products", list);
		return "admin/trangchu";
	}
	
	
	@PostMapping("/qlsp")
	public String qqlsp(Model model,HttpServletRequest request,@RequestParam("productImage") MultipartFile file) throws SQLException {
		int id = Integer.valueOf(request.getParameter("id"));
		String tieu_de = request.getParameter("tieu_de");
		String tac_gia = request.getParameter("tac_gia");
		String danh_muc = request.getParameter("danh_muc");
		String nha_xuat_ban = request.getParameter("nha_xuat_ban");
		String ngay_phat_hanh = request.getParameter("ngay_phat_hanh");
		int so_trang = Integer.valueOf(request.getParameter("so_trang"));
		String mo_ta = request.getParameter("mo_ta");
		int so_luong_ban = Integer.valueOf(request.getParameter("so_luong_ban"));
		int so_luong_sach = Integer.valueOf(request.getParameter("so_luong_sach"));
		long gia = Long.valueOf(parseMoney(request.getParameter("gia"))); 
		System.out.println(tieu_de.length() + tac_gia.length());
		Loi loi = new Loi();
		Product product = new Product(id, danh_muc, nha_xuat_ban, tieu_de, tac_gia, ngay_phat_hanh, so_trang, so_luong_ban, so_luong_sach, gia, mo_ta);
		if(tieu_de.length() == 0 || tac_gia.length() == 0 || ngay_phat_hanh.length() == 0) {
			if(tieu_de.length() == 0) {
				loi.setError_tieu_de("Vui lòng không được trống tiêu đề");
			}
			if(tac_gia.length() == 0) {
				loi.setError_tac_gia("Vui lòng không được trống tác giả");
			}
			if(ngay_phat_hanh.length() == 0) {
				loi.setError_ngay_xuat_ban("vui lòng không bỏ trống ngày phát hành");
			}
			model.addAttribute("loi",loi);
			model.addAttribute("product",product);
			model.addAttribute("isadmin",1);
			return "admin/trangchu";
		}
		if(bookDAO.checkNgoaiLe(tieu_de, tac_gia)){
			loi.setError_trung_lap("Tác phẩm đã có vui lòng nhập lại");
			model.addAttribute("loi",loi);
			model.addAttribute("product",product);
			model.addAttribute("isadmin",1);
			return "admin/trangchu";
		}
		bookDAO.insertBook(product);
		int n = bookDAO.getProductTop();
		if (!file.isEmpty()) {
	        try {  
	            String originalFileName = String.valueOf(n)+"a.png";            
	            String fileName = "E:/KiemTraCuoiKyWeb/KiemTraCuoiKyWeb/src/main/resources/static/img/" + originalFileName;
	            file.transferTo(new File(fileName));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		String url = "redirect:/oneproduct?id=" + String.valueOf(n);
		return url;
	}
}
