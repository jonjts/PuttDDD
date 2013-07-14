package br.com.jonjts.putddd.outgoing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import br.com.jonjts.putddd.AddCodigoArea;
import br.com.jonjts.putddd.MainActivity;

public class OutgoingReceiver extends BroadcastReceiver {

	private String codigo;
	private SharedPreferences prefs;

	public OutgoingReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			prefs = context.getSharedPreferences("myPrefs",
					Context.MODE_PRIVATE);
			if (prefs.getBoolean(MainActivity.ON_OFF, false)) {
				codigo = prefs.getString(MainActivity.CODIGO_OPERADORA, "");

				String areaCode = prefs.getString(AddCodigoArea.CODIGO_AREA,
						"00");

				if (codigo.length() < 3) {
					codigo = '0' + codigo;
				}

				final String oldNumber = intent
						.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
				String n = oldNumber;
				String ddd = getAreaCode(oldNumber);
				try {
					if (!ddd.equals(areaCode)) {
						if (oldNumber.length() == 10) {// então ta tipo 79
														// 9135-1417
							n = codigo + n;
						} else if (oldNumber.length() == 11) {// então ta tipo
																// 079 9135-1417
							n = codigo
									+ oldNumber.substring(1,
											oldNumber.length() - 1);
						} else if (oldNumber.length() >= 12) {// então a tipo
																// +55 079
																// 9134-1417 ou
																// com 9090
							String p = changeOk(n);
							if (p != null) {
								n = codigo + p;
							} else {
								n = codigo
										+ oldNumber.substring(
												oldNumber.length() - 10,
												oldNumber.length());
							}
						}
					} else {
						n = n.substring(n.length() - 8, n.length());
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
		} catch (Exception e) {

		}
	}

	/*
	 * Retorna o numero em formato 9
	 */
	private String changeOk(String n) {
		if (n.contains("9090")) {
			n = n.substring(2, n.length() - 1);
			return n;
		}
		return null;
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