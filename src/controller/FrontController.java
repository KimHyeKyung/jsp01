package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("reg")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("서버에 요청하면 doGet이 실행됩니다.");
		request.setCharacterEncoding("utf-8");
		
		String name = request.getParameter("name");
		int sal = Integer.parseInt( request.getParameter("sal") ); //Integer.parseInt: 문자형타입을 숫자형으로 변환 
		
		System.out.println("name: "+name);
		System.out.println("sal: "+sal);
		
//--------------------------------------------------------------------------------------------------	
//DB
		try {
			Class.forName("oracle.jdbc.OracleDriver").getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String user="the";
		String password="oracle";
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println("DB연결완료");
			String sql="Insert into test values(seq_test.nextval,?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, sal);
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
