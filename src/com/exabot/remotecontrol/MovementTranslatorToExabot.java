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

import android.graphics.PointF;

public class MovementTranslatorToExabot
{
	public static PointF translateMovementVector(PointF movementVector)
	{
		PointF result = new PointF(1.0f, 1.0f);
		
		float tanAngle = movementVector.y / movementVector.x;
		double angle = Math.atan(tanAngle);
		
		if (movementVector.x < 0)
		{
			result.x = (float)(angle / (Math.PI/2));
		}
		else if(movementVector.x > 0)
		{
			result.y = (float)(angle / (Math.PI/2));
		}
		
		if (movementVector.y < 0)
		{
			result.x = -Math.abs(result.x);
			result.y = -Math.abs(result.y);
		}
		else if (movementVector.y > 0)
		{
			result.x = Math.abs(result.x);
			result.y = Math.abs(result.y);			
		}

		float vectorLength = (float)Math.sqrt(Math.pow(movementVector.x, 2) + Math.pow(movementVector.y, 2));
		
		result.x *= vectorLength;
		result.y *= vectorLength;
		
//		Log.d("Exabot engine velocities", "( " + result.x + ", " + result.y + " )");
		
		return result;
	}
}
