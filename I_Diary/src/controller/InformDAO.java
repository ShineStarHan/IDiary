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
	// ������ �Է� (insert)
	public int getInformRegist(String send, String receive, String contents) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into informtbl " + "(no, send, receive, inform, date)" + " values "
				+ "(null, ?,?,?, now())";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, send);
			pstmt.setString(2, receive);
			pstmt.setString(3, contents);

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

	// ���� ��ü ����Ʈ (select)
	public ObservableList<Inform> getInformTotal(String className) {
		ObservableList<Inform> list = FXCollections.observableArrayList();
		String dml = "select * from informtbl where receive=? or send=?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
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

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
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
//	// ���� ���� ��� (delete)
//	public void getChildrenDelete(int no) throws Exception {
//		// �� ������ ó���� ���� SQL ��
//		String dml = "delete from informtbl where no = ?";
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
//			con = DBUtil.getConnection();
//
//			// �� SQL���� ������ ó�� ����� ����
//			pstmt = con.prepareStatement(dml);
//			pstmt.setInt(1, no);
//
//			// �� SQL���� ������ ó�� ����� ����
//			int i = pstmt.executeUpdate();
//
//			if (i == 1) {
//				Alert alert = new Alert(AlertType.INFORMATION);
//		          alert.setTitle("��� ����");
//		            alert.setHeaderText("��� ���� �Ϸ�!");
//		            alert.setContentText("������ �Ϸ�Ǿ����. (*'��'*)");
//
//				alert.showAndWait();
//
//			} else {
//				Alert alert = new Alert(AlertType.INFORMATION);
//	            alert.setTitle("��� ����");
//	            alert.setHeaderText("��� ���� ����!");
//	            alert.setContentText("�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
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
//				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//			}
//		}
//	}

	// ���� ��� (update tableName set �ʵ�� = �������� where ���ǳ���)
	public Inform getChildrenUpdate(Inform svo, int no) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "update useridtbl set " + " no=?, name=?, inform=?, date=? where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ ���� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
//			pstmt.setInt(1, svo.getNo());
//			pstmt.setString(2, svo.getName());
//			pstmt.setString(3, svo.getInform());
//			pstmt.setString(4, svo.getDate());

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

//			if (i == 1) {
//				ShareMethod.alertDisplay(1, "���� �Ϸ�!", svo.getName() + " ������ �Ϸ�Ǿ����ϴ�!", "���� (*'��'*)");
//
//			} else {
//				ShareMethod.alertDisplay(1, "���� ����!", svo.getName() + " ������ �����Ͽ����ϴ�!", "���� (;��;)");
//				return null;
//			}

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
		return svo;
	}

	// ���� ã�� ��� (select * from schoolchild where name like '%�ҿ�%';)// �� �л��� name��
	// �Է¹޾� ���� ��ȸ
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

	// �˸��� �޴� ��� �޺����� ����
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
