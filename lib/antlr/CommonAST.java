package antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.cs.usfca.edu
 * Software rights: http://www.antlr.org/license.html
 *
 * $Id: CommonAST.java,v 1.1 2007-10-19 15:54:58 soto-r Exp $
 */

import antlr.collections.AST;

/** Common AST node implementation */
public class CommonAST extends BaseAST {
    int ttype = Token.INVALID_TYPE;

    String text;

    // user code============================
    int column;

    int line;

    String filename;

    // user code============================

    /** Get the token text for this node */
    public String getText() {
        return text;
    }

    /** Get the token type for this node */
    public int getType() {
        return ttype;
    }

    public void initialize(int t, String txt) {
        setType(t);
        setText(txt);
    }

    public void initialize(AST t) {
        setText(t.getText());
        setType(t.getType());
    }

    public CommonAST() {
    }

    public CommonAST(Token tok) {
        initialize(tok);
    }

    public void initialize(Token tok) {
        setText(tok.getText());
        setType(tok.getType());
        // user code============================
        setColumn(tok.getColumn());
        setLine(tok.getLine());
        setFilename(tok.getFilename());
        // user code============================
    }

    /** Set the token text for this node */
    public void setText(String text_) {
        text = text_;
    }

    /** Set the token type for this node */
    public void setType(int ttype_) {
        ttype = ttype_;
    }

    // user code============================
    public void setColumn(int column_) {
        column = column_;
    }

    public void setLine(int line_) {
        line = line_;
    }

    public void setFilename(String filename_) {
        filename = filename_;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public String getFilename() {
        return filename;
    }
    // user code============================
}
