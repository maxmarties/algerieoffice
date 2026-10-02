package com.rinitec.algerieoffice.services.analytic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.AgentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.security.users.ActiveUserStore;
import com.rinitec.algerieoffice.web.modal.analytic.OfficeSkills;

@Service
public class SkillsService implements ISkillsService {

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private AgentRepository agentRepository;
	private PostRepository postRepository;
	private AnnonceRepository annonceRepository;
	private EventRepository eventRepository;
	private EmployeRepository employeRepository;
	private ActiveUserStore activeUserStore;
	
	@Autowired
	public SkillsService(UserRepository userRepository, CompanyRepository companyRepository, AgentRepository agentRepository, PostRepository postRepository, 
			AnnonceRepository annonceRepository, EventRepository eventRepository, EmployeRepository employeRepository, ActiveUserStore activeUserStore) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.agentRepository = agentRepository;
		this.postRepository = postRepository;
		this.annonceRepository = annonceRepository;
		this.eventRepository = eventRepository;
		this.employeRepository = employeRepository;
		this.activeUserStore = activeUserStore;
	}
	
	@Override
	@Transactional(readOnly = true)
	public OfficeSkills readOfficeSkills() {
		final Long[] count = new Long[4];
		count[0] = userRepository.countAllActiveUser();
		count[1] = companyRepository.countCompaniesForSector(null, null);
		count[2] = agentRepository.countAgentsForSector(null, null, null);
		count[3] = (long) activeUserStore.countLogged();
		return new OfficeSkills(count);
	}
	
	@Override
	@Transactional(readOnly = true)
	public OfficeSkills readMarketplaceSkills() {
		final Long[] count = new Long[4];
		count[0] = postRepository.countAllActivePost();
		count[1] = annonceRepository.countAllActiveAnnonce();
		count[2] = eventRepository.countAllActiveEvent();
		count[3] = employeRepository.countAllActiveEmploye();
		return new OfficeSkills(count);
	}
	
}
