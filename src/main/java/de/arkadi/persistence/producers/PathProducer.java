package de.arkadi.persistence.producers;

import de.arkadi.persistence.qualifier.Root;
import de.arkadi.persistence.qualifier.TempRoot;

import javax.enterprise.inject.Produces;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathProducer {

    // ======================================
    // =          Business methods          =
    // ======================================

    @Produces
    @Root
    private Path toolsRoot = Paths.get(System.getProperty("user.home")).resolve("Tools");

    @Produces
    @TempRoot
    private Path tmpRoot = Paths.get("/tmp");

}
