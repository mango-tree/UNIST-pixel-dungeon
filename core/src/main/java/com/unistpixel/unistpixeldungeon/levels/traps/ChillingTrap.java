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
package com.unistpixel.unistpixeldungeon.levels.traps;

import com.unistpixel.unistpixeldungeon.Assets;
import com.unistpixel.unistpixeldungeon.Dungeon;
import com.unistpixel.unistpixeldungeon.actors.Actor;
import com.unistpixel.unistpixeldungeon.actors.Char;
import com.unistpixel.unistpixeldungeon.actors.buffs.Chill;
import com.unistpixel.unistpixeldungeon.effects.Splash;
import com.unistpixel.unistpixeldungeon.items.Heap;
import com.unistpixel.unistpixeldungeon.messages.Messages;
import com.unistpixel.unistpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class ChillingTrap extends Trap{

	{
		color = WHITE;
		shape = DOTS;
	}

	@Override
	public void activate() {
		if (Dungeon.visible[ pos ]){
			Splash.at( pos, 0xFFB2D6FF, 5);
			Sample.INSTANCE.play( Assets.SND_SHATTER );
		}

		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null) heap.freeze();

		Char ch = Actor.findChar( pos );
		if (ch != null){
			Chill.prolong(ch, Chill.class, 5f + Random.Int(Dungeon.depth));
			ch.damage(Random.NormalIntRange(1 , Dungeon.depth), this);
			if (!ch.isAlive() && ch == Dungeon.hero){
				Dungeon.fail( getClass() );
				GLog.n( Messages.get(this, "ondeath") );
			}
		}
	}
}
