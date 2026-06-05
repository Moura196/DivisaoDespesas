package br.edu.utfpr.gabrielmoura.divisaodespesas.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.edu.utfpr.gabrielmoura.divisaodespesas.modelo.Lancamento;
import br.edu.utfpr.gabrielmoura.divisaodespesas.modelo.Converters;

@Database(entities = {Lancamento.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class LancamentosDatabase extends RoomDatabase {

    public abstract LancamentoDao getLancamentoDao();

    private static LancamentosDatabase INSTANCE;

    public static LancamentosDatabase getInstance(final Context context){
        if (INSTANCE == null) {
            synchronized (LancamentosDatabase.class) {

                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            LancamentosDatabase.class,
                            "lancamentos.db").allowMainThreadQueries().build();
                }

            }
        }
        return INSTANCE;
    };
}
