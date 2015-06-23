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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MovementListener implements OnTouchListener {

	private float radio = 100;
	
	private PointF _touchingPointInit = new PointF();
	private PointF _touchingPoint = new PointF();
	
	static double EPSILON = 0.00001;
	
	private boolean _dragging = false;

	public PointF getTouchingPoint() {
		return _touchingPoint;
	}

	public PointF getTouchingPointInit() {
		return _touchingPointInit;
	}

	public float getRadio() {
		return radio;
	}
	
	public PointF getMovementVector()
	{
		if (!_dragging)
		{
			return new PointF(0.0f, 0.0f);
		}
		
		return new PointF((_touchingPoint.x - _touchingPointInit.x) / radio, (_touchingPointInit.y - _touchingPoint.y) / radio);
	}

	public void setRadio(float radio) {
		this.radio = radio;
	}

	public boolean isDragging()
	{
		return _dragging;
	}

	public boolean onTouch(View v, MotionEvent event) {
		//drag drop 
		if ( event.getAction() == MotionEvent.ACTION_DOWN )
		{
			_dragging = true;
			_touchingPointInit = new PointF(event.getX(), event.getY());
		}
		else if ( event.getAction() == MotionEvent.ACTION_UP)
		{
			_dragging = false;
		}

		if ( _dragging )
		{
			// get the pos
			_touchingPoint.x = event.getX();
			_touchingPoint.y = event.getY();

			// touch vector is the vector from the center of the control to the touch position
			// vectorLength is touch vector's length
			// normalizedVector is touch vector normalized
			PointF touchVector = new PointF(_touchingPointInit.x - _touchingPoint.x, _touchingPointInit.y - _touchingPoint.y);
			double vectorLength = Math.sqrt( Math.pow(touchVector.x, 2) + Math.pow(touchVector.y, 2) );
			PointF normalizedVector;
			
			// particular case: when the vector length is "0", we can't do touchVector.x / vectorLength
			if (vectorLength < EPSILON)
			{
				vectorLength = 0;
				normalizedVector = new PointF(0.0f, 0.0f);
			}
			else
			{
				 normalizedVector = new PointF((float)(touchVector.x / vectorLength), (float)(touchVector.y / vectorLength));				
			}
			
			// if the touchingPoint exceeds the controller circle
			if( vectorLength - radio > -EPSILON )
			{
				// correct the touch vector
				touchVector.x = normalizedVector.x * radio;
				touchVector.y = normalizedVector.y * radio;

				// correct vector length
				vectorLength = radio;

				// set the touchingPoint to the nearest point to the circle
				_touchingPoint.x = _touchingPointInit.x - touchVector.x;
				_touchingPoint.y = _touchingPointInit.y - touchVector.y;
			}
		}

		return true;
	}
}
