<%@ include file="/WEB-INF/tags/script/script_public.jsp"%>
<c:import url="/WEB-INF/tags/plugins/script_blog.jsp"/>
<script type="text/javascript">
$(function(){$(".page-blog").scripblog({category:'<c:out value="${category}"/>'})});
</script>