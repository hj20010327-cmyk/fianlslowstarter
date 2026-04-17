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

@WebServlet("/quality/add")
public class QualityAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    		System.out.println("quality/add do Get 실행");
    	
    	
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        HttpSession session = request.getSession();
        LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");

        if (loginUser == null ||
            (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
            response.sendRedirect(request.getContextPath() + "/qualityList");
            return;
        }

        try {
            QualityDTO dto = new QualityDTO();
            QualityService service = new QualityService();

            // 검사번호 자동 생성
            dto.setQuality_code(service.getNextQualityCode());

            dto.setInspect_date(Date.valueOf(request.getParameter("inspect_date")));
            dto.setInspect_qty(Integer.parseInt(request.getParameter("inspect_qty")));
            dto.setGood_qty(Integer.parseInt(request.getParameter("good_qty")));
            dto.setDefect_qty(Integer.parseInt(request.getParameter("defect_qty")));
            dto.setDefect_reason(request.getParameter("defect_reason"));
            dto.setQc_status(request.getParameter("qc_status"));
            dto.setProd_key(Integer.parseInt(request.getParameter("prod_key")));   // WORK_ORDER_KEY
            dto.setUser_key(Integer.parseInt(request.getParameter("user_key")));

            int result = service.addquality(dto);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/qualityList");
            } else {
                response.getWriter().println("<script>alert('품질 등록 실패'); history.back();</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('품질 등록 중 오류가 발생했습니다.'); history.back();</script>");
        }
    }
}