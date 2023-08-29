package osm.seventhArtApp.Movies.Business.mapper;

import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpdate {

    public static String renameXmlFile(String newId,String xmlUploadDirectory, String fileNameXml) {
        try {
            String oldFilePath = xmlUploadDirectory + "/" + fileNameXml + ".xml";
            String newFileName = newId + ".xml";
            String newFilePath = xmlUploadDirectory + "/" + newFileName;

            Path oldPath = Paths.get(oldFilePath);
            Path newPath = Paths.get(newFilePath);

            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (Exception e) {
            System.out.println("Failed to rename XML file: " + e.getMessage());
            return null;
        }
    }
}
