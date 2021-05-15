package com.flair.bi.compiler.search;

import com.flair.bi.compiler.ThrowingErrorListener;
import com.flair.bi.grammar.searchql.SearchQLLexer;
import com.flair.bi.grammar.searchql.SearchQLParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class SearchQLCompiler {

	public SearchResult compile(SearchQuery query) throws SearchQLException {
		try {
			ANTLRInputStream input = new ANTLRInputStream(query.getStatement());
			SearchQLLexer lexer = new SearchQLLexer(input);
			lexer.removeErrorListeners();
			lexer.addErrorListener(new ThrowingErrorListener());

			CommonTokenStream tokens = new CommonTokenStream(lexer);

			SearchQLParser parser = new SearchQLParser(tokens);
			parser.removeErrorListeners();
			parser.addErrorListener(new ThrowingErrorListener());

			ParseTree tree = parser.parse();
			ParseTreeWalker walker = new ParseTreeWalker();
			SearchQLListener listener = new SearchQLListener();
			walker.walk(listener, tree);
			return listener.getResult();
		} catch (Exception e) {
			throw new SearchQLException(e);
		}
	}
}
