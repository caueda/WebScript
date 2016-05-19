
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


/**
 * @author Rafael Hancke
 * 
 * Classe utilizada para leitura da estrutura do menu definida em arquivo XML e
 * convers�o da mesma para fun��es javascript as quais ser�o interpretadas pelo
 * Client-Side ap�s a autentica��o do usu�rio no sistema.
 */ 

public class MenuXMLReader {
    /**
     * Ip do usu�rio
     */
	private String ip;


	/**
	 * Caminho do arquivo xml.
	 */
	private String caminhoArquivo;

	private Map mapItens = null; 
	/**
	 * Constantes definidas para os elementos de menu. 
	 */
	private final String ELEMENTO_MENUBAR = "MenuBar", ELEMENTO_MENU = "Menu",
			ELEMENTO_ITEM_MENU = "ItemMenu", ELEMENTO_SUBMENU = "SubMenu";

	/**
	 * Quantidade de divs onde ser�o exibidos os submenus.
	 */
	public static final String TOTAL_NIVEIS = "5";

	/**
	 * Atributo para manipula��o e armazenamento do resultado final gerado pela leitura do xml. 
	 */
	private StringBuffer estruturaMenuJS;

	/**
	 * Lista contendo os objetos de menu.
	 */
	private ArrayList listaObjetosAux;

	/**
	 * Mapa contendo toda a estrutura do menu.
	 */
	private Map mapEstruturaFinalMenu;

	/**
	 * Construtor para inicializar os atributos
	 * 
	 * @param caminhoArquivo
	 * @param usuario
	 * @param ip
	 */
	public MenuXMLReader(String caminhoArquivo, String ip) {
		this.caminhoArquivo = caminhoArquivo;
		this.estruturaMenuJS = new StringBuffer();
		this.listaObjetosAux = new ArrayList();
		this.mapEstruturaFinalMenu = new HashMap();
		this.ip = ip;
	}

	/**
	 * M�todo utilizado para ler o arquivo xml, e iniciar a leitura e montagem
	 * dos objetos.
	 * 
	 * @throws Exception
	 */
	public void lerDocumentoXML() throws Exception {
		try {
			Node doc = MontaMenu.carregar();
			encontrarNo(doc, null);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * M�todo utilizado atrav�s de recursividade, respons�vel em percorrer todos
	 * os itens filho de determinado Menu e SubMenu.
	 * 
	 * @param no
	 * @param parent
	 * @throws Exception
	 */
	public void encontrarNo(Node no, Menu parent) throws Exception {

		String tipoObjeto = no.getNodeName();
		Menu objetoMenu = null;

		if (no instanceof Element && !tipoObjeto.equals(ELEMENTO_MENUBAR) && !tipoObjeto.equals("#document")) {

			// Criar o objeto de menu (Menu, SubMenu ou ItemMenu)
			Element tagElemento = (Element) no;
			objetoMenu = criarObjetoMenu(tagElemento, tipoObjeto);

			if (parent != null) {
				if (objetoMenu.getType().equals(ELEMENTO_ITEM_MENU)) {
					Element noPai = (Element) no.getParentNode();
					parent = getObjetoMenu(noPai.getAttribute("parentId"),
							noPai.getNodeName());
				}
				parent.adicionarItem(objetoMenu, ip, mapItens);
			}
				objetoMenu.setItemPai(parent);
			listaObjetosAux.add(objetoMenu);
		}

		// Verificar se o n� possue filhos
		if (no.hasChildNodes()) {
			Node primeiroFilho = no.getFirstChild();
			encontrarNo(primeiroFilho, objetoMenu);
		}

		// Carregar pr�ximo n�
		Node proximoNo = no.getNextSibling();

		if (proximoNo != null) {
			encontrarNo(proximoNo, parent);
		}

		// Verificar se � final do documento
		if (no instanceof Document) {
			// Carregar todos os itens Topo do menu
			ArrayList itensTopo = getItensMenu(ELEMENTO_MENU);

			if (itensTopo.size() > 0) {
				Iterator itToposMenu = itensTopo.iterator();

				while (itToposMenu.hasNext()) {
					Menu itemMenu = (Menu) itToposMenu.next();

					ordenarEstrutura(itemMenu);
					montarSubMenu(itemMenu);

					Iterator itSubMenus = mapEstruturaFinalMenu.keySet()
							.iterator();

					while (itSubMenus.hasNext()) {
						String id = (String) itSubMenus.next();
						Menu itemSubMenu = getObjetoMenu(id, ELEMENTO_SUBMENU);
						montarSubMenu(itemSubMenu);
					}
					mapEstruturaFinalMenu.clear();
				}
			}
		}
	}

	/**
	 * M�todo utilizado para escrever a estrutura com instru��es javascript para
	 * a montagem de itens Topo ou SubMenu.
	 * 
	 * @param itemMenu
	 */
	public void montarSubMenu(Menu itemMenu) {
		String idSubMenu = itemMenu.getId();

		if (mapEstruturaFinalMenu.containsKey(idSubMenu)) {
			// Caso o seja um item do tipo MENU
			if (itemMenu.getType().equals(ELEMENTO_MENU)) {
				estruturaMenuJS.append("\n<li>");				
				
				idSubMenu = itemMenu.getId().replaceAll("menu", "topo");
				estruturaMenuJS.append(itemMenu.getLabel());

				montarSubMenu(getObjetoMenu(itemMenu.getId(), ELEMENTO_SUBMENU));
				
				estruturaMenuJS.append("</li>");

				// Remover do map, a chave referente ao topo
				mapEstruturaFinalMenu.remove(idSubMenu);
				mapEstruturaFinalMenu.remove(itemMenu.getId());
			} else {
				List<Menu> listaItensSubMenu = ((List) mapEstruturaFinalMenu.get(idSubMenu));
				if(itemMenu.getType().equals(ELEMENTO_SUBMENU)) {
					estruturaMenuJS.append("<ul>");					
				}
				for (int i=0; i<listaItensSubMenu.size(); i++) {
					Menu item = listaItensSubMenu.get(i);
					if(item.getType().equals(ELEMENTO_ITEM_MENU)) {
						if(item.getItens() == null || item.getItens().isEmpty()) {
							estruturaMenuJS.append("<li>").append("<a href=\"javascript:alert('" + item.getLabel() + "');\">" + item.getLabel() + "</a>" );	
						} else {
							estruturaMenuJS.append("<li>").append(item.getLabel());
						}
					}				
					Menu prox = getObjetoMenu(item.getId(), ELEMENTO_SUBMENU);
					if(prox != null) montarSubMenu(prox);
					estruturaMenuJS.append("</li>");
				}
				if(itemMenu.getType().equals(ELEMENTO_SUBMENU)) {
					estruturaMenuJS.append("</ul>");					
				}
			}
		}
	}

	/**
	 * M�todo utilizado para ordenar os objetos carregados ap�s a leitura do
	 * xml, de modo a facilitar a gera��o das instru��es javascript.
	 * 
	 * @param objetoMenu
	 */
	public void ordenarEstrutura(Menu objetoMenu) {
		List itens = objetoMenu.getItens();
		int qtdeItens = itens.size();

		if (qtdeItens > 0) {
			for (int i = 0; i < qtdeItens; i++) {
				Menu subItem = (Menu) itens.get(i);

				ordenarEstrutura(subItem);

				String idItemPai = ((objetoMenu.getItemPai() == null) ? null
						: objetoMenu.getItemPai().getId());
				String idItemPaiFilho = subItem.getItemPai().getId();

				List itensFilho = new ArrayList();
				if (mapEstruturaFinalMenu.containsKey(idItemPai)) {
					Iterator iter = ((List) mapEstruturaFinalMenu
							.get(idItemPai)).iterator();

					while (iter.hasNext()) {
						itensFilho.add(iter.next());
					}
				}

				boolean adicionarItem = false;
				if (idItemPai != null && idItemPai.equals(idItemPaiFilho)) {
					adicionarItem = true;
				} else if (idItemPai == null) {
					adicionarItem = true;
					idItemPai = idItemPaiFilho.replaceAll("menu", "topo");
				}

				if (adicionarItem) {
					if (subItem.getId().startsWith("item")
							|| (subItem.getId().startsWith("menu") && mapEstruturaFinalMenu
									.containsKey(subItem.getId()))) {
						itensFilho.add(subItem);
					}
				}

				if (itensFilho.size() > 0) {
					mapEstruturaFinalMenu.put(idItemPai, itensFilho);
				}
			}
		}
	}

	/**
	 * M�todo utilizado para criar objetos que comp�em o menu. 
	 * Tipos de objeto: Menu, SubMenu e ItemMenu.
	 * 
	 * @param tagElemento
	 * @param tipoObjeto
	 * @return Menu
	 */
	public Menu criarObjetoMenu(Element tagElemento, String tipoObjeto) {
		String attId = null, attLabel = null, attWidth = null, attUrl = null, attNivelDivExibir = null, attNivelDivEsconder = null, attPopup = null;

		Menu objetoMenu = null;

		if (tipoObjeto.equals(ELEMENTO_MENU)) {
			attId = tagElemento.getAttribute("id");
			attLabel = tagElemento.getAttribute("label");
			attWidth = tagElemento.getAttribute("width");

			objetoMenu = new Menu(attId, attLabel, attWidth, tipoObjeto);
		} else if (tipoObjeto.equals(ELEMENTO_ITEM_MENU)) {
			attId = tagElemento.getAttribute("id");
			attLabel = tagElemento.getAttribute("label");
			attUrl = tagElemento.getAttribute("url");

			attNivelDivExibir = (attId.startsWith("item")) ? "0" : tagElemento
					.getAttribute("nivelDivExibir");
			attNivelDivEsconder = (attId.startsWith("item")) ? tagElemento
					.getAttribute("nivelDivEsconder") : "#";

			if (tagElemento.hasAttribute("tipoPopup")) {
				attPopup = tagElemento.getAttribute("tipoPopup");
			}

			objetoMenu = new Menu(attId, attLabel, attUrl, attNivelDivExibir,
					attNivelDivEsconder, attPopup, tipoObjeto);
		} else if (tipoObjeto.equals(ELEMENTO_SUBMENU)) {
			attId = tagElemento.getAttribute("parentId");

			objetoMenu = new Menu(attId, tipoObjeto);
		}

		return objetoMenu;
	}

	/**
	 * M�todo utilizado para escrever as instru��es javascript conforme o tipo
	 * de objeto carregado.
	 * 
	 * @param objetoMenu
	 * @return StringBuffer
	 */
//	public StringBuffer escreverFuncaoJS(Menu objetoMenu) {
//		StringBuffer estrutura = new StringBuffer();
//
//		if (objetoMenu != null) {
//			String tipoObjeto = objetoMenu.getType();
//
//			if (tipoObjeto.equals(ELEMENTO_MENU)) {
//				String id = objetoMenu.getId().replaceAll("menu", "topo");
//				estrutura.append("\n\tescreverTopoMenu('" + id + "',")
//						.append("'" + objetoMenu.getLabel() + "',").append("'"
//								+ objetoMenu.getWidth() + "');");
//			} else if (tipoObjeto.equals(ELEMENTO_ITEM_MENU)) {
//				String parametroPopup = "";
//				if (objetoMenu.getPopup() != null)
//					parametroPopup = ",'" + objetoMenu.getPopup() + "'";
//
//				estrutura.append("\n\t\t\tescreverItemMenu('")
//						.append(objetoMenu.getId() + "','")
//						.append(objetoMenu.getLabel() + "','")
//						.append(objetoMenu.getUrl() + "','")
//						.append(objetoMenu.getNivelDivExibir() + "','")
//						.append(objetoMenu.getNivelDivEsconder() + "'")
//						.append(parametroPopup + ");");
//			} else if (tipoObjeto.equals(ELEMENTO_SUBMENU)) {
//				estrutura.append("\n\t\tabrirMenu('" + objetoMenu.getId()
//						+ "');");
//			}
//		} else {
//			estrutura.append("\n\t\tfecharMenu();");
//		}
//		return estrutura;
//	}
	
	public StringBuffer escreverFuncaoJS(Menu objetoMenu) {
		StringBuffer estrutura = new StringBuffer();

		if (objetoMenu != null) {
			String tipoObjeto = objetoMenu.getType();

			if (tipoObjeto.equals(ELEMENTO_MENU)) {
				estrutura.append(objetoMenu.getLabel()).append("\n");
//				String id = objetoMenu.getId().replaceAll("menu", "topo");
//				estrutura.append("\n\tescreverTopoMenu('" + id + "',")
//						.append("'" + objetoMenu.getLabel() + "',").append("'"
//								+ objetoMenu.getWidth() + "');");
			} else if (tipoObjeto.equals(ELEMENTO_ITEM_MENU)) {
//				String parametroPopup = "";
//				if (objetoMenu.getPopup() != null)
//					parametroPopup = ",'" + objetoMenu.getPopup() + "'";
//
//				estrutura.append("\n\t\t\tescreverItemMenu('")
//						.append(objetoMenu.getId() + "','")
//						.append(objetoMenu.getLabel() + "','")
//						.append(objetoMenu.getUrl() + "','")
//						.append(objetoMenu.getNivelDivExibir() + "','")
//						.append(objetoMenu.getNivelDivEsconder() + "'")
//						.append(parametroPopup + ");");
				estrutura.append("<a href=\"" + objetoMenu.getUrl() + "\">" + objetoMenu.getLabel() + "</a>");
			} else if (tipoObjeto.equals(ELEMENTO_SUBMENU)) {
				estrutura.append("\n\t\t<ul><li>").append(objetoMenu.getLabel()).append("\n");;
//				estrutura.append("\n\t\tabrirMenu('" + objetoMenu.getId()
//						+ "');");
			}
		} else {
			estrutura.append("\n\t\t</li></ul>\n");
//			estrutura.append("\n\t\tfecharMenu();");
		}
		return estrutura;
	}

	/**
	 * M�todo utilizado para retornar todas as instru��es utilizadas para o
	 * funcionamento do menu.
	 * 
	 * @param tipoObjeto
	 * @return ArrayList
	 */
	public ArrayList getItensMenu(String tipoObjeto) {
		ArrayList itens = new ArrayList();
		Iterator iter = listaObjetosAux.iterator();

		while (iter.hasNext()) {
			Menu itemMenu = (Menu) iter.next();

			if (itemMenu.getType().equals(tipoObjeto)) {
				itens.add(itemMenu);
			}
		}
		return itens;
	}

	/**
	 * M�todo utilizado para retornar o objeto referente ao n� pai atrav�s do id
	 * lendo uma lista de objetos.
	 * 
	 * @param id
	 * @param tipoObjeto
	 * @return
	 */
	public Menu getObjetoMenu(String id, String tipoObjeto) {
		Menu itemMenu = null;
		Iterator iter = listaObjetosAux.iterator();

		while (iter.hasNext()) {
			Menu objetoMenu = (Menu) iter.next();

			if (id.equals(objetoMenu.getId())
					&& tipoObjeto.equals(objetoMenu.getType())) {
				itemMenu = objetoMenu;
				break;
			}
		}
		return itemMenu;
	}

	/**
	 * M�todo respons�vel em retornar o resultado final com as instru��es javascript
	 * as quais ser�o interpretadas, carregadas e exibidas para o usu�rio.
	 * 
	 * @return StringBuffer
	 */
	public StringBuffer getEstruturaMenuJS() {
		return estruturaMenuJS;
	}
}