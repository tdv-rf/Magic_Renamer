import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scan {
    public static List<RenFiles> fileList = new ArrayList<>();
    private List<File> folderList = new ArrayList<>();

    public void check(String checkPath, Boolean recurse){
        fileList.clear();
        scanForFiles(checkPath);
        while (!(folderList.isEmpty())&recurse){
            checkRecurse();
        }
    }

    private void scanForFiles(String checkPath) throws NullPointerException, SecurityException{
        File[] path;
        path = new File(checkPath).listFiles();
        for(File file: path){
            if (file.isDirectory()){
                folderList.add(file);
            }else {
                fileList.add(new RenFiles(file,file.getName()));
            }
        }
    }

    private void checkRecurse(){
        Iterator<File> iterator = folderList.iterator();
        while(iterator.hasNext()){
            String subPath = iterator.next().toString();
            scanForFiles(subPath);
            iterator.remove();
        }
    }
} 