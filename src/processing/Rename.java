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
            NameChoiser choice = NameChoiser.getValueformString(nameChoice);
            switch (choice) {
                case DIRECTORY_AND_NAME:
                    currentFile.setNewName(currentFile.getFile().getPath() + " " + currentFile.getFile().getName());
                    break;
                case YEAR_AND_NAME:
                    String str = currentFile.getCreationStamp().toString().substring(0, 4);
                    currentFile.setNewName(str + " " + currentFile.getFile().getName());
                    break;
                case CREATION_DATE:
                    str = currentFile.getCreationStamp().toString().substring(0, 10);
                    currentFile.setNewName(str + " " + currentFile.getFile().getName());
                    break;
                case DEL_PART_NAME:
                    currentFile.setNewName(currentFile.getFile().getName().replace(renameOwnMask, ""));
                    break;
                case ADD_PART_NAME:
                    int z = currentFile.getFile().getName().lastIndexOf(".");
                    currentFile.setNewName(currentFile.getFile().getName().substring(0, z) + " " + renameOwnMask + extension);
                    break;
                case MAKE_OWN_NAME:
                    currentFile.setNewName(renameOwnMask + extension);
                    break;
                case DATE_BY_MASK:
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
        int indexOfExtension = currentFile.getFile().getName().lastIndexOf(".");
        if (indexOfExtension > 0){
            extension = currentFile.getFile().getName().substring(indexOfExtension);
        }
        return extension;
    }

    void setNamesToFiles(List<RenFiles> file) {
        for (RenFiles current : file) {
            File resultFileName = new File(current.getFile().getParent() + File.separator + current.getNewName());
            boolean renameStatus = current.getFile().renameTo(resultFileName);
            if (renameStatus) {
                current.setNewName("Done!");
            } else {
                current.setNewName("Error!");
            }
        }
    }
}