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
            String qcStatusParam = request.getParameter("qc_status");
            String itemNameParam = request.getParameter("item_name");
            String defectReasonParam = request.getParameter("defect_reason");

            // 필수값 체크
            if (prodKeyParam == null || prodKeyParam.trim().equals("") ||
                inspectQtyParam == null || inspectQtyParam.trim().equals("") ||
                goodQtyParam == null || goodQtyParam.trim().equals("") ||
                defectQtyParam == null || defectQtyParam.trim().equals("") ||
                userKeyParam == null || userKeyParam.trim().equals("") ||
                qcStatusParam == null || qcStatusParam.trim().equals("") ||
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

            // 검사번호는 DB 최대값 기준으로 자동 생성
            dto.setQuality_code(service.getNextQualityCode());

            // [수정] 날짜 기본값 처리
            // 화면에서 날짜를 안 넘겨도 오늘 날짜로 기본 저장되게 처리
            if (inspectDateParam == null || inspectDateParam.trim().equals("")) {
                dto.setInspect_date(Date.valueOf(LocalDate.now()));
            } else {
                dto.setInspect_date(Date.valueOf(inspectDateParam));
            }

            // 수량 정보 세팅
            dto.setInspect_qty(inspectQty);
            dto.setGood_qty(goodQty);
            dto.setDefect_qty(defectQty);

            // 기타 정보 세팅
            dto.setDefect_reason(defectReasonParam);
            dto.setQc_status(qcStatusParam);
            dto.setProd_key(Integer.parseInt(prodKeyParam));
            dto.setUser_key(Integer.parseInt(userKeyParam));

            // 품목명 저장
            dto.setItem_name(itemNameParam);

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