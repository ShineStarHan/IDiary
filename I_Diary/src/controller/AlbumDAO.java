package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Album;

public class AlbumDAO {

	// 테이블뷰리스트 (select)
	public ObservableList<Album> getAlbumTotal() {
		ObservableList<Album> list = FXCollections.observableArrayList();
		String dml = "select * from albumtbl";

		Connection con = null;
		PreparedStatement pstmt = null;

		// 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
		ResultSet rs = null;
		Album albumVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				albumVO = new Album(rs.getString(2));
				list.add(albumVO);
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

	// 수정 기능 (update tableName set 필드명 = 수정내용 where 조건내용)
	public Album getAlbumUpdate(Album album, int no) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "update albumtbl set " + " image=? where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 수정한 원아 정보를 수정하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, album.getImage());
			pstmt.setInt(2, no);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				ShareMethod.alertDisplay(1, "수정 완료!", "사진수정 완료!", "성공 (*'ㅅ'*)");

			} else {
				ShareMethod.alertDisplay(1, "수정 실패!", "사진수정 실패!", "실패 (;ㅅ;)");
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
		return album;
	}
}