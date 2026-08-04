package br.edu.utfpr.gabrielmoura.divisaodespesas.modelo;

import br.edu.utfpr.gabrielmoura.divisaodespesas.Morador.Genero;

/** Entidade Morador:
 * Para cadastro de moradores de um GrupoMoradores*/
public class Morador {

    private Long id_morador;
    private String nome;
    private Genero genero;
    // TODO: Develop a GrupoFamiliar class for a later registration functionality. I need to register a ConjuntoFamiliar to insert one or
    //  more objects that is an instance of Morador and then create GrupoFamiliar to make possible for the user to indentify which
    //  Item is for a specific GrupoFamiliar to pay or not.(CONFIRM THE NAMES I AM GIVING)
    private int grupo_familiar; //spinner listando os grupos familiares cadastrados
    private boolean responsavel_contas; //checkbox identificando se o morador é responsável por pagar as contas

    public Morador(Long id_morador, String nome, Genero genero, int grupo_familiar, boolean responsavel_contas) {
        this.id_morador = id_morador;
        this.nome = nome;
        this.genero = genero;
        this.grupo_familiar = grupo_familiar;
        this.responsavel_contas = responsavel_contas;
    }

    public Long getId_morador() {
        return id_morador;
    }

    public void setId_morador(Long id_morador) {
        this.id_morador = id_morador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getGrupo_familiar() {
        return grupo_familiar;
    }

    public void setGrupo_familiar(int grupo_familiar) {
        this.grupo_familiar = grupo_familiar;
    }

    public boolean isResponsavel_contas() {
        return responsavel_contas;
    }

    public void setResponsavel_contas(boolean responsavel_contas) {
        this.responsavel_contas = responsavel_contas;
    }

    @Override
    public String toString() {
        return id_morador + '\n' +
                nome + '\n' +
                genero + '\n' +
                grupo_familiar + '\n' +
                responsavel_contas;
    }
}
