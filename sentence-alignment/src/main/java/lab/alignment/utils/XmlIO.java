/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.utils;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import lab.alignment.configs.Configs;
//import lab.corpora.configs.Configs;

/**
 *
 * @author TRAN
 */
public class XmlIO {

//    public static <T> void writeObjectToXml(T obj, File xmlFile) throws IOException, JAXBException {
//        FileOutputStream fos = new FileOutputStream(xmlFile);
//        writeObjectToXml(obj, fos);
//        fos.close();
//    }
    public static <T> void writeObjectToXml(T obj, File xmlFile) throws IOException, JAXBException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(xmlFile), Configs.ENCODING));
//        writer.write(ConfigsMonoTED.XML_HEADER);
//        writer.write(System.lineSeparator());
        writeObjectToXml(obj, writer);
        writer.close();
    }

    public static <T> void writeObjectToXml_Ansi(T obj, File xmlFile) throws IOException, JAXBException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(xmlFile)));
//        writer.write(ConfigsMonoTED.XML_HEADER);
//        writer.write(System.lineSeparator());
        writeObjectToXml(obj, writer);
        writer.close();
    }

    public static <T> void writeObjectToXml(T obj, OutputStream xmlStream) throws IOException, JAXBException {
        OutputStreamWriter osw = new OutputStreamWriter(xmlStream);
        writeObjectToXml(obj, osw);
        osw.flush();
    }

    public static <T> void writeObjectToXml(T obj, Writer xmlWriter) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//         marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\"?>");

//        marshaller.setProperty("com.sun.xml.internal.bind.characterEscapeHandler",
//                new CharacterEscapeHandler() {
//            @Override
//            public void escape(char[] ch, int start, int length,
//                    boolean isAttVal, Writer writer)
//                    throws IOException {
//                writer.write(ch, start, length);
//            }
//        });

        marshaller.marshal(obj, xmlWriter);

//        marshaller.setProperty("javax.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//        marshaller.setProperty(javax.xml.bind.Mar, obj);
    }

    public static <T> T readObjectFromXML(Class<T> clazz, File xmlFile) throws IOException, JAXBException {
        //InputStream is = null;
        BufferedReader reader = null;
        try {
//            is = new FileInputStream(xmlFile);
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFile), Configs.ENCODING));
//            return readObjectFromXML(clazz, is);
            return readObjectFromXML(clazz, reader);

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static <T> T readObjectFromXML_Ansi(Class<T> clazz, File xmlFile) throws IOException, JAXBException {
        //InputStream is = null;
        BufferedReader reader = null;
        try {
//            is = new FileInputStream(xmlFile);
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFile)));
//            return readObjectFromXML(clazz, is);
            return readObjectFromXML(clazz, reader);

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static <T> T readObjectFromXML(Class<T> clazz, InputStream xmlInputStream) throws IOException, JAXBException {
        try {
            return readObjectFromXML(clazz, new InputStreamReader(xmlInputStream, "utf-8"));
        } catch (JAXBException ex) {
            if (ex.getMessage().contains("Invalid encoding name")) {
                Logger.getLogger(XmlIO.class.getName()).log(Level.SEVERE, "", ex);
                try {
                    return readObjectFromXML(clazz, new InputStreamReader(xmlInputStream, "utf-8"));
                } catch (JAXBException ex2) {
                    Logger.getLogger(XmlIO.class.getName()).log(Level.SEVERE, "", ex2);
                    throw ex;
                }

            }
            throw ex;
        }
    }

    public static <T> T readObjectFromXML(Class<T> clazz, Reader xmlReader) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
//        unmarshaller.setProperty("com.sun.xml.internal.bind.characterEscapeHandler",

//            new CharacterEscapeHandler() {
//                @Override
//                public void escape
//                (char[] ch, int start, int length,
//                boolean isAttVal, Reader reader)
//                    throws IOException {
//                    reader.write(ch, start, length);
//                }
//            }
//            );
        return (T) unmarshaller.unmarshal(xmlReader);
    }
}
