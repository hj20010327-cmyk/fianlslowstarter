package bom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		
		List<BOMDTO> material = bomservice.MaterialBOM();
		request.setAttribute("material",material);
		
		RequestDispatcher rd = request.getRequestDispatcher("/bom_detail.jsp");
        rd.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
