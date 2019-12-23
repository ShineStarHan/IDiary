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
import model.Children;

public class ChildrenDAO {
	
	// ������ �Է� (insert)
	public int getChildrenRegist(Children svo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into useridtbl"
				+ "(id, birthday, class, gender, parent, phone, address)" + " values "
				+ "(?, ?, ?, ?, ?, ?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int count=0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� ���� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); //������ ���� ���
			pstmt.setString(1, svo.getChildrenName());
			pstmt.setString(2, svo.getBirthday());
			pstmt.setString(3, svo.getClassName());
			pstmt.setString(4, svo.getChildrenGender());
			pstmt.setString(5, svo.getParentName());
			pstmt.setString(6, svo.getPhone());
			pstmt.setString(7, svo.getAddress());
			
			// �� SQL���� ������ ó�� ����� ����
			count= pstmt.executeUpdate(); //mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

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
	
	
	public ObservableList<Children> getMyClassChildrenTotal(String className) {
		ObservableList<Children> list = FXCollections.observableArrayList();
		String dml = "select * from useridtbl where class=? ";

		Connection con = null;
		PreparedStatement pstmt = null;

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
		ResultSet rs = null;
		Children childrenVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1,className);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				childrenVO = new Children(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				list.add(childrenVO);
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
	
	public ObservableList<Children> getMyImageChildrenTotal(String id) {
		ObservableList<Children> list = FXCollections.observableArrayList();
		String dml = "select * from useridtbl where class=? ";

		Connection con = null;
		PreparedStatement pstmt = null;

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
		ResultSet rs = null;
		Children childrenVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				childrenVO = new Children(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7),rs.getString(8));
				list.add(childrenVO);
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
	
	// ���� ��ü ����Ʈ (select)
	public ObservableList<Children> getChildrenTotal() {
		ObservableList<Children> list = FXCollections.observableArrayList();
		String dml = "select * from useridtbl";

		Connection con = null;
		PreparedStatement pstmt = null;

		// �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
		ResultSet rs = null;
		Children childrenVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				childrenVO = new Children(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				list.add(childrenVO);
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
	public void getChildrenDelete(String name) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from useridtbl where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("���� ����");
	            alert.setHeaderText("���� ���� �Ϸ�!");
	            alert.setContentText("������ �Ϸ�Ǿ����. (*'��'*)");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("���� ����");
	            alert.setHeaderText("���� ���� ����!");
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
	public Children getChildrenUpdate(Children svo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "update useridtbl set "
				+ " class=?, gender=?, parent=?, phone=?, address=? where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ ���� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, svo.getClassName());
			pstmt.setString(2, svo.getChildrenGender());
			pstmt.setString(3, svo.getParentName());
			pstmt.setString(4, svo.getPhone());
			pstmt.setString(5, svo.getAddress());
			pstmt.setString(6, svo.getChildrenName());
			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();
			
			if (i == 1) {
				ShareMethod.alertDisplay(1, "���� �Ϸ�!", svo.getChildrenName() + " ������ ����", "������ �����߾��! (*'��'*)");

			} else {
				ShareMethod.alertDisplay(1, "���� ����!", svo.getChildrenName() + " ������ ����", "������ �����߾��! (;��;)");
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
	
	public void getChildrenImageUpdate(String url,String id) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "update useridtbl set "
				+ " image=? where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		Children svo=new Children();
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

	// ���� ã�� ��� (select * from schoolchild where name like '%�ҿ�%';)// �� �л��� name��
	// �Է¹޾� ���� ��ȸ
	public ObservableList<Children> getChildrenSearch(String name,String className) throws Exception {
		String dml = "select * from useridtbl where id like ? and class=?";
		ObservableList<Children> list = FXCollections.observableArrayList();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Children retval = null;

		String name2 = "%" + name + "%";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			pstmt.setString(2,className);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				retval = new Children(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8));
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
	
	public ArrayList<Children> getChildrenIdCheck(String name) throws Exception {
		ArrayList<Children> list = new ArrayList<Children>();
		String dml = "select * from useridtbl where id like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Children retval = null;
		String name2 = name;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new Children(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8));
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
	
	public String getChildrenClass(String name) {
		String dml = "select class from useridtbl where id like ?";
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
	
	public String getChildrenPassword(String name) {
		String dml = "select birthday from useridtbl where id like ?";
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
	
	public String getChildrenID(String name) {
		String dml = "select id from useridtbl where id like ?";
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
}
