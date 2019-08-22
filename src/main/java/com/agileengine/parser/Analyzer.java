package com.agileengine.parser;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.max;
import static java.util.Comparator.comparing;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.jsoup.Jsoup.parse;

public class Analyzer {
    private static final String CHARSET = "utf8";
    private static final String BUTTON_ID = "make-everything-ok-button";
    private static final int ZERO_VALUE = 0;
    private static final String DELIM = "/";

    public Document getDocument(File page) throws IOException {
        return of(parse(page, CHARSET, page.getAbsolutePath())).orElseThrow(IOException::new);
    }

    public Element findCoreElement(Element element) {
        return element.getElementById(BUTTON_ID);
    }

    public Element compareElements(Document document, Element element) {
        List<Element> allDocumentElements = getAllDocumentEntries(document, element);
        Map<Element, Integer> allSameElements = new HashMap<>();
        for (Element elem : allDocumentElements) {
            allSameElements.putAll(getAllSameButtonsWithNumberOfEqualFields(elem, element));
        }
        Map.Entry<Element, Integer> maxEntry = max(allSameElements.entrySet(), comparing(Map.Entry::getValue));
        return maxEntry.getKey();
    }

    public Map<Element, Integer> getAllSameButtonsWithNumberOfEqualFields(Element docElement, Element searchElement) {
        Map<Element, Integer> sameElement = new HashMap<>();
        List<Attribute> documentAttributes = docElement.attributes().asList();
        List<Attribute> searchElementAttributes = searchElement.attributes().asList();
        putSameElementsWithSizeOfEqualFields(docElement, sameElement, documentAttributes, searchElementAttributes);
        return sameElement;
    }

    public StringBuilder getPath(List<Element> elements, StringBuilder stringBuilder) {
        for (Element el : elements) {
            stringBuilder.append(el.tagName());
            stringBuilder.append(DELIM);
            return getPath(el.children(), stringBuilder);
        }
        return stringBuilder;
    }

    private List<Element> getAllDocumentEntries(Document document, Element element) {
        return document.getAllElements().stream()
                .filter(doc -> doc.tagName().equalsIgnoreCase(element.tagName()))
                .collect(toList());
    }

    private void putSameElementsWithSizeOfEqualFields(Element docElement, Map<Element, Integer> sameElement, List<Attribute> attributes1, List<Attribute> attributes2) {
        List<Attribute> list = attributes1.stream().filter(attributes2::contains).collect(toList());
        if (list.size() > ZERO_VALUE) {
            sameElement.put(docElement, list.size());
        }
    }
}
