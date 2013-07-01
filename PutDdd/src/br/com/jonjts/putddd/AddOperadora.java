package br.com.jonjts.putddd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.jonjts.putddd.dao.OperadoraDAO;
import br.com.jonjts.putddd.model.Operadora;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class AddOperadora extends SherlockActivity{
	
	private EditText edtNome;
	private EditText edtCodigo;
	private Button btnSalvar;
	
	private OperadoraDAO operadoraDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_add_operadora);
		edtNome = (EditText) findViewById(R.id.edtOperadora);
		edtCodigo = (EditText) findViewById(R.id.edtCodigo);
		btnSalvar = (Button) findViewById(R.id.btnSalvar);
		edtNome.setFocusable(true);
		
		operadoraDAO = OperadoraDAO.getInstance(getApplicationContext());
		
		btnSalvar.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String nome = edtNome.getText().toString();
				String codigo = edtCodigo.getText().toString();
				if(!codigo.isEmpty()){
				Operadora operadora = new Operadora(nome, codigo);
				try{
					operadoraDAO.insert(operadora);
					setResult(RESULT_OK);
					finish();
				}catch(Exception exception){
					setResult(RESULT_CANCELED);
					Toast.makeText(getApplicationContext(), "Data Base Error", Toast.LENGTH_LONG).show();
					finish();
				}
				}else{
					Toast.makeText(getApplicationContext(), R.string.codigoInvalido, Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        case android.R.id.home:
             finish();
             break;

        default:
            return super.onOptionsItemSelected(item);
        }
        return false;
    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
