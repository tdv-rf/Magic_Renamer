package processing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class DropDownFieldValues {
    static final String helpTextForMask =   "G   Era designator  Text    AD\n" +
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
            "[i] Для вставки счетчика";
    static final String version = "0.97";
    static final ObservableList<String> recurseChoise =  FXCollections.observableArrayList("Да","Нет");
    static final ObservableList<String> sortChoise = FXCollections.observableArrayList("По имени", "По дате создания");
    static final ObservableList<String> predifinedNameChoise = FXCollections.observableArrayList(
            "Дата создания","Директория + имя",
            "Год + имя", "Удалить часть имени",
            "Добавить часть имени", "Задать свое имя",
            "Дата по маске");
}
