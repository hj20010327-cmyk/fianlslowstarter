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

        // 로그인 사용자 확인
        HttpSession session = request.getSession();
        LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");

        // 관리자 / 슈퍼바이저만 수정 가능
        if (loginUser == null ||
            (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
            response.sendRedirect(request.getContextPath() + "/qualityList");
            return;
        }

        try {
            QualityDTO dto = new QualityDTO();

            // 수정할 값 세팅
            dto.setQuality_key(Integer.parseInt(request.getParameter("quality_key")));
            dto.setInspect_date(Date.valueOf(request.getParameter("inspect_date")));
            dto.setInspect_qty(Integer.parseInt(request.getParameter("inspect_qty")));
            dto.setGood_qty(Integer.parseInt(request.getParameter("good_qty")));
            dto.setDefect_qty(Integer.parseInt(request.getParameter("defect_qty")));
            dto.setDefect_reason(request.getParameter("defect_reason"));
            dto.setQc_status(request.getParameter("qc_status"));
            dto.setUser_key(Integer.parseInt(request.getParameter("user_key")));

            // ★ 품목명도 같이 받기
            dto.setItem_name(request.getParameter("item_name"));

            QualityService service = new QualityService();

            // 수정 실행
            int result = service.updatequality(dto);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/qualityList");
            } else {
                response.getWriter().println("<script>alert('품질 수정 실패'); history.back();</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('품질 수정 중 오류가 발생했습니다.'); history.back();</script>");
        }
    }
}