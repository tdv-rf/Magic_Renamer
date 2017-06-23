import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.util.List;

public class Controller {
    public TableColumn currentNameColumn = new TableColumn(), newNameColumn = new TableColumn();
    public TextField scanDir = new TextField(), mask = new TextField();;
    public ComboBox recurse = new ComboBox();
    public ComboBox templateRename = new ComboBox();
    public Button buttonScan, buttonRename;
    private Boolean state;
    private String dir, choiceState;

    @FXML
    private void initialize(){
    recurse.setItems(FXCollections.observableArrayList("Да","Нет"));
//    recurse.setValue("Да");
    templateRename.setItems(FXCollections.observableArrayList("Дата создания","Директория + имя", "Год + имя"));
    }

    public String setDirToScan() {
        dir = scanDir.getText();
        return dir;
    }

    public Boolean choiceRecurse() {
        String recurseState = (String)recurse.getValue();
        if(recurseState.equals("Да")){
            this.state = true;
        }else if(recurseState.equals("Нет")){
            this.state = false;
        }
        return state;
    }

    public void fieldMask(ActionEvent actionEvent) {
    }

    public void choiceTemplate(MouseEvent mouseEvent) {
        choiceState = (String)templateRename.getValue();
    }

    public void buttonBeginScan(MouseEvent mouseEvent) {
        setDirToScan();
        choiceRecurse();
        Scan scanner = new Scan();
        List<File> fileList = scanner.check(dir,state);
        for(File file: fileList){
            currentNameColumn.setText(file.getName());
        }
    }

    public void buttonRename(MouseEvent mouseEvent) {
    }
}
