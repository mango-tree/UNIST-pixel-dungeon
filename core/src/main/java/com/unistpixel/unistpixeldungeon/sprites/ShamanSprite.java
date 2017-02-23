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
import com.unistpixel.unistpixeldungeon.actors.mobs.Shaman;
import com.unistpixel.unistpixeldungeon.effects.Lightning;
import com.watabou.noosa.TextureFilm;

public class ShamanSprite extends MobSprite {
	
	public ShamanSprite() {
		super();
		
		texture( Assets.SHAMAN );
		
		TextureFilm frames = new TextureFilm( texture, 12, 15 );
		
		idle = new Animation( 15, true );
		idle.frames( frames, 0, 1, 2, 3, 4 );
		
		run = new Animation( 12, true );
		run.frames( frames, 5, 6, 7, 8, 9, 10 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 );
		
		zap = attack.clone();
		
		die = new Animation( 12, false );
		die.frames( frames, 21, 22, 23, 24 );
		
		play( idle );
	}
	
	public void zap( int pos ) {

		parent.add( new Lightning( ch.pos, pos, (Shaman)ch ) );
		
		turnTo( ch.pos, pos );
		play( zap );
	}
}
