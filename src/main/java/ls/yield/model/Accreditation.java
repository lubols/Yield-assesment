package ls.yield.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "taccreditation")
public class Accreditation {

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE)
	public Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date timestamp;
	
	@JsonProperty("user_id")
	@Column
	public String userId;
	
	@JsonProperty("accreditation_type")
	@Enumerated(EnumType.STRING)
	public AccreditationType accrType;
	
	@JsonProperty("accreditation_id")
	@Column
	public String accrId;
	
	public String accrStatus;
	
	public Document document;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getUser_id() {
		return userId;
	}
	public void setUser_id(String user_id) {
		this.userId = user_id;
	}
	public AccreditationType getAccrType() {
		return accrType;
	}
	public void setAccrType(AccreditationType accrType) {
		this.accrType = accrType;
	}
	public String getAccrId() {
		return accrId;
	}
	public void setAccrId(String accrId) {
		this.accrId = accrId;
	}
	public String getAccrStatus() {
		return accrStatus;
	}
	public void setAccrStatus(String accrStatus) {
		this.accrStatus = accrStatus;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
		
}
