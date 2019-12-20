package br.com.edumaxsantos;

import java.time.LocalDate;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;
import br.com.edumaxsantos.dao.*;

public class EncerradorDeLeilao {

  private int total = 0;
  private final LeilaoDao dao;
  private final Carteiro carteiro;

  public EncerradorDeLeilao(LeilaoDao dao, Carteiro carteiro) {
    this.dao = dao;
    this.carteiro = carteiro;
  }

  public void encerra() {

    List<Leilao> todosLeiloesCorrentes = dao.correntes();

    for(Leilao leilao: todosLeiloesCorrentes) {
      try {
        if(comecouSemanaPassada(leilao)) {
          leilao.encerra();
          total++;
          dao.atualiza(leilao);
          carteiro.envia(leilao);
        }
      } catch(Exception e) {
        // salvo a exceção no sistema de logs
        // e o loop continua!
      }
    }
  }

  private long diasEntre(LocalDate inicio, LocalDate fim) {
    return  DAYS.between(inicio, fim);
  }

  private boolean comecouSemanaPassada(Leilao leilao) {
    return diasEntre(leilao.getData(), LocalDate.now()) >= 7;
  }

  public int getQuantidadeDeEncerrados() {
    return total;
  }

}