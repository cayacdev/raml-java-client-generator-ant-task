package com.github.jazhead.client.codegen.anttask;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.BuildFileTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RamlJavaClientGeneratorTaskTest extends BuildFileTest {

    private Path tempDir;

    public void setUp() throws Exception {
        super.setUp();

        tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "raml-java-client-generator");
        configureProject("src/test/resources/test.xml");
    }

    public void tearDown() throws Exception {
        super.tearDown();

        FileUtils.deleteDirectory(tempDir.toFile());
    }

    public void testTask_createsOutputDirectory() {
        executeTarget("createJsonSchema");

        final File outputDirectory = getFile("output");
        assertTrue(outputDirectory.isDirectory());
    }

    public void testTask_useExistingOutputDirectory() throws IOException {
        final File outputDirectory = getFile("output");
        FileUtils.forceMkdir(outputDirectory);

        executeTarget("createJsonSchema");

        assertTrue(outputDirectory.isDirectory());
    }

    public void testTask_isClientApiGenerated() throws IOException {
        executeTarget("createJsonSchema");

        final File outputDirectory = getFile("output/de/test/");

        assertTrue(outputDirectory.isDirectory());
    }

    private File getFile(String path) {

        return tempDir.resolve(Paths.get(path)).toFile();
    }
}

