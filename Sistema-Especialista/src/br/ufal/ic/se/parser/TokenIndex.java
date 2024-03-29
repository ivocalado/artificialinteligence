/* This file was generated by SableCC (http://www.sablecc.org/). */

package br.ufal.ic.se.parser;

import br.ufal.ic.se.node.*;
import br.ufal.ic.se.analysis.*;

class TokenIndex extends AnalysisAdapter
{
    int index;

    public void caseTAnd(TAnd node)
    {
        index = 0;
    }

    public void caseTOr(TOr node)
    {
        index = 1;
    }

    public void caseTNot(TNot node)
    {
        index = 2;
    }

    public void caseTEntails(TEntails node)
    {
        index = 3;
    }

    public void caseTBidientails(TBidientails node)
    {
        index = 4;
    }

    public void caseTTrue(TTrue node)
    {
        index = 5;
    }

    public void caseTFalse(TFalse node)
    {
        index = 6;
    }

    public void caseTLpar(TLpar node)
    {
        index = 7;
    }

    public void caseTRpar(TRpar node)
    {
        index = 8;
    }

    public void caseTLetter(TLetter node)
    {
        index = 9;
    }

    public void caseEOF(EOF node)
    {
        index = 10;
    }
}
