package com.example.app_finance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.app_finance.CRUD.Editar;
import com.example.app_finance.CRUD.Novo;
import com.example.app_finance.pojo.Transaction;
import com.example.app_finance.utils.Banco;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declaração das variaveis dos objetos da VIEW
    FloatingActionButton floatBtAdd;
    GraphView graph;
    ListView lvTransaction;
    ArrayList<Transaction> transactions = new ArrayList<>();
    ArrayAdapter<Transaction> adaptador;

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


        // Atribuição das variaveis com os respectivos ID dos objetos da VIEW
        floatBtAdd = findViewById(R.id.floatBtAdd);
        graph = findViewById(R.id.graph);
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
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 1),
                new DataPoint(2, 5)
        });
        graph.addSeries(series);


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
