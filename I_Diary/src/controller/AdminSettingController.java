package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Admin;
import model.Children;

public class AdminSettingController implements Initializable {
	@FXML
	private Button btnMain;
	@FXML
	private Button btnPrivateSave;
	@FXML
	private ComboBox<String> cbClass;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtSearch;
	@FXML
	private PasswordField txtPasswordCheck;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private ImageView imageView;
	@FXML
	private Button btnSelectFile;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnRemove;
	@FXML
	private Button btnSearch;
	@FXML
	private TableView<Children> tableView;
	ObservableList<Children> data;

	// ���̺� �並 ���������� ��ġ���� ��ü���� ������ �� �ִ� ���� ����
	private int selectedIndex;
	private ObservableList<Children> selectChildren;

	private String selectFileName = ""; // �̹��� ���ϸ�
	private String localUrl = ""; // �̹��� ���� ���
	private Image localImage;
	private File selectedFile = null;
	// �̹��� ó��
	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:/images");
	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		btnPrivateSave.setOnAction(event -> {
			handlerBtnPrivateSaveAction(event);
		});
		btnEdit.setOnAction(event -> {
			hanlderBtnEditAction(event);
		});
		btnRemove.setOnAction(event -> {
			hanlderBtnRemoveAction(event);
		});
		btnSelectFile.setOnAction(event -> {
			hanlderBtnSelectFileAction(event);
		});
		btnSearch.setOnAction(event -> {
			hanlderBtnSearchAction(event);
		});
		comboBoxSetting();
		tableViewSetting();

		totalList();
		privateInfoSetting();
	}

	// �������� ID �ʱ⼼��
	public void privateInfoSetting() {
		txtId.setDisable(true);
		txtId.setText(ShareMethod.id);
		AdminDAO ad = new AdminDAO();
		String url="";
		try {
			ArrayList<Admin> a =new ArrayList<Admin>();
					a=ad.getChildrenCheck(ShareMethod.id);
			if(a.get(0).getImage()==null) {
				url="file:///C:/Users/�ҿ�/Desktop/I_Diary%20(2)/src/image/default_profile.png";
			}
			url=a.get(0).getImage();
			imageView.setImage(new Image(url, false));
			imageView.setFitHeight(200);
			imageView.setFitWidth(200);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// �����ͺ��̽� ���� �о ���̺�信 ��������
	public void totalList() {
		ObservableList<Children> list = null;
		ChildrenDAO childrenDAO = new ChildrenDAO();
		Children childrenVO = new Children();
		list = childrenDAO.getMyClassChildrenTotal(ShareMethod.className);

		if (list == null) {
			ShareMethod.alertDisplay(1, "���!", "DB �������� ����!", "�ٽ� �� �� Ȯ���� �ּ��� (;��;)");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			childrenVO = list.get(i);
			data.add(childrenVO);
		}
	}

	// �˻� ��ư�� ������ �� �̺�Ʈ
	private void hanlderBtnSearchAction(ActionEvent event) {
		try {
			ObservableList<Children> list = FXCollections.observableArrayList();
			ChildrenDAO childrenDAO = new ChildrenDAO();
			list = childrenDAO.getChildrenSearch(txtSearch.getText(), ShareMethod.className);

			if (list == null) {
				throw new Exception("�˻�����!");
			}

			data.removeAll(data);

			for (Children svo : list) {
				data.add(svo);
			}

		} catch (Exception e) {
			// �˸�â ����
			ShareMethod.alertDisplay(1, "�̸� �˻� ����!", "�̸��� ��Ȯ���� �ʽ��ϴ�!", "�̸��� ��Ȯ�� �Է��� �ּ��� (;��;)");
		}
	}


	// ���̺�� �⺻ �����ϱ�
	private void tableViewSetting() {
		// ���̺� ����.ArrayList();
		data = FXCollections.observableArrayList();
		// ���̺� ����.���̺�並 �������� ���ϰ� ����
		tableView.setEditable(false);

		TableColumn colChildrenName = tableView.getColumns().get(0);
		colChildrenName.setCellValueFactory(new PropertyValueFactory("childrenName"));

		TableColumn colBirthday = tableView.getColumns().get(1);
		colBirthday.setCellValueFactory(new PropertyValueFactory("birthday"));

		TableColumn colClassName = tableView.getColumns().get(2);
		colClassName.setCellValueFactory(new PropertyValueFactory("className"));

		TableColumn colChildrenGender = tableView.getColumns().get(3);
		colChildrenGender.setCellValueFactory(new PropertyValueFactory("childrenGender"));

		TableColumn colParentName = tableView.getColumns().get(4);
		colParentName.setCellValueFactory(new PropertyValueFactory("parentName"));

		TableColumn colPhone = tableView.getColumns().get(5);
		colPhone.setCellValueFactory(new PropertyValueFactory("phone"));

		TableColumn colAddress = tableView.getColumns().get(6);
		colAddress.setCellValueFactory(new PropertyValueFactory("address"));

		tableView.setItems(data);
	}

	// ������ ���� ���ϼ��� ��ư�� ������ �� �̺�Ʈ
	private void hanlderBtnSelectFileAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {

			selectedFile = fileChooser.showOpenDialog(btnSelectFile.getScene().getWindow());
			if (selectedFile != null) {
				// �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e1) {
			ShareMethod.alertDisplay(5, "���� ���!", "���� ���� ���!", "���� ������ ��ҵǾ����. (*'��'*)");
		}
		try {
			System.out.println(selectedFile.toURI().toURL().toString());
			localImage = new Image(localUrl, false);
			imageView.setImage(localImage);
			imageView.setFitHeight(200);
			imageView.setFitWidth(200);
			btnSelectFile.setDisable(false);
			AdminDAO ad = new AdminDAO();
			System.out.println(ShareMethod.id);

			ad.getAdminImageUpdate(localUrl, ShareMethod.id);
		} catch (Exception e) {
			ShareMethod.alertDisplay(5, "���� ���!", "���� ���� ���!", "���� ������ ��ҵǾ����. (*'��'*)");
		}

	}

	// �޺��ڽ� �����ϱ�
	public void comboBoxSetting() {
		ObservableList<String> listClass = FXCollections.observableArrayList();
		listClass.add("�̽��� (5��)");
		listClass.add("Ǯ�ٹ� (6��)");
		listClass.add("���ٹ� (7��)");
		cbClass.setItems(listClass);
	}

	// ���� ���� ���� (�߰�, ����)
	private void hanlderBtnEditAction(ActionEvent event) {
		Parent EditProfileRoot;
		try {
			EditProfileRoot = FXMLLoader.load(getClass().getResource("/view/admin_setting_children_edit_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnEdit.getScene().getWindow());
			stage.setTitle("���� ����");
			Button btnAdd = (Button) EditProfileRoot.lookup("#btnAdd");
			Button btnEdit = (Button) EditProfileRoot.lookup("#btnEdit");
			Button btnExit = (Button) EditProfileRoot.lookup("#btnExit");
			TextField txtChildrenName = (TextField) EditProfileRoot.lookup("#txtChildrenName");
			ComboBox<String> cbClass = (ComboBox<String>) EditProfileRoot.lookup("#cbClass");
			TextField txtBirthday = (TextField) EditProfileRoot.lookup("#txtBirthday");
			TextField txtAddress = (TextField) EditProfileRoot.lookup("#txtAddress");
			TextField txtParentName = (TextField) EditProfileRoot.lookup("#txtParentName");
			TextField txtPhone = (TextField) EditProfileRoot.lookup("#txtPhone");
			ComboBox<String> cbGender = (ComboBox<String>) EditProfileRoot.lookup("#cbGender");
			Scene scene = new Scene(EditProfileRoot);
			stage.setScene(scene);
			stage.show();
			ObservableList<String> listClass = FXCollections.observableArrayList();
			ObservableList<String> listGender = FXCollections.observableArrayList();
			listClass.add("�̽��� (5��)");
			listClass.add("Ǯ�ٹ� (6��)");
			listClass.add("���ٹ� (7��)");
			listGender.add("����");
			listGender.add("����");
			cbClass.setItems(listClass);
			cbGender.setItems(listGender);
			btnAdd.setOnAction(event1 -> {

				int num = 0;
				int count = 0;
				ChildrenDAO ca = new ChildrenDAO();
				ArrayList<Children> str = null;
				try {
					str = ca.getChildrenIdCheck(txtChildrenName.getText());
					System.out.println(str.size());
					if (str.size() == 0) {
						count++;
					} else {
						throw new Exception();
					}
				} catch (Exception e) {
					ShareMethod.alertDisplay(1, "�ߺ��� ���̵�!", "�ߺ��� ���̵��Դϴ�!", "�ٸ� ���̵� �Է����ּ��� (*'��'*)");
				}
				try {
					if (txtBirthday.getText().equals("") || txtChildrenName.getText().equals("")
							|| cbClass.getSelectionModel().getSelectedIndex() == -1
							|| cbGender.getSelectionModel().getSelectedIndex() == -1
							|| txtParentName.getText().equals("") || txtPhone.getText().equals("")
							|| txtAddress.getText().equals("")) {

						throw new Exception();
					} else {
						count++;
					}
				} catch (Exception ee) {
					ShareMethod.alertDisplay(1, "�����Է¿���!", "���� �̱���!", "���߸��� �׸��� ������ Ȯ���� �ּ��� (;��;)");
				}
				try {
					if (count == 2) {

						num = ca.getChildrenRegist(new Children(txtChildrenName.getText(), txtBirthday.getText(),
								cbClass.getSelectionModel().getSelectedItem(),
								cbGender.getSelectionModel().getSelectedItem(), txtParentName.getText(),
								txtPhone.getText(), txtAddress.getText()));
						if (num == 0) {
							throw new Exception();
						}
						((Stage) btnExit.getScene().getWindow()).close();
						ShareMethod.alertDisplay(5, "�����Է� �Ϸ�!", "�����Է� ����!", "�����Է��� �Ϸ�Ǿ����. (*'��'*)");
						data.removeAll(data);
						totalList();
					}
				} catch (Exception e) {
					ShareMethod.alertDisplay(1, "�����Է� ����!", "�����Է� ����!", "�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
					e.printStackTrace();
				}
			});
			btnExit.setOnAction(event2 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ���� ���� ���� ��ư�� ������ �� �̺�Ʈ
	private void hanlderBtnRemoveAction(ActionEvent event) {
		ChildrenDAO ca = new ChildrenDAO();
		String str = data.get(tableView.getSelectionModel().getSelectedIndex()).getChildrenName();

		// ���� ���â �˾�
		Parent rabbitRoot;
		try {
			rabbitRoot = FXMLLoader.load(getClass().getResource("/view/alert_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("���� ���!");
			Button btnYes = (Button) rabbitRoot.lookup("#btnYes");
			Button btnNo = (Button) rabbitRoot.lookup("#btnNo");
			Scene scene = new Scene(rabbitRoot);
			stage.setScene(scene);
			stage.show();
			
			btnYes.setOnAction(event1 -> {
				try {
					ca.getChildrenDelete(str);
					data.removeAll(data);
					totalList();
					//data.remove(tableView.getSelectionModel().getSelectedIndex());
				} catch (Exception e) {
					e.printStackTrace();
				}
				((Stage) btnYes.getScene().getWindow()).close();
			});
			
			btnNo.setOnAction(event1 -> {
				((Stage) btnNo.getScene().getWindow()).close();
			});
			
		} catch (Exception e1) {
			return;
		}
	}

	public void handlerBtnPrivateSaveAction(ActionEvent event) {
		int count = 0;
		AdminDAO ad = new AdminDAO();

		Admin admin = new Admin(txtId.getText(), txtPassword.getText(), txtName.getText(),
				cbClass.getSelectionModel().getSelectedItem());

		// ��й�ȣ Ȯ��
		try {
			if (((txtPasswordCheck.getText()).equals((txtPassword.getText()))) == false) {
				throw new Exception();
			} else {
				count++;
			}

		} catch (Exception e) {
			ShareMethod.alertDisplay(1, "��й�ȣ ����!", "��й�ȣ ����ġ!", "�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
		}
		try {
			if (txtPasswordCheck.getText().equals("") || txtId.getText().equals("")
					|| cbClass.getSelectionModel().getSelectedItem().equals(null) || txtName.getText().equals("")
					|| txtPassword.getText().equals("")) {

				throw new Exception();
			} else {
				count++;
			}
		} catch (Exception e) {
			ShareMethod.alertDisplay(1, "�����Է¿���!", "���� �̱���!", "���߸��� �׸��� ������ Ȯ���� �ּ��� (;��;)" + e.toString());
		}
		try {
			if (count == 2) {
				int num = ad.getAdminUpdate(admin);
				if (num == 0) {
					throw new Exception();
				} else {
					ShareMethod.alertDisplay(5, "�������� �Ϸ�!", "�������� ����!", "������ �����Ǿ����. (*'��'*)");
				}
			}
		} catch (Exception e1) {
			ShareMethod.alertDisplay(1, "�����Է� ����!", "�����Է� ����!", "�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
		}
	}

	// ������ ����â���� �̵��ϱ�
	public void handlerBtnMainAction(ActionEvent event) {
		Parent mainView = null;
		Stage informStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/admin_menu.fxml"));
			Scene scene = new Scene(mainView);
			informStage = new Stage();
			informStage.setTitle("Main Window");
			informStage.setScene(scene);
			informStage.setResizable(true);
			((Stage) btnMain.getScene().getWindow()).close();
			informStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
