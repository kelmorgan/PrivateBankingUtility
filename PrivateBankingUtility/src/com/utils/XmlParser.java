package com.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class XmlParser {
    private String parseString;
    private String copyString;
    private int IndexOfPrevSrch;
    Document document;

    public XmlParser(String xml) {
        setInputXML(xml);
    }

//    public XmlParser(String parseThisString) {
//        copyString = new String(parseThisString);
//        parseString = toUpperCase(copyString, 0, 0);
//    }

    public void setInputXML(String ParseThisString) {
        if (ParseThisString != null) {
            copyString = new String(ParseThisString);
            parseString = toUpperCase(copyString, 0, 0);
            IndexOfPrevSrch = 0;
        } else {
            parseString = null;
            copyString = null;
            IndexOfPrevSrch = 0;
        }
    }

    public  Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            return builder.parse( new InputSource( new StringReader( xmlStr ) ) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getServiceName() {
        try {
            return new String(copyString.substring(parseString.indexOf(
                    toUpperCase(
                            "<Option>", 0, 0)) +
                            (new String(toUpperCase("<Option>",
                                    0, 0))).length(),
                    parseString.indexOf(toUpperCase(
                            "</Option>", 0, 0))));
        } catch (StringIndexOutOfBoundsException e) {
            throw e;
        }
    }

    public String getServiceName(char chr) {
        try {
            if (chr == 'A') {
                return new String(copyString.substring(parseString.indexOf(
                        "<AdminOption>".toUpperCase()) +
                                (new String("<AdminOption>".
                                        toUpperCase())).length(),
                        parseString.indexOf(
                                "</AdminOption>".toUpperCase())));
            } else {
                return "";
            }
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return "NoServiceFound";
        }
    }

    public boolean validateXML() {
        try {
            return parseString.indexOf("<?xml version=\"1.0\"?>".toUpperCase()) !=
                    -1;
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return false;
        }
    }

    public String getValueOf(String valueOf) {
        try {
            return new String(copyString.substring(parseString.indexOf("<" +
                            toUpperCase(valueOf, 0, 0) + ">") + valueOf.length() + 2,
                    parseString.
                            indexOf("</" +
                                    toUpperCase(valueOf, 0, 0) + ">")));
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return "";
        }
    }

    public String getValueOf(String valueOf, String type) {
        try {
            if (type.equalsIgnoreCase("Binary")) {
                int startPos = copyString.indexOf("<" + valueOf + ">");
                if (startPos == -1) {
                    return "";
                } else {
                    int endPos = copyString.lastIndexOf("</" + valueOf + ">");
                    startPos += (new String("<" + valueOf + ">")).length();
                    return copyString.substring(startPos, endPos);
                }
            } else {
                return "";
            }
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return "";
        }
    }

    public String getValueOf(String valueOf, boolean fromlast) {
        try {
            if (fromlast) {
                return new String(copyString.substring(parseString.indexOf("<" +
                                toUpperCase(valueOf, 0, 0) + ">") + valueOf.length() +
                                2,
                        parseString.lastIndexOf("</" +
                                toUpperCase(valueOf, 0, 0) +
                                ">")));
            } else {
                return new String(copyString.substring(parseString.indexOf("<" +
                                toUpperCase(valueOf, 0, 0) + ">") + valueOf.length() +
                                2,
                        parseString.indexOf("</" +
                                toUpperCase(valueOf, 0, 0) + ">")));
            }
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return "";
        }
    }

    public String getValueOf(String valueOf, int start, int end) {
        try {
            if (start >= 0) {
                int endIndex = parseString.indexOf("</" +
                        toUpperCase(valueOf, 0, 0) +
                        ">", start);
                if (endIndex > start && (end == 0 || end >= endIndex)) {
                    return new String(copyString.substring(parseString.indexOf(
                            "<" +
                                    toUpperCase(valueOf, 0, 0) + ">", start) +
                                    valueOf.length() + 2,
                            endIndex));
                }
            }
            return "";
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return "";
        }
    }

    public int getStartIndex(String tag, int start, int end) {
        try {
            if (start >= 0) {
                int startIndex = parseString.indexOf("<" +
                                toUpperCase(tag, 0, 0) + ">",
                        start);
                if (startIndex >= start && (end == 0 || end >= startIndex)) {
                    return startIndex + tag.length() + 2;
                }
            }
            return -1;
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return -1;
        }
    }

    public int getEndIndex(String tag, int start, int end) {
        try {
            if (start >= 0) {
                int endIndex = parseString.indexOf("</" + toUpperCase(tag, 0, 0) +
                                ">",
                        start);
                if (endIndex > start && (end == 0 || end >= endIndex)) {
                    return endIndex;
                }
            }
            return -1;
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return -1;
        }
    }

    public int getTagStartIndex(String tag, int start, int end) {
        try {
            if (start >= 0) {
                int startIndex = parseString.indexOf("<" +
                                toUpperCase(tag, 0, 0) + ">",
                        start);
                if (startIndex >= start && (end == 0 || end >= startIndex)) {
                    return startIndex;
                }
            }
            return -1;
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return -1;
        }
    }

    public int getTagEndIndex(String tag, int start, int end) {
        try {
            if (start >= 0) {
                int endIndex = parseString.indexOf("</" + toUpperCase(tag, 0, 0) +
                                ">",
                        start);
                if (endIndex > start && (end == 0 || end >= endIndex)) {
                    return endIndex + tag.length() + 3;
                }
            }
            return -1;
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return -1;
        }
    }

    public String getFirstValueOf(String valueOf) {
        try {
            IndexOfPrevSrch = parseString.indexOf("<" +
                    toUpperCase(valueOf, 0, 0) +
                    ">");
            return new String(copyString.substring(IndexOfPrevSrch +
                            valueOf.length() +
                            2,
                    parseString.indexOf("</" +
                            toUpperCase(valueOf, 0, 0) + ">")));
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return "";
        }
    }

    public String getFirstValueOf(String valueOf, int start) {
        try {
            IndexOfPrevSrch = parseString.indexOf("<" + toUpperCase(valueOf, 0, 0) + ">", start);
            return new String(copyString.substring(IndexOfPrevSrch + valueOf.length() + 2, parseString.indexOf("</" + toUpperCase(valueOf, 0, 0) + ">", start)));
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return "";
        }
    }

    public String getNextValueOf(String valueOf) {
        try {
            IndexOfPrevSrch = parseString.indexOf("<" +
                            toUpperCase(valueOf, 0, 0) +
                            ">",
                    IndexOfPrevSrch +
                            valueOf.length() +
                            2);
            return new String(copyString.substring(IndexOfPrevSrch +
                            valueOf.length() +
                            2,
                    parseString.indexOf("</" +
                                    toUpperCase(valueOf, 0, 0) + ">",
                            IndexOfPrevSrch)));
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return "";
        }
    }

    public int getNoOfFields(String tag) {
        int noOfFields = 0;
        int beginPos = 0;
        try {
            for (tag = toUpperCase(tag, 0, 0) + ">";
                 parseString.indexOf("<" + tag, beginPos) != -1;
                 beginPos += tag.length() + 2) {
                noOfFields++;
                beginPos = parseString.indexOf("</" + tag, beginPos);
                if (beginPos == -1) {
                    break;
                }
            }

        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {}
        return noOfFields;
    }

    public int getNoOfFields(String tag, int startPos, int endPos) {
        int noOfFields = 0;
        int beginPos = startPos;
        try {
            for (tag = toUpperCase(tag, 0, 0) + ">";
                 parseString.indexOf("<" + tag, beginPos) != -1 &&
                         (beginPos < endPos || endPos == 0); ) {
                beginPos = parseString.indexOf("</" + tag, beginPos) +
                        tag.length() + 2;
                if (beginPos != -1 && (beginPos <= endPos || endPos == 0)) {
                    noOfFields++;
                }
            }

        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {}
        return noOfFields;
    }

    public String convertToSQLString(String strName) {
        try {
            for (int count = strName.indexOf("["); count != -1;
                 count = strName.indexOf("[", count + 2)) {
                strName = strName.substring(0, count) + "[[]" +
                        strName.substring(count + 1, strName.length());

            }
        } catch (Exception exception) {}
        try {
            for (int count = strName.indexOf("_"); count != -1;
                 count = strName.indexOf("_", count + 2)) {
                strName = strName.substring(0, count) + "[_]" +
                        strName.substring(count + 1, strName.length());

            }
        } catch (Exception exception1) {}
        try {
            for (int count = strName.indexOf("%"); count != -1;
                 count = strName.indexOf("%", count + 2)) {
                strName = strName.substring(0, count) + "[%]" +
                        strName.substring(count + 1, strName.length());

            }
        } catch (Exception exception2) {}
        strName = strName.replace('?', '_');
        return strName;
    }

    public String getValueOf(String valueOf, String type, int from, int end) {
        try {
            if (type.equalsIgnoreCase("Binary")) {
                int startPos = copyString.indexOf("<" + valueOf + ">", from);
                if (startPos == -1) {
                    return "";
                }
                int endPos = copyString.indexOf("</" + valueOf + ">", from);
                if (endPos > end) {
                    return "";
                } else {
                    startPos += (new String("<" + valueOf + ">")).length();
                    return copyString.substring(startPos, endPos);
                }
            } else {
                return "";
            }
        } catch (StringIndexOutOfBoundsException
                stringindexoutofboundsexception) {
            return "";
        }
    }

    public String toUpperCase(String valueOf, int begin, int end) throws
            StringIndexOutOfBoundsException {
        String returnStr = "";
        try {
            int count = valueOf.length();
            char strChar[] = new char[count];
            valueOf.getChars(0, count, strChar, 0);
            while (count-- > 0) {
                strChar[count] = Character.toUpperCase(strChar[count]);
            }
            returnStr = new String(strChar);
        } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {}
        return returnStr;
    }

    public String changeValue(String ParseString, String TagName,
                              String NewValue) {
        try {
            String ParseStringTmp = ParseString.toUpperCase();
            String StrTag = (new String("<" + TagName + ">")).toUpperCase();
            int StartIndex = ParseStringTmp.indexOf(StrTag) + StrTag.length();
            int EndIndex = ParseStringTmp.indexOf((new String("</" + TagName +
                    ">")).
                    toUpperCase());
            String RetStr = ParseString.substring(0, StartIndex);
            RetStr = RetStr + NewValue + ParseString.substring(EndIndex);
            return RetStr;
        } catch (Exception exception) {
            return "";
        }
    }

    public void changeValue(String TagName, String NewValue) {
        try {
            String StrTag = ("<" + TagName + ">").toUpperCase();
            int StartIndex = parseString.indexOf(StrTag);
            if (StartIndex > -1) {
                StartIndex += StrTag.length();
                int EndIndex = parseString.indexOf(("</" + TagName + ">").
                        toUpperCase());
                String RetStr = copyString.substring(0, StartIndex);
                copyString = RetStr + NewValue + copyString.substring(EndIndex);
            } else {
                int EndIndex = StartIndex = parseString.lastIndexOf("</");
                String RetStr = copyString.substring(0, StartIndex);
                copyString = RetStr + "<" + TagName + ">" + NewValue + "</" +
                        TagName +
                        ">" + copyString.substring(EndIndex);
            }
            parseString = toUpperCase(copyString, 0, 0);
        } catch (Exception exception) {}
    }

    /**
     * This method will take the xml as input and will give the Set of maps
     * equal to number of records
     *
     * @param xmlInput
     * @return
     */
    public  Set<Map<String, String>> getXMLData(String xmlInput, String tag) {

        Document doc = null;
        DocumentBuilder documentbuilder;

        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlInput));
        Set<Map<String, String>> tagSet = new HashSet<Map<String, String>>();
        try {
            documentbuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            doc = documentbuilder.parse(is);

            NodeList list = doc.getElementsByTagName(tag);
            for (int temp = 0; temp < list.getLength(); temp++) {
                Map<String, String> tagMap = new LinkedHashMap<String, String>();
                Node nNode = list.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    NodeList nodeList = eElement.getElementsByTagName("*");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        tagMap.put(
                                nodeList.item(i).getNodeName(),
                                getTagValues(nodeList.item(i).getNodeName(),
                                        eElement));
                    }
                    tagSet.add(tagMap);
                }
            }
        } catch (SAXException ex) {
            ex.getStackTrace();
        } catch (IOException ex) {
            ex.getStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.getStackTrace();
        }
        return tagSet;
    }
    /**This function returns Value of a particular tag.
     * @param tagname name of tag for which value is required.
     * @param elt Element of root type
     */
    public String getTagValues(String tagname, Element elt) {
        NodeList nlList = elt.getElementsByTagName(tagname);
        if (nlList != null && nlList.getLength() > 0) {
            NodeList subList = nlList.item(0).getChildNodes();
            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        return null;
    }

    public String toString() {
        return copyString;
    }

}
