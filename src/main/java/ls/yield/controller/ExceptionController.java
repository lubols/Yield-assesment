package ls.yield.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ls.yield.model.ErrorMessage;

@ControllerAdvice
public class ExceptionController {

	// Exceptions handling
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorMessage handleGeneric(Exception e) {
		if (e.getMessage().contains("BY_INCOME") || e.getMessage().contains("BY_NET"))
			return new ErrorMessage(451, "Wrong accreditation type!");
		else if (e.getMessage().contains("CONFIRMED") || e.getMessage().contains("EXPIRED") || e.getMessage().contains("FAILED"))
			return new ErrorMessage(452, "Wrong accreditation outcome!");
		else if (e.getMessage().contains("failed_cannot_be_modified"))
			return new ErrorMessage(453, "FAILED status cannot be updated further!");
		else if (e.getMessage().contains("expire_only_confirmed"))
			return new ErrorMessage(454, "Only CONFIRMED status can be EXPIRED!");
		else if (e.getMessage().contains("pending_exists"))
			return new ErrorMessage(454, "There's already PENDING accreditation, cannot create new one!");
		else 
			return new ErrorMessage(450, "Generic error: " + e.toString());
	}

}