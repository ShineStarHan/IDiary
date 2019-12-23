package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Children;

public class ChildrenDAO {
	
	// 데이터 입력 (insert)
	public int getChildrenRegist(Children svo) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
		String dml = "insert into useridtbl"
				+ "(id, birthday, class, gender, parent, phone, address)" + " values "
				+ "(?, ?, ?, ?, ?, ?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int count=0;

		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 원아 정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml); //보안을 위해 사용
			pstmt.setString(1, svo.getChildrenName());
			pstmt.setString(2, svo.getBirthday());
			pstmt.setString(3, svo.getClassName());
			pstmt.setString(4, svo.getChildrenGender());
			pstmt.setString(5, svo.getParentName());
			pstmt.setString(6, svo.getPhone());
			pstmt.setString(7, svo.getAddress());
			
			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			count= pstmt.executeUpdate(); //mysql에서 번개모양(업데이트)를 하면 이문장을 실행

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
	
	
	public ObservableList<Children> getMyClassChildrenTotal(String className) {
		ObservableList<Children> list = FXCollections.observableArrayList();
		String dml = "select * from useridtbl where class=? ";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		Children childrenVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1,className);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				childrenVO = new Children(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				list.add(childrenVO);
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
	
	public ObservableList<Children> getMyImageChildrenTotal(String id) {
		ObservableList<Children> list = FXCollections.observableArrayList();
		String dml = "select * from useridtbl where class=? ";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		Children childrenVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				childrenVO = new Children(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7),rs.getString(8));
				list.add(childrenVO);
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
	
	// 원아 전체 리스트 (select)
	public ObservableList<Children> getChildrenTotal() {
		ObservableList<Children> list = FXCollections.observableArrayList();
		String dml = "select * from useridtbl";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		Children childrenVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				childrenVO = new Children(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				list.add(childrenVO);
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
	
	// 원아 삭제 기능 (delete)
	public void getChildrenDelete(String name) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "delete from useridtbl where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("원아 삭제");
	            alert.setHeaderText("원아 삭제 완료!");
	            alert.setContentText("삭제가 완료되었어요. (*'ㅅ'*)");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("원아 삭제");
	            alert.setHeaderText("원아 삭제 실패!");
	            alert.setContentText("다시 한 번 확인해 주세요. (;ㅅ;)");

				alert.showAndWait();
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
	}
	
	// 수정 기능 (update tableName set 필드명 = 수정내용 where 조건내용)
	public Children getChildrenUpdate(Children svo) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "update useridtbl set "
				+ " class=?, gender=?, parent=?, phone=?, address=? where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 수정한 원아 정보를 수정하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getClassName());
			pstmt.setString(2, svo.getChildrenGender());
			pstmt.setString(3, svo.getParentName());
			pstmt.setString(4, svo.getPhone());
			pstmt.setString(5, svo.getAddress());
			pstmt.setString(6, svo.getChildrenName());
			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();
			
			if (i == 1) {
				ShareMethod.alertDisplay(1, "수정 완료!", svo.getChildrenName() + " 원아의 정보", "수정에 성공했어요! (*'ㅂ'*)");

			} else {
				ShareMethod.alertDisplay(1, "수정 실패!", svo.getChildrenName() + " 원아의 정보", "수정에 실패했어요! (;ㅅ;)");
				return null;
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
		return svo;
	}
	
	public void getChildrenImageUpdate(String url,String id) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "update useridtbl set "
				+ " image=? where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		Children svo=new Children();
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

	// 원아 찾기 기능 (select * from schoolchild where name like '%소영%';)// ⑦ 학생의 name을
	// 입력받아 정보 조회
	public ObservableList<Children> getChildrenSearch(String name,String className) throws Exception {
		String dml = "select * from useridtbl where id like ? and class=?";
		ObservableList<Children> list = FXCollections.observableArrayList();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Children retval = null;

		String name2 = "%" + name + "%";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			pstmt.setString(2,className);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				retval = new Children(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8));
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
	
	public ArrayList<Children> getChildrenIdCheck(String name) throws Exception {
		ArrayList<Children> list = new ArrayList<Children>();
		String dml = "select * from useridtbl where id like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Children retval = null;
		String name2 = name;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new Children(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8));
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
	
	public String getChildrenClass(String name) {
		String dml = "select class from useridtbl where id like ?";
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
	
	public String getChildrenPassword(String name) {
		String dml = "select birthday from useridtbl where id like ?";
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
	
	public String getChildrenID(String name) {
		String dml = "select id from useridtbl where id like ?";
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
}
