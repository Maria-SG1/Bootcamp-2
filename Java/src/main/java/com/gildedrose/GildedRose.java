package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
		String nombre = items[0].name;
		Item item = items[0];
		
		switch (nombre) {
		case "Aged Brie":
			if (item.quality < 50) {
				item.quality += 1;
			}
			item.sellIn-=1;
			if (item.sellIn<0 && item.quality<50) {
				item.quality += 1;
			}
			break;

		case "Sulfuras, Hand of Ragnaros":
			break;

		case "Backstage passes to a TAFKAL80ETC concert":
			if (item.quality < 50) {
				item.quality += 1;
				if (item.sellIn < 11) {					
					item.quality = item.quality + 1;					
				}

				if (item.sellIn < 6) {					
					item.quality = item.quality + 1;					
				}
			}
			item.sellIn-=1;
			if (item.sellIn < 0) {
				item.quality = 0;
			}			
			break;
			
		case "Conjured Mana Cake":
			if (item.quality > 1) {
				item.quality -= 2;				
				if (item.sellIn<0) {
					item.quality-=2;
				}				
			}
			if (item.quality==1) {
				item.quality=0;
			}
			item.sellIn-=1;	
			break;
			
		default:
			if (item.quality > 0) {
				item.quality -= 1;
				if (item.sellIn < 0) {
					item.quality -= 1;
				}
			} else {
				item.quality=0;
			}	
			item.sellIn-=1;
		}
	}
}


	    
	    


