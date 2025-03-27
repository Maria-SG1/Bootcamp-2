package com.example.domains.entities.dto;

import com.example.domain.entities.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor
@Schema(name="Categoría")
public class CategoryDTO {
	private int categoryId;
	private String name;
	
	public static CategoryDTO from(Category source) {
		return new CategoryDTO(source.getCategoryId(), source.getName());
	}
	
	public static Category from(CategoryDTO source) {
		return new Category(source.getCategoryId(), source.getName());
	}
}
