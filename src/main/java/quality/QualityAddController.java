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

        // 한글 처리
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        // 로그인 사용자 정보 가져오기
        HttpSession session = request.getSession();
        LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");

        // 관리자 / 슈퍼바이저만 등록 가능
        if (loginUser == null ||
            (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
            response.sendRedirect(request.getContextPath() + "/qualityList");
            return;
        }

        try {
            QualityDTO dto = new QualityDTO();
            QualityService service = new QualityService();

            // 화면에서 넘어온 값 받기
            String prodKeyParam = request.getParameter("prod_key");
            String inspectDateParam = request.getParameter("inspect_date");
            String inspectQtyParam = request.getParameter("inspect_qty");
            String goodQtyParam = request.getParameter("good_qty");
            String defectQtyParam = request.getParameter("defect_qty");
            String userKeyParam = request.getParameter("user_key");

            // 필수값 체크
            if (prodKeyParam == null || prodKeyParam.trim().equals("") ||
                inspectDateParam == null || inspectDateParam.trim().equals("") ||
                inspectQtyParam == null || inspectQtyParam.trim().equals("") ||
                goodQtyParam == null || goodQtyParam.trim().equals("") ||
                defectQtyParam == null || defectQtyParam.trim().equals("") ||
                userKeyParam == null || userKeyParam.trim().equals("")) {

                response.getWriter().println("<script>alert('등록값이 누락되었습니다.'); history.back();</script>");
                return;
            }

            // 검사번호는 DB 최대값 기준으로 자동 생성
            dto.setQuality_code(service.getNextQualityCode());

            // 날짜 제한 없음
            // 4월이든 5월이든 사용자가 선택한 날짜 그대로 저장
            dto.setInspect_date(Date.valueOf(inspectDateParam));

            // 수량 정보 세팅
            dto.setInspect_qty(Integer.parseInt(inspectQtyParam));
            dto.setGood_qty(Integer.parseInt(goodQtyParam));
            dto.setDefect_qty(Integer.parseInt(defectQtyParam));

            // 기타 정보 세팅
            dto.setDefect_reason(request.getParameter("defect_reason"));
            dto.setQc_status(request.getParameter("qc_status"));
            dto.setProd_key(Integer.parseInt(prodKeyParam));   // WORK_ORDER_KEY
            dto.setUser_key(Integer.parseInt(userKeyParam));

            // 등록 실행
            int result = service.addquality(dto);

            // 성공하면 목록으로 이동
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