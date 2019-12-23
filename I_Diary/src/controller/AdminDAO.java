package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Admin;
import model.Children;
import model.Notice;

public class AdminDAO {
	// 학부모 ID DB등록
	public int getChildrenRegist(Admin adm) throws Exception {
		// 데이터 처리를 위한 SQL 문
		String dml = "insert into adminidtbl " + "(id, password, name,class)" + " values " + "(?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			// System.out.println("11");
			con = DBUtil.getConnection();
			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml); // 보안을 위해 사용
			pstmt.setString(1, adm.getId());
			pstmt.setString(2, adm.getPassword());
			pstmt.setString(3, adm.getName());
			pstmt.setString(4, adm.getClassName());

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			count = pstmt.executeUpdate(); // mysql에서 번개모양(업데이트)를 하면 이문장을 실행

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

	// ID중복확인
	public ArrayList<Admin> getChildrenCheck(String name) throws Exception {
		ArrayList<Admin> list = new ArrayList<Admin>();
		String dml = "select * from adminidtbl where id like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Admin retval = null;
		String name2 = name;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new Admin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
				list.add(retval);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}

	// ID중복확인
		public ArrayList<Admin> getSeekAdminInfo(String name) throws Exception {
			ArrayList<Admin> list = new ArrayList<Admin>();
			String dml = "select * from adminidtbl where name like ?";

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Admin retval = null;
			String name2 = name;

			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, name2);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					retval = new Admin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
					list.add(retval);
				}
			} catch (SQLException se) {
				System.out.println(se);
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException se) {
				}
			}
			return list;
		}
	
	
	
	// Password 찾기
	public String getChildrenPassword(String name) {
		String dml = "select password from adminidtbl where id like ?";
		String str = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String retval = null;
		String name2 = name;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = rs.getString(1);
			}
			str = retval.toString();
			// ShareMethod.alertDisplay(4, "비번 찾기", "비밀번호는 "+str, "해당 비번으로 로그인 요망");
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return str;
	}
	
	//어드민 이름 가져오기
	public String getAdminName(String name) {
		String dml = "select name from adminidtbl where id like ?";
		String str = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String retval = null;
		String name2 = name;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = rs.getString(1);
			}
			str = retval.toString();
			// ShareMethod.alertDisplay(4, "비번 찾기", "비밀번호는 "+str, "해당 비번으로 로그인 요망");
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return str;
	}
	//어드민 ID가져오기
	public String getAdminID(String name) {
		String dml = "select id from adminidtbl where name like ?";
		String str = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String retval = null;
		String name2 = name;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = rs.getString(1);
			}
			str = retval.toString();
			// ShareMethod.alertDisplay(4, "비번 찾기", "비밀번호는 "+str, "해당 비번으로 로그인 요망");
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return str;
	}
	//어드민 클래스 가져오기
	public String getAdminClass(String name) {
		String dml = "select class from adminidtbl where id like ?";
		String str = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String retval = null;
		String name2 = name;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = rs.getString(1);
			}
			str = retval.toString();
			// ShareMethod.alertDisplay(4, "비번 찾기", "비밀번호는 "+str, "해당 비번으로 로그인 요망");
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return str;
	}
	
	public String adminLoginID(String name) {
	      String dml = "select id from adminidtbl where id like ?";
	      String str = null;
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String retval = null;
	      String name2 = name;

	      try {
	         con = DBUtil.getConnection();
	         pstmt = con.prepareStatement(dml);
	         pstmt.setString(1, name2);
	         rs = pstmt.executeQuery();
	         while (rs.next()) {
	            retval = rs.getString(1);
	         }
	         str = retval.toString();
	         // ShareMethod.alertDisplay(4, "비번 찾기", "비밀번호는 "+str, "해당 비번으로 로그인 요망");
	      } catch (SQLException se) {
	         System.out.println(se);
	      } catch (Exception e) {
	         System.out.println(e);
	      } finally {
	         try {
	            if (rs != null)
	               rs.close();
	            if (pstmt != null)
	               pstmt.close();
	            if (con != null)
	               con.close();
	         } catch (SQLException se) {
	         }
	      }
	      return str;
	   }

	// 선생님 정보 수정
	public int getAdminUpdate(Admin adm) throws Exception {
		// 데이터 처리를 위한 SQL 문
		String dml = "update adminidtbl set password=?,name=?,class=? where id=?";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			// System.out.println("11");
			con = DBUtil.getConnection();
			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml); // 보안을 위해 사용
			pstmt.setString(1, adm.getPassword());
			pstmt.setString(2, adm.getName());
			pstmt.setString(3, adm.getClassName());
			pstmt.setString(4, adm.getId());

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			count = pstmt.executeUpdate(); // mysql에서 번개모양(업데이트)를 하면 이문장을 실행

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}
	//같은반 아이 이름 뽑아오기
	public ObservableList<String> getInformReceiverSet(String name) throws Exception {
		ObservableList<String> list = FXCollections.observableArrayList();
		String dml = "select id from useridtbl where class like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String retval = null;
		String name2 = name;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = rs.getString(1);
				list.add(retval);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}
	
	public void getAdminImageUpdate(String url,String id) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "update adminidtbl set image=? where id=?";
				
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 수정한 원아 정보를 수정하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, url);
			pstmt.setString(2, id);
			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				ShareMethod.alertDisplay(1, "수정 완료!", " 수정이 완료되었습니다!", "성공 (*'ㅂ'*)");

			} else {
				ShareMethod.alertDisplay(1, "수정 실패!", " 수정이 실패하였습니다!", "실패 (;ㅅ;)");
				return ;
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return ;
	}
	
}
