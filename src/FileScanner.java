import error.EnumError;

import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

class FileScanner {
    private List<RenFiles> fileList = new ArrayList<>();
    private Boolean recurseStatus;

    FileScanner(String checkPath, Boolean recurseStatus) {
        this.recurseStatus = recurseStatus;
        fileList.clear();
        createFileList(new File(checkPath));
    }

    private void createFileList(File checkPath) {
        File[] path = checkPath.listFiles();
        if (path != null)
            try {
                for (File file : path) {
                    if (file.isDirectory() && recurseStatus) {
                        createFileList(file);
                    } else {
                        FileTime fileDate = FileAttributesGetter.getDateTimeFromFile(file);
                        fileList.add(new RenFiles(file, file.getName(), fileDate));
                    }
                }
            } catch (SecurityException e) {
                System.out.println(EnumError.SecurityException);
            }
    }

    List<RenFiles> getFileList() {
        return fileList;
    }
} 