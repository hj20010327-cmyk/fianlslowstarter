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
		
		String skeycode = request.getParameter("keycode");
		String keyword = request.getParameter("keyword");
		
		//페이징
		int size = 5; 
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
		
		// 서비스 
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
		
		// form 요소
		String cmd = request.getParameter("cmd");
		
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
		String QTY = request.getParameter("QTY");
		int qty = Integer.parseInt(QTY);
		String remark = request.getParameter("remark");
		String bom_item_key = request.getParameter("bom_item_key");
		
		// DTO실행
		BOMDTO bomDTO = new BOMDTO(); 
		
		
		bomDTO.setBom_code(bom_code);
		bomDTO.setQTY(qty);
		bomDTO.setRemark(remark);
		bomDTO.setBom_item_key(bom_item_key);
		
		// service & DTO
		BOMService bomservice = new BOMService(); 
		int result = bomservice.insert(bomDTO);
		
		System.out.println("insert된 rows: " + result);
		
		response.sendRedirect("/slowstarter/BOM");
		
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/BOM update 실행");
		
		// 파라미터
		String Bom_key = request.getParameter("bom_key");
		int bom_key = Integer.parseInt(Bom_key);
		String bom_code = request.getParameter("bom_code");
		String QTY = request.getParameter("QTY");
		int qty = Integer.parseInt(QTY);
		String remark = request.getParameter("remark");
		String bom_item_key = request.getParameter("bom_item_key");
		
		try {
			BOMDTO bomDTO = new BOMDTO();
			
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			bomDTO.setBom_key(bom_key);
			bomDTO.setBom_code(bom_code);
			bomDTO.setQTY(qty);
			bomDTO.setRemark(remark);
			bomDTO.setBom_item_key(bom_item_key);
			
			BOMService bomservice = new BOMService(); 
			int result = bomservice.update(bomDTO);
			
			System.out.println("update된 열 : " + result);
			
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
			response.sendRedirect("../slowstarter/BOM");
			
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
