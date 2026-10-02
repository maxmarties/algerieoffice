<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-column explorer-mainabout">
	<div class="mainabout mainabout-${explorerPage.about.size} background-container" style="background-image: url('<c:url value="${explorerPage.about.urlCover}" />');">
		<div class="mainabout-overlay flexed flex-colone flex-jusitify">
			<p class="sh-black"><i class="cmsms-icon-quote text-segond m-r-10"></i><c:out value="${explorerPage.about.word}"/></p>
			<div class="m-t-20">
				<h2 class="h-header h-header5"><c:out value="${explorerPage.about.name}"/></h2>
				<span class="font-small text-gray"><c:out value="${explorerPage.about.function}"/></span>
			</div>
		</div>
	</div>
</div>