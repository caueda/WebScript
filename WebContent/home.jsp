<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>">
	<meta http-equiv="Cache-Control" content="no-store" />
	<link rel="stylesheet" href="css/jquery.treefilter.css">
	
	<style>
	body		{background:#fff; padding:0;}
	.container	{font-family:"roboto"; font-size:16px; background:#fff; border-radius:3px;
				border:1px solid #ddd;}
	h1			{color:#08f; font-weight:100; font-size:44px; margin-top:150px;}
	input		{background:transparent; font-size:16px; border:1px solid #ddd; border-width:0 0 1px 0; border-radius: 0; line-height:40px; height:40px; width:100%; outline:none;}
	ul#my-tree	{margin:0; padding:10px 5px; color:#666;}
	ul#my-tree	li		{margin:8px 0;}
	div.desc	{margin:20px 0; color:#aaa; font-size:11px; text-align:left;}
	</style>
	<script src="scripts/jquery-1.12.0.js"></script>
	<script src="scripts/jquery-ui.js"></script>	
	<script src="scripts/jquery.treefilter-0.1.0.js"></script>
	<script src="scripts/angular.min.js"></script>
	<script>
		$(function() {		
		  var tree = new treefilter($("#my-tree"), {	 
			  searcher : $("input#my-search"),
				multiselect : false

		  });
		});

	</script>
<title>Página de Testes</title>
</head>
<body>
			<div class="container">
				<input type="search" id="my-search" placeholder="search">
				<ul id="my-tree">
					<li><!-- [01]In�cio do Menu Tabelas -->
						<div>Tabelas</div>
						<ul>
							<li>
								<div>Convênio de Arrecadação</div>
								<ul>
									<li><div>Incluir</div></li>
									<li><div>Consultar</div></li>									
								</ul>
							</li>							
						</ul>
						<ul>
							<li>
								<div>Tributo</div>
								<ul>
									<li><div>Consultar</div></li>
									<li><div>Alterar</div></li>									
								</ul>
							</li>							
						</ul>
						<ul>
							<li>
								<div>Tributo Orçamentário</div>
								<ul>
									<li><div>Incluir</div></li>
									<li><div>Incluir com Base</div></li>
									<li><div>Consultar</div></li>
									<li><div>Alterar</div></li>									
								</ul>
							</li>							
						</ul>						
					</li><!-- [01] Fim do Menu Tabelas -->
					
					<li>
						<div>Documentos</div>
						<ul>
							<li>
								<div>Previsão</div>
								<ul>
									<li>
										<div>Abertura da Receita (ABR)</div>
										<ul>
											<li><div>Incluir</div></li>
											<li><div>Consultar</div></li>
											<li><div>Estornar</div></li>
											<li><div>Listar</div></li>
										</ul>
									</li>
								</ul>
							</li>
						</ul>
					</li>
					
				</ul>
			</div>
	</table>
</body>
</html>
