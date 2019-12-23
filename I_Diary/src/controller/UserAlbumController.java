package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Album;

public class UserAlbumController implements Initializable {
	@FXML
	private Button btnMain;
	@FXML
	private ImageView imageView1;
	@FXML
	private ImageView imageView2;
	@FXML
	private ImageView imageView3;
	@FXML
	private ImageView imageView4;
	@FXML
	private ImageView imageView5;
	@FXML
	private ImageView imageView6;
	@FXML
	private ImageView imageView7;
	@FXML
	private ImageView imageView8;
	@FXML
	private ImageView imageView9;
	
	// 이미지 뷰를 선택했을때 객체값을 저장할 수 있는 변수 선언
	private Image selectAlbum;
	private Image localImage;
	private String localUrl="";
	ObservableList<Album> data=FXCollections.observableArrayList();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnMain.setOnAction(event-> {
			handlerBtnMainAction(event);
		});
		imageView1.setOnMouseClicked(event -> {
	         handlerDoubleClick(event);
	    });
		imageView2.setOnMouseClicked(event -> {
	         handlerDoubleClick2(event);
	      });
		imageView3.setOnMouseClicked(event -> {
	         handlerDoubleClick3(event);
	      });
		imageView4.setOnMouseClicked(event -> {
	         handlerDoubleClick4(event);
	    });
		imageView5.setOnMouseClicked(event -> {
	         handlerDoubleClick5(event);
	      });
		imageView6.setOnMouseClicked(event -> {
	         handlerDoubleClick6(event);
	      });
		imageView7.setOnMouseClicked(event -> {
	         handlerDoubleClick7(event);
	      });
		imageView8.setOnMouseClicked(event -> {
	         handlerDoubleClick8(event);
	      });
		imageView9.setOnMouseClicked(event -> {
	         handlerDoubleClick9(event);
	      });
		totalList();
		imageViewSetting();
	}

	
	
	private void handlerDoubleClick9(MouseEvent event) {
		Parent viewRoot;
		try {
		      if (event.getClickCount() != 2) {
		            return;
		         }
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_album_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("사진보기");
			ImageView imageView = (ImageView) viewRoot.lookup("#imageView");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			selectAlbum = imageView9.getImage();
			imageView.setImage(selectAlbum);
			imageView.setStyle("-fx-alignment: CENTER;");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event3 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}



	private void handlerDoubleClick8(MouseEvent event) {
		Parent viewRoot;
		try {
		      if (event.getClickCount() != 2) {
		            return;
		         }
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_album_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("사진보기");
			ImageView imageView = (ImageView) viewRoot.lookup("#imageView");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			selectAlbum = imageView8.getImage();
			imageView.setImage(selectAlbum);
			imageView.setStyle("-fx-alignment: CENTER;");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event3 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



	private void handlerDoubleClick7(MouseEvent event) {
		Parent viewRoot;
		try {
		      if (event.getClickCount() != 2) {
		            return;
		         }
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_album_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("사진보기");
			ImageView imageView = (ImageView) viewRoot.lookup("#imageView");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			selectAlbum = imageView7.getImage();
			imageView.setImage(selectAlbum);
			imageView.setStyle("-fx-alignment: CENTER;");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event3 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}



	private void handlerDoubleClick6(MouseEvent event) {
		Parent viewRoot;
		try {
		      if (event.getClickCount() != 2) {
		            return;
		         }
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_album_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("사진보기");
			ImageView imageView = (ImageView) viewRoot.lookup("#imageView");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			selectAlbum = imageView6.getImage();
			imageView.setImage(selectAlbum);
			imageView.setStyle("-fx-alignment: CENTER;");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event3 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



	private void handlerDoubleClick5(MouseEvent event) {
		Parent viewRoot;
		try {
		      if (event.getClickCount() != 2) {
		            return;
		         }
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_album_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("사진보기");
			ImageView imageView = (ImageView) viewRoot.lookup("#imageView");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			selectAlbum = imageView5.getImage();
			imageView.setImage(selectAlbum);
			imageView.setStyle("-fx-alignment: CENTER;");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event3 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



	private void handlerDoubleClick4(MouseEvent event) {
		Parent viewRoot;
		try {
		      if (event.getClickCount() != 2) {
		            return;
		         }
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_album_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("사진보기");
			ImageView imageView = (ImageView) viewRoot.lookup("#imageView");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			selectAlbum = imageView4.getImage();
			imageView.setImage(selectAlbum);
			imageView.setStyle("-fx-alignment: CENTER;");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event3 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}



	private void handlerDoubleClick3(MouseEvent event) {
		Parent viewRoot;
		try {
		      if (event.getClickCount() != 2) {
		            return;
		         }
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_album_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("사진보기");
			ImageView imageView = (ImageView) viewRoot.lookup("#imageView");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			selectAlbum = imageView3.getImage();
			imageView.setImage(selectAlbum);
			imageView.setStyle("-fx-alignment: CENTER;");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event3 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



	private void handlerDoubleClick2(MouseEvent event) {
		Parent viewRoot;
		try {
		      if (event.getClickCount() != 2) {
		            return;
		         }
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_album_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("사진보기");
			ImageView imageView = (ImageView) viewRoot.lookup("#imageView");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			selectAlbum = imageView2.getImage();
			imageView.setImage(selectAlbum);
			imageView.setStyle("-fx-alignment: CENTER;");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event3 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



	private void handlerDoubleClick(MouseEvent event) {
		Parent viewRoot;
		try {
		      if (event.getClickCount() != 2) {
		            return;
		         }
			viewRoot = FXMLLoader.load(getClass().getResource("/view/admin_album_popup.fxml"));
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(btnMain.getScene().getWindow());
			stage.setTitle("사진보기");
			ImageView imageView = (ImageView) viewRoot.lookup("#imageView");
			Button btnExit = (Button) viewRoot.lookup("#btnExit");
			selectAlbum = imageView1.getImage();
			imageView.setImage(selectAlbum);
			imageView.setStyle("-fx-alignment: CENTER;");
			Scene scene = new Scene(viewRoot);
			stage.setScene(scene);
			stage.show();
			btnExit.setOnAction(event3 -> {
				((Stage) btnExit.getScene().getWindow()).close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void imageViewSetting() {
		ObservableList<ImageView> imageList=FXCollections.observableArrayList();
		imageList.add(imageView1);
		imageList.add(imageView2);
		imageList.add(imageView3);
		imageList.add(imageView4);
		imageList.add(imageView5);
		imageList.add(imageView6);
		imageList.add(imageView7);
		imageList.add(imageView8);
		imageList.add(imageView9);
		
		for(int i=0;i<data.size();i++) {
	         localUrl=data.get(i).getImage();
	         System.out.println(localUrl+"d오류");
	         if(data.get(i).getImage()==null) {
				localImage=new Image("C:\\Users\\소영\\Desktop\\I_Diary (2)\\src\\image\\default_profile.png",false);
				imageList.get(i).setImage(localImage);
			}else {
				localImage=new Image(localUrl, false);
				imageList.get(i).setImage(localImage);
			}
			
		}
		
	}

	// 데이터베이스 값을 읽어서 테이블뷰에 가져오기
	public void totalList() {
		try {
		ObservableList<Album> list =FXCollections.observableArrayList();
		AlbumDAO albumDAO = new AlbumDAO();
		Album albumVO = new Album();
		list = albumDAO.getAlbumTotal();
		System.out.println(list.get(0).getImage());
		if (list == null) {
			ShareMethod.alertDisplay(1, "경고!", "DB 가져오기 오류!", "다시 한 번 확인해 주세요 (;ㅅ;)");
			return;
		} else {
			data.removeAll(data);
			for (int i = 0; i < list.size()-1; i++) {
				albumVO = list.get(i);
				data.add(albumVO);
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(localUrl=data.get(0).getImage());
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
