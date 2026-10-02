<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="sliderExplorer" class="slider-overview ${!explorerPage.inbox.slider.hasNavigation ? 'slider-notnav' : ''} ${!explorerPage.inbox.slider.hasDots ? 'slider-notdots' : ''}"
	data-hasautoplay="${explorerPage.inbox.slider.hasAutoplay}" data-hashover="${explorerPage.inbox.slider.hasHover}" data-speed="${explorerPage.inbox.slider.speed}" 
	data-timeout="${explorerPage.inbox.slider.timeout}" data-animatein="${explorerPage.inbox.slider.animateIn}" data-animateout="${explorerPage.inbox.slider.animateOut}">
	<div class="slider-body" style="min-height:60px;">
		<c:forEach var="itemDescription" items="${explorerPage.inbox.slider.descriptions}" varStatus="state">
			<div id="sliderFade${state.count}" 
				class="slider-fade animated onne ${explorerPage.inbox.slider.animateFade != '-' ? explorerPage.inbox.slider.animateFade : ''}" 
				style="color:${explorerPage.inbox.slider.textColor}; background:${explorerPage.inbox.slider.fadeColor}; display:none;">
				<c:out value="${itemDescription}"/>
			</div>
		</c:forEach>
		<div class="owl-carousel owl-theme">
			<c:forEach var="itemTitle" items="${explorerPage.inbox.slider.titles}" varStatus="state">
				<div class="item">
					<img class="img-responsive" src="<c:url value="${explorerPage.inbox.slider.photosURL.get(state.count - 1)}"/>" 
						alt="<c:out value="${itemTitle}" />">
				</div>
			</c:forEach>
		</div>
	</div>
</div>