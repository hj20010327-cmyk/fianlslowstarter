package quality;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

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
            // 파라미터 받기
            String qualityKeyParam = request.getParameter("quality_key");
            String inspectDateParam = request.getParameter("inspect_date");
            String inspectQtyParam = request.getParameter("inspect_qty");
            String goodQtyParam = request.getParameter("good_qty");
            String defectQtyParam = request.getParameter("defect_qty");
            String defectReasonParam = request.getParameter("defect_reason");
            String qcStatusParam = request.getParameter("qc_status");
            String userKeyParam = request.getParameter("user_key");
            String itemNameParam = request.getParameter("item_name");

            // 필수값 체크
            if (qualityKeyParam == null || qualityKeyParam.trim().equals("") ||
                inspectQtyParam == null || inspectQtyParam.trim().equals("") ||
                goodQtyParam == null || goodQtyParam.trim().equals("") ||
                defectQtyParam == null || defectQtyParam.trim().equals("") ||
                qcStatusParam == null || qcStatusParam.trim().equals("") ||
                userKeyParam == null || userKeyParam.trim().equals("") ||
                itemNameParam == null || itemNameParam.trim().equals("")) {

                response.getWriter().println("<script>alert('필수 입력값이 누락되었습니다.'); history.back();</script>");
                return;
            }

            int inspectQty = Integer.parseInt(inspectQtyParam);
            int goodQty = Integer.parseInt(goodQtyParam);
            int defectQty = Integer.parseInt(defectQtyParam);

            // 수량 체크
            if (inspectQty < 0 || goodQty < 0 || defectQty < 0) {
                response.getWriter().println("<script>alert('수량은 0보다 작을 수 없습니다.'); history.back();</script>");
                return;
            }

            if (defectQty > inspectQty) {
                response.getWriter().println("<script>alert('불량수량은 검사수량보다 클 수 없습니다.'); history.back();</script>");
                return;
            }

            if (goodQty != (inspectQty - defectQty)) {
                response.getWriter().println("<script>alert('양품수량이 올바르지 않습니다.'); history.back();</script>");
                return;
            }

            QualityDTO dto = new QualityDTO();

            // 수정할 값 세팅
            dto.setQuality_key(Integer.parseInt(qualityKeyParam));

            // [수정] 수정에서도 날짜 비었을 때 오늘 날짜로 안전 처리
            if (inspectDateParam == null || inspectDateParam.trim().equals("")) {
                dto.setInspect_date(Date.valueOf(LocalDate.now()));
            } else {
                dto.setInspect_date(Date.valueOf(inspectDateParam));
            }

            dto.setInspect_qty(inspectQty);
            dto.setGood_qty(goodQty);
            dto.setDefect_qty(defectQty);
            dto.setDefect_reason(defectReasonParam);
            dto.setQc_status(qcStatusParam);
            dto.setUser_key(Integer.parseInt(userKeyParam));
            dto.setItem_name(itemNameParam);

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