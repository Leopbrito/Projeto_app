package com.example.app_finance.CRUD;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_finance.R;
import com.example.app_finance.pojo.Transaction;
import com.example.app_finance.utils.Banco;

import java.util.Date;

public class Novo extends AppCompatActivity {

    // Declaração das variaveis dos objetos da VIEW
    EditText name, value, category, date;
    Spinner type;
    Button btAdd;

    // Declaração da variavel para acessar o banco
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova);

        //abertura e/ou criação do banco de dados
        db = openOrCreateDatabase(Banco.banco(), MODE_PRIVATE, null);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("Nova Transação");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Atribuição das variaveis com os respectivos ID dos objetos da VIEW
        name = findViewById(R.id.editName_editar);
        value = findViewById(R.id.editValue_editar);
        category = findViewById(R.id.editCategory_editar);
        type = findViewById(R.id.spin_editar);
        date = findViewById(R.id.editDate_nova);
        btAdd = findViewById(R.id.btAdd_nova);

        // Botão Adicionar
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Transaction transacao = new Transaction();
                transacao.setName(name.getText().toString());
                transacao.setValue(Float.parseFloat(String.valueOf(value.getText())));
                transacao.setCategory(category.getText().toString());
                transacao.setType(type.getSelectedItem().toString());
                transacao.setDate(date.getText().toString());

                //criar um container para informar ao banco
                ContentValues values = new ContentValues();
                values.put("name", transacao.getName());
                values.put("value", transacao.getValue());
                values.put("category", transacao.getCategory());
                values.put("type", transacao.getType());
                values.put("date", transacao.getDate());

                //insere os dados na tabela
                db.insert(Banco.tabela(), null, values);

                Toast.makeText(Novo.this, "Transação adicionada com sucesso!", Toast.LENGTH_SHORT).show();

                finish();
            }
        });


    }

    // Configura o botão (seta) na ActionBar (Barra Superior)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
