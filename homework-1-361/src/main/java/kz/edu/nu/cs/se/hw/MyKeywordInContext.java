package kz.edu.nu.cs.se.hw;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Comparator;

public class MyKeywordInContext implements KeywordInContext {

    private String name;
    private String pathstring;
    private String[] stopWords;
    private ArrayList<String> kwicLines;
    private ArrayList<Indexable> kwicIndexes;

    public MyKeywordInContext(String name, String pathstring) {
        this.name = name;
        this.pathstring = pathstring;
        this.kwicLines = new ArrayList<String>();
        this.kwicIndexes = new ArrayList<Indexable>();
        this.stopWords = new String[413];
    }

    @Override
    public int find(String word) {
        for (Indexable index : this.kwicIndexes) {
            if (index.getEntry().toLowerCase().compareToIgnoreCase(word) == 0) return index.getLineNumber();
        }
        return -1;
    }

    @Override
    public Indexable get(int i) {
        for (Indexable index : this.kwicIndexes) {
            if (index.getLineNumber() == i) return index;
        }
        return null;
    }

    @Override
    public void txt2html() {
        try {

            InputStream fstream = new FileInputStream(this.pathstring);
            BufferedReader txt = new BufferedReader(new InputStreamReader(fstream));
            OutputStream html = new FileOutputStream(this.name + ".html");

            String FileData = "";

            String htmlHeader = "<!DOCTYPE html>\n<html><head><meta charset=\"UTF-8\">";
            htmlHeader += "</head><body>\n";
            htmlHeader += "<div>\n";
            String htmlFooter = "</div></body></html>";
            int lineNumber = 0;

            html.write(htmlHeader.getBytes());
            while ((FileData = txt.readLine()) != null) {
                html.write(FileData.getBytes());
                lineNumber++;
                String end = "<span id=\"line_"+lineNumber+"\">&nbsp&nbsp["+lineNumber+"]</span><br>";
                html.write(end.getBytes());
                html.write("\n".getBytes());
            }
            html.write(htmlFooter.getBytes());
            html.close();
            txt.close();

        }

        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void indexLines() {
        try{

            FileInputStream fstream = new FileInputStream(this.pathstring);
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(fstream));
            BufferedReader stopWordsTxt = new BufferedReader(new FileReader("stopwords.txt"));

            int lineNumber = 0;
            String stopWordsData = "";
            while ((stopWordsData = stopWordsTxt.readLine()) != null) {
                this.stopWords[lineNumber] = stopWordsData;
                lineNumber++;
            }
            stopWordsTxt.close();

            String FileData;
            lineNumber = 0;
            while ((FileData = bufReader.readLine()) != null) {
                lineNumber++;
                this.kwicLines.add(FileData);

                Pattern word_pattern = Pattern.compile("\\b[a-zA-Z]+\\b");
                Matcher matcher = word_pattern.matcher(FileData);

                while(matcher.find()) {
                    String word = FileData.substring(matcher.start(), matcher.end());
                    if(Arrays.asList(this.stopWords).contains(word.toLowerCase()))
                        continue;
                    Indexable indexedWord = new MyIndexable(word, lineNumber);
                    if(this.kwicIndexes.contains(indexedWord)) continue;
                    this.kwicIndexes.add(indexedWord);
                }
            }

            this.kwicIndexes.sort(new Comparator<Indexable>() {
                @Override
                public int compare(Indexable index1, Indexable index2) {
                    return index1.getEntry().toLowerCase().compareToIgnoreCase(index2.getEntry());
                }
            });
            fstream.close();
            bufReader.close();

        }

        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void writeIndexToFile() {
        try {
            FileOutputStream fstream = new FileOutputStream("kwic-"+this.name + ".html");
            String htmlHeader = "<!DOCTYPE html>\n";
            htmlHeader += "<html><head><meta charset=\"UTF-8\"></head><body>\n";
            htmlHeader += "<div style=\"text-align:center;line-height:1.6\">\n";
            String htmlFooter = "</div></body></html>";
            fstream.write(htmlHeader.getBytes());

            for (Indexable index : this.kwicIndexes) {
                String lineToWrite = this.kwicLines.get(index.getLineNumber()-1);

                Pattern pattern = Pattern.compile(index.getEntry());
                Matcher matcher = pattern.matcher(lineToWrite);

                while(matcher.find()){
                    fstream.write(
                            (lineToWrite.substring(0, matcher.start())
                                    +"<a href=\""+this.name+".html#line_" + index.getLineNumber()+"\">" + index.getEntry().toUpperCase()+"</a> "
                                    +lineToWrite.substring(matcher.end())
                            ).getBytes());
                    fstream.write(("<br>").getBytes());
                    fstream.write("\n".getBytes());
                }

            }
            fstream.write(htmlFooter.getBytes());
            fstream.close();
        }

        catch (Exception e) {
            System.out.println(e);
        }
    }

}
