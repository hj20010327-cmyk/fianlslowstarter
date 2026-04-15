package product;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    
    // 리스트 조회 (5개 제한 적용)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        String p_ = request.getParameter("p");
        int page = (p_ != null && !p_.isEmpty()) ? Integer.parseInt(p_) : 1;
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        int pageSize = 5; // 5개만 나오도록 설정
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        ProductService service = new ProductService();
        List<ProductDTO> list = service.getList(startRow, endRow, keyword);
        int totalCount = service.getTotalCount(keyword);

        request.setAttribute("list", list);
        request.setAttribute("totalCount", totalCount);
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }

    // 신규등록, 수정, 삭제 처리를 한꺼번에 관리
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String cmd = request.getParameter("cmd");
        ProductService service = new ProductService();
        
        if ("insert".equals(cmd) || "update".equals(cmd)) {
            ProductDTO dto = new ProductDTO();
            if("update".equals(cmd)) {
                dto.setProduct_key(Integer.parseInt(request.getParameter("product_key")));
            }
            dto.setItem_code(request.getParameter("item_code"));
            dto.setProduct_name(request.getParameter("product_name"));
            dto.setSpec(request.getParameter("spec"));
            dto.setUnit(request.getParameter("unit"));
            
            String priceStr = request.getParameter("price");
            if (priceStr != null) {
                dto.setPrice(Integer.parseInt(priceStr.replace(",", "")));
            }
            
            if("insert".equals(cmd)) service.register(dto);
            else service.update(dto);
            
        } else if ("delete".equals(cmd)) {
            String[] codes = request.getParameterValues("codes");
            if (codes != null) {
                service.remove(String.join(",", codes));
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/product");
    }
}