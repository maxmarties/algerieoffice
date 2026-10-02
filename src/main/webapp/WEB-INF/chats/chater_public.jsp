<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><div id="chaterSupports" class="chater-supports flexed flex-row"></div></sec:authorize>
<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
	<div id="chaterPublic" class="chater-public ${currentConfig.chaterCollapse ? '' : 'open'}">
		<a class="chater-header h-header lien bn-segond bn-explorer-segond">
			<i class="breadview-trigger cmsms-icon-angle-up"></i>
			<span class="text-chat">
				<i class="cmsms-icon-comment m-r-10"></i><spring:message code="lbl.sub.chater1.1"/><span id="countChat" class="count-chat text-center font-bold" style="display:none;"></span>
			</span>
		</a>
		<div class="chater-body">
			<c:import url="/WEB-INF/basics/loading_span.jsp"/>
			<div class="chater-content"><ul class="list-none list-block chater-list"></ul></div>
		</div>
		<div class="chater-footer">
			<form name="chatPublicForm" action="/" novalidate="novalidate">
				<textarea class="form-control form-area" id="chatpublic" name="chatpublic" placeholder="<spring:message code="tool.chat.public" />" ></textarea>
			</form>
		</div>
	</div>
	<div id="chaterPrivate" class="chater-private flexed flex-row"></div>
	<c:import url="/WEB-INF/basics/brand_sound.jsp"/>
</sec:authorize>