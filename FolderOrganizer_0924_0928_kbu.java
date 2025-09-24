// 代码生成时间: 2025-09-24 09:28:15
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import play.Logger;
import play.libs.F;

/**
 * FolderOrganizer is a utility class to organize files within a directory based on file extensions.
 * It sorts files into separate subdirectories for each file type.
 */
public class FolderOrganizer {

    private static final Logger.ALogger logger = Logger.of(FolderOrganizer.class);

    /**
     * Main method to run the folder organizer.
     * @param args the directory path to organize.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            logger.error("Please provide a directory path as an argument.");
            return;
        }

        String directoryPath = args[0];
        try {
            organizeFiles(directoryPath);
        } catch (IOException e) {
            logger.error("Error organizing files in directory: " + directoryPath, e);
        }
    }

    /**
     * Organizes files in the given directory by file extensions.
     * @param directoryPath the path of the directory to organize.
     * @throws IOException if an I/O error occurs.
     */
    public static void organizeFiles(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException("The provided path is not a valid directory.");
        }

        // Get all files in the directory
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Failed to list files in the directory.");
        }

        // Sort files by extension
        Arrays.sort(files, Comparator.comparingInt(file -> file.getName().lastIndexOf("."))
                .thenComparing(Comparator.comparing(file -> file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase()))
        );

        // Create subdirectories for each file extension and move files into them
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                File destDir = new File(directory, extension);

                if (!destDir.exists()) {
                    boolean isCreated = destDir.mkdir();
                    if (!isCreated) {
                        throw new IOException("Failed to create directory for extension: " + extension);
                    }
                }

                Path sourcePath = Paths.get(file.toURI());
                Path destPath = Paths.get(destDir.toURI()).resolve(fileName);
                Files.move(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
