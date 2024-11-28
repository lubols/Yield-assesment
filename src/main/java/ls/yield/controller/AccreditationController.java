package ls.yield.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ls.yield.model.Accreditation;
import ls.yield.model.AccreditationOutcome;
import ls.yield.model.AccreditationRet;
import ls.yield.model.PatchAccreditation;
import ls.yield.service.AccreditationService;

/*
 * Main controller to handle accreditation actions
 */
@RestController
@RequestMapping("/user")
public class AccreditationController extends ExceptionController {

	@Autowired
	private AccreditationService service;
	
	/*
	 * Process running once per day, checks for all accreditations with status CONFIRMED and older then 30 days.
	 */
	@Scheduled(cron = "* * 0 * * *")
	public void reportCurrentTime() throws Exception {
		List<Accreditation> confAccr = service.getConfirmedAccr();
    	for (Iterator<Accreditation> iterator = confAccr.iterator(); iterator.hasNext();) {
			Accreditation accreditation = (Accreditation) iterator.next();
			service.updateAccreditation(accreditation, new PatchAccreditation(AccreditationOutcome.EXPIRED));
		}
	}
	
	/*
	 * Register new accreditation
	 * Creation works only when user does not have any active accreditations with status PENDING
	 */
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/accreditation")
	public Map<String, String> createAccreditation(@RequestBody Accreditation accr) throws Exception {
		List<Accreditation> pendingAccr = Optional.ofNullable(accr.getUser_id())
				.map(u -> accr.getUser_id())
				.map(service::getPendingAccrByUser)
				.orElseThrow();
		
		if (pendingAccr.isEmpty()) {
			accr.setTimestamp(new Date());
			Accreditation accrRet = service.createAccreditation(accr); 
			Map<String, String> responseProperties = new HashMap<String, String>();
			responseProperties.put("accreditation_id", accrRet.getAccrId());
			return responseProperties;
		} else {
			throw new Exception ("pending_exists");
		}
	}
	
	/*
	 * Modify existing accreditation
	 * Status cannot be changed if it is FAILED
	 * Option to change status to EXPIRED only when it's already set to CONFIRMED 
	 */
	@PutMapping("accreditation/{id}")
	public Map<String, String> updateAccreditation(@PathVariable("id") String accreditationId,
			@RequestBody PatchAccreditation patchAccr) throws Exception {
		Accreditation activeAccr = Optional.ofNullable(accreditationId)
				.map(u -> accreditationId)
				.map(service::getAccreditation)
				.orElseThrow();
		
		int accr = activeAccr.getAccrStatus().hashCode();
		int patc = patchAccr.getAccrOutcome().name().hashCode();
		int fail = AccreditationOutcome.FAILED.name().hashCode();
		int conf = AccreditationOutcome.CONFIRMED.name().hashCode();
		int expi = AccreditationOutcome.EXPIRED.name().hashCode();
		
		if(accr == fail){
			throw new Exception ("failed_cannot_be_modified");
		} else if (patc == expi && accr != conf) {
			throw new Exception ("expire_only_confirmed");
		} else {
			Accreditation accrRet = service.updateAccreditation(activeAccr, patchAccr);
			
			Map<String, String> responseProperties = new HashMap<String, String>();
			responseProperties.put("accreditation_id", accrRet.getAccrId());
			return responseProperties;
		}
	}
	
	/*
	 * Returns all accreditation statuses linked to the provided user ID.
	 */
	@GetMapping("/{id}/accreditation")
	public AccreditationRet getAccreditations(@PathVariable("id") String userId) {
		List<Accreditation> activeAccr = Optional.ofNullable(userId)
				.map(u -> userId)
				.map(service::getAccreditationsByUser)
				.orElseThrow();
		
		AccreditationRet accrRet = new AccreditationRet();
		accrRet.setUser_id(userId);
		
		Map<String, Map<String, String>> accreditation_statuses = new HashMap<String, Map<String, String>>();
		
		for (Iterator<Accreditation> iterator = activeAccr.iterator(); iterator.hasNext();) {
			Accreditation accreditation = (Accreditation) iterator.next();
			Map<String, String> accrStatuses = new HashMap<String, String>();
			accrStatuses.put("accreditation_type", accreditation.getAccrType().toString());
			accrStatuses.put("status", accreditation.getAccrStatus());
			
			accreditation_statuses.put(accreditation.getAccrId(), accrStatuses);
		}
		
		accrRet.setAccreditation_statuses(accreditation_statuses);
		
		return accrRet;
	}
}
