package br.com.edumaxsantos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import br.com.edumaxsantos.dao.LeilaoDao;

public class GeradorDePagamento {

  private final RepositorioDePagamentos pagamentos;
  private final LeilaoDao leiloes;
  private final Avaliador avaliador;
  private final Relogio relogio;

  public GeradorDePagamento(LeilaoDao leiloes, RepositorioDePagamentos pagamentos, Avaliador avaliador, Relogio relogio) {
    this.leiloes = leiloes;
    this.pagamentos = pagamentos;
    this.avaliador = avaliador;
    this.relogio = relogio;
  }

  public GeradorDePagamento(LeilaoDao leiloes, RepositorioDePagamentos pagamentos, Avaliador avaliador) {
    this(leiloes, pagamentos, avaliador, new RelogioDoSistema());
  }

  public void gera() {
    List<Leilao> leiloesEncerrados = leiloes.encerrados();

    leiloesEncerrados.forEach(avaliador::avalia);
    Pagamento novoPagamento = new Pagamento(avaliador.getMaiorLance(), primeiroDiaUtil());
    pagamentos.salva(novoPagamento);

  }

  private LocalDate primeiroDiaUtil() {
    LocalDate data = relogio.hoje();
    while(Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(data.getDayOfWeek())) {
      data = data.plusDays(1);
    }

    return data;
  }


}