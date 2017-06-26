import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Controller {
    public TableColumn currentNameColumn = new TableColumn<RenFiles,String>();
    public TableColumn newNameColumn = new TableColumn<RenFiles,String>();
    public TextField scanDir = new TextField(), mask = new TextField();;
    public ComboBox recurse = new ComboBox();
    public ComboBox templateRename = new ComboBox();
    public Button buttonScan, buttonRename;
    public TableView tableBase = new TableView<RenFiles>();
    public Label totalCount = new Label();
    private Boolean state;
    private String dir, choiceState,choiceMask;
    private ObservableList<RenFiles> data;

    @FXML
    private void initialize(){
        currentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        newNameColumn.setCellValueFactory(new PropertyValueFactory<>("newName"));
        recurse.setItems(FXCollections.observableArrayList("Да","Нет"));
        recurse.setValue("Да");
        templateRename.setItems(FXCollections.observableArrayList("Дата создания","Директория + имя", "Год + имя"));
        templateRename.setValue("Год + имя");
    }

    private void setDirToScan() {
        dir = scanDir.getText();
    }

    private void choiceRecurse() {
        String recurseState = (String)recurse.getValue();
        if(recurseState.equals("Да")){
            this.state = true;
        }else if(recurseState.equals("Нет")){
            this.state = false;
        }
    }

    private void fieldMask() {
        choiceMask = mask.getText();
    }

    private void choiceTemplate() {
        choiceState = templateRename.getValue().toString();
    }

    public void buttonBeginScan(MouseEvent mouseEvent) throws IOException, ParseException {
        setDirToScan();
        choiceRecurse();
        choiceTemplate();
        fieldMask();
     //Получаем список файлов
        Scan scanner = new Scan();
        scanner.check(dir,state);
     //Генерируем новые имена файлов
        if(choiceMask.equals("")){
            Rename.offerNames(Scan.fileList,choiceState);
        }else {
            Rename.offerNames(Scan.fileList,choiceMask);
        }
        addTableData();
        totalCount.setText("Итого файлов: "+ Integer.toString(Scan.fileList.size()));
    }

    public void buttonRename(MouseEvent mouseEvent) {
        Rename.rename(Scan.fileList);
        addTableData();
    }

    public void checkForBlock(KeyEvent keyEvent) {
        if(!mask.getText().equals("")){
            templateRename.setDisable(true);
        }else{
            templateRename.setDisable(false);
        }
    }

    private void addTableData(){
        //Заполняем данными таблицу
        ObservableList<RenFiles> data = FXCollections.observableArrayList(Scan.fileList);
        tableBase.setItems(data);
        tableBase.refresh();
    }
}
