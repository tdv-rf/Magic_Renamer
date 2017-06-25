import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.BasicFileAttributeView;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;


public class Rename {

    public static List<RenFiles> offerNames(List<RenFiles> file, String mask) throws ParseException, IOException {
        Iterator<RenFiles> iterator = file.iterator();
        while (iterator.hasNext()){
            RenFiles current = iterator.next();
            switch (mask){
                case "Директория + имя":
                    current.setNewName(current.getFile().getPath() + " " + current.getFile().getName());
                    break;
                case "Год + имя":
                    String str = dateTime(current.getFile()).substring(0,4);
                    current.setNewName(str + " " +current.getFile().getName());
                    break;
                case "Дата создания":
                    str = dateTime(current.getFile()).substring(0,10);
                    current.setNewName(str + " " + current.getFile().getName());
                    break;
            }
        }
        return file;
    }

    private static String dateTime(File file) throws IOException{
        Path path = Paths.get(file.getAbsoluteFile().toURI());
        BasicFileAttributes attr = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
        String str =attr.creationTime().toString();
      return str;
    }

    public static void rename(List<RenFiles> file){
        Iterator<RenFiles> iterator = file.iterator();
        while (iterator.hasNext()){
            RenFiles current = iterator.next();
            File resultFileName = new File(current.getFile().getParent()+ File.separator + current.getNewName());
            current.getFile().renameTo(resultFileName);
            if (resultFileName.exists()){
                current.setNewName("Done!");
            }else{
                current.setNewName("Error!");
            }
        }
    }
}
