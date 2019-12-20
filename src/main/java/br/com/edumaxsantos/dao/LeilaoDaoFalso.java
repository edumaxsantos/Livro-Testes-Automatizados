package br.com.edumaxsantos.dao;

import br.com.edumaxsantos.Leilao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LeilaoDaoFalso implements LeilaoDao {

  private static List<Leilao> leiloes = new ArrayList<>();

  public void salva(Leilao leilao) {
    leiloes.add(leilao);
  }

  public List<Leilao> encerrados() {
    return leiloes.stream()
      .filter(Leilao::isEncerrado)
      .collect(Collectors.toList());
  }
  
  public List<Leilao> correntes() {
    return leiloes.stream()
      .filter(leilao -> !leilao.isEncerrado())
      .collect(Collectors.toList());
  }

  public void atualiza(Leilao leilao) {
    
  }

}