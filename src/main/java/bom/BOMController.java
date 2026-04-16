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
@WebServlet("/bom")
public class BOMController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/BOM doGet 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String skeycode = request.getParameter("keycode");
		String keyword = request.getParameter("keyword");
		
		String sParent = request.getParameter("parent_item_key");
		int parent_item_key = 0;

		try {
		    parent_item_key = Integer.parseInt(sParent);
		} catch (Exception e) {}

		
		//페이징
		int size = 10; 
		int page = 1; 
		int keycode = 0; 
		
		String sSize = request.getParameter("size");
		String sPage = request.getParameter("page");
		
	    try {
	        size = Integer.parseInt(sSize);
	    } catch (Exception e) {}
		
	    try {
	        page = Integer.parseInt(sPage);
	    } catch (Exception e) {}
	    try {
	    	keycode = Integer.parseInt(skeycode);
	    } catch (Exception e) {}
	  
		
		
		BOMDTO bomDTO = new BOMDTO(); 
		bomDTO.setSize(size);
		bomDTO.setPage(page);
		bomDTO.setKeycode(keycode);
		bomDTO.setKeyword(keyword);
		bomDTO.setParent_item_key(parent_item_key);
		
		// 서비스 
		BOMService bomservice = new BOMService();
		
		Map<String, Object> map = bomservice.getPaging(bomDTO);
		map.put("size", size);
		map.put("page", page);
		
		request.setAttribute("map",map);
		request.setAttribute("list",map.get("list"));
		
		
		System.out.println("parent_item_key = " + request.getParameter("parent_item_key"));
		
		String view = request.getParameter("view");
		
		if("detail".equals(view)){
			RequestDispatcher rd = request.getRequestDispatcher("/bom_detail.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/bom.jsp");
			rd.forward(request, response);
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/BOM doPost 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		// form 요소
		String cmd = request.getParameter("cmd");
		
		System.out.println("cmd: " + cmd);
		
		if(cmd.equals("insert")) {
			insert(request, response);
		} else if(cmd.equals("update")) {
			update(request, response); 
		} else if(cmd.equals("delete")) {
			delete(request, response);
		}
	}
	
	protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/BOM insert 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		// 파라미터
		
		String bom_code = request.getParameter("bom_code");
		String QTY = request.getParameter("qty");
		int qty = Integer.parseInt(QTY);
		String remark = request.getParameter("remark");
		String Bitem_key = request.getParameter("item_key");
		String item_name = request.getParameter("item_name");
		String Bparent_item_key = request.getParameter("parent_item_key");
		String parent_item_name = request.getParameter("parent_item_name");
		
		int item_key = 0; 
		if (Bitem_key != null && !Bitem_key.trim().isEmpty()) {
			item_key = Integer.parseInt(Bitem_key.trim());
		}
		
		int parent_item_key = 0; 
		if (Bparent_item_key != null && !Bparent_item_key.trim().isEmpty()) {
			parent_item_key = Integer.parseInt(Bparent_item_key.trim());
		}
		
		// DTO실행
		BOMDTO bomDTO = new BOMDTO(); 
		
		bomDTO.setBom_code(bom_code);
		bomDTO.setQty(qty);
		bomDTO.setRemark(remark);
		bomDTO.setItem_key(item_key);
		bomDTO.setParent_item_key(parent_item_key);
		bomDTO.setItem_name(item_name);
		bomDTO.setParent_item_name(parent_item_name);
		
		
		// service & DTO
		BOMService bomservice = new BOMService(); 
		int result = bomservice.insert(bomDTO);
		
		System.out.println("insert된 rows: " + result);
		
		response.sendRedirect("/slowstarter/bom");
		
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/BOM update 실행");
		
		// 파라미터
		String Bom_key = request.getParameter("bom_key");
		int bom_key = Integer.parseInt(Bom_key);
		String bom_code = request.getParameter("bom_code");
		String QTY = request.getParameter("qty");
		int qty = Integer.parseInt(QTY);
		String remark = request.getParameter("remark");
		String Bitem_key = request.getParameter("item_key");
		String Bparent_item_key = request.getParameter("parent_item_key");
		String item_name = request.getParameter("item_name");
		String parent_item_name = request.getParameter("parent_item_name");
		
		int item_key = 0; 
		if (Bitem_key != null && !Bitem_key.trim().isEmpty()) {
			item_key = Integer.parseInt(Bitem_key.trim());
		}
		
		int parent_item_key = 0; 
		if (Bparent_item_key != null && !Bparent_item_key.trim().isEmpty()) {
			parent_item_key = Integer.parseInt(Bparent_item_key.trim());
		}
		
		try {
			BOMDTO bomDTO = new BOMDTO();
			
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			bomDTO.setBom_key(bom_key);
			bomDTO.setBom_code(bom_code);
			bomDTO.setQty(qty);
			bomDTO.setRemark(remark);
			bomDTO.setItem_key(item_key);
			bomDTO.setParent_item_key(parent_item_key);
			bomDTO.setItem_name(item_name);
			bomDTO.setParent_item_name(parent_item_name);
			
			BOMService bomservice = new BOMService(); 
			int result = bomservice.update(bomDTO);
			
			System.out.println("update된 열 : " + result);
			
			response.sendRedirect("/slowstarter/bom");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/BOM delete 실행");
		
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
	
			String[] keys=request.getParameterValues("bom_key");

			BOMDTO dto = new BOMDTO();
			
			if(keys != null) {
				BOMService bomservice = new BOMService();
				
				for(String key : keys) {
					int bom_key = Integer.parseInt(key);
					System.out.println("bom_key : " + bom_key);
					
					dto.setBom_key(bom_key);
					
					bomservice.delete(dto);
				}
			int result = bomservice.delete(dto);
			}
			response.sendRedirect("../slowstarter/bom");
			
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
//			BOMDTO bomDTO = new BOMDTO();
//			
//			String Bom_key = request.getParameter("bom_key");
//			int bom_key = Integer.parseInt(Bom_key);
//			System.out.println("bom_key: " + bom_key);
//			
//			// bom_key 
//			BOMService bomservice = new BOMService(); 
//			List list = bomservice.getBOM(bomDTO);
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
