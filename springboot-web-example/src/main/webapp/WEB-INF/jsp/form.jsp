<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Form JSP</title>
</head>
<body>

  <form method="post" action="<c:url value="/post-form" />">
  	<input type="checkbox" name="itens" value="1"/> Item 1 <br/> 
  	<input type="checkbox" name="itens" value="2"/> Item 2 <br/> 
  	<input type="checkbox" name="itens" value="3"/> Item 3 <br/> 
  	<input type="checkbox" name="itens" value="4"/> Item 4 <br/> 
  	<input type="checkbox" name="itens" value="5"/> Item 5 <br/> 
    <button>Enviar Form</button>
  </form>
  
  <hr/>
  
  <button id="ajax-button">Enviar Ajax Listas</button>
  
<script>

document.getElementById('ajax-button').onclick = function() {
    var xmlhttp;

    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
           if(xmlhttp.status == 200){
               alert(xmlhttp.responseText);
           }
           else if(xmlhttp.status == 400) {
              alert('There was an error 400')
           }
           else {
               alert('something else other than 200 was returned')
           }
        }
    }

    xmlhttp.open("post", "/post-form", true);
    var itens = [ 1, 2, 3, 4 ];
    
    var request = "";
    for (var i = 0; i < itens.length; i++) {
    	if (request) request += "&"
    	request += "itens=" + itens[i];
    }
    alert(request)
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send(request);
}

</script>

  <hr/>
  
  <form method="post" action="<c:url value="/post-form-entidade" />">
  	Item 1: <input type="text" name="itens" value="1"/> <br/> 
  	Item 2: <input type="text" name="itens" value="2"/> <br/> 
  	Item Lista 1: <input type="text" name="itensLista" value="3"/> <br/> 
  	Item Lista 2: <input type="text" name="itensLista" value="4"/> <br/> 
  	Item Sub Entidade 1: <input type="text" name="outraEntidade.itens" value="5"/> <br/> 
  	Item Sub Entidade 2: <input type="text" name="outraEntidade.itens" value="6"/> <br/> 
  	Item Lista Sub Entidade 1: <input type="text" name="outraEntidade.itensLista" value="7"/> <br/> 
  	Item Lista Sub Entidade 2: <input type="text" name="outraEntidade.itensLista" value="8"/> <br/> 
    <button>Enviar Form Entidade</button>
  </form>
  
</body>
</html>
