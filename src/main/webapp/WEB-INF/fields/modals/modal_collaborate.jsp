<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="collaborateModal" class="modal explorerModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-collaborate bn-segond bn-explorer-segond animated pulse" role="document">
		<form name="formCollaborate" action="/" method="POST" novalidate="novalidate">
			<div class="modal-content">
				<div class="modal-header">
					<div></div>
					<button type="button" class="btn btn-simple btn-modal btn-transparent" data-dismiss="modal" title='<spring:message code="btn.close"/>'>
		    			<i class="cmsms-icon-cancel-2"></i>
		    		</button>
				</div>
				<div class="modal-body">
					<div class="modal-parag text-center i-white m-auto">
						<spring:message code="txt.help.explorer3.6"/> <strong><c:out value="${explorerCompany.profile.tradename}"/></strong>
					</div>
					<div class="modal-form">
						<div id="functionCollaborateForm" class="form-group">
							<c:set var="faholder" scope="page"><spring:message code="lbl.sub.collaborator" /></c:set>
							<input class="form-control form-simple" type="text" id="functionCollaborate" name="functionCollaborate" placeholder="${pageScope.faholder}" />
							<span class="error"></span>
						</div>
						<div class="form-group text-center m-t-20 m-b-20">
            				<div id="submitCollaborateForm" class="form-submit">
            					<button type="submit" class="btn btn-primary btn-submit btn-fixed"><span><spring:message code="btn.send"/></span></button>
            				</div>
            			</div>
            			<hr class="my-2 i-light">
						<span class="help-text i-gray"><spring:message code="txt.help.explorer3.6.1" /></span>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>