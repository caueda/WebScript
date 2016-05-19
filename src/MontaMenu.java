import java.io.File;

import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;



public class MontaMenu {
	static final String PATH = "/home/caueda/workspace/WebScript/WebContent/WEB-INF/config/FIPLAN.xml";
	Node node;
	
	public static Document carregar() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(PATH));
		
		return doc;
	}
	
	public static void main(String[] args) throws Exception {
		StringBuilder funcoesMenuJS = new StringBuilder();
	    MenuXMLReader leitorMenu = new MenuXMLReader("", "");

	    funcoesMenuJS.append("var menuCarregado = true;\n").append(
	                         "function carregarMenus() { ");
    
		try {
		    leitorMenu.lerDocumentoXML();
		    funcoesMenuJS.append(leitorMenu.getEstruturaMenuJS());
		} catch(Exception e) {
			e.printStackTrace();
		    throw new ServletException(e);
		}
		funcoesMenuJS.append("\n}");
		
		// Função para carregar Menus Fixos ( Sobre e Deslogar )
		funcoesMenuJS.append("\nfunction carregarMenusFixos() {\n").append(
       		    	 	     "}");
		
		System.out.println(funcoesMenuJS.toString());
	}
}
