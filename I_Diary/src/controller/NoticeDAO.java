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

	// ������ �Է� (insert)
	public int getNoticeRegist(Notice svo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into noticetbl " + "(no, title, contents,date,class)" + " values "
				+ "(null, ?, ?, now(), ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, svo.getTitle());
			pstmt.setString(2, svo.getContents());
			pstmt.setString(3, svo.getClassName());

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

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
		return count;
	}

	// �������� ����Ʈ
	public ObservableList<Notice> getChildrenTotal(String className) {
		ObservableList<Notice> list = FXCollections.observableArrayList();
		String dml = "select no,title,date from noticetbl where class like ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
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

	// �������� ����Ʈ
	public ObservableList<Notice> getEditTotal(int index) {
		ObservableList<Notice> list = FXCollections.observableArrayList();
		String dml = "select title,contents from noticetbl where no like ? order by no";

		Connection con = null;
		PreparedStatement pstmt = null;

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
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

	// ���� ���� ��� (delete)
	public void getChildrenDelete(int no) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from noticetbl where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��� ����");
				alert.setHeaderText("��� ���� �Ϸ�!");
				alert.setContentText("����� �����Ǿ����. (*'��'*)");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��� ����");
				alert.setHeaderText("��� ���� ����!");
				alert.setContentText("�ٽ� Ȯ���� �ּ���. (;��;)");

				alert.showAndWait();
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
	}

	// ���� ã�� ��� (select * from schoolchild where name like '%�ҿ�%';)// �� �л��� name��
	// �Է¹޾� ���� ��ȸ
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

	// �������� ���� ����
	public int getNoticeUpdate(Notice adm, int no) throws Exception {
		// ������ ó���� ���� SQL ��
		String dml = "update noticetbl set title=?,contents=? where no=?";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			// System.out.println("11");
			con = DBUtil.getConnection();
			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, adm.getTitle());
			pstmt.setString(2, adm.getContents());
			pstmt.setInt(3, no);

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

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
		return count;
	}

}