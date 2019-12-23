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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Attend;

public class UserAttendController implements Initializable {

	@FXML
	private Button btnMain;
	@FXML
	private Button btnPieChart;
	@FXML
	private PieChart pieChart;

	@FXML
	private TableView<Attend> tableView;
	ObservableList<Attend> data;

	// ���̺� �並 ���������� ��ġ���� ��ü���� ������ �� �ִ� ���� ����
	private int selectedIndex;
	private ObservableList<Attend> selectChildren;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		btnPieChart.setOnAction(event -> {
			handlerBtnPieChartAction(event);
		});
		tableViewSetting();
		totalList();
	}

	// ���̺�並 �����ϰ� ���� �⼮�� ��ư�� ������ �� ������Ʈ�� �����ִ� �̺�Ʈ
	private void handlerBtnPieChartAction(ActionEvent event) {

		if (tableView.getSelectionModel().getSelectedItem() == null) {
			ShareMethod.alertDisplay(1, "�̼��� ����!", "����� ���õ��� �ʾҾ��!", "�ٽ� Ȯ���� �ּ��� (;��;)");
			return;
		}

		// �������� ��ġ�� �ش�� ��ü�� �����´�
		selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		selectChildren = tableView.getSelectionModel().getSelectedItems();

		pieChart.setData(FXCollections.observableArrayList(new PieChart.Data("�⼮", (selectChildren.get(0).getAttend())),
				new PieChart.Data("�Ἦ", (selectChildren.get(0).getAbsent())),
				new PieChart.Data("����", (selectChildren.get(0).getEarly()))));
	}

	public void totalList() {
		ArrayList<Attend> list = null;
		AttendDAO attendDAO = new AttendDAO();
		Attend attendVO = new Attend();
		try {
			list = attendDAO.getChildrenCheck(ShareMethod.id);
			if (list == null) {
				ShareMethod.alertDisplay(1, "���!", "DB �������� ����!", "�ٽ� �� �� Ȯ���� �ּ��� (;��;)");
				return;
			}

			data.removeAll(data);
			for (int i = 0; i < list.size(); i++) {
				attendVO = list.get(i);
				data.add(attendVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ���̺�� �⺻ �����ϱ�
	private void tableViewSetting() {
		// ���̺� ����.ArrayList();
		data = FXCollections.observableArrayList();
		// ���̺� ����.���̺�並 �������� ���ϰ� ����
		tableView.setEditable(false);

		TableColumn colMonth = tableView.getColumns().get(0);
		colMonth.setCellValueFactory(new PropertyValueFactory("month"));

		TableColumn colName = tableView.getColumns().get(1);
		colName.setCellValueFactory(new PropertyValueFactory("name"));

		TableColumn colAttend = tableView.getColumns().get(2);
		colAttend.setCellValueFactory(new PropertyValueFactory("attend"));

		TableColumn colAbsent = tableView.getColumns().get(3);
		colAbsent.setCellValueFactory(new PropertyValueFactory("absent"));

		TableColumn colEarly = tableView.getColumns().get(4);
		colEarly.setCellValueFactory(new PropertyValueFactory("early"));

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
			e.printStackTrace();
		}
	}

}