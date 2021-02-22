package kz.edu.nu.cs.se;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyLanguageParser {
    private List<String> tokens;

    private static final String SEMICOLON = ";";
    private static final String COMMA = ",";
    private static final String OPEN_PAREN = "(";
    private static final String CLOS_PAREN = ")";

    private static final String HTML_TOP = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><link rel=\"" +
            "stylesheet\" type=\"text/css\" href=\"style.css\"></head><body><div>\n";
    private static final String HTML_BOTTOM = "</div></body></html>";

    public MyLanguageParser(String input) {
        StringReader stringReader = new StringReader(input);
        StreamTokenizer tokenizer = new StreamTokenizer(stringReader);
        this.tokens = new ArrayList<>();

        try {
            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                if (tokenizer.ttype == 40) {
                    this.tokens.add(OPEN_PAREN);
                } else if (tokenizer.ttype == 41) {
                    this.tokens.add(CLOS_PAREN);
                } else if (tokenizer.ttype == 59) {
                    this.tokens.add(SEMICOLON);
                } else if (tokenizer.ttype == 44) {
                    this.tokens.add(COMMA);
                } else if (tokenizer.sval != null){
                    this.tokens.add(tokenizer.sval);
                } else {
                    Double num = tokenizer.nval;
                    this.tokens.add(num.toString());
                }
            }
        } catch (IOException exception) {
            System.out.println("Failed tokenizing input.");
        }

    }

    public static void main(String[] args) {
        // to check getHTML method, tokens from sampledefinition.txt were taken as an example. Generated file is Aiya'sParser.html.
        String input = "a;b;c;d;e;\n();\n(());\n((()));\n((),(),());\n(a,b,c,d,e,f,(g,h,i,j,(k,l,(m,n),o,p)" +
                ",q,r,s,t,u,v),w,x,y,z);\n(a,b);\n(a,b,(u,v));\n(alice,(bob,(claire,dennis)));\n(((alice,bob),claire),dennis);\n" +
                "((alice,2.0),(),(),(())); \n((a,1),(b,2),(c,3),(d,4));\nAlice;\nBob;\nClaire;";

        MyLanguageParser languageParser = new MyLanguageParser(input);
        languageParser.getHTML();
    }

    public boolean program() {
        return start(0);
    }

    private boolean isInRange(int left, int right) {
        return left >= 0 && left < tokens.size() &&
                right >= 0 && right < tokens.size() &&
                left <= right;
    }

    private boolean isTerminal(int left, int right, String token) {
        return isInRange(left, right) && left == right &&
                !(token.equals(COMMA) || token.equals(OPEN_PAREN) || token.equals(CLOS_PAREN));
    }

    private int endOfStatement(int start) {
        for (int i = start; i < tokens.size(); i++)
            if (tokens.get(i).equals(SEMICOLON)) return i;
        return -1;
    }

    private int nextComma(int start, int limit) {
        for (int i = start; i <= limit; i++)
            if (tokens.get(i).equals(COMMA)) return i;
        return limit + 1;
    }

    private boolean start(int start) {
        int end = endOfStatement(start);

        return (subprogram(start, end - 1) && end + 1 == tokens.size()) ||
                (subprogram(start, end - 1) && start(end + 1));
    }

    private boolean subprogram(int left, int right) {
        return isTerminal(left, right, tokens.get(left)) || isList(left, right);
    }

    private boolean isList(int left, int right) {
        if (!isInRange(left, right)) return false;

        if (left + 1 == right) {
            return tokens.get(left).equals(OPEN_PAREN) && tokens.get(right).equals(CLOS_PAREN);
        }

        return element(left + 1, right - 1);
    }

    private boolean element(int left, int right) {
        if (!isInRange(left, right)) return false;

        int comma = nextComma(left, right);
        boolean checkSecondCase = false;

        while (comma <= right) {
            checkSecondCase |= (value(left, comma - 1) && element(comma + 1, right));
            comma = nextComma(comma + 1, right);
            if (checkSecondCase) break;
        }

        return value(left, right) || checkSecondCase;
    }

    private boolean value(int left, int right) {
        if (!isInRange(left, right)) return false;
        return isList(left, right) || isTerminal(left, right, tokens.get(left));
    }

    public void getHTML() {
        StringBuilder htmlString = new StringBuilder(HTML_TOP);

        if (!program()) {
            htmlString.append("\n<br>Input string is not valid.");
        } else {
            Integer depth = -1;
            for (String token : tokens) {
                if (token.equals(SEMICOLON)) {
                    htmlString.append(";\n<br>");
                } else if (token.equals(OPEN_PAREN)) {
                    depth++;
                    htmlString.append(String.format("(<span class=\"lv%d\">", depth));
                } else if (token.equals(CLOS_PAREN)) {
                    depth--;
                    htmlString.append("</span>)");
                } else if (token.equals(COMMA)) {
                    htmlString.append(COMMA);
                } else {
                    htmlString.append(String.format("<i>%s</i>", token));
                }
            }
        }
        htmlString.append(HTML_BOTTOM);
        try {
            createHTMLFile(htmlString.toString(), "Aiya'sParser.html");
        } catch (IOException exception) {
            System.out.println("Failed getting HTML file.");
        }
    }

    public static void createHTMLFile(String fileContent, String fileName) throws IOException {
        String path = System.getProperty("user.dir");
        String filePath = path + File.separator + fileName;
        File file = new File(filePath);

        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(fileContent);
        writer.close();
    }
}
