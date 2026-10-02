package com.rinitec.algerieoffice.services.company.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.dao.company.team.AgentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.CollaboratorRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.ProfileRepository;
import com.rinitec.algerieoffice.persistence.modal.company.team.Agent;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.PhoneExistException;
import com.rinitec.algerieoffice.web.error.exception.SocialExistException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.team.AgentForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.team.AgentLine;

@Service
public class AgentService implements IAgentService {

	private AgentRepository agentRepository;
	private UserRepository userRepository;
	private ProfileRepository profileRepository;
	private CollaboratorRepository collaboratorRepository;
	private IAvatarService avatarService;
	
	@Autowired
	public AgentService(AgentRepository agentRepository, UserRepository userRepository, 
			ProfileRepository profileRepository, CollaboratorRepository collaboratorRepository, 
			IAvatarService avatarService) {
		this.agentRepository = agentRepository;
		this.userRepository = userRepository;
		this.profileRepository = profileRepository;
		this.collaboratorRepository = collaboratorRepository;
		this.avatarService = avatarService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countAgent(final Long companyId) {
		return agentRepository.countByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllChoseUser(final Long companyId) {
		return userRepository.findAllChoseUserMini(companyId);
	}
	
	private final AgentForm parseAgentForm(final Agent agent) {
		final AgentForm agentForm = new AgentForm();
		agentForm.setId(agent.getId());
		agentForm.setCompanyId(agent.getCompanyId());
		agentForm.setSexe(agent.getSexe());
		agentForm.setFirstname(agent.getFirstName());
		agentForm.setLastname(agent.getLastName());
		agentForm.setFunction(agent.getFunction());
		agentForm.setBiography(agent.getBiography());
		agentForm.setEmail(agent.getEmail());
		agentForm.setPhone(agent.getPhone());
		agentForm.setFacebook(agent.getFacebook());
		agentForm.setTwitter(agent.getTwitter());
		agentForm.setLinkedin(agent.getLinkedin());
		agentForm.setUserId(agent.getUserId());
		agentForm.setHasPingled(agent.getHasPingled());
		agentForm.setHasAvatar(agent.getHasAvatar());
		agentForm.setUrlAvatar(agent.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + agent.getId() + "&type=" + AvatarType.agent 
				: "/static/picts/avatars/".concat(agent.getSexe() ? "agent-male-min.jpg" : "agent-female-min.jpg"));
		return agentForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AgentForm readAgentForm(final Long id, final Long companyId) {
		final Optional<Agent> uOptional = agentRepository.findById(id);
		if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
			return parseAgentForm(uOptional.get());
		}
		return null;
	}
	
	private final AgentForm parseAgentFormCollaborator(final Long companyId, final User user, final Profile profile, final String function) {
		final AgentForm agentForm = new AgentForm(companyId);
		agentForm.setFirstname(user.getFirstName());
		agentForm.setLastname(user.getLastName());
		agentForm.setEmail(user.getEmail());
		if(profile != null) {
			agentForm.setSexe(profile.getSexe());
			agentForm.setPhone(profile.getPhone());
		}
		agentForm.setFunction(function);
		return agentForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AgentForm readAgentFormCollaborator(final Long userId, final Long companyId) {
		final Collaborator collaborator = collaboratorRepository.findByUserIdAndCompanyId(userId, companyId);
		if(collaborator != null) {
			final Optional<User> uOptional = userRepository.findById(userId);
			if(uOptional.isPresent()) {
				final Optional<Profile> uOptionalProfile = profileRepository.findById(userId);
				return parseAgentFormCollaborator(companyId, uOptional.get(), 
						uOptionalProfile.isPresent() ? uOptionalProfile.get() : null, collaborator.getFunction());
			}
		}
		return null;
	}
	
	@Transactional
	private final Agent postAgent(final Agent agent, final AgentForm agentForm) {
		agent.setSexe(agentForm.getSexe());
		agent.setFirstName(agentForm.getFirstname());
		agent.setLastName(agentForm.getLastname());
		agent.setFunction(agentForm.getFunction());
		agent.setBiography(agentForm.getBiography());
		agent.setEmail(agentForm.getEmail());
		agent.setPhone(agentForm.getPhone());
		agent.setFacebook(!StringUtils.isEmpty(agentForm.getFacebook()) ? agentForm.getFacebook() : null);
		agent.setTwitter(!StringUtils.isEmpty(agentForm.getTwitter()) ? agentForm.getTwitter() : null);
		agent.setLinkedin(!StringUtils.isEmpty(agentForm.getLinkedin()) ? agentForm.getLinkedin() : null);
		agent.setHasPingled(agentForm.getHasPingled());
		agent.setHasAvatar(agentForm.isHasAvatar());
		agent.setUserId(agentForm.hasPresentUserId() ? agentForm.getUserId() : null);
		return agentRepository.save(agent);
	}
	
	@Override
	@Transactional
	public Agent addAgent(final AgentForm agentForm) {
		if(agentRepository.existsByEmail(agentForm.getEmail())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		if(agentRepository.existsByPhone(agentForm.getPhone())) {
			throw new PhoneExistException("message.error.alreadyexist");
		}
		if(!StringUtils.isEmpty(agentForm.getFacebook()) && agentRepository.existsByFacebook(agentForm.getFacebook())) {
			throw new SocialExistException("facebook");
		}
		if(!StringUtils.isEmpty(agentForm.getTwitter()) && agentRepository.existsByTwitter(agentForm.getTwitter())) {
			throw new SocialExistException("twitter");
		}
		if(!StringUtils.isEmpty(agentForm.getLinkedin()) && agentRepository.existsByLinkedin(agentForm.getLinkedin())) {
			throw new SocialExistException("linkedin");
		}
		if(agentForm.hasPresentUserId()) {
			if(!userRepository.existsById(agentForm.getUserId())) {
				throw new NotFoundException("message.input.notfound");
			}
			if(agentRepository.existsByUserId(agentForm.getUserId())) {
				throw new UrlUnavailableException("message.error.alreadyexist");
			}
		}
		final Agent agent = postAgent(new Agent(agentForm.getCompanyId()), agentForm);
		if(agentForm.isHasAvatar()) {
			avatarService.postOrUpdate(agentForm.getFile(), agent.getId(), AvatarType.agent);
		}
		return agent;
	}
	
	@Override
	@Transactional
	public Agent updateAgent(final AgentForm agentForm) {
		final Optional<Agent> uOptional = agentRepository.findById(agentForm.getId());
		if(uOptional.isPresent() && agentForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Agent agent = uOptional.get();
			if(!agentForm.getEmail().equalsIgnoreCase(agent.getEmail()) && agentRepository.existsByEmail(agentForm.getEmail())) {
				throw new AlreadyExistException("message.error.alreadyexist");
			}
			if(!agentForm.getPhone().equals(agent.getPhone()) && agentRepository.existsByPhone(agentForm.getPhone())) {
				throw new PhoneExistException("message.error.alreadyexist");
			}
			if(!StringUtils.isEmpty(agentForm.getFacebook()) && !agentForm.getFacebook().equalsIgnoreCase(agent.getFacebook()) 
					&& agentRepository.existsByFacebook(agentForm.getFacebook())) {
				throw new SocialExistException("facebook");
			}
			if(!StringUtils.isEmpty(agentForm.getTwitter()) && !agentForm.getTwitter().equalsIgnoreCase(agent.getTwitter()) 
					&& agentRepository.existsByTwitter(agentForm.getTwitter())) {
				throw new SocialExistException("twitter");
			}
			if(!StringUtils.isEmpty(agentForm.getLinkedin()) && !agentForm.getLinkedin().equalsIgnoreCase(agent.getLinkedin()) 
					&& agentRepository.existsByLinkedin(agentForm.getLinkedin())) {
				throw new SocialExistException("linkedin");
			}
			if(agentForm.hasPresentUserId() && !agentForm.getUserId().equals(agent.getUserId())) {
				if(!userRepository.existsById(agentForm.getUserId())) {
					throw new NotFoundException("message.input.notfound");
				}
				if(agentRepository.existsByUserId(agentForm.getUserId())) {
					throw new UrlUnavailableException("message.error.alreadyexist");
				}
			}
			if(agentForm.isHasFileChanged()) {
				if(agentForm.isHasAvatar()) {
					avatarService.postOrUpdate(agentForm.getFile(), agent.getId(), AvatarType.agent);
				} else if(agent.getHasAvatar()) {
					avatarService.deleteAvatar(agent.getId(), AvatarType.agent);
				}
			}
			return postAgent(agent, agentForm);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAgentsList(final Long companyId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = agentRepository.countAllAgentCriteria(companyId, filter, search);
		final List<AgentLine> lines = countResult == 0L ? new ArrayList<AgentLine>() 
				: agentRepository.findAllAgentCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Agent deleteAgent(final Long id, final Long companyId) {
		final Optional<Agent> uOptional = agentRepository.findById(id);
		if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
			throw new NotFoundException("message.error.notfound");
		}
		final Agent agent = uOptional.get();
		if(agent.getHasAvatar()) {
			avatarService.deleteAvatar(agent.getId(), AvatarType.agent);
		}
		agentRepository.delete(agent);
		return agent;
	}
	
	@Override
	@Transactional
	public void deleteAgents(final List<Long> lines, final Long companyId) {
		if(lines.isEmpty() || lines.size() != (int) agentRepository.countAgents(companyId, lines)) {
			throw new NotFoundException("message.error.notfound");
		}
		agentRepository.deleteAgents(lines);
		avatarService.deleteAllAvatar(lines, AvatarType.agent);
	}
	
	@Override
	@Transactional
	public void deleteAllAgents(final Long companyId) {
		final List<Long> lines = agentRepository.findAllIdByCompanyId(companyId);
		if(!lines.isEmpty()) {
			agentRepository.deleteByCompanyId(companyId);
			avatarService.deleteAllAvatar(lines, AvatarType.agent);
		}
	}
	
}
