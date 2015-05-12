package antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.cs.usfca.edu
 * Software rights: http://www.antlr.org/license.html
 *
 * $Id: TokenStreamRetryException.java,v 1.1 2007-10-19 15:54:59 soto-r Exp $
 */

/**
 * Aborted recognition of current token. Try to get one again. Used by
 * TokenStreamSelector.retry() to force nextToken() of stream to re-enter and
 * retry.
 */
public class TokenStreamRetryException extends TokenStreamException {
    public TokenStreamRetryException() {
    }
}
