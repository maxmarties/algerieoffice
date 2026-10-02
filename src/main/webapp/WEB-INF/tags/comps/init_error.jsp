<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<script src="<c:url value="/static/webjars/js/lib/ui.min.js" />"></script>
<script src="<c:url value="/static/webjars/js/algerieoffice.min.js" />"></script>
<script type="text/javascript">$(function(){Algerieoffice.init('<c:url value="/" />','<c:out value="${langage.lang}" />')});</script>