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

import com.exabot.remote.ExabotRemote;

public class MovementBroadcaster implements ConfigurationListener {
	
	private String ipHost;

	private ConfigurationManager cManager;
	private PointF               movementVector;
	private ExabotRemote         exaRemote;
	private double               deltaAccum;	

	public MovementBroadcaster() {
		 cManager       = ConfigurationManager.getInstance();
		 movementVector = null;
		 exaRemote      = new ExabotRemote();
		 deltaAccum     = 0;
		 ipHost         = cManager.getIpHost();
	}
	
	public void startBroadcasting()
	{
		ConfigurationManager cManager = ConfigurationManager.getInstance();
		cManager.subscribe(this);
		exaRemote.exaRemoteInitialize(ipHost);		
	}

	public void stopBroadcasting()
	{
		cManager.unSubscribe(this);
		exaRemote.exaRemoteDeinitialize();
	}

	public void setMovementVector(PointF movementVector) {
		this.movementVector = movementVector;
	}

	public PointF getMovementVector() {
		return movementVector;
	}

	public void update(double delta) {
		deltaAccum += delta;
		if (deltaAccum > 0.1f)
		{
			exaRemote.exaRemoteSetMotors(movementVector.x, movementVector.y);
			deltaAccum = 0;
		}
	}
	
	//Implementación del método de la interfaz
	public void configurationChanged(ConfigurationManager c) {
		String newIp = c.getIpHost();
		
		if (newIp != ipHost){
			exaRemote.exaRemoteDeinitialize();
			ipHost = newIp;
			exaRemote.exaRemoteInitialize(ipHost);
		}
	}
}
