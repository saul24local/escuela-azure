package org.gerdoc.util;

public final class Html
{
    private Html( )
    {
    }

    /** Escapa texto para imprimirlo de forma segura en HTML o dentro de atributos. */
    public static String esc(Object valor)
    {
        if (valor == null)
        {
            return "";
        }
        String s = String.valueOf(valor);
        StringBuilder sb = new StringBuilder(s.length( ) + 16);
        for (char c : s.toCharArray( ))
        {
            switch (c)
            {
                case '&': sb.append("&amp;"); break;
                case '<': sb.append("&lt;"); break;
                case '>': sb.append("&gt;"); break;
                case '"': sb.append("&quot;"); break;
                case '\'': sb.append("&#39;"); break;
                default: sb.append(c);
            }
        }
        return sb.toString( );
    }
}
