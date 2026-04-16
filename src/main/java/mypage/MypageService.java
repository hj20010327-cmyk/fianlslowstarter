package mypage;

import java.util.List;

public class MypageService {

	 MypageDAO dao = new MypageDAO();

	    public MypageDTO getUserByUserId(String userId, int page, int pageSize) {
	        MypageDTO dto = dao.selectUserByUserId(userId);

	        if (dto != null) {
	            int totalCount = dao.selectMyWorkOrderCount(userId);
	            int totalPage = (int) Math.ceil((double) totalCount / pageSize);
	            if (totalPage == 0) {
	                totalPage = 1;
	            }

	            List<MypageWorkorderDTO> myWorks = dao.selectMyWorkOrders(userId, page, pageSize);

	            dto.setMyWorkOrders(myWorks);
	            dto.setWorkOrderPage(page);
	            dto.setWorkOrderPageSize(pageSize);
	            dto.setWorkOrderTotalCount(totalCount);
	            dto.setWorkOrderTotalPage(totalPage);
	        }

	        return dto;
	    }

	    public boolean checkPassword(String userId, String rawPw) {
	        String encryptedPw = PasswordUtil.sha256(rawPw);
	        return dao.checkPassword(userId, encryptedPw);
	    }

	    public int updateOnlyInfo(MypageDTO dto) {
	        return dao.updateUserInfo(dto);
	    }

	    public int updateInfoAndPassword(MypageDTO dto, String newRawPw) {
	        dto.setUser_pw(PasswordUtil.sha256(newRawPw));
	        return dao.updateUserInfoAndPassword(dto);
	    }
	
}
