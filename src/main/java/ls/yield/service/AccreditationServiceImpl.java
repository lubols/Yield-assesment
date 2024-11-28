package ls.yield.service;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ls.yield.model.Accreditation;
import ls.yield.model.AccreditationOutcome;
import ls.yield.model.AccreditationType;
import ls.yield.model.PatchAccreditation;
import ls.yield.repository.AccreditationRepository;

@Service
public class AccreditationServiceImpl implements AccreditationService{
	
	@Autowired
	private AccreditationRepository accrRepo;

	/*
	 * Returns all records
	 */
	@Override
	public List<Accreditation> getAccreditations() {
		return accrRepo.findAll();
	}
	
	/*
	 * Returns all records for specified user
	 */
	@Override
	public List<Accreditation> getAccreditationsByUser(String id) {
		return accrRepo.findAccrByUserId(id);
	}
	
	/*
	 * Returns all records for specified user with status PENDING
	 */
	@Override
	public List<Accreditation> getPendingAccrByUser(String id) {
		return accrRepo.findPendingAccrByUserId(id);
	}
	
	/*
	 * Returns all records for specified user with status CONFIRMED
	 */
	@Override
	public List<Accreditation> getConfirmedAccr() {
		return accrRepo.findConfirmedAccrByUserId();
	}

	/*
	 * Returns specific accreditation by its ID
	 */
	@Override
	public Accreditation getAccreditation(String id) {
		Accreditation accr = accrRepo.findAccrByAccrId(id);
		return accr;
	}

	/*
	 * Saves record in db 
	 * Works only for specified accreditation types
	 */
	@Override
	public Accreditation createAccreditation(Accreditation accr) throws Exception {
		if(EnumSet.allOf(AccreditationType.class).contains(accr.getAccrType())) {
			accr.setAccrId(generateAccrId());
			accr.setAccrStatus("PENDING");
			return accrRepo.save(accr);
		} else 
			throw new Exception("wrong_accr_type");
	}

	/*
	 * Update accreditation status 
	 * Works only for specified accreditation statuses
	 */
	@Override
	public Accreditation updateAccreditation(Accreditation accreditation, PatchAccreditation patchAccr) throws Exception {
		if(EnumSet.allOf(AccreditationOutcome.class).contains(patchAccr.getAccrOutcome())) {
			accreditation.setAccrStatus(patchAccr.getAccrOutcome().toString());
			return accrRepo.save(accreditation);
		} else 
			throw new Exception("wrong_accr_outcome");
	}
	
	/*
	 * Generate random identifier
	 */
	private String generateAccrId() {
		return UUID.randomUUID().toString();
	}

}
