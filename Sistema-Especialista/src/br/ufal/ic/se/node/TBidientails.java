/* This file was generated by SableCC (http://www.sablecc.org/). */

package br.ufal.ic.se.node;

import br.ufal.ic.se.analysis.*;

public final class TBidientails extends Token
{
    public TBidientails()
    {
        super.setText("<=>");
    }

    public TBidientails(int line, int pos)
    {
        super.setText("<=>");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TBidientails(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTBidientails(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TBidientails text.");
    }
}
