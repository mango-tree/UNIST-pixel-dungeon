/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.unistpixel.unistpixeldungeon.levels.painters;

import com.unistpixel.unistpixeldungeon.Dungeon;
import com.unistpixel.unistpixeldungeon.actors.hero.Belongings;
import com.unistpixel.unistpixeldungeon.actors.mobs.Mob;
import com.unistpixel.unistpixeldungeon.actors.mobs.npcs.ImpShopkeeper;
import com.unistpixel.unistpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.unistpixel.unistpixeldungeon.items.Ankh;
import com.unistpixel.unistpixeldungeon.items.Bomb;
import com.unistpixel.unistpixeldungeon.items.Generator;
import com.unistpixel.unistpixeldungeon.items.Heap;
import com.unistpixel.unistpixeldungeon.items.Honeypot;
import com.unistpixel.unistpixeldungeon.items.Item;
import com.unistpixel.unistpixeldungeon.items.MerchantsBeacon;
import com.unistpixel.unistpixeldungeon.items.Stylus;
import com.unistpixel.unistpixeldungeon.items.Torch;
import com.unistpixel.unistpixeldungeon.items.Weightstone;
import com.unistpixel.unistpixeldungeon.items.armor.LeatherArmor;
import com.unistpixel.unistpixeldungeon.items.armor.MailArmor;
import com.unistpixel.unistpixeldungeon.items.armor.PlateArmor;
import com.unistpixel.unistpixeldungeon.items.armor.ScaleArmor;
import com.unistpixel.unistpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.unistpixel.unistpixeldungeon.items.bags.PotionBandolier;
import com.unistpixel.unistpixeldungeon.items.bags.ScrollHolder;
import com.unistpixel.unistpixeldungeon.items.bags.SeedPouch;
import com.unistpixel.unistpixeldungeon.items.bags.WandHolster;
import com.unistpixel.unistpixeldungeon.items.food.OverpricedRation;
import com.unistpixel.unistpixeldungeon.items.potions.Potion;
import com.unistpixel.unistpixeldungeon.items.potions.PotionOfHealing;
import com.unistpixel.unistpixeldungeon.items.scrolls.Scroll;
import com.unistpixel.unistpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.unistpixel.unistpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.unistpixel.unistpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.unistpixel.unistpixeldungeon.items.wands.Wand;
import com.unistpixel.unistpixeldungeon.items.weapon.melee.BattleAxe;
import com.unistpixel.unistpixeldungeon.items.weapon.melee.Greatsword;
import com.unistpixel.unistpixeldungeon.items.weapon.melee.HandAxe;
import com.unistpixel.unistpixeldungeon.items.weapon.melee.Longsword;
import com.unistpixel.unistpixeldungeon.items.weapon.melee.Mace;
import com.unistpixel.unistpixeldungeon.items.weapon.melee.NewShortsword;
import com.unistpixel.unistpixeldungeon.items.weapon.melee.Sword;
import com.unistpixel.unistpixeldungeon.items.weapon.melee.WarHammer;
import com.unistpixel.unistpixeldungeon.items.weapon.missiles.CurareDart;
import com.unistpixel.unistpixeldungeon.items.weapon.missiles.IncendiaryDart;
import com.unistpixel.unistpixeldungeon.items.weapon.missiles.Javelin;
import com.unistpixel.unistpixeldungeon.items.weapon.missiles.Shuriken;
import com.unistpixel.unistpixeldungeon.items.weapon.missiles.Tamahawk;
import com.unistpixel.unistpixeldungeon.levels.LastShopLevel;
import com.unistpixel.unistpixeldungeon.levels.Level;
import com.unistpixel.unistpixeldungeon.levels.Room;
import com.unistpixel.unistpixeldungeon.levels.Terrain;
import com.unistpixel.unistpixeldungeon.plants.Plant;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;

public class ShopPainter extends Painter {

	private static int pasWidth;
	private static int pasHeight;

	private static ArrayList<Item> itemsToSpawn;
	
	public static void paint( Level level, Room room ) {
		
		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Terrain.EMPTY_SP );
		
		pasWidth = room.width() - 2;
		pasHeight = room.height() - 2;
		int per = pasWidth * 2 + pasHeight * 2;
		
		if (itemsToSpawn == null)
			generateItems();
		
		int pos = xy2p( room, room.entrance() ) + (per - itemsToSpawn.size()) / 2;
		for (Item item : itemsToSpawn) {
			
			Point xy = p2xy( room, (pos + per) % per );
			int cell = xy.x + xy.y * level.width();
			
			if (level.heaps.get( cell ) != null) {
				do {
					cell = level.pointToCell(room.random());
				} while (level.heaps.get( cell ) != null);
			}
			
			level.drop( item, cell ).type = Heap.Type.FOR_SALE;
			
			pos++;
		}
		
		placeShopkeeper( level, room );
		
		for (Room.Door door : room.connected.values()) {
			door.set( Room.Door.Type.REGULAR );
		}

		itemsToSpawn = null;
	}
	
	private static void generateItems() {

		itemsToSpawn = new ArrayList<Item>();
		
		switch (Dungeon.depth) {
		case 6:
			itemsToSpawn.add( (Random.Int( 2 ) == 0 ? new NewShortsword().identify() : new HandAxe()).identify() );
			/* 상점에서 다트 안나오게 함
			itemsToSpawn.add( Random.Int( 2 ) == 0 ?
					new IncendiaryDart().quantity(Random.NormalIntRange(2, 4)) :
					new CurareDart().quantity(Random.NormalIntRange(1, 3)));
			*/
			itemsToSpawn.add( new LeatherArmor().identify() );
			break;
			
		case 11:
			itemsToSpawn.add( (Random.Int( 2 ) == 0 ? new Sword().identify() : new Mace()).identify() );
			itemsToSpawn.add( Random.Int( 2 ) == 0 ?
					new CurareDart().quantity(Random.NormalIntRange(2, 5)) :
					new Shuriken().quantity(Random.NormalIntRange(3, 6)));
			itemsToSpawn.add( new MailArmor().identify() );
			break;
			
		case 16:
			itemsToSpawn.add( (Random.Int( 2 ) == 0 ? new Longsword().identify() : new BattleAxe()).identify() );
			itemsToSpawn.add( Random.Int( 2 ) == 0 ?
					new Shuriken().quantity(Random.NormalIntRange(4, 7)) :
					new Javelin().quantity(Random.NormalIntRange(3, 6)));
			itemsToSpawn.add( new ScaleArmor().identify() );
			break;
			
		case 21:
			itemsToSpawn.add( Random.Int( 2 ) == 0 ? new Greatsword().identify() : new WarHammer().identify() );
			itemsToSpawn.add( Random.Int(2) == 0 ?
					new Javelin().quantity(Random.NormalIntRange(4, 7)) :
					new Tamahawk().quantity(Random.NormalIntRange(4, 7)));
			itemsToSpawn.add( new PlateArmor().identify() );
			itemsToSpawn.add( new Torch() );
			itemsToSpawn.add( new Torch() );
			break;
		}

		itemsToSpawn.add( new MerchantsBeacon() );


		ChooseBag(Dungeon.hero.belongings);


		itemsToSpawn.add( new PotionOfHealing() );
		for (int i=0; i < 3; i++)
			itemsToSpawn.add( Generator.random( Generator.Category.POTION ) );

		itemsToSpawn.add( new ScrollOfIdentify() );
		itemsToSpawn.add( new ScrollOfRemoveCurse() );
		itemsToSpawn.add( new ScrollOfMagicMapping() );
		itemsToSpawn.add( Generator.random( Generator.Category.SCROLL ) );

		for (int i=0; i < 2; i++)
			itemsToSpawn.add( Random.Int(2) == 0 ?
					Generator.random( Generator.Category.POTION ) :
					Generator.random( Generator.Category.SCROLL ) );


		itemsToSpawn.add( new OverpricedRation() );
		itemsToSpawn.add( new OverpricedRation() );

		itemsToSpawn.add( new Bomb().random() );
		switch (Random.Int(5)){
			case 1:
				itemsToSpawn.add( new Bomb() );
				break;
			case 2:
				itemsToSpawn.add( new Bomb().random() );
				break;
			case 3:
			case 4:
				itemsToSpawn.add( new Honeypot() );
				break;
		}


		if (Dungeon.depth == 6) {
			itemsToSpawn.add( new Ankh() );
			itemsToSpawn.add( new Weightstone() );
		} else {
			itemsToSpawn.add(Random.Int(2) == 0 ? new Ankh() : new Weightstone());
		}


		TimekeepersHourglass hourglass = Dungeon.hero.belongings.getItem(TimekeepersHourglass.class);
		if (hourglass != null){
			int bags = 0;
			//creates the given float percent of the remaining bags to be dropped.
			//this way players who get the hourglass late can still max it, usually.
			switch (Dungeon.depth) {
				case 6:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.20f ); break;
				case 11:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.25f ); break;
				case 16:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.50f ); break;
				case 21:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.80f ); break;
			}

			for(int i = 1; i <= bags; i++){
				itemsToSpawn.add( new TimekeepersHourglass.sandBag());
				hourglass.sandBags ++;
			}
		}

		Item rare = Generator.random(Generator.Category.RING);
		rare.level( 1 );
		/* 반지만 나오게 설정
		switch (Random.Int(10)){
			case 0:
				rare = Generator.random( Generator.Category.WAND );
				rare.level( 0 );
				break;
			case 1:
				rare = Generator.random(Generator.Category.RING);
				rare.level( 1 );
				break;
			case 2:
				rare = Generator.random( Generator.Category.ARTIFACT ).identify();
				break;
			default:
				rare = new Stylus();
		}
		*/
		rare.cursed = rare.cursedKnown = false;
		itemsToSpawn.add( rare );

		//this is a hard limit, level gen allows for at most an 8x5 room, can't fit more than 39 items + 1 shopkeeper.
		if (itemsToSpawn.size() > 39)
			throw new RuntimeException("Shop attempted to carry more than 39 items!");

		Collections.shuffle(itemsToSpawn);
	}

	private static void ChooseBag(Belongings pack){

		int seeds = 0, scrolls = 0, potions = 0, wands = 0;

		//count up items in the main bag, for bags which haven't yet been dropped.
		for (Item item : pack.backpack.items) {
			if (!Dungeon.limitedDrops.seedBag.dropped() && item instanceof Plant.Seed)
				seeds++;
			else if (!Dungeon.limitedDrops.scrollBag.dropped() && item instanceof Scroll)
				scrolls++;
			else if (!Dungeon.limitedDrops.potionBag.dropped() && item instanceof Potion)
				potions++;
			else if (!Dungeon.limitedDrops.wandBag.dropped() && item instanceof Wand)
				wands++;
		}

		//then pick whichever valid bag has the most items available to put into it.
		//note that the order here gives a perference if counts are otherwise equal
		/*
		if (seeds >= scrolls && seeds >= potions && seeds >= wands && !Dungeon.limitedDrops.seedBag.dropped()) {
			Dungeon.limitedDrops.seedBag.drop();
			itemsToSpawn.add( new SeedPouch() );

		} else if (scrolls >= potions && scrolls >= wands && !Dungeon.limitedDrops.scrollBag.dropped()) {
			Dungeon.limitedDrops.scrollBag.drop();
			itemsToSpawn.add( new ScrollHolder() );

		} else if (potions >= wands && !Dungeon.limitedDrops.potionBag.dropped()) {
			Dungeon.limitedDrops.potionBag.drop();
			itemsToSpawn.add( new PotionBandolier() );

		} else if (!Dungeon.limitedDrops.wandBag.dropped()) {
			Dungeon.limitedDrops.wandBag.drop();
			itemsToSpawn.add(new WandHolster());
		}
		*/
		// 무조건 scrollBag하고 potionBag 생기게 함
		Dungeon.limitedDrops.scrollBag.drop();
		itemsToSpawn.add( new ScrollHolder() );
		Dungeon.limitedDrops.potionBag.drop();
		itemsToSpawn.add( new PotionBandolier() );
	}

	public static int spaceNeeded(){
		if (itemsToSpawn == null)
			generateItems();

		//plus one for the shopkeeper
		return itemsToSpawn.size() + 1;
	}
	
	private static void placeShopkeeper( Level level, Room room ) {
		
		int pos;
		do {
			pos = level.pointToCell(room.random());
		} while (level.heaps.get( pos ) != null);
		
		Mob shopkeeper = level instanceof LastShopLevel ? new ImpShopkeeper() : new Shopkeeper();
		shopkeeper.pos = pos;
		level.mobs.add( shopkeeper );
		
		if (level instanceof LastShopLevel) {
			for (int i = 0; i < PathFinder.NEIGHBOURS9.length; i++) {
				int p = shopkeeper.pos + PathFinder.NEIGHBOURS9[i];
				if (level.map[p] == Terrain.EMPTY_SP) {
					level.map[p] = Terrain.WATER;
				}
			}
		}
	}
	
	private static int xy2p( Room room, Point xy ) {
		if (xy.y == room.top) {
			
			return (xy.x - room.left - 1);
			
		} else if (xy.x == room.right) {
			
			return (xy.y - room.top - 1) + pasWidth;
			
		} else if (xy.y == room.bottom) {
			
			return (room.right - xy.x - 1) + pasWidth + pasHeight;
			
		} else {
			
			if (xy.y == room.top + 1) {
				return 0;
			} else {
				return (room.bottom - xy.y - 1) + pasWidth * 2 + pasHeight;
			}
			
		}
	}
	
	private static Point p2xy( Room room, int p ) {
		if (p < pasWidth) {
			
			return new Point( room.left + 1 + p, room.top + 1);
			
		} else if (p < pasWidth + pasHeight) {
			
			return new Point( room.right - 1, room.top + 1 + (p - pasWidth) );
			
		} else if (p < pasWidth * 2 + pasHeight) {
			
			return new Point( room.right - 1 - (p - (pasWidth + pasHeight)), room.bottom - 1 );
			
		} else {
			
			return new Point( room.left + 1, room.bottom - 1 - (p - (pasWidth * 2 + pasHeight)) );
			
		}
	}
}
