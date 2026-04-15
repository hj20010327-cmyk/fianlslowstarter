package product;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        // 1. 페이지 번호 처리
        String p_ = request.getParameter("p");
        int page = 1; 
        try {
            if (p_ != null && !p_.isEmpty()) page = Integer.parseInt(p_);
        } catch (NumberFormatException e) { page = 1; }

        // 검색어 처리
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        // 2. 페이징 설정 (한 페이지에 5개씩)
        int pageSize = 5; 
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        ProductService service = new ProductService();
        List<ProductDTO> list = service.getList(startRow, endRow, keyword);
        int totalCount = service.getTotalCount(keyword);
        
        // 3. [팀원 코드 방식] 자바에서 미리 전체 페이지 수 계산
        // (전체 데이터 수 / 페이지당 개수)를 올림 처리
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        
        // 4. 데이터 바인딩 (JSP에서 사용할 변수들)
        request.setAttribute("list", list);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPage", totalPage); // JSP의 c:forEach end값으로 사용됨
        
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String cmd = request.getParameter("cmd");
        ProductService service = new ProductService();
        ProductDTO dto = new ProductDTO();

        // 파라미터 수집 (이름, 스펙, 단위)
        dto.setProduct_name(request.getParameter("product_name"));
        dto.setSpec(request.getParameter("spec"));
        dto.setUnit(request.getParameter("unit"));
        
        // 가격 처리 (콤마 제거 및 정수 변환)
        String priceStr = request.getParameter("price");
        int finalPrice = 0;
        if (priceStr != null && !priceStr.isEmpty()) {
            try {
                String cleanPrice = priceStr.replaceAll(",", "").trim();
                finalPrice = Integer.parseInt(cleanPrice);
            } catch (NumberFormatException e) { finalPrice = 0; }
        }
        dto.setPrice(finalPrice);

        // 명령 실행 분기
        if ("insert".equals(cmd)) {
            // [등록 에러 방지 핵심] 
            // 신규 등록 시에는 dto에 product_key를 절대로 세팅하지 않습니다.
            // 그래야 DB의 시퀀스(SEQ_ITEM)가 중복 없는 다음 번호를 자동으로 부여합니다.
            service.register(dto); 
        } else if ("update".equals(cmd)) {
            // 수정 시에는 어떤 제품을 고칠지 알아야 하므로 key를 세팅합니다.
            String keyStr = request.getParameter("product_key");
            if(keyStr != null && !keyStr.isEmpty()) {
                dto.setProduct_key(Integer.parseInt(keyStr));
            }
            service.update(dto);
        } else if ("delete".equals(cmd)) {
            // 삭제 처리
            String[] codes = request.getParameterValues("codes");
            if (codes != null) service.remove(String.join(",", codes));
        }

        // 모든 작업 완료 후 목록 페이지로 이동 (새로고침 시 중복 등록 방지)
        response.sendRedirect(request.getContextPath() + "/product");
    }
}