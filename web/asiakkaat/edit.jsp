<%-- 
    Document   : edit
    Created on : 9.2.2015, 0:36:36
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template pageTitle="Muokkaa asiakasta" selectedTab="2">
    <c:forEach items="${errors}" var="error">
        ${error.key}: ${error.value} <br>
    </c:forEach>
    <div class="container">
        <form class="form-horizontal" role="form" action="AsiakasServletUpdate" method="POST">
            <div class="form-group">
                <label for="inputNimi1" class="col-md-2 control-label">Nimi</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputNimi1" name="nimi" placeholder="Nimi" value="${asiakas.nimi}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputKatuosoite1" class="col-md-2 control-label">Katuosoite</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputKatuosoite1" name="katuosoite" placeholder="Katuosoite" value="${asiakas.katuosoite}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPostinumero1" class="col-md-2 control-label">Postinumero</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputPostinumero1" name="postinumero" placeholder="Postinumero" value="${asiakas.postinumero}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputKaupunki1" class="col-md-2 control-label">Kaupunki</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputKaupunki1" name="kaupunki" placeholder="Kaupunki" value="${asiakas.kaupunki}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputEmail1" class="col-md-2 control-label">Sähköposti</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputEmail1" name="email" placeholder="Sähköposti" value="${asiakas.email}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputAsiakasnumero1" class="col-md-2 control-label">Asiakasnumero</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputAsiakasnumero1" name="asiakasnumero" placeholder="Asiakasnumero" value="${asiakasnumero}" disabled>
                </div>
            </div>
            <div class="form-group">
                <label for="inputLaskujaLahetetty1" class="col-md-2 control-label">Laskuja lähetetty</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputLaskujaLahetetty1" name="laskujaLahetetty" placeholder="Laskuja lähetetty" value="${laskujaLahetetty}">
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-2 col-md-10">
                    <button type="submit" class="btn btn-default">Muokkaa</button>
                </div>
            </div>
        </form>
    </div>
</t:template>
