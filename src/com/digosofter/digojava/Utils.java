package com.digosofter.digojava;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.digosofter.digojava.erro.Erro;

public abstract class Utils {

  public enum EnmDataFormato {

    DD_MM,
    DD_MM_YY,
    DD_MM_YYYY,
    DD_MM_YYYY_HH_MM,
    DD_MM_YYYY_HH_MM_SS,
    HH_MM,
    HH_MM_DD_MM_YYYY,
    HH_MM_SS_DD_MM_YYYY,
    YYYY_MM_DD_HH_MM_SS
  }

  public static enum EnmStrTipo {

    ALPHA,
    ALPHANUMERICO,
    NUMERICO
  }

  public static final Locale LOCAL_BRASIL = new Locale("pt", "BR");
  public static final String STR_VAZIA = "";

  public static double arredondar(double dblValor, int intQtdCasas, int ceilOrFloor) {

    double dblResultado = dblValor;

    try {

      dblResultado *= Math.pow(10, intQtdCasas);

      if (ceilOrFloor == 0) {

        dblResultado = Math.ceil(dblResultado);
      }
      else {

        dblResultado = Math.floor(dblResultado);
      }

      dblResultado /= Math.pow(10, intQtdCasas);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(109), ex);
    }
    finally {
    }

    return dblResultado;
  }

  private static String enmDataFormatoToString(EnmDataFormato enmDataFormato) {

    String strResultado = null;

    try {

      switch (enmDataFormato) {
        case DD_MM:
          strResultado = "dd/MM";
          break;
        case DD_MM_YY:
          strResultado = "dd/MM/yy";
          break;
        case DD_MM_YYYY:
          strResultado = "dd/MM/yyyy";
          break;
        case DD_MM_YYYY_HH_MM:
          strResultado = "dd/MM/yyyy HH:mm";
          break;
        case DD_MM_YYYY_HH_MM_SS:
          strResultado = "dd/MM/yyyy HH:mm:ss";
          break;
        case HH_MM:
          strResultado = "HH:mm";
          break;
        case HH_MM_DD_MM_YYYY:
          strResultado = "HH:mm dd/MM/yyyy";
          break;
        case HH_MM_SS_DD_MM_YYYY:
          strResultado = "HH:mm:ss dd/MM/yyyy";
          break;
        case YYYY_MM_DD_HH_MM_SS:
          strResultado = "yyyy/MM/dd HH:mm:ss";
          break;
        default:
          strResultado = "dd/MM/yyyy";
          break;
      }
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return strResultado;
  }

  /**
   * @return Retorna true se o valor de "str" é um numeral.
   */
  public static boolean getBooNumeral(String str) {

    try {

      if (Utils.getBooStrVazia(str)) {

        return false;
      }

      return str.matches("[-+]?\\d*\\.?\\d+");
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return false;
  }

  public static boolean getBooStrVazia(String str) {

    boolean booResultado = true;

    try {

      if (str != null && !str.isEmpty()) {

        booResultado = false;
      }
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return booResultado;
  }

  /**
   * Verifica se a string é uma URL válida.
   */
  public static boolean getBooUrlValida(String url) {

    boolean booResultado = true;

    try {

      new URL(url);
    }
    catch (Exception ex) {

      booResultado = false;
    }
    finally {
    }

    return booResultado;
  }

  public static int getIntNumeroAleatorio(int intMaximo) {

    int intResultado = 0;

    try {

      intResultado = new Random().nextInt(intMaximo);
    }
    catch (Exception ex) {

      new Erro("Erro ao gerar cor aleatória.\n", ex);
    }
    finally {
    }

    return intResultado;
  }

  public static String getMd5(String str) {

    BigInteger objBigInteger;
    MessageDigest objMessageDigest;
    String md5Resultado = null;

    try {

      objMessageDigest = MessageDigest.getInstance("MD5");
      objBigInteger = new BigInteger(1, objMessageDigest.digest(str.getBytes()));

      md5Resultado = String.format("%0" + (objMessageDigest.digest(str.getBytes()).length << 1) + "X", objBigInteger);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(112), ex);
    }
    finally {
    }

    return md5Resultado;
  }

  public static String getStrAleatoria(int intTamanho, EnmStrTipo enmStrTipo) {

    int intCharactersLength;
    StringBuffer stbResultado = new StringBuffer();
    String strCharacters = "";

    try {

      switch (enmStrTipo) {
        case ALPHA:
          strCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
          break;
        case ALPHANUMERICO:
          strCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
          break;
        case NUMERICO:
          strCharacters = "1234567890";
          break;
      }

      intCharactersLength = strCharacters.length();

      for (int i = 0; i < intTamanho; i++) {

        double index = Math.random() * intCharactersLength;
        stbResultado.append(strCharacters.charAt((int) index));
      }
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(111), ex);
    }
    finally {
    }

    return stbResultado.toString();
  }

  public static String getStrConcatenarLst(List<String> lstStr, String strDelimitador, boolean booEliminarDuplicata) {

    boolean booStrIncluida;
    List<String> lstStrIncluida;
    String strDelimitador2;
    String strResultado = Utils.STR_VAZIA;
    StringBuilder stb;

    try {

      booStrIncluida = false;
      lstStrIncluida = new ArrayList<String>();
      stb = new StringBuilder();
      strDelimitador2 = Utils.STR_VAZIA;
      strResultado = Utils.STR_VAZIA;

      for (String str : lstStr) {

        if (booEliminarDuplicata) {

          for (String strInserida : lstStrIncluida) {

            if (strInserida == str) {
              booStrIncluida = true;
            }
          }
        }

        if (!booStrIncluida) {

          stb.append(strDelimitador2);
          stb.append(str);

          strDelimitador2 = strDelimitador;

          lstStrIncluida.add(str);
        }
      }

      strResultado = stb.toString();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return strResultado;
  }

  public static String getStrConcatenarLst(String[] arrStr, String strDelimitador, boolean booEliminarDuplicata) {

    List<String> lstStr = null;

    try {

      lstStr = new ArrayList<String>();

      for (String str : arrStr) {

        lstStr.add(str);
      }

    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return Utils.getStrConcatenarLst(lstStr, strDelimitador, booEliminarDuplicata);
  }

  public static String getStrDataFormatada(GregorianCalendar objGregorianCalendar, EnmDataFormato enmDataFormato) {

    String strDataFormato = Utils.STR_VAZIA;
    SimpleDateFormat objSimpleDateFormat = null;

    try {

      if (objGregorianCalendar == null) {

        return Utils.STR_VAZIA;
      }

      strDataFormato = Utils.enmDataFormatoToString(enmDataFormato);
      objSimpleDateFormat = new SimpleDateFormat(strDataFormato, LOCAL_BRASIL);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return objSimpleDateFormat.format(objGregorianCalendar.getTime());
  }

  public static String getStrPrimeiraMaiuscula(String str) {

    try {

      str = str.substring(0, 1).toUpperCase(LOCAL_BRASIL) + str.substring(1);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return str;
  }

  public static String getStrSimplificada(String strComplexa) {

    String[] arrChrAcentos;
    String[] arrChrCaracteresEspeciais;
    String[] arrChrSemAcento;

    try {

      strComplexa = strComplexa.toLowerCase(Utils.LOCAL_BRASIL);

      arrChrAcentos = new String[] { "ç", "á", "é", "í", "ó", "ú", "ý", "à", "è", "ì", "ò", "ù", "ã", "õ", "ñ", "ä", "ë", "ï", "ö", "ü", "ÿ", "â", "ê", "î", "ô", "û" };
      arrChrSemAcento = new String[] { "c", "a", "e", "i", "o", "u", "y", "a", "e", "i", "o", "u", "a", "o", "n", "a", "e", "i", "o", "u", "y", "a", "e", "i", "o", "u" };

      for (int intTemp = 0; intTemp < arrChrAcentos.length; intTemp++) {

        strComplexa = strComplexa.replace(arrChrAcentos[intTemp], arrChrSemAcento[intTemp]);
      }

      arrChrCaracteresEspeciais = new String[] { "/", "\\.", ",", "-", ":", "\\(", "\\)", "ª", "\\|", "\\\\", "°", "^\\s+", "\\s+$", "\\s+", ".", "(", ")" };

      for (String arrChrCaracteresEspeciai : arrChrCaracteresEspeciais) {

        strComplexa = strComplexa.replace(arrChrCaracteresEspeciai, "");
      }

      strComplexa = strComplexa.replace(" ", "_");
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {

      arrChrAcentos = null;
      arrChrCaracteresEspeciais = null;
      arrChrSemAcento = null;
    }

    return strComplexa;
  }

  public static String getStrToken(List<String> lstStrTermo) {

    return Utils.getStrToken(lstStrTermo, 5);
  }

  public static String getStrToken(List<String> lstStrTermo, int intTamanho) {

    String strTermoMd5;
    String strResultado = Utils.STR_VAZIA;

    try {

      for (String strTermo : lstStrTermo) {

        if (Utils.getBooStrVazia(strTermo)) {

          continue;
        }

        strTermoMd5 = Utils.getMd5(strTermo);
        strResultado = Utils.getMd5(strResultado + strTermoMd5);
      }

      strResultado = strResultado.substring(0, intTamanho);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return strResultado;
  }

  public static String getStrValorMonetario(double monValor) {

    NumberFormat objNumberFormat = null;

    try {

      objNumberFormat = NumberFormat.getCurrencyInstance(LOCAL_BRASIL);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return objNumberFormat.format(monValor);
  }

  public static String gregorianCalendarToString(GregorianCalendar objGregorianCalendar) {

    StringBuilder stbResultado = null;

    try {

      stbResultado = new StringBuilder();
      stbResultado.append(String.format("%d/%02d/%02d", objGregorianCalendar.get(Calendar.DAY_OF_MONTH), objGregorianCalendar.get(Calendar.MONTH) + 1, objGregorianCalendar.get(Calendar.YEAR)));
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return stbResultado.toString();
  }

  /**
   * "Pinga" um host e retorna true caso haja resposta deste.
   */
  public static boolean ping(String url) {

    boolean booResultado = false;
    HttpURLConnection objHttpURLConnection;

    try {

      objHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();
      objHttpURLConnection.setRequestMethod("HEAD");

      booResultado = objHttpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK;
    }
    catch (Exception ex) {

      booResultado = false;
    }
    finally {
    }

    return booResultado;
  }

  public static String removerMascara(String s) {

    return s.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", "");
  }

  public static String removerUltimaLetra(String str) {

    try {

      str = str.substring(0, str.length() - 1);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return str;
  }

  public static String removerUltimaLetra(String str, int intQtdCaracter) {

    try {

      str = str.substring(0, str.length() - intQtdCaracter);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return str;
  }

  public static Date strToDte(String strDte, EnmDataFormato enmDataFormato) {

    Date dteResultado = null;
    SimpleDateFormat sdf;

    try {

      sdf = new SimpleDateFormat(Utils.enmDataFormatoToString(enmDataFormato), LOCAL_BRASIL);
      dteResultado = sdf.parse(strDte);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return dteResultado;
  }

  public static GregorianCalendar strToGregorianCalendar(String strDte) {

    GregorianCalendar dteResultado = null;

    try {

      dteResultado = new GregorianCalendar();

      dteResultado.setTime(Utils.strToDte(strDte, EnmDataFormato.DD_MM_YYYY));
      dteResultado.add(Calendar.MONTH, 1);

      if (dteResultado.get(Calendar.MONTH) == 12) {

        dteResultado.add(Calendar.YEAR, 1);
      }
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return dteResultado;
  }
}
