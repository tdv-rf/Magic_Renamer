import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class Controller {
    public TableColumn currentNameColumn = new TableColumn<RenFiles,String>();
    public TableColumn newNameColumn = new TableColumn<RenFiles,String>();
    public TextField scanDir = new TextField(), mask = new TextField();;
    public ComboBox recurse = new ComboBox();
    public ComboBox templateRename = new ComboBox();
    public Button buttonScan, buttonRename;
    public TableView tableBase = new TableView<RenFiles>();
    public Label totalCount = new Label(), version = new Label();
    public TextArea textField = new TextArea();
    private Boolean state;
    private String dir, choiceState,choiceMask;
    private ObservableList<RenFiles> data;
    private final String VER = "0.92";

    @FXML
    private void initialize(){
        String stri =   "G   Era designator  Text    AD\n" +
                        "y   Year    Year    1996; 96\n" +
                        "Y   Week year   Year    2009; 09\n" +
                        "M   Month in year   Month   July; Jul; 07\n" +
                        "w   Week in year    Number  27\n" +
                        "W   Week in month   Number  2\n" +
                        "D   Day in year Number  189\n" +
                        "d   Day in month    Number  10\n" +
                        "F   Day of week in month    Number  2\n" +
                        "E   Day name in week    Text    Tuesday; Tue\n" +
                        "u   Day number of week (1 = Monday, ..., 7 = Sunday) Number  1\n" +
                        "\n"+
                        "\n"+
                        "Для набора своего текста введите в поле Text: +свой текст";
        textField.setText(stri);
        version.setText(VER);
        currentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        newNameColumn.setCellValueFactory(new PropertyValueFactory<>("newName"));
        recurse.setItems(FXCollections.observableArrayList("Да","Нет"));
        recurse.setValue("Да");
        templateRename.setItems(FXCollections.observableArrayList("Дата создания","Директория + имя", "Год + имя"));
        templateRename.setValue("Год + имя");
    }

    private void setDirToScan(){
        scanDir.setStyle("-fx-border-color:d9dce0;");
        try {
            dir = scanDir.getText();
            if(dir.equals(""))
                throw new MyError(EnumError.EMPTY_PATH);
        }catch (MyError e) {
            textField.setVisible(true);
            textField.setStyle("-fx-font-size: 20px;");
            scanDir.setStyle("-fx-border-color:red;");
            textField.setText(e.description.toString());
        }
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

    public void buttonBeginScan(MouseEvent mouseEvent) {
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
            textField.setVisible(true);
        }else{
            templateRename.setDisable(false);
            textField.setVisible(false);
        }
    }

    private void addTableData(){
        //Заполняем данными таблицу
        ObservableList<RenFiles> data = FXCollections.observableArrayList(Scan.fileList);
        tableBase.setItems(data);
        tableBase.refresh();
    }

    public void showHelp(MouseEvent mouseEvent) {
        textField.setVisible(true);
    }

    public void openFileExplorer(MouseEvent mouseEvent) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.showOpenDialog(stage);
    }
}
