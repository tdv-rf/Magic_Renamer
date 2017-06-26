import java.io.File;


public class RenFiles {
    private File file;
    private String name;
    private String newName;

    RenFiles(File file, String name){
        this.file = file;
        this.name = name;
    }

    //Public для TableView
    public String getName(){
        return name;
    }
    //Public для TableView
    public String getNewName() {
        return newName;
    }

    protected void setName(String name) {
        this.name = name;
    }


    File getFile() {
        return file;
    }
    void setNewName(String newName) {
        this.newName = newName;
    }
}
