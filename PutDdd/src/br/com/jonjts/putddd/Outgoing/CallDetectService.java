package br.com.jonjts.putddd.Outgoing;


import br.com.jonjts.putddd.MainActivity;
import br.com.jonjts.putddd.model.Operadora;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class CallDetectService extends Service {
		
	private OutgoingReceiver outgoingReceiver;
	private String codigo;
 
    public CallDetectService() {
    	outgoingReceiver = new OutgoingReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int res = super.onStartCommand(intent, flags, startId);
        outgoingReceiver = new OutgoingReceiver();
        
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        getApplicationContext().registerReceiver(outgoingReceiver, intentFilter);
        return res;
    }
 
    
    public void setOutgoingReceiver(OutgoingReceiver outgoingReceiver) {
    	if(outgoingReceiver == null){
    		this.outgoingReceiver = outgoingReceiver;
    	}else{
    		outgoingReceiver = new OutgoingReceiver();
    	}
	}
    
    public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
    
    public OutgoingReceiver getOutgoingReceiver() {
		return outgoingReceiver;
	}
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(outgoingReceiver);
//        outgoingReceiver.abortBroadcast();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // not supporting binding
        return null;
   }
}
