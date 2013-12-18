/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;

import org.catrobat.catroid.R;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class BubbleBrickBaseType extends BrickBaseType implements OnClickListener, FormulaBrick, Cloneable {

	private static final long serialVersionUID = 1L;
	protected static final int STRING_OFFSET = 20;
	protected static final int BOUNDARY_PIXEL = 30;

	protected Formula text;
	protected Formula duration;
	protected transient View prototypeView;
	protected transient View bubble;
	protected transient Context context;
	protected byte[] bubbleByteArray;

	public BubbleBrickBaseType() {
	}

	public BubbleBrickBaseType(Sprite sprite, String say, int seconds, Context context) {
		this.sprite = sprite;
		this.text = new Formula(say);
		this.duration = new Formula(seconds);
		this.context = context;
	}

	public BubbleBrickBaseType(Sprite sprite, Formula say, Formula seconds, Context context) {
		this.sprite = sprite;
		this.text = say;
		this.duration = seconds;
		this.context = context;
	}

	@Override
	public Formula getFormula() {
		return text;
	}

	@Override
	public int getRequiredResources() {
		return NO_RESOURCES;
	}

	@Override
	public void onClick(View view) {
		if (checkbox.getVisibility() == View.VISIBLE) {
			return;
		}
		switch (view.getId()) {
			case R.id.brick_say_edit_text:
				FormulaEditorFragment.showFragment(view, this, text);
				break;

			case R.id.brick_think_edit_text:
				FormulaEditorFragment.showFragment(view, this, text);
				break;

			case R.id.brick_for_edit_text:
				FormulaEditorFragment.showFragment(view, this, duration);
				break;
		}
	}

	protected String getNormalizedText() {
		String text;
		try {
			text = this.text.interpretString(sprite);
		} catch (ClassCastException classCastException) {
			text = String.valueOf(this.text.interpretDouble(sprite));
		}

		StringBuffer stringBuffer = new StringBuffer();

		for (int index = 0; index < text.length(); index++) {
			if (index % STRING_OFFSET == 0) {
				stringBuffer.append("\n");
			}
			//TODO: max size of text.
			stringBuffer.append(text.charAt(index));
		}
		return stringBuffer.toString();
	}

	protected void updateBubbleByteArrayFromDrawingCache() {
		bubble.setDrawingCacheEnabled(true);
		bubble.measure(MeasureSpec.makeMeasureSpec(ScreenValues.SCREEN_WIDTH - BOUNDARY_PIXEL, MeasureSpec.AT_MOST),
				MeasureSpec.makeMeasureSpec(ScreenValues.SCREEN_HEIGHT - BOUNDARY_PIXEL, MeasureSpec.AT_MOST));
		bubble.layout(0, 0, bubble.getMeasuredWidth(), bubble.getMeasuredHeight());

		Bitmap bitmap = bubble.getDrawingCache();
		ByteArrayOutputStream stream = new ByteArrayOutputStream(bitmap.getByteCount());
		bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
		bubbleByteArray = stream.toByteArray();
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		bubble.setDrawingCacheEnabled(false);
	}

	public void setContext(Context applicationContext) {
		this.context = applicationContext;
	}
}
