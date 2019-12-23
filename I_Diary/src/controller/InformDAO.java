package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Inform;

public class InformDAO {
	// 데이터 입력 (insert)
	public int getInformRegist(String send, String receive, String contents) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
		String dml = "insert into informtbl " + "(no, send, receive, inform, date)" + " values "
				+ "(null, ?,?,?, now())";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml); // 보안을 위해 사용
			pstmt.setString(1, send);
			pstmt.setString(2, receive);
			pstmt.setString(3, contents);

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

	// 원아 전체 리스트 (select)
	public ObservableList<Inform> getInformTotal(String className) {
		ObservableList<Inform> list = FXCollections.observableArrayList();
		String dml = "select * from informtbl where receive=? or send=?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		Inform InformVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, className);
			pstmt.setString(2, className);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				InformVO = new Inform(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				list.add(InformVO);
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

	public ObservableList<Inform> getInformUserTotal(String id) {
		ObservableList<Inform> list = FXCollections.observableArrayList();
		String dml = "select * from informtbl where receive=? or send=?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		Inform InformVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, id);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				InformVO = new Inform(rs.getInt(1), rs.getString(2), rs.getString(3),  rs.getString(5));
				list.add(InformVO);
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
//	// 원아 삭제 기능 (delete)
//	public void getChildrenDelete(int no) throws Exception {
//		// ② 데이터 처리를 위한 SQL 문
//		String dml = "delete from informtbl where no = ?";
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
//			con = DBUtil.getConnection();
//
//			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
//			pstmt = con.prepareStatement(dml);
//			pstmt.setInt(1, no);
//
//			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
//			int i = pstmt.executeUpdate();
//
//			if (i == 1) {
//				Alert alert = new Alert(AlertType.INFORMATION);
//		          alert.setTitle("목록 삭제");
//		            alert.setHeaderText("목록 삭제 완료!");
//		            alert.setContentText("삭제가 완료되었어요. (*'ㅅ'*)");
//
//				alert.showAndWait();
//
//			} else {
//				Alert alert = new Alert(AlertType.INFORMATION);
//	            alert.setTitle("목록 삭제");
//	            alert.setHeaderText("목록 삭제 실패!");
//	            alert.setContentText("다시 한 번 확인해 주세요. (;ㅅ;)");
//
//				alert.showAndWait();
//			}
//
//		} catch (SQLException e) {
//			System.out.println("e=[" + e + "]");
//		} catch (Exception e) {
//			System.out.println("e=[" + e + "]");
//		} finally {
//			try {
//				// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//			}
//		}
//	}

	// 수정 기능 (update tableName set 필드명 = 수정내용 where 조건내용)
	public Inform getChildrenUpdate(Inform svo, int no) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "update useridtbl set " + " no=?, name=?, inform=?, date=? where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 수정한 원아 정보를 수정하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
//			pstmt.setInt(1, svo.getNo());
//			pstmt.setString(2, svo.getName());
//			pstmt.setString(3, svo.getInform());
//			pstmt.setString(4, svo.getDate());

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

//			if (i == 1) {
//				ShareMethod.alertDisplay(1, "수정 완료!", svo.getName() + " 수정이 완료되었습니다!", "성공 (*'ㅂ'*)");
//
//			} else {
//				ShareMethod.alertDisplay(1, "수정 실패!", svo.getName() + " 수정이 실패하였습니다!", "실패 (;ㅅ;)");
//				return null;
//			}

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

	// 원아 찾기 기능 (select * from schoolchild where name like '%소영%';)// ⑦ 학생의 name을
	// 입력받아 정보 조회
	public ArrayList<Inform> getStudentCheck(String name) throws Exception {
		String dml = "select * from useridtbl where name like ?";
		ArrayList<Inform> list = new ArrayList<Inform>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Inform retval = null;

		String name2 = "%" + name + "%";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// retval = new Inform(rs.getInt(1), rs.getString(2), rs.getString(3),
				// rs.getString(4));
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

	// 알림장 받는 사람 콤보상자 세팅
	public ObservableList<String> getInformReceiverSet(String name) throws Exception {
		ObservableList<String> list = FXCollections.observableArrayList();
		String dml = "select name from useidtbl where class like ?";

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
}
