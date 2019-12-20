package br.com.edumaxsantos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

  private String descricao;
  private List<Lance> lances;
  private LocalDate data;
  private boolean encerrado;

  public Leilao(String descricao) {
    this.descricao = descricao;
    this.lances = new ArrayList<>();
    data = LocalDate.now();
    encerrado = false;
  }

  public void propoe(Lance lance) {

    if(lances.isEmpty() || podeDarLance(lance.getUsuario())) {
     lances.add(lance);
    }
    
  }

  private boolean podeDarLance(Usuario usuario) {
    return  !ultimoLanceDado().getUsuario().equals(usuario)
    && qtdeDelancesDo(usuario) < 5;
  }

  private int qtdeDelancesDo(Usuario usuario) {
    int total = 0;
    for(Lance lance: lances) {
      if(lance.getUsuario().equals(usuario))
        total++;
    }
    return total;
  }

  private Lance ultimoLanceDado() {
    return lances.get(lances.size() - 1);
  }

  public String getDescricao() {
    return descricao;
  }

  public List<Lance> getLances() {
    return Collections.unmodifiableList(lances);
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public LocalDate getData() {
    return data;
  }

  public void encerra() {
    encerrado = true;
  }

  public boolean isEncerrado() {
    return encerrado;
  }

}