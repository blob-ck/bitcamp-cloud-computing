package bitcamp.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bitcamp.pms.domain.Member;

public class MemberDao {
	
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

	
	
	public MemberDao(
			String jdbcurl, String username, String password) {
		this.jdbcUrl = jdbcurl;
		this.username = username;
		this.password = password;
	}
	
	
	@SuppressWarnings("unused")
	public List<Member> selectList() throws Exception {
		
        try (
            Connection con = DriverManager.getConnection(
        		jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "select mid, email from pms2_member");
            ResultSet rs = stmt.executeQuery();) {
            
            ArrayList<Member> list = new ArrayList<>();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getString("mid"));
                member.setEmail(rs.getString("email"));
                list.add(member);
            }
            return list;
        }
    }
	
	
	
	@SuppressWarnings("unused")
	public Member selectOne(String id) throws Exception {
		
        try (
		Connection con = DriverManager.getConnection(
				jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement("select mid, email from pms2_member where mid=?");
            ) {
    		stmt.setString(1, id);
    		ResultSet rs = stmt.executeQuery();
    		if (!rs.next()) {
    			return null;
    		} else {
    			Member member = new Member();
                member.setId(rs.getString("mid"));
                member.setEmail(rs.getString("email"));
    			return member;
    		}
        }
	}
	
	
	@SuppressWarnings("unused")
	public int update(Member member) throws Exception {
		
        try (
        	Connection con = DriverManager.getConnection(
        			jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "update pms2_member set email=?, pwd=password(?) where mid=?");) {
            
            stmt.setString(1, member.getEmail());
            stmt.setString(2, member.getPassword());
            stmt.setString(3, member.getId());
            
            return stmt.executeUpdate();
        }
	}
	
	
	
	
	@SuppressWarnings("unused")
	public int delete(String id) throws Exception {
		
        try (
        	Connection con = DriverManager.getConnection(
        			jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "delete from pms2_member where mid=?");) {
            
            stmt.setString(1, id);
            
            return stmt.executeUpdate();
        } 
	}
	
	
	
	@SuppressWarnings("unused")
	public void insert(Member member) throws Exception {
		
        try (
        	Connection con = DriverManager.getConnection(
        			jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "insert into pms2_member(mid,email,pwd) values(?,?,password(?))");) {
            
            stmt.setString(1, member.getId());
            stmt.setString(2, member.getEmail());
            stmt.setString(3, member.getPassword());
        
            stmt.executeUpdate();
        }
	}
}
