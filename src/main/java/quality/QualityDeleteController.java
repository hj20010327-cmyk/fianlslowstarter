package quality;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 화면에서 삭제 버튼을 눌러 /qualityDelete 주소로 요청이 올 때 실행됩니다.
@WebServlet("/qualityDelete")
public class QualityDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/qualityDelete (삭제처리) 실행");
        
        // 1. 삭제할 데이터의 고유 키(quality_key)를 파라미터로 받습니다.
        // 선택 삭제 시 "1,2,3" 처럼 콤마로 구분된 문자열이 들어올 수 있습니다.
        String quality_key = request.getParameter("quality_key");
        
        if (quality_key != null && !quality_key.isEmpty()) {
            // 2. 서비스를 호출하여 삭제 작업을 수행합니다.
            QualityService qualityService = new QualityService();

            /* [추가 로직] 다중 삭제 처리 */
            // 문자열에 포함된 콤마(,)를 기준으로 잘라서 배열로 만듭니다.
            String[] keyArray = quality_key.split(",");
            int totalResult = 0; // 실제 삭제된 건수를 저장할 변수

            // 배열에 담긴 키 개수만큼 반복문을 돌리며 삭제를 수행합니다.
            for (String key : keyArray) {
                // 각 키(quality_key) 하나하나에 대해 서비스의 delete 메서드 호출
                int result = qualityService.delete(key);
                totalResult += result; // 성공 시 결과 합산
            }
            
            System.out.println("삭제 요청 건수: " + keyArray.length);
            System.out.println("최종 삭제 결과 건수: " + totalResult);
        }
        
        // 3. 삭제가 완료되면 다시 목록 화면으로 이동시킵니다.
        response.sendRedirect("qualityList");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 보통 삭제는 링크 클릭(GET)으로 처리하는 경우가 많아 doGet으로 보냅니다.
        doGet(request, response);
    }
}