package com.sistema.app.models;

import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="teacher")
public class Teacher {

	@Id
	private String id;
	
	@NotEmpty
	private String namesTeacher;
	@NotEmpty
	private String surnamesTeacher;
	@NotEmpty
	private String typeDocument;
	@NotEmpty
	private String numberDocument;
	
}
