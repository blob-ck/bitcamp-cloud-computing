package bitcamp.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bitcamp.pms.domain.Member;

public class TeamMemberDao {
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	String jdbcUrl;
	String username;
	String password;
	
	public TeamMemberDao(
			String jdbcurl, String username, String password) {
		this.jdbcUrl = jdbcurl;
		this.username = username;
		this.password = password;
	}
	
	
	
	public int delete(String teamName, String memberId) throws Exception {
		
    	try (
    			Connection con = DriverManager.getConnection(
    					jdbcUrl, username, password);
    			PreparedStatement stmt = con.prepareStatement(
    					"delete from pms2_team_member where tnm=? and mid=?");) {
    		
    		stmt.setString(1, teamName);
    		stmt.setString(2, memberId);
    		
    		return stmt.executeUpdate();
    	}
	}
	
	
	//임의 추가
/*	public int deleteById(String memberId) throws Exception {
		
		try (
				Connection con = DriverManager.getConnection(
						jdbcUrl, username, password);
				PreparedStatement stmt = con.prepareStatement(
						"delete from pms2_team_member where mid=?");) {
			
			stmt.setString(1, memberId);
			
			return stmt.executeUpdate();
		}
	}*/
	

	
	public int delete(String teamName) throws Exception {
		
    	try (
    			Connection con = DriverManager.getConnection(
    					jdbcUrl, username, password);
    			PreparedStatement stmt = con.prepareStatement(
    					"delete from pms2_team_member where tnm=?");) {
    		
    		stmt.setString(1, teamName);
    		
    		return stmt.executeUpdate();
    	}
	}
	
	
	
	public boolean isExist(String teamName, String memberId) throws Exception {
		
		try (
            Connection con = DriverManager.getConnection(
            		jdbcUrl, username, password);
        	) {
                
            PreparedStatement stmt = con.prepareStatement(
            		"select mid from pms2_team_member where tnm=? and mid=?");
            stmt.setString(1, teamName);
            stmt.setString(2, memberId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	return true;
                } 
                return false;
            }
        }
	}
	
	
	
	public int insert(String teamName, String memberId) throws Exception {
		
		try (
            Connection con = DriverManager.getConnection(
            		jdbcUrl, username, password);
        	) {
        	PreparedStatement stmt = con.prepareStatement(
                    "insert into pms2_team_member(tnm,mid) values(?,?)");
            stmt.setString(1, teamName);
            stmt.setString(2, memberId);
            
            return stmt.executeUpdate();
        }
	}	
	
	public ArrayList<Member> selectListWithEmail(String teamName) throws Exception {
		
		try (
			Connection con = DriverManager.getConnection(
					jdbcUrl, username, password);
			PreparedStatement stmt = con.prepareStatement(
					"select tm.mid, m.email" + 
					"    from pms2_team_member tm inner join pms2_member m on tm.mid=m.mid" + 
					"    where tm.tnm=?");
		){
	        stmt.setString(1, teamName);
	        
	        try (ResultSet rs = stmt.executeQuery();) {

        	
        	ArrayList<Member> members = new ArrayList<>();
        	
        	while(rs.next()) {
        		Member member = new Member(); 
				member.setId(rs.getString("mid"));
				member.setEmail(rs.getString("email"));
				
				members.add(member);
        	}
        	return members;
	        }
        }
	}
}
