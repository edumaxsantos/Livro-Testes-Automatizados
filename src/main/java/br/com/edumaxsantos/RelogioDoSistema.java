package br.com.edumaxsantos;

import java.time.LocalDate;

public class RelogioDoSistema implements Relogio {
  public LocalDate hoje() {
    return LocalDate.now();
  }
}