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
import com.watabou.noosa.TextureFilm;

public class CrabSprite extends MobSprite {

	public CrabSprite() {
		super();

		texture( Assets.CRAB );

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

		idle = new Animation( 5, true );
		idle.frames( frames, 0, 1, 2, 3, 4, 5 );

		run = new Animation( 15, true );
		run.frames( frames, 6, 7, 8, 9 );

		attack = new Animation( 18, false );
		attack.frames( frames, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);

		die = new Animation( 12, false );
		die.frames( frames, 22, 23, 24, 25 );

		play( idle );
	}

	@Override
	public int blood() {
		return 0xFFFFEA80;
	}
}
