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

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

public class main extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set main view to full screen 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(new ControlSurface(this));
		
		//not so nice but... it works
		//(It is used by the ConfigurationManager to gain access to the sharedPreferencies and Android Sources)
		Application app = getApplication();
		ConfigurationManager.app = app;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Add buttons to menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainoptionmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.main_menu_configuration_item1:
			Intent intent = new Intent(this, ConfigurationActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
