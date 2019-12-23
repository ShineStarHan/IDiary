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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Children;
import javafx.stage.FileChooser.ExtensionFilter;

public class UserSettingController implements Initializable {
	@FXML
	private Button btnMain;
	@FXML
	private Button btnPrivateSave;
	@FXML
	private ComboBox<String> cbClass;
	@FXML
	private ComboBox<String> cbChildrenGender;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtAddress;
	@FXML
	private TextField txtParentName;
	@FXML
	private ImageView imageView;
	@FXML
	private Button btnSelectFile;

	private String selectFileName = ""; // 이미지 파일명
	private String localUrl = ""; // 이미지 파일 경로
	private Image localImage;
	private File selectedFile = null;
	// 이미지 처리
	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave = new File("C:/images");
	// 이미지 불러올 파일을 저장할 파일 객체 선언
	private File file = null;
	private ChildrenDAO cd = new ChildrenDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		btnPrivateSave.setOnAction(event -> {
			handlerBtnPrivateSaveAction(event);
		});
		btnSelectFile.setOnAction(event -> {
			hanlderBtnSelectFileAction(event);
		});
		comboBoxSetting();
		privateMenuSetting();
	}

	public void privateMenuSetting() {
		txtId.setText(ShareMethod.id);
		txtId.setEditable(false);

		ChildrenDAO cd = new ChildrenDAO();
		ArrayList<Children> list = new ArrayList<Children>();
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
			imageView.setImage(localImage);
			imageView.setFitHeight(200);
			imageView.setFitWidth(200);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
			e1.printStackTrace();
		}
		try {
			System.out.println(selectedFile.toURI().toURL().toString());
			localImage = new Image(localUrl, false);
			imageView.setImage(localImage);
			imageView.setFitHeight(200);
			imageView.setFitWidth(200);
			cd.getChildrenImageUpdate(localUrl, ShareMethod.id);
			btnSelectFile.setDisable(false);
		} catch (Exception e) {
			ShareMethod.alertDisplay(5, "수정 취소!", "사진 수정 취소!", "사진 수정이 취소되었어요. (*'ㅅ'*)");
			e.printStackTrace();
		}

		if (selectedFile != null) {
			selectFileName = selectedFile.getName();
		}
	}


//	// 이미지 저장 메소드
//	public String imageSave(File file) {
//		BufferedInputStream bis = null;
//		BufferedOutputStream bos = null;
//
//		int data = -1;
//		String fileName = null;
//		try {
//			// 이미지 파일명 생성
//			fileName = "admin" + System.currentTimeMillis() + "_" + file.getName();
//			bis = new BufferedInputStream(new FileInputStream(file));
//			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + fileName));
//
//			// 선택한 이미지 파일 InputStream의 마지막에 이르렀을 경우는 -1
//			while ((data = bis.read()) != -1) {
//				bos.write(data);
//				bos.flush();
//			}
//		} catch (Exception e) {
//			e.getMessage();
//		} finally {
//			try {
//				if (bos != null) {
//					bos.close();
//				}
//				if (bis != null) {
//					bis.close();
//				}
//			} catch (IOException e) {
//				e.getMessage();
//			}
//		}
//		return fileName;
//	}

	// 콤보박스 셋팅하기
	public void comboBoxSetting() {
		ObservableList<String> listClass = FXCollections.observableArrayList();
		ObservableList<String> listGender = FXCollections.observableArrayList();
		listGender.add("남자");
		listGender.add("여자");
		listClass.add("이슬반 (5세)");
		listClass.add("풀잎반 (6세)");
		listClass.add("꽃잎반 (7세)");
		cbChildrenGender.setItems(listGender);
		cbClass.setItems(listClass);
	}

	// 개인정보 저장 버튼을 눌렀을 때 이벤트
	public void handlerBtnPrivateSaveAction(ActionEvent event) {
		
		System.out.println(ShareMethod.id);
		System.out.println(cbChildrenGender.getSelectionModel().getSelectedIndex());
		System.out.println(cbClass.getSelectionModel().getSelectedItem());
		System.out.println(txtAddress.getText());
		System.out.println(txtPhone.getText());
		System.out.println(txtParentName.getText());
		
		int count = 0;
		try {
			if (txtAddress.getText().equals("") || txtId.getText().equals("") || txtParentName.getText().equals("")
					|| txtPhone.getText().equals("") || cbClass.getSelectionModel().getSelectedItem() == null
					|| cbChildrenGender.getSelectionModel().getSelectedItem() == null) {
				throw new Exception();
			} else {
				count++;
			}
		} catch (Exception e) {
			ShareMethod.alertDisplay(1, "정보입력오류!", "정보 미기재!", "빠뜨리신 항목이 없는지 확인해 주세요 (;ㅅ;)");
		}
		try {
			if (count == 1) {
				ChildrenDAO cd = new ChildrenDAO();
				cd.getChildrenUpdate(new Children( ShareMethod.id,cbClass.getSelectionModel().getSelectedItem(),
						cbChildrenGender.getSelectionModel().getSelectedItem(), txtParentName.getText(),
						txtPhone.getText(), txtAddress.getText()));
			}
		} catch (Exception e) {
		}
	}

	// 학부모 메인창으로 이동하기
	public void handlerBtnMainAction(ActionEvent event) {
		Parent mainView = null;
		Stage informStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/user_menu.fxml"));
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