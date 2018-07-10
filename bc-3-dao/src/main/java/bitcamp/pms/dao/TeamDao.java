package bitcamp.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bitcamp.pms.domain.Team;

public class TeamDao {
	
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
	
	public TeamDao(
			String jdbcurl, String username, String password) {
		this.jdbcUrl = jdbcurl;
		this.username = username;
		this.password = password;
	}
	
	
	
	public int delete(String teamName) throws Exception {

		try (
			Connection con = DriverManager.getConnection(
					jdbcUrl, username, password);
			PreparedStatement stmt = con.prepareStatement(
            		"delete from pms2_team where name=?");) {
            
    		stmt.setString(1, teamName);
    		
    		return stmt.executeUpdate();
    	}
	}
	
	
	public int update(Team team) throws Exception {
		
        try (
            Connection con = DriverManager.getConnection(
            		jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "update pms2_team set dscrt=?, max_qty=?, sdt=?, edt=? where name=?");) {
        	
            stmt.setString(1, team.getDescription());
            stmt.setInt(2, team.getMaxQty());
            stmt.setDate(3, team.getStartDate());
            stmt.setDate(4, team.getEndDate());
            stmt.setString(5, team.getName());

            return stmt.executeUpdate();
        }
	}
	
	
	public int insert(Team team) throws Exception {
		
        try (
            Connection con = DriverManager.getConnection(
            		jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "insert into pms2_team(name,dscrt,max_qty,sdt,edt) values(?,?,?,?,?)");) {
            
            stmt.setString(1, team.getName());
            stmt.setString(2, team.getDescription());
            stmt.setInt(3, team.getMaxQty());
            stmt.setDate(4, team.getStartDate());
            stmt.setDate(5, team.getEndDate());

            return stmt.executeUpdate();
        }
	}
	
	
	public Team selectOne(String name) throws Exception {
		
        try (
    		Connection con = DriverManager.getConnection(
        			jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "select dscrt, sdt, edt, max_qty from pms2_team where name=?");
        ){
            
            stmt.setString(1, name);
            
            try (ResultSet rs = stmt.executeQuery();) {
                if (!rs.next()) {
                	return null;
                }
                
                Team team = new Team();
                team.setName(name);
        		team.setDescription(rs.getString("dscrt"));
        		team.setMaxQty(rs.getInt("max_qty"));
        		team.setStartDate(rs.getDate("sdt"));
        		team.setEndDate(rs.getDate("edt"));

        		return team;
            }
        }
	}
	

	public ArrayList<Team> selectList() throws Exception {
		
        try (
            Connection con = DriverManager.getConnection(
            		jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "select name, sdt, edt, max_qty from pms2_team");
            ResultSet rs = stmt.executeQuery();) {
            
        	ArrayList<Team> list = new ArrayList<>();
        	
    		while (rs.next()) {
    			Team team = new Team();
    			team.setName(rs.getString("name"));
    			team.setMaxQty(rs.getInt("max_qty"));
    			team.setStartDate(rs.getDate("sdt"));
    			team.setEndDate(rs.getDate("edt"));
            	
    			list.add(team);
            }
    		return list;
        }
	}
}
