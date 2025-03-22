package com.example.application.controllers;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.contracts.CatalogoService;
import com.example.application.models.NovedadesDTO;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping(path = "/")
public class CatalogoController {
	
	@Autowired
	private CatalogoService srv;
	
	@GetMapping(path = "/novedades/v1")
	public NovedadesDTO novedades(@Parameter(example = "2021-01-01 00:00:00") @RequestParam(required = false, defaultValue = "2021-01-01 00:00:00") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date fecha) {
		if(fecha == null)
			fecha = Date.from(Instant.now().minusSeconds(36000));
		return srv.novedades(fecha);
	}
}
