package ls.yield.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class PatchAccreditation {
	
	@JsonProperty("outcome")
	@Enumerated(EnumType.STRING)
	public AccreditationOutcome accrOutcome;

	public PatchAccreditation(AccreditationOutcome accrOutcome) {
		this.accrOutcome = accrOutcome;
	}

	public AccreditationOutcome getAccrOutcome() {
		return accrOutcome;
	}

	public void setAccrOutcome(AccreditationOutcome accrOutcome) {
		this.accrOutcome = accrOutcome;
	}

}
