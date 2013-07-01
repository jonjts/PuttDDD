package br.com.jonjts.putddd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class AddCodigoArea extends SherlockActivity {
	
	private EditText edtCodigoArea;
	private Button btnSalvar;
	private SharedPreferences prefs;
	
	public static String CODIGO_AREA = "codigo_area";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_codigo_area);
		
		prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
		
		edtCodigoArea = (EditText) findViewById(R.id.edtCodigoArea);
		btnSalvar = (Button) findViewById(R.id.btnSalveCdArea);
		
		edtCodigoArea.setText(prefs.getString(CODIGO_AREA, "00"));
		
		btnSalvar.setOnClickListener(new Button.OnClickListener() {
			

			@Override
			public void onClick(View v) {
				String cod = edtCodigoArea.getText().toString();
				if(!cod.isEmpty()){
					prefs.edit().putString(CODIGO_AREA, cod).commit();
					setResult(RESULT_OK);
					Toast.makeText(getApplicationContext(), R.string.realizadoSucess, Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});
		
	}

}
