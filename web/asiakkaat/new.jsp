<%-- 
    Document   : new
    Created on : 8.2.2015, 15:18:11
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template pageTitle="Uusi asiakas" selectedTab="2">
    <c:forEach var="error" items="${errors}">
        <c:out value="${error}"/>
   </c:forEach>
    <div class="container">
        <form class="form-horizontal" role="form" action="AsiakasServletCreate" method="POST">
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
                    <input type="text" class="form-control" id="inputAsiakasnumero1" name="asiakasnumero" placeholder="Asiakasnumero" value="${asiakasnumero}">
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
