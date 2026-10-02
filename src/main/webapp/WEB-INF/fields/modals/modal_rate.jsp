<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div id="rateModal" class="modal defaultModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-appointment animated pulse" role="document">
		<form:form name="formRate" action="/" method="POST" modelAttribute="rate" enctype="utf8" novalidate="novalidate">
			<spring:bind path="memberRate"><form:input type="hidden" path="memberRate" /></spring:bind>
			<div class="modal-content">
				<div class="modal-header">
					<p class="modal-title"><spring:message code="explorer.popup.home3.2"/></p>
					<button type="button" class="btn btn-simple btn-modal" data-dismiss="modal" title='<spring:message code="btn.close"/>'>
		    			<i class="cmsms-icon-cancel-2"></i>
		    		</button>
				</div>
				<div class="modal-body">
					<div class="modal-brand">
						<i class="cmsms-icon-umbrella i-segond text-segond i-34 pull-left"></i>
						<p>
							<spring:message code="txt.help.explorer6.1"/><br>
							<label class="col-form-label"><span class="help-text"><spring:message code="txt.help.explorer6.1.1" /></span></label>
						</p>
						<span class="clearfix"></span>
					</div>
					<hr class="my-4">
					<spring:bind path="typeRate">
						<div id="typeRateForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="typeRate">
								<spring:message code="tabs.type" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.explorer6.1.2" /></span>
							</form:label>
							<div class="col-md-8">
								<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
								<form:select class="form-select2-simple" path="typeRate" data-placeholder="${pageScope.faholder}">
									<option></option>
									<c:forEach var="i" begin="1" end="4" step="1">
										<option value="${i}"><spring:message code="chose.rate${i}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="reasonRate">
						<div id="reasonRateForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="reasonRate">
								<spring:message code="lbl.sub.report" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.explorer6.1.3" /></span>
							</form:label>
							<div class="col-md-8">
								<form:textarea class="form-control form-area" rows="4" path="reasonRate" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<div id="acceptRateForm" class="form-group row">
						<div class="col-md-4 hidden-sm-down"></div>
						<div class="col-md-8">
							<spring:bind path="acceptRate">
								<label class="ui-checkbox ui-checkbox-segond font-small">
									<form:checkbox path="acceptRate" />
									<span class="input-span"></span><spring:message code="comp.report" />
								</label>
							</spring:bind>
							<span class="error"></span>
						</div>
					</div>
					<hr class="my-4">
					<label class="col-form-label"><span class="help-text"><spring:message code="txt.help.explorer3.5.5" /></span></label>
				</div>
				<div class="modal-footer">
					<div id="submitRateForm" class="form-submit m-r-5">
						<button type="submit" class="btn btn-primary btn-submit btn-fixed"><span><spring:message code="btn.send"/></span></button>
					</div>
					<button type="button" class="btn btn-segond btn-fixed m-r-10" data-dismiss="modal"><span><spring:message code="btn.cancel"/></span></button>
				</div>
			</div>
		</form:form>
	</div>
</div>