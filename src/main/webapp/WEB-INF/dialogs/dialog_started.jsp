<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:if test="${currentConfig.started && (!empty requestScope.accountDashboard || !empty begginer)}">
	<div id="startedModal" class="modal startedModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-started animated pulse" role="document">
			<div class="modal-content">
				<div class="modal-header h-header"><spring:message code="subheader.dashboard1.5.${empty requestScope.accountDashboard ? '1' : '2'}"/></div>
				<div class="modal-body">
					<p class="font-bold font-big"><spring:message code="txt.help.dashboard1.4.${empty requestScope.accountDashboard ? '1' : '2'}"/></p>
					<p class="m-t-10"><spring:message code="txt.help.dashboard1.4.3"/></p>
				</div>
				<ul class="navbar-nav nav-flex-icons">
					<li>
						<a id="beginStarted" class="btn btn-segond btn-flat btn-fixed">
							<span><i class="cmsms-icon-explorer-hand m-r-5"></i><spring:message code="txt.help.dashboard1.4.4"/></span></a>
					</li>
					<li class="ml-auto"><a id="hideStarted" class="btn btn-file btn-transparent btn-simple"><span><spring:message code="txt.help.dashboard1.4.5"/></span></a></li>
				</ul>
			</div>
		</div>
	</div>
</c:if>