package quality;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;

@WebServlet("/quality/update")
public class QualityUpdateController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 한글 처리
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        // 로그인 세션 가져오기
        HttpSession session = request.getSession();
        LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");

        // 관리자 / 슈퍼바이저만 수정 가능
        if (loginUser == null ||
            (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
            response.sendRedirect(request.getContextPath() + "/qualityList");
            return;
        }

        try {
            // 수정할 데이터 DTO에 담기
            QualityDTO dto = new QualityDTO();

            dto.setQuality_key(Integer.parseInt(request.getParameter("quality_key")));   // 수정할 PK
            dto.setQuality_code(request.getParameter("quality_code"));                   // 검사번호
            dto.setInspect_date(Date.valueOf(request.getParameter("inspect_date")));     // 검사일자
            dto.setInspect_qty(Integer.parseInt(request.getParameter("inspect_qty")));   // 검사수량
            dto.setDefect_reason(request.getParameter("defect_reason"));                 // 불량사유
            dto.setProd_key(Integer.parseInt(request.getParameter("prod_key")));         // 생산 KEY
            dto.setUser_key(Integer.parseInt(request.getParameter("user_key")));         // 담당자 KEY

            // 서비스 호출
            QualityService service = new QualityService();
            int result = service.updatequality(dto);

            // 수정 후 다시 목록으로 이동
            response.sendRedirect(request.getContextPath() + "/qualityList");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('품질 수정 중 오류가 발생했습니다.'); history.back();</script>");
        }
    }
}