import java.io.File;
import java.nio.file.attribute.FileTime;


public class RenFiles {
    private File file;
    private String name;
    private String newName;
    private FileTime creationStamp;

    RenFiles(File file, String name, FileTime creationStamp) {
        this.file = file;
        this.name = name;
        this.creationStamp = creationStamp;
    }

    public FileTime getCreationStamp() {
        return creationStamp;
    }

    //Public для TableView
    public String getName() {
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
