<%-- 
    Document   : asiakkaat
    Created on : 29.1.2015, 14:07:26
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template pageTitle="Asiakkaat" selectedTab="2">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Nimi</th>
                <th>Katuosoite</th>
                <th>Postinumero</th>
                <th>Kaupunki</th>
                <th>Sähköposti</th>
                <th>Asiakasnumero</th>
                <th>Laskuja lähetetty</th>
                <th>Muokkaa</th>
                <th>Poista</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="asiakas" items="${asiakkaat}">
            <tr>
            <td>${asiakas.nimi}</td>
            <td>${asiakas.katuosoite}</td>
            <td>${asiakas.postinumero}</td>
            <td>${asiakas.kaupunki}</td>
            <td>${asiakas.email}</td>
            <td>${asiakas.asiakasnumero}</td>
            <td>${asiakas.laskujaLahetetty}</td>
            <td><button onclick=window.location.href="AsiakasServletEdit?asiakasnumero=${asiakas.asiakasnumero}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-edit"></span>Muokkaa</button></td>
            <td><button onclick=window.location.href="AsiakasServletDestroy?asiakasnumero=${asiakas.asiakasnumero}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-remove"></span>Poista</button></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
        <button onclick=window.location.href="AsiakasServletNew" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-user"></span>Uusi asiakas</button>
</t:template>
