package br.com.edumaxsantos;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.edumaxsantos.dao.LeilaoDao;

public class GeradorDePagamentoTest {

  @Test
  public void deveGerarPagamentoparaUmLeilaoEncerrado() {

    LeilaoDao leiloes = mock(LeilaoDao.class);

    RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);

    Avaliador avaliador = mock(Avaliador.class);

    Leilao leilao = new CriadorDeLeilao()
      .para("Playstation")
      .lance(new Usuario("José da Silva"), 2000.0)
      .lance(new Usuario("Maria Pereira"), 2500.0)
      .constroi();

    when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));

    when(avaliador.getMaiorLance()).thenReturn(2500.0);

    GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, avaliador);

    gerador.gera();

    ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);

    verify(pagamentos).salva(argumento.capture());

    Pagamento pagamentoGerado = argumento.getValue();
    assertEquals(2500.0, pagamentoGerado.getValor(), 0.00001);

  }

  @Test
  public void deveEmpurrarParaOProximoDiaUtil() {
    LeilaoDao leiloes = mock(LeilaoDao.class);

    RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);

    Relogio relogio = mock(Relogio.class);
    LocalDate sabado = LocalDate.of(2019, 12, 7);

    when(relogio.hoje()).thenReturn(sabado);

    Leilao leilao = new CriadorDeLeilao()
      .para("Playstation")
      .lance(new Usuario("José da Silva"), 2000.0)
      .lance(new Usuario("Maria Pereira"), 2500.0)
      .constroi();

    when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));

    GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, new Avaliador(), relogio);

    gerador.gera();

    ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
    verify(pagamentos).salva(argumento.capture());
    Pagamento pagamentoGerado = argumento.getValue();

    assertEquals(DayOfWeek.MONDAY, pagamentoGerado.getData().getDayOfWeek());
  }

}