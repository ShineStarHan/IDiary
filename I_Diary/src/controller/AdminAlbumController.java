package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Album;

public class AdminAlbumController implements Initializable {

	@FXML
	private Button btnMain;
	@FXML
	private Button btnEdit;
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

	//private String selectFileName = ""; // 이미지 파일명
	private String localUrl = ""; // 이미지 파일 경로
	private Image localImage;
	private File selectedFile = null;

	// 이미지 뷰를 선택했을때 객체값을 저장할 수 있는 변수 선언
	private Image selectAlbum;
	ObservableList<Album> data = FXCollections.observableArrayList(); // 테이블뷰에 보여주기위해서 저장된 데이타

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//메인 메뉴로 돌아감
		btnMain.setOnAction(event -> {
			handlerBtnMainAction(event);
		});
		
		imageView1.setOnMousePressed(event -> {
			handlerImageView1Action(event);
		});
		imageView2.setOnMousePressed(event -> {
			handlerImageView2Action(event);
		});
		imageView3.setOnMousePressed(event -> {
			handlerImageView3Action(event);
		});
		imageView4.setOnMousePressed(event -> {
			handlerImageView4Action(event);
		});
		imageView5.setOnMousePressed(event -> {
			handlerImageView5Action(event);
		});
		imageView6.setOnMousePressed(event -> {
			handlerImageView6Action(event);
		});
		imageView7.setOnMousePressed(event -> {
			handlerImageView7Action(event);
		});
		imageView8.setOnMousePressed(event -> {
			handlerImageView8Action(event);
		});
		imageView9.setOnMousePressed(event -> {
			handlerImageView9Action(event);
		});
		//이미지 더블클릭시 보기
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
		//이미지 저장된 DB 긁어오기
		totalList();
		//이미지뷰 저장된 사진 세팅
		imageViewSetting();
	}

	public void imageViewSetting() {
		ObservableList<ImageView> imageList = FXCollections.observableArrayList();
		imageList.add(imageView1);
		imageList.add(imageView2);
		imageList.add(imageView3);
		imageList.add(imageView4);
		imageList.add(imageView5);
		imageList.add(imageView6);
		imageList.add(imageView7);
		imageList.add(imageView8);
		imageList.add(imageView9);

		for (int i = 0; i < data.size(); i++) {
			localUrl = data.get(i).getImage();
			System.out.println(localUrl);
			if (data.get(i).getImage() == null) {
				localImage = new Image("file:///C:/Users/소영/Desktop/I_Diary%20(2)/src/image/default_profile.png", false);
				imageList.get(i).setImage(localImage);
			} else {
				localImage = new Image(localUrl, false);
				imageList.get(i).setImage(localImage);
			}
		}
	}

	// 데이터베이스 값을 읽어서 테이블뷰에 가져오기
	public void totalList() {
		try {
			ObservableList<Album> list = FXCollections.observableArrayList();
			AlbumDAO albumDAO = new AlbumDAO();
			Album albumVO = new Album();
			list = albumDAO.getAlbumTotal();
			System.out.println(list.get(0).getImage());
			if (list == null) {
				ShareMethod.alertDisplay(1, "경고!", "DB 가져오기 오류!", "다시 확인해 주세요 (;ㅅ;)");
				return;
			} else {
				data.removeAll(data);//리스트 초기화
				for (int i = 0; i < list.size()-1; i++) {
					albumVO = list.get(i);
					data.add(albumVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(localUrl = data.get(0).getImage());
	}

	// 더블클릭 하면 사진이 보여지는 이벤트
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

	// 사진 수정 이벤트
	private void handlerImageView9Action(MouseEvent event) {
		btnEdit.setOnAction(event2 -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
			try {
				selectedFile = fileChooser.showOpenDialog(btnEdit.getScene().getWindow());
				if (selectedFile != null) {
					// 이미지 파일 경로
					localUrl = selectedFile.toURI().toURL().toString();
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			try {

				localImage = new Image(localUrl, false);
				imageView9.setImage(localImage);
				imageView9.setFitHeight(200);
				imageView9.setFitWidth(200);

				Album album = new Album(localUrl);

				AlbumDAO albumDAO = new AlbumDAO();
				Album albumVO = albumDAO.getAlbumUpdate(album, 9);
				data.remove(album);
				data.add(album);
				totalList();

			} catch (Exception e) {
				ShareMethod.alertDisplay(5, "수정취소!", "앨범수정 취소!", "수정이 취소되었습니다. (*'ㅅ'*)");
			}
		});

	}

	private void handlerImageView8Action(MouseEvent event) {
		btnEdit.setOnAction(event2 -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
			try {
				selectedFile = fileChooser.showOpenDialog(btnEdit.getScene().getWindow());
				if (selectedFile != null) {
					// 이미지 파일 경로
					localUrl = selectedFile.toURI().toURL().toString();
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			try {
		       	 System.out.println(selectedFile.toURI().toURL().toString());
				localImage = new Image(localUrl, false);
				imageView8.setImage(localImage);
				imageView8.setFitHeight(200);
				imageView8.setFitWidth(200);

				Album album = new Album(localUrl);

				AlbumDAO albumDAO = new AlbumDAO();
				Album albumVO = albumDAO.getAlbumUpdate(album, 8);
				data.remove(album);
				data.add(album);
				totalList();

			} catch (Exception e) {
				ShareMethod.alertDisplay(5, "수정취소!", "앨범수정 취소!", "수정이 취소되었습니다. (*'ㅅ'*)");
			}

		});

	}

	private void handlerImageView7Action(MouseEvent event) {
		btnEdit.setOnAction(event2 -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
			try {
				selectedFile = fileChooser.showOpenDialog(btnEdit.getScene().getWindow());
				if (selectedFile != null) {
					// 이미지 파일 경로
					localUrl = selectedFile.toURI().toURL().toString();
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			try {
		       	 System.out.println(selectedFile.toURI().toURL().toString());
				localImage = new Image(localUrl, false);
				imageView7.setImage(localImage);
				imageView7.setFitHeight(200);
				imageView7.setFitWidth(200);

				Album album = new Album(localUrl);

				AlbumDAO albumDAO = new AlbumDAO();
				Album albumVO = albumDAO.getAlbumUpdate(album, 7);
				data.remove(album);
				data.add(album);
				totalList();

			} catch (Exception e) {
				ShareMethod.alertDisplay(5, "수정취소!", "앨범수정 취소!", "수정이 취소되었습니다. (*'ㅅ'*)");
			}
		});

	}

	private void handlerImageView6Action(MouseEvent event) {
		btnEdit.setOnAction(event2 -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
			try {
				selectedFile = fileChooser.showOpenDialog(btnEdit.getScene().getWindow());
				if (selectedFile != null) {
					// 이미지 파일 경로
					localUrl = selectedFile.toURI().toURL().toString();
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			try {
		       	 System.out.println(selectedFile.toURI().toURL().toString());
				localImage = new Image(localUrl, false);
				imageView6.setImage(localImage);
				imageView6.setFitHeight(200);
				imageView6.setFitWidth(200);

				Album album = new Album(localUrl);

				AlbumDAO albumDAO = new AlbumDAO();
				Album albumVO = albumDAO.getAlbumUpdate(album, 6);
				data.remove(album);
				data.add(album);
				totalList();

			} catch (Exception e) {
				ShareMethod.alertDisplay(5, "수정취소!", "앨범수정 취소!", "수정이 취소되었습니다. (*'ㅅ'*)");
			}
		});

	}

	private void handlerImageView5Action(MouseEvent event) {
		btnEdit.setOnAction(event2 -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
			try {
				selectedFile = fileChooser.showOpenDialog(btnEdit.getScene().getWindow());
				if (selectedFile != null) {
					// 이미지 파일 경로
					localUrl = selectedFile.toURI().toURL().toString();
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			try {
		       	 System.out.println(selectedFile.toURI().toURL().toString());
				localImage = new Image(localUrl, false);
				imageView5.setImage(localImage);
				imageView5.setFitHeight(200);
				imageView5.setFitWidth(200);

				Album album = new Album(localUrl);

				AlbumDAO albumDAO = new AlbumDAO();
				Album albumVO = albumDAO.getAlbumUpdate(album, 5);
				data.remove(album);
				data.add(album);
				totalList();

			} catch (Exception e) {
				ShareMethod.alertDisplay(5, "수정취소!", "앨범수정 취소!", "수정이 취소되었습니다. (*'ㅅ'*)");
			}
		});

	}

	private void handlerImageView4Action(MouseEvent event) {
		btnEdit.setOnAction(event2 -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
			try {
				selectedFile = fileChooser.showOpenDialog(btnEdit.getScene().getWindow());
				if (selectedFile != null) {
					// 이미지 파일 경로
					localUrl = selectedFile.toURI().toURL().toString();
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			try {
		       	 System.out.println(selectedFile.toURI().toURL().toString());
				localImage = new Image(localUrl, false);
				imageView4.setImage(localImage);
				imageView4.setFitHeight(200);
				imageView4.setFitWidth(200);

				Album album = new Album(localUrl);

				AlbumDAO albumDAO = new AlbumDAO();
				Album albumVO = albumDAO.getAlbumUpdate(album, 4);
				data.remove(album);
				data.add(album);
				totalList();

			} catch (Exception e) {
				ShareMethod.alertDisplay(5, "수정취소!", "앨범수정 취소!", "수정이 취소되었습니다. (*'ㅅ'*)");
			}
		});
	}

	private void handlerImageView3Action(MouseEvent event) {
		btnEdit.setOnAction(event2 -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
			try {
				selectedFile = fileChooser.showOpenDialog(btnEdit.getScene().getWindow());
				if (selectedFile != null) {
					// 이미지 파일 경로
					localUrl = selectedFile.toURI().toURL().toString();
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			try {
		       	 System.out.println(selectedFile.toURI().toURL().toString());
				localImage = new Image(localUrl, false);
				imageView3.setImage(localImage);
				imageView3.setFitHeight(200);
				imageView3.setFitWidth(200);

				Album album = new Album(localUrl);

				AlbumDAO albumDAO = new AlbumDAO();
				Album albumVO = albumDAO.getAlbumUpdate(album, 3);
				data.remove(album);
				data.add(album);
				totalList();

			} catch (Exception e) {
				ShareMethod.alertDisplay(5, "수정취소!", "앨범수정 취소!", "수정이 취소되었습니다. (*'ㅅ'*)");
			}
		});
	}

	private void handlerImageView2Action(MouseEvent event) {
		btnEdit.setOnAction(event2 -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
			try {
				selectedFile = fileChooser.showOpenDialog(btnEdit.getScene().getWindow());
				if (selectedFile != null) {
					// 이미지 파일 경로
					localUrl = selectedFile.toURI().toURL().toString();
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			try {
		       	 System.out.println(selectedFile.toURI().toURL().toString());
				localImage = new Image(localUrl, false);
				imageView2.setImage(localImage);
				imageView2.setFitHeight(200);
				imageView2.setFitWidth(200);

				Album album = new Album(localUrl);

				AlbumDAO albumDAO = new AlbumDAO();
				Album albumVO = albumDAO.getAlbumUpdate(album, 2);
				data.remove(album);
				data.add(album);
				totalList();

			} catch (Exception e) {
				ShareMethod.alertDisplay(5, "수정취소!", "앨범수정 취소!", "수정이 취소되었습니다. (*'ㅅ'*)");
			}
		});

	}

	private void handlerImageView1Action(MouseEvent event) {
		btnEdit.setOnAction(event2 -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
			try {
				selectedFile = fileChooser.showOpenDialog(btnEdit.getScene().getWindow());
				if (selectedFile != null) {
					// 이미지 파일 경로
					localUrl = selectedFile.toURI().toURL().toString();
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			try {
		       	 System.out.println(selectedFile.toURI().toURL().toString());
				localImage = new Image(localUrl, false);
				imageView1.setImage(localImage);
				imageView1.setFitHeight(200);
				imageView1.setFitWidth(200);

				Album album = new Album(localUrl);

				AlbumDAO albumDAO = new AlbumDAO();
				Album albumVO = albumDAO.getAlbumUpdate(album, 1);
				data.remove(album);
				data.add(album);
				totalList();

			} catch (Exception e) {
				ShareMethod.alertDisplay(5, "수정취소!", "앨범수정 취소!", "수정이 취소되었습니다. (*'ㅅ'*)");
			}
		});
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
			e.printStackTrace();
		}
	}

}
