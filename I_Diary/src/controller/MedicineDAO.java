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
import model.Medicine;

public class MedicineDAO {
	// 데이터 입력 (insert)
	public int getMedicinRegist(Medicine svo) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
		String dml = "insert into medicinetbl " + "(no, send, receive, time,capacity,keep,ref,date)" + " values "
				+ "(null, ?, ?, ?,?,?,?,now())";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml); // 보안을 위해 사용
			pstmt.setString(1, svo.getSend());
			pstmt.setString(2, svo.getReceive());
			pstmt.setString(3, svo.getTime());
			pstmt.setString(4, svo.getCapacity());
			pstmt.setString(5, svo.getKeep());
			pstmt.setString(6, svo.getRef());

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

	// 투약의뢰서 테이블뷰리스트 (select)
	public ObservableList<Medicine> getMedicineTotal(String id) {
		ObservableList<Medicine> list = FXCollections.observableArrayList();
		String dml = "select no,send,ref,date from medicinetbl where send=? or receive=?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		Medicine MedicineVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, id);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MedicineVO = new Medicine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				list.add(MedicineVO);
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
	
	//내용보기
	public ObservableList<Medicine> getMedicineView(int num) {
		ObservableList<Medicine> list = FXCollections.observableArrayList();
		String dml = "select * from medicinetbl where no=?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		Medicine MedicineVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MedicineVO = new Medicine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
				list.add(MedicineVO);
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
	public void getChildrenDelete(int no) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "delete from medicinetbl where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("원아 삭제");
				alert.setHeaderText("원아 삭제 완료.");
				alert.setContentText("원아 삭제 성공!!!");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("원아 삭제");
				alert.setHeaderText("원아 삭제 실패.");
				alert.setContentText("원아 삭제 실패!!!");

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
	public Medicine getChildrenUpdate(Medicine svo, int no) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "update schoolchild set " + " no=?, name=?, medicine=?, date=? where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 수정한 원아 정보를 수정하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, svo.getNo());
			pstmt.setString(2, svo.getSend());
			pstmt.setString(3, svo.getMedicine());
			pstmt.setString(4, svo.getDate());

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				ShareMethod.alertDisplay(1, "수정 완료!", svo.getSend() + " 수정이 완료되었습니다!", "성공 (*'ㅂ'*)");

			} else {
				ShareMethod.alertDisplay(1, "수정 실패!", svo.getSend() + " 수정이 실패하였습니다!", "실패 (;ㅅ;)");
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
}
