package com.amazter.service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFService {
    public static boolean createPDF(String filename, String text) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            document.add(new Paragraph(text));
            document.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean mergePDfFiles(String destFileName, String ... pdfFiles) {
        PDFMergerUtility ut = new PDFMergerUtility();
        for(String pdfFile: pdfFiles) {
            try {
                ut.addSource(pdfFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ut.setDestinationFileName(destFileName);
        try {
            ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}

