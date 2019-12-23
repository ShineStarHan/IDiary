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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Schedule;

public class AdminScheduleController implements Initializable {
	@FXML
	private Button btnMain;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnAdd;
	@FXML
	private TableView<Schedule> tableView;
	@FXML
	private TextField txtMon;
	@FXML
	private TextField txtTue;
	@FXML
	private TextField txtWen;
	@FXML
	private TextField txtThur;
	@FXML
	private TextField txtFri;
	@FXML
	private TextField txtSat;
	@FXML
	private Button btnSearch;
	@FXML
	private ComboBox<String> cbMonth;
	@FXML
	private ComboBox<String> cbWeek;

	ObservableList<Schedule> data = FXCollections.observableArrayList(); // 테이블뷰에 보여주기위해서 저장된 데이타

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		btnEdit.setOnAction(event -> {
			handlerBtnEditAction(event);
		});
		btnAdd.setOnAction(event -> {
			handlerBtnAddAction(event);
		});
		btnSearch.setOnAction(event -> {
			handlerBtnSearchAction(event);
		});
		comboBoxSetting();
	}

	public void handlerBtnSearchAction(ActionEvent event) {
		if (cbMonth.getSelectionModel().getSelectedIndex() == -1
				|| cbWeek.getSelectionModel().getSelectedIndex() == -1) {
			ShareMethod.alertDisplay(1, "경고!", "항목 선택 오류!", "모든 항목을 선택해 주세요 (;ㅅ;)");
			return;
		} else {

			totalList();
			tableViewSetting();
			txtMon.setText(tableView.getColumns().get(0).getCellData(0).toString());
			txtTue.setText(tableView.getColumns().get(1).getCellData(0).toString());
			txtWen.setText(tableView.getColumns().get(2).getCellData(0).toString());
			txtThur.setText(tableView.getColumns().get(3).getCellData(0).toString());
			txtFri.setText(tableView.getColumns().get(4).getCellData(0).toString());
			txtSat.setText(tableView.getColumns().get(5).getCellData(0).toString());

		}
	}

	// 데이터베이스 값을 읽어서 테이블뷰에 가져오기
	public void totalList() {
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		ScheduleDAO scheduleDAO = new ScheduleDAO();
		Schedule scheduleVO = new Schedule();
		String month = cbMonth.getSelectionModel().getSelectedItem();
		String week = cbWeek.getSelectionModel().getSelectedItem();
		list = scheduleDAO.getDuplicateCheck(month, week);
		if (list == null) {
			ShareMethod.alertDisplay(1, "경고!", "DB 가져오기 오류!", "다시 한 번 확인해 주세요 (;ㅅ;)");
			return;
		} else {
			data.removeAll(data);
			for (int i = 0; i < list.size(); i++) {
				scheduleVO = list.get(i);
				data.add(scheduleVO);
			}
		}

	}

	// 테이블뷰 기본 설정하기
	private void tableViewSetting() {
		// 테이블 설정.ArrayList();

		// 테이블 설정.테이블뷰를 편집하지 못하게 설정
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

	// 콤보박스 셋팅하기
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

	// 추가 버튼을 눌렀을 때 이벤트
	private void handlerBtnAddAction(ActionEvent event) {
		String month = cbMonth.getSelectionModel().getSelectedItem();
		String week = cbWeek.getSelectionModel().getSelectedItem();
		Schedule schedule = new Schedule(month, week, txtMon.getText(), txtTue.getText(), txtWen.getText(),
				txtThur.getText(), txtFri.getText(), txtSat.getText());
		ScheduleDAO scheduleDAO = new ScheduleDAO();
		ArrayList<Schedule> list = scheduleDAO.getDuplicateCheck(month, week);
		try {
			if (list.size() == 0) {
				int num = scheduleDAO.getScheduleRegist(schedule);
				if (num == 1) {
					ShareMethod.alertDisplay(5, "일정 등록!", "일정 등록 성공!", "일정이 등록되었어요. (*'ㅅ'*)");

				} else {
					throw new Exception();
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			ShareMethod.alertDisplay(1, "일정 등록!", "일정 등록 실패!", "다시 한 번 확인해 주세요. (;ㅅ;)");
			e.printStackTrace();
		}
	}

	// 수정완료 버튼을 눌렀을 때 이벤트
	private void handlerBtnEditAction(ActionEvent event) {
		try {
			String month = cbMonth.getSelectionModel().getSelectedItem();
			String week = cbWeek.getSelectionModel().getSelectedItem();
			Schedule schedule = new Schedule(month, week, txtMon.getText(), txtTue.getText(), txtWen.getText(),
					txtThur.getText(), txtFri.getText(), txtSat.getText());

			ScheduleDAO scheduleDAO = new ScheduleDAO();
			Schedule scheduleVO = scheduleDAO.getScheduleUpdate(schedule);

			data.remove(schedule);
			data.add(schedule);

		} catch (Exception e1) {
			// 알림창 띄우기
			ShareMethod.alertDisplay(1, "저장실패!", "저장등록 실패!", "다시 한 번 확인해 주세요 (;ㅅ;)");
			e1.printStackTrace();
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