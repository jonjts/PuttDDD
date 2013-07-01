package br.com.jonjts.putddd;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import br.com.jonjts.putddd.dao.OperadoraDAO;
import br.com.jonjts.putddd.model.Operadora;
import br.com.jonjts.putddd.model.OperadoraAdapterRemove;

import com.actionbarsherlock.app.SherlockActivity;

public class RemoveOperadora extends SherlockActivity {

	private ListView listOperadoras;
	private Button btnRemove;

	private OperadoraDAO operadoraDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remove_operadoras);
		
		setTitle(R.string.remover);
		
		listOperadoras = (ListView) findViewById(R.id.lisOperadoras);
		btnRemove = (Button) findViewById(R.id.btnRemove);
		operadoraDAO = OperadoraDAO.getInstance(getApplicationContext());

		loadListView();
		
		btnRemove.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				List<Operadora> toDie = ((OperadoraAdapterRemove) listOperadoras
						.getAdapter()).getCheckedToDie();
				if (toDie.size() > 0) {
					showDialog(toDie);
				} else {
					Toast.makeText(getApplicationContext(),
							R.string.nadaRemover, Toast.LENGTH_LONG).show();
				}
			}
		});

	}
	

	private void showDialog(final List<Operadora> toDie) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(R.string.remover);
		builder.setMessage(R.string.confirmarRemove);
		// define um botão como positivo
		builder.setPositiveButton(R.string.sim,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						try {
							for (Operadora o : toDie) {
								operadoraDAO.delete(o);
							}
							setResult(RESULT_OK);
							Toast.makeText(getApplicationContext(), R.string.removido, Toast.LENGTH_LONG).show();
							finish();
						} catch (Exception e) {
							setResult(RESULT_CANCELED);
						}
					}
				});
		// define um botão como negativo.
		builder.setNegativeButton(R.string.cancelar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
		builder.create().show();

	}
	
	private void loadListView(){
		OperadoraAdapterRemove adapterRemove = new OperadoraAdapterRemove(
				getApplicationContext(), operadoraDAO.getAll());
		listOperadoras.setAdapter(adapterRemove);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

}
