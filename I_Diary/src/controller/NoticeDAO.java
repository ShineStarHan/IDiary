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
import model.Notice;

public class NoticeDAO {

	// 데이터 입력 (insert)
	public int getNoticeRegist(Notice svo) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
		String dml = "insert into noticetbl " + "(no, title, contents,date,class)" + " values "
				+ "(null, ?, ?, now(), ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml); // 보안을 위해 사용
			pstmt.setString(1, svo.getTitle());
			pstmt.setString(2, svo.getContents());
			pstmt.setString(3, svo.getClassName());

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

	// 공지사항 리스트
	public ObservableList<Notice> getChildrenTotal(String className) {
		ObservableList<Notice> list = FXCollections.observableArrayList();
		String dml = "select no,title,date from noticetbl where class like ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		Notice NoticeVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, className);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NoticeVO = new Notice(rs.getInt(1), rs.getString(2), rs.getString(3));
				list.add(NoticeVO);
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

	// 공지사항 리스트
	public ObservableList<Notice> getEditTotal(int index) {
		ObservableList<Notice> list = FXCollections.observableArrayList();
		String dml = "select title,contents from noticetbl where no like ? order by no";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		Notice NoticeVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, index);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NoticeVO = new Notice(rs.getString(1), rs.getString(2));
				list.add(NoticeVO);
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
		String dml = "delete from noticetbl where no = ?";
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
				alert.setTitle("목록 삭제");
				alert.setHeaderText("목록 삭제 완료!");
				alert.setContentText("목록이 삭제되었어요. (*'ㅅ'*)");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("목록 삭제");
				alert.setHeaderText("목록 삭제 실패!");
				alert.setContentText("다시 확인해 주세요. (;ㅅ;)");

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

	// 원아 찾기 기능 (select * from schoolchild where name like '%소영%';)// ⑦ 학생의 name을
	// 입력받아 정보 조회
	public ArrayList<Notice> getStudentCheck(String name) throws Exception {
		String dml = "select * from schoolchild where name like ?";
		ArrayList<Notice> list = new ArrayList<Notice>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice retval = null;

		String name2 = "%" + name + "%";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				retval = new Notice(rs.getInt(1), rs.getString(2), rs.getString(3));
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

	// 공지사항 내용 수정
	public int getNoticeUpdate(Notice adm, int no) throws Exception {
		// 데이터 처리를 위한 SQL 문
		String dml = "update noticetbl set title=?,contents=? where no=?";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			// System.out.println("11");
			con = DBUtil.getConnection();
			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml); // 보안을 위해 사용
			pstmt.setString(1, adm.getTitle());
			pstmt.setString(2, adm.getContents());
			pstmt.setInt(3, no);

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

}