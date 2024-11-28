package ls.yield.service;

import java.util.List;

import ls.yield.model.Accreditation;
import ls.yield.model.PatchAccreditation;

public interface AccreditationService {

	public List<Accreditation> getAccreditations();

	public Accreditation getAccreditation(String id);
	
	public List<Accreditation> getAccreditationsByUser(String id);

	public Accreditation createAccreditation(Accreditation accreditation) throws Exception;
	
	public Accreditation updateAccreditation(Accreditation accreditation, PatchAccreditation patchAccr) throws Exception;

	List<Accreditation> getPendingAccrByUser(String id);

	List<Accreditation> getConfirmedAccr();
}
