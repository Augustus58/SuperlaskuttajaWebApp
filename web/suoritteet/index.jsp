<%-- 
    Document   : suoritteet
    Created on : 29.1.2015, 14:19:37
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:template pageTitle="Suoritteet" selectedTab="3">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Tilaajan nimi</th>
                <th>Tilaajan asiakasnumero</th>
                <th>Kuvaus</th>
                <th>Päivämäärä</th>
                <th>Määrä</th>
                <th>Yksikkö</th>
                <th>Yhteensä</th>
                <th>Laskutettu</th>
                <th>Muokkaa</th>
                <th>Poista</th> 
            </tr>
        </thead>
        <tbody>
            <c:forEach var="suorite" items="${suoritteet}">
                <fmt:parseDate value="${suorite.aloitusaika}" var="dateObject" pattern="yyyy-MM-dd HH:mm:ss" />
                <tr>
                    <td>${suorite.tilaajanNimi}</td>
                    <td>${suorite.tilaaja}</td>
                    <td>${suorite.kuvaus}</td>
                    <td><fmt:formatDate pattern="dd.MM.yyyy" value="${dateObject}" /></td>
                    <td>${suorite.maara}</td>
                    <td>${suorite.maaranYksikot}</td>
                    <td>${suorite.yht}</td>
                    <td>${suorite.lasku}</td>
                    <td><button onclick=window.location.href="SuoriteServletEdit?suoritteenNumero=${suorite.suoritteenNumero}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-edit"></span>Muokkaa</button></td>
                    <td><button onclick=window.location.href="SuoriteServletDestroy?suoritteenNumero=${suorite.suoritteenNumero}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-remove"></span>Poista</button></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <button onclick=window.location.href="SuoriteServletNew" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-list-alt"></span>Uusi suorite</button>
</t:template>
