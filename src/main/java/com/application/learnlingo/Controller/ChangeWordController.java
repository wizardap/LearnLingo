package com.application.learnlingo.Controller;

import com.application.learnlingo.Model.Word;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChangeWordController extends GeneralController {

    private static final StringBuilder DEFAULT_HTML_CONTENT_FILEPATH =
            new StringBuilder("src/main/resources/com/application/learnlingo/database/defaultHTMLEditor.txt");
    private static String defaultHTMLContent;
    private static boolean showFoundWord;
    @FXML
    private Button resetButton;
    @FXML
    private Button searchWordButton;
    private boolean isLookedUp;
    @FXML
    private HTMLEditor contentHTMLEditor;
    @FXML
    private Button deleteContentButton;
    @FXML
    private TextField textfield1;
    @FXML
    private CheckBox d1;
    @FXML
    private CheckBox d2;
    @FXML
    private JFXButton addWordButton;
    @FXML
    private JFXButton deleteWordButton;
    private Word currentSearchWord;
    @FXML
    private Label dictionary;

    @FXML
    private Label labelWord;

    private static void loadDefaultHTMLContent() {
        FileReader fr = null;
        try {
            fr = new FileReader(DEFAULT_HTML_CONTENT_FILEPATH.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fr != null;
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            defaultHTMLContent = line;
        }

    }

    @Override
    public void changeMode() {
        if (changeL) {
            currentDictionary = veDict;
            changeDictionary.getChildren().removeAll(british, vn, changeModeButton);
            changeDictionary.getChildren().addAll(vn, changeModeButton, british);
            dictionaryButton.setText("Từ điển");
            translationButton.setText("Dịch câu");
            synonymButton.setText("Đồng nghĩa");
            antonymButton.setText("Trái nghĩa");
            searchButton.setText("TRANG CHỦ");
            changeDictionaryButton.setText("THÊM/XÓA");
            gameButton.setText("TRÒ CHƠI");
            bookmarkButton.setText("TỪ ĐÃ LƯU");
            settingsButton.setText("CÀI ĐẶT");
            deleteWordButton.setText("Xóa từ");
            addWordButton.setText("Thêm từ");
            dictionary.setText("Từ điển");
            labelWord.setText("Từ");
        } else {
            currentDictionary = evDict;
            changeDictionary.getChildren().removeAll(vn, british, changeModeButton);
            changeDictionary.getChildren().addAll(british, changeModeButton, vn);
            dictionaryButton.setText("Dictionary");
            translationButton.setText("Translation");
            synonymButton.setText("Synonyms");
            antonymButton.setText("Antonyms");
            searchButton.setText("HOME");
            changeDictionaryButton.setText("ADD/DELETE");
            gameButton.setText("GAME");
            bookmarkButton.setText("BOOKMARK");
            settingsButton.setText("SETTINGS");
            deleteWordButton.setText("Delete word");
            addWordButton.setText("Add word");
            dictionary.setText("Dictionary");
            labelWord.setText("Word");
        }
        changeL = !changeL;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + "bg3.jpg")).toString(), 910, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        center.setBackground(background);
        if (!changeL) {
            d2.setSelected(true);
            deleteWordButton.setText("Xóa từ");
            addWordButton.setText("Thêm từ");
            dictionary.setText("Từ điển");
            labelWord.setText("Từ");
        } else {
            d1.setSelected(true);
            deleteWordButton.setText("Delete word");
            addWordButton.setText("Add word");
            dictionary.setText("Dictionary");
            labelWord.setText("Word");
        }


        d1.setOnAction(event -> {
            d2.setSelected(!d1.isSelected());
            changeMode();
            isLookedUp = false;
        });

        d2.setOnAction(event -> {
            d1.setSelected(!d2.isSelected());
            changeMode();
            isLookedUp = false;
        });
        textfield1.setOnKeyTyped(e -> {
            isLookedUp = false;
        });
        loadDefaultHTMLContent();
        contentHTMLEditor.setHtmlText(defaultHTMLContent);
        textfield1.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                searchWordExist(new ActionEvent());
            }
        });

    }

    @FXML
    public void deleteContentSearch() {
        textfield1.setText("");
        isLookedUp = false;
    }

    @FXML
    public void addWordToDatabase() {
        Alert alert = new Alert(Alert.AlertType.WARNING, (changeL ? "Warning" : "Cảnh báo"), ButtonType.YES, ButtonType.NO);
        if (changeL) {
            alert.setContentText("Are you sure that you want to add this word to this dictionary?");
        } else {
            alert.setContentText("Bạn có chắc chắn muốn thêm từ này vào từ điển?");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.YES) {
                String contentSearchWord = textfield1.getText().toLowerCase();
                if (!contentSearchWord.isEmpty() && !currentDictionary.contain(contentSearchWord)) {
                    Word modifiedWord = new Word(contentSearchWord, contentHTMLEditor.getHtmlText(), false);
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    if (changeL) {
                        success.setHeaderText("'" +
                                contentSearchWord +
                                "'" +
                                " has been added to this dictionary!");
                    } else {
                        success.setHeaderText("'" +
                                contentSearchWord +
                                "'" +
                                " đã được thêm vào từ điển!");
                    }
                    success.showAndWait();
                    currentDictionary.insertWord(modifiedWord);
                } else {
                    Alert failed = new Alert(Alert.AlertType.ERROR);
                    if (changeL) {
                        failed.setHeaderText("'" +
                                contentSearchWord +
                                "'" +
                                " already exists in the dictionary.\n");
                        failed.setContentText("You should edit the definition of this word and save it!");
                    } else {
                        failed.setHeaderText("'" +
                                contentSearchWord +
                                "'" +
                                " đã tồn tại trong từ điển.\n");
                        failed.setContentText("Bạn nên sửa định nghĩa của từ này và lưu lại!");
                    }
                    failed.showAndWait();

                }
            }
        }
    }

    @FXML
    public void deleteWordFromDatabase() {
        Alert alert = new Alert(Alert.AlertType.WARNING, (changeL ? "Warning" : "Cảnh báo"), ButtonType.YES, ButtonType.NO);
        if (changeL) {
            alert.setContentText("Are you sure that you want to remove this word to this dictionary?");
        } else {
            alert.setContentText("Bạn có chắc chắn muốn xóa từ này khỏi từ điển?");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.YES) {
                String contentSearchWord = textfield1.getText().toLowerCase();
                if (!contentSearchWord.isEmpty() && currentDictionary.contain(contentSearchWord)) {
                    currentDictionary.deleteWord(contentSearchWord);
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    if (changeL) {
                        success.setHeaderText("'" +
                                contentSearchWord +
                                "'" +
                                " has been deleted to this dictionary!");
                    } else {
                        success.setHeaderText("'" +
                                contentSearchWord +
                                "'" +
                                " đã được xóa khỏi từ điển!");
                    }
                    success.showAndWait();
                    contentHTMLEditor.setHtmlText(defaultHTMLContent);
                } else {
                    Alert failed = new Alert(Alert.AlertType.ERROR);
                    if (changeL) {
                        failed.setHeaderText("'" +
                                contentSearchWord +
                                "'" +
                                " doesn't exists in the dictionary.\n");
                        failed.setContentText("You should check your typo or make sure that this word is available!");
                    } else {
                        failed.setHeaderText("'" +
                                contentSearchWord +
                                "'" +
                                " không tồn tại trong từ điển.\n");
                        failed.setContentText("Bạn nên kiểm tra lại lỗi chính tả hoặc đảm bảo từ này có sẵn!");
                    }
                    failed.showAndWait();

                }
            }
        }
    }

    @FXML
    public void searchWordExist(ActionEvent actionEvent) {
        String searchWord = textfield1.getText().toLowerCase();
        isLookedUp = true;
        if (searchWord.isEmpty() || !currentDictionary.contain(searchWord)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if (changeL) {
                alert.setTitle("Warning");
                alert.setContentText("'" +
                        searchWord +
                        "'" +
                        " isn't exist in this dictionary!\n" +
                        "If you need to add a new word, please insert content.\n" +
                        "Otherwise, you need to check your typo and search again.");
            } else {
                alert.setTitle("Cảnh báo");
                alert.setContentText("'" +
                        searchWord +
                        "'" +
                        " không tồn tại trong từ điển!\n" +
                        "Nếu bạn muốn thêm từ mới, vui lòng nhập nội dung.\n" +
                        "Nếu không, bạn cần kiểm tra lại lỗi chính tả và tìm kiếm lại.");
            }
            alert.showAndWait();
            contentHTMLEditor.setHtmlText(defaultHTMLContent);
        } else {
            currentSearchWord = currentDictionary.getWordInformation(searchWord);
            contentHTMLEditor.setHtmlText(currentSearchWord.getHtml());
        }
    }

    @FXML
    public void saveWordIntoDatabase(MouseEvent mouseEvent) {
        String contentSearchWord = textfield1.getText().toLowerCase();
        if (!contentSearchWord.isEmpty() && currentDictionary.contain(contentSearchWord)) {
            if (!isLookedUp) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (changeL) {
                    alert.setContentText("Please click search button to show the definition before save the word!");
                } else {
                    alert.setContentText("Vui lòng nhấn nút tìm kiếm để hiển thị định nghĩa trước khi lưu từ!");
                }
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, (changeL ?"Warning" : "Cảnh báo"), ButtonType.YES, ButtonType.NO);
                if (changeL) {
                    alert.setContentText("Are you sure that you want to save the definition of this word?");
                } else {
                    alert.setContentText("Bạn có chắc chắn muốn lưu định nghĩa của từ này?");
                }
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == ButtonType.YES) {
                        Word modifiedWord = currentSearchWord;
                        modifiedWord.setHtml(contentHTMLEditor.getHtmlText());
                        currentDictionary.modifyWord(modifiedWord);
                    }
                }
            }
        } else {
            if (contentSearchWord.isEmpty()) {
                if (changeL) {
                    contentSearchWord = "This word on the search field";
                } else {
                    contentSearchWord = "Từ trên ô tìm kiếm";
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (changeL) {
                alert.setHeaderText("ERROR: Can not save into dictionary!");
                alert.setContentText(contentSearchWord +
                        " does not exist in this dictionary!\n" +
                        "Please sure that your word is available in this dictionary!");
            } else {
                alert.setHeaderText("LỖI: Không thể lưu vào từ điển!");
                alert.setContentText(contentSearchWord +
                        " không tồn tại trong từ điển!\n" +
                        "Vui lòng đảm bảo từ của bạn có sẵn trong từ điển này!");
            }
            alert.showAndWait();
        }
    }

    @FXML
    public void resetToDefaultContent(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING, (changeL ? "Warning" : "Cảnh báo"), ButtonType.YES, ButtonType.NO);
        if (changeL) {
            alert.setContentText("Are you sure you want to refresh the content?");
        } else {
            alert.setContentText("Bạn có chắc chắn muốn làm mới nội dung?");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.YES) {
                if (isLookedUp) {
                    contentHTMLEditor.setHtmlText(currentSearchWord.getHtml());
                } else contentHTMLEditor.setHtmlText(defaultHTMLContent);
            }
        }
    }
}
