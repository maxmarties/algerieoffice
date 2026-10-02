<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="privacyModal" class="modal defaultModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-privacy animated speed pulse" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<p class="modal-title"><spring:message code="txt.infos.privacy2.1"/></p>
				<button type="button" class="btn btn-simple btn-modal" data-dismiss="modal" title='<spring:message code="btn.close"/>'>
		    		<i class="cmsms-icon-cancel-2"></i>
		    	</button>
			</div>
			<div class="modal-body">
				<p><spring:message code="txt.infos.privacy2.1.1"/></p>
				<p class="m-t-10"><spring:message code="txt.infos.privacy2.1.2"/></p>
				<p class="m-t-10"><spring:message code="txt.infos.privacy2.1.3"/></p>
				<div id="accordPrivacy" class="accord-3d m-t-20">
					<div class="card m-b-5">
						<div id="accord1" class="card-header">
							<a class="lien" data-toggle="collapse" data-target="#accord-card1" aria-expanded="false" aria-controls="accord-card1">
								<i class="cmsms-icon-explorer-angle breadview-trigger m-r-10"></i>
								<spring:message code="txt.infos.privacy2.2.1"/>
								<span class="font-mini pull-right"><spring:message code="txt.infos.privacy2.2.3"/></span>
								<span class="clearfix"></span>
							</a>
						</div>
						<div id="accord-card1" class="collapse" aria-labelledby="accord1" data-parent="#accordPrivacy">
							<div class="card-body"><p class="font-small i-help"><spring:message code="txt.infos.privacy2.2.2"/></p></div>
						</div>
					</div>
					<div class="card">
						<div id="accord2" class="card-header">
							<a class="lien" data-toggle="collapse" data-target="#accord-card2" aria-expanded="true" aria-controls="accord-card2">
								<i class="cmsms-icon-explorer-angle breadview-trigger m-r-10"></i>
								<spring:message code="txt.infos.privacy2.3.1"/>
							</a>
						</div>
						<div id="accord-card2" class="collapse show" aria-labelledby="accord2" data-parent="#accordPrivacy">
							<div class="card-body">
								<p class="font-small i-help"><spring:message code="txt.infos.privacy2.3.2"/></p>
								<ul class="navbar-nav nav-flex-icons m-t-20">
									<c:forEach var="i" begin="1" end="4" step="1">
										<li style="width:25%;">
											<label class="ui-checkbox ui-checkbox-segond font-small">
											    <input type="checkbox" name="privacyChecked" checked><span class="input-span"></span><spring:message code="txt.infos.privacy2.3.2.${i}" />
											</label>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<label class="font-small mr-auto m-l-10"><spring:message code="txt.infos.privacy2.4"/></label>
				<button id="privacySave" type="button" class="btn btn-primary btn-fixed m-r-10"><span><spring:message code="btn.save"/></span></button>
			</div>
		</div>
	</div>
</div>