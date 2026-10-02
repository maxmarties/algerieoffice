<%@ include file="/WEB-INF/tags/script/script_public.jsp"%>
<c:import url="/WEB-INF/tags/plugins/script_document.jsp"/>
<script type="text/javascript">
$(function(){$("#screenDocument").scripdocument({urlFind:'<c:url value="/marketplace/offres-emploi"/>',urlSimultude:'<c:url value="/feedback/screen/employes-simultude"/>',typeDocument:"employe"})});
</script>