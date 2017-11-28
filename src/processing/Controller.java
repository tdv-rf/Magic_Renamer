package processing;

import error.EnumError;
import error.MyError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;


public class Controller {
    public TableColumn<RenFiles,String> currentNameColumn = new TableColumn<>();
    public TableColumn<RenFiles,String> newNameColumn = new TableColumn<>();
    public TextField selectedDir = new TextField(), mask = new TextField();
    public ComboBox<String> recurse = new ComboBox<>();
    public ComboBox<String> sortOrder = new ComboBox<>();
    public ComboBox<String> templateRename = new ComboBox<>();
    public Button buttonScan, buttonRename;
    public TableView<RenFiles> tableBase = new TableView<>();
    public Label totalCount = new Label(), version = new Label();
    public TextArea textField = new TextArea();
    private List<RenFiles> fileList;
    private Rename rename;

    @FXML
    private void initialize(){
        currentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        newNameColumn.setCellValueFactory(new PropertyValueFactory<>("newName"));
        version.setText(DropDownFieldValues.version);
        textField.setText(DropDownFieldValues.helpTextForMask);
        recurse.setItems(DropDownFieldValues.recurseChoise);
        recurse.setValue("Да");
        sortOrder.setItems(DropDownFieldValues.sortChoise);
        sortOrder.setValue("По имени");
        templateRename.setItems(DropDownFieldValues.predifinedNameChoise);
        templateRename.setValue("Год + имя");
    }

    private String getDirectoryTextField(){
        selectedDir.setStyle("-fx-border-color:d9dce0;");
        String selectedDirectory = "";
        try {
            selectedDirectory = selectedDir.getText();
            if(selectedDirectory.equals("")) {
                throw new MyError(EnumError.EMPTY_PATH);
            }
        }catch (MyError e) {
            textField.setVisible(true);
            textField.setStyle("-fx-font-size: 20px;");
            selectedDir.setStyle("-fx-border-color:red;");
            textField.setText(e.description.toString());
        }
        return selectedDirectory;
    }

    public void buttonProcessContent() {
        getFileList();
        generateNewFileNames();
        fillTableWithData();
        totalCount.setText("Итого файлов: "+ Integer.toString(fileList.size()));
    }

    private void getFileList(){
        String recurseState = recurse.getValue();
        String sortBy = sortOrder.getValue();
        FileScanner fileScanner = new FileScanner();
        fileScanner.setRecurseStatus(recurseState);
        fileScanner.setDirToScan(getDirectoryTextField());
        fileScanner.sortFileList(sortBy);
        fileList = fileScanner.getFileList();
    }

    private void generateNewFileNames(){
        String renameOwnMask = mask.getText();
        if (renameOwnMask.equals("")){
            renameOwnMask = "dd-MM-YYYY";
        }
        String nameChoice = templateRename.getValue();
        rename = new Rename(fileList, renameOwnMask, nameChoice);
        rename.offerNames();
    }

    public void buttonRename() {
        rename.setNamesToFiles(fileList);
        fillTableWithData();
    }

    private void fillTableWithData(){
        ObservableList<RenFiles> data = FXCollections.observableArrayList(fileList);
        tableBase.setItems(data);
        tableBase.refresh();
    }

    public void showHelp() {
        textField.setVisible(true);
    }

    public void hideHelp() {
        if(!mask.getText().equals("")){
            textField.setVisible(true);
        }else{
            textField.setVisible(false);
        }
    }

    public void openFileExplorer() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        File selectedFile = dirChooser.showDialog(new Stage());
        if(selectedFile != null){
            selectedDir.setText(selectedFile.getPath());
        }
    }
}