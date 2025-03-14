package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GildedRoseTest {

	private void comparar(String resultado, Item[] items) {
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		assertEquals(resultado, app.items[0].toString());
	}
	
	private Item[] nuevoProducto(String nombre, int sellIn, int quality) {
		return new Item[] {new Item(nombre, sellIn, quality)};
	}
	
	@Nested
	@DisplayName("Elexir of the mongoose")
	class Elixir {
		@ParameterizedTest
		@CsvSource({
			"10, 30, Elixir of the Mongoose,  9, 29",
			"10, 0, Elixir of the Mongoose, 9, 0"
		})
		void elexirTest(int sellIn, int quality, String nombre, int resultSellIn, int resultQuality) {
			comparar(nombre+", "+resultSellIn+", "+ resultQuality, nuevoProducto(nombre, sellIn, quality));
		}
	}
	
	@Nested
	@DisplayName("Aged Brie")
	class AgedBrie {
		@ParameterizedTest
		@CsvSource({
			"10, 50, Aged Brie,  9, 50",
			"2, 0, Aged Brie, 1, 1",
			"-1, 40, Aged Brie, -2, 42",
			"-1, 50, Aged Brie, -2, 50",			
		})
		void elexirTest(int sellIn, int quality, String nombre, int resultSellIn, int resultQuality) {
			comparar(nombre+", "+resultSellIn+", "+ resultQuality, nuevoProducto(nombre, sellIn, quality));
		}
	}
	
	@Nested
	@DisplayName("Backstage passes to a TAFKAL80ETC concert")
	class Backstage {
		@ParameterizedTest
		@CsvSource({
			"9, 40, Backstage passes to a TAFKAL80ETC concert,  8, 42",
			"11, 40, Backstage passes to a TAFKAL80ETC concert, 10, 41",
			"10, 50, Backstage passes to a TAFKAL80ETC concert, 9, 50",
			"4, 40, Backstage passes to a TAFKAL80ETC concert, 3, 43",
			"0, 40, Backstage passes to a TAFKAL80ETC concert, -1, 0"
		})
		void elexirTest(int sellIn, int quality, String nombre, int resultSellIn, int resultQuality) {
			comparar(nombre+", "+resultSellIn+", "+ resultQuality, nuevoProducto(nombre, sellIn, quality));
		}
	}
	
	@Nested
	@DisplayName("Conjured Mana Cake")
	class Conjured {
		@ParameterizedTest
		@CsvSource({
			"3, 10, Conjured Mana Cake,  2, 8",
			"-1, 10, Conjured Mana Cake, -2, 6",
			"3, 1, Conjured Mana Cake, 2, 0"			
		})
		void elexirTest(int sellIn, int quality, String nombre, int resultSellIn, int resultQuality) {
			comparar(nombre+", "+resultSellIn+", "+ resultQuality, nuevoProducto(nombre, sellIn, quality));
		}
	}
	
	@Nested
	@DisplayName("Otros productos")
	class Otros {
		@ParameterizedTest
		@CsvSource({
			"-1, 10, +5 Dexterity Vest,  -2, 8",
			"-1, 80, 'Sulfuras, Hand of Ragnaros', -1, 80",
			"5, 80, 'Sulfuras, Hand of Ragnaros', 5, 80",
			"5, 60, 'Sulfuras, Hand of Ragnaros', 5, 60"
		})
		void elexirTest(int sellIn, int quality, String nombre, int resultSellIn, int resultQuality) {
			comparar(nombre+", "+resultSellIn+", "+ resultQuality, nuevoProducto(nombre, sellIn, quality));
		}
	}

    
    /* Utilizando Approvals.verify */
    
    @Test
//	@Disabled
	void instantanea() {
		Item[] items = new Item[] { 
				new Item("+5 Dexterity Vest", 10, 20), 
				new Item("Aged Brie", 2, 0),
				new Item("Elixir of the Mongoose", 5, 7), 
				new Item("Sulfuras, Hand of Ragnaros", 0, 80),
				new Item("Sulfuras, Hand of Ragnaros", -1, 80),
				new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
				new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
				new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
				// this conjured item does not work properly yet 
				new Item("Conjured Mana Cake", 3, 6) 
				};

		GildedRose app = new GildedRose(items);
		StringBuilder output = new StringBuilder();
		output.append("day,name, sellIn, quality\n");
		List.of(items).forEach(item -> output.append("0," + item + "\n"));
		for (int i = 1; i <= 31; i++) {
			app.updateQuality();
			for(Item item: items)
				output.append(i + "," + item + "\n");
		}
		Approvals.verify(output);
	

 }   
    }

