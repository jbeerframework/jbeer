package jbeer_file_viewer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) throws IOException {
        String temp="bytes=2000070-";
        Pattern pattern = Pattern.compile("[0-9]{1,}");
       Matcher matcher =  pattern.matcher(temp);
       if(matcher.find()){
           System.out.println(matcher.group());
       }
    }

}
