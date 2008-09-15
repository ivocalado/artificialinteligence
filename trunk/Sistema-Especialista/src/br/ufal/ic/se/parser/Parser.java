/* This file was generated by SableCC (http://www.sablecc.org/). */

package br.ufal.ic.se.parser;

import br.ufal.ic.se.lexer.*;
import br.ufal.ic.se.node.*;
import br.ufal.ic.se.analysis.*;
import java.util.*;

import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class Parser
{
    public final Analysis ignoredTokens = new AnalysisAdapter();

    protected ArrayList nodeList;

    private final Lexer lexer;
    private final ListIterator stack = new LinkedList().listIterator();
    private int last_shift;
    private int last_pos;
    private int last_line;
    private Token last_token;
    private final TokenIndex converter = new TokenIndex();
    private final int[] action = new int[2];

    private final static int SHIFT = 0;
    private final static int REDUCE = 1;
    private final static int ACCEPT = 2;
    private final static int ERROR = 3;

    public Parser(Lexer lexer)
    {
        this.lexer = lexer;
    }

    protected void filter() throws ParserException, LexerException, IOException
    {
    }

    private void push(int numstate, ArrayList listNode, boolean hidden) throws ParserException, LexerException, IOException
    {
	this.nodeList = listNode;

        if(!hidden)
        {
            filter();
        }

        if(!stack.hasNext())
        {
            stack.add(new State(numstate, this.nodeList));
            return;
        }

        State s = (State) stack.next();
        s.state = numstate;
        s.nodes = this.nodeList;
    }

    private int goTo(int index)
    {
        int state = state();
        int low = 1;
        int high = gotoTable[index].length - 1;
        int value = gotoTable[index][0][1];

        while(low <= high)
        {
            int middle = (low + high) / 2;

            if(state < gotoTable[index][middle][0])
            {
                high = middle - 1;
            }
            else if(state > gotoTable[index][middle][0])
            {
                low = middle + 1;
            }
            else
            {
                value = gotoTable[index][middle][1];
                break;
            }
        }

        return value;
    }

    private int state()
    {
        State s = (State) stack.previous();
        stack.next();
        return s.state;
    }

    private ArrayList pop()
    {
        return (ArrayList) ((State) stack.previous()).nodes;
    }

    private int index(Switchable token)
    {
        converter.index = -1;
        token.apply(converter);
        return converter.index;
    }

    public Start parse() throws ParserException, LexerException, IOException
    {
        push(0, null, true);
        List ign = null;
        while(true)
        {
            while(index(lexer.peek()) == -1)
            {
                if(ign == null)
                {
                    ign = new TypedLinkedList(NodeCast.instance);
                }

                ign.add(lexer.next());
            }

            if(ign != null)
            {
                ignoredTokens.setIn(lexer.peek(), ign);
                ign = null;
            }

            last_pos = lexer.peek().getPos();
            last_line = lexer.peek().getLine();
            last_token = lexer.peek();

            int index = index(lexer.peek());
            action[0] = actionTable[state()][0][1];
            action[1] = actionTable[state()][0][2];

            int low = 1;
            int high = actionTable[state()].length - 1;

            while(low <= high)
            {
                int middle = (low + high) / 2;

                if(index < actionTable[state()][middle][0])
                {
                    high = middle - 1;
                }
                else if(index > actionTable[state()][middle][0])
                {
                    low = middle + 1;
                }
                else
                {
                    action[0] = actionTable[state()][middle][1];
                    action[1] = actionTable[state()][middle][2];
                    break;
                }
            }

            switch(action[0])
            {
                case SHIFT:
		    {
		        ArrayList list = new ArrayList();
		        list.add(lexer.next());
                        push(action[1], list, false);
                        last_shift = action[1];
                    }
		    break;
                case REDUCE:
                    switch(action[1])
                    {
                    case 0: /* reduce AAtomSentence */
		    {
			ArrayList list = new0();
			push(goTo(0), list, false);
		    }
		    break;
                    case 1: /* reduce AComplexSentence */
		    {
			ArrayList list = new1();
			push(goTo(0), list, false);
		    }
		    break;
                    case 2: /* reduce ATruesentenceAtomicsentence */
		    {
			ArrayList list = new2();
			push(goTo(1), list, false);
		    }
		    break;
                    case 3: /* reduce AFalsesentenceAtomicsentence */
		    {
			ArrayList list = new3();
			push(goTo(1), list, false);
		    }
		    break;
                    case 4: /* reduce ASymbolAtomicsentence */
		    {
			ArrayList list = new4();
			push(goTo(1), list, false);
		    }
		    break;
                    case 5: /* reduce ANegationComplexsentence */
		    {
			ArrayList list = new5();
			push(goTo(2), list, false);
		    }
		    break;
                    case 6: /* reduce AAndsentenceComplexsentence */
		    {
			ArrayList list = new6();
			push(goTo(2), list, false);
		    }
		    break;
                    case 7: /* reduce AOrsentenceComplexsentence */
		    {
			ArrayList list = new7();
			push(goTo(2), list, false);
		    }
		    break;
                    case 8: /* reduce AEntailssentenceComplexsentence */
		    {
			ArrayList list = new8();
			push(goTo(2), list, false);
		    }
		    break;
                    case 9: /* reduce ABentailssentenceComplexsentence */
		    {
			ArrayList list = new9();
			push(goTo(2), list, false);
		    }
		    break;
                    }
                    break;
                case ACCEPT:
                    {
                        EOF node2 = (EOF) lexer.next();
                        PSentence node1 = (PSentence) ((ArrayList)pop()).get(0);
                        Start node = new Start(node1, node2);
                        return node;
                    }
                case ERROR:
                    throw new ParserException(last_token,
                        "[" + last_line + "," + last_pos + "] " +
                        errorMessages[errors[action[1]]]);
            }
        }
    }



    ArrayList new0() /* reduce AAtomSentence */
    {
        ArrayList nodeList = new ArrayList();

        ArrayList nodeArrayList1 = (ArrayList) pop();
        PSentence psentenceNode1;
        {
        PAtomicsentence patomicsentenceNode2;
        patomicsentenceNode2 = (PAtomicsentence)nodeArrayList1.get(0);

        psentenceNode1 = new AAtomSentence(patomicsentenceNode2);
        }
	nodeList.add(psentenceNode1);
        return nodeList;
    }



    ArrayList new1() /* reduce AComplexSentence */
    {
        ArrayList nodeList = new ArrayList();

        ArrayList nodeArrayList3 = (ArrayList) pop();
        ArrayList nodeArrayList2 = (ArrayList) pop();
        ArrayList nodeArrayList1 = (ArrayList) pop();
        PSentence psentenceNode1;
        {
        TLpar tlparNode2;
        PComplexsentence pcomplexsentenceNode3;
        TRpar trparNode4;
        tlparNode2 = (TLpar)nodeArrayList1.get(0);
        pcomplexsentenceNode3 = (PComplexsentence)nodeArrayList2.get(0);
        trparNode4 = (TRpar)nodeArrayList3.get(0);

        psentenceNode1 = new AComplexSentence(tlparNode2, pcomplexsentenceNode3, trparNode4);
        }
	nodeList.add(psentenceNode1);
        return nodeList;
    }



    ArrayList new2() /* reduce ATruesentenceAtomicsentence */
    {
        ArrayList nodeList = new ArrayList();

        ArrayList nodeArrayList1 = (ArrayList) pop();
        PAtomicsentence patomicsentenceNode1;
        {
        TTrue ttrueNode2;
        ttrueNode2 = (TTrue)nodeArrayList1.get(0);

        patomicsentenceNode1 = new ATruesentenceAtomicsentence(ttrueNode2);
        }
	nodeList.add(patomicsentenceNode1);
        return nodeList;
    }



    ArrayList new3() /* reduce AFalsesentenceAtomicsentence */
    {
        ArrayList nodeList = new ArrayList();

        ArrayList nodeArrayList1 = (ArrayList) pop();
        PAtomicsentence patomicsentenceNode1;
        {
        TFalse tfalseNode2;
        tfalseNode2 = (TFalse)nodeArrayList1.get(0);

        patomicsentenceNode1 = new AFalsesentenceAtomicsentence(tfalseNode2);
        }
	nodeList.add(patomicsentenceNode1);
        return nodeList;
    }



    ArrayList new4() /* reduce ASymbolAtomicsentence */
    {
        ArrayList nodeList = new ArrayList();

        ArrayList nodeArrayList1 = (ArrayList) pop();
        PAtomicsentence patomicsentenceNode1;
        {
        TLetter tletterNode2;
        tletterNode2 = (TLetter)nodeArrayList1.get(0);

        patomicsentenceNode1 = new ASymbolAtomicsentence(tletterNode2);
        }
	nodeList.add(patomicsentenceNode1);
        return nodeList;
    }



    ArrayList new5() /* reduce ANegationComplexsentence */
    {
        ArrayList nodeList = new ArrayList();

        ArrayList nodeArrayList2 = (ArrayList) pop();
        ArrayList nodeArrayList1 = (ArrayList) pop();
        PComplexsentence pcomplexsentenceNode1;
        {
        TNot tnotNode2;
        PSentence psentenceNode3;
        tnotNode2 = (TNot)nodeArrayList1.get(0);
        psentenceNode3 = (PSentence)nodeArrayList2.get(0);

        pcomplexsentenceNode1 = new ANegationComplexsentence(tnotNode2, psentenceNode3);
        }
	nodeList.add(pcomplexsentenceNode1);
        return nodeList;
    }



    ArrayList new6() /* reduce AAndsentenceComplexsentence */
    {
        ArrayList nodeList = new ArrayList();

        ArrayList nodeArrayList3 = (ArrayList) pop();
        ArrayList nodeArrayList2 = (ArrayList) pop();
        ArrayList nodeArrayList1 = (ArrayList) pop();
        PComplexsentence pcomplexsentenceNode1;
        {
        PSentence psentenceNode2;
        TAnd tandNode3;
        PSentence psentenceNode4;
        psentenceNode2 = (PSentence)nodeArrayList1.get(0);
        tandNode3 = (TAnd)nodeArrayList2.get(0);
        psentenceNode4 = (PSentence)nodeArrayList3.get(0);

        pcomplexsentenceNode1 = new AAndsentenceComplexsentence(psentenceNode2, tandNode3, psentenceNode4);
        }
	nodeList.add(pcomplexsentenceNode1);
        return nodeList;
    }



    ArrayList new7() /* reduce AOrsentenceComplexsentence */
    {
        ArrayList nodeList = new ArrayList();

        ArrayList nodeArrayList3 = (ArrayList) pop();
        ArrayList nodeArrayList2 = (ArrayList) pop();
        ArrayList nodeArrayList1 = (ArrayList) pop();
        PComplexsentence pcomplexsentenceNode1;
        {
        PSentence psentenceNode2;
        TOr torNode3;
        PSentence psentenceNode4;
        psentenceNode2 = (PSentence)nodeArrayList1.get(0);
        torNode3 = (TOr)nodeArrayList2.get(0);
        psentenceNode4 = (PSentence)nodeArrayList3.get(0);

        pcomplexsentenceNode1 = new AOrsentenceComplexsentence(psentenceNode2, torNode3, psentenceNode4);
        }
	nodeList.add(pcomplexsentenceNode1);
        return nodeList;
    }



    ArrayList new8() /* reduce AEntailssentenceComplexsentence */
    {
        ArrayList nodeList = new ArrayList();

        ArrayList nodeArrayList3 = (ArrayList) pop();
        ArrayList nodeArrayList2 = (ArrayList) pop();
        ArrayList nodeArrayList1 = (ArrayList) pop();
        PComplexsentence pcomplexsentenceNode1;
        {
        PSentence psentenceNode2;
        TEntails tentailsNode3;
        PSentence psentenceNode4;
        psentenceNode2 = (PSentence)nodeArrayList1.get(0);
        tentailsNode3 = (TEntails)nodeArrayList2.get(0);
        psentenceNode4 = (PSentence)nodeArrayList3.get(0);

        pcomplexsentenceNode1 = new AEntailssentenceComplexsentence(psentenceNode2, tentailsNode3, psentenceNode4);
        }
	nodeList.add(pcomplexsentenceNode1);
        return nodeList;
    }



    ArrayList new9() /* reduce ABentailssentenceComplexsentence */
    {
        ArrayList nodeList = new ArrayList();

        ArrayList nodeArrayList3 = (ArrayList) pop();
        ArrayList nodeArrayList2 = (ArrayList) pop();
        ArrayList nodeArrayList1 = (ArrayList) pop();
        PComplexsentence pcomplexsentenceNode1;
        {
        PSentence psentenceNode2;
        TBidientails tbidientailsNode3;
        PSentence psentenceNode4;
        psentenceNode2 = (PSentence)nodeArrayList1.get(0);
        tbidientailsNode3 = (TBidientails)nodeArrayList2.get(0);
        psentenceNode4 = (PSentence)nodeArrayList3.get(0);

        pcomplexsentenceNode1 = new ABentailssentenceComplexsentence(psentenceNode2, tbidientailsNode3, psentenceNode4);
        }
	nodeList.add(pcomplexsentenceNode1);
        return nodeList;
    }



    private static int[][][] actionTable;
/*      {
			{{-1, ERROR, 0}, {5, SHIFT, 1}, {6, SHIFT, 2}, {7, SHIFT, 3}, {9, SHIFT, 4}, },
			{{-1, REDUCE, 2}, },
			{{-1, REDUCE, 3}, },
			{{-1, ERROR, 3}, {2, SHIFT, 7}, {5, SHIFT, 1}, {6, SHIFT, 2}, {7, SHIFT, 3}, {9, SHIFT, 4}, },
			{{-1, REDUCE, 4}, },
			{{-1, ERROR, 5}, {10, ACCEPT, -1}, },
			{{-1, REDUCE, 0}, },
			{{-1, ERROR, 7}, {5, SHIFT, 1}, {6, SHIFT, 2}, {7, SHIFT, 3}, {9, SHIFT, 4}, },
			{{-1, ERROR, 8}, {0, SHIFT, 11}, {1, SHIFT, 12}, {3, SHIFT, 13}, {4, SHIFT, 14}, },
			{{-1, ERROR, 9}, {8, SHIFT, 15}, },
			{{-1, REDUCE, 5}, },
			{{-1, ERROR, 11}, {5, SHIFT, 1}, {6, SHIFT, 2}, {7, SHIFT, 3}, {9, SHIFT, 4}, },
			{{-1, ERROR, 12}, {5, SHIFT, 1}, {6, SHIFT, 2}, {7, SHIFT, 3}, {9, SHIFT, 4}, },
			{{-1, ERROR, 13}, {5, SHIFT, 1}, {6, SHIFT, 2}, {7, SHIFT, 3}, {9, SHIFT, 4}, },
			{{-1, ERROR, 14}, {5, SHIFT, 1}, {6, SHIFT, 2}, {7, SHIFT, 3}, {9, SHIFT, 4}, },
			{{-1, REDUCE, 1}, },
			{{-1, REDUCE, 6}, },
			{{-1, REDUCE, 7}, },
			{{-1, REDUCE, 8}, },
			{{-1, REDUCE, 9}, },
        };*/
    private static int[][][] gotoTable;
/*      {
			{{-1, 5}, {3, 8}, {7, 10}, {11, 16}, {12, 17}, {13, 18}, {14, 19}, },
			{{-1, 6}, },
			{{-1, 9}, },
        };*/
    private static String[] errorMessages;
/*      {
			"expecting: 'true', 'false', '(', letter",
			"expecting: 'AND', 'OR', '=>', '<=>', ')', EOF",
			"expecting: 'NOT', 'true', 'false', '(', letter",
			"expecting: EOF",
			"expecting: 'AND', 'OR', '=>', '<=>'",
			"expecting: ')'",
        };*/
    private static int[] errors;
/*      {
			0, 1, 1, 2, 1, 3, 1, 0, 4, 5, 5, 0, 0, 0, 0, 1, 5, 5, 5, 5, 
        };*/

    static 
    {
        try
        {
            DataInputStream s = new DataInputStream(
                new BufferedInputStream(
                Parser.class.getResourceAsStream("parser.dat")));

            // read actionTable
            int length = s.readInt();
            actionTable = new int[length][][];
            for(int i = 0; i < actionTable.length; i++)
            {
                length = s.readInt();
                actionTable[i] = new int[length][3];
                for(int j = 0; j < actionTable[i].length; j++)
                {
                for(int k = 0; k < 3; k++)
                {
                    actionTable[i][j][k] = s.readInt();
                }
                }
            }

            // read gotoTable
            length = s.readInt();
            gotoTable = new int[length][][];
            for(int i = 0; i < gotoTable.length; i++)
            {
                length = s.readInt();
                gotoTable[i] = new int[length][2];
                for(int j = 0; j < gotoTable[i].length; j++)
                {
                for(int k = 0; k < 2; k++)
                {
                    gotoTable[i][j][k] = s.readInt();
                }
                }
            }

            // read errorMessages
            length = s.readInt();
            errorMessages = new String[length];
            for(int i = 0; i < errorMessages.length; i++)
            {
                length = s.readInt();
                StringBuffer buffer = new StringBuffer();

                for(int j = 0; j < length; j++)
                {
                buffer.append(s.readChar());
                }
                errorMessages[i] = buffer.toString();
            }

            // read errors
            length = s.readInt();
            errors = new int[length];
            for(int i = 0; i < errors.length; i++)
            {
                errors[i] = s.readInt();
            }

            s.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException("The file \"parser.dat\" is either missing or corrupted.");
        }
    }
}