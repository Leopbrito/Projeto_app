package com.example.app_finance.CRUD;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_finance.R;
import com.example.app_finance.pojo.Transaction;
import com.example.app_finance.utils.Banco;

public class Editar extends AppCompatActivity {

    // Declaração das variaveis dos objetos da VIEW
    EditText name, value, category, date;
    Spinner type;
    Button btEdit, btDelete;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("Editar Transação");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Atribuição das variaveis com os respectivos ID dos objetos da VIEW
        name = findViewById(R.id.editName_editar);
        value = findViewById(R.id.editValue_editar);
        category = findViewById(R.id.editCategory_editar);
        type = findViewById(R.id.spin_editar);
        date = findViewById(R.id.editDate_editar);
        btEdit = findViewById(R.id.btEdit);
        btDelete = findViewById(R.id.btDelete);

        Intent itTransaction = getIntent();

        final Transaction transaction = (Transaction) itTransaction.getExtras().getSerializable("transacao");

        final int id = transaction.getId();
        name.setText(transaction.getName());
        value.setText(String.valueOf(transaction.getValue()));
        category.setText(transaction.getCategory());
        type.setSelection(getIndex(type, transaction.getType()));
        date.setText(transaction.getDate());

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = openOrCreateDatabase(
                        Banco.banco(),
                        Context.MODE_PRIVATE,
                        null
                );

                db.execSQL(
                        "UPDATE " + Banco.tabela() + " SET " +
                                "name='" + name.getText() + "', " +
                                "value='" + value.getText() + "', " +
                                "category='" + category.getText() + "', " +
                                "type='" + type.getSelectedItem() + "', " +
                                "date='" + date.getText() + "' " +
                                "WHERE id=" + id
                );

                finish();

            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = openOrCreateDatabase(
                        Banco.banco(),
                        Context.MODE_PRIVATE,
                        null
                );

                db.execSQL("DELETE FROM " + Banco.tabela() + " WHERE id=" + id);

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

    // Setar o valor do spinner
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
}
