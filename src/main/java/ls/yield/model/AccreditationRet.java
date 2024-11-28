package ls.yield.model;

import java.util.Map;

public class AccreditationRet {
	
	public String user_id;
	Map<String, Map<String, String>> accreditation_statuses;
	
	public AccreditationRet() {
	}
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Map<String, Map<String, String>> getAccreditation_statuses() {
		return accreditation_statuses;
	}

	public void setAccreditation_statuses(Map<String, Map<String, String>> accreditation_statuses) {
		this.accreditation_statuses = accreditation_statuses;
	}
	
}
