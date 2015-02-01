<%-- 
    Document   : layout
    Created on : 26.1.2015, 17:17:14
    Author     : Augustus58
--%>

<%@tag description="Generic template for Superlaskuttaja pages" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle"%>
<%@attribute name="pageError"%>
<%@attribute name="selectedTab"%>
<%@attribute name="userLogged"%>

<!DOCTYPE html>
<html>
    <head>
        <title>${pageTitle}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
        <script src="http://code.jquery.com/jquery.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <c:if test="${selectedTab == 1}">
            <nav class="navbar navbar-default">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="Laskuttaja">Laskuttaja</a></li>
                    <li><a href="AsiakasServletIndex">Asiakkaat</a></li>
                    <li><a href="SuoriteServletIndex">Suoritteet</a></li>
                    <li><a href="LaskuServletIndex">Laskut</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${userLogged != null}">
                        <li><p class="navbar-text">Signed in as <a href="#" class="navbar-link">${userLogged}</a></p></li>
                        <li><a href="LoginServletLogOut" class="navbar-link">Sign out</a></li>
                        </c:if>
                        <c:if test="${userLogged == null}">
                        <li><a href="LoginServletIndex" class="navbar-link">Sign in</a></li>
                        </c:if>
                </ul>
            </nav>
        </c:if>
        <c:if test="${selectedTab == 2}">
            <nav class="navbar navbar-default">
                <ul class="nav navbar-nav">
                    <li><a href="Laskuttaja">Laskuttaja</a></li>
                    <li class="active"><a href="AsiakasServletIndex">Asiakkaat</a></li>
                    <li><a href="SuoriteServletIndex">Suoritteet</a></li>
                    <li><a href="LaskuServletIndex">Laskut</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${userLogged != null}">
                        <li><p class="navbar-text">Signed in as <a href="#" class="navbar-link">${userLogged}</a></p></li>
                        <li><a href="LoginServletLogOut" class="navbar-link">Sign out</a></li>
                        </c:if>
                        <c:if test="${userLogged == null}">
                        <li><a href="LoginServletIndex" class="navbar-link">Sign in</a></li>
                        </c:if>
                </ul>
            </nav>
        </c:if>
        <c:if test="${selectedTab == 3}">
            <nav class="navbar navbar-default">
                <ul class="nav navbar-nav">
                    <li><a href="Laskuttaja">Laskuttaja</a></li>
                    <li><a href="AsiakasServletIndex">Asiakkaat</a></li>
                    <li class="active"><a href="SuoriteServletIndex">Suoritteet</a></li>
                    <li><a href="LaskuServletIndex">Laskut</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${userLogged != null}">
                        <li><p class="navbar-text">Signed in as <a href="#" class="navbar-link">${userLogged}</a></p></li>
                        <li><a href="LoginServletLogOut" class="navbar-link">Sign out</a></li>
                        </c:if>
                        <c:if test="${userLogged == null}">
                        <li><a href="LoginServletIndex" class="navbar-link">Sign in</a></li>
                        </c:if>
                </ul>
            </nav>
        </c:if>
        <c:if test="${selectedTab == 4}">
            <nav class="navbar navbar-default">
                <ul class="nav navbar-nav">
                    <li><a href="Laskuttaja">Laskuttaja</a></li>
                    <li><a href="AsiakasServletIndex">Asiakkaat</a></li>
                    <li><a href="SuoriteServletIndex">Suoritteet</a></li>
                    <li class="active"><a href="LaskuServletIndex">Laskut</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${userLogged != null}">
                        <li><p class="navbar-text">Signed in as <a href="#" class="navbar-link">${userLogged}</a></p></li>
                        <li><a href="LoginServletLogOut" class="navbar-link">Sign out</a></li>
                        </c:if>
                        <c:if test="${userLogged == null}">
                        <li><a href="LoginServletIndex" class="navbar-link">Sign in</a></li>
                        </c:if>
                </ul>
            </nav>
        </c:if>
        <div class="container">
            <c:if test="${pageError != null}">
                <div class="alert alert-danger">${pageError}</div>
            </c:if>
            <jsp:doBody/>
        </div>
    </body>
</html>
