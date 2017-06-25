import java.io.File;


public class RenFiles {
    private File file;
    private String name;
    private String newName;

    public RenFiles(File file, String name){
        this.file = file;
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public File getFile() {
        return file;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
