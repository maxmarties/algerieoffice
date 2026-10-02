<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div id="lockModal" class="modal explorerModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-notice bn-segond bn-explorer-segond animated pulse" role="document">
		<form:form name="formLock" action="/" method="POST" modelAttribute="lock" enctype="utf8" novalidate="novalidate">
			<spring:bind path="memberLock"><form:input type="hidden" path="memberLock" /></spring:bind>
			<div class="modal-content">
				<div class="modal-header">
					<div></div>
					<button type="button" class="btn btn-simple btn-modal btn-transparent" data-dismiss="modal" title='<spring:message code="btn.close"/>'>
		    			<i class="cmsms-icon-cancel-2"></i>
		    		</button>
				</div>
				<div class="modal-body">
					<div class="modal-parag text-center i-white text-white m-auto" style="line-height:20px;">
						<spring:message code="txt.help.explorer6.4"/> : <br><span class="font-big font-bold"><c:out value="${profile.username}"/></span>
					</div>
					<div class="modal-form">
						<spring:bind path="reasonLock">
							<div id="reasonLockForm" class="form-group">
								<c:set var="faholder" scope="page"><spring:message code="lbl.sub.report" /></c:set>
								<form:textarea class="form-control form-simple form-area" rows="3" path="reasonLock" placeholder="${pageScope.faholder}" />
								<span class="error"></span>
							</div>
						</spring:bind>
						<span class="help-text i-gray text-gray"><spring:message code="txt.help.explorer6.4.1" /></span>
						<hr class="my-4 i-light">
						<div class="form-group text-center m-t-20 m-b-0">
            				<div id="submitLockForm" class="form-submit">
            					<button type="submit" class="btn btn-primary btn-submit btn-fixed"><span><spring:message code="btn.lock"/></span></button>
            				</div>
            			</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>