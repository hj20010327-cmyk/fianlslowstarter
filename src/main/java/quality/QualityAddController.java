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
        // 1. �ѱ� ���� ���� ����
        request.setCharacterEncoding("UTF-8");

        // 2. �Ķ���� ���� (HTML input�� name �Ӽ��� ��ġ�ؾ� ��)
        String prod_key = request.getParameter("prod_key");
        String item_key = request.getParameter("item_key");
        
        // ���ڷ� ��ȯ�ؾ� �ϴ� �ʵ�� (null�̳� �� üũ�� �ʿ��� �� ����)
        int inspect_qty = Integer.parseInt(request.getParameter("inspect_qty"));
        int good_qty = Integer.parseInt(request.getParameter("good_qty"));
        int defect_qty = Integer.parseInt(request.getParameter("defect_qty"));
        
        String defect_reason = request.getParameter("defect_reason");
        String qc_status = request.getParameter("qc_status");
        String user_key = request.getParameter("user_key");

        // 3. DTO ��ü ���� �� ������ ����
        QualityDTO dto = new QualityDTO();
        dto.setProd_key(prod_key);
        dto.setItem_key(item_key);
        dto.setInspect_qty(inspect_qty);
        dto.setGood_qty(good_qty);
        dto.setDefect_qty(defect_qty);
        dto.setDefect_reason(defect_reason);
        dto.setQc_status(qc_status);
        dto.setUser_key(user_key);

        // 4. DAO�� ���� DB ����
        QualityDAO dao = new QualityDAO();
        int result = dao.insert(dto);

        // 5. ����� ���� ������ �̵�
        if (result > 0) {
            // ���� �� ��� �������� �̵�
            response.sendRedirect("list.do");
        } else {
            // ���� �� ���� �������� ���� �������� �̵�
            response.sendRedirect("add_form.jsp?error=1");
        }
    }
}