import java.util.ArrayList;
import java.util.Map;


public class Menu {

	/**
	 * Atributos necessários para a estrutura do menu.
	 */
	private String id, label, width, url, popup, nivelDivExibir,
			nivelDivEsconder, type;

	/**
	 * Repositório para controle de itens de menu e submenus.
	 */
	private ArrayList itens;

	/**
	 * Atributo para referenciar o item pai do menu.
	 */
	private Menu itemPai;

	/**
	 * Construtor para inicializar elementos do tipo ItemMenu.
	 * 
	 * @param id
	 * @param label
	 * @param width
	 * @param type
	 */
	public Menu(String id, String label, String width, String type) {
		this.id = id;
		this.label = label;
		this.width = width;
		this.type = type;
		this.itens = new ArrayList();
	}

	/**
	 * Construtor para inicializar elementos do tipo MenuItem.
	 * 
	 * @param id
	 * @param label
	 * @param url
	 * @param nivelDivExibir
	 * @param nivelDivEsconder
	 * @param popup
	 * @param type
	 */
	public Menu(String id, String label, String url, String nivelDivExibir,
			String nivelDivEsconder, String popup, String type) {
		this.id = id;
		this.label = label;
		this.url = url;
		this.type = type;
		this.popup = popup;
		this.nivelDivExibir = nivelDivExibir;
		this.nivelDivEsconder = nivelDivEsconder;
		this.itens = new ArrayList();
	}

	/**
	 * Construtor para inicializar elementos do tipo SubMenu.
	 * 
	 * @param id
	 * @param type
	 */
	public Menu(String id, String type) {
		this.id = id;
		this.type = type;
		this.itens = new ArrayList();
	}

	/**
	 * Método utilizado para obter o valor do atributo id.
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * Método utilizado para obter o valor do atributo label.
	 * 
	 * @return String
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Método utilizado para obter o valor do atributo url.
	 * 
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Método utilizado para obter o valor do atributo width.
	 * 
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * Método utilizado para obter o valor do atributo nivelDivExibir.
	 * 
	 * @return String
	 */
	public String getNivelDivExibir() {
		return nivelDivExibir;
	}

	/**
	 * Método utilizado para obter o valor do atributo nivelDivEsconder.
	 * 
	 * @return String
	 */
	public String getNivelDivEsconder() {
		return nivelDivEsconder;
	}

	/**
	 * Método utilizado para obter o valor do atributo type.
	 *  
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Método utilizado para obter o valor do atributo popup.
	 * 
	 * @return String
	 */
	public String getPopup() {
		return popup;
	}

	/**
	 * Método utilizado para obter a lista de itens de menu.
	 * 
	 * @return ArrayList
	 */
	public ArrayList getItens() {
		return this.itens;
	}

	/**
	 * Método utilizado para obter o valor do atributo itemPai.
	 * 
	 * @return Menu
	 */
	public Menu getItemPai() {
		return this.itemPai;
	}

	/**
	 * Método utilizado para definir um valor para o atributo itemPai.
	 * 
	 * @param itemPai
	 */
	public void setItemPai(Menu itemPai) {
		this.itemPai = itemPai;
	}

	/**
	 * Método utilizado para adicionar um novo objeto à estrutura do menu.
	 * 
	 * @param objetoMenu
	 * @param usuario
	 * @param ip
	 * @throws Exception
	 */
	public void adicionarItem(Menu objetoMenu, String ip, Map itens)
			throws Exception {
		boolean adicionaItem = false;

		if (objetoMenu.getId().startsWith("item")) {
//			adicionaItem = objetoMenu.autorizarPermissao(usuario, ip);
			adicionaItem = true;//itens.get(objetoMenu.getId())!=null;
		} else {
			adicionaItem = true;
		}

		if (adicionaItem) {
			this.itens.add(objetoMenu);
		}
	}
}