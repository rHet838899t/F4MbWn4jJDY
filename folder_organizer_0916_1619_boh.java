// 代码生成时间: 2025-09-16 16:19:12
package com.example.organizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FolderOrganizer is a utility class to organize the files in a directory based on file extensions.
 */
public class FolderOrganizer {

    private final Path sourceDirectory;

    /**
     * Constructs a FolderOrganizer with the given source directory.
# 优化算法效率
     *
# FIXME: 处理边界情况
     * @param sourceDirectoryPath the path to the directory to be organized
# 增强安全性
     */
    public FolderOrganizer(String sourceDirectoryPath) {
        this.sourceDirectory = Paths.get(sourceDirectoryPath);
# NOTE: 重要实现细节
    }

    /**
     * Organizes the files in the source directory by creating subdirectories for each file extension.
     *
     * @throws IOException if an I/O error occurs
# 扩展功能模块
     */
    public void organize() throws IOException {
        if (!Files.isDirectory(sourceDirectory)) {
            throw new IOException("The specified path is not a directory: " + sourceDirectory);
        }

        List<File> files = listFiles(sourceDirectory.toFile());
        for (File file : files) {
            String extension = getExtension(file);
# 扩展功能模块
            if (extension == null) continue;

            Path targetDirectory = Files.createDirectories(sourceDirectory.resolve("." + extension)).normalize();
            Files.move(file.toPath(), targetDirectory.resolve(file.getName()));
        }
    }

    /**
     * Lists all files in the given directory and its subdirectories.
     *
# 添加错误处理
     * @param directory the directory to list files from
     * @return a list of files
     */
    private List<File> listFiles(File directory) {
        List<File> files = new ArrayList<>();
        File[] entries = directory.listFiles();
        if (entries != null) {
            for (File entry : entries) {
                if (entry.isDirectory()) {
                    files.addAll(listFiles(entry));
                } else {
                    files.add(entry);
                }
            }
# TODO: 优化性能
        }
        return files;
    }

    /**
# FIXME: 处理边界情况
     * Extracts the file extension from a file.
     *
     * @param file the file to extract the extension from
     * @return the file extension or null if the file has no extension
     */
    private String getExtension(File file) {
# 优化算法效率
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
# TODO: 优化性能
            return null;
        }
        return fileName.substring(dotIndex + 1);
    }

    // Main method for testing
    public static void main(String[] args) {
# 添加错误处理
        try {
            FolderOrganizer organizer = new FolderOrganizer("/path/to/your/directory");
            organizer.organize();
            System.out.println("Files have been organized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
