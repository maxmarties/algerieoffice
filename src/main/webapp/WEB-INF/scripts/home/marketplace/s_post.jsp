<%@ include file="/WEB-INF/tags/script/script_public.jsp"%>
<script src="<c:url value="/static/webjars/js/lib/featherlight/featherlight.min.js" />"></script>
<c:import url="/WEB-INF/tags/plugins/script_document.jsp"/>
<script type="text/javascript">
$(function(){$("a.inner-link").featherlightGallery(),$("#screenDocument").scripdocument({urlFind:'<c:url value="/marketplace/produits-et-services"/>',urlSimultude:'<c:url value="/feedback/screen/posts-simultude"/>',typeDocument:"post"})});
</script>