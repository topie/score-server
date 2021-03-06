package com.orange.score;

import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class Docx2Html {

    public static void canExtractImage() throws IOException {
        File f = new File("/Users/chenguojun/Downloads/approve.docx");
        if (!f.exists()) {
            System.out.println("Sorry File does not Exists!");
        } else {
            if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {

                InputStream in = new FileInputStream(f);
                XWPFDocument document = new XWPFDocument(in);
                XHTMLOptions options = XHTMLOptions.create();
                OutputStream out = new FileOutputStream(new File("/Users/chenguojun/Downloads/approve.html"));
                XHTMLConverter.getInstance().convert(document, out, options);
            } else {
                System.out.println("Enter only MS Office 2007+ files");
            }
        }
    }

    public static void main(String args[]) {
        try {
            //canExtractImage();
            System.out.println("9".length());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
