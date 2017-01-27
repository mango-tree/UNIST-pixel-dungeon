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
package com.unistpixel.unistpixeldungeon.items.weapon.enchantments;

import com.unistpixel.unistpixeldungeon.actors.Char;
import com.unistpixel.unistpixeldungeon.actors.buffs.Buff;
import com.unistpixel.unistpixeldungeon.actors.buffs.Poison;
import com.unistpixel.unistpixeldungeon.effects.CellEmitter;
import com.unistpixel.unistpixeldungeon.effects.particles.PoisonParticle;
import com.unistpixel.unistpixeldungeon.items.weapon.Weapon;
import com.unistpixel.unistpixeldungeon.sprites.ItemSprite;
import com.unistpixel.unistpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Venomous extends Weapon.Enchantment {

	private static ItemSprite.Glowing PURPLE = new ItemSprite.Glowing( 0x4400AA );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		// lvl 0 - 33%
		// lvl 1 - 50%
		// lvl 2 - 60%
		int level = Math.max( 0, weapon.level() );
		
		if (Random.Int( level + 3 ) >= 2) {
			
			Buff.affect( defender, Poison.class ).extend( Poison.durationFactor( defender ) * ((level/2) + 1) );
			CellEmitter.center(defender.pos).burst( PoisonParticle.SPLASH, 5 );

		}

		return damage;
	}
	
	@Override
	public Glowing glowing() {
		return PURPLE;
	}
}
