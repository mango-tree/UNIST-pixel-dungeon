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
package com.unistpixel.unistpixeldungeon.scenes;

import android.content.Intent;
import android.net.Uri;
import com.unistpixel.unistpixeldungeon.UNISTPixelDungeon;
import com.unistpixel.unistpixeldungeon.effects.Flare;
import com.unistpixel.unistpixeldungeon.ui.Archs;
import com.unistpixel.unistpixeldungeon.ui.ExitButton;
import com.unistpixel.unistpixeldungeon.ui.Icons;
import com.unistpixel.unistpixeldungeon.ui.RenderedTextMultiline;
import com.unistpixel.unistpixeldungeon.ui.Window;
import com.watabou.input.Touchscreen.Touch;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;

public class AboutScene extends PixelScene {

	private static final String TTL_UPD = "UNIST Pixel Dungeon";

	private static final String TXT_UPD =
			"Design, Code, & Graphics\n" +
			"\t\t\t\t\t\t\t\t\t- UNIST HeXA -";

	private static final String LNK_UPD =
			"Github.com/mango-tree/UNIST-pixel-dungeon";


	private static final String TTL_SHPX = "Shattered Pixel Dungeon";

	private static final String TXT_SHPX =
			"Design, Code, & Graphics: Evan";

	private static final String LNK_SHPX = "ShatteredPixel.com";
	
	@Override
	public void create() {
		super.create();

		final float colWidth = Camera.main.width / (UNISTPixelDungeon.landscape() ? 2 : 1);
		final float colTop = (Camera.main.height / 2) - (UNISTPixelDungeon.landscape() ? 30 : 90);
		final float shpxOffset = UNISTPixelDungeon.landscape() ? colWidth : 0;

		final int UPD_COLOR = 0x88bfe8; // 하늘색
		Image UPD = Icons.UPD.get();
		UPD.x = (colWidth - UPD.width()) / 2;
		UPD.y = colTop;
		align(UPD);
		add( UPD );

		new Flare( 7, 100 ).color( 0x112233, true ).show( UPD, 0 ).angularSpeed = +20;

		RenderedText UPDtitle = renderText(TTL_UPD, 8 );
		UPDtitle.hardlight( UPD_COLOR );
		add( UPDtitle );

		UPDtitle.x = (colWidth - UPDtitle.width()) / 2;
		UPDtitle.y = UPD.y + UPD.height + 5;
		align(UPDtitle);

		RenderedTextMultiline UPDtext = renderMultiline(TXT_UPD, 8 );
		UPDtext.maxWidth((int)Math.min(colWidth, 120));
		add( UPDtext );

		UPDtext.setPos((colWidth - UPDtext.width()) / 2, UPDtitle.y + UPDtitle.height() + 12);
		align(UPDtext);

		RenderedTextMultiline UPDlink = renderMultiline(LNK_UPD, 7 );
		UPDlink.maxWidth(UPDtext.maxWidth());
		UPDlink.hardlight( UPD_COLOR );
		add( UPDlink );

		UPDlink.setPos((colWidth - UPDlink.width()) / 2, UPDtext.bottom() + 6);
		align(UPDlink);

		TouchArea shpxhotArea = new TouchArea( UPDlink.left(), UPDlink.top(), UPDlink.width(), UPDlink.height() ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://" + LNK_UPD) );
				Game.instance.startActivity( intent );
			}
		};
		add( shpxhotArea );

		Image shpx = Icons.SHPX.get();
		shpx.x = shpxOffset + (colWidth - shpx.width()) / 2;
		shpx.y = UNISTPixelDungeon.landscape() ?
						colTop + 15:
						UPDlink.top() + shpx.height + 20;
		align(shpx);
		add( shpx );

		new Flare( 7, 36 ).color( 0x225511, true ).show( shpx, 0 ).angularSpeed = +20;

		RenderedText shpxTitle = renderText(TTL_SHPX, 8 );
		shpxTitle.hardlight(Window.SHPX_COLOR);
		add( shpxTitle );

		shpxTitle.x = shpxOffset + (colWidth - shpxTitle.width()) / 2;
		shpxTitle.y = shpx.y + shpx.height + 7;
		align(shpxTitle);

		RenderedTextMultiline shpxText = renderMultiline(TXT_SHPX, 8 );
		shpxText.maxWidth((int)Math.min(colWidth, 120));
		add( shpxText );

		shpxText.setPos(shpxOffset + (colWidth - shpxText.width()) / 2, shpxTitle.y + shpxTitle.height() + 12);
		align(shpxText);
		
		RenderedTextMultiline shpxLink = renderMultiline( LNK_SHPX, 8 );
		shpxLink.maxWidth((int)Math.min(colWidth, 120));
		shpxLink.hardlight(Window.SHPX_COLOR);
		add(shpxLink);
		
		shpxLink.setPos(shpxOffset + (colWidth - shpxLink.width()) / 2 , shpxText.bottom() + 126);
		align(shpxLink);
		
		TouchArea hotArea = new TouchArea( shpxLink.left(), shpxLink.top(), shpxLink.width(), shpxLink.height() ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://" + LNK_SHPX) );
				Game.instance.startActivity( intent );
			}
		};
		add( hotArea );

		
		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		UNISTPixelDungeon.switchNoFade(TitleScene.class);
	}
}
