package br.com.jonjts.putddd.Outgoing;

import java.util.List;

import br.com.jonjts.putddd.AddCodigoArea;
import br.com.jonjts.putddd.MainActivity;
import br.com.jonjts.putddd.R.string;
import br.com.jonjts.putddd.dao.OperadoraDAO;
import br.com.jonjts.putddd.model.Operadora;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class OutgoingReceiver extends BroadcastReceiver {

	private String codigo;
	private SharedPreferences prefs;

	public OutgoingReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		prefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
		if (prefs.getBoolean(MainActivity.ON_OFF, false)) {
			codigo = prefs.getString(MainActivity.CODIGO_OPERADORA, "");

			String areaCode = prefs.getString(AddCodigoArea.CODIGO_AREA, "00");

			if (codigo.length() < 3) {
				codigo = '0' + codigo;
			}

			final String oldNumber = intent
					.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			String n = oldNumber;
			String ddd = getAreaCode(oldNumber);
			try {
				if (!ddd.equals(areaCode)) {
					if (oldNumber.length() == 10) {// então ta tipo 79 9135-1417
						n = codigo + n;
					} else if (oldNumber.length() == 11) {// então ta tipo 0799135-1417
						n = codigo
								+ oldNumber
										.substring(1, oldNumber.length() - 1);
					} else if (oldNumber.length() >= 12) {// então a tipo +55 079 9134-1417
						n = codigo
								+ oldNumber.substring(oldNumber.length() - 10,oldNumber.length());
					}
				}else{
					n = n.substring(n.length()-8,n.length());
				}
			} catch (Exception e) {

			}
			this.setResultData(n);
			/*
			 * final String newNumber = this.getResultData(); String msg =
			 * "Intercepted outgoing call. Old number " + codigo +
			 * ", new number " + codigo; Toast.makeText(context, msg,
			 * Toast.LENGTH_LONG).show();
			 */
		}
	}

	private String getAreaCode(String numero) {
		String novo = numero.substring(numero.length() - 10,
				numero.length() - 8);
		return novo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}