<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div id="reportModal" class="modal defaultModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-appointment animated pulse" role="document">
		<form:form name="formReport" action="/" method="POST" modelAttribute="report" enctype="multipart/form-data" novalidate="novalidate">
			<spring:bind path="companyReport"><form:input type="hidden" path="companyReport" /></spring:bind>
			<div class="modal-content">
				<div class="modal-header">
					<p class="modal-title"><spring:message code="explorer.popup.home2.3"/></p>
					<button type="button" class="btn btn-simple btn-modal" data-dismiss="modal" title="<spring:message code="btn.close"/>">
		    			<i class="cmsms-icon-cancel-2"></i>
		    		</button>
				</div>
				<div class="modal-body">
					<div class="modal-brand">
						<i class="cmsms-icon-umbrella i-segond text-segond i-34 pull-left"></i>
						<p>
							<spring:message code="txt.help.explorer3.5"/><br>
							<label class="col-form-label"><span class="help-text"><spring:message code="txt.help.explorer3.5.1" /></span></label>
						</p>
						<span class="clearfix"></span>
					</div>
					<hr class="my-4">
					<spring:bind path="typeReport">
						<div id="typeReportForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="typeReport">
								<spring:message code="tabs.type" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.explorer3.5.2" /></span>
							</form:label>
							<div class="col-md-8">
								<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
								<form:select class="form-select2-simple" path="typeReport" data-placeholder="${pageScope.faholder}">
									<option></option>
									<c:forEach var="i" begin="1" end="5" step="1">
										<option value="${i}"><spring:message code="chose.report${i}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="reasonReport">
						<div id="reasonReportForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="reasonReport">
								<spring:message code="lbl.sub.report" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.explorer3.5.3" /></span>
							</form:label>
							<div class="col-md-8">
								<form:textarea class="form-control form-area" rows="4" path="reasonReport" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="hasFileReport"><form:input type="hidden" path="hasFileReport" /></spring:bind>
					<div id="fileReportForm" class="form-group row">
						<label class="col-form-label col-md-4">
							<spring:message code="lbl.sub.linked2.1" />
							<span class="help-text"><spring:message code="txt.help.explorer3.5.4" /></span>
						</label>
						<div class="col-md-8">
							<div class="file-input">
								<label class="btn btn-file btn-simple btn-fixed m-r-10" for="inputFileReport">
									<input type="file" class="sr-only" id="inputFileReport" name="inputFileReport" accept="image/*">
									<span><spring:message code="btn.file"/></span>
								</label>
								<label id="resultFileReport"><spring:message code="tooltip.file" /></label>
								<a id="clearFileReport" class="btn btn-table btn-red" style="display:none;" title="<spring:message code="btn.delete.file"/>">
									<i class="cmsms-icon-trash-7"></i>
								</a>
							</div>
							<span class="error"></span>
						</div>
					</div>
					<div id="acceptReportForm" class="form-group row">
						<div class="col-md-4 hidden-sm-down"></div>
						<div class="col-md-8">
							<spring:bind path="acceptReport">
								<label class="ui-checkbox ui-checkbox-segond font-small">
									<form:checkbox path="acceptReport" />
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
					<div id="submitReportForm" class="form-submit m-r-5">
						<button type="submit" class="btn btn-primary btn-explorer-primary btn-submit btn-fixed"><span><spring:message code="btn.send"/></span></button>
					</div>
					<button type="button" class="btn btn-segond btn-explorer-segond btn-fixed m-r-10" data-dismiss="modal"><span><spring:message code="btn.cancel"/></span></button>
				</div>
			</div>
		</form:form>
	</div>
</div>