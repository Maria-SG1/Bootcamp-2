package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonaDTO {
	private long id;
	private String nombre; 
	private String apellidos; 
	private String correo; 
	private String sexo; 
	private String ip;
}
