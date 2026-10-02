<%@ include file="/WEB-INF/tags/comps/init_explorer.jsp"%>
<c:import url="/WEB-INF/tags/plugins/script_page.jsp"/>
<sec:authorize access="isAnonymous()">
	<c:import url="/WEB-INF/tags/plugins/script_chatboter.jsp"/>
	<script type="text/javascript">$(function(){$("#chatbotExplorer").scripchatboter()});</script>
</sec:authorize>