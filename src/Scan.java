import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Scan {
    private File[] path;
    private List<File> fileList = new ArrayList<>();
    private List<File> folderList = new ArrayList<>();

    public List<File> check(String checkPath, Boolean recurse){
        scanForFiles(checkPath);
        if(!(folderList.isEmpty())&recurse){
            checkRecurse();
        }
        return fileList;
    }

    private void scanForFiles(String checkPath) throws NullPointerException, SecurityException{
        path = new File(checkPath).listFiles();
        for(File file: path){
            if (file.isDirectory()){
                folderList.add(file);
            }else {
                fileList.add(file);
            }
        }
    }

    private void checkRecurse(){
        for(File folder: folderList){
            scanForFiles(folder.toString());
            folderList.remove(folder);
        }
    }
}
