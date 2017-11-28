package processing;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

class Rename {
    private String renameOwnMask;
    private String nameChoice;
    private List<RenFiles> fileList;

    Rename(List<RenFiles> fileList, String renameOwnMask, String nameChoice) {
        this.renameOwnMask = renameOwnMask;
        this.fileList = fileList;
        this.nameChoice = nameChoice;
    }

    void offerNames() {
        int i = 0;
        for (RenFiles currentFile : fileList) {
            String extension = getExtension(currentFile);

            switch (nameChoice) {
                case "Директория + имя":
                    currentFile.setNewName(currentFile.getFile().getPath() + " " + currentFile.getFile().getName());
                    break;
                case "Год + имя":
                    String str = currentFile.getCreationStamp().toString().substring(0, 4);
                    currentFile.setNewName(str + " " + currentFile.getFile().getName());
                    break;
                case "Дата создания":
                    str = currentFile.getCreationStamp().toString().substring(0, 10);
                    currentFile.setNewName(str + " " + currentFile.getFile().getName());
                    break;
                case "Удалить часть имени":
                    currentFile.setNewName(currentFile.getFile().getName().replace(renameOwnMask, ""));
                    break;
                case "Добавить часть имени":
                    int z = currentFile.getFile().getName().lastIndexOf(".");
                    currentFile.setNewName(currentFile.getFile().getName().substring(0, z) + " " + renameOwnMask + extension);
                    break;
                case "Задать свое имя":
                    currentFile.setNewName(renameOwnMask + extension);
                    break;
                case "Дата по маске":
                    String setName = new SimpleDateFormat(renameOwnMask).format(currentFile.getCreationStamp().toMillis());
                    currentFile.setNewName(setName + extension);
                    break;
            }
            if (renameOwnMask.contains("[i]")) {
                String fileNumber = getCurrentNumber(++i);
                currentFile.setNewName(fileNumber + renameOwnMask.substring(3) + currentFile.getFile().getName());
            }
        }
    }

    private String getCurrentNumber(int currentIteration) {
        String fileNumber;
        if (currentIteration < 10) {
            fileNumber = "0" + currentIteration + " ";
        } else {
            fileNumber = currentIteration + " ";
        }
        return fileNumber;
    }

    private String getExtension(RenFiles currentFile) {
        String extension = "";
        int z = currentFile.getFile().getName().lastIndexOf(".");
        if (z > 0) extension = currentFile.getFile().getName().substring(z);
        return extension;
    }

    void setNamesToFiles(List<RenFiles> file) {
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