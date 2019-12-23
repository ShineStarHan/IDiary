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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Children;
import model.Inform;

public class AdminInformController implements Initializable {
	@FXML
	private Button btnMain;
	@FXML
	private Button btnSend;
	@FXML
	private ComboBox<String> cbName;
	@FXML
	private TextArea txtAreaContents;
	@FXML
	private TableView<Inform> tableView;
	ObservableList<Inform> data =FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		btnSend.setOnAction(event -> {
			handlerBtnSendAction(event);
		});
		tableView.setOnMouseClicked(event -> {
			handlerDoubleClick(event);
		});
		tableViewSetting();
		comboBoxSetting();
		totalList();
	}

	public void handlerDoubleClick(MouseEvent event) {
		Parent viewRoot;
		try {
			if (event.getClickCount() != 2) {
				return;
			}
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_inform_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("알림장 내용보기");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			TextField txtName = (TextField) viewRoot.lookup("#txtName");
			TextArea txtAreaContents = (TextArea) viewRoot.lookup("#txtAreaContents");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			txtName.setText(tableView.getSelectionModel().getSelectedItem().getSend().toString());
			txtAreaContents.setText(tableView.getSelectionModel().getSelectedItem().getInform().toString());
			btnExit.setOnAction(event2 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 데이터베이스 값을 읽어서 테이블뷰에 가져오기
	public void totalList() {
		ObservableList<Inform> list = null;
		InformDAO informDAO = new InformDAO();
		Inform informVO = new Inform();
		list = informDAO.getInformTotal(ShareMethod.className);

		if (list == null) {
			ShareMethod.alertDisplay(1, "경고!", "DB 가져오기 오류!", "다시 확인해 주세요 (;ㅅ;)");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			informVO = list.get(i);
			data.add(informVO);
		}
	}

	// 작성하기 버튼을 눌렀을 때 이벤트
	private void handlerBtnSendAction(ActionEvent event) {
		InformDAO inform = new InformDAO();

		if (cbName.getSelectionModel().getSelectedIndex() == -1) {
			ShareMethod.alertDisplay(1, "미선택 오류!", "받는 사람이 선택되지 않았어요!", "다시 확인해 주세요 (;ㅅ;)");
			return;
		} else if (txtAreaContents.getText().equals("")) {
			ShareMethod.alertDisplay(1, "내용입력 오류!", "내용 미기재!", "빠뜨리신 항목이 없는지 확인해 주세요 (;ㅅ;)");
			return;
		}

		try {
			inform.getInformRegist(ShareMethod.className, cbName.getSelectionModel().getSelectedItem(),
					txtAreaContents.getText());
		} catch (Exception e) {
			ShareMethod.alertDisplay(1, "알림장 발송오류!", "알림장 발송실패!", "다시 한 번 확인해 주세요. (;ㅅ;)");
			e.printStackTrace();
		}
		ShareMethod.alertDisplay(5, "알림장 발송완료!", "알림장 발송성공!", "알림장이 발송되었어요. (*'ㅅ'*)");
		txtAreaContents.clear();
		data.removeAll(data);
		totalList();

	}

	// 테이블뷰 기본 설정하기
	private void tableViewSetting() {
		
		// 테이블 설정.테이블뷰를 편집하지 못하게 설정
		tableView.setEditable(false);

		TableColumn colNo = tableView.getColumns().get(0);
		colNo.setCellValueFactory(new PropertyValueFactory("no"));

		TableColumn colSend = tableView.getColumns().get(1);
		colSend.setCellValueFactory(new PropertyValueFactory("send"));

		TableColumn colReceive = tableView.getColumns().get(2);
		colReceive.setCellValueFactory(new PropertyValueFactory("receive"));

//		TableColumn colInform = tableView.getColumns().get(3);
//		colInform.setCellValueFactory(new PropertyValueFactory("inform"));

		TableColumn colDate = tableView.getColumns().get(3);
		colDate.setCellValueFactory(new PropertyValueFactory("date"));

		tableView.setItems(data);
	}

	// 콤보박스 셋팅하기
	public void comboBoxSetting() {
		AdminDAO ad = new AdminDAO();
		ObservableList<String> listName = FXCollections.observableArrayList();
		try {
			listName = ad.getInformReceiverSet(ShareMethod.className);
			cbName.setItems(listName);
		} catch (Exception e) {
			e.printStackTrace();
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