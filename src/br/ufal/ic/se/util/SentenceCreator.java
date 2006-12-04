package br.ufal.ic.se.util;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import br.ufal.ic.se.lexer.Lexer;
import br.ufal.ic.se.lexer.LexerException;
import br.ufal.ic.se.node.Start;
import br.ufal.ic.se.node.PSentence;
import br.ufal.ic.se.parser.Parser;
import br.ufal.ic.se.parser.ParserException;


public class SentenceCreator {
	public static PSentence getSentence(String expression) throws ParserException, LexerException, IOException {
		Lexer lexer = new Lexer(new PushbackReader(new StringReader(expression))); 
		Start astStartNode = new Parser(lexer).parse();
		return astStartNode.getPSentence();
	}
}
