package com.rinitec.algerieoffice.web.controllers.publics;

import java.time.Instant;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.blog.IBlogSearchService;
import com.rinitec.algerieoffice.services.blacklist.IBlacklistMemberService;
import com.rinitec.algerieoffice.services.publics.IMemberService;
import com.rinitec.algerieoffice.services.user.favorite.IFavoriteGlobeService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.feedback.LockForm;
import com.rinitec.algerieoffice.web.form.feedback.RateForm;
import com.rinitec.algerieoffice.web.form.search.SearchMemberForm;
import com.rinitec.algerieoffice.web.listener.events.OnDetectEvent;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.CurrentUser;
import com.rinitec.algerieoffice.web.modal.publics.members.MemberProfile;

@Controller
@RequestMapping(value = "/membres")
public class MembersController {

	private IAttributeService attributeService;
	private IMemberService memberService;
	private IFavoriteGlobeService favoriteGlobeService;
	private IBlacklistMemberService blacklistMemberService;
	private IBlogSearchService blogSearchService;
	private ApplicationEventPublisher eventPublisher;
	
	public MembersController(IAttributeService attributeService, IMemberService memberService, IFavoriteGlobeService favoriteGlobeService, 
			IBlacklistMemberService blacklistMemberService, IBlogSearchService blogSearchService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.memberService = memberService;
		this.favoriteGlobeService = favoriteGlobeService;
		this.blacklistMemberService = blacklistMemberService;
		this.blogSearchService = blogSearchService;
		this.eventPublisher = eventPublisher;
	}
	
	@ModelAttribute("urlLetters")
	public String[] urlLetters() {
		return ConstraintesURL.URL_LETTERS;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param id
	 * @param letterURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showMembers(final Model model, final Authentication authentication, @RequestParam(name = "id", required = false) final Long id, 
			@RequestParam(name = "token", required = false) final String token, @RequestParam(name = "letter", required = false) final String letterURL,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(id != null) {
			final String pseudo = memberService.findPsuedoByUserId(id);
			if(pseudo == null) {
				return "redirect:/404/members";
			}
			return "redirect:/membres/".concat(pseudo);
		}
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication);
		final CurrentConfig currentConfig = new CurrentConfig(config);
		final Long userId = currentUser != null ? currentUser.getUserId() : null;
		final Integer letter = !StringUtils.isEmpty(letterURL) ? ConstraintesURL.getIndexLetter(letterURL) : null;
		final Long countMembers = memberService.countAllActiveMember();
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("searchMember", new SearchMemberForm(userId, token, letter, currentConfig.parseDefaultMembers()));
		model.addAttribute("countMembers", ParseUtil.getFormattedCount(countMembers));
		model.addAttribute("toDay", new DateTime(Date.from(Instant.now())));
		model.addAttribute("marketBlogs", blogSearchService.findExplorerBlogMini(5));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMembersMapsiteURL());
		return "homeMembers";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param authentication
	 * @param pseudo
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{pseudo}", method = RequestMethod.GET)
	public String showMember(final HttpServletRequest request, final Model model, final Authentication authentication, 
			@PathVariable("pseudo") final String pseudo, @CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final MemberProfile memberProfile = memberService.readMemberProfile(pseudo);
		if(memberProfile == null) {
			return "redirect:/404/members";
		}
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication, config);
		final boolean hasLeader = currentUser != null && currentUser.getUserId().equals(memberProfile.getId());
		if(currentUser != null && !currentUser.getUserId().equals(memberProfile.getId())) {
			final Long memberId = memberProfile.getId();
			model.addAttribute("rate", new RateForm(memberId));
			model.addAttribute("lock", new LockForm(memberId));
			model.addAttribute("hasFavorite", favoriteGlobeService.hasFavoriteAccount(currentUser.getUserId(), memberId));
			model.addAttribute("hasLocked", blacklistMemberService.HasMemberBlocked(currentUser.getUserId(), memberId));
		}
		model.addAttribute("profile", memberProfile);
		model.addAttribute("hasLeader", hasLeader);
		model.addAttribute("marketBlogs", blogSearchService.findLastBlogMarketMini(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMemberMapsiteURL(pseudo));
		if(!hasLeader && currentUser != null && !currentUser.admin()) {
			eventPublisher.publishEvent(new OnDetectEvent(currentUser.getUserId(), memberProfile.getId(), false, request));
		}
		return "homeMember";
	}
	
}
