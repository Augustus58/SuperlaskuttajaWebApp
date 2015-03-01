<%-- 
    Document   : new
    Created on : 14.2.2015, 9:53:06
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:template pageTitle="Uusi lasku" selectedTab="4">
    <c:forEach items="${errors}" var="error">
        ${error.key}: ${error.value} <br>
    </c:forEach>
    <div class="container">
        <form class="form-horizontal" role="form" action="LaskuServletCreate" method="POST">
            <div class="form-group">
                <label for="inputTilaaja1" class="col-md-2 control-label">Tilaaja</label>
                <div class="col-md-10">
                    <select class="form-control" name="tilaaja" id="inputTilaaja1">
                        <c:forEach var="tilaaja" items="${tilaajat}">
                            <option value="${tilaaja.asiakasnumero}">${tilaaja.nimi}&nbsp;${tilaaja.asiakasnumero}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="inputVastaanottaja1" class="col-md-2 control-label">Vastaanottaja</label>
                <div class="col-md-10">
                    <select class="form-control" name="vastaanottaja" id="inputVastaanottaja1">
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="inputSuoritteet1" class="col-md-2 control-label">Laskuttamattomat suoritteet</label>
                <div class="col-md-10">
                    <select class="form-control" name="suoritteet" id="inputSuoritteet1" multiple>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <fmt:parseDate value="${lasku.paivays}" var="dateObject" pattern="yyyy-MM-dd" />
                <label for="inputPaivays1" class="col-md-2 control-label">Päiväys</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputPaivays1" name="paivays" placeholder="Päiväys" value="<fmt:formatDate pattern="dd.MM.yyyy" value="${dateObject}"/>">
                </div>
            </div>
            <div class="form-group">
                <label for="inputMaksuaika1" class="col-md-2 control-label">Maksuaika</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputMaksuaika1" name="maksuaika" placeholder="Maksuaika" value=${lasku.maksuaika}>
                </div>
            </div>
            <div class="form-group">
                <label for="inputViivastyskorko1" class="col-md-2 control-label">Viivästyskorko</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputViivastyskorko1" name="viivastyskorko" placeholder="Viivästyskorko" value=${lasku.viivastyskorko}>
                </div>
            </div>
            <div class="form-group">
                <label for="inputMaksuehto1" class="col-md-2 control-label">Maksuehto</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputMaksuehto1" name="maksuehto" placeholder="Maksuehto" value="${lasku.maksuehto}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputLisatiedot1" class="col-md-2 control-label">Lisätiedot</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputLisatiedot1" name="lisatiedot" placeholder="Lisätiedot" value="${lasku.lisatiedot}">
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-2 col-md-10">
                    <button type="submit" class="btn btn-default">Ok</button>
                </div>
            </div>
        </form>
    </div>

    <script>
        $(document).ready(function () {
            updateVastaanottajat($('#inputTilaaja1'), function () {
                updateSuoritteet($('#inputVastaanottaja1'));
            });
            $('#inputTilaaja1').change(function () {
                updateVastaanottajat($(this), function () {
                    updateSuoritteet($('#inputVastaanottaja1'));
                });
            });
            $('#inputVastaanottaja1').change(function () {
                updateSuoritteet($(this));
            });
        });

        function updateVastaanottajat(sel, done) {
            getVastaanottajatJSON(sel.val()).done(function (vastaanottajat) {
                renderVastaanottajatSelect(vastaanottajat);
                done();
            });
        }

        function updateSuoritteet(sel) {
            var tilaaja = $("#inputTilaaja1 option:selected").val();
            getSuoritteetJSON(tilaaja, sel.val()).done(function (suoritteet) {
                renderSuoritteetSelect(suoritteet);
            });
        }

        function renderVastaanottajatSelect(vastaanottajat) {
            var $select = $('#inputVastaanottaja1');
            $select.find('option').remove();
            $.each(vastaanottajat, function (i, asiakas) {
                $select.append("<option value=" + asiakas['asiakasnumero'] + ">" + asiakas['nimi'] + " " + asiakas['asiakasnumero'] + " </option>");
            });
        }

        function getVastaanottajatJSON(asiakasnumero) {
            return $.get('AsiakasServletGetVastaanottajat?asiakasnumero=' + asiakasnumero);
        }

        function renderSuoritteetSelect(suoritteet) {
            var $select = $('#inputSuoritteet1');
            $select.find('option').remove();
            $.each(suoritteet, function (i, suorite) {
                var $aika = new Date(suorite['aloitusaika']);
                $select.append("<option value=" + suorite['suoritteenNumero'] + ">" + $.format.date(suorite['aloitusaika'], "dd.MM.yyyy") + " " + suorite['kuvaus'] + " </option>");
            });
        }

        function getSuoritteetJSON(tilaaja, vastaanottaja) {
            return $.get('SuoriteServletGetSuoritteet?tilaaja=' + tilaaja + '&' + 'vastaanottaja=' + vastaanottaja);
        }

    </script>

</t:template>