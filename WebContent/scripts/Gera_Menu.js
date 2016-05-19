/***
 *  Fun??es Fixas respons?veis pela montagem do menu.
 *
 */
 
var existeItemMenu = false;
var raiz;
if(parent && parent.menu) {
	raiz= new String(parent.menu.document.location);
}
if(raiz) {
	raiz=raiz.substring(0,raiz.lastIndexOf("/")) + "/";
}
var posLeftTopo = 20;
var posTopTopo  = 61;
var qtdMenuTopo = 1;
var letras = "";
function escreverTopoMenu (idDiv, descricao, largura) {
  largura = parseInt(largura);
  var style    = "position:absolute;left:"+posLeftTopo+"px;width:"+largura+"px;top:"+posTopTopo+"px;padding-top:3px;";
  posLeftTopo += largura;
  var args = letraDescricaoChave(descricao);
  document.write("<div id='"+idDiv+"' style='"+style+"'  ____onMouseOver='javascript:carregarDiv(\""+idDiv.replace(/topo/gi, 'menu')+"Anchor\",0);' onMouseOut=''>");
  document.write("<a tabindex=1 accesskey='"+args[0]+"' onKeyUp='parent.menu.vereficarOnKey(this,event)' onblur='this.style.backgroundColor=\"\";' ");
  document.write("onfocus='parent.menu.itemRootFocus(this,\""+idDiv.replace(/topo/gi, 'menu')+"Anchor\")' ");
  document.write("id='"+idDiv.replace(/topo/gi, 'menu')+"Anchor'  class='linkMenu' ");
  document.write("href='javascript:carregarDiv(\""+idDiv.replace(/topo/gi, 'menu')+"Anchor\",0);'>");
  document.write(args[1]);
  document.write("</a></div>");
}

function abrirMenu(idDiv) {
  document.write("<div id='"+idDiv+"' class='hidden' style=\"display:none\">");
  document.write("<table width='100%' cellspacing='1' cellpading='0'>");
}

/*
   idItemFolha => utilizado somente para itens folha
   descricao   => nome do item
         url   => link para arquivo (item de menu)
               => nome do id ( item de menu que aponta para submenu )
       nivel   => valor do id da div disponivel na area de aplicacao, onde o submenu ser? carregado
  escondeDiv   => valor da div inicial que ser? utilizada somente por itens 'folha' de menu
*/

function escreverItemMenu(idItemFolha,descricao,url,nivel,esconderDiv, popup) {
  nivel = parseInt(nivel);
  if(esconderDiv != "#") {
    esconderDiv = parseInt(esconderDiv);
  }
  
  document.write("<tr bgColor='#0066CC'>");  
  if( nivel == 0) { //Se for link direto, não link para submenu.
    url = insereSessionIdUrl(idSession, url);
    document.write("<td id='"+idItemFolha+"' class='itemMenu' onMouseOver='parent.menu.itemOnfocus(parent.menu.buscarElemento(this,\"A\"));' onclick='");
   	if(popup == 1)
	    document.write("javascript:popUpCenter(\""+url+"\", 800, 600)");
	else if(popup == 2)
	    document.write("javascript:popUpCenterMenu(\""+url+"\", 800, 600)");
	else
	    document.write("javascript:void(null);");
	document.write(";'>");
	//alert("<a href='javascript:popUpCenter(\""+url+idSession+"\", 800, 600);' class='linkMenu'>"+descricao+"</a>");
	if(popup != 2 && popup != 1)
	    document.write("<a onKeyUp='parent.menu.vereficarOnKey(this,event)' onfocus='parent.menu.itemOnfocus(this);' href='"+raiz+url+"' class='linkMenu'><span style='cursor:pointer;width:100%;height:100%;'>"+descricao+"</span></a>");
   else
	    document.write("<a onKeyUp='parent.menu.vereficarOnKey(this,event)' onfocus='parent.menu.itemOnfocus(this);' href='javascript:void(null);' class='linkMenu'>"+descricao+"</a>");	   
  } else {
    document.write("<td id='"+url+"' class='itemMenu' onMouseOver='parent.menu.itemOnfocus(parent.menu.buscarElemento(this,\"A\"),\""+nivel+"\",\""+url+"\",event);'>");
	document.write("<div class='seta'><img src='imagens/topo/Seta_Preta.gif'></div>");	
    document.write("<a onKeyUp='parent.menu.vereficarOnKey(this,event,"+nivel+")' class='linkMenu noHand' href='javascript:void(null);' ");
	document.write(" onfocus='parent.menu.itemOnfocus(this,\""+nivel+"\",\""+url+"\",event);'>"+descricao+"</a>");
  }
  document.write("</td></tr>");
  existeItemMenu = true;
}

function letraDescricaoChave(descricao) {
	var args = new Array();
		for(var i=0;i<descricao.length;i++) {
			var varChar = descricao.charAt(i);
			if(letras.toUpperCase().indexOf(varChar.toUpperCase()) == -1) {
				letras = letras + varChar;
				args[0] = varChar;
				args[1] = descricao.substring(0,i)+"<span style='text-decoration: underline'>"+descricao.charAt(i)+"</span>"+descricao.substring(i+1);
				break;
			}
		}
	return args; 
}

var menuPrincipalCorrente;

