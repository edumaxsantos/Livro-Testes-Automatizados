package br.com.edumaxsantos;

public class Lance {

  private Usuario usuario;
  private double valor;

  public Lance(Usuario usuario, double valor) {
    this.usuario = usuario;
    this.valor = valor;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public double getValor() {
    return valor;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (o == null)
      return false;
    if(getClass() != o.getClass())
      return false;

    Lance other = (Lance) o;
    return this.usuario.getNome().equals(other.usuario.getNome())
    && this.valor == other.getValor();
  }

}