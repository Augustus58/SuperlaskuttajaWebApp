<%-- 
    Document   : login
    Created on : 27.1.2015, 12:23:45
    Author     : Augustus58
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template pageTitle="Login">
    <div class="container">
        <form class="form-horizontal" role="form" action="LoginServlet?univParam=logInAttempt" method="POST">
            <div class="form-group">
                <label for="inputUsername1" class="col-md-2 control-label">Käyttäjätunnus</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="inputUsername1" name="username" value="${kayttaja}" placeholder="Käyttäjätunnus">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword1" class="col-md-2 control-label">Salasana</label>
                <div class="col-md-10">
                    <input type="password" class="form-control" id="inputPassword1" name="password" placeholder="Salasana">
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-2 col-md-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox"> Muista kirjautuminen
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-2 col-md-10">
                    <button type="submit" class="btn btn-default">Kirjaudu sisään</button>
                </div>
            </div>
        </form>
    </div>
</t:template>
