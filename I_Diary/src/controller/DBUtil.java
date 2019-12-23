package controller;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

// ����̹��� ����, ���̵�� �н������ ����Ÿ���̽� mysql�� �����û�Ѵ�-> ��ü�������� �ش�.
public class DBUtil {

	// 1. ����̹��� ����
	private static String driver = "com.mysql.jdbc.Driver";
	// 2. �����ͺ��̽� url ����
	private static String url = "jdbc:mysql://localhost/idiarydb";

	// 3. ����̹��� �����ϰ�, ����Ÿ���̽��� �����ϴ� �Լ�
	public static Connection getConnection() throws Exception {
		// 3.1. ����̹��� ����
		Class.forName(driver);
		// 3.2. �����ͺ��̽��� ����
		Connection con = DriverManager.getConnection(url, "root", "123456");
		return con;
	}

}