<%-- 
    Document   : index
    Created on : 29.1.2015, 14:19:37
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:template pageTitle="Laskut" selectedTab="4">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Asiakas</th>
                <th>Asiakasnumero</th>
                <th>Viite</th>
                <th>Laskun numero</th>
                <th>Summa</th>
                <th>Päiväys</th>
                <th>Eräpäivä</th>
                <th>Maksettu</th>
                <th>Muuta maksettu</th>
                <th>Vie pdf</th>
                <th>Poista</th> 
            </tr>
        </thead>
        <tbody>
            <c:forEach var="lasku" items="${laskut}">
                <fmt:parseDate value="${lasku.paivays}" var="paivays" pattern="yyyy-MM-dd" />
                <fmt:parseDate value="${lasku.erapaiva}" var="erapaiva" pattern="yyyy-MM-dd" />
                <tr>
                    <td>${lasku.asiakas}</td>
                    <td>${lasku.asiakasnumero}</td>
                    <td>${lasku.viite}</td>
                    <td>${lasku.laskunNumero}</td>
                    <td>${lasku.summa}</td>
                    <td><fmt:formatDate pattern="dd.MM.yyyy" value="${paivays}" /></td>
                    <td><fmt:formatDate pattern="dd.MM.yyyy" value="${erapaiva}" /></td>
                    <td>${lasku.onkoMaksettu}</td>
                    <td><button onclick=window.location.href="LaskuServletSwitchMaksettu?laskunNumero=${lasku.laskunNumero}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-book"></span>Muuta maksettu</button></td>
                    <td><button onclick=window.open("LaskuServletToPdf?laskunNumero=${lasku.laskunNumero}") type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-export"></span>Vie pdf</button></td>
                    <td><button onclick=window.location.href="LaskuServletDestroy?laskunNumero=${lasku.laskunNumero}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-remove"></span>Poista</button></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <button onclick=window.location.href="LaskuServletNew" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-list-alt"></span>Uusi lasku</button>
</t:template>
