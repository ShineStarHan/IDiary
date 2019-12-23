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
import model.Medicine;

public class UserMedicineController implements Initializable {
	@FXML
	private Button btnMain;
	@FXML
	private ComboBox<String> cbName;
	@FXML
	private TextField txtTime;
	@FXML
	private TextField txtCapacity;
	@FXML
	private ComboBox<String> cbKeep;
	@FXML
	private TextArea txtRef;
	@FXML
	private Button btnSend;

	@FXML
	private TableView<Medicine> tableView;
	ObservableList<Medicine> data=FXCollections.observableArrayList();

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
		comboBoxSetting();
		totalList();
		tableViewSetting();
	}

	public void handlerDoubleClick(MouseEvent event) {
		Parent viewRoot;
		try {
			if (event.getClickCount() != 2) {
				return;
			}
			viewRoot = FXMLLoader.load(getClass().getResource("/view/user_medicine_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("�����Ƿڼ� ���뺸��");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			TextField txtName = (TextField) viewRoot.lookup("#txtName");
			TextField txtTime = (TextField) viewRoot.lookup("#txtTime");
			TextField txtCapacity = (TextField) viewRoot.lookup("#txtCapacity");
			TextField txtKeep = (TextField) viewRoot.lookup("#txtKeep");
			TextArea txtAreaRef = (TextArea) viewRoot.lookup("#txtAreaRef");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			int num=tableView.getSelectionModel().getSelectedItem().getNo();
			MedicineDAO md=new MedicineDAO();
			ObservableList<Medicine> view=FXCollections.observableArrayList();
			view=md.getMedicineView(num);
			txtName.setText(view.get(0).getSend());
			txtTime.setText(view.get(0).getTime());
			txtCapacity.setText(view.get(0).getCapacity());
			txtKeep.setText(view.get(0).getKeep());
			txtAreaRef.setText(view.get(0).getRef());
			btnExit.setOnAction(event2 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void totalList() {
		ObservableList<Medicine> list = null;
		MedicineDAO medicineDAO = new MedicineDAO();
		Medicine medicineVO = new Medicine();
		list = medicineDAO.getMedicineTotal(ShareMethod.id);

		if (list == null) {
			ShareMethod.alertDisplay(1, "���!", "DB �������� ����!", "�ٽ� �� �� Ȯ���� �ּ��� (;��;)");
			return;
		}
		data.remove(data);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("����ý"+i);
			medicineVO = list.get(i);
			data.add(medicineVO);
		}
	}

	// �ۼ��ϱ� ��ư�� ������ �� �̺�Ʈ
	private void handlerBtnSendAction(ActionEvent event) {
		MedicineDAO md = new MedicineDAO();
		Medicine medi = null;
		try {
			
			md.getMedicinRegist(
					new Medicine(ShareMethod.id, cbName.getSelectionModel().getSelectedItem(), txtTime.getText(),
							txtCapacity.getText(), cbKeep.getSelectionModel().getSelectedItem(), txtRef.getText()));

			if(cbName.getSelectionModel().getSelectedItem()==null|| txtTime.getText().equals("")||
					txtCapacity.getText().equals("")||cbKeep.getSelectionModel().getSelectedItem()==null||txtRef.getText().equals("")) {
				
				throw new Exception();
				
			}else {
			ShareMethod.alertDisplay(5, "���� �Ϸ�!", "���� ����!", "�����Ƿڼ��� ���۵Ǿ����. (*'��'*)");
			}
		} catch (Exception e) {
			ShareMethod.alertDisplay(1, "���� ����!", "���� ����!", "�ٽ� �� �� Ȯ���� �ּ���. (;��;)");
			e.printStackTrace();
		}
		//data.remove(data);
		totalList();
	}

	// ���̺�� �⺻ �����ϱ�
	private void tableViewSetting() {
		// ���̺� ����.ArrayList();
		// ���̺� ����.���̺�並 �������� ���ϰ� ����
		tableView.setEditable(false);

		TableColumn colNo = tableView.getColumns().get(0);
		colNo.setCellValueFactory(new PropertyValueFactory("no"));

		TableColumn colName = tableView.getColumns().get(1);
		colName.setCellValueFactory(new PropertyValueFactory("receive"));

		TableColumn colMedicine = tableView.getColumns().get(2);
		colMedicine.setCellValueFactory(new PropertyValueFactory("ref"));

		TableColumn colDate = tableView.getColumns().get(3);
		colDate.setCellValueFactory(new PropertyValueFactory("date"));

		tableView.setItems(data);
	}

	// �޺��ڽ� �����ϱ�
	public void comboBoxSetting() {
		ObservableList<String> listName = FXCollections.observableArrayList();
		ObservableList<String> listKeep = FXCollections.observableArrayList();
		listName.add("�̽��� (5��)");
		listName.add("Ǯ�ٹ� (6��)");
		listName.add("���ٹ� (7��)");
		listKeep.add("���庸��");
		listKeep.add("�ǿº���");
		cbName.setItems(listName);
		cbKeep.setItems(listKeep);
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
