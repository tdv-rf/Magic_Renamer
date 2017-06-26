import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.BasicFileAttributeView;
import java.text.ParseException;
import java.util.List;

class Rename {

    static void offerNames(List<RenFiles> file, String mask) throws ParseException, IOException {
        for (RenFiles current : file) {
            switch (mask) {
                case "Директория + имя":
                    current.setNewName(current.getFile().getPath() + " " + current.getFile().getName());
                    break;
                case "Год + имя":
                    String str = dateTime(current.getFile()).substring(0, 4);
                    current.setNewName(str + " " + current.getFile().getName());
                    break;
                case "Дата создания":
                    str = dateTime(current.getFile()).substring(0, 10);
                    current.setNewName(str + " " + current.getFile().getName());
                    break;
            }
        }
    }

    private static String dateTime(File file) throws IOException{
        Path path = Paths.get(file.getAbsoluteFile().toURI());
        BasicFileAttributes attr = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
      return attr.creationTime().toString();
    }

    static void rename(List<RenFiles> file){
        for (RenFiles current : file) {
            File resultFileName = new File(current.getFile().getParent() + File.separator + current.getNewName());
            current.getFile().renameTo(resultFileName);
            if (resultFileName.exists()) {
                current.setNewName("Done!");
            } else {
                current.setNewName("Error!");
            }
        }
    }
}
