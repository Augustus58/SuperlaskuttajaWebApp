<%-- 
    Document   : new
    Created on : 14.2.2015, 9:53:06
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:template pageTitle="Uusi suorite" selectedTab="3">
    <c:forEach items="${errors}" var="error">
        ${error.key}: ${error.value} <br>
    </c:forEach>
    <div class="container">
        <form class="form-horizontal" role="form" action="SuoriteServletCreate" method="POST">
            <div class="form-group">
                <label for="inputTilaaja1" class="col-md-2 control-label">Tilaaja</label>
                <div class="col-md-10">
                    <select class="form-control" name="tilaaja" id="inputTilaaja1">
                        <c:forEach var="asiakas" items="${asiakkaat}">
                            <option value="${asiakas.asiakasnumero}" <c:if test="${asiakas.asiakasnumero == suorite.tilaaja}">selected</c:if>>${asiakas.nimi} ${asiakas.asiakasnumero}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="inputVastaanottaja1" class="col-md-2 control-label">Vastaanottaja</label>
                <div class="col-md-10">
                    <select class="form-control" name="vastaanottaja" id="inputVastaanottaja1">
                        <c:forEach var="asiakas" items="${asiakkaat}">
                            <option value="${asiakas.asiakasnumero}" <c:if test="${asiakas.asiakasnumero == suorite.vastaanottaja}">selected</c:if>>${asiakas.nimi} ${asiakas.asiakasnumero}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="inputKuvaus1" class="col-md-2 control-label">Kuvaus</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputKuvaus1" name="kuvaus" placeholder="Kuvaus" value="${suorite.kuvaus}">
                </div>
            </div>
            <div class="form-group">
                <fmt:parseDate value="${suorite.aloitusaika}" var="dateObject" pattern="yyyy-MM-dd HH:mm:ss" />
                <label for="inputAloitusaika1" class="col-md-2 control-label">Päivämäärä</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputAloitusaika1" name="aloitusaika" placeholder="Päivämäärä" value="<fmt:formatDate pattern="dd.MM.yyyy" value="${dateObject}" />">
                </div>
            </div>
            <div class="form-group">
                <label for="inputMaara1" class="col-md-2 control-label">Määrä</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputMaara1" name="maara" placeholder="Määrä" value="${suorite.maara}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputYksikot1" class="col-md-2 control-label">Yksiköt</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputYksikot1" name="maaranYksikot" placeholder="Yksiköt" value="${suorite.maaranYksikot}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputAHintaVerollinen1" class="col-md-2 control-label">à hinta verollinen</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputAHintaVerollinen1" name="aHintaVerollinen" placeholder="à hinta verollinen" value="${suorite.aHintaVerollinen}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputAlvProsentti1" class="col-md-2 control-label">Alv-prosentti</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputAlvProsentti1" name="alvProsentti" placeholder="Alv-prosentti" value="${suorite.alvProsentti}">
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-2 col-md-10">
                    <button type="submit" class="btn btn-default">Ok</button>
                </div>
            </div>
        </form>
    </div>

    <!--

<script>
function isValidDate(controlName, format) {
var isValid = true;
try {
    jQuery.datepicker.parseDate(format, $('#' + controlName).val(), null);
}
catch (error) {
    isValid = false;
}
return isValid;
}

$(function () {
$('#inputAloitusaika1').keyup(function () {
    if (isValidDate('inputAloitusaika1', 'dd.MM.yyyy HH.mm')) {
        alert('new value = ');
    } else {

    }
});
});
</script>
    
    -->

</t:template>
