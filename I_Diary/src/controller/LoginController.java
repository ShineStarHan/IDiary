package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Admin;
import model.Children;

public class LoginController implements Initializable {
	@FXML
	private TextField txtId;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private ComboBox<String> cbUser;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnRegist;
	@FXML
	private Button btnSearchPassword;
	@FXML
	private Button btnSearchAdmin;
	static int select = 500;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 1. ��ư Ȯ��(�α���) �̺�Ʈ ó��
		btnLogin.setOnAction(e -> handlerBtnLoginAction(e));
		// 2. ��ư ��� �̺�Ʈ ó��
		btnExit.setOnAction(e -> handlerBtnCancelAction(e));
		// 3. ������ ��� �̺�Ʈ ó��
		btnRegist.setOnAction(e -> handlerBtnRegistAction(e));
		// 4. �кθ� ��й�ȣ ã�� �̺�Ʈ ó��
		btnSearchPassword.setOnAction(e -> handlerBtnSearchPasswordAction(e));

		btnSearchAdmin.setOnAction(e -> handlerBtnSearchAdmindAction(e));
//		txtId.setText("�����");
//		txtPassword.setText("1234");
		userTypeSetting();
	}

	public void handlerBtnSearchAdmindAction(ActionEvent e) {
		Parent registRoot;
		try {
			registRoot = FXMLLoader.load(getClass().getResource("/view/admin_search_id_password_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnSearchPassword.getScene().getWindow());
			stage.setTitle("������ �α��� ���� ã��");
			Button btnRegist = (Button) registRoot.lookup("#btnSearch");
			Button btnExit = (Button) registRoot.lookup("#btnExit");
			TextField txtId = (TextField) registRoot.lookup("#txtId");
			ComboBox<String> cbClass = (ComboBox<String>) registRoot.lookup("#cbClass");
			ObservableList<String> listClass = FXCollections.observableArrayList();
			listClass.add("�̽��� (5��)");
			listClass.add("Ǯ�ٹ� (6��)");
			listClass.add("���ٹ� (7��)");
			cbClass.setItems(listClass);
			btnRegist.setOnAction(event -> {
				AdminDAO ad = new AdminDAO();
				ArrayList<Admin> list=new ArrayList<Admin>();
				try {
					list = ad.getSeekAdminInfo(txtId.getText());
					if (list.size() == 1) {
						String seekID = list.get(0).getId();
						String seekPassword = list.get(0).getPassword();
						String seekClass = list.get(0).getClassName();
						if (cbClass.getSelectionModel().getSelectedItem().equals(seekClass)) {
							ShareMethod.alertDisplay(5, "���̵� ��й�ȣ ã��!", "���̵� ��й�ȣ ã�� ����!",
									"���̵� : [ " + seekID + " ]\n" + "��й�ȣ : [ " + seekPassword + " ] (*'��'*)");
						} else {
							throw new Exception();
						}
					}
				} catch (Exception e1) {
					ShareMethod.alertDisplay(1, "���� ã�� ����!", "������ �������� �ʽ��ϴ�!", "�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
					e1.printStackTrace();
				}
			});
			Scene scene = new Scene(registRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event2 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (Exception e1) {
			// TODO: handle exception
		}

	}

	// �кθ� ��й�ȣ ã��
	private void handlerBtnSearchPasswordAction(ActionEvent e) {
		Parent registRoot;
		try {
			registRoot = FXMLLoader.load(getClass().getResource("/view/user_search_password_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnSearchPassword.getScene().getWindow());
			stage.setTitle("�кθ� ��й�ȣ ã��");
			Button btnRegist = (Button) registRoot.lookup("#btnSearch");
			Button btnExit = (Button) registRoot.lookup("#btnExit");
			TextField txtId = (TextField) registRoot.lookup("#txtId");
			TextField txtPhone = (TextField) registRoot.lookup("#txtPhone");
			Scene scene = new Scene(registRoot);
			stage.setScene(scene);
			stage.show();

			ChildrenDAO cd = new ChildrenDAO();
			btnRegist.setOnAction(event -> {
				try {
					ArrayList<Children> list = cd.getChildrenIdCheck(txtId.getText());
					System.out.println("dd"+list.size());
					if (list.size() == 1) {
						
						String pass = list.get(0).getPhone();
						String name = list.get(0).getChildrenName();
					
						if (txtId.getText().equals(name) && txtPhone.getText().equals(pass)) {
							ShareMethod.alertDisplay(5, "��й�ȣ ã��!", "��й�ȣ ã�� ����!",
									"��й�ȣ�� [ " + list.get(0).getBirthday() + " ] �Դϴ�. (*'��'*)");
						} else {
							throw new Exception();
						}
					}else {
						throw new Exception();
					}

				} catch (Exception e1) {
					ShareMethod.alertDisplay(1, "���� ã�� ����!", "������ �������� �ʽ��ϴ�!", "�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
					e1.printStackTrace();
				}

			});

			btnExit.setOnAction(event2 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (Exception e1) {
		}
	}

	// ������ ����ϱ�
	public void handlerBtnRegistAction(ActionEvent e) {
		Parent registRoot;
		try {
			registRoot = FXMLLoader.load(getClass().getResource("/view/regist.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnRegist.getScene().getWindow());
			stage.setTitle("������ ���");
			Button btnRegist = (Button) registRoot.lookup("#btnRegist");
			Button btnExit = (Button) registRoot.lookup("#btnExit");
			TextField txtId = (TextField) registRoot.lookup("#txtId");
			PasswordField txtPassword = (PasswordField) registRoot.lookup("#txtPassword");
			PasswordField txtPasswordCheck = (PasswordField) registRoot.lookup("#txtPasswordCheck");
			TextField txtName = (TextField) registRoot.lookup("#txtName");
			ComboBox<String> cbClass = (ComboBox<String>) registRoot.lookup("#cbClass");
			ObservableList<String> listClass = FXCollections.observableArrayList();
			listClass.add("�̽��� (5��)");
			listClass.add("Ǯ�ٹ� (6��)");
			listClass.add("���ٹ� (7��)");
			cbClass.setItems(listClass);
			Scene scene = new Scene(registRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event2 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
			btnRegist.setOnAction(event -> {
				int num = 0;
				int count = 0;
				ArrayList<Admin> idCheck = null;
				try {
					if (((txtPasswordCheck.getText()).equals((txtPassword.getText()))) == false) {
						throw new Exception();
					} else {
						count++;
					}

				} catch (Exception e1) {
					ShareMethod.alertDisplay(1, "��й�ȣ ����!", "��й�ȣ�� ��ġ���� �ʽ��ϴ�!", "�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
				}
				try {
					AdminDAO adminDAO = new AdminDAO();
					idCheck = adminDAO.getChildrenCheck(txtId.getText());
					if (idCheck.size() != 0) {
						throw new Exception();
					} else {
						count++;
					}

				} catch (Exception e1) {
					ShareMethod.alertDisplay(1, "�ߺ��� ���̵�!", "�ߺ��� ���̵��Դϴ�!", "�ٸ� ���̵� �Է����ּ��� (*'��'*)");
				}
				try {
					if (txtId.getText().equals("") || txtPassword.getText().equals("")
							|| txtPasswordCheck.getText().equals("")
							|| cbClass.getSelectionModel().getSelectedItem() == null) {
						throw new Exception();
					} else {
						count++;
					}

				} catch (Exception e2) {
		               ShareMethod.alertDisplay(1, "�����Է¿���!", "���� �̱���!", "���߸��� �׸��� ������ Ȯ���� �ּ��� (;��;)");
				}
				try {
					if (count == 3) {
						AdminDAO adminDAO = new AdminDAO();
						num = adminDAO.getChildrenRegist(new Admin(txtId.getText(), txtPassword.getText(),
								txtName.getText(), cbClass.getSelectionModel().getSelectedItem()));
						if (num == 0) {
							throw new Exception();
						} else {
							ShareMethod.alertDisplay(5, "ȸ������ �Ϸ�!", "ȸ������ ����!", "ȸ�������� �Ϸ�Ǿ����. (*'��'*)");
							((Stage) btnExit.getScene().getWindow()).close();
						}
					}
				} catch (Exception e3) {
					ShareMethod.alertDisplay(5, "ȸ������ ����!", "ȸ������ ����!", "�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
				}

			});

		} catch (Exception e0) {
		}
	}

	public void userTypeSetting() {
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("�кθ�");
		list.add("������ (������)");
		cbUser.setItems(list);

	}

	// 1. ��ư Ȯ�� �̺�Ʈ ó��
	public void handlerBtnLoginAction(ActionEvent event) {
		ShareMethod.id = txtId.getText();
		select = cbUser.getSelectionModel().getSelectedIndex();
		ChildrenDAO cd = new ChildrenDAO();
		AdminDAO ad = new AdminDAO();
		//DB���� ���̵� �н����� �ܾ�ͼ� ���������� ����
		String adid = ad.adminLoginID(txtId.getText());
		String adpass = ad.getChildrenPassword(txtId.getText());
		String cdpass = cd.getChildrenPassword(txtId.getText());
		String cdid = cd.getChildrenID(txtId.getText());
		System.out.println(select);
		// (1) ���̵�� �н����尡 �Էµ��� �ʾ��� �� �˸�â�� ��
		if ((txtId.getText().equals("") || txtPassword.getText().equals("")) || select == -1) {
			ShareMethod.alertDisplay(1, "�ڷα��� ���С�", " ���� �̱��� ����!",
					" ���̵� / ��й�ȣ / ����� ���� �� \n �Է����� ���� �׸��� �־��!" + "\n �ٽ� �� �� Ȯ���� �ּ���. (;��;)");

			// (2) ���̵�� �н����尡 �ùٸ��� �ԷµǾ����� �˸�â�� �� - �кθ� �α���
		} else if ((txtId.getText().equals(cdid))&& txtPassword.getText().equals(cdpass) && select == 0) {

			// (2-1) �α����� �Ϸ�Ǿ����� ���� ����â���� �̵���
			Parent mainView = null;
			Stage mainStage = null;
			try {
				if (select == 0) {
					mainView = FXMLLoader.load(getClass().getResource("/view/user_menu.fxml"));
					Scene scene = new Scene(mainView);
					mainStage = new Stage();
					mainStage.setTitle("�޴�");
					mainStage.setScene(scene);
					mainStage.setResizable(true);
					((Stage) btnLogin.getScene().getWindow()).close();
					mainStage.show();
				}
			} catch (IOException e) {
				ShareMethod.alertDisplay(1, "���� �߻�!", "����â �θ��� ����!", "�ٽ� �� �� Ȯ���� �ּ��� (;��;)");
//				System.out.println("����â �θ��� ����" + e);
				e.printStackTrace();
			}

			System.out.println("�кθ� �α��� �Ϸ�(*'��'*)");

			// (2) ���̵�� �н����尡 �ùٸ��� �ԷµǾ����� �˸�â�� �� - ������ �α���
		} else if (txtId.getText().equals(adid) && txtPassword.getText().equals(adpass) && select == 1) {
			// ������ �α���
			Parent mainView = null;
			Stage mainStage = null;
			try {

				mainView = FXMLLoader.load(getClass().getResource("/view/admin_menu.fxml"));
				Scene scene = new Scene(mainView);
				mainStage = new Stage();
				mainStage.setTitle("�޴�");
				mainStage.setScene(scene);
				mainStage.setResizable(true);
				((Stage) btnLogin.getScene().getWindow()).close();
				mainStage.show();

			} catch (IOException e) {
				ShareMethod.alertDisplay(1, "���� �߻�!", "����â �θ��� ����!", "�ٽ� �� �� Ȯ���� �ּ��� (;��;)");
				e.printStackTrace();
			}

			System.out.println("������ (������)�α��� �Ϸ�(*'��'*)");
		} else {
			ShareMethod.alertDisplay(1, "�ڷα��� ���С�", " ���̵�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.",
					" �ٽ� �� �� Ȯ���� �ּ���. (;��;)");
		}

	}

	// 2. ��ư ��� �̺�Ʈ ó��
	public void handlerBtnCancelAction(ActionEvent event) {

		Stage stage = (Stage) btnLogin.getScene().getWindow();
		stage.close();
	}

}
