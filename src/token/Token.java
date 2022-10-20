package token;

public class Token {

    final private int riga;
    final private TokenType tipo;
    final private String val;

    public Token(TokenType tipo, int riga, String val) {
        this.riga = riga;
        this.tipo = tipo;
        this.val = val;
    }

    public Token(TokenType tipo, int riga) {
        this.riga = riga;
        this.tipo = tipo;
        this.val = null;
    }


    public int getRiga() {
        return riga;
    }

    public TokenType getTipo() {
        return tipo;
    }

    public String getVal() {
        return val;
    }


    public String toString() {
        if(this.val == null)
            return "<"+this.tipo.toString()+",r:"+this.riga+">";
        else
            return "<"+this.tipo.toString()+",r:"+this.riga+","+this.val.toString()+">";
    }

}
