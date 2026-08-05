package br.edu.utfpr.gabrielmoura.divisaodespesas.modelo;

import java.io.Serializable;

public class Item implements Serializable {

    private Long id_item;
    private String descricao_item;
    private int quantidade;
    private Double valor_unitario;
    private Double valor_desconto;
    private Double valor_total;
    private boolean is_rateio_grupo; // checkbox identificando se o item é rateado entre os moradores ou pelo casal
    private int grupo_rateio; // spinner listando os casais cadastrados, caso o item seja rateado pelo casal

    public Item() {
    }

    public Item(String descricao_item, int quantidade, Double valor_unitario, Double valor_desconto, Double valor_total, boolean is_rateio_grupo, int grupo_rateio) {
        this.descricao_item = descricao_item;
        this.quantidade = quantidade;
        this.valor_unitario = valor_unitario;
        this.valor_desconto = valor_desconto;
        this.valor_total = valor_total;
        this.is_rateio_grupo = is_rateio_grupo;
        this.grupo_rateio = grupo_rateio;
    }

    public Long getId_item() {
        return id_item;
    }

    public void setId_item(Long id_item) {
        this.id_item = id_item;
    }

    public String getDescricao_item() {
        return descricao_item;
    }

    public void setDescricao_item(String descricao_item) {
        this.descricao_item = descricao_item;
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

    public boolean isIs_rateio_grupo() {
        return is_rateio_grupo;
    }

    public void setIs_rateio_grupo(boolean is_rateio_grupo) {
        this.is_rateio_grupo = is_rateio_grupo;
    }

    public int getGrupo_rateio() {
        return grupo_rateio;
    }

    public void setGrupo_rateio(int grupo_rateio) {
        this.grupo_rateio = grupo_rateio;
    }

    @Override
    public String toString() {
        return descricao_item + '\n' +
                quantidade + '\n' +
                valor_unitario + '\n' +
                valor_desconto + '\n' +
                valor_total + '\n' +
                is_rateio_grupo + '\n' +
                grupo_rateio;
    }
}
