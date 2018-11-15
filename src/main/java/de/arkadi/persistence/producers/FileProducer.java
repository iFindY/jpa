package de.arkadi.persistence.producers;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class FileProducer {

    // ======================================
    // =          Business methods          =
    // ======================================

    //  @Produces
    public Path produceFile() throws IOException {

        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-x---");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);

        Path directory = FileSystems.getDefault().getPath("store");

        if (Files.notExists(directory))
            Files.createDirectory(directory, attr);

        Path file = directory.resolve("myfile.txt");

        if (Files.notExists(file))
            Files.createFile(file, attr);

        return file;
    }
}
