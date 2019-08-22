package com.agileengine.parser;

import java.io.File;
import java.io.FileNotFoundException;

import static java.util.Optional.of;

public class Validator {

    public static final int NUMBER_OF_VALID_ARGUMENTS = 2;

    public File getFile(String filePath) throws FileNotFoundException {
        return of(new File(filePath)).orElseThrow(FileNotFoundException::new);
    }

    public boolean isNumberOfArgumentsValid(String[] args) {
        return args.length == NUMBER_OF_VALID_ARGUMENTS;
    }
}
