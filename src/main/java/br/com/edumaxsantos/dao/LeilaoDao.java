package br.com.edumaxsantos.dao;

import br.com.edumaxsantos.Leilao;

import java.util.List;

public interface LeilaoDao {
  void salva(Leilao leilao);
  List<Leilao> encerrados();
  List<Leilao> correntes();
  void atualiza(Leilao leilao);
}