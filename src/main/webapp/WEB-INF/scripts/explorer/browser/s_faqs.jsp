<%@ include file="/WEB-INF/tags/script/script_browser.jsp"%>
<script src="<c:url value="/static/webjars/js/plugin/scripdesktop.min.js" />"></script>
<script type="text/javascript">
$(function(){$(".page-explorer").scrippage({urlElements:'<c:url value="${explorerCurrent.companyURL}/faqs-load"/>',filter:!1})});
</script>