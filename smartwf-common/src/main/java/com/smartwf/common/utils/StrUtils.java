package com.smartwf.common.utils;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class StrUtils {
	 /**
     * @ 去除前后逗号
     * @param clazz
     * @param xmlStr
     * @return
     */
    public static String regex(String str) {
    	String regex = "^,*|,*$";
    	return str.replaceAll(regex, "");
    }

    /**
     * @对象转xml字符串
     * @param obj
     * @param load
     * @return
     * @throws JAXBException
     */
    public static String objectToXmlStr(Object obj,Class<?> load){
        String result = "";
        try{
            JAXBContext context = JAXBContext.newInstance(load);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj,writer);
            result = writer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
   
}
