<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div id="appointmentModal" class="modal defaultModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-appointment animated pulse" role="document">
		<form:form name="formAppointment" action="/" method="POST" modelAttribute="appointment" enctype="utf8" novalidate="novalidate">
			<spring:bind path="companyAppoint"><form:input type="hidden" path="companyAppoint" /></spring:bind>
			<div class="modal-content">
				<div class="modal-header">
					<p class="modal-title"><spring:message code="txt.help.explorer3.4"/></p>
					<button type="button" class="btn btn-simple btn-modal" data-dismiss="modal" title='<spring:message code="btn.close"/>'>
		    			<i class="cmsms-icon-cancel-2"></i>
		    		</button>
				</div>
				<div class="modal-body">
					<div class="modal-brand">
						<i class="cmsms-icon-calendar i-segond text-segond i-34 pull-left"></i>
						<p><spring:message code="txt.help.explorer3.4.1"/> :<br><strong><c:out value="${explorerCompany.profile.tradename}"/></strong></p>
						<span class="clearfix"></span>
					</div>
					<hr class="my-4">
					<spring:bind path="motifAppoint">
						<div id="motifAppointForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="motifAppoint">
								<spring:message code="lbl.sub.appoint1" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.explorer3.4.2" /></span>
							</form:label>
							<div class="col-md-8">
								<c:set var="faholder" scope="page"><spring:message code="txt.help.explorer3.4.3" /></c:set>
								<form:textarea class="form-control form-area" rows="3" path="motifAppoint" placeholder="${pageScope.faholder}" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="degreeAppoint">
						<div id="degreeAppointForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="degreeAppoint">
								<spring:message code="lbl.sub.appoint2" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.explorer3.4.4" /></span>
							</form:label>
							<div class="col-md-8">
								<form:select class="form-select2-simple" path="degreeAppoint">
									<c:forEach var="i" begin="1" end="4" step="1">
										<option value="${i}"><spring:message code="chose.degree${i}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<div id="dateForm" class="form-group row">
						<label class="col-form-label col-md-4">
							<spring:message code="lbl.sub.appoint3" />
							<span class="help-text"><spring:message code="txt.help.explorer3.4.5" /></span>
						</label>
						<div class="col-md-8">
							<div class="row">
								<spring:bind path="forDateAppoint">
									<div id="forDateAppointForm" class="form-group col-md-6">
										<div class="input-group-icon date">
											<c:set var="faholder" scope="page"><spring:message code="lbl.date.for" /></c:set>
											<form:input class="form-control" type="text" path="forDateAppoint" placeholder="${pageScope.faholder}" />
											<span class="input-group-addon" style="display:none;"></span>
										</div>
										<span class="error"></span>
									</div>
								</spring:bind>
								<spring:bind path="toDateAppoint">
									<div id="toDateAppointForm" class="form-group col-md-6">
										<div class="input-group-icon date">
											<c:set var="faholder" scope="page"><spring:message code="lbl.date.to" /></c:set>
											<form:input class="form-control" type="text" path="toDateAppoint" placeholder="${pageScope.faholder}" />
											<span class="input-group-addon" style="display:none;"></span>
										</div>
										<span class="error"></span>
									</div>
								</spring:bind>
							</div>
						</div>
					</div>
					<spring:bind path="periodAppoint">
						<div id="periodAppointForm" class="form-group row">
							<label class="col-form-label col-md-4">
								<spring:message code="lbl.sub.appoint4" />
							</label>
							<div class="col-md-8">
								<div>
									<c:forEach var="i" begin="1" end="4" step="1">
										<label class="ui-radio ui-radio-segond font-small m-r-10">
											<form:radiobutton value="${i}" path="periodAppoint" />
											<span class="input-span"></span><spring:message code="chose.appoint${i}" />
										</label>
									</c:forEach>
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<hr class="my-4">
					<label class="col-form-label"><span class="help-text"><spring:message code="txt.help.explorer3.4.6" /></span></label>
				</div>
				<div class="modal-footer">
					<div id="submitAppointForm" class="form-submit m-r-5">
						<button type="submit" class="btn btn-primary btn-explorer-primary btn-submit btn-fixed"><span><spring:message code="btn.send"/></span></button>
					</div>
					<button type="button" class="btn btn-segond btn-explorer-segond btn-fixed m-r-10" data-dismiss="modal"><span><spring:message code="btn.cancel"/></span></button>
				</div>
			</div>
		</form:form>
	</div>
</div>