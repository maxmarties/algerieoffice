<%@ include file="/WEB-INF/tags/script/script_preview.jsp"%>
<script src="<c:url value="/static/webjars/js/plugin/scripdesktop.min.js" />"></script>
<script type="text/javascript">
$(function(){$("#desktopElements").scripdesktop({url:'<c:url value="/explorer/preview/works-load"/>',uri:'<c:out value="${explorerCurrent.companyURL}"/>',companyId:'<c:out value="${explorerCurrent.companyId}"/>',premium:'<c:out value="${explorerCurrent.premium}"/>',desc:!0,view:!1,filter:!1})});
</script>