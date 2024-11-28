package ls.yield.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Document {

	@Column
	public String name;
	
	@JsonProperty("mime_type")
	@Column
	public String mimeType;
	
	@Column
	public String content;
	
	public Document() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
