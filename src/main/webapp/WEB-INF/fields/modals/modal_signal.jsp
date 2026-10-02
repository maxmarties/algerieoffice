<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div id="signalModal" class="modal defaultModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-collaborate animated pulse" role="document">
		<form:form name="formSignal" action="/" method="POST" modelAttribute="signal" enctype="utf8" novalidate="novalidate">
			<spring:bind path="userSignal"><form:input type="hidden" path="userSignal" /></spring:bind>
			<spring:bind path="topicSignal"><form:input type="hidden" path="topicSignal" /></spring:bind>
			<spring:bind path="commentSignal"><form:input type="hidden" path="commentSignal" /></spring:bind>
			<div class="modal-content">
				<div class="modal-header">
					<p class="modal-title"><spring:message code="explorer.popup.topic2"/></p>
					<button type="button" class="btn btn-simple btn-modal" data-dismiss="modal" title="<spring:message code="btn.close"/>">
		    			<i class="cmsms-icon-cancel-2"></i>
		    		</button>
				</div>
				<div class="modal-body">
					<div class="modal-brand">
						<i class="cmsms-icon-umbrella i-segond1 i-34 pull-left"></i>
						<p>
							<spring:message code="txt.help.topic3.1"/><br>
							<label class="col-form-label"><span class="help-text"><spring:message code="txt.help.topic3.2" /> :</span></label>
						</p>
						<span class="clearfix"></span>
					</div>
					<hr class="my-4">
					<spring:bind path="typeSignal">
						<div id="typeSignalForm" class="form-group">
							<form:label class="col-form-label" path="typeSignal">
								<spring:message code="txt.help.topic3.3" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
							<form:select class="form-select2-simple" path="typeSignal" data-placeholder="${pageScope.faholder}">
								<option></option>
								<c:forEach var="i" begin="1" end="4" step="1">
									<option value="${i}"><spring:message code="chose.topic.signal${i}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="reasonSignal">
						<div id="reasonSignalForm" class="form-group">
							<form:label class="col-form-label" path="reasonSignal"><spring:message code="txt.help.topic3.4" /></form:label>
							<form:textarea class="form-control form-area" rows="4" path="reasonSignal" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="acceptSignal">
						<div id="acceptSignalForm" class="form-group">
							<label class="ui-checkbox ui-checkbox-segond font-small">
								<form:checkbox path="acceptSignal" /><span class="input-span"></span><spring:message code="comp.report" />
							</label>
							<span class="error"></span>
						</div>
					</spring:bind>
					<hr class="my-4">
					<label class="col-form-label"><span class="help-text"><spring:message code="txt.help.explorer3.5.5" /></span></label>
				</div>
				<div class="modal-footer">
					<div id="submitSignalForm" class="form-submit m-r-5">
						<button type="submit" class="btn btn-primary btn-submit btn-fixed"><span><spring:message code="btn.send"/></span></button>
					</div>
					<button type="button" class="btn btn-segond btn-fixed m-r-10" data-dismiss="modal"><span><spring:message code="btn.cancel"/></span></button>
				</div>
			</div>
		</form:form>
	</div>
</div>