
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

class Rename {

    static void offerNames(List<RenFiles> file, String mask, String state) {
        int i =0;
        for (RenFiles current : file) {
            String setName, k, ext="";
            i++;
            if(i<10) {
                k = "0" + i + " ";
            }else {
                k = i + " ";
            }
            int z = current.getFile().getName().lastIndexOf(".");
            if (z > 0) ext = current.getFile().getName().substring(z);

            switch (state) {
                case "Директория + имя":
                    current.setNewName(current.getFile().getPath() + " " + current.getFile().getName());
                    break;
                case "Год + имя":
                    String str = current.getCreationStamp().toString().substring(0, 4);
                    current.setNewName(str + " " + current.getFile().getName());
                    break;
                case "Дата создания":
                    str = current.getCreationStamp().toString().substring(0, 10);
                    current.setNewName(str + " " + current.getFile().getName());
                    break;
                case "Удалить часть имени":
                    current.setNewName(current.getFile().getName().replace(mask,""));
                    break;
                case "Добавить часть имени":
                    current.setNewName(current.getFile().getName().substring(0,z) + " " + mask + ext);
                    break;
                case "Задать свое имя":
                    if(mask.contains("[i]")) {
                        current.setNewName(k + mask.substring(3) + ext);
                    }else{
                        current.setNewName(mask + ext);
                    }
                    break;
                case "Дата по маске":
                    if(!mask.equals("")) {
                        if(mask.contains("[i]")) {
                            setName = new SimpleDateFormat(mask.substring(3)).format(current.getCreationStamp().toMillis());
                            current.setNewName(k + setName + ext);
                        }else {
                            setName = new SimpleDateFormat(mask).format(current.getCreationStamp().toMillis());
                            current.setNewName(setName + ext);
                        }
                    }
                    break;
            }
        }
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