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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class ControlThread extends Thread {

	private SurfaceHolder mSurfaceHolder;
	private ControlSurface mControlSurface;
	private double lastUpdateTime;
	
	//state (Running or Paused).
	public int state = 3;
	public final static int RUNNING = 1;
	public final static int PAUSED = 2;
	public final static int STOPPED = 3;

	public ControlThread(SurfaceHolder surfaceHolder, Context context, Handler handler,ControlSurface controlSurface) {
		//data about the screen
		mSurfaceHolder = surfaceHolder;
		mControlSurface=controlSurface;
	}

	//This is the most important part of the code. It is invoked when the call to start() is
	//made from the SurfaceView class. It loops continuously until the application is finished or suspended.
	public void run() {
		state = RUNNING;
		
		//UPDATE
		while (state==RUNNING) {
			//This is where we update the application modules
			double delta = (System.nanoTime() - lastUpdateTime)/1000000000L;
			lastUpdateTime = System.nanoTime();
			mControlSurface.update(delta);
			
			//DRAW
			Canvas c = null;
			try {
				//lock canvas so nothing else can use it
				c = mSurfaceHolder.lockCanvas(null);
				synchronized (mSurfaceHolder) {
					//clear the screen with the black painter.
					//reset the canvas
					c.drawColor(Color.WHITE);
					//This is where we draw the views.
					mControlSurface.doDraw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}

			while (state==PAUSED){
				Log.d("State","Thread is pausing");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
