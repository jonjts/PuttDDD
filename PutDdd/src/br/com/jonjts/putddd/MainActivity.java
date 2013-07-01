package br.com.jonjts.putddd;


import br.com.jonjts.putddd.dao.OperadoraDAO;
import br.com.jonjts.putddd.model.Operadora;
import br.com.jonjts.putddd.model.OperadoraAdapter;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener;
import com.actionbarsherlock.internal.widget.IcsSpinner;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import com.google.ads.*;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.test.IsolatedContext;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends SherlockActivity {

	private IcsSpinner spnOperadoras;
	private ToggleButton tglOnOff;
	private TextView txvCodigoArea;
	private OperadoraDAO operadoraDAO;
	private SharedPreferences prefs;
	private boolean salvar = false;
	

	public static int ADD_OPERADORA = 1;
	public static int REMOVE_OPERADORA = 2;
	public static int ADD_CODIGO_AREA = 3;

	public static String ON_OFF = "on_off";
	public static String ID_OPERADORA = "id_ope";
	public static String CODIGO_OPERADORA = "codigo_op";
	
	private Animation animation;
	
	ActionBarSherlock mSherlock = ActionBarSherlock.wrap(this);
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		prefs = this.getSharedPreferences("myPrefs", 
                Context.MODE_PRIVATE);
		
		animation = AnimationUtils.loadAnimation(this, R.anim.fade);
		
		if(prefs.getString(AddCodigoArea.CODIGO_AREA, "00").equals("00")){
			fixAreaCode();
		}
		
    	
		spnOperadoras = (IcsSpinner) findViewById(R.id.spnOperadoras);
		tglOnOff = (ToggleButton) findViewById(R.id.tbgOnOff);
		txvCodigoArea = (TextView) findViewById(R.id.txtCodigoArea);
		txvCodigoArea.startAnimation(animation);
		spnOperadoras.startAnimation(animation);
		
		
		operadoraDAO = OperadoraDAO.getInstance(getApplicationContext());
		Log.i("Banco", "Banco criado");

		OperadoraAdapter adapter = new OperadoraAdapter(this,
				operadoraDAO.getAll());
		spnOperadoras.setAdapter(adapter);

		Long id = getPreferences(MODE_PRIVATE).getLong(ID_OPERADORA, 0);
		try{
			txvCodigoArea.setText(prefs.getString(AddCodigoArea.CODIGO_AREA, "00"));
		spnOperadoras.setSelection(adapter.getPosision(id));
		}catch(Exception e){
			
		}

		tglOnOff.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				prefs.edit().putBoolean(ON_OFF, isChecked).commit();

			}
		});
		tglOnOff.setChecked(prefs.getBoolean(ON_OFF, true));

		spnOperadoras.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(IcsAdapterView<?> parent, View view,
					int position, long id) {
				Operadora operadora = ((Operadora) spnOperadoras
						.getAdapter().getItem(position));

				getPreferences(MODE_PRIVATE).edit().putLong(ID_OPERADORA, id).commit();
				prefs.edit().putString(CODIGO_OPERADORA, operadora.getCodigo()).commit();
				
			}

			@Override
			public void onNothingSelected(IcsAdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
			}

	private void fixAreaCode() {
		final TelephonyManager telephony = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
		if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
		    final GsmCellLocation location = (GsmCellLocation) telephony.getCellLocation();
		    if (location != null) {
		    	String string = String.valueOf(location.getLac());
		    	String novo = string.substring(string.length()-2,string.length()); 
		    	prefs.edit().putString(AddCodigoArea.CODIGO_AREA, novo).commit();
		    	txvCodigoArea.setText(novo);
		        
		    }
		}
		
		
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ADD_OPERADORA || requestCode == REMOVE_OPERADORA) {

			if (resultCode == RESULT_OK) {
				OperadoraAdapter adapter = new OperadoraAdapter(this,
						operadoraDAO.getAll());
				spnOperadoras.setAdapter(adapter);
			}
		}else if(requestCode == ADD_CODIGO_AREA){
			txvCodigoArea.setText(prefs.getString(AddCodigoArea.CODIGO_AREA, "00"));
			txvCodigoArea.startAnimation(animation);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		SubMenu subMenu1 = menu.addSubMenu("Option");
		subMenu1.add(0, ADD_OPERADORA, 0, R.string.adicionar);
		subMenu1.add(0, REMOVE_OPERADORA, 0, R.string.remover);
		subMenu1.add(0, ADD_CODIGO_AREA, 0, R.string.seuCodigoArea);

		MenuItem subMenu1Item = subMenu1.getItem();
		subMenu1Item
				.setIcon(com.actionbarsherlock.R.drawable.abs__ic_menu_moreoverflow_normal_holo_dark);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			startActivityForResult(new Intent(this, AddOperadora.class),
					ADD_OPERADORA);
			break;
		case 2:
			startActivityForResult(new Intent(this, RemoveOperadora.class),
					REMOVE_OPERADORA);
			break;
		case 3:
			startActivityForResult(new Intent(this,AddCodigoArea.class), ADD_CODIGO_AREA);
			break;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			operadoraDAO.close();
		} catch (Exception e) {
			Log.i("Banco", e.getMessage());
		}
	}

}
