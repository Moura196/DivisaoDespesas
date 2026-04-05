package br.edu.utfpr.gabrielmoura.divisaodespesas.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.gabrielmoura.divisaodespesas.modelo.Lancamento;

@Dao
public interface LancamentoDao {

    @Insert
    long inserir(Lancamento lancamento);

    @Delete
    int deletar(Lancamento lancamento);

    @Update
    int atualizar(Lancamento lancamento);

    @Query("SELECT * FROM lancamento WHERE id_lancamento = :id")
    Lancamento queryForId(Long id);

    @Query("SELECT * FROM lancamento ORDER BY descricao ASC")
    List<Lancamento> queryAllAscending();

    @Query("SELECT * FROM lancamento ORDER BY descricao DESC")
    List<Lancamento> queryAllADownward();
}
