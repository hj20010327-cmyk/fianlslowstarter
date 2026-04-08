package quality;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quality/add.do")
public class QualityAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 한글 깨짐 방지 설정
        request.setCharacterEncoding("UTF-8");

        // 2. 파라미터 수집 (HTML input의 name 속성과 일치해야 함)
        String prod_key = request.getParameter("prod_key");
        String item_key = request.getParameter("item_key");
        
        // 숫자로 변환해야 하는 필드들 (null이나 빈값 체크가 필요할 수 있음)
        int inspect_qty = Integer.parseInt(request.getParameter("inspect_qty"));
        int good_qty = Integer.parseInt(request.getParameter("good_qty"));
        int defect_qty = Integer.parseInt(request.getParameter("defect_qty"));
        
        String defect_reason = request.getParameter("defect_reason");
        String qc_status = request.getParameter("qc_status");
        String user_key = request.getParameter("user_key");

        // 3. DTO 객체 생성 및 데이터 세팅
        QualityDTO dto = new QualityDTO();
        dto.setProd_key(prod_key);
        dto.setItem_key(item_key);
        dto.setInspect_qty(inspect_qty);
        dto.setGood_qty(good_qty);
        dto.setDefect_qty(defect_qty);
        dto.setDefect_reason(defect_reason);
        dto.setQc_status(qc_status);
        dto.setUser_key(user_key);

        // 4. DAO를 통한 DB 저장
        QualityDAO dao = new QualityDAO();
        int result = dao.insert(dto);

        // 5. 결과에 따른 페이지 이동
        if (result > 0) {
            // 성공 시 목록 페이지로 이동
            response.sendRedirect("list.do");
        } else {
            // 실패 시 이전 페이지나 에러 페이지로 이동
            response.sendRedirect("add_form.jsp?error=1");
        }
    }
}