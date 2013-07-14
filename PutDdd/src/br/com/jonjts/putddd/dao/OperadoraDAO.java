package br.com.jonjts.putddd.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.jonjts.putddd.model.Operadora;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OperadoraDAO {
	
	 public static final String NOME_TABELA = "operadora";
	    public static final String COLUNA_ID = "ope_id";
	    public static final String COLUNA_NOME = "ope_nome";
	    public static final String COLUNA_CODIGO = "ope_codigo";
	 
	 
	    public static final String SCRIPT_CRIACAO_TABELA_OPERADORA = "CREATE TABLE [operadora]" +
	    		" ([ope_id] INTEGER PRIMARY KEY, " +
	    		"[ope_codigo] [TEXT(10)] NOT NULL, [ope_nome] [TEXT(30)] NOT NULL) ";
	 
	    public static final String SCRIPT_DELECAO_TABELA =  "DROP TABLE IF EXISTS " + NOME_TABELA;
	    public static final String[] ss = new String[]{"INSERT INTO operadora (ope_nome, ope_codigo)VALUES ('Tim','041')",
	    												"INSERT INTO operadora (ope_nome, ope_codigo)VALUES ('Vivo','015')",
	    												"INSERT INTO operadora (ope_nome, ope_codigo)VALUES ('Claro','021')",
	    												"INSERT INTO operadora (ope_nome, ope_codigo)VALUES ('Oi','031')"};
	 
	 
	    private SQLiteDatabase dataBase = null;
	 
	 
	    private static OperadoraDAO instance;
	     
	    public static OperadoraDAO getInstance(Context context) {
	        if(instance == null)
	            instance = new OperadoraDAO(context);
	        return instance;
	    }
	 
	    private OperadoraDAO(Context context) {
	        SQLiteOpenHelper persistenceHelper = SQLiteOpenHelper.getInstance(context);
	        dataBase = persistenceHelper.getWritableDatabase();
	    }
	    
	    
	    public void insert(Operadora operadora) {
	        ContentValues values = gerarContentValeuesVeiculo(operadora);
	        dataBase.insert(NOME_TABELA, null, values);
	    }
	 
	    public List<Operadora> getAll() {
	        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
	        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
	        List<Operadora> list = construirVeiculoPorCursor(cursor);
	 
	        return list;
	    }
	 
	    public void delete(Operadora operadora) {
	 
	        String[] valoresParaSubstituir = {
	                String.valueOf(operadora.getId())
	        };
	 
	        dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
	    }
	 
	    public void update(Operadora operadora) {
	        ContentValues valores = gerarContentValeuesVeiculo(operadora);
	 
	        String[] valoresParaSubstituir = {
	                String.valueOf(operadora.getId())
	        };
	 
	        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
	    }
	 
	    public void close() {
	        if(dataBase != null && dataBase.isOpen())
	            dataBase.close();
	        instance = null;
	    }
	 
	 
	    private List<Operadora> construirVeiculoPorCursor(Cursor cursor) {
	        List<Operadora> listOperadoras = new ArrayList<Operadora>();
	        if(cursor == null)
	            return listOperadoras;
	         
	        try {
	 
	            if (cursor.moveToFirst()) {
	                do {
	 
	                    int indexID = cursor.getColumnIndex(COLUNA_ID);
	                    int indexNome = cursor.getColumnIndex(COLUNA_NOME);
	                    int indexCodigo = cursor.getColumnIndex(COLUNA_CODIGO);
	 
	                    Long id = cursor.getLong(indexID);
	                    String nome = cursor.getString(indexNome);
	                    String codigo = cursor.getString(indexCodigo);
	 
	                    Operadora operadora = new Operadora(id, nome, codigo);
	 
	                    listOperadoras.add(operadora);
	 
	                } while (cursor.moveToNext());
	            }
	             
	        } finally {
	            cursor.close();
	        }
	        return listOperadoras;
	    }
	 
	    private ContentValues gerarContentValeuesVeiculo(Operadora operadora) {
	        ContentValues values = new ContentValues();
	        values.put(COLUNA_CODIGO, operadora.getCodigo());
	        values.put(COLUNA_NOME, operadora.getNome());
	 
	        return values;
	    }
}
