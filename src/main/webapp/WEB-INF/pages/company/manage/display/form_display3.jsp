<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header4 i-primary font-normal"><spring:message code="wizard.manage.display3"/></h2>
<p class="font-small m-t-5"><spring:message code="txt.company.manage1.3"/></p>
<hr class="my-4">
<spring:bind path="pageStyle"><form:input type="hidden" path="pageStyle" /></spring:bind>
<spring:bind path="postStyle">
	<div id="postStyleForm" class="form-group row">
		<c:forEach var="i" begin="1" end="3" step="1">
			<div class="col-sm-4 m-b-20">
				<div class="form-manage m-auto h-100 ${maindisplay.postStyle == i ? 'selected' : ''}">
					<label class="ui-radio ui-radio-segond font-small">
						<form:radiobutton value="${i}" path="postStyle" />
						<span class="input-span"></span><spring:message code="lbl.sub.display3.${i}" />
					</label>
					<div class="m-t-5">
						<img class="img-responsive" src="<c:url value="/static/vectors/display/m_aoprd${i}-min.jpg"/>" alt="<spring:message code="lbl.sub.display3.${i}" />">
					</div>
					<hr class="my-4">
					<span class="help-text"><spring:message code="txt.help.display3.${i}" /></span>
				</div>
			</div>
		</c:forEach>
	</div>
</spring:bind>