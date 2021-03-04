package com.o4codes.helpers;

import com.o4codes.models.Token;

import java.io.*;
import java.util.ArrayList;

public class Util {
    // method to tokenize the strings in a text
    public static ArrayList<Token> tokenizeString(String sentence, File file) throws IOException {
        ArrayList<Token> tokenCollection = new ArrayList<>();
        StreamTokenizer streamTokenizer = file == null ? new StreamTokenizer(new StringReader(sentence)) : new StreamTokenizer(new FileReader(file));
        // change recognition of important identifiers in JAVA
        streamTokenizer.ordinaryChar('/');
        streamTokenizer.ordinaryChar('-');
        streamTokenizer.ordinaryChar('\'');
        streamTokenizer.ordinaryChar('\"');
        streamTokenizer.ordinaryChar('.');

        int currentToken = streamTokenizer.nextToken(); // get present token location
        while (currentToken != StreamTokenizer.TT_EOF) {
            if (streamTokenizer.ttype == StreamTokenizer.TT_NUMBER) { // check if token is a number
                tokenCollection.add(new Token(String.valueOf(streamTokenizer.nval), tokenType.Number));
            } else if (streamTokenizer.ttype == StreamTokenizer.TT_WORD) { // check if token is a word
                tokenCollection.add(new Token(streamTokenizer.sval, tokenType.Word));
            } else {
                tokenCollection.add(new Token(String.valueOf((char) currentToken), tokenType.Symbol));
            }
            currentToken = streamTokenizer.nextToken();
        }
        return tokenCollection;
    }

    // method to write output of lexeme and tokens to file
    public static void writeOutputToFile(ArrayList<Token> tokenCollection, File file) throws IOException {
        /** two kinds of files will be created
         1. create a file with not described tokens (tokenized.txt)
         2. create a file with described tokens (lexemes.txt)
         */
        File tokenizedFile = new File(file.getParent() + Constants.tokenizedFileName);
        File lexemeFile = new File(file.getParent() + Constants.lexemeFileName);

        if (tokenizedFile.createNewFile()) {
            System.out.println("File is created");
        }
        if (lexemeFile.createNewFile()) {
            System.out.println("File is created");
        }
        System.out.println(tokenizedFile.getPath());
        PrintWriter tokenizedWriter = new PrintWriter(tokenizedFile);
        PrintWriter lexemeWriter = new PrintWriter(lexemeFile);
        for (Token token : tokenCollection) {
            String content = token.getToken() + " ";
            tokenizedWriter.write(content);
            String lexemeContent = token.getTokenType() + " : " + token.getToken() + "\n";
            lexemeWriter.write(lexemeContent);
        }
        tokenizedWriter.close();
        lexemeWriter.close();
    }

    // enumerate the different types of token types
    public enum tokenType {
        Number,
        Word,
        Symbol
    }

}
