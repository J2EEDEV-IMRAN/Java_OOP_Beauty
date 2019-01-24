package me.ataur.bdlaws.util;




import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;

/**
 * Created by ataur on 10/26/17.
 */
public class DocxToXhtmlAndBack{

    //static String dir = "/home/ataur/";
    static String dir = "/E:/imran/";
    static String inputfilepath = "sample.docx";

    private static final Logger logger = LoggerFactory.getLogger(DocxToXhtmlAndBack.class);

    public static void main(String[] args)
    {
        DocxToXhtmlAndBack d=new DocxToXhtmlAndBack();

        try {
            d.toDoc();
        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public void doConvert()
            throws Exception {


        Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);


        System.out.println(dir+inputfilepath);
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File(dir+inputfilepath));

        // XHTML export
        //AbstractHtmlExporter exporter = new HtmlExporterNG2();
        //AbstractHtmlExporter.HtmlSettings htmlSettings = new AbstractHtmlExporter.HtmlSettings();
        HTMLSettings htmlSettings = new HTMLSettings();
        htmlSettings.setWmlPackage(wordMLPackage);

        htmlSettings.setImageDirPath(dir + inputfilepath + "_files");
        htmlSettings.setImageTargetUri(dir + inputfilepath + "_files");

        String htmlFilePath = dir + "/DocxToXhtmlAndBack.html";
        System.out.println(htmlFilePath);
        OutputStream os = new java.io.FileOutputStream(htmlFilePath);

//		javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(os);
//		exporter.html(wordMLPackage, result, htmlSettings);
//		os.flush();
//		os.close();


        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_NONE);


        // XHTML to docx
//        String stringFromFile = FileUtils.readFileToString(new File(htmlFilePath), "UTF-8");
//
//
//        WordprocessingMLPackage docxOut = WordprocessingMLPackage.createPackage();
//        NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
//        docxOut.getMainDocumentPart().addTargetPart(ndp);
//        ndp.unmarshalDefaultNumbering();
//
//        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(docxOut);
//        XHTMLImporter.setHyperlinkStyle("Hyperlink");
//
//        docxOut.getMainDocumentPart().getContent().addAll(
//                XHTMLImporter.convert(stringFromFile, null) );
//
//        docxOut.save(new java.io.File(dir + "/DocxToXhtmlAndBack.docx") );

    }
    public void toDoc() throws Docx4JException, IOException, JAXBException {
        String htmlFilePath = dir + "Html2Docx.html";
        logger.info("File Path : "+htmlFilePath);
        String stringFromFile = FileUtils.readFileToString(new File(htmlFilePath), "UTF-8");
        logger.info("readFileToString : ",stringFromFile);
        logger.info(new File(htmlFilePath).toString());

        WordprocessingMLPackage docxOut = WordprocessingMLPackage.createPackage();

        NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
        docxOut.getMainDocumentPart().addTargetPart(ndp);
        ndp.unmarshalDefaultNumbering();

        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(docxOut);
        XHTMLImporter.setHyperlinkStyle("Hyperlink");

        docxOut.getMainDocumentPart().getContent().addAll(
                XHTMLImporter.convert(stringFromFile, null) );

        docxOut.save(new java.io.File(dir + "/DocxToXhtmlAndBack.docx") );
    }
}