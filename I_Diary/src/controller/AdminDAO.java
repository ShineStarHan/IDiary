package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Admin;
import model.Children;
import model.Notice;

public class AdminDAO {
	// �кθ� ID DB���
	public int getChildrenRegist(Admin adm) throws Exception {
		// ������ ó���� ���� SQL ��
		String dml = "insert into adminidtbl " + "(id, password, name,class)" + " values " + "(?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			// System.out.println("11");
			con = DBUtil.getConnection();
			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, adm.getId());
			pstmt.setString(2, adm.getPassword());
			pstmt.setString(3, adm.getName());
			pstmt.setString(4, adm.getClassName());

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

	// ID�ߺ�Ȯ��
	public ArrayList<Admin> getChildrenCheck(String name) throws Exception {
		ArrayList<Admin> list = new ArrayList<Admin>();
		String dml = "select * from adminidtbl where id like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Admin retval = null;
		String name2 = name;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new Admin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
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

	// ID�ߺ�Ȯ��
		public ArrayList<Admin> getSeekAdminInfo(String name) throws Exception {
			ArrayList<Admin> list = new ArrayList<Admin>();
			String dml = "select * from adminidtbl where name like ?";

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Admin retval = null;
			String name2 = name;

			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, name2);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					retval = new Admin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
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
	
	
	
	// Password ã��
	public String getChildrenPassword(String name) {
		String dml = "select password from adminidtbl where id like ?";
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
			// ShareMethod.alertDisplay(4, "��� ã��", "��й�ȣ�� "+str, "�ش� ������� �α��� ���");
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
	
	//���� �̸� ��������
	public String getAdminName(String name) {
		String dml = "select name from adminidtbl where id like ?";
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
			// ShareMethod.alertDisplay(4, "��� ã��", "��й�ȣ�� "+str, "�ش� ������� �α��� ���");
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
	//���� ID��������
	public String getAdminID(String name) {
		String dml = "select id from adminidtbl where name like ?";
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
			// ShareMethod.alertDisplay(4, "��� ã��", "��й�ȣ�� "+str, "�ش� ������� �α��� ���");
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
	//���� Ŭ���� ��������
	public String getAdminClass(String name) {
		String dml = "select class from adminidtbl where id like ?";
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
			// ShareMethod.alertDisplay(4, "��� ã��", "��й�ȣ�� "+str, "�ش� ������� �α��� ���");
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
	
	public String adminLoginID(String name) {
	      String dml = "select id from adminidtbl where id like ?";
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
	         // ShareMethod.alertDisplay(4, "��� ã��", "��й�ȣ�� "+str, "�ش� ������� �α��� ���");
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

	// ������ ���� ����
	public int getAdminUpdate(Admin adm) throws Exception {
		// ������ ó���� ���� SQL ��
		String dml = "update adminidtbl set password=?,name=?,class=? where id=?";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			// System.out.println("11");
			con = DBUtil.getConnection();
			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, adm.getPassword());
			pstmt.setString(2, adm.getName());
			pstmt.setString(3, adm.getClassName());
			pstmt.setString(4, adm.getId());

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
	//������ ���� �̸� �̾ƿ���
	public ObservableList<String> getInformReceiverSet(String name) throws Exception {
		ObservableList<String> list = FXCollections.observableArrayList();
		String dml = "select id from useridtbl where class like ?";

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
	
	public void getAdminImageUpdate(String url,String id) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "update adminidtbl set image=? where id=?";
				
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ ���� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, url);
			pstmt.setString(2, id);
			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				ShareMethod.alertDisplay(1, "���� �Ϸ�!", " ������ �Ϸ�Ǿ����ϴ�!", "���� (*'��'*)");

			} else {
				ShareMethod.alertDisplay(1, "���� ����!", " ������ �����Ͽ����ϴ�!", "���� (;��;)");
				return ;
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
		return ;
	}
	
}
