package br.ufal.ic.se.models;

import br.ufal.ic.se.node.*;
import java.util.List;


class HornClause {
	private TLetter head;
	private List<TLetter> body;
	
	public HornClause(TLetter head, List<TLetter> body) {
		this.head = head;
		this.body = body;
	}
	
	public TLetter getHead() {
		return head;
	}
	
	public List<TLetter> getBody() {
		return body;
	}
	
	public String toString() {
		return "head: " + head + " body: " + body;		
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((head == null) ? 0 : head.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		final HornClause other = (HornClause) obj;
		
		if (body.size() != other.body.size())
			return false;
		for (TLetter t : body) {
			if (!other.body.contains(t))
				return false;
		}
		return true;
	}
}
