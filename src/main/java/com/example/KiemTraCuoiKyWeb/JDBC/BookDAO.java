package com.example.KiemTraCuoiKyWeb.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.example.KiemTraCuoiKyWeb.Controller.Cart;
import com.example.KiemTraCuoiKyWeb.Controller.Category;
import com.example.KiemTraCuoiKyWeb.Controller.Comments;
import com.example.KiemTraCuoiKyWeb.Controller.Customer;
import com.example.KiemTraCuoiKyWeb.Controller.Issuer;
import com.example.KiemTraCuoiKyWeb.Controller.Item;
import com.example.KiemTraCuoiKyWeb.Controller.OrderManagement;
import com.example.KiemTraCuoiKyWeb.Controller.Product;





public class BookDAO {
	
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/book_online";
	private static final String JDBC_USERNAME = "root";
	private static final String JDBC_PASSWORD = "20052002";
	
	private static final String SELECT_LOGIN ="select *from user,roll_user where user_name =? and password=? and user.vai_tro = roll_user.id";
	private static final String SELECT_CATEGORY ="select *from category";
	private static final String SELECT_BOOK_BIGSALE="SELECT * FROM product,category,issuer where product.id_category = category.id and product.id_issuer = issuer.id ORDER BY so_luong_ban DESC LIMIT 12";
	private static final String SELECT_ALL_BOOK="SELECT * FROM product,category,issuer where product.id_category = category.id and product.id_issuer = issuer.id order by product.id";
	private static final String SELECT_ALL_ISSUER="SELECT *FROM ISSUER";
	private static final String SELECT_ALLL_INSERT_USER = "INSERT INTO USER(USER_NAME,USER,PASSWORD,EMAIL,DIA_CHI,VAI_TRO) VALUES (?,?,?,?,?,?)";
	private static final String SELECT_ALL_COMMENTS_BY_ID ="SELECT * FROM book_online.comments,user where id_user_comments = user.id and id_product_comment = ?";
	private static final String INSERT_COMMENT ="INSERT INTO comments(id_user_comments,comment, star,id_product_comment) VALUES(?,?,?,?)";
	private static final String Insert_Order = "INSERT INTO orders(totalmoney,date,uid) VALUES (?,?,?)";
	private static final String SELECT_ONE_BOOK_ID ="SELECT * FROM product,category,issuer where product.id = ? and product.id_category = category.id and product.id_issuer = issuer.id";
	private static final String SELECT_TOP_ONE_Order ="SELECT id FROM orders ORDER BY ID DESC LIMIT 1;";
	private static final String Insert_OrderDetail = "INSERT INTO orderdetail(quantity,price,oid,pid) VALUES (?,?,?,?)";
	private static final String add_subtrac_Product_Product ="UPDATE product SET so_luong_ban = ?, so_luong_sach = ? WHERE ID = ?";
	private static final String SELECT_ORDER_CUSTOMER ="select *from user as a, orders b, orderdetail as c, product d where a.user_name = ? and a.id = b.uid and b.id = c.oid and c.pid = d.id ";
	private static final String UPDATE_CUSTOMER_BY_ID ="UPDATE USER SET DIA_CHI = ?,EMAIL = ?, USER= ?,PASSWORD = ?,USER_NAME = ?,VAI_TRO = ? WHERE ID = ?";
	private static final String UPDATE_PRODUCT_BY_ID ="UPDATE PRODUCT SET ID_CATEGORY = ?, ID_ISSUER = ? , TIEU_DE =?, TAC_GIA = ? ,NGAY_PHAT_HANH = ?, SO_TRANG = ?, SO_LUONG_BAN = ?, SO_LUONG_SACH = ?, GIA =?, MO_TA =? WHERE ID = ?";
	private static final String INSERT_PRODUCT_BY_ID ="INSERT INTO PRODUCT(id_category,id_issuer, tieu_de, tac_gia, ngay_phat_hanh,so_trang,so_luong_ban,so_luong_sach,gia,mo_ta) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE_PRODUCT_BY_ID ="DELETE FROM Product WHERE id = ?";
	private static final String SEARCH_PRODUCT_BY_ID ="SELECT*FROM PRODUCT WHERE TIEU_DE = ? AND TAC_GIA = ?";
	private static final String DELETE_Comment_BY_ID ="delete from comments where id_product_comment = ?";
	private static final String SELECT_PRODUCT_TOP ="SELECT id FROM product ORDER BY id DESC LIMIT 1;";
	
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

	public BookDAO() {

	}
	
	
	
	public  Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
		return connection;
	}
	
	public Customer getLogin(String name,String password1) {
		Customer customer = new Customer();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_LOGIN);
			ps.setString(1,name);
			ps.setString(2, password1);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				int id = resultSet.getInt("user.id");
				String user_name = resultSet.getString("user_name");
				String user = resultSet.getString("user");
				String password = resultSet.getString("password");
				String email = resultSet.getString("email");
				String diachi = resultSet.getString("dia_chi");
				String vaitro = resultSet.getString("name_roll_user");
				customer = new Customer(id, user_name, user, password, email, diachi, vaitro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}
	
	public List<Category> getALLCategory() {
		List<Category> categorys = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_CATEGORY);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name_category");
				categorys.add(new Category(id,name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categorys;
	}
	
	public List<Product> getBigSale(){
		List<Product> products = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_BOOK_BIGSALE);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("product.id");
				String danh_muc = resultSet.getString("name_category");
				String nha_san_xuat = resultSet.getString("name_issuer");
				String tieu_de = resultSet.getString("tieu_de");
				String tac_gia = resultSet.getString("tac_gia");
				String ngay_phat_hanh = resultSet.getString("ngay_phat_hanh");
				int so_trang = resultSet.getInt("so_trang");
				int so_luong_ban = resultSet.getInt("so_luong_ban");
				int so_luong_sach = resultSet.getInt("so_luong_sach");
				long gia = resultSet.getLong("gia");
				String mo_ta = resultSet.getString("mo_ta");
				products.add(new Product(id, danh_muc, nha_san_xuat, tieu_de, tac_gia, ngay_phat_hanh, so_trang, so_luong_ban, so_luong_sach, gia, mo_ta));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public List<Product> getALLProduct(){
		List<Product> products = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_ALL_BOOK);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("product.id");
				String danh_muc = resultSet.getString("name_category");
				String nha_san_xuat = resultSet.getString("name_issuer");
				String tieu_de = resultSet.getString("tieu_de");
				String tac_gia = resultSet.getString("tac_gia");
				String ngay_phat_hanh = resultSet.getString("ngay_phat_hanh");
				int so_trang = resultSet.getInt("so_trang");
				int so_luong_ban = resultSet.getInt("so_luong_ban");
				int so_luong_sach = resultSet.getInt("so_luong_sach");
				long gia = resultSet.getLong("gia");
				String mo_ta = resultSet.getString("mo_ta");
				products.add(new Product(id, danh_muc, nha_san_xuat, tieu_de, tac_gia, ngay_phat_hanh, so_trang, so_luong_ban, so_luong_sach, gia, mo_ta));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public List<Issuer> getAllIssuer(){
		List<Issuer> issuers = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_ALL_ISSUER);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name_issuer = resultSet.getString("name_issuer");
				issuers.add(new Issuer(id, name_issuer));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return issuers;
	}
	
	public List<Product> getProduct(String sql){
		List<Product> products = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("product.id");
				String danh_muc = resultSet.getString("name_category");
				String nha_san_xuat = resultSet.getString("name_issuer");
				String tieu_de = resultSet.getString("tieu_de");
				String tac_gia = resultSet.getString("tac_gia");
				String ngay_phat_hanh = resultSet.getString("ngay_phat_hanh");
				int so_trang = resultSet.getInt("so_trang");
				int so_luong_ban = resultSet.getInt("so_luong_ban");
				int so_luong_sach = resultSet.getInt("so_luong_sach");
				long gia = resultSet.getLong("gia");
				String mo_ta = resultSet.getString("mo_ta");
				products.add(new Product(id, danh_muc, nha_san_xuat, tieu_de, tac_gia, ngay_phat_hanh, so_trang, so_luong_ban, so_luong_sach, gia, mo_ta));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public void InsertUser(Customer customer) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_ALLL_INSERT_USER);
			ps.setString(1, customer.getUser_name());
			ps.setString(2, customer.getUser());
			ps.setString(3, customer.getPassword());
			ps.setString(4, customer.getEmail());
			ps.setString(5, customer.getDiachi());
			ps.setInt(6, 2);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Product getOneProduct(int id) {
		Product product = new Product();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_ONE_BOOK_ID);
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				String danh_muc = resultSet.getString("name_category");
				String nha_san_xuat = resultSet.getString("name_issuer");
				String tieu_de = resultSet.getString("tieu_de");
				String tac_gia = resultSet.getString("tac_gia");
				String ngay_phat_hanh = resultSet.getString("ngay_phat_hanh");
				int so_trang = resultSet.getInt("so_trang");
				int so_luong_ban = resultSet.getInt("so_luong_ban");
				int so_luong_sach = resultSet.getInt("so_luong_sach");
				long gia = resultSet.getLong("gia");
				String mo_ta = resultSet.getString("mo_ta");
				product = new Product(id, danh_muc, nha_san_xuat, tieu_de, tac_gia, ngay_phat_hanh, so_trang, so_luong_ban, so_luong_sach, gia, mo_ta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}
	
	public List<Comments> getComments_ID(int id){
		List<Comments> comments = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_ALL_COMMENTS_BY_ID);
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				int id_user_comments = resultSet.getInt("id_user_comments");
				String  comment = resultSet.getString("comment");
				int star = resultSet.getInt("star");
				int id_product_comment =  resultSet.getInt("id_product_comment");
				String user = resultSet.getString("user");
				comments.add(new Comments(id, id_user_comments, star, id_product_comment, comment,user));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	public void insertComment(int star, int id_user_comments, int id_product_comment,String comment) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(INSERT_COMMENT);
			ps.setInt(1, id_user_comments);
			ps.setString(2, comment);
			ps.setInt(3, star);
			ps.setInt(4, id_product_comment);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addOrder(Customer u, Cart cart) {
		LocalDate curDate =java.time.LocalDate.now();
		String date = curDate.toString();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(Insert_Order);
			ps.setLong(1, parseMoney(cart.getTotalMoney()));
			ps.setString(2, date);
			ps.setInt(3, u.getId());
			ps.executeUpdate();
			PreparedStatement ps1 = con.prepareStatement(SELECT_TOP_ONE_Order);
			ResultSet resultSet = ps1.executeQuery();
			if(resultSet.next()) {
				int oid =resultSet.getInt("id");
				for(Item i : cart.getItems()) {
					PreparedStatement ps3 = con.prepareStatement(Insert_OrderDetail);
					ps3.setInt(1,i.getQuantity());
					ps3.setLong(2,i.getPrice());
					ps3.setInt(3,oid);
					ps3.setInt(4, i.getProduct().getId());
					ps3.executeUpdate();
					ps3.close();
					int don_vi_kho = i.getProduct().getSo_luong_sach()-i.getQuantity();
					int don_vi_ban = i.getProduct().getSo_luong_ban()+i.getQuantity();
					PreparedStatement ps4 = con.prepareStatement(add_subtrac_Product_Product);
					ps4.setInt(1, don_vi_ban);
					ps4.setInt(2, don_vi_kho);
					ps4.setInt(3, i.getProduct().getId());
					ps4.executeUpdate();
					ps4.close();
				}
			}
			ps.close();
			ps1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<OrderManagement> getALLDonHangCustomer(String phone) {
		List<OrderManagement> orderManagements = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_ORDER_CUSTOMER);
			ps.setString(1, phone);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				OrderManagement orderManagement = new OrderManagement();
				orderManagement.getOrderDetail().setId(resultSet.getInt("c.id"));
				orderManagement.getProduct().setTieu_de("tieu_de");
				orderManagement.getCustomer().setUser_name("user_name");
				orderManagement.getCustomer().setUser("user");
				orderManagement.getOrderDetail().setPid(resultSet.getInt("pid"));
				orderManagement.getOrderDetail().setCid(resultSet.getInt("oid"));
				orderManagement.getOrderDetail().setQuantity(resultSet.getInt("quantity"));
				orderManagement.getOrderDetail().setPrice(resultSet.getLong("price"));
				orderManagement.getOrder().setDate(resultSet.getString("date"));
				orderManagements.add(orderManagement);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderManagements;
	}
	
	public void deleteOrderManagement(int id) {
		try {
			int oid = 0;
			int pid = 0;
			int quantity = 0;
			Connection con = getConnection();
			PreparedStatement ps1 = con.prepareStatement("select * from orderdetail where id = ?");
			ps1.setInt(1, id);
			ResultSet resultSet = ps1.executeQuery();
			if(resultSet.next()) {
				oid = resultSet.getInt("oid");
				pid = resultSet.getInt("pid");
				quantity = resultSet.getInt("quantity");
			}
			ps1.close();
			PreparedStatement ps2 = con.prepareStatement("select count(*) as soluong from orderdetail where oid = ? group by oid");
			ps2.setInt(1, oid);
			resultSet = ps2.executeQuery();
			int count = 0;
			if(resultSet.next()) {
				count = resultSet.getInt("soluong");
			}
			ps2.close();
			if(count >= 2) {
				PreparedStatement ps3 = con.prepareStatement("delete from orderdetail where id = ?");
				ps3.setInt(1, id);
				ps3.executeUpdate();
				ps3.close();
			} else {
				PreparedStatement ps3 = con.prepareStatement("delete from orderdetail where id = ?");
				ps3.setInt(1, id);
				ps3.executeUpdate();
				ps3.close();
				PreparedStatement ps4 = con.prepareStatement("delete from orders where id = ?");
				ps4.setInt(1, oid);
				ps4.executeUpdate();
				ps4.close();
			}
			Product product = getOneProduct(pid);
			int don_vi_ban = product.getSo_luong_ban()-quantity;
			int don_vi_kho = product.getSo_luong_sach() + quantity;
			PreparedStatement ps3 = con.prepareStatement(add_subtrac_Product_Product);
			ps3.setInt(1, don_vi_ban);
			ps3.setInt(2, don_vi_kho);
			ps3.setInt(3, pid);
			ps3.executeUpdate();
			ps3.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateCustomer(Customer customer, String key) throws SQLException{
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(UPDATE_CUSTOMER_BY_ID);
			ps.setString(1, customer.getDiachi());
			ps.setString(2,customer.getEmail());
			ps.setString(3, customer.getUser());
			ps.setString(4, customer.getPassword());
			ps.setString(5, customer.getUser_name());
			ps.setString(6, "2");
			ps.setString(7, key);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Issuer> getALLIssuer() {
		List<Issuer> issuers = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_ALL_ISSUER);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name_issuer");
				issuers.add(new Issuer(id,name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return issuers;
	}
	
	public void updateProduct(Product product) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(UPDATE_PRODUCT_BY_ID);
			if(product.getDanh_muc().length() > 2) {
				List<Category> categories = getALLCategory();
				for (Category i : categories) {
					if(i.getName().equals(product.getDanh_muc())) {
						product.setDanh_muc(String.valueOf(i.getId()));
					}
				}
			}
			if(product.getNha_xuat_ban().length() > 2) {
				List<Issuer> issuers = getAllIssuer();
				for (Issuer i : issuers) {
					if(i.getName_issuer().equals(product.getNha_xuat_ban())) {
						product.setNha_xuat_ban(String.valueOf(i.getId()));
					}
				}
			}
			ps.setString(1,product.getDanh_muc());
			ps.setString(2,product.getNha_xuat_ban());
			ps.setString(3,product.getTieu_de());
			ps.setString(4,product.getTac_gia());
			ps.setString(5,product.getNgay_phat_hanh());
			ps.setInt(6, product.getSo_trang());
			ps.setInt(7, product.getSo_luong_ban());
			ps.setInt(8, product.getSo_luong_sach());
			ps.setLong(9,parseMoney(product.getGia()));
			ps.setString(10,product.getMo_ta());
			ps.setInt(11, product.getId());
			List<Category> categories = getALLCategory();
			for (Category i : categories) {
				if(String.valueOf(i.getId()).equals(product.getDanh_muc())) {
					product.setDanh_muc(String.valueOf(i.getName()));
				}
			}
			
			List<Issuer> issuers = getAllIssuer();
			for (Issuer i : issuers) {
				if(String.valueOf(i.getId()).equals(product.getNha_xuat_ban())) {
					product.setNha_xuat_ban(String.valueOf(i.getName_issuer()));
				}
			}
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBook(String id) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(DELETE_PRODUCT_BY_ID);
			PreparedStatement ps1 = con.prepareStatement(DELETE_Comment_BY_ID);
			ps1.setString(1, id);
			ps1.execute();
			ps.setString(1, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertBook(Product product) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(INSERT_PRODUCT_BY_ID);
			ps.setString(1, product.getDanh_muc());
			ps.setString(2, product.getNha_xuat_ban());
			ps.setString(3, product.getTieu_de());
			ps.setString(4, product.getTac_gia());
			ps.setString(5, product.getNgay_phat_hanh());
			ps.setInt(6, product.getSo_trang());
			ps.setInt(7, product.getSo_luong_ban());
			ps.setInt(8, product.getSo_luong_sach());
			ps.setLong(9, parseMoney(product.getGia()));
			ps.setString(10,product.getMo_ta());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkNgoaiLe(String tieu_de, String tac_gia) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SEARCH_PRODUCT_BY_ID);
			ps.setString(1, tieu_de);
			ps.setString(2, tac_gia);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int getProductTop() {
		int i = 0;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_PRODUCT_TOP);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				i = resultSet.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public boolean checkUserName(String uername) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT*FROM USER WHERE USER_NAME =?");
			ps.setString(1, uername);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
