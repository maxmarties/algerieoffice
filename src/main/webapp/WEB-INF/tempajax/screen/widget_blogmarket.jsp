<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="screen-column screen-blogmarket background-container screen-${blogMarket.language == 'ar' ? 'ar' : 'fr'} m-b-20" 
	style="background-image: linear-gradient(to ${blogMarket.language == 'ar' ? 'right' : 'left'}, rgba(24,31,42,.25) 30%, #181F2A 80%), url('<c:url value="${blogMarket.photoURL}" />');">
	<div class="widget-title" data-dir="${blogMarket.language == 'ar' ? 'rtl' : 'ltr'}">
		<a href="<c:url value="${blogMarket.identifyURL}" />" class="h-doc h-doc2 lien sh-black" target="_blank"><c:out value="${blogMarket.title}" /></a>
	</div>
	<div class="widget-body text-${blogMarket.language == 'ar' ? 'ar' : 'fr'}">
		<div class="h-header font-big i-gray m-b-30"><c:out value="${blogMarket.description}" /></div>
		<a href="<c:url value="${blogMarket.identifyURL}" />" class="btn btn-segond btn-simple btn-add btn-right" target="_blank">
			<span><i class="cmsms-icon-explorer-arrow"></i><spring:message code="lien.more"/></span></a>
	</div>
</div>
</compress:html>