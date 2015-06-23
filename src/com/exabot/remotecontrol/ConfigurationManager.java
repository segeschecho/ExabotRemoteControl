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

import java.util.ArrayList;

import android.app.Application;
import android.content.SharedPreferences;

/*
 * Esta clase tiene una única instancia en todo el programa(es Singleton).
 */
public class ConfigurationManager {
	
	private static ConfigurationManager       instance;
	public static Application                 app;
	private ArrayList<ConfigurationListener>  subscribers;
	private String                            ipHost;
	private SharedPreferences                 preferences;
	private String                            ipFieldKey;
	
	private ConfigurationManager() {
		subscribers      = new ArrayList<ConfigurationListener>();
		
		//Se obtienen los datos mostrar la ip del host guardada
		ipFieldKey       = app.getString(R.string.exabotRemoteControlConfigIpFieldKey);
		preferences      = app.getSharedPreferences(app.getString(R.string.exabotRemoteControlConfigFileName), 0);
		String  ip       = preferences.getString(ipFieldKey, "undefined");
		
		if (ip != "undefined")
				ipHost = ip;
	}
	
	public static ConfigurationManager getInstance() {
		if (instance == null) 
			instance = new ConfigurationManager();
		
		return instance;
	}
	
	public void subscribe(ConfigurationListener o) {
		subscribers.add(o);
	}
	
	public void unSubscribe(ConfigurationListener o) {
		if (subscribers.contains(o)){
			subscribers.remove(o);
		}
	}
	
	private void notifyChanges() {
		for (ConfigurationListener listener : subscribers) {
			listener.configurationChanged(this);
		}
	}
	
	public void setIpHost(String newIp) {
		//Se reemplaza el valor de la ip que se guardó la última vez
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putString(ipFieldKey, newIp);
		editor.commit();
		
		ipHost = newIp;

		notifyChanges();
	}
	
	public String getIpHost() {
		return ipHost;
	}
}
