package quality;

import java.util.List;

public class QualityService {
    private QualityDAO dao = new QualityDAO();

    // 목록 조회
    public List<QualityDTO> getList() { 
        return dao.selectAll(); 
    }

    // 등록 (Controller에서 .insert(dto)로 부르는 이름)
    public int insert(QualityDTO dto) { 
        return dao.insert(dto); 
    }

    // 삭제 (Controller에서 .delete(key)로 부르는 이름)
    public int delete(String key) { 
        return dao.delete(key); 
    }
}