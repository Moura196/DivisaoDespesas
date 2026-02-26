package br.edu.utfpr.gabrielmoura.divisaodespesas.Lancamento;

import java.util.Date;

public class Lancamento  {

    private Long id_lancamento;
    private String descricao;
    private Double valor_total;
    private Date data;
    private int morador_comprador; // spinner listando os moradores cadastrados
    private boolean tipo_lancamento; // checkbox identificando se é uma conta de casa ou compra de mercado

    public Lancamento(Long id_lancamento, String descricao, Double valor_total, Date data, int morador_comprador, boolean tipo_lancamento) {
        this.id_lancamento = id_lancamento;
        this.descricao = descricao;
        this.valor_total = valor_total;
        this.data = data;
        this.morador_comprador = morador_comprador;
        this.tipo_lancamento = tipo_lancamento;
    }

    public Long getId_lancamento() {
        return id_lancamento;
    }

    public void setId_lancamento(Long id_lancamento) {
        this.id_lancamento = id_lancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor_total() {
        return valor_total;
    }

    public void setValor_total(Double valor_total) {
        this.valor_total = valor_total;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getMorador_comprador() {
        return morador_comprador;
    }

    public void setMorador_comprador(int morador_comprador) {
        this.morador_comprador = morador_comprador;
    }

    public boolean isTipo_lancamento() {
        return tipo_lancamento;
    }

    public void setTipo_lancamento(boolean tipo_lancamento) {
        this.tipo_lancamento = tipo_lancamento;
    }

    @Override
    public String toString() {
        return id_lancamento + '\n' +
                descricao + '\n' +
                valor_total + '\n' +
                data + '\n' +
                morador_comprador + '\n' +
                tipo_lancamento;
    }
}
