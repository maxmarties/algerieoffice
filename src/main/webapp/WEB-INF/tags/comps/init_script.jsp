<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<script src="<c:url value="/static/webjars/js/lib/ui.min.js" />"></script>
<script src="<c:url value="/static/webjars/js/lib/moment/moment.min.js" />"></script>
<script src="<c:url value="/static/webjars/js/lib/select2/select2.min.js" />"></script>
<script src="<c:url value="/static/webjars/js/algerieoffice.min.js" />"></script>
<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
	<script src="<c:url value="/static/webjars/js/lib/socket/sockjs.min.js" />"></script>
	<script src="<c:url value="/static/webjars/js/lib/socket/stomp.min.js" />"></script>
	<c:import url="/WEB-INF/tags/plugins/script_socket.jsp"/>
</sec:authorize>
<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><c:import url="/WEB-INF/tags/plugins/script_supports.jsp"/></sec:authorize>
<script type="text/javascript">$(function(){Algerieoffice.init('<c:url value="/" />','<c:out value="${langage.lang}" />')});</script>
<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
	<script type="text/javascript">$(function(){Algerieoffice.connectUser('<c:url value="/secured/room" />','<c:out value="${currentUser.parseAutoritySocket()}"/>'),$("#iMessages").scripmessages(),$("#iNotifications").scripnotification(),$("#chaterPublic").scripchater()});</script>
</sec:authorize>
<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')"><script type="text/javascript">$(function(){$("#iTalks").scriptalk()});</script></sec:authorize>
<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><script type="text/javascript">$(function(){$("#iSupports").scripsupports()});</script></sec:authorize>