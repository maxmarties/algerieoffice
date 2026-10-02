<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="Language" content="${langage.lang}">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="robots" content="All" />
<meta name="author" content="Rinitec developpement">
<meta name="keywords" content="marketplace,annuaire entreprises,annonce en ligne,algérie,mise en ligne,produits ou services,recherche entreprises en algérie,evenements,offres emploi">
<meta name="msapplication-TileColor" content="#2C3F50">
<meta name="msapplication-TileImage" content="<c:url value="/static/icons/apple-icon-min.png" />">
<link rel="icon" type="image/x-icon" href="<c:url value="/static/icons/favicon.ico" />">
<link rel="icon" type="image/png" href="<c:url value="/static/icons/apple-icon-min.png" />" sizes="192x192">
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/static/icons/favicon.ico" />">
<link rel="apple-touch-icon" type="image/png" href="<c:url value="/static/icons/apple-icon-min.png" />">
<link rel="apple-touch-icon" type="image/png" href="<c:url value="/static/icons/apple-icon-76-min.png" />" sizes="76x76">
<link rel="apple-touch-icon" type="image/png" href="<c:url value="/static/icons/apple-icon-120-min.png" />" sizes="120x120">
<link rel="apple-touch-icon" type="image/png" href="<c:url value="/static/icons/apple-icon-152-min.png" />" sizes="152x152">
<link rel="apple-touch-icon" type="image/png" href="<c:url value="/static/icons/apple-icon-180-min.png" />" sizes="180x180">
<c:if test="${!empty mapsiteURL}">
	<link rel="alternate" hreflang="x-default" href="${mapsiteURL}"/>
	<link rel="alternate" hreflang="fr" href="${mapsiteURL}?lang=fr"/>
	<link rel="alternate" hreflang="en" href="${mapsiteURL}?lang=en"/>
	<link rel="alternate" hreflang="ar" href="${mapsiteURL}?lang=ar"/>
</c:if>
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-196486359-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'UA-196486359-1');
</script>