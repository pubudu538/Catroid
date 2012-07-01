/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team 
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.catroid.tutorial.tasks;

import java.util.HashMap;

import at.tugraz.ist.catroid.tutorial.CloudController;
import at.tugraz.ist.catroid.tutorial.SurfaceObjectTutor;

/**
 * @author faxxe
 * 
 */
public class TaskFadeOut implements Task {

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.FADEOUT;
	}

	@Override
	public Tutor getTutorType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean execute(HashMap<Tutor, SurfaceObjectTutor> tutors) {
		CloudController co = new CloudController();
		co.fadeOut();
		return false;
	}

	@Override
	public void setEndPositionForTutor(HashMap<Tutor, SurfaceObjectTutor> tutors) {
		// TODO Auto-generated method stub

	}

}
