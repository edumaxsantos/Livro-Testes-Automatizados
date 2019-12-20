package br.com.edumaxsantos;

import br.com.edumaxsantos.dao.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class EncerradorDeLeilaoTest {

  @Test
  public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {

    LocalDate antiga = LocalDate.of(1999, 1, 20);

    Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
      .naData(antiga)
      .constroi();

    Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
      .naData(antiga)
      .constroi();

    List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

    // criamos o mock
    LeilaoDao daoFalso = mock(LeilaoDao.class);

    // ensinamos ele a retornar a lista de leilões antigos
    when(daoFalso.correntes()).thenReturn(leiloesAntigos);

    Carteiro carteiroFalso = mock(Carteiro.class);

    EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);

    encerrador.encerra();

    assertTrue(leilao1.isEncerrado());
    assertTrue(leilao2.isEncerrado());
    assertEquals(2, encerrador.getQuantidadeDeEncerrados());
  }

  @Test
  public void deveAtualizarLeiloesEncerrados() {

    LocalDate antiga = LocalDate.of(1999, 1, 20);

    Leilao leilao1 = new CriadorDeLeilao()
      .para("TV de plasma")
      .naData(antiga)
      .constroi();

    LeilaoDao daoFalso = mock(LeilaoDao.class);

    when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));

    Carteiro carteiroFalso = mock(Carteiro.class);

    EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);

    encerrador.encerra();

    // verificando que o método atualiza foi realmente invocado
    verify(daoFalso, times(1)).atualiza(leilao1);

  }

  @Test
  public void deveContinuarAExecucaoMesmoQaundoDaoFalha() {
    LocalDate antiga = LocalDate.of(1999, 1, 20);

    Leilao leilao1 = new CriadorDeLeilao()
      .para("TV de plasma")
      .naData(antiga)
      .constroi();
    
    Leilao leilao2 = new CriadorDeLeilao()
      .para("Geladeira")
      .naData(antiga)
      .constroi();

    LeilaoDao daoFalso = mock(LeilaoDao.class);

    when(daoFalso.correntes())
    .thenReturn(Arrays.asList(leilao1, leilao2));

    doThrow(new RuntimeException()).when(daoFalso)
      .atualiza(leilao1);

      Carteiro carteiro = mock(Carteiro.class);

      EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiro);

    encerrador.encerra();

    verify(daoFalso).atualiza(leilao2);
    verify(carteiro).envia(leilao2);


  }

}