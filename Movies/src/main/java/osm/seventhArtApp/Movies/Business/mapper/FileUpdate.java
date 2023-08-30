package osm.seventhArtApp.Movies.Business.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpdate {
    private static final Logger log = LoggerFactory.getLogger(FileUpdate.class);

    public static String renameXmlFile(String newTitle, String xmlUploadDirectory, String fileNameXml) {
        try {
            String oldFilePath = xmlUploadDirectory + "/" + fileNameXml + ".xml";
            String newFileName = newTitle + ".xml";
            String newFilePath = xmlUploadDirectory + "/" + newFileName;

            Path oldPath = Paths.get(oldFilePath);
            Path newPath = Paths.get(newFilePath);

            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);

            log.info("File renamed successfully");

            return newFileName;
        } catch (Exception e) {
            System.out.println("Failed to rename XML file: " + e.getMessage());
            return null;
        }
    }

    public static void deleteXmlFile(String fileName, String xmlUploadDirectory) {
        try {
            String filePath = xmlUploadDirectory + "/" + fileName + ".xml";
            Path path = Paths.get(filePath);

            Files.deleteIfExists(path);
            System.out.println("XML file deleted: " + fileName + ".xml");
        } catch (Exception e) {
            System.out.println("Failed to delete XML file: " + e.getMessage());
        }
    }
    public static void moveXmlFileToDestination(String fileName, String xmlUploadDirectory, String xmlUploadDestination) {
        try {
            String sourceFilePath = xmlUploadDirectory + "/" + fileName;
            String destinationFilePath = xmlUploadDestination + "/" + fileName;

            Path sourcePath = Paths.get(sourceFilePath);
            Path destinationPath = Paths.get(destinationFilePath);

            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File moved to: " + destinationFilePath);

        } catch (Exception e) {
            System.out.println("Failed to move file: " + e.getMessage());
        }
    }



}
