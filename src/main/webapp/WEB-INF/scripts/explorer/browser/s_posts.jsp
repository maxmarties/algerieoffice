<%@ include file="/WEB-INF/tags/script/script_browser.jsp"%>
<script src="<c:url value="/static/webjars/js/plugin/scripdesktop.min.js" />"></script>
<script type="text/javascript">
$(function(){$(".page-explorer").scrippage({urlElements:'<c:url value="${explorerCurrent.companyURL}/produits-load"/>',desc:!0,view:!1,lateral:'<c:out value="${explorerCompany.menu.hasLateral()}"/>'})});
</script>