<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="Language" content="${explorerCompany.profile.language}">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="robots" content="All" />
<meta name="author" content="Rinitec developpement">
<meta name="keywords" content="<c:out value="${explorerPage.meta.keysword}"/>">
<meta name="description" content="<c:out value="${explorerPage.meta.description}"/>">
<meta name="msapplication-TileColor" content="#2C3F50">
<meta name="msapplication-TileImage" content="<c:url value="/static/icons/apple-icon-min.png" />">
<meta property="og:url" content="${explorerPage.meta.mapsiteURL}">
<meta property="og:type" content="article">
<meta property="og:description" content="<c:out value="${explorerPage.meta.description}"/>">
<c:if test="${!empty explorerPage.meta.urlOverview}"><meta property="og:image" content="<c:url value="${explorerPage.meta.urlOverview}" />"></c:if>
<link rel="icon" type="image/x-icon" href="<c:url value="/static/icons/favicon.ico" />">
<link rel="icon" type="image/png" href="<c:url value="/static/icons/apple-icon-min.png" />" sizes="192x192">
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/static/icons/favicon.ico" />">
<link rel="apple-touch-icon" type="image/png" href="<c:url value="/static/icons/apple-icon-min.png" />">
<link rel="apple-touch-icon" type="image/png" href="<c:url value="/static/icons/apple-icon-76-min.png" />" sizes="76x76">
<link rel="apple-touch-icon" type="image/png" href="<c:url value="/static/icons/apple-icon-120-min.png" />" sizes="120x120">
<link rel="apple-touch-icon" type="image/png" href="<c:url value="/static/icons/apple-icon-152-min.png" />" sizes="152x152">
<link rel="apple-touch-icon" type="image/png" href="<c:url value="/static/icons/apple-icon-180-min.png" />" sizes="180x180">
<link rel="alternate" hreflang="x-default" href="${explorerPage.meta.mapsiteURL}"/>
<link rel="alternate" hreflang="fr" href="${explorerPage.meta.mapsiteURL}?lang=fr"/>
<link rel="alternate" hreflang="en" href="${explorerPage.meta.mapsiteURL}?lang=en"/>
<link rel="alternate" hreflang="ar" href="${explorerPage.meta.mapsiteURL}?lang=ar"/>
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-196486359-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'UA-196486359-1');
</script>