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

	// 테이블 뷰를 선택했을때 위치값과 객체값을 저장할 수 있는 변수 선언
	private int selectedIndex;
	private ObservableList<Children> selectChildren;

	private String selectFileName = ""; // 이미지 파일명
	private String localUrl = ""; // 이미지 파일 경로
	private Image localImage;
	private File selectedFile = null;
	// 이미지 처리
	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave = new File("C:/images");
	// 이미지 불러올 파일을 저장할 파일 객체 선언
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

	// 개인정보 ID 초기세팅
	public void privateInfoSetting() {
		txtId.setDisable(true);
		txtId.setText(ShareMethod.id);
		AdminDAO ad = new AdminDAO();
		String url="";
		try {
			ArrayList<Admin> a =new ArrayList<Admin>();
					a=ad.getChildrenCheck(ShareMethod.id);
			if(a.get(0).getImage()==null) {
				url="file:///C:/Users/소영/Desktop/I_Diary%20(2)/src/image/default_profile.png";
			}
			url=a.get(0).getImage();
			imageView.setImage(new Image(url, false));
			imageView.setFitHeight(200);
			imageView.setFitWidth(200);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 데이터베이스 값을 읽어서 테이블뷰에 가져오기
	public void totalList() {
		ObservableList<Children> list = null;
		ChildrenDAO childrenDAO = new ChildrenDAO();
		Children childrenVO = new Children();
		list = childrenDAO.getMyClassChildrenTotal(ShareMethod.className);

		if (list == null) {
			ShareMethod.alertDisplay(1, "경고!", "DB 가져오기 오류!", "다시 한 번 확인해 주세요 (;ㅅ;)");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			childrenVO = list.get(i);
			data.add(childrenVO);
		}
	}

	// 검색 버튼을 눌렀을 때 이벤트
	private void hanlderBtnSearchAction(ActionEvent event) {
		try {
			ObservableList<Children> list = FXCollections.observableArrayList();
			ChildrenDAO childrenDAO = new ChildrenDAO();
			list = childrenDAO.getChildrenSearch(txtSearch.getText(), ShareMethod.className);

			if (list == null) {
				throw new Exception("검색오류!");
			}

			data.removeAll(data);

			for (Children svo : list) {
				data.add(svo);
			}

		} catch (Exception e) {
			// 알림창 띄우기
			ShareMethod.alertDisplay(1, "이름 검색 오류!", "이름이 정확하지 않습니다!", "이름을 정확히 입력해 주세요 (;ㅅ;)");
		}
	}


	// 테이블뷰 기본 설정하기
	private void tableViewSetting() {
		// 테이블 설정.ArrayList();
		data = FXCollections.observableArrayList();
		// 테이블 설정.테이블뷰를 편집하지 못하게 설정
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

	// 프로필 사진 파일선택 버튼을 눌렀을 때 이벤트
	private void hanlderBtnSelectFileAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {

			selectedFile = fileChooser.showOpenDialog(btnSelectFile.getScene().getWindow());
			if (selectedFile != null) {
				// 이미지 파일 경로
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e1) {
			ShareMethod.alertDisplay(5, "수정 취소!", "사진 수정 취소!", "사진 수정이 취소되었어요. (*'ㅅ'*)");
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
			ShareMethod.alertDisplay(5, "수정 취소!", "사진 수정 취소!", "사진 수정이 취소되었어요. (*'ㅅ'*)");
		}

	}

	// 콤보박스 셋팅하기
	public void comboBoxSetting() {
		ObservableList<String> listClass = FXCollections.observableArrayList();
		listClass.add("이슬반 (5세)");
		listClass.add("풀잎반 (6세)");
		listClass.add("꽃잎반 (7세)");
		cbClass.setItems(listClass);
	}

	// 원아 정보 설정 (추가, 수정)
	private void hanlderBtnEditAction(ActionEvent event) {
		Parent EditProfileRoot;
		try {
			EditProfileRoot = FXMLLoader.load(getClass().getResource("/view/admin_setting_children_edit_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnEdit.getScene().getWindow());
			stage.setTitle("정보 설정");
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
			listClass.add("이슬반 (5세)");
			listClass.add("풀잎반 (6세)");
			listClass.add("꽃잎반 (7세)");
			listGender.add("남자");
			listGender.add("여자");
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
					ShareMethod.alertDisplay(1, "중복된 아이디!", "중복된 아이디입니다!", "다른 아이디를 입력해주세요 (*'ㅅ'*)");
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
					ShareMethod.alertDisplay(1, "정보입력오류!", "정보 미기재!", "빠뜨리신 항목이 없는지 확인해 주세요 (;ㅅ;)");
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
						ShareMethod.alertDisplay(5, "정보입력 완료!", "정보입력 성공!", "정보입력이 완료되었어요. (*'ㅅ'*)");
						data.removeAll(data);
						totalList();
					}
				} catch (Exception e) {
					ShareMethod.alertDisplay(1, "정보입력 오류!", "정보입력 실패!", "다시 한 번 확인해 주세요. (;ㅅ;)");
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

	// 원아 정보 삭제 버튼을 눌렀을 때 이벤트
	private void hanlderBtnRemoveAction(ActionEvent event) {
		ChildrenDAO ca = new ChildrenDAO();
		String str = data.get(tableView.getSelectionModel().getSelectedIndex()).getChildrenName();

		// 삭제 경고창 팝업
		Parent rabbitRoot;
		try {
			rabbitRoot = FXMLLoader.load(getClass().getResource("/view/alert_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("삭제 경고!");
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

		// 비밀번호 확인
		try {
			if (((txtPasswordCheck.getText()).equals((txtPassword.getText()))) == false) {
				throw new Exception();
			} else {
				count++;
			}

		} catch (Exception e) {
			ShareMethod.alertDisplay(1, "비밀번호 오류!", "비밀번호 미일치!", "다시 한 번 확인해 주세요. (;ㅅ;)");
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
			ShareMethod.alertDisplay(1, "정보입력오류!", "정보 미기재!", "빠뜨리신 항목이 없는지 확인해 주세요 (;ㅅ;)" + e.toString());
		}
		try {
			if (count == 2) {
				int num = ad.getAdminUpdate(admin);
				if (num == 0) {
					throw new Exception();
				} else {
					ShareMethod.alertDisplay(5, "정보수정 완료!", "정보수정 성공!", "정보가 수정되었어요. (*'ㅅ'*)");
				}
			}
		} catch (Exception e1) {
			ShareMethod.alertDisplay(1, "정보입력 오류!", "정보입력 실패!", "다시 한 번 확인해 주세요. (;ㅅ;)");
		}
	}

	// 선생님 메인창으로 이동하기
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
