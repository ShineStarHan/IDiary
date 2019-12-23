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
	// ������ �Է� (insert)
	public int getMedicinRegist(Medicine svo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into medicinetbl " + "(no, send, receive, time,capacity,keep,ref,date)" + " values "
				+ "(null, ?, ?, ?,?,?,?,now())";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, svo.getSend());
			pstmt.setString(2, svo.getReceive());
			pstmt.setString(3, svo.getTime());
			pstmt.setString(4, svo.getCapacity());
			pstmt.setString(5, svo.getKeep());
			pstmt.setString(6, svo.getRef());

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

	// �����Ƿڼ� ���̺�丮��Ʈ (select)
	public ObservableList<Medicine> getMedicineTotal(String id) {
		ObservableList<Medicine> list = FXCollections.observableArrayList();
		String dml = "select no,send,ref,date from medicinetbl where send=? or receive=?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
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
	
	//���뺸��
	public ObservableList<Medicine> getMedicineView(int num) {
		ObservableList<Medicine> list = FXCollections.observableArrayList();
		String dml = "select * from medicinetbl where no=?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
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
	
	
	// ���� ���� ��� (delete)
	public void getChildrenDelete(int no) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from medicinetbl where no = ?";
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
				alert.setTitle("���� ����");
				alert.setHeaderText("���� ���� �Ϸ�.");
				alert.setContentText("���� ���� ����!!!");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� ����");
				alert.setHeaderText("���� ���� ����.");
				alert.setContentText("���� ���� ����!!!");

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

	// ���� ��� (update tableName set �ʵ�� = �������� where ���ǳ���)
	public Medicine getChildrenUpdate(Medicine svo, int no) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "update schoolchild set " + " no=?, name=?, medicine=?, date=? where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ ���� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, svo.getNo());
			pstmt.setString(2, svo.getSend());
			pstmt.setString(3, svo.getMedicine());
			pstmt.setString(4, svo.getDate());

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				ShareMethod.alertDisplay(1, "���� �Ϸ�!", svo.getSend() + " ������ �Ϸ�Ǿ����ϴ�!", "���� (*'��'*)");

			} else {
				ShareMethod.alertDisplay(1, "���� ����!", svo.getSend() + " ������ �����Ͽ����ϴ�!", "���� (;��;)");
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
		return svo;
	}
}
