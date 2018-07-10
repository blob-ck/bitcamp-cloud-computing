package bitcamp.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bitcamp.pms.domain.Member;
import bitcamp.pms.domain.Task;
import bitcamp.pms.domain.Team;

public class TaskDao {
	
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
	
	public TaskDao(
			String jdbcurl, String username, String password) {
		this.jdbcUrl = jdbcurl;
		this.username = username;
		this.password = password;
	}
		

	
	
	public int delete(int no) throws Exception {
		
		try (
			Connection con = DriverManager.getConnection(
					jdbcUrl, username, password); 
			PreparedStatement stmt = con.prepareStatement(
					"delete from pms2_task where tano=?");) {

			stmt.setInt(1, no);
			return stmt.executeUpdate();
		}
	}
	
	
	//임의 추가
/*	public int deleteById(String memberId) throws Exception {
		
		try (
			Connection con = DriverManager.getConnection(
					jdbcUrl, username, password); 
			PreparedStatement stmt = con.prepareStatement(
					"delete from pms2_task where mid=?");) {
		
			stmt.setString(1, memberId);
			return stmt.executeUpdate();
		}
	}
	*/
	

	public int deleteByTeam(String teamName) throws Exception {
		
		try (
			Connection con = DriverManager.getConnection(
					jdbcUrl, username, password); 
			PreparedStatement stmt = con.prepareStatement(
					"delete from pms2_task where tnm=?");) {

			stmt.setString(1, teamName);
			return stmt.executeUpdate();
		}
	}
	

	//임의 추가
/*	public int update(String memberId) throws Exception {
		
		try (
				Connection con = DriverManager.getConnection(
						jdbcUrl, username, password);
				PreparedStatement stmt = con.prepareStatement(
						"update pms2_task set mid=? where mid=?");) {
			
			stmt.setString(1, null);
			stmt.setString(2, memberId);
			stmt.setString(1, task.getTitle());
			stmt.setDate(2, task.getStartDate());
			stmt.setDate(3, task.getEndDate());
			stmt.setInt(4, task.getState());
			stmt.setString(5, (task.getWorker() == null ? null : task.getWorker().getId()));
			stmt.setString(6, task.getTeam().getName());
			stmt.setInt(7, task.getNo());
			
			return stmt.executeUpdate();
		}
	}
	*/
	
	
	public int update(Task task) throws Exception {
		
		try (
			Connection con = DriverManager.getConnection(
					jdbcUrl, username, password);
			PreparedStatement stmt = con.prepareStatement(
					"update pms2_task set titl=?,sdt=?,edt=?,stat=?,mid=?,tnm=? where tano=?");) {

			stmt.setString(1, task.getTitle());
			stmt.setDate(2, task.getStartDate());
			stmt.setDate(3, task.getEndDate());
			stmt.setInt(4, task.getState());
			stmt.setString(5, (task.getWorker() == null ? null : task.getWorker().getId()));
			stmt.setString(6, task.getTeam().getName());
			stmt.setInt(7, task.getNo());

			return stmt.executeUpdate();
		}
	}
	
	
	
	public int insert(Task task) throws Exception {
		
		try (
			Connection con = DriverManager.getConnection(
					jdbcUrl, username, password); 
			PreparedStatement stmt = con.prepareStatement(
					"insert into pms2_task(titl,sdt,edt,stat,mid,tnm) values(?,?,?,?,?,?)");) {

			stmt.setString(1, task.getTitle());
			stmt.setDate(2, task.getStartDate());
			stmt.setDate(3, task.getEndDate());
			stmt.setInt(4, ((Integer)task.getState()==null) ? 0:task.getState());
			stmt.setString(5, task.getWorker().getId());
			stmt.setString(6, task.getTeam().getName());
			
			return stmt.executeUpdate();
		}
	}
	

	
	public Task selectOne(int no) throws Exception {
		
		try (
			Connection con = DriverManager.getConnection(
					jdbcUrl, username, password); 
			PreparedStatement stmt = con.prepareStatement(
				"select titl,sdt,edt,stat,mid,tnm from pms2_task where tano=?");) {

			stmt.setInt(1, no);

			try (ResultSet rs = stmt.executeQuery();) {
				if (!rs.next()) {
					return null;
				}
				Task task = new Task();
				task.setTitle(rs.getString("titl"));
				task.setStartDate(rs.getDate("sdt"));
				task.setEndDate(rs.getDate("edt"));
				task.setState(rs.getInt("stat"));
				
				Member obj1 = new Member();
				obj1.setId(rs.getString("mid"));
				task.setWorker(obj1);
				
				Team obj2 = new Team();
				obj2.setName(rs.getString("tnm"));
				task.setTeam(obj2);
				
				return task;
			}
		}
	}
	
	
	
	public ArrayList<Task> selectList(String teamName)	throws Exception {
		
		try (
			Connection con = DriverManager.getConnection(
					jdbcUrl, username, password); 
			PreparedStatement stmt = con.prepareStatement(
					"select tano,titl,sdt,edt,stat,mid from pms2_task where tnm=?");) {
			stmt.setString(1, teamName);
			try (ResultSet rs = stmt.executeQuery()) {

				ArrayList<Task> list = new ArrayList<>();
				while (rs.next()) {
					
					Task task = new Task();
					task.setNo(rs.getInt("tano"));
					task.setTitle(rs.getString("titl"));
					task.setStartDate(rs.getDate("sdt"));
					task.setEndDate(rs.getDate("edt"));
					
					//TaskMapper.xml 참조
					Member obj = new Member();
					obj.setId(rs.getString("mid"));
					task.setWorker(obj);
					
					list.add(task);
				}
				return list;
			}
		}
	}
}
