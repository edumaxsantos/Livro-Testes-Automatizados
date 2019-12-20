package br.com.edumaxsantos;

import java.time.LocalDate;

public class CriadorDeLeilao {

  private Leilao leilao;

  public CriadorDeLeilao() {}

  public CriadorDeLeilao para(String descricao) {
    this.leilao = new Leilao(descricao);
    return this;
  }

  public CriadorDeLeilao lance(Usuario usuario, double valor) {
    leilao.propoe(new Lance(usuario, valor));
    return this;
  }

  public CriadorDeLeilao naData(LocalDate data) {
    leilao.setData(data);
    return this;
  }

  public Leilao constroi() {
    return leilao;
  }

}