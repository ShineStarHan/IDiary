package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Attend;

public class AttendDAO {

	// ������ �Է� (insert)
	public int getChildrenregiste(Attend svo, String className) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into attendtbl " + "(class, month, name, attend, absent, early)" + " values "
				+ "(?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, className);
			pstmt.setString(2, svo.getMonth());
			pstmt.setString(3, svo.getName());
			pstmt.setInt(4, svo.getAttend());
			pstmt.setInt(5, svo.getAbsent());
			pstmt.setInt(6, svo.getEarly());

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
	public ArrayList<Attend> getChildrenTotal(String className) {
		ArrayList<Attend> list = new ArrayList<Attend>();
		String dml = "select * from attendtbl where class=?";

		Connection con = null;
		PreparedStatement pstmt = null;

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
		ResultSet rs = null;
		Attend attendVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, className);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				attendVO = new Attend(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
				list.add(attendVO);
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

	   //��������
	   public ArrayList<Attend> getSelectedList(String month,String className) {
		      ArrayList<Attend> list = new ArrayList<Attend>();
		      String dml = "select * from attendtbl where month = ? and class=?";

		      Connection con = null;
		      PreparedStatement pstmt = null;

		      // �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
		      ResultSet rs = null;
		      Attend attendVO = null;

		      try {
		         con = DBUtil.getConnection();
		         pstmt = con.prepareStatement(dml);
		         pstmt.setString(1, month);
		         pstmt.setString(2,className);
		         rs = pstmt.executeQuery();

		         while (rs.next()) {
		            attendVO = new Attend(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),rs.getInt(6));
		            list.add(attendVO);
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
	   
	   //��������
	   public ArrayList<Attend> SelectedList(String month,String name) {
		      ArrayList<Attend> list = new ArrayList<Attend>();
		      String dml = "select * from attendtbl where month = ? and name=?";

		      Connection con = null;
		      PreparedStatement pstmt = null;

		      // �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
		      ResultSet rs = null;
		      Attend attendVO = null;

		      try {
		         con = DBUtil.getConnection();
		         pstmt = con.prepareStatement(dml);
		         pstmt.setString(1, month);
		         pstmt.setString(2, name);
		         rs = pstmt.executeQuery();

		         while (rs.next()) {
		            attendVO = new Attend( rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),rs.getInt(6));
		            list.add(attendVO);
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
	public void getChildrenDelete(String name, String month) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from attendtbl where name = ? and month = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name);
			pstmt.setString(2, month);
			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i >= 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��� ����");
				alert.setHeaderText("��� ���� �Ϸ�!");
				alert.setContentText("������ �Ϸ�Ǿ����. (*'��'*)");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��� ����");
				alert.setHeaderText("��� ���� ����!");
				alert.setContentText("�ٽ� �� �� Ȯ���� �ּ���. (;��;)");

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
	public Attend getChildrenUpdate(Attend svo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "update attendtbl set "
				+ " month=?, name=?, attend=?, absent=?, early=? where month = ? and name=?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ ���� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getMonth());
			pstmt.setString(2, svo.getName());
			pstmt.setInt(3, svo.getAttend());
			pstmt.setInt(4, svo.getAbsent());
			pstmt.setInt(5, svo.getEarly());
			pstmt.setString(6, svo.getMonth());
			pstmt.setString(7, svo.getName());
			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				ShareMethod.alertDisplay(1, "���� �Ϸ�!", svo.getName() + " ������ �⼮��", "������ �����߾��! (*'��'*)");

			} else {
				ShareMethod.alertDisplay(1, "���� ����!", svo.getName() + " ������ �⼮��", "������ �����߾��! (;��;)");
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

	// ���� ã�� ��� (select * from schoolchild where name like '%�ҿ�%';)// �� �л��� name��
	// �Է¹޾� ���� ��ȸ
	public ArrayList<Attend> getChildrenCheck(String name) throws Exception {
		String dml = "select * from attendtbl where name like ?";
		ArrayList<Attend> list = new ArrayList<Attend>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Attend retval = null;

		String name2 = "%" + name + "%";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				retval = new Attend(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
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