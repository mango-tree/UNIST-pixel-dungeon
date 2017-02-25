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
package com.unistpixel.unistpixeldungeon.sprites;

import com.unistpixel.unistpixeldungeon.Assets;
import com.unistpixel.unistpixeldungeon.Dungeon;
import com.unistpixel.unistpixeldungeon.items.weapon.missiles.CurareDart;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class GnollTricksterSprite extends MobSprite {

	private Animation cast;

	public GnollTricksterSprite() {
		super();

		texture( Assets.GNOLL );

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

		idle = new MovieClip.Animation( 2, true );
		idle.frames( frames, 16, 17, 18 );

		run = new MovieClip.Animation( 12, true );
		run.frames( frames, 19, 20, 25  );

		attack = new MovieClip.Animation( 12, false );
		attack.frames( frames, 22, 23, 24, 25, 26 );

		cast = attack.clone();

		die = new MovieClip.Animation( 12, false );
		die.frames( frames, 27, 28, 29, 30 );

		play( idle );
	}

	@Override
	public void attack( int cell ) {
		if (!Dungeon.level.adjacent(cell, ch.pos)) {

			((MissileSprite)parent.recycle( MissileSprite.class )).
					reset( ch.pos, cell, new CurareDart(), new Callback() {
						@Override
						public void call() {
							ch.onAttackComplete();
						}
					} );

			play( cast );
			turnTo( ch.pos , cell );

		} else {

			super.attack( cell );

		}
	}
}
