<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="cropperModal" class="modal defaultModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<input type="hidden" id="currentCropper" name="currentCropper" />
	<div class="modal-dialog modal-crooper animated pulse" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<p class="modal-title"><spring:message code="tooltip.image"/></p>
				<button type="button" class="btn btn-simple btn-modal" data-dismiss="modal" title="<spring:message code="btn.close"/>">
	    			<i class="cmsms-icon-cancel-2"></i>
	    		</button>
			</div>
			<div class="modal-body">
				<div class="container cropper" style="background: url('<c:url value="/static/vectors/mask-min.jpg" />') repeat;">
					<div id="imgContainer" class="img-container">
						<img id="image">
						<div class="ind-text"><p class="m-b-5"><spring:message code="txt.help.cropper"/></p></div>
					</div>
					<div class="slider-container">
						<div class="row">
							<div class="col-6">
								<div class="m-t-10"><spring:message code="lbl.zoom"/><span id="zoomTo" class="pull-right">1</span></div>
								<input id="sZoom" class="slider sliderProvider" type="text" data-slider-min="0.4" 
								data-slider-max="1.3" data-slider-step="0.1" data-slider-value="0.4" data-sl-provider="zoomTo"/>
							</div>
							<div class="col-6">
								<div class="m-t-10"><spring:message code="lbl.redres"/><span id="rotateTo" class="pull-right">0</span></div>
								<input id="sRotate" class="slider sliderProvider" type="text" data-slider-min="-180" 
								data-slider-max="180" data-slider-step="10" data-slider-value="0" data-sl-provider="rotateTo"/>
							</div>
						</div>
					</div>
					<div class="docs-buttons">
						<div class="btn-group">
							<button type="button" class="btn btn-icon btn-simple" data-method="scaleX" data-option="-1"
								title="<spring:message code="tooltip.horizontal"/>">
								<i class="cmsms-icon-resize-horizontal"></i>
	                        </button>
	                        <button type="button" class="btn btn-icon btn-simple" data-method="scaleY" data-option="-1"
	                        	title="<spring:message code="tooltip.vertical"/>">
	                        	<i class="cmsms-icon-resize-vertical"></i>
	                        </button>
						</div>
						<div class="btn-group">
							<button type="button" class="btn btn-icon btn-simple" data-method="move" data-option="-10" 
								data-second-option="0" title="<spring:message code="tooltip.move1"/>">
	                          	<i class="cmsms-icon-right"></i>
	                        </button>
	                        <button type="button" class="btn btn-icon btn-simple" data-method="move" data-option="10" 
	                        	data-second-option="0" title="<spring:message code="tooltip.move2"/>">
	                          	<i class="cmsms-icon-left"></i>
	                        </button>
	                        <button type="button" class="btn btn-icon btn-simple" data-method="move" data-option="0" 
	                        	data-second-option="-10" title="<spring:message code="tooltip.move3"/>">
	                          	<i class="cmsms-icon-up"></i>
	                        </button>
	                        <button type="button" class="btn btn-icon btn-simple" data-method="move" data-option="0" 
	                        	data-second-option="10" title="<spring:message code="tooltip.move4"/>">
	                          	<i class="cmsms-icon-down"></i>
	                        </button>
						</div>
						<div class="btn-group">
							<button type="button" class="btn btn-icon btn-simple" data-method="rotate" data-option="-45" 
								title="<spring:message code="tooltip.rotate1"/>">
	                          	<i class="cmsms-icon-ccw"></i>
	                        </button>
	                        <button type="button" class="btn btn-icon btn-simple" data-method="rotate" data-option="45" 
	                        	title="<spring:message code="tooltip.rotate2"/>">
	                          	<i class="cmsms-icon-cw"></i>
	                        </button>
						</div>
						<div class="btn-group pull-right">
							<button type="button" class="btn btn-icon btn-simple" data-method="reset" 
								title="<spring:message code="tooltip.reset"/>">
	                          	<i class="cmsms-icon-arrows-cw"></i>
	                        </button>	
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<label class="btn btn-segond btn-fixed mr-auto" for="inputImage">
					<input type="file" class="sr-only" id="inputImage" name="file" accept="image/*">
					<span><spring:message code="btn.photo"/></span>
				</label>
				<label id="submitImage" class="btn btn-primary btn-fixed"><span><spring:message code="btn.use"/></span></label>
			</div>
		</div>
	</div>
</div>