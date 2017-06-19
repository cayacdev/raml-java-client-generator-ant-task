package com.github.jazhead.client.codegen.anttask;


import com.sun.codemodel.JClassAlreadyExistsException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.mule.client.codegen.RamlJavaClientGenerator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RamlJavaClientGeneratorTask extends Task {

    private File ramlFile;

    private String basePackage;

    private File destinationDirectory;

    @Override
    public void execute() throws BuildException {
        validateParameters();

        URL url;
        try {
            url = ramlFile.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new BuildException(e);
        }

        generateClient(url);
    }

    private void generateClient(URL url) {
        final RamlJavaClientGenerator ramlJavaClientGenerator = new RamlJavaClientGenerator(basePackage, destinationDirectory);

        try {
            ramlJavaClientGenerator.generate(url);
        } catch (JClassAlreadyExistsException | IOException e) {
            throw new BuildException(e);
        }
    }

    private void validateParameters() throws BuildException {

        if (ramlFile == null) {
            throw new BuildException("A 'ramlFile' must be given!");
        }

        if (ramlFile.exists() && !ramlFile.isFile()) {
            throw new BuildException("'ramlFile' must point to a file, not a directory!");
        }

        if (destinationDirectory == null) {
            throw new BuildException("A 'destinationDirectory' must be given!");
        }

        if (destinationDirectory.exists() && !destinationDirectory.isDirectory()) {
            throw new BuildException("'destinationDirectory' must point to a directory, not a file!");
        }

        if (basePackage == null || basePackage.equals("")) {
            throw new BuildException("A 'basePackage' must be given!");
        }
    }

    public void setRamlFile(File ramlFile) {
        this.ramlFile = ramlFile;
    }

    public void setDestinationDirectory(File destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
