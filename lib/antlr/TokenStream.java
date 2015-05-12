package antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.cs.usfca.edu
 * Software rights: http://www.antlr.org/license.html
 *
 * $Id: TokenStream.java,v 1.1 2007-10-19 15:54:57 soto-r Exp $
 */

public interface TokenStream {
    public Token nextToken() throws TokenStreamException;
}
