/* This file was generated by SableCC (http://www.sablecc.org/). */

package br.ufal.ic.se.node;

import java.util.*;
import br.ufal.ic.se.analysis.*;

public class AEntailssentenceComplexsentence extends PComplexsentence
{
    private PSentence _left_;
    private TEntails _entails_;
    private PSentence _right_;

    public AEntailssentenceComplexsentence()
    {
    }

    public AEntailssentenceComplexsentence(
        PSentence _left_,
        TEntails _entails_,
        PSentence _right_)
    {
        setLeft(_left_);

        setEntails(_entails_);

        setRight(_right_);

    }
    public Object clone()
    {
        return new AEntailssentenceComplexsentence(
            (PSentence) cloneNode(_left_),
            (TEntails) cloneNode(_entails_),
            (PSentence) cloneNode(_right_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAEntailssentenceComplexsentence(this);
    }

    public PSentence getLeft()
    {
        return _left_;
    }

    public void setLeft(PSentence node)
    {
        if(_left_ != null)
        {
            _left_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _left_ = node;
    }

    public TEntails getEntails()
    {
        return _entails_;
    }

    public void setEntails(TEntails node)
    {
        if(_entails_ != null)
        {
            _entails_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _entails_ = node;
    }

    public PSentence getRight()
    {
        return _right_;
    }

    public void setRight(PSentence node)
    {
        if(_right_ != null)
        {
            _right_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _right_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_left_)
            + toString(_entails_)
            + toString(_right_);
    }

    void removeChild(Node child)
    {
        if(_left_ == child)
        {
            _left_ = null;
            return;
        }

        if(_entails_ == child)
        {
            _entails_ = null;
            return;
        }

        if(_right_ == child)
        {
            _right_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_left_ == oldChild)
        {
            setLeft((PSentence) newChild);
            return;
        }

        if(_entails_ == oldChild)
        {
            setEntails((TEntails) newChild);
            return;
        }

        if(_right_ == oldChild)
        {
            setRight((PSentence) newChild);
            return;
        }

    }
}
