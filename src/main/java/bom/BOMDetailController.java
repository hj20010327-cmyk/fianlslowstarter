package bom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BOMDetailController
 */
@WebServlet("/bom/detail")
public class BOMDetailController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/BOM/detail doGet 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String sParent = request.getParameter("parent_item_key");
		int parent_item_key = 0;
		if (sParent != null) {
		    parent_item_key = Integer.parseInt(sParent);
		}
		
		BOMDTO bomDTO = new BOMDTO(); 
		bomDTO.setParent_item_key(parent_item_key);
		
		BOMService bomservice = new BOMService();
		
		List<BOMDTO> itemList = bomservice.getItemForBOM();

		List<BOMDTO> filtered = new ArrayList<>();

		for (BOMDTO dto : itemList) {
			if (dto.getItem_key() >= 1 && dto.getItem_key() <= 5) {
				filtered.add(dto);
			}
		}
		
		request.setAttribute("itemList", filtered);
		
		Map<String, Object> map = bomservice.getPaging(bomDTO);
		
		request.setAttribute("list", map.get("list"));
		
		List<BOMDTO> material = bomservice.MaterialBOM();
		request.setAttribute("material",material);
		
		RequestDispatcher rd = request.getRequestDispatcher("/bom_detail.jsp");
        rd.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		System.out.println("/BOM doPost 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		update(request, response);
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("/bom_detail update 실행");

		// 파라미터
		String Bom_key = request.getParameter("bom_key");
		
		if (Bom_key == null || Bom_key.isEmpty()) {
		    throw new RuntimeException("bom_key 안넘어옴");
		}
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

			response.sendRedirect("/slowstarter/bom/detail?parent_item_key=" + parent_item_key);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
