import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.BasicFileAttributeView;
import java.text.SimpleDateFormat;
import java.util.List;

class Rename {

    private static BasicFileAttributes attr;
    static void offerNames(List<RenFiles> file, String mask) {
        int i =0;
        for (RenFiles current : file) {
            i++;
            String setName, ext="";
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
                default:
                    if(mask.contains("Text:")){
                        if(mask.substring(5).contains("[i]")) {
                            if(i<10) {setName = "0" + i + " " + mask.substring(8);
                            }else{setName = i + " " + mask.substring(8);}
                        }else{
                            setName = mask.substring(5);
                        }
                    }else {
                        //Проверяем на спецсимволы по маске.
                        dateTime(current.getFile());
                        setName = new SimpleDateFormat(mask).format(attr.creationTime().toMillis());
                    }
                    int z = current.getFile().getName().lastIndexOf(".");
                    if (z > 0) ext = current.getFile().getName().substring(z);
                    current.setNewName(setName + ext);
            }
        }
    }

    private static String dateTime(File file) {
        try {
            Path path = Paths.get(file.getAbsoluteFile().toURI());
            attr = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
        }catch (IOException e){
            System.out.println(EnumError.IOException.toString());
        }
      return attr.creationTime().toString();
    }

    static void rename(List<RenFiles> file){
        for (RenFiles current : file) {
            File resultFileName = new File(current.getFile().getParent() + File.separator + current.getNewName());
            boolean status = current.getFile().renameTo(resultFileName);
            if (status) {
                current.setNewName("Done!");
            } else {
                current.setNewName("Error!");
            }
        }
    }
}
