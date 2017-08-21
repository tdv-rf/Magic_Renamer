import error.EnumError;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

class Scan {
    static List<RenFiles> fileList = new ArrayList<>();
    private Boolean recurse;

    void check(String checkPath, Boolean recurse){
        this.recurse = recurse;
        fileList.clear();
        scanForFiles(new File(checkPath));
    }
    private void scanForFiles(File checkPath){
        File[] path = checkPath.listFiles();
        try {
            if (path != null) {
                for (File file : path) {
                    if (file.isDirectory() & recurse) {
                        scanForFiles(file);
                    } else {
                        FileTime fileDate = dateTime(file);
                        fileList.add(new RenFiles(file, file.getName(), fileDate));
                    }
                }
            }
        }catch (SecurityException e){
            System.out.println(EnumError.SecurityException);
        }
    }

    private static FileTime dateTime(File file) {
        BasicFileAttributes attr;
        FileTime filedate = null;
        try {
            Path path = Paths.get(file.getAbsoluteFile().toURI());
            attr = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
            filedate = attr.creationTime();
        }catch (IOException e){
            System.out.println(EnumError.IOException.toString());
        }
        return filedate;
    }
} 