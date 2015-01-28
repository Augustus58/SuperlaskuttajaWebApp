–<%-- 
    Document   : layout
    Created on : 26.1.2015, 17:17:14
    Author     : Augustus58
--%>

<%@tag description="Generic template for Superlaskuttaja pages" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle"%>
<%@attribute name="pageError"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${pageTitle}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/css/bootstrap.css" rel="stylesheet">
        <link href="/css/bootstrap-theme.css" rel="stylesheet">
        <link href="/css/main.css" rel="stylesheet">
        <script src="http://code.jquery.com/jquery.js"></script>
        <script src="/js/bootstrap.min.js"></script>
    </head>
    <body>
        <!--Seuraavan tabivalikon valitun tabin voisi valita ehtolauseella esim. sivuotsikon perusteella.-->
        <ul class="nav nav-tabs">
            <li class="active"><a href="Laskuttaja">Laskuttaja</a></li>
            <li><a href="#">Asiakkaat</a></li>
            <li><a href="#">Suoritteet</a></li>
            <li><a href="#">Laskut</a></li>
        </ul>
        <a href="LoginServlet?univParam=index">Login</a>
        <div class="container">
            <c:if test="${pageError != null}">
                <div class="alert alert-danger">${pageError}</div>
            </c:if>
            <jsp:doBody/>
        </div>
    </body>
</html>
