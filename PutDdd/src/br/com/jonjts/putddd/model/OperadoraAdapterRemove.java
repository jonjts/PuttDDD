package br.com.jonjts.putddd.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import br.com.jonjts.putddd.R;
import br.com.jonjts.putddd.model.OperadoraAdapter.ViewHolder;

public class OperadoraAdapterRemove extends BaseAdapter{
	
	//Lista de pessoas!
			private List<Operadora> operadoras;
			private LayoutInflater mInflater;
			private ViewHolder holder;
			private List<Operadora> CheckedToDie;
		 
		 
			static class ViewHolder{
				private TextView tvNome;
				private TextView tvCod;
				private CheckBox chekRemove;
			}
		 
		 public List<Operadora> getCheckedToDie() {
			return CheckedToDie;
		}
			
			public OperadoraAdapterRemove(Context context, List<Operadora> operadoras) {
				mInflater = LayoutInflater.from(context);
				this.operadoras = operadoras;
				CheckedToDie = new ArrayList<Operadora>();
			}
		 
			@Override
			public int getCount() {
				return operadoras.size();
			}
		 
			@Override
			public Operadora getItem(int index) {
				return operadoras.get(index);
			}
		 
			@Override
			public long getItemId(int index) {
				return operadoras.get(index).getId();
			}
		 
			@Override
			public View getView(final int posicao, View convertView, ViewGroup arg2) {
		 
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.list_item_remove, null);
					holder = new ViewHolder();
		 
					holder.tvNome = (TextView) convertView.findViewById(R.id.textView1);
					holder.tvCod = (TextView) convertView.findViewById(R.id.textView13);
					holder.chekRemove = (CheckBox) convertView.findViewById(R.id.chekRemove);
		 
					convertView.setTag(holder);
		 
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
		 
		 
				Operadora o = operadoras.get(posicao);
		 
				holder.tvNome.setText(o.getNome());
				holder.tvCod.setText(": "+o.getCodigo());
				holder.chekRemove.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if(isChecked){
							CheckedToDie.add(operadoras.get(posicao));
						}else{
							CheckedToDie.remove(operadoras.get(posicao));
						}
						
					}
				});
		 
				return convertView;
			}

}
