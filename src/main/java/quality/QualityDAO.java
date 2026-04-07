public List<QualityDTO> selectSearch(String searchCode, String searchName, String searchResult) {
    
  
    List<QualityDTO> list = new ArrayList<>();
    
  
    String sql = "SELECT * FROM (SELECT * FROM tb_quality WHERE 1=1";
    
   
    if (searchCode != null && !searchCode.isEmpty()) {
        sql += " AND inspect_code LIKE ?"; // 검사번호 검색
    }
    if (searchName != null && !searchName.isEmpty()) {
        sql += " AND item_name LIKE ?";    // 제품명 검색 
    }
    if (searchResult != null && !searchResult.isEmpty()) {
        sql += " AND result = ?";           // 판정 결과 검색
    }
    
   
    sql += " ORDER BY inspect_date DESC) WHERE ROWNUM <= 3";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        int idx = 1; // 물음표(?)의 순서를 정해주는 변수
        
        
        if (searchCode != null && !searchCode.isEmpty()) {
            pstmt.setString(idx++, "%" + searchCode + "%");
        }
        if (searchName != null && !searchName.isEmpty()) {
            pstmt.setString(idx++, "%" + searchName + "%");
        }
        if (searchResult != null && !searchResult.isEmpty()) {
            pstmt.setString(idx++, searchResult);
        }

   
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                QualityDTO dto = new QualityDTO();
               
                dto.setInspect_code(rs.getString("inspect_code"));
                dto.setItem_name(rs.getString("item_name"));
                dto.setInspect_date(rs.getString("inspect_date"));
                dto.setInspector(rs.getString("inspector"));
                dto.setResult(rs.getString("result"));
                
                list.add(dto); 
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list; 
}
}