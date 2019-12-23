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
		// 1. 버튼 확인(로그인) 이벤트 처리
		btnLogin.setOnAction(e -> handlerBtnLoginAction(e));
		// 2. 버튼 취소 이벤트 처리
		btnExit.setOnAction(e -> handlerBtnCancelAction(e));
		// 3. 선생님 등록 이벤트 처리
		btnRegist.setOnAction(e -> handlerBtnRegistAction(e));
		// 4. 학부모 비밀번호 찾기 이벤트 처리
		btnSearchPassword.setOnAction(e -> handlerBtnSearchPasswordAction(e));

		btnSearchAdmin.setOnAction(e -> handlerBtnSearchAdmindAction(e));
//		txtId.setText("강경원");
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
			stage.setTitle("선생님 로그인 정보 찾기");
			Button btnRegist = (Button) registRoot.lookup("#btnSearch");
			Button btnExit = (Button) registRoot.lookup("#btnExit");
			TextField txtId = (TextField) registRoot.lookup("#txtId");
			ComboBox<String> cbClass = (ComboBox<String>) registRoot.lookup("#cbClass");
			ObservableList<String> listClass = FXCollections.observableArrayList();
			listClass.add("이슬반 (5세)");
			listClass.add("풀잎반 (6세)");
			listClass.add("꽃잎반 (7세)");
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
							ShareMethod.alertDisplay(5, "아이디 비밀번호 찾기!", "아이디 비밀번호 찾기 성공!",
									"아이디 : [ " + seekID + " ]\n" + "비밀번호 : [ " + seekPassword + " ] (*'ㅅ'*)");
						} else {
							throw new Exception();
						}
					}
				} catch (Exception e1) {
					ShareMethod.alertDisplay(1, "계정 찾기 실패!", "계정이 존재하지 않습니다!", "다시 한 번 확인해 주세요. (;ㅅ;)");
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

	// 학부모 비밀번호 찾기
	private void handlerBtnSearchPasswordAction(ActionEvent e) {
		Parent registRoot;
		try {
			registRoot = FXMLLoader.load(getClass().getResource("/view/user_search_password_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnSearchPassword.getScene().getWindow());
			stage.setTitle("학부모 비밀번호 찾기");
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
							ShareMethod.alertDisplay(5, "비밀번호 찾기!", "비밀번호 찾기 성공!",
									"비밀번호는 [ " + list.get(0).getBirthday() + " ] 입니다. (*'ㅅ'*)");
						} else {
							throw new Exception();
						}
					}else {
						throw new Exception();
					}

				} catch (Exception e1) {
					ShareMethod.alertDisplay(1, "계정 찾기 실패!", "계정이 존재하지 않습니다!", "다시 한 번 확인해 주세요. (;ㅅ;)");
					e1.printStackTrace();
				}

			});

			btnExit.setOnAction(event2 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (Exception e1) {
		}
	}

	// 선생님 등록하기
	public void handlerBtnRegistAction(ActionEvent e) {
		Parent registRoot;
		try {
			registRoot = FXMLLoader.load(getClass().getResource("/view/regist.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnRegist.getScene().getWindow());
			stage.setTitle("선생님 등록");
			Button btnRegist = (Button) registRoot.lookup("#btnRegist");
			Button btnExit = (Button) registRoot.lookup("#btnExit");
			TextField txtId = (TextField) registRoot.lookup("#txtId");
			PasswordField txtPassword = (PasswordField) registRoot.lookup("#txtPassword");
			PasswordField txtPasswordCheck = (PasswordField) registRoot.lookup("#txtPasswordCheck");
			TextField txtName = (TextField) registRoot.lookup("#txtName");
			ComboBox<String> cbClass = (ComboBox<String>) registRoot.lookup("#cbClass");
			ObservableList<String> listClass = FXCollections.observableArrayList();
			listClass.add("이슬반 (5세)");
			listClass.add("풀잎반 (6세)");
			listClass.add("꽃잎반 (7세)");
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
					ShareMethod.alertDisplay(1, "비밀번호 오류!", "비밀번호가 일치하지 않습니다!", "다시 한 번 확인해 주세요. (;ㅅ;)");
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
					ShareMethod.alertDisplay(1, "중복된 아이디!", "중복된 아이디입니다!", "다른 아이디를 입력해주세요 (*'ㅅ'*)");
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
		               ShareMethod.alertDisplay(1, "정보입력오류!", "정보 미기재!", "빠뜨리신 항목이 없는지 확인해 주세요 (;ㅅ;)");
				}
				try {
					if (count == 3) {
						AdminDAO adminDAO = new AdminDAO();
						num = adminDAO.getChildrenRegist(new Admin(txtId.getText(), txtPassword.getText(),
								txtName.getText(), cbClass.getSelectionModel().getSelectedItem()));
						if (num == 0) {
							throw new Exception();
						} else {
							ShareMethod.alertDisplay(5, "회원가입 완료!", "회원가입 성공!", "회원가입이 완료되었어요. (*'ㅅ'*)");
							((Stage) btnExit.getScene().getWindow()).close();
						}
					}
				} catch (Exception e3) {
					ShareMethod.alertDisplay(5, "회원가입 오류!", "회원가입 실패!", "다시 한 번 확인해 주세요. (;ㅅ;)");
				}

			});

		} catch (Exception e0) {
		}
	}

	public void userTypeSetting() {
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("학부모");
		list.add("선생님 (관리자)");
		cbUser.setItems(list);

	}

	// 1. 버튼 확인 이벤트 처리
	public void handlerBtnLoginAction(ActionEvent event) {
		ShareMethod.id = txtId.getText();
		select = cbUser.getSelectionModel().getSelectedIndex();
		ChildrenDAO cd = new ChildrenDAO();
		AdminDAO ad = new AdminDAO();
		//DB에서 아이디 패스워드 긁어와서 정적변수에 저장
		String adid = ad.adminLoginID(txtId.getText());
		String adpass = ad.getChildrenPassword(txtId.getText());
		String cdpass = cd.getChildrenPassword(txtId.getText());
		String cdid = cd.getChildrenID(txtId.getText());
		System.out.println(select);
		// (1) 아이디와 패스워드가 입력되지 않았을 때 알림창을 줌
		if ((txtId.getText().equals("") || txtPassword.getText().equals("")) || select == -1) {
			ShareMethod.alertDisplay(1, "★로그인 실패★", " 정보 미기재 오류!",
					" 아이디 / 비밀번호 / 사용자 유형 중 \n 입력하지 않은 항목이 있어요!" + "\n 다시 한 번 확인해 주세요. (;ㅅ;)");

			// (2) 아이디와 패스워드가 올바르게 입력되었을때 알림창을 줌 - 학부모 로그인
		} else if ((txtId.getText().equals(cdid))&& txtPassword.getText().equals(cdpass) && select == 0) {

			// (2-1) 로그인이 완료되었으면 다음 메인창으로 이동함
			Parent mainView = null;
			Stage mainStage = null;
			try {
				if (select == 0) {
					mainView = FXMLLoader.load(getClass().getResource("/view/user_menu.fxml"));
					Scene scene = new Scene(mainView);
					mainStage = new Stage();
					mainStage.setTitle("메뉴");
					mainStage.setScene(scene);
					mainStage.setResizable(true);
					((Stage) btnLogin.getScene().getWindow()).close();
					mainStage.show();
				}
			} catch (IOException e) {
				ShareMethod.alertDisplay(1, "오류 발생!", "메인창 부르기 오류!", "다시 한 번 확인해 주세요 (;ㅅ;)");
//				System.out.println("메인창 부르기 오류" + e);
				e.printStackTrace();
			}

			System.out.println("학부모 로그인 완료(*'ㅅ'*)");

			// (2) 아이디와 패스워드가 올바르게 입력되었을때 알림창을 줌 - 선생님 로그인
		} else if (txtId.getText().equals(adid) && txtPassword.getText().equals(adpass) && select == 1) {
			// 선생님 로그인
			Parent mainView = null;
			Stage mainStage = null;
			try {

				mainView = FXMLLoader.load(getClass().getResource("/view/admin_menu.fxml"));
				Scene scene = new Scene(mainView);
				mainStage = new Stage();
				mainStage.setTitle("메뉴");
				mainStage.setScene(scene);
				mainStage.setResizable(true);
				((Stage) btnLogin.getScene().getWindow()).close();
				mainStage.show();

			} catch (IOException e) {
				ShareMethod.alertDisplay(1, "오류 발생!", "메인창 부르기 오류!", "다시 한 번 확인해 주세요 (;ㅅ;)");
				e.printStackTrace();
			}

			System.out.println("선생님 (관리자)로그인 완료(*'ㅅ'*)");
		} else {
			ShareMethod.alertDisplay(1, "★로그인 실패★", " 아이디와 비밀번호가 일치하지 않습니다.",
					" 다시 한 번 확인해 주세요. (;ㅅ;)");
		}

	}

	// 2. 버튼 취소 이벤트 처리
	public void handlerBtnCancelAction(ActionEvent event) {

		Stage stage = (Stage) btnLogin.getScene().getWindow();
		stage.close();
	}

}
