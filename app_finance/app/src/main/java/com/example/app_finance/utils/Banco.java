package com.example.app_finance.utils;

import java.io.Serializable;

public class Banco implements Serializable {
    private static String banco = "db_finance.db";
    private static String tabela = "transacti";

    public static String banco() {
        return banco;
    }

    public static String criaTabela() {
        return "CREATE TABLE IF NOT EXISTS " + tabela() + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR NOT NULL, " +
                "value REAL NOT NULL, " +
                "category VARCHAR NOT NULL, " +
                "type VARCHAR NOT NULL, " +
                "date TEXT NOT NULL);";
    }

    public static String tabela() {
        return tabela;
    }
}
