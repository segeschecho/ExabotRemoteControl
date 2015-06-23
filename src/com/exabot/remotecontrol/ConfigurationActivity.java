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
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigurationActivity extends Activity {

	private Button            okButton;
	private EditText          ipHostEditText;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.configurations);
		
		//Se crea la función que reemplaza la ip para conectarse por las nuevas que se ingresan
		okButton = (Button)findViewById(R.id.configuration_view_ok);
		okButton.setOnClickListener(
					new View.OnClickListener()
					{
						public void onClick(View v) {
							onClickOkButton();
						}
					}
				);
		
		ipHostEditText = (EditText)findViewById(R.id.ipexaboteditbox);
		ipHostEditText.setText(ConfigurationManager.getInstance().getIpHost());
	}
	
	
	private void onClickOkButton()
	{
		//Se muestra mensaje de notificación
		Context context = getApplicationContext();
		CharSequence text = "Se han guardado los cambios.";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		//Se notifica que se realizó un cambio
		ConfigurationManager cManager = ConfigurationManager.getInstance();
		cManager.setIpHost(ipHostEditText.getText().toString());
	}
}
