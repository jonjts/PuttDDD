package br.com.jonjts.putddd.model;

import java.util.List;

import br.com.jonjts.putddd.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OperadoraAdapter extends BaseAdapter{
	
	//Lista de pessoas!
		private List<Operadora> operadoras;
		private LayoutInflater mInflater;
		private ViewHolder holder;
	 
	 
		static class ViewHolder{
			private TextView tvNome;
			private TextView tvCod;
		}
	 
	 
		public OperadoraAdapter(Context context, List<Operadora> operadoras) {
			mInflater = LayoutInflater.from(context);
			this.operadoras = operadoras;
		}
	 
		public int getPosision(Long id){
			int i = 0;
			for(Operadora operadora : operadoras){
				if(operadora.getId() == id){
					return i;
				}
				i++;
			}
			return 0;
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
		public View getView(int posicao, View convertView, ViewGroup arg2) {
	 
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item, null);
				holder = new ViewHolder();
	 
				holder.tvNome = (TextView) convertView.findViewById(R.id.txtNome);
				holder.tvCod = (TextView) convertView
						.findViewById(R.id.txtCod);
	 
				convertView.setTag(holder);
	 
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
	 
	 
			Operadora o = operadoras.get(posicao);
	 
			holder.tvNome.setText(o.getNome());
			holder.tvCod.setText(": "+o.getCodigo());
	 
			return convertView;
		}
	 }
