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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ControlSurface extends SurfaceView implements SurfaceHolder.Callback {

	private static int TEXT_POSITION_X = 40;
	private static int TEXT_POSITION_Y = 40;
	
	private Context _context;
	private ControlThread _thread;
	private MovementListener _movementListener;
	private PointF currMovementVector;
	
	private Joystick _joystick;
	
	private Paint paint = new Paint();
	
	private MovementBroadcaster _movementBroadcaster;

	public ControlSurface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		_context = context;
		init();
	}

	private void init(){
		//initialize our screen holder
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		//initialize our Thread class. A call will be made to start it later
		_thread = new ControlThread(holder, _context, new Handler(),this);
		setFocusable(true);

		//initialize auxiliary members
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		
		Bitmap jbitmap = (Bitmap)BitmapFactory.decodeResource(getContext().getResources(), R.drawable.joystick);
		Bitmap jbackbitmap = (Bitmap)BitmapFactory.decodeResource(getContext().getResources(), R.drawable.joystick_bg);
		_joystick = new Joystick(jbitmap, jbackbitmap);

		//movement listener
		_movementListener = new MovementListener();
		setOnTouchListener(_movementListener);

		currMovementVector = _movementListener.getMovementVector();
	}

	public void doDraw(Canvas canvas) {
		
		canvas.drawColor(Color.GRAY);
		
		if(_movementListener.isDragging())
		{
			//draw the joystick background
			canvas.drawBitmap(_joystick.get_joystickBg(), _movementListener.getTouchingPointInit().x - _joystick.get_joystickBg().getWidth() / 2,_movementListener.getTouchingPointInit().y - _joystick.get_joystickBg().getHeight() / 2, null);

			//draw the dragable joystick
			canvas.drawBitmap(_joystick.get_joystick(),_movementListener.getTouchingPoint().x - _joystick.get_joystick().getWidth() / 2, _movementListener.getTouchingPoint().y - _joystick.get_joystick().getHeight() / 2, null);			
		}

		DecimalFormat decimalFormat = new DecimalFormat("0.0000");
		decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
		decimalFormat.setPositivePrefix(" ");

		PointF translatedMovement = MovementTranslatorToExabot.translateMovementVector(currMovementVector);
		canvas.drawText( "(" + decimalFormat.format(translatedMovement.x) + ", " + decimalFormat.format(translatedMovement.y) + ")" , TEXT_POSITION_X, TEXT_POSITION_Y, paint);
	}

	//these methods are overridden from the SurfaceView super class. They are automatically called 
	//when a SurfaceView is created, resumed or suspended.
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	private boolean retry;

	public void surfaceDestroyed(SurfaceHolder arg0) {
		_movementBroadcaster.stopBroadcasting();

		retry = true;
		//code to end main loop
		_thread.state = ControlThread.STOPPED;
		while (retry) {
			try {
				//code to kill Thread
				_thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		if(_thread.state==ControlThread.STOPPED){
			//When the application is opened again in the Android OS
			_thread = new ControlThread(getHolder(), _context, new Handler(),this);
			_thread.start();
		} else if(_thread.state==ControlThread.PAUSED) {
			_thread.resume();
		} else
		{
			// The thread canot be already running, because the app
			// will enter here just in case its opened, therefore the
			// thread is STOPPED or PAUSED
			_thread.start();
		}
		
		_movementBroadcaster = new MovementBroadcaster();
		_movementBroadcaster.startBroadcasting();
	}

	public void update(double delta) {
		if (_movementBroadcaster == null)
			return;
		
		currMovementVector = _movementListener.getMovementVector();

		PointF translatedMovement = MovementTranslatorToExabot.translateMovementVector(currMovementVector);
		_movementBroadcaster.setMovementVector(translatedMovement);
		_movementBroadcaster.update(delta);
	}

}
