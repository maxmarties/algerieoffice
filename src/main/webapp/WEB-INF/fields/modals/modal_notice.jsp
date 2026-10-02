<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div id="noticeModal" class="modal explorerModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-notice bn-segond bn-explorer-segond animated pulse" role="document">
		<form:form name="formNotice" action="/" method="POST" modelAttribute="notice" enctype="utf8" novalidate="novalidate">
			<spring:bind path="companyNotice"><form:input type="hidden" path="companyNotice" /></spring:bind>
			<div class="modal-content">
				<div class="modal-header">
					<div></div>
					<button type="button" class="btn btn-simple btn-modal btn-transparent" data-dismiss="modal" title='<spring:message code="btn.close"/>'>
		    			<i class="cmsms-icon-cancel-2"></i>
		    		</button>
				</div>
				<div class="modal-body">
					<div class="modal-parag font-big text-center i-white text-white m-auto">
						<spring:message code="txt.help.explorer3.3"/> <strong><c:out value="${explorerCompany.profile.tradename}"/></strong>
					</div>
					<div class="modal-form">
						<spring:bind path="titleNotice">
							<div id="titleNoticeForm" class="form-group">
								<c:set var="faholder" scope="page"><spring:message code="tabs.title" /></c:set>
								<form:input class="form-control form-simple" type="text" path="titleNotice" placeholder="${pageScope.faholder}" />
								<span class="error"></span>
								<span class="help-text i-gray text-gray m-t-5"><spring:message code="txt.help.explorer3.3.1" /></span>
							</div>
						</spring:bind>
						<spring:bind path="messageNotice">
							<div id="messageNoticeForm" class="form-group">
								<c:set var="faholder" scope="page"><spring:message code="tabs.message" /></c:set>
								<form:textarea class="form-control form-simple form-area" rows="3" path="messageNotice" placeholder="${pageScope.faholder}" />
								<span class="error"></span>
							</div>
						</spring:bind>
						<spring:bind path="autorisedNotice">
							<div class="form-group m-b-10">
								<label class="ui-checkbox ui-checkbox-primary font-small">
							    	<form:checkbox path="autorisedNotice" />
									<span class="input-span"></span><spring:message code="comp.notice" />
								</label>
            				</div>
						</spring:bind>
						<span class="help-text i-gray text-gray"><spring:message code="txt.help.explorer3.3.2" /></span>
						<hr class="my-4 i-light">
						<div class="form-group text-center m-t-20 m-b-10">
            				<div id="submitNoticeForm" class="form-submit">
            					<button type="submit" class="btn btn-primary btn-explorer-primary btn-submit btn-fixed"><span><spring:message code="btn.send"/></span></button>
            				</div>
            			</div>
					</div>
				</div>
			</div>
		</form:form>
		<div class="modal-more bn-primary bn-explorer-primary">
			<p class="text-center font-mini i-gray text-gray">
				<span style="opacity:.75;"><spring:message code="txt.help.explorer3.3.3" /> </span><a href="<c:url value="/infos/cgu" />" 
					class="lien lien-hover lien-gray" target="_blank"><spring:message code="lien.cgu"/></a>.</p>
		</div>
	</div>
</div>