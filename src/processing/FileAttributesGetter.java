package processing;

import error.EnumError;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

class FileAttributesGetter {

    static FileTime getDateTimeFromFile(File file) {
        BasicFileAttributes fileAttributes;
        FileTime fileDate = null;
        try {
            Path filePath = Paths.get(file.getAbsoluteFile().toURI());
            fileAttributes = Files.getFileAttributeView(filePath, BasicFileAttributeView.class).readAttributes();
            fileDate = fileAttributes.creationTime();
        }catch (IOException e){
            System.out.println(EnumError.IOException.toString());
        }
        return fileDate;
    }
}
