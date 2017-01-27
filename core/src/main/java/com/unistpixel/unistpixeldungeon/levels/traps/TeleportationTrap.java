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
import com.unistpixel.unistpixeldungeon.actors.hero.Hero;
import com.unistpixel.unistpixeldungeon.effects.CellEmitter;
import com.unistpixel.unistpixeldungeon.effects.Speck;
import com.unistpixel.unistpixeldungeon.items.Heap;
import com.unistpixel.unistpixeldungeon.items.Item;
import com.unistpixel.unistpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.unistpixel.unistpixeldungeon.messages.Messages;
import com.unistpixel.unistpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class TeleportationTrap extends Trap {

	{
		color = TEAL;
		shape = DOTS;
	}

	@Override
	public void activate() {

		CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
		Sample.INSTANCE.play( Assets.SND_TELEPORT );

		Char ch = Actor.findChar( pos);
		if (ch instanceof Hero){
			ScrollOfTeleportation.teleportHero( (Hero)ch);
		} else if (ch != null){
			int count = 10;
			int pos;
			do {
				pos = Dungeon.level.randomRespawnCell();
				if (count-- <= 0) {
					break;
				}
			} while (pos == -1);

			if (pos == -1 || Dungeon.bossLevel()) {

				GLog.w( Messages.get(ScrollOfTeleportation.class, "no_tele") );

			} else {

				ch.pos = pos;
				ch.sprite.place(ch.pos);
				ch.sprite.visible = Dungeon.visible[pos];

			}
		}

		Heap heap = Dungeon.level.heaps.get(pos);

		if (heap != null){
			int cell = Dungeon.level.randomRespawnCell();

			Item item = heap.pickUp();

			if (cell != -1) {
				Dungeon.level.drop( item, cell );
			}
		}
	}
}
