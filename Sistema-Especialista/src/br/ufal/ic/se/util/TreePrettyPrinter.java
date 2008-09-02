package br.ufal.ic.se.util;

import br.ufal.ic.se.analysis.DepthFirstAdapter;
import br.ufal.ic.se.node.Node;
import br.ufal.ic.se.node.Token;
import java.io.PrintWriter;

public class TreePrettyPrinter extends DepthFirstAdapter {
    private int depth = 0;
    private PrintWriter out;

    public TreePrettyPrinter(PrintWriter out) {
        this.out = out;
    }

    public void defaultCase(Node node) {
        indent();
        out.println(((Token)node).getText());
    }

    public void defaultIn(Node node) {
        indent();
        printNodeName(node);
        out.println();

        depth = depth+1;
    }

    public void defaultOut(Node node) {
        depth = depth-1;
        out.flush();
    }

    private void printNodeName(Node node) {
        String fullName = node.getClass().getName();
        String name = fullName.substring(fullName.lastIndexOf('.')+1);

        out.print(name);
    }

    private void indent() {
        for (int i = 0; i < depth; i++) out.write("   ");
    }   
}