function vereficarOnKey(obj,evento,nivel) {
	var key=obterCaracter(evento);
	var ch = String.fromCharCode(key);
	ch = new String(ch).toUpperCase();
	if(obj.id == "idLinkSobre")
		return null;
	
	if(key == "27")	{  //5
		parent.areaAplicacao.limparDivs(1,"force");
		parent.menu.document.getElementById("idLinkSobre").focus();
		parent.areaAplicacao.focus();
		return;
	}
	var elemento = null;				
	if(obj.parentNode.id.substring(0,4) == "topo") {	
		if(key == "40") { //2			
			var elemento =  parent.areaAplicacao.document.getElementById("1")
			elemento = parent.menu.buscarElemento(elemento,"A");
			elemento.focus();			
		} else if(key == "38") { //8
			parent.areaAplicacao.limparDivs(1,"force");
			parent.menu.document.getElementById("idLinkSobre").focus();
			parent.areaAplicacao.focus();
		}
	} else {
		if(key == "40") { //2
			elemento = obj.parentNode.parentNode.nextSibling;
			if(elemento != null) {
				elemento = parent.menu.buscarElemento(elemento,"A");
				elemento.focus();
			}
		} else if(key == "39") { //6
			elemento = parent.areaAplicacao.document.getElementById(nivel+1);
			if(elemento != null) {
				elemento = parent.menu.buscarElemento(elemento,"A");
				elemento.focus();
			}
		} else if(key == "38") { //8
			elemento = obj.parentNode.parentNode.previousSibling;			
			if(elemento != null) {
				elemento = parent.menu.buscarElemento(elemento,"A");
				elemento.focus();
			} else { 
				var esconderMenu = false;
				if(nivel != null && nivel == 1) {
					esconderMenu = true;
				} else {					
					elemento = obj.parentNode.parentNode.parentNode.parentNode.parentNode.previousSibling.previousSibling;
					if(elemento != null) {
						elemento = parent.menu.buscarElemento(elemento,"A","#0066cc");
						if(elemento == null)
							esconderMenu = true;
					}
				}
				if(esconderMenu) {
					parent.areaAplicacao.limparDivs(1,"force");
					parent.menu.document.getElementById("idLinkSobre").focus();
					parent.areaAplicacao.focus();
				}
			}
		} else if(key == "37") { //4								
			elemento = obj.parentNode.parentNode.parentNode.parentNode.parentNode.previousSibling.previousSibling;
			if(elemento != null) {
				elemento = parent.menu.buscarElemento(elemento,"A","#0066cc");
				if(elemento != null)
					elemento.focus();
			}
		}		
	}
}

function buscarElemento(root,tipo,cor) {		
	var elemento = null;
	for(var i=0;i<root.childNodes.length;i++) {					
		if(root.childNodes[i].nodeType==1) {			
			if(root.childNodes[i].nodeName==tipo.toUpperCase()) {								
				if(cor != null) {
					var str = root.childNodes[i].parentNode.style.backgroundColor;
					if(cor == str) {
						return root.childNodes[i];
					}	
				} else {
					return root.childNodes[i];
				}
			}
			elemento = buscarElemento(root.childNodes[i],tipo,cor);
			if(elemento != null)
				return elemento;
		}
	}
}

function itemRootFocus(obj,id) {
	if(parent.menu.menuPrincipalCorrente != null) {
		parent.menu.menuPrincipalCorrente.style.backgroundColor="";
	}
	parent.menu.menuPrincipalCorrente=obj;
	carregarDiv(id,0);
}
function itemOnfocus(obj,nivel,url,evento) {
//	if(obj.href != "javascript:void(null);") {
	if(obj.parentNode.firstChild.tagName == "A") {
		var nivelCorrente = obj.parentNode.parentNode.parentNode.parentNode.parentNode.id;
		nivelCorrente = parseInt(nivelCorrente);
		parent.areaAplicacao.limparDivs(nivelCorrente+1,"force");			
	}	
	obj.parentNode.style.backgroundColor="#0066cc";
	setCorDown(obj.parentNode.parentNode.nextSibling);	
	setCorUp(obj.parentNode.parentNode.previousSibling);
	parent.menu.menuPrincipalCorrente.style.backgroundColor="0066cc";			
	if(url != null) {			
		var id=getDivId(obj).id;
		nivel = parseInt(nivel);
		parent.areaAplicacao.limparDivs(nivel+2,"force");							
		parent.areaAplicacao.carregarDiv(url,nivel);
	}
}

function itemOnblur(obj) {
	obj.parentNode.style.backgroundColor="#777777";
}

function setCorDown(obj) {
	if(obj != null) {		
		//if(obj.childNodes[0].style.backgroundColor != "#777777") {
			obj.childNodes[0].style.backgroundColor = "#777777";
			setCorDown(obj.nextSibling);
		//}
	}	
}
function setCorUp(obj) {
	if(obj != null) {
		//if(obj.childNodes[0].style.backgroundColor != "#777777") {
			obj.childNodes[0].style.backgroundColor = "#777777";
			setCorUp(obj.previousSibling);		
		//}		
	}
}

function fecharMenu() {
  document.write("</table></div>");
}
function obterCaracter(event)
{
		var key =( typeof event.which!="undefined"&&event.which!=null?event.which:event.keyCode);
		if(key==0)
		{
			key = event.keyCode;
		}
		return key;
}