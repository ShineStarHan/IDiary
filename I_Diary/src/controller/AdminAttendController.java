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
	ObservableList<Attend> data = FXCollections.observableArrayList(); // ���̺�信 �����ֱ����ؼ� ����� ����Ÿ

	// ���̺� �並 ���������� ��ġ���� ��ü���� ������ �� �ִ� ���� ����
	private int selectedIndex;
	private ObservableList<Attend> selectChildren;

	//private String selectMonth;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		//������Ʈ
		btnPieChart.setOnAction(event -> {
			handlerBtnPieChartAction(event);
		});
		//����Ʈ
		btnBarChart.setOnAction(event -> {
			handlerBtnBarChartAction(event);
		});
		//�⼮ ���� �Է�
		btnAdd.setOnAction(event -> {
			handlerBtnAddAction(event);
		});
		//�⼮���� ����
		btnEdit.setOnAction(event -> {
			handlerBtnEditAction(event);
		});
		//�⼮���� ����
		btnRemove.setOnAction(event -> {
			handlerBtnRemoveAction(event);
		});
		//�ش��̸� �⼮���� �˻�
		btnSearch.setOnAction(event -> {
			handlerBtnSearchAction(event);
		});
		//�ش� �� �⼮���� �˻�
		btnView.setOnAction(event -> {
			handlerBtnViewAction(event);
		});
		//���̺�� �׸� Ŭ���� �̺�Ʈ
		tableView.setOnMousePressed((e) -> {
			handlerTableViewPressedAction(e);
		});
		//DB���� �⼮���� ��������
		totalList();
		//DB���� ������ ���� ���̺�信 �Է�
		tableViewSetting();
		//�޺��ڽ�����Ʈ
		comboBoxSetting();
	}

	// �޺��ڽ��� �����ϰ� ��ư�� Ŭ������ �� �̺�Ʈ
	public void handlerBtnViewAction(ActionEvent event) {
		AttendDAO ad = new AttendDAO();
		String month = cbMonth.getSelectionModel().getSelectedItem().toString();
		System.out.println("�վ� : "+month);
		ArrayList<Attend> al = ad.getSelectedList(month,ShareMethod.className);

		try {
			if (al == null) {
				throw new Exception("�˻�����!");
			}
			data.removeAll(data);

			for (Attend svo : al) {
				data.add(svo);
			}
		} catch (Exception e) {
			// �˸�â ����
			ShareMethod.alertDisplay(1, "�� �̼���!", "�� �̼���!", "���� ������ �ּ���. (;��;)");
		}

	}

	// �˻� ��ư�� ������ �� �̺�Ʈ
	private void handlerBtnSearchAction(ActionEvent event) {
		try {
			ArrayList<Attend> list = new ArrayList<Attend>();
			AttendDAO attendDAO = new AttendDAO();
			list = attendDAO.getChildrenCheck(txtSearch.getText());

			if (list == null) {
				throw new Exception("�˻�����!");
			}

			data.removeAll(data);
			for (Attend svo : list) {
				data.add(svo);
			}
		} catch (Exception e) {
			// �˸�â ����
			ShareMethod.alertDisplay(1, "�̸� �˻� ����!", "�̸��� ��Ȯ���� �ʽ��ϴ�!", "�̸��� ��Ȯ�� �Է��� �ּ��� (;��;)");
		}
	}

	// ���̺� �並 ������ �� �ؽ�Ʈ�ʵ忡 ���� ��������
	private void handlerTableViewPressedAction(MouseEvent e) {
		// �������� ��ġ�� �ش�� ��ü�� �����´�
		selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		selectChildren = tableView.getSelectionModel().getSelectedItems();

		cbMonthEdit.setValue(String.valueOf(selectChildren.get(0).getMonth()));
		cbName.setValue(selectChildren.get(0).getName());
		txtAttend.setText(String.valueOf(selectChildren.get(0).getAttend()));
		txtAbsent.setText(String.valueOf(selectChildren.get(0).getAbsent()));
		txtEarly.setText(String.valueOf(selectChildren.get(0).getEarly()));
	}

	// �����ͺ��̽� ���� �о ���̺�信 ��������
	public void totalList() {
		ArrayList<Attend> list = null;
		AttendDAO attendDAO = new AttendDAO();
		Attend attendVO = new Attend();
		list = attendDAO.getChildrenTotal(ShareMethod.className);

		if (list == null) {
			ShareMethod.alertDisplay(1, "���!", "DB �������� ����!", "�ٽ�  �� �� Ȯ���� �ּ��� (;��;)");
			return;
		}
		data.removeAll(data);
		for (int i = 0; i < list.size(); i++) {
			attendVO = list.get(i);
			data.add(attendVO);
		}
	}

	// �߰� ��ư�� ������ �� �̺�Ʈ
	private void handlerBtnAddAction(ActionEvent event) {
		try {
			Attend attendVO = new Attend(cbMonthEdit.getSelectionModel().getSelectedItem(),
					cbName.getSelectionModel().getSelectedItem(), Integer.parseInt(txtAttend.getText().trim()),
					Integer.parseInt(txtAbsent.getText().trim()), Integer.parseInt(txtEarly.getText().trim()));
			ArrayList<Attend> list = new ArrayList<Attend>();

			// data.remove(selectedIndex);
			data.add(selectedIndex, attendVO);

			// DB�� �θ��� ���
			AttendDAO attendDAO = new AttendDAO();
			// �����ͺ��̽� ���̺� �Է°��� �Է��ϴ� �Լ�
			list = attendDAO.SelectedList(cbMonthEdit.getSelectionModel().getSelectedItem(),
					cbName.getSelectionModel().getSelectedItem());
			if (list.size() == 0) {

				int count = attendDAO.getChildrenregiste(attendVO, ShareMethod.className);
				if (count != 0) {
					data.removeAll(data);
					totalList();
				} else {

					throw new Exception("�����ͺ��̽� ��� ���� (;��;)");
				}
				// �˸�â ����
				ShareMethod.alertDisplay(1, "�߰�����!", "���̺� ��� ���� (*'��'*)", "��ī��ī~��");
			} else {
				
				totalList();
				throw new Exception("�����ͺ��̽� ��� ���� (;��;)");
			}
		} catch (Exception e) {
			// �˸�â ����
			ShareMethod.alertDisplay(1, "�߰�����!", "�ٽ� �Է��� �ּ��� (;��;)", e.toString());
			e.printStackTrace();
			return;
		}
	}

	// ���� ��ư�� ������ �� �̺�Ʈ
	private void handlerBtnRemoveAction(ActionEvent event) {
		AttendDAO attendDAO = new AttendDAO();
		String num = tableView.getSelectionModel().getSelectedItem().getMonth();
		String kid = tableView.getSelectionModel().getSelectedItem().getName();
		ArrayList<Attend> ar = attendDAO.getChildrenTotal(ShareMethod.className);

		// ���� ���â �˾�
		Parent rabbitRoot;
		try {
			rabbitRoot = FXMLLoader.load(getClass().getResource("/view/alert_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("���� ���!");
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

	// ���� ��ư�� ������ �� �̺�Ʈ
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
				throw new Exception("������� ����!");
			}
			ShareMethod.alertDisplay(1, "��������!", "������� ����!", "���̺��� �����Ǿ����. (*'��'*)");
		} catch (Exception e) {
			// �˸�â ����
			ShareMethod.alertDisplay(1, "��������!", "������� ����!", "�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
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

	// ���� �⼮�� ��ư�� ������ �� �̺�Ʈ
	private void handlerBtnPieChartAction(ActionEvent event) {
		Parent pieChartRoot;
		try {
			if (cbMonthEdit.getSelectionModel().getSelectedIndex() == -1) {
				ShareMethod.alertDisplay(1, "�̼��� ����!", "���� ���õ��� �ʾҾ��!", "�ٽ� Ȯ���� �ּ��� (;��;)");
			} else if (tableView.getSelectionModel().getSelectedItem() == null) {
				ShareMethod.alertDisplay(1, "�̼��� ����!", "����� ���õ��� �ʾҾ��!", "�ٽ� Ȯ���� �ּ��� (;��;)");
				return;
			}
			// �������� ��ġ�� �ش�� ��ü�� �����´�
			selectedIndex = tableView.getSelectionModel().getSelectedIndex();
			selectChildren = tableView.getSelectionModel().getSelectedItems();

			pieChartRoot = FXMLLoader.load(getClass().getResource("/view/admin_attend_piechart_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnPieChart.getScene().getWindow());
			PieChart pieChart = (PieChart) pieChartRoot.lookup("#pieChart");
			Button btnExit = (Button) pieChartRoot.lookup("#btnExit");
			// �׷��� �׸���
			pieChart.setData(
					FXCollections.observableArrayList(new PieChart.Data("�⼮", (selectChildren.get(0).getAttend())),
							new PieChart.Data("�Ἦ", (selectChildren.get(0).getAbsent())),
							new PieChart.Data("����", (selectChildren.get(0).getEarly()))));

			Scene scene = new Scene(pieChartRoot);
			stage.setScene(scene);
			stage.setTitle(selectChildren.get(0).getName() + "������ ���� �⼮��");
			stage.setResizable(false);
			stage.show();
			btnExit.setOnAction((e2) -> {
				stage.close();
			});

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// ��ü ���� �⼮�� ��ư�� ������ �� �̺�Ʈ
	private void handlerBtnBarChartAction(ActionEvent event) {
		Parent attendRoot;
		try {
			if (cbMonth.getSelectionModel().getSelectedIndex() == -1) {
				ShareMethod.alertDisplay(1, "�̼��� ����!", "���� ���õ��� �ʾҾ��!", "�ٽ� Ȯ���� �ּ��� (;��;)");
				return;
			}
			attendRoot = FXMLLoader.load(getClass().getResource("/view/admin_attend_barchart_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnBarChart.getScene().getWindow());
			stage.setTitle("��ü ������ ���� �⼮��");
			Button btnExit = (Button) attendRoot.lookup("#btnExit");
			StackedBarChart barChart = (StackedBarChart) attendRoot.lookup("#barChart");

			// ��� ������ ���� �⼮���� ����Ʈ�� �Է��ϱ�
			// ��� �⼮ �ϼ�
			XYChart.Series seriesAttend = new XYChart.Series();
			seriesAttend.setName("�⼮");
			ObservableList attendList = FXCollections.observableArrayList();
			for (int i = 0; i < data.size(); i++) {
				attendList.add(new XYChart.Data(data.get(i).getName(), data.get(i).getAttend()));
			}

			seriesAttend.setData(attendList);
			barChart.getData().add(seriesAttend);

			// ��� �Ἦ �ϼ�
			XYChart.Series seriesAbsent = new XYChart.Series();
			seriesAbsent.setName("�Ἦ");
			ObservableList absentList = FXCollections.observableArrayList();
			for (int i = 0; i < data.size(); i++) {
				absentList.add(new XYChart.Data(data.get(i).getName(), data.get(i).getAbsent()));
			}
			seriesAbsent.setData(absentList);
			barChart.getData().add(seriesAbsent);

			// ��� ���� �ϼ�
			XYChart.Series seriesEarly = new XYChart.Series();
			seriesEarly.setName("����");
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

	// �޺��ڽ� �����ϱ�
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