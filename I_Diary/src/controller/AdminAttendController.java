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
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Attend;

public class AdminAttendController implements Initializable {

	@FXML
	private Button btnMain;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnRemove;
	@FXML
	private Button btnView;
	@FXML
	private Button btnBarChart;
	@FXML
	private Button btnPieChart;
	@FXML
	private Button btnSearch;
	@FXML
	private TextField txtMonth;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtAttend;
	@FXML
	private TextField txtAbsent;
	@FXML
	private TextField txtEarly;
	@FXML
	private TextField txtSearch;
	@FXML
	private ComboBox<String> cbMonth;
	@FXML
	private ComboBox<String> cbMonthEdit;
	@FXML
	private ComboBox<String> cbName;
	@FXML
	private TableView<Attend> tableView;
	ObservableList<Attend> data = FXCollections.observableArrayList(); // 테이블뷰에 보여주기위해서 저장된 데이타

	// 테이블 뷰를 선택했을때 위치값과 객체값을 저장할 수 있는 변수 선언
	private int selectedIndex;
	private ObservableList<Attend> selectChildren;

	//private String selectMonth;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		//파이차트
		btnPieChart.setOnAction(event -> {
			handlerBtnPieChartAction(event);
		});
		//바차트
		btnBarChart.setOnAction(event -> {
			handlerBtnBarChartAction(event);
		});
		//출석 정보 입력
		btnAdd.setOnAction(event -> {
			handlerBtnAddAction(event);
		});
		//출석정보 수정
		btnEdit.setOnAction(event -> {
			handlerBtnEditAction(event);
		});
		//출석정보 삭제
		btnRemove.setOnAction(event -> {
			handlerBtnRemoveAction(event);
		});
		//해당이름 출석정보 검색
		btnSearch.setOnAction(event -> {
			handlerBtnSearchAction(event);
		});
		//해당 월 출석정보 검색
		btnView.setOnAction(event -> {
			handlerBtnViewAction(event);
		});
		//테이블뷰 항목 클릭시 이벤트
		tableView.setOnMousePressed((e) -> {
			handlerTableViewPressedAction(e);
		});
		//DB에서 출석정보 가져오기
		totalList();
		//DB에서 가져온 내용 테이블뷰에 입력
		tableViewSetting();
		//콤보박스리스트
		comboBoxSetting();
	}

	// 콤보박스를 선택하고 버튼을 클릭했을 때 이벤트
	public void handlerBtnViewAction(ActionEvent event) {
		AttendDAO ad = new AttendDAO();
		String month = cbMonth.getSelectionModel().getSelectedItem().toString();
		System.out.println("먼쓰 : "+month);
		ArrayList<Attend> al = ad.getSelectedList(month,ShareMethod.className);

		try {
			if (al == null) {
				throw new Exception("검색오류!");
			}
			data.removeAll(data);

			for (Attend svo : al) {
				data.add(svo);
			}
		} catch (Exception e) {
			// 알림창 띄우기
			ShareMethod.alertDisplay(1, "월 미선택!", "월 미선택!", "월을 선택해 주세요. (;ㅅ;)");
		}

	}

	// 검색 버튼을 눌렀을 때 이벤트
	private void handlerBtnSearchAction(ActionEvent event) {
		try {
			ArrayList<Attend> list = new ArrayList<Attend>();
			AttendDAO attendDAO = new AttendDAO();
			list = attendDAO.getChildrenCheck(txtSearch.getText());

			if (list == null) {
				throw new Exception("검색오류!");
			}

			data.removeAll(data);
			for (Attend svo : list) {
				data.add(svo);
			}
		} catch (Exception e) {
			// 알림창 띄우기
			ShareMethod.alertDisplay(1, "이름 검색 오류!", "이름이 정확하지 않습니다!", "이름을 정확히 입력해 주세요 (;ㅅ;)");
		}
	}

	// 테이블 뷰를 눌렀을 때 텍스트필드에 값을 가져오기
	private void handlerTableViewPressedAction(MouseEvent e) {
		// 눌렀을때 위치와 해당된 객체를 가져온다
		selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		selectChildren = tableView.getSelectionModel().getSelectedItems();

		cbMonthEdit.setValue(String.valueOf(selectChildren.get(0).getMonth()));
		cbName.setValue(selectChildren.get(0).getName());
		txtAttend.setText(String.valueOf(selectChildren.get(0).getAttend()));
		txtAbsent.setText(String.valueOf(selectChildren.get(0).getAbsent()));
		txtEarly.setText(String.valueOf(selectChildren.get(0).getEarly()));
	}

	// 데이터베이스 값을 읽어서 테이블뷰에 가져오기
	public void totalList() {
		ArrayList<Attend> list = null;
		AttendDAO attendDAO = new AttendDAO();
		Attend attendVO = new Attend();
		list = attendDAO.getChildrenTotal(ShareMethod.className);

		if (list == null) {
			ShareMethod.alertDisplay(1, "경고!", "DB 가져오기 오류!", "다시  한 번 확인해 주세요 (;ㅅ;)");
			return;
		}
		data.removeAll(data);
		for (int i = 0; i < list.size(); i++) {
			attendVO = list.get(i);
			data.add(attendVO);
		}
	}

	// 추가 버튼을 눌렀을 때 이벤트
	private void handlerBtnAddAction(ActionEvent event) {
		try {
			Attend attendVO = new Attend(cbMonthEdit.getSelectionModel().getSelectedItem(),
					cbName.getSelectionModel().getSelectedItem(), Integer.parseInt(txtAttend.getText().trim()),
					Integer.parseInt(txtAbsent.getText().trim()), Integer.parseInt(txtEarly.getText().trim()));
			ArrayList<Attend> list = new ArrayList<Attend>();

			// data.remove(selectedIndex);
			data.add(selectedIndex, attendVO);

			// DB를 부르는 명령
			AttendDAO attendDAO = new AttendDAO();
			// 데이터베이스 테이블에 입력값을 입력하는 함수
			list = attendDAO.SelectedList(cbMonthEdit.getSelectionModel().getSelectedItem(),
					cbName.getSelectionModel().getSelectedItem());
			if (list.size() == 0) {

				int count = attendDAO.getChildrenregiste(attendVO, ShareMethod.className);
				if (count != 0) {
					data.removeAll(data);
					totalList();
				} else {

					throw new Exception("데이터베이스 등록 실패 (;ㅅ;)");
				}
				// 알림창 띄우기
				ShareMethod.alertDisplay(1, "추가성공!", "테이블 등록 성공 (*'ㅂ'*)", "추카추카~♥");
			} else {
				
				totalList();
				throw new Exception("데이터베이스 등록 실패 (;ㅅ;)");
			}
		} catch (Exception e) {
			// 알림창 띄우기
			ShareMethod.alertDisplay(1, "추가실패!", "다시 입력해 주세요 (;ㅅ;)", e.toString());
			e.printStackTrace();
			return;
		}
	}

	// 삭제 버튼을 눌렀을 때 이벤트
	private void handlerBtnRemoveAction(ActionEvent event) {
		AttendDAO attendDAO = new AttendDAO();
		String num = tableView.getSelectionModel().getSelectedItem().getMonth();
		String kid = tableView.getSelectionModel().getSelectedItem().getName();
		ArrayList<Attend> ar = attendDAO.getChildrenTotal(ShareMethod.className);

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
				try {
					attendDAO.getChildrenDelete(kid, num);
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

	// 수정 버튼을 눌렀을 때 이벤트
	private void handlerBtnEditAction(ActionEvent event) {
		try {
			Attend avo = new Attend(cbMonthEdit.getSelectionModel().getSelectedItem(),
					cbName.getSelectionModel().getSelectedItem(), Integer.parseInt(txtAttend.getText().trim()),
					Integer.parseInt(txtAbsent.getText().trim()), Integer.parseInt(txtEarly.getText().trim()));

			AttendDAO attendDAO = new AttendDAO();
			Attend attendVO = attendDAO.getChildrenUpdate(avo);
			totalList();
			if (attendVO != null) {
				data.remove(selectedIndex);
				data.add(selectedIndex, avo);
			} else {
				throw new Exception("수정등록 오류!");
			}
			ShareMethod.alertDisplay(1, "수정성공!", "수정등록 성공!", "테이블이 수정되었어요. (*'ㅅ'*)");
		} catch (Exception e) {
			// 알림창 띄우기
			ShareMethod.alertDisplay(1, "수정실패!", "수정등록 실패!", "다시 한 번 확인해 주세요. (;ㅅ;)");
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

	// 월별 출석률 버튼을 눌렀을 때 이벤트
	private void handlerBtnPieChartAction(ActionEvent event) {
		Parent pieChartRoot;
		try {
			if (cbMonthEdit.getSelectionModel().getSelectedIndex() == -1) {
				ShareMethod.alertDisplay(1, "미선택 오류!", "월이 선택되지 않았어요!", "다시 확인해 주세요 (;ㅅ;)");
			} else if (tableView.getSelectionModel().getSelectedItem() == null) {
				ShareMethod.alertDisplay(1, "미선택 오류!", "목록이 선택되지 않았어요!", "다시 확인해 주세요 (;ㅅ;)");
				return;
			}
			// 눌렀을때 위치와 해당된 객체를 가져온다
			selectedIndex = tableView.getSelectionModel().getSelectedIndex();
			selectChildren = tableView.getSelectionModel().getSelectedItems();

			pieChartRoot = FXMLLoader.load(getClass().getResource("/view/admin_attend_piechart_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnPieChart.getScene().getWindow());
			PieChart pieChart = (PieChart) pieChartRoot.lookup("#pieChart");
			Button btnExit = (Button) pieChartRoot.lookup("#btnExit");
			// 그래프 그리기
			pieChart.setData(
					FXCollections.observableArrayList(new PieChart.Data("출석", (selectChildren.get(0).getAttend())),
							new PieChart.Data("결석", (selectChildren.get(0).getAbsent())),
							new PieChart.Data("조퇴", (selectChildren.get(0).getEarly()))));

			Scene scene = new Scene(pieChartRoot);
			stage.setScene(scene);
			stage.setTitle(selectChildren.get(0).getName() + "원아의 월별 출석률");
			stage.setResizable(false);
			stage.show();
			btnExit.setOnAction((e2) -> {
				stage.close();
			});

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// 전체 원아 출석률 버튼을 눌렀을 때 이벤트
	private void handlerBtnBarChartAction(ActionEvent event) {
		Parent attendRoot;
		try {
			if (cbMonth.getSelectionModel().getSelectedIndex() == -1) {
				ShareMethod.alertDisplay(1, "미선택 오류!", "월이 선택되지 않았어요!", "다시 확인해 주세요 (;ㅅ;)");
				return;
			}
			attendRoot = FXMLLoader.load(getClass().getResource("/view/admin_attend_barchart_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnBarChart.getScene().getWindow());
			stage.setTitle("전체 원아의 월별 출석률");
			Button btnExit = (Button) attendRoot.lookup("#btnExit");
			StackedBarChart barChart = (StackedBarChart) attendRoot.lookup("#barChart");

			// 모든 원아의 월별 출석률을 바차트에 입력하기
			// 모든 출석 일수
			XYChart.Series seriesAttend = new XYChart.Series();
			seriesAttend.setName("출석");
			ObservableList attendList = FXCollections.observableArrayList();
			for (int i = 0; i < data.size(); i++) {
				attendList.add(new XYChart.Data(data.get(i).getName(), data.get(i).getAttend()));
			}

			seriesAttend.setData(attendList);
			barChart.getData().add(seriesAttend);

			// 모든 결석 일수
			XYChart.Series seriesAbsent = new XYChart.Series();
			seriesAbsent.setName("결석");
			ObservableList absentList = FXCollections.observableArrayList();
			for (int i = 0; i < data.size(); i++) {
				absentList.add(new XYChart.Data(data.get(i).getName(), data.get(i).getAbsent()));
			}
			seriesAbsent.setData(absentList);
			barChart.getData().add(seriesAbsent);

			// 모든 조퇴 일수
			XYChart.Series seriesEarly = new XYChart.Series();
			seriesEarly.setName("조퇴");
			ObservableList earlyList = FXCollections.observableArrayList();
			for (int i = 0; i < data.size(); i++) {
				earlyList.add(new XYChart.Data(data.get(i).getName(), data.get(i).getEarly()));
			}
			seriesEarly.setData(earlyList);
			barChart.getData().add(seriesEarly);

			Scene scene = new Scene(attendRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event1 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 콤보박스 셋팅하기
	public void comboBoxSetting() {
		ObservableList<String> listMonth = FXCollections.observableArrayList();
		ObservableList<String> listName = FXCollections.observableArrayList();
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

		cbMonth.setItems(listMonth);
		cbMonthEdit.setItems(listMonth);
		AdminDAO ad = new AdminDAO();
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