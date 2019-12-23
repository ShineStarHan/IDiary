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
			stage.setTitle("�˸��� ���뺸��");
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

	// �����ͺ��̽� ���� �о ���̺�信 ��������
	public void totalList() {
		ObservableList<Inform> list = null;
		InformDAO informDAO = new InformDAO();
		Inform informVO = new Inform();
		list = informDAO.getInformTotal(ShareMethod.className);

		if (list == null) {
			ShareMethod.alertDisplay(1, "���!", "DB �������� ����!", "�ٽ� Ȯ���� �ּ��� (;��;)");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			informVO = list.get(i);
			data.add(informVO);
		}
	}

	// �ۼ��ϱ� ��ư�� ������ �� �̺�Ʈ
	private void handlerBtnSendAction(ActionEvent event) {
		InformDAO inform = new InformDAO();

		if (cbName.getSelectionModel().getSelectedIndex() == -1) {
			ShareMethod.alertDisplay(1, "�̼��� ����!", "�޴� ����� ���õ��� �ʾҾ��!", "�ٽ� Ȯ���� �ּ��� (;��;)");
			return;
		} else if (txtAreaContents.getText().equals("")) {
			ShareMethod.alertDisplay(1, "�����Է� ����!", "���� �̱���!", "���߸��� �׸��� ������ Ȯ���� �ּ��� (;��;)");
			return;
		}

		try {
			inform.getInformRegist(ShareMethod.className, cbName.getSelectionModel().getSelectedItem(),
					txtAreaContents.getText());
		} catch (Exception e) {
			ShareMethod.alertDisplay(1, "�˸��� �߼ۿ���!", "�˸��� �߼۽���!", "�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
			e.printStackTrace();
		}
		ShareMethod.alertDisplay(5, "�˸��� �߼ۿϷ�!", "�˸��� �߼ۼ���!", "�˸����� �߼۵Ǿ����. (*'��'*)");
		txtAreaContents.clear();
		data.removeAll(data);
		totalList();

	}

	// ���̺�� �⺻ �����ϱ�
	private void tableViewSetting() {
		
		// ���̺� ����.���̺�並 �������� ���ϰ� ����
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

	// �޺��ڽ� �����ϱ�
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