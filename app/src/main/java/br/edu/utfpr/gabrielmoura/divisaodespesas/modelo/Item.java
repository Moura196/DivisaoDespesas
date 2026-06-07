package br.edu.utfpr.gabrielmoura.divisaodespesas.modelo;

import java.io.Serializable;

public class Item implements Serializable {

    private Long id_item;
    private String descricao;
    private int quantidade;
    private Double valor_unitario;
    private Double valor_desconto;
    private Double valor_total;
    private boolean rateio_casal; // checkbox identificando se o item é rateado entre os moradores ou pelo casal
    private int casal_rateio; // spinner listando os casais cadastrados, caso o item seja rateado pelo casal

    public Item() {
    }

    public Item(String descricao, int quantidade, Double valor_unitario, Double valor_desconto, Double valor_total, boolean rateio_casal, int casal_rateio) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor_unitario = valor_unitario;
        this.valor_desconto = valor_desconto;
        this.valor_total = valor_total;
        this.rateio_casal = rateio_casal;
        this.casal_rateio = casal_rateio;
    }

    public Long getId_item() {
        return id_item;
    }

    public void setId_item(Long id_item) {
        this.id_item = id_item;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(Double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public Double getValor_desconto() {
        return valor_desconto;
    }

    public void setValor_desconto(Double valor_desconto) {
        this.valor_desconto = valor_desconto;
    }

    public Double getValor_total() {
        return valor_total;
    }

    public void setValor_total(Double valor_total) {
        this.valor_total = valor_total;
    }

    public boolean isRateio_casal() {
        return rateio_casal;
    }

    public void setRateio_casal(boolean rateio_casal) {
        this.rateio_casal = rateio_casal;
    }

    public int getCasal_rateio() {
        return casal_rateio;
    }

    public void setCasal_rateio(int casal_rateio) {
        this.casal_rateio = casal_rateio;
    }

    @Override
    public String toString() {
        return descricao + '\n' +
                quantidade + '\n' +
                valor_unitario + '\n' +
                valor_desconto + '\n' +
                valor_total + '\n' +
                rateio_casal + '\n' +
                casal_rateio;
    }
}
