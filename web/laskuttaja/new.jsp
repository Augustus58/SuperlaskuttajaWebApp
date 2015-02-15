<%--
    Document   : new
    Created on : 12.2.2015, 15:13:20
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template pageTitle="Lisää laskuttajan tiedot" selectedTab="1">
    <c:forEach items="${errors}" var="error">
        ${error.key}: ${error.value} <br>
    </c:forEach>
    <div class="container">
        <form class="form-horizontal" role="form" action="LaskuttajaServletCreate" method="POST">
            <div class="form-group">
                <label for="inputNimi1" class="col-md-2 control-label">Nimi</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputNimi1" name="nimi" placeholder="Nimi" value="${laskuttaja.nimi}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputKatuosoite1" class="col-md-2 control-label">Katuosoite</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputKatuosoite1" name="katuosoite" placeholder="Katuosoite" value="${laskuttaja.katuosoite}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPostinumero1" class="col-md-2 control-label">Postinumero</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputPostinumero1" name="postinumero" placeholder="Postinumero" value="${laskuttaja.postinumero}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputKaupunki1" class="col-md-2 control-label">Kaupunki</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputKaupunki1" name="kaupunki" placeholder="Kaupunki" value="${laskuttaja.kaupunki}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputYrityksenNimi1" class="col-md-2 control-label">Yrityksen nimi</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputYrityksenNimi1" name="yrityksenNimi" placeholder="Yrityksen nimi" value="${laskuttaja.yrityksenNimi}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputAlvTunniste1" class="col-md-2 control-label">Alv-tunniste</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputAlvTunniste1" name="alvTunniste" placeholder="Alv-tunniste" value="${laskuttaja.alvTunniste}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputTilinumero1" class="col-md-2 control-label">Tilinumero</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputTilinumero1" name="tilinumero" placeholder="Tilinumero" value="${laskuttaja.tilinumero}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputTilinumeronPankki1" class="col-md-2 control-label">Tilinumeron pankki</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputTilinumeronPankki1" name="tilinumeronPankki" placeholder="Tilinumeron pankki" value="${laskuttaja.tilinumeronPankki}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputTilinumeronSwiftBic1" class="col-md-2 control-label">Tilinumeron Swift/Bic</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputTilinumeronSwiftBic1" name="tilinumeronSwiftBic" placeholder="Tilinumeron Swift/Bic" value="${laskuttaja.tilinumeronSwiftBic}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPuhelinnumero1" class="col-md-2 control-label">Puhelinnumero</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputPuhelinnumero1" name="puhelinnumero" placeholder="Puhelinnumero" value="${laskuttaja.puhelinnumero}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputEmail1" class="col-md-2 control-label">Sähköposti</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputEmail1" name="email" placeholder="Sähköposti" value="${laskuttaja.email}">
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
                    <button type="submit" class="btn btn-default">Ok</button>
                </div>
            </div>
        </form>
    </div>
</t:template>
