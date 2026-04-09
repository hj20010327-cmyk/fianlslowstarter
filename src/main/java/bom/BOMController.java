package bom;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class bomcontroller
 */
@WebServlet("/BOM")
public class BOMController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/BOM doGet 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		// 페이징 
		int size = 5; 
		int page = 1; 
		
		String sSize = request.getParameter("size");
		String sPage = request.getParameter("page");
		
		
	    try {
	        size = Integer.parseInt(sSize);
	    } catch (Exception e) {}
		
	    try {
	        page = Integer.parseInt(sPage);
	    } catch (Exception e) {}
		
		
		BOMDTO bomDTO = new BOMDTO(); 
		bomDTO.setSize(size);
		bomDTO.setPage(page);
		
		
//		String cmd = request.getParameter("cmd");
//		
//		if(cmd.equals("list")) {
//			list(request, response);
//		} 
//		else if(cmd.equals("detail")) {
//			detail(request, response);
//		}
		
		// 서비스 연결 
		BOMService bomservice = new BOMService();
		
		Map<String, Object> map = bomservice.getPaging(bomDTO);
		map.put("size", size);
		map.put("page", page);
		
		request.setAttribute("map",map);
		request.setAttribute("list",map.get("list"));
		
		RequestDispatcher rd = request.getRequestDispatcher("/bom.jsp");
		rd.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/BOM doPost 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		// form 요소에 반영 
		String cmd = request.getParameter("cmd");
		
//		if(cmd.equals("insert")) {
//			insert(request, response);
//		} else if(cmd.equals("update")) {
//			update(request, response); 
//		} else if(cmd.equals("delete")) {
//			delete(request, response);
//		}
	}
	
//	protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		System.out.println("/BOM insert 실행");
//		
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=utf-8");
//		
//		// 파라미터 확보 
//		String bom_key = request.getParameter("bom_key");
//		String item_code = request.getParameter("item_code");
//		String itemCount = request.getParameter("item_count");
//		int item_count = Integer.parseInt(itemCount);
//		String Status = request.getParameter("status");
//		int status = Integer.parseInt(Status);
//		String code_id = request.getParameter("code_id");
//		
//		// DTO에 넣기 
//		BOMDTO bomDTO = new BOMDTO(); 
//		bomDTO.setBOM_key(bom_key);
//		bomDTO.setItem_code(item_code);
//		bomDTO.setItem_count(item_count);
//		bomDTO.setStatus(status);
//		bomDTO.setCode_id(code_id);
//		
//		// service로 DTO를 보냄 
//		BOMService bomservice = new BOMService(); 
//		int result = bomservice.insert(bomDTO);
//		
//		System.out.println("insert된 rows: " + result);
//		
//		response.sendRedirect("");
//		
//	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/BOM update 실행");
		
		// 파라미터 확보 
		String Bom_key = request.getParameter("bom_key");
		int bom_key = Integer.parseInt(Bom_key);
		String product_item_key = request.getParameter("product_item_key");
		String material_item_key = request.getParameter("material_item_key");
		String QTY = request.getParameter("QTY");
		int qty = Integer.parseInt(QTY);
		String remark = request.getParameter("remark");
		
		try {
			BOMDTO bomDTO = new BOMDTO();
			
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			bomDTO.setBom_key(bom_key);
			bomDTO.setProduct_item_key(product_item_key);
			bomDTO.setMaterial_item_key(material_item_key);
			bomDTO.setQTY(qty);
			bomDTO.setRemark(remark);
			
			BOMService bomservice = new BOMService(); 
			int result = bomservice.update(bomDTO);
			
			System.out.println("update 결과 : " + result);
			
			response.sendRedirect("");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/BOM delete 실행");
		
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			BOMDTO bomDTO = new BOMDTO(); 
			int bom_key = Integer.parseInt(request.getParameter("bom_key"));
			bomDTO.setBom_key(bom_key); // 확인 
			
			System.out.println("bom_key : " + bom_key);
			
			BOMService bomservice = new BOMService(); 
			int result = bomservice.delete(bomDTO);
			
			response.sendRedirect("");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
//	public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		System.out.println("/BOM detail 실행");
//		
//		
//		try {
//			request.setCharacterEncoding("utf-8");
//			response.setContentType("text/html; charset=utf-8");
//			
//			String bom_key = request.getParameter("bom_key");
//			System.out.println("bom_key: " + bom_key);
//			
//			// bom_key 를 service 랑 DAO 보내기 
//			BOMService bomservice = new BOMService(); 
//			BOMDTO bomDTO = bomservice.getBOM(bom_key);
//			
//			request.setAttribute("bomDTO", bomDTO );
//			
//			System.out.println(bomDTO);
//			
//			request.getRequestDispatcher("").forward(request, response);
//			
//		} catch(Exception e) {
//			e.printStackTrace(); 
//		}
//		
//		
//	}

}
