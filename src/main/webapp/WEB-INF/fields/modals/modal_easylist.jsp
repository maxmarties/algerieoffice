<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="easylistModal" class="modal defaultModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-easylist animated pulse" role="document">
		<form name="formEasylist" action="/" method="POST" novalidate="novalidate">
			<div class="modal-content">
				<div class="modal-header">
					<p class="modal-title"><spring:message code="tool.explorer.easylist"/></p>
					<button type="button" class="btn btn-simple btn-modal" data-dismiss="modal" title="<spring:message code="btn.close"/>">
			    		<i class="cmsms-icon-cancel-2"></i>
			    	</button>
				</div>
				<div class="modal-body">
					<div class="modal-brand">
						<i class="cmsms-icon-floppy i-segond i-34 pull-left"></i>
						<p>
							<spring:message code="lbl.sub.easylist1.2"/><br>
							<label class="col-form-label">
								<span class="help-text"><spring:message code="lbl.sub.filter1.1" />: <span class="font-bold countLine"></span></span>
							</label>
						</p>
						<span class="clearfix"></span>
					</div>
					<hr class="my-4">
					<div id="easynameForm" class="form-group">
						<label class="col-form-label" for="easyname">
							<spring:message code="tabs.easylist" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</label>
						<input class="form-control" type="text" id="easyname" name="easyname" />
						<span class="error"></span>
					</div>
				</div>
				<div class="modal-footer">
					<div id="submitEasylistForm" class="form-submit m-r-5">
						<button type="submit" class="btn btn-primary btn-submit btn-fixed"><span><spring:message code="btn.save"/></span></button>
					</div>
					<button type="button" class="btn btn-segond btn-fixed m-r-10" data-dismiss="modal"><span><spring:message code="btn.cancel"/></span></button>
				</div>
			</div>
		</form>
	</div>
</div>