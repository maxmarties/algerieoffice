<%@ include file="/WEB-INF/tags/script/script_dashboard.jsp"%>
<c:import url="/WEB-INF/tags/plugins/script_table.jsp"/>
<script type="text/javascript">
$(function(){"true"==='<c:out value="${currentCompany.hasPremium()}" />'&&$(".page-wrapper").scriplist({url:'<c:url value="/company/communication/contacts"/>'})});
</script>