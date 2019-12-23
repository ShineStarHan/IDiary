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

public class AdminMedicineController implements Initializable {
	@FXML
	private Button btnMain;
	@FXML
	private TableView<Medicine> tableView;
	ObservableList<Medicine> data;
	
	// ���̺� �並 ���������� ��ġ���� ��ü���� ������ �� �ִ� ���� ����
	private int selectedIndex;
	private ObservableList<Medicine> selectMedicine;
	
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
		try {
			if (event.getClickCount() != 2) {
				return;
			}
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_medicine_popup.fxml"));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �����ͺ��̽� ���� �о ���̺�信 ��������
	public void totalList() {
		ObservableList<Medicine> list = null;
		MedicineDAO medicineDAO = new MedicineDAO();
		Medicine medicineVO = new Medicine();
		list = medicineDAO.getMedicineTotal(ShareMethod.className);

		if (list == null) {
			ShareMethod.alertDisplay(1, "���!", "DB �������� ����!", "�ٽ� Ȯ���� �ּ��� (;��;)");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			medicineVO = list.get(i);
			data.add(medicineVO);
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

		TableColumn colName = tableView.getColumns().get(1);
		colName.setCellValueFactory(new PropertyValueFactory("receive"));

		TableColumn colMedicine = tableView.getColumns().get(2);
		colMedicine.setCellValueFactory(new PropertyValueFactory("ref"));

		TableColumn colDate = tableView.getColumns().get(3);
		colDate.setCellValueFactory(new PropertyValueFactory("date"));

		tableView.setItems(data);
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
