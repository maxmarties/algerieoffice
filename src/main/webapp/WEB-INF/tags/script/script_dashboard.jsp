<%@ include file="/WEB-INF/tags/comps/init_script.jsp"%>
<script src="<c:url value="/static/webjars/js/window/aside.min.js" />"></script>
<c:import url="/WEB-INF/tags/plugins/script_followed.jsp"/>
<sec:authorize access="!hasAuthority('SUPPORT_AUTOR_PRIVILEGE')">
	<c:import url="/WEB-INF/tags/plugins/script_support.jsp"/>
	<script type="text/javascript">
	$(function(){$("#iSupport").scripsupport()});
	</script>
</sec:authorize>
<script type="text/javascript">
$(function(){$("#collapseMenu").collabseMenu({color:'<c:out value="${currentConfig.aocolor(4)}"/>',messageOpen:'<spring:message code="tooltip.sidebar.open" javaScriptEscape="true"/>',messageClose:'<spring:message code="tooltip.sidebar.close" javaScriptEscape="true"/>',onCollapse:function(e){$.post('<c:url value="/config/menu-collapse?collapsed="/>'+e)}}),$("#collapsePanel").length&&$("#collapsePanel").collabsePanel({messageOpen:'<spring:message code="tooltip.panel.open" javaScriptEscape="true"/>',messageClose:'<spring:message code="tooltip.panel.close" javaScriptEscape="true"/>',onCollapse:function(e){$.post('<c:url value="/config/panel-collapse?collapsed="/>'+e)}}),$(".panel-sidebar").length&&$(".panel-sidebar").find(".panel-scroll").slimScroll({position:Algerieoffice.rtl()?"left":"right",distance:"0",size:"5px",height:"100%",color:'<c:out value="${currentConfig.aocolor(4)}"/>',opacity:.75}),$("#iCompanies").scripfollowed({url:'<c:url value="/inbox/followed/companies" />',form:"form[name=iCompaniesForm]",find:"#iSearchCompanies"}),$("#iMembers").scripfollowed({url:'<c:url value="/inbox/followed/accounts" />',form:"form[name=iMembersForm]",find:"#iSearchMembers",messenger:!0}),$("#navSearchHome").scripfinder()});
</script>
<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
	<script type="text/javascript">
	$(function(){$("#iUsers").scripfollowed({url:'<c:url value="/inbox/followed/users" />',form:"form[name=iUsersForm]",find:"#iSearchUsers",messenger:!0})});
	</script>
</sec:authorize>