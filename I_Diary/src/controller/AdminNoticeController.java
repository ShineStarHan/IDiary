package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Admin;
import model.Notice;

public class AdminNoticeController implements Initializable {
	@FXML
	private Button btnMain;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnRemove;
	@FXML
	private TableView<Notice> tableView;
	ObservableList<Notice> data=FXCollections.observableArrayList(); // 테이블뷰에 보여주기위해서 저장된 데이타

	// 테이블 뷰를 선택했을때 위치값과 객체값을 저장할 수 있는 변수 선언
	private int selectedIndex;
	private ObservableList<Notice> selectNotice;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		btnAdd.setOnAction(event -> {
			handlerBtnAddAction(event);
		});
		btnRemove.setOnAction(event -> {
			handlerBtnRemoveAction(event);
		});
		tableView.setOnMouseClicked(event -> {
			handlerDoubleClick(event);
		});
		totalList();
		tableViewSetting();
	}

	// 테이블 목록을 더블클릭 했을 때 공지사항 내용 수정하기 이벤트
	public void handlerDoubleClick(MouseEvent event) {
		Parent writeRoot;
		NoticeDAO nd = new NoticeDAO();
		Notice notice = null;
		ObservableList<Notice> list = FXCollections.observableArrayList();
		try {
			if (event.getClickCount() != 2) {
				return;
			}
			writeRoot = FXMLLoader.load(getClass().getResource("/view/admin_notice_edit_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("공지사항 수정하기");
			Button btnEdit = (Button) writeRoot.lookup("#btnEdit");
			Button btnCancel = (Button) writeRoot.lookup("#btnCancel");
			TextField txtTitle = (TextField) writeRoot.lookup("#txtTitle");
			TextArea txtAreaContents = (TextArea) writeRoot.lookup("#txtAreaContents");
			Scene scene = new Scene(writeRoot);
			stage.setScene(scene);
			stage.show();
			int num = data.get(tableView.getSelectionModel().getSelectedIndex()).getNo();
			list = nd.getEditTotal(num);
			txtTitle.setText(list.get(0).getTitle().toString());
			txtAreaContents.setText(list.get(0).getContents().toString());
			
			btnEdit.setOnAction(event5 -> {
				int count = 0;

				try {
					if (txtAreaContents.getText().equals("") || txtTitle.getText().equals("")) {
						throw new Exception();
					} else {
						count++;
					}
				} catch (Exception e) {
					ShareMethod.alertDisplay(1, "정보입력오류!", "정보 미기재!", "빠뜨리신 항목이 없는지 확인해 주세요 (;ㅅ;)");
				}

				try {
					if (count == 1) {
						nd.getNoticeUpdate(new Notice(txtTitle.getText(), txtAreaContents.getText()), num);
						ShareMethod.alertDisplay(5, "공지사항 수정완료!", "공지사항 수정성공!", "공지사항이 수정되었어요. (*'ㅅ'*)");
						((Stage) btnCancel.getScene().getWindow()).close();
						totalList();

					}
				} catch (Exception e) {
					ShareMethod.alertDisplay(1, "공지사항 수정오류!", "공지사항 수정실패!", "다시 한 번 확인해 주세요. (;ㅅ;)");
				}

			});

			btnCancel.setOnAction(event2 -> {
				
				((Stage) btnCancel.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 데이터베이스 값을 읽어서 테이블뷰에 가져오기
	public void totalList() {
		ObservableList<Notice> list = FXCollections.observableArrayList();
		NoticeDAO noticeDAO = new NoticeDAO();
		Notice noticeVO = new Notice();
		AdminDAO ad = new AdminDAO();
		String str = ShareMethod.className;
				//ad.getAdminClass(ShareMethod.id);
		
		list = noticeDAO.getChildrenTotal(str);

		if (list == null) {
			ShareMethod.alertDisplay(1, "경고!", "DB 가져오기 오류!", "다시 한 번 확인해 주세요 (;ㅅ;)");
			return;
		}
		data.removeAll(data);
		for (int i = 0; i < list.size(); i++) {
			noticeVO = list.get(i);
			System.out.println(noticeVO.getDate()+i);
			data.add(noticeVO);
		}
	}

	// 테이블뷰 기본 설정하기
	private void tableViewSetting() {
		// 테이블 설정.ArrayList();
		//data = FXCollections.observableArrayList();
		// 테이블 설정.테이블뷰를 편집하지 못하게 설정
		tableView.setEditable(false);

		TableColumn colNo = tableView.getColumns().get(0);
		colNo.setCellValueFactory(new PropertyValueFactory("no"));

		TableColumn colTitle = tableView.getColumns().get(1);
		colTitle.setCellValueFactory(new PropertyValueFactory("title"));

		TableColumn colDate = tableView.getColumns().get(2);
		colDate.setCellValueFactory(new PropertyValueFactory("date"));
		
		tableView.setItems(data);

	}

	// 추가 버튼을 눌렀을 때 이벤트
	private void handlerBtnAddAction(ActionEvent event) {
		Parent writeRoot;
		try {
			writeRoot = FXMLLoader.load(getClass().getResource("/view/admin_notice_add_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("공지사항 추가하기");
			Button btnAdd = (Button) writeRoot.lookup("#btnAdd");
			Button btnCancel = (Button) writeRoot.lookup("#btnCancel");
			TextField txtTitle = (TextField) writeRoot.lookup("#txtTitle");
			TextArea txtAreaContents = (TextArea) writeRoot.lookup("#txtAreaContents");
			Scene scene = new Scene(writeRoot);
			stage.setScene(scene);
			stage.show();
			btnCancel.setOnAction(event2 -> {
				((Stage) btnCancel.getScene().getWindow()).close();
			});

			btnAdd.setOnAction(event3 -> {
				int count = 0;
				Notice notice = null;
				AdminDAO ad = new AdminDAO();
				NoticeDAO nd = new NoticeDAO();
				String str = ad.getAdminClass(ShareMethod.id);
				try {
					if (txtTitle.getText().equals("") || txtAreaContents.getText().equals("")) {
						throw new Exception();
					} else {
						count++;
					}
				} catch (Exception e) {
					ShareMethod.alertDisplay(1, "정보입력오류!", "정보 미기재!", "빠뜨리신 항목이 없는지 확인해 주세요 (;ㅅ;)");
					e.printStackTrace();
				}
				try {
					if (count == 1) {
						nd.getNoticeRegist(new Notice(txtTitle.getText(), txtAreaContents.getText(), str));
						ShareMethod.alertDisplay(5, "정보등록 완료!", "정보등록 성공!", "정보가 등록되었어요. (*'ㅅ'*)");
						((Stage) btnCancel.getScene().getWindow()).close();
						totalList();
					} else {
						throw new Exception();
					}
				} catch (Exception e2) {
					ShareMethod.alertDisplay(1, "정보등록 오류!", "정보등록 실패!", "다시 한 번 확인해 주세요. (;ㅅ;)");
				}

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 삭제 버튼을 눌렀을 때 이벤트
	public void handlerBtnRemoveAction(ActionEvent event) {
		NoticeDAO noticeDAO = new NoticeDAO();

		if (tableView.getSelectionModel().getSelectedItem() == null) {
			ShareMethod.alertDisplay(1, "미선택 오류!", "목록이 선택되지 않았어요!", "다시 한 번 확인해 주세요 (;ㅅ;)");
			return;
		}
		
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
				int num = data.get(tableView.getSelectionModel().getSelectedIndex()).getNo();
				try {
					noticeDAO.getChildrenDelete(num);
					data.removeAll(data);
					totalList();
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
			e.printStackTrace();
		}
	}

}