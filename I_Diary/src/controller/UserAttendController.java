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

	// 테이블 뷰를 선택했을때 위치값과 객체값을 저장할 수 있는 변수 선언
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

	// 테이블뷰를 선택하고 원아 출석률 버튼을 눌렀을 때 파이차트를 보여주는 이벤트
	private void handlerBtnPieChartAction(ActionEvent event) {

		if (tableView.getSelectionModel().getSelectedItem() == null) {
			ShareMethod.alertDisplay(1, "미선택 오류!", "목록이 선택되지 않았어요!", "다시 확인해 주세요 (;ㅅ;)");
			return;
		}

		// 눌렀을때 위치와 해당된 객체를 가져온다
		selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		selectChildren = tableView.getSelectionModel().getSelectedItems();

		pieChart.setData(FXCollections.observableArrayList(new PieChart.Data("출석", (selectChildren.get(0).getAttend())),
				new PieChart.Data("결석", (selectChildren.get(0).getAbsent())),
				new PieChart.Data("조퇴", (selectChildren.get(0).getEarly()))));
	}

	public void totalList() {
		ArrayList<Attend> list = null;
		AttendDAO attendDAO = new AttendDAO();
		Attend attendVO = new Attend();
		try {
			list = attendDAO.getChildrenCheck(ShareMethod.id);
			if (list == null) {
				ShareMethod.alertDisplay(1, "경고!", "DB 가져오기 오류!", "다시 한 번 확인해 주세요 (;ㅅ;)");
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

	// 테이블뷰 기본 설정하기
	private void tableViewSetting() {
		// 테이블 설정.ArrayList();
		data = FXCollections.observableArrayList();
		// 테이블 설정.테이블뷰를 편집하지 못하게 설정
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
			e.printStackTrace();
		}
	}

}