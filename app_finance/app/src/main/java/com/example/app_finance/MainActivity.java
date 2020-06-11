package com.example.app_finance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.app_finance.CRUD.Editar;
import com.example.app_finance.CRUD.Novo;
import com.example.app_finance.pojo.Transaction;
import com.example.app_finance.utils.Banco;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declaração das variaveis dos objetos da VIEW
    FloatingActionButton floatBtAdd;
    PieChart pieChart;
    ListView lvTransaction;
    ArrayList<Transaction> transactions = new ArrayList<>();
    ArrayAdapter<Transaction> adaptador;

    ArrayList<PieEntry> dataValues(){
        ArrayList<PieEntry> dataVals = new ArrayList<PieEntry>();
        dataVals.add(new PieEntry(renda,"Renda"));
        dataVals.add(new PieEntry(despesa,"Despesa"));
        return dataVals;
    }

    float renda, despesa;
    int[] colorClassArray = new int[]{Color.GREEN,Color.RED};

    // Declaração da variavel para acessar o banco
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Wallet");

        //abertura e/ou criação do banco de dados
        db = openOrCreateDatabase(Banco.banco(), MODE_PRIVATE, null);

        // cria a tabela caso nao exista
        db.execSQL(Banco.criaTabela());


        Cursor cursorRenda = db.rawQuery("SELECT value FROM " + Banco.tabela() + " WHERE type='Renda'",null);

        while (cursorRenda.moveToNext()){
            renda += cursorRenda.getFloat(0);
        }

        Cursor cursorDespesa = db.rawQuery("SELECT value FROM " + Banco.tabela() + " WHERE type='Despesa'",null);

        while (cursorDespesa.moveToNext()){
            despesa += cursorDespesa.getFloat(0);
        }


        // Atribuição das variaveis com os respectivos ID dos objetos da VIEW
        floatBtAdd = findViewById(R.id.floatBtAdd);
        pieChart = findViewById(R.id.pieChart);
        lvTransaction = findViewById(R.id.lvTransaction_main);

        // Botão FLutuante
        floatBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent novo = new Intent(getApplicationContext(), Novo.class);

                startActivity(novo);
            }
        });

        // Grafico

        PieDataSet pieDataSet = new PieDataSet(dataValues(),"");
        pieDataSet.setColors(colorClassArray);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();


        //popular a lista (List View)
        //Limpar a listar
        transactions.clear();

        // carregar os registros em ordem alfabetica
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + Banco.tabela() + " ORDER BY name ASC",
                null);


        //Vamos percorrer o Cursor e atribuir os valores ao nosso ArrayList<Aluno>
        while (cursor.moveToNext()){
            transactions.add(new Transaction(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getFloat(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5))
            );
        }


        // configurar o adpatador
        adaptador = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                transactions);

        lvTransaction.setAdapter(adaptador);


        lvTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Transaction transaction = (Transaction) lvTransaction.getItemAtPosition(position);

                Intent itTransaction = new Intent(getApplicationContext(), Editar.class);

                itTransaction.putExtra("transacao", transaction);

                startActivity(itTransaction);
            }
        });


    }
}
