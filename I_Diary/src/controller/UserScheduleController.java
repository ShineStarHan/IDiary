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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Schedule;

public class UserScheduleController implements Initializable {
	@FXML
	private Button btnMain;
	@FXML
	private TableView<Schedule> tableView;
	@FXML
	private Button btnSearch;
	@FXML
	private ComboBox<String> cbWeek;
	@FXML
	private ComboBox<String> cbMonth;

	ObservableList<Schedule> data = FXCollections.observableArrayList(); // ���̺�信 �����ֱ����ؼ� ����� ����Ÿ

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		btnSearch.setOnAction(event -> {
			handlerBtnSearchAction(event);
		});
		comboBoxSetting();
	}

	// ��ȸ ��ư�� ������ �� �̺�Ʈ
	private void handlerBtnSearchAction(ActionEvent event) {
		totalList();
		tableViewSetting();
		if(cbMonth.getSelectionModel().getSelectedIndex()==-1||cbWeek.getSelectionModel().getSelectedIndex()==-1) {
			
			ShareMethod.alertDisplay(1,"���!", "�׸� ���� ����!", "��� �׸��� ������ �ּ��� (;��;)");
		}
	}

	// �����ͺ��̽� ���� �о ���̺�信 ��������
	public void totalList() {
		ArrayList<Schedule> list = null;
		ScheduleDAO scheduleDAO = new ScheduleDAO();
		Schedule scheduleVO = new Schedule();
		String month = cbMonth.getSelectionModel().getSelectedItem();
		String week = cbWeek.getSelectionModel().getSelectedItem();
		try {
			if(cbMonth.getSelectionModel().getSelectedItem()==null||cbWeek.getSelectionModel().getSelectedItem()==null) {
				throw new Exception();
			}
			
		} catch (Exception e) {
			ShareMethod.alertDisplay(1, "���!", "�׸� ���� ����!", "��� �׸��� ������ �ּ��� (;��;)");
		}
		
		
		list = scheduleDAO.getDuplicateCheck(month, week);
		if (list == null) {
			ShareMethod.alertDisplay(1, "���!", "DB �������� ����!", "�ٽ� �� �� Ȯ���� �ּ��� (;��;)");
			return;
		} else {
			data.removeAll(data);
			for (int i = 0; i < list.size(); i++) {
				scheduleVO = list.get(i);
				data.add(scheduleVO);
			}
		}
	}

	// ���̺�� �⺻ �����ϱ�
	private void tableViewSetting() {
		// ���̺� ����.ArrayList();

		// ���̺� ����.���̺�並 �������� ���ϰ� ����
		tableView.setEditable(false);

		TableColumn colMon = tableView.getColumns().get(0);
		colMon.setStyle("-fx-alignment: CENTER;");
		colMon.setCellValueFactory(new PropertyValueFactory("mon"));

		TableColumn colTue = tableView.getColumns().get(1);
		colTue.setStyle("-fx-alignment: CENTER;");
		colTue.setCellValueFactory(new PropertyValueFactory("tue"));

		TableColumn colWen = tableView.getColumns().get(2);
		colWen.setStyle("-fx-alignment: CENTER;");
		colWen.setCellValueFactory(new PropertyValueFactory("wen"));

		TableColumn colThur = tableView.getColumns().get(3);
		colThur.setStyle("-fx-alignment: CENTER;");
		colThur.setCellValueFactory(new PropertyValueFactory("thur"));

		TableColumn colFri = tableView.getColumns().get(4);
		colFri.setStyle("-fx-alignment: CENTER;");
		colFri.setCellValueFactory(new PropertyValueFactory("fri"));

		TableColumn colSat = tableView.getColumns().get(5);
		colSat.setStyle("-fx-alignment: CENTER;");
		colSat.setCellValueFactory(new PropertyValueFactory("sat"));

		tableView.setFixedCellSize(45.0);
		tableView.setItems(data);
	}

	// �޺��ڽ� �����ϱ�
	public void comboBoxSetting() {
		ObservableList<String> listMonth = FXCollections.observableArrayList();
		ObservableList<String> listWeek = FXCollections.observableArrayList();

		listMonth.add("3");
		listMonth.add("4");
		listMonth.add("5");
		listMonth.add("6");
		listMonth.add("7");
		listMonth.add("8");
		listMonth.add("9");
		listMonth.add("10");
		listMonth.add("11");
		listMonth.add("12");
		listMonth.add("1");
		listMonth.add("2");

		listWeek.add("1");
		listWeek.add("2");
		listWeek.add("3");
		listWeek.add("4");
		listWeek.add("5");

		cbMonth.setItems(listMonth);
		cbWeek.setItems(listWeek);
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
			e.printStackTrace();
		}
	}

}
