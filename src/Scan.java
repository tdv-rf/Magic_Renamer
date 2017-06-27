import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Scan {
    static List<RenFiles> fileList = new ArrayList<>();
    private Boolean recurse;

    void check(String checkPath, Boolean recurse){
        this.recurse = recurse;
        fileList.clear();
        scanForFiles(new File(checkPath));
        fileList.sort(Comparator.comparing(RenFiles::getFile));
    }
    private void scanForFiles(File checkPath){
        File[] path = checkPath.listFiles();
        try {
            if (path != null) {
                for (File file : path) {
                    if (file.isDirectory() & recurse) {
                        scanForFiles(file);
                    } else {
                        fileList.add(new RenFiles(file, file.getName()));
                    }
                }
            }
        }catch (SecurityException e){
            System.out.println(EnumError.SecurityException);
        }
    }
} 