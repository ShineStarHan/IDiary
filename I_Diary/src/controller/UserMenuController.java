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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Children;

public class UserMenuController implements Initializable {

	@FXML
	private Button btnNotice;
	@FXML
	private Button btnInform;
	@FXML
	private Button btnAlbum;
	@FXML
	private Button btnSchedule;
	@FXML
	private Button btnDiet;
	@FXML
	private Button btnMedicine;
	@FXML
	private Button btnAttend;
	@FXML
	private Button btnSetting;
	@FXML
	private Button btnMain;
	@FXML
	private ImageView profileImageView;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtClass;

	private String localUrl = ""; // 이미지 파일 경로
	private Image localImage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtName.setEditable(false);
		txtClass.setEditable(false);

		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		btnNotice.setOnAction(event -> {
			handlerBtnNoticeAction(event);
		});
		btnInform.setOnAction(event -> {
			handlerBtnInformAction(event);
		});
		btnMedicine.setOnAction(event -> {
			hanlderBtnMedicineAction();
		});
		btnSetting.setOnAction(event -> {
			hanlderBtnSettingAction(event);
		});
		btnAlbum.setOnAction(event -> {
			hanlderBtnAlbumAction(event);
		});
		btnSchedule.setOnAction(event -> {
			hanlderBtnScheduleAction(event);
		});
		btnDiet.setOnAction(event -> {
			hanlderBtnDietAction(event);
		});
		btnAttend.setOnAction(event -> {
			hanlderBtnAttendAction(event);
		});
		userNameSetting();
	}

	// 이미지뷰 넣으면 수정
	public void userNameSetting() {
		ChildrenDAO cd = new ChildrenDAO();
		txtClass.setText(cd.getChildrenClass(ShareMethod.id));
		txtName.setText(ShareMethod.id);
		ArrayList<Children> list=new ArrayList<Children>();
		try {
			list = cd.getChildrenIdCheck(ShareMethod.id);

			System.out.println(localUrl);
			if (list.get(0).getImage() == null) {
				localUrl = "file:///C:/Users/소영/Desktop/I_Diary%20(2)/src/image/default_profile.png";
				localImage = new Image(localUrl, false);
			} else {
				localUrl = list.get(0).getImage();
				localImage = new Image(localUrl, false);
			}
			profileImageView.setImage(localImage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 출석부
	private void hanlderBtnAttendAction(ActionEvent event) {
		Parent mainView = null;
		Stage informStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_attend.fxml"));
			Scene scene = new Scene(mainView);
			informStage = new Stage();
			informStage.setTitle("출석부");
			informStage.setScene(scene);
			informStage.setResizable(true);
			((Stage) btnAttend.getScene().getWindow()).close();
			informStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 식단표
	public void hanlderBtnDietAction(ActionEvent event) {
		Parent dietRoot;
		try {
			AlbumDAO diet = new AlbumDAO();
			String url = "";
			dietRoot = FXMLLoader.load(getClass().getResource("/view/user_diet_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnNotice.getScene().getWindow());
			stage.setTitle("식단표");
			Button btnClose = (Button) dietRoot.lookup("#btnMain");
			ImageView imageView = (ImageView) dietRoot.lookup("#imageView");
			if (diet.getAlbumTotal().get(9).getImage() == null) {
				url = "file:C:\\Users\\소영\\Desktop\\I_Diary (2)\\src\\image\\default_profile.png";
			} else {
				url = diet.getAlbumTotal().get(9).getImage();
			}
			imageView.setImage(new Image(url, false));
			Scene scene = new Scene(dietRoot);
			stage.setScene(scene);
			stage.show();
			btnClose.setOnAction(event1 -> {
				((Stage) btnClose.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 일정표
	public void hanlderBtnScheduleAction(ActionEvent event) {
		Parent mainView = null;
		Stage informStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_schedule.fxml"));
			Scene scene = new Scene(mainView);
			informStage = new Stage();
			informStage.setTitle("일정표");
			informStage.setScene(scene);
			informStage.setResizable(true);
			((Stage) btnInform.getScene().getWindow()).close();
			informStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 앨범
	public void hanlderBtnAlbumAction(ActionEvent event) {
		Parent mainView = null;
		Stage informStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_album.fxml"));
			Scene scene = new Scene(mainView);
			informStage = new Stage();
			informStage.setTitle("앨범");
			informStage.setScene(scene);
			informStage.setResizable(true);
			((Stage) btnInform.getScene().getWindow()).close();
			informStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 설정
	public void hanlderBtnSettingAction(ActionEvent event) {
		Parent mainView = null;
		Stage informStage = null;
		ObservableList<String> classCbList = FXCollections.observableArrayList();
		ObservableList<String> genderCbList = FXCollections.observableArrayList();
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_setting.fxml"));
			Scene scene = new Scene(mainView);
			informStage = new Stage();
			informStage.setTitle("설정 (학부모)");
			informStage.setScene(scene);
			informStage.setResizable(true);
			((Stage) btnInform.getScene().getWindow()).close();
			informStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			// informStage.show();
		}
	}

	// 투약의뢰서
	public void hanlderBtnMedicineAction() {
		Parent mainView = null;
		Stage informStage = null;
		ObservableList<String> medicineCbList = FXCollections.observableArrayList();
		ObservableList<String> nameCbList = FXCollections.observableArrayList();
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_medicine.fxml"));
			Scene scene = new Scene(mainView);
			informStage = new Stage();
			informStage.setTitle("투약의뢰서");
			informStage.setScene(scene);
			informStage.setResizable(true);
			((Stage) btnInform.getScene().getWindow()).close();
			informStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			informStage.show();
		}
	}

	// 알림장
	public void handlerBtnInformAction(ActionEvent event) {
		Parent mainView = null;
		Stage informStage = null;
		ObservableList<String> informCbList = FXCollections.observableArrayList();
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_inform.fxml"));
			Scene scene = new Scene(mainView);
			informStage = new Stage();
			informStage.setTitle("알림장");
			informStage.setScene(scene);
			informStage.setResizable(true);
			((Stage) btnInform.getScene().getWindow()).close();
			informStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 공지사항
	public void handlerBtnNoticeAction(ActionEvent event) {
		Parent mainView = null;
		Stage noticeStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_notice.fxml"));
			Scene scene = new Scene(mainView);
			noticeStage = new Stage();
			noticeStage.setTitle("공지사항");
			noticeStage.setScene(scene);
			noticeStage.setResizable(true);
			((Stage) btnNotice.getScene().getWindow()).close();
			noticeStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 로그인창으로 돌아가기
	public void handlerBtnMainAction(ActionEvent event) {
		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setTitle("메인");
			mainStage.setScene(scene);
			mainStage.setResizable(true);
			((Stage) btnMain.getScene().getWindow()).close();
			mainStage.show();

			// (2-2) 메인창 오류 발생 시 메세지 보여줌
		} catch (IOException e) {
			ShareMethod.alertDisplay(1, "오류 발생!", "메인창 부르기 오류!", "다시 한 번 확인해 주세요 (;ㅅ;)");
//			System.out.println("메인창 부르기 오류" + e);
			e.printStackTrace();
		}

	}
}
