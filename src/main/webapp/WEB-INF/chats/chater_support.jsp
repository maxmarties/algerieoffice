<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<sec:authorize access="!hasAuthority('SUPPORT_AUTOR_PRIVILEGE')">
	<div id="chaterSupport" class="chater-support animated pulse">
		<div class="chater-header">
			<a class="btn btn-popup iClose" title="<spring:message code="btn.close"/>"><i class="cmsms-icon-cancel-2"></i></a>
			<img src="<c:url value="/static/icons/icon-black-min.jpg"/>" class="pull-left img-circle" alt="<spring:message code="app.brand" />">
			<div class="brand-header">
				<span class="h-header i-primary font-big"><spring:message code="tool.support.header"/></span>
				<span class="help-text iModirators"></span>
			</div>
			<span class="clearfix"></span>
		</div>
		<div class="chater-body">
			<c:import url="/WEB-INF/basics/loading_span.jsp"/>
			<div class="chater-content"><ul class="list-none list-block chater-list"></ul></div>
		</div>
		<div class="chater-footer">
			<form name="chatSupportForm" action="/" novalidate="novalidate">
				<div class="input-group-support">
					<textarea class="form-control form-area" id="chatsupport" name="chatsupport" placeholder="<spring:message code="tool.chat.support" />"></textarea>
					<div id="submitSupportForm" class="form-submit">
						<label class="icon-support btn-file btn-submit transition-color" for="inputSupport" title="<spring:message code="tool.support.file"/>">
							<input type="file" class="sr-only" id="inputSupport" name="inputSupport" accept="image/*">
							<i class="cmsms-icon-camera"></i>
						</label>
					</div>
				</div>
			</form>
		</div>
	</div>
</sec:authorize>