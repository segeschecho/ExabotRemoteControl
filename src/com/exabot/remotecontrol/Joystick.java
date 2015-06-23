//Copyright (C) 2013 Sergio E. Gonzalez and Emiliano D. González
//Facultad de Ciencias Exactas y Naturales, Universidad de Buenos Aires, Buenos Aires, Argentina.
// 
//C/C++, Java and XML/YML code for a remote control Exabot Robot
//
//This file is part of ExabotRemoteControl.
//
//ExabotRemoteControl is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//ExabotRemoteControl is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with ExabotRemoteControl.  If not, see <http:www.gnu.org/licenses/>.
//
//
//Authors:    Sergio E. Gonzalez - segonzalez@dc.uba.ar
//            Emiliano D. González - edgonzalez@dc.uba.ar
//
//Departamento de Computación
//Facultad de Ciencias Exactas y Naturales
//Universidad de Buenos, Buenos Aires, Argentina
//Date: June 2013

package com.exabot.remotecontrol;

import android.graphics.Bitmap;

public class Joystick {
	
	private Bitmap _joystick;
	private Bitmap _joystickBg;

	public Joystick(Bitmap joystickBitmap, Bitmap joystickBackgroundBitmap) {
		_joystick = joystickBitmap;
		_joystickBg = joystickBackgroundBitmap;
	}

	public Bitmap get_joystick() {
		return _joystick;
	}

	public void set_joystick(Bitmap joystick) {
		_joystick = joystick;
	}

	public Bitmap get_joystickBg() {
		return _joystickBg;
	}

	public void set_joystickBg(Bitmap joystickBg) {
		_joystickBg = joystickBg;
	}
}
