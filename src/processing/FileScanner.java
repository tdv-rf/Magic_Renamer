package processing;

import error.EnumError;

import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class FileScanner {
    private List<RenFiles> fileList = new ArrayList<>();
    private Boolean recurseStatus;

    void setRecurseStatus(String status) {
        if (status.equals("Да")) {
            this.recurseStatus = true;
        } else if (status.equals("Нет")) {
            this.recurseStatus = false;
        }
    }

    void setDirToScan(String checkPath) {
        if (!(checkPath == null || checkPath.equals("")))
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

    void sortFileList(String sortOrder) {
        if (sortOrder.equals("По имени")) {
            fileList.sort(Comparator.comparing(RenFiles::getFile));
        } else if (sortOrder.equals("По дате создания")) {
            fileList.sort(Comparator.comparing(RenFiles::getCreationStamp));
        }
    }

    List<RenFiles> getFileList() {
        return fileList;
    }
} 