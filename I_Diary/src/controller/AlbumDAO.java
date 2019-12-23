package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Album;

public class AlbumDAO {

	// ���̺�丮��Ʈ (select)
	public ObservableList<Album> getAlbumTotal() {
		ObservableList<Album> list = FXCollections.observableArrayList();
		String dml = "select * from albumtbl";

		Connection con = null;
		PreparedStatement pstmt = null;

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
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

	// ���� ��� (update tableName set �ʵ�� = �������� where ���ǳ���)
	public Album getAlbumUpdate(Album album, int no) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "update albumtbl set " + " image=? where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ ���� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, album.getImage());
			pstmt.setInt(2, no);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				ShareMethod.alertDisplay(1, "���� �Ϸ�!", "�������� �Ϸ�!", "���� (*'��'*)");

			} else {
				ShareMethod.alertDisplay(1, "���� ����!", "�������� ����!", "���� (;��;)");
				return null;
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
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