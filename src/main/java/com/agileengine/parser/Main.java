package com.agileengine.parser;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.util.Collections.singletonList;

public class Main {
    public static void main(String[] args) {
        Validator validator = new Validator();
        Analyzer analyzer = new Analyzer();
        StringBuilder stringBuilder = new StringBuilder();
        File source = null;
        File target = null;
        Document sourceDocument = null;
        Document targetDocument = null;
        if (!validator.isNumberOfArgumentsValid(args)) {
            return;
        }

        try {
            source = validator.getFile(args[0]);
            target = validator.getFile(args[1]);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
            System.out.println(e.getMessage());
        }
        try {
            sourceDocument = analyzer.getDocument(source);
            targetDocument = analyzer.getDocument(target);
        } catch (IOException e) {
            System.out.println("Can't parse the document:");
            System.out.println(e.getMessage());
        }
        Element coreElement = analyzer.findCoreElement(sourceDocument);
        System.out.println(coreElement.attributes());
        System.out.println(analyzer.compareElements(targetDocument, coreElement));
        System.out.println(analyzer.getPath(singletonList(targetDocument), stringBuilder).toString());
    }
}
