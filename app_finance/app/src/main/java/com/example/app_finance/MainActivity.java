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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declaração das variaveis dos objetos da VIEW
    FloatingActionButton floatBtAdd;
   // GraphView graph;
    ListView lvTransaction;
    ArrayList<Transaction> transactions = new ArrayList<>();
    ArrayAdapter<Transaction> adaptador;

    /**grafico**/
    PieChart grafico;

    /**Array float, sera responsavel por definir a % de cada item do grafico**/
    float itensGrafico[] = {96.6f, 56.8f, 35.6f, 45.7f, 10.5f};
    /** Array de String, Sera responsavel  por  definir o  nome de cada item  do nosso grafico**/
     String descricao[] ={"Item Um", "Item Dois", "Item Tres", "Item Quatro", "Item Cinco"};

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
        //graph = findViewById(R.id.graph);
        grafico = (PieChart) findViewById(R.id.graficoID);
        /**Criamos uma list  do tipo <PieChart>**/
        List<PieChart> entradasGrafico = new ArrayList<>();
        /**Preenchendo o grafico**/
        for (int i =0; i < itensGrafico.length; i ++) {
            entradasGrafico.add(new PieEntry(itensGrafico[i],descricao[i]));
        }
        PieDataSet dataSet= new PieDataSet(entradasGrafico,"Legenda do grafico");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(dataSet);

        grafico.setData(pieData);

        grafico.invalidate();

        }
        //lvTransaction = findViewById(R.id.lvTransaction_main);

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
        grafico.addSeries(series);


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
