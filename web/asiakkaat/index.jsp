<%-- 
    Document   : asiakkaat
    Created on : 29.1.2015, 14:07:26
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
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
                <th>Versio</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="asiakas" items="${asiakkaat}">
            <td>${asiakas.getNimi}</td>
            <td>${asiakas.getKatuosoite}</td>
            <td>${asiakas.getPostinumero}</td>
            <td>${asiakas.getKaupunki}</td>
            <td>${asiakas.getEmail}</td>
            <td>${asiakas.getAsiakasnumero}</td>
            <td>${asiakas.getLaskujaLahetetty}</td>
            <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-edit"></span>Muokkaa</button></td>
            <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-remove"></span>Poista</button></td>
            <td>${asiakas.getVersio}</td>
        </c:forEach>
    </tbody>
</table>
        <button onclick=window.location.href="AsiakasServletNew" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-user"></span>Uusi asiakas</button>
</t:template>
