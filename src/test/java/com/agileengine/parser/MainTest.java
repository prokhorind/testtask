package com.agileengine.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MainTest {
    private static final String EXPECTED_RESULT_FOR_FIRST_FILE = "<a class=\"btn btn-success\" href=\"#check-and-ok\" title=\"Make-Button\" rel=\"done\" onclick=\"javascript:window.okDone(); return false;\"> Make everything OK </a>";
    private static final String EXPECTED_RESULT_FOR_SECOND_FILE = "<a class=\"btn test-link-ok\" href=\"#ok\" title=\"Make-Button\" rel=\"next\" onclick=\"javascript:window.okComplete(); return false;\"> Make everything OK </a>";
    private static final String EXPECTED_RESULT_FOR_THIRD_FILE = "<a class=\"btn btn-success\" href=\"#ok\" title=\"Do-Link\" rel=\"next\" onclick=\"javascript:window.okDone(); return false;\"> Do anything perfect </a>";
    private static final String EXPECTED_RESULT_FOR_FOURTH_FILE = "<a class=\"btn btn-success\" href=\"#ok\" title=\"Make-Button\" rel=\"next\" onclick=\"javascript:window.okFinalize(); return false;\"> Do all GREAT </a>";

    private Validator validator;
    private Analyzer analyzer;

    @Before
    public void setUp() {
        validator = new Validator();
        analyzer = new Analyzer();
    }


    @Test
    public void shouldFindButtonWhenSampleFirstUsed() throws IOException {
        String[] args = new String[]{"pages/sample-0-origin.html", "pages/sample-1-evil-gemini.html"};
        Element actualResult = getButton(args);
        assertEquals(EXPECTED_RESULT_FOR_FIRST_FILE, actualResult.toString());
    }

    @Test
    public void shouldFindButtonWhenSampleSecondUsed() throws IOException {
        String[] args = new String[]{"pages/sample-0-origin.html", "pages/sample-2-container-and-clone.html"};
        Element actualResult = getButton(args);
        assertEquals(EXPECTED_RESULT_FOR_SECOND_FILE, actualResult.toString());
    }

    @Test
    public void shouldFindButtonWhenSampleThirdUsed() throws IOException {
        String[] args = new String[]{"pages/sample-0-origin.html", "pages/sample-3-the-escape.html"};
        Element actualResult = getButton(args);
        assertEquals(EXPECTED_RESULT_FOR_THIRD_FILE, actualResult.toString());
    }

    @Test
    public void shouldFindButtonWhenSampleFourthUsed() throws IOException {
        String[] args = new String[]{"pages/sample-0-origin.html", "pages/sample-4-the-mash.html"};
        Element actualResult = getButton(args);
        assertEquals(EXPECTED_RESULT_FOR_FOURTH_FILE, actualResult.toString());
    }

    private Element getButton(String[] args) throws IOException {
        File source = validator.getFile(args[0]);
        File target = validator.getFile(args[1]);

        Document sourceDocument = analyzer.getDocument(source);
        Document targetDocument = analyzer.getDocument(target);

        Element coreElement = analyzer.findCoreElement(sourceDocument);
        return analyzer.compareElements(targetDocument, coreElement);
    }
}