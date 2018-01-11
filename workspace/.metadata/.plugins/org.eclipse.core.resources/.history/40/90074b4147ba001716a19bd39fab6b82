package api.jdbc.resposta.ex01;

public class Fone implements Comparable<Fone> {
	private Integer id = null;
	private String ddd = "11";
	private String numero = "";
	private TipoFone tipo = TipoFone.CELULAR;

	public Fone() {
		super();
	}
	
	public Fone(String numero) {
		super();
		this.numero = numero;
	}

	public Fone(String ddd, String numero, TipoFone tipo) {
		super();
		this.ddd = ddd;
		this.numero = numero;
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoFone getTipo() {
		return tipo;
	}

	public void setTipo(TipoFone tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int BASE = 2348;
		return ddd.hashCode() * BASE +
		       numero.hashCode() * BASE +
		       tipo.hashCode() * BASE;
	}

	@Override
	public boolean equals(Object obj) {
		boolean ret = false;
		
		if(obj instanceof Fone) {
			Fone f = (Fone)obj;
			ret = ddd.equals(f.ddd) &&
			      numero.equals(f.numero) &&
			      tipo.equals(f.tipo);
		}
		
		return ret;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Fone f = new Fone();
		f.setDdd(new String(ddd));
		f.setNumero(new String(numero));
		f.setTipo(tipo);
		
		return f;
	}

	@Override
	public int compareTo(Fone o) {
		int ret = ddd.compareTo(o.ddd);
		
		if(ret == 0) {
			ret = numero.compareTo(o.numero);
			
		  if(ret == 0)
		  	ret = tipo.compareTo(o.tipo);
		}
		
		return ret;
	}

	@Override
	public String toString() {
		return "ddd: " + ddd + ", numero: " + numero + ", tipo: " + tipo;
	}
}
