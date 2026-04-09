package plan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PlanDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

    public List<PlanDTO> selectAll() {
        List<PlanDTO> list = new ArrayList<>();


        try {
            Context ctx = new InitialContext();
            DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
            conn = dataFactory.getConnection();

            String query = "SELECT * FROM tb_plan where rownum<=4";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                PlanDTO dto = new PlanDTO();

                dto.setPlan_key(rs.getInt("plan_key"));
                dto.setPlan_code(rs.getString("plan_code"));
                dto.setItem_key(rs.getInt("item_key"));
                dto.setPlan_date(rs.getDate("plan_date"));
                dto.setDue_date(rs.getDate("due_date"));
                dto.setPlan_qty(rs.getInt("plan_qty"));
                dto.setStatus(rs.getString("plan_status"));   
                dto.setUser_key(rs.getInt("user_key"));
                dto.setCreate_at(rs.getDate("created_at"));   
                dto.setPriority(rs.getInt("priority"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("list.size(): " + list.size());
		return list;
	}

    public int insertPlan(PlanDTO dto) {
        
        int result = -1;

        try {
            Context ctx = new InitialContext();
            DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
            conn = dataFactory.getConnection();

            String query = "INSERT INTO tb_plan (" +
                    "plan_key, plan_code, item_key, plan_date, due_date, " +
                    "plan_qty, plan_status, user_key, created_at, priority" +
                    ") VALUES (" +
                    "seq_plan.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?" +
                    ")";

            ps = conn.prepareStatement(query);

            ps.setString(1, dto.getPlan_code());
            ps.setInt(2, dto.getItem_key());
            ps.setDate(3, dto.getPlan_date());
            ps.setDate(4, dto.getDue_date());
            ps.setInt(5, dto.getPlan_qty());
            ps.setString(6, dto.getStatus());     
            ps.setInt(7, dto.getUser_key());
            ps.setInt(8, dto.getPriority());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

    public int updatePlan(PlanDTO dto) {
       
        int result = -1;

        try {
            Context ctx = new InitialContext();
            DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
            conn = dataFactory.getConnection();

            String query = "UPDATE tb_plan SET " +
                    "plan_code = ?, " +
                    "item_key = ?, " +
                    "plan_date = ?, " +
                    "due_date = ?, " +
                    "plan_qty = ?, " +
                    "plan_status = ?, " +
                    "user_key = ?, " +
                    "priority = ? " +
                    "WHERE plan_key = ?";

            ps = conn.prepareStatement(query);

            ps.setString(1, dto.getPlan_code());
            ps.setInt(2, dto.getItem_key());
            ps.setDate(3, dto.getPlan_date());
            ps.setDate(4, dto.getDue_date());
            ps.setInt(5, dto.getPlan_qty());
            ps.setString(6, dto.getStatus());    
            ps.setInt(7, dto.getUser_key());
            ps.setInt(8, dto.getPriority());
            ps.setInt(9, dto.getPlan_key());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}

    public int deletePlan(int plankey) {
        
        int result = -1;

        try {
            Context ctx = new InitialContext();
            DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
            conn = dataFactory.getConnection();

            String query = "DELETE FROM tb_plan WHERE plan_key = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, plankey);

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}