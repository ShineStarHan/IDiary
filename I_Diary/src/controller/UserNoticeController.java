package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.chart.PieChart;
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
import model.Notice;

public class UserNoticeController implements Initializable {
	@FXML
	private Button btnMain;
	@FXML
	private TableView<Notice> tableView;
	ObservableList<Notice> data;

	// ���̺� �並 ���������� ��ġ���� ��ü���� ������ �� �ִ� ���� ����
//	private int selectedIndex;
	private ObservableList<Notice> selectedNotice;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		tableView.setOnMouseClicked(event -> {
			handlerDoubleClick(event);
		});
		tableViewSetting();
		totalList();
	}
	
	public void handlerDoubleClick(MouseEvent event) {
		Parent viewRoot;
		NoticeDAO nd=new NoticeDAO();
		ObservableList<Notice> list=FXCollections.observableArrayList();
		try {
			if (event.getClickCount() != 2) {
				return;
			}
			viewRoot = FXMLLoader.load(getClass().getResource("/view/user_notice_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("�������� ���뺸��");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			TextField txtTitle = (TextField) viewRoot.lookup("#txtTitle");
			TextArea txtAreaContents = (TextArea) viewRoot.lookup("#txtAreaContents");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			txtTitle.setEditable(false);
			txtAreaContents.setEditable(false);
			list=nd.getEditTotal(tableView.getSelectionModel().getSelectedItem().getNo());
			
			txtTitle.setText(list.get(0).getTitle());
			txtAreaContents.setText(list.get(0).getContents());
			btnExit.setOnAction(event2 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void totalList() {
		ObservableList<Notice> list = FXCollections.observableArrayList();
		NoticeDAO noticeDAO = new NoticeDAO();
		Notice noticeVO = new Notice();
		ChildrenDAO ad = new ChildrenDAO();
		String str = ad.getChildrenClass(ShareMethod.id);
		list = noticeDAO.getChildrenTotal("Ǯ�ٹ� (6��)");

		if (list == null) {
			ShareMethod.alertDisplay(1, "���!", "DB �������� ����!", "�ٽ� �� �� Ȯ���� �ּ��� (;��;)");
			return;
		}
		data.removeAll(data);
		for (int i = 0; i < list.size(); i++) {
			noticeVO = list.get(i);
			data.add(noticeVO);
		}
	}

	// ���̺�� �⺻ �����ϱ�
	private void tableViewSetting() {
		// ���̺� ����.ArrayList();
		data = FXCollections.observableArrayList();
		// ���̺� ����.���̺�並 �������� ���ϰ� ����
		tableView.setEditable(false);

		TableColumn colNo = tableView.getColumns().get(0);
		colNo.setCellValueFactory(new PropertyValueFactory("no"));

		TableColumn colTitle = tableView.getColumns().get(1);
		colTitle.setCellValueFactory(new PropertyValueFactory("title"));

		TableColumn colDate = tableView.getColumns().get(2);
		colDate.setCellValueFactory(new PropertyValueFactory("date"));

		tableView.setItems(data);
	}

	// �кθ� ����â���� �̵��ϱ�
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
