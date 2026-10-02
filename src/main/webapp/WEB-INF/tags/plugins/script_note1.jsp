<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<script src="<c:url value="/static/webjars/js/lib/editor/froala_editor.js"/>"></script>
<script src="<c:url value="/static/webjars/js/lib/editor/froala_language.js"/>"></script>
<script src="<c:url value="/static/webjars/js/lib/editor/froala_note.js"/>"></script>
<script src="<c:url value="/static/webjars/js/plugin/scripeditor.js" />"></script>
<script type="text/javascript">
	
	;(function ($) {
		"use strict";
		var Scripform = function(element, options) {
			var element = $(element);
			var that = this;
			var settings = $.extend({}, $.fn.scripform.defaults, options);
			
			this._init = function () {
			};
		
		};
		
		function Plugin(options) {
			if (typeof options == 'string') {
				var args = Array.prototype.slice.call(arguments, 1)
				if(args.length === 1) {
					args = args.toString();
				}
				return this.data('scripform')[options](args);
			}
			return this.each(function (index) {
				var element = $(this);
				if (element.data('scripform')) return;
				var scripform = new Scripform(element, options);
				element.data('scripform', scripform);
				scripform._init();
			});
		};
		
		var old = $.fn.scripform;
		
		$.fn.scripform = Plugin;
		$.fn.scripform.Constructor = Scripform;
		
		$.fn.scripform.noConflict = function () {
		    $.fn.scripform = old;
		    return this;
		};
		
		$.fn.scripform.defaults = {
		};
		
	})(jQuery);
	
	$(function() {$('.page-wrapper').scripform();});
	
</script>