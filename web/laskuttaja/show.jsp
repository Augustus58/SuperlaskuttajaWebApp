<%-- 
    Document   : laskuttaja
    Created on : 27.1.2015, 10:44:23
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template pageTitle="Laskuttaja" selectedTab="1">
    <div class="jumbotron">
        <h1>Tervetuloa ${laskuttaja.nimi}!</h1>
        <p>
            ${laskuttaja.yrityksenNimi} <br>
            Laskuja lähehetty ${laskuttaja.laskujaLahetetty}
        </p>
        <p><a class="btn btn-primary btn-lg" href="LaskuttajaServletEdit" role="button">Muokkaa laskuttajan tietoja</a></p>
    </div>
</t:template>