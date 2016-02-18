package com.digosofter.digojava;

import com.digosofter.digojava.erro.Erro;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public abstract class Utils {

  public enum EnmDataFormato {

    DD,
    DD_MM,
    DD_MM_YY,
    DD_MM_YYYY,
    DD_MM_YYYY_HH_MM,
    DD_MM_YYYY_HH_MM_SS,
    HH_MM,
    HH_MM_DD_MM_YY,
    HH_MM_DD_MM_YYYY,
    HH_MM_SS_DD_MM_YYYY,
    MM,
    YYYY,
    YYYY_MM_DD,
    YYYY_MM_DD_HH_MM_SS,
  }

  public static enum EnmStrTipo {

    ALPHA,
    ALPHANUMERICO,
    NUMERICO
  }

  public static final Locale LOCAL_BRASIL = new Locale("pt", "BR");
  public static final String STR_VAZIA = "";

  public static String addMascara(String str, String strMascara) {

    String strResultado;

    try {

      if (Utils.getBooStrVazia(str)) {

        return null;
      }

      if (Utils.getBooStrVazia(strMascara)) {

        return null;
      }

      strResultado = Utils.STR_VAZIA;

      for (char chr : strMascara.toCharArray()) {

        if (chr == '*' && str.length() > 0) {

          strResultado += str.charAt(0);
          str = str.substring(1);
          continue;
        }

        strResultado += chr;
      }

      return strResultado;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  public static String addMascaraCep(String strCep) {

    try {

      if (Utils.getBooStrVazia(strCep)) {

        return null;
      }

      strCep = Utils.simplificar(strCep);
      strCep = Utils.getStrFixa(strCep, 10);

      return Utils.addMascara(strCep, "**.***-***");
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  public static String addMascaraCnpj(String strCnpj) {

    try {

      if (Utils.getBooStrVazia(strCnpj)) {

        return null;
      }

      strCnpj = Utils.simplificar(strCnpj);
      strCnpj = Utils.getStrFixa(strCnpj, 14);

      return Utils.addMascara(strCnpj, "**.***.***/****-**");
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  public static String addMascaraCpf(String strCpf) {

    try {

      if (Utils.getBooStrVazia(strCpf)) {

        return null;
      }

      strCpf = Utils.simplificar(strCpf);
      strCpf = Utils.getStrFixa(strCpf, 11);

      return Utils.addMascara(strCpf, "***.***.***-**");
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  public static double arredondar(double dblValor, int intQtdCasas) {

    double dblResultado;

    try {

      dblResultado = dblValor;
      dblResultado *= Math.pow(10, intQtdCasas);
      dblResultado = Math.floor(dblResultado);
      dblResultado /= Math.pow(10, intQtdCasas);

      return dblResultado;
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(109), ex);
    }
    finally {
    }

    return 0;
  }

  /**
   * @return Retorna true se o valor de "str" é um numeral.
   */
  public static boolean getBooNumeral(String str) {

    try {

      if (Utils.getBooStrVazia(str)) {

        return false;
      }

      str = Utils.simplificar(str);

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

    return ((str == null) || (str.isEmpty()));
  }

  /**
   * Verifica se a string é uma URL válida.
   */
  public static boolean getBooUrlValida(String url) {

    boolean booResultado = true;

    try {

      if (Utils.getBooStrVazia(url)) {

        return false;
      }

      if (!url.startsWith("http://")) {

        url = "http://" + url;
      }

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

    return new Random().nextInt(intMaximo);
  }

  public static String getMd5(String str) {

    BigInteger objBigInteger;
    MessageDigest objMessageDigest;

    try {

      objMessageDigest = MessageDigest.getInstance("MD5");
      objBigInteger = new BigInteger(1, objMessageDigest.digest(str.getBytes()));

      return String.format("%0" + (objMessageDigest.digest(str.getBytes()).length << 1) + "X", objBigInteger);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(112), ex);
    }
    finally {
    }

    return null;
  }

  public static String getStrAleatoria(int intTamanho, EnmStrTipo enmStrTipo) {

    double dblIndex;
    int intQtd;
    String strConjunto;
    String strResultado;

    try {

      strConjunto = Utils.STR_VAZIA;

      switch (enmStrTipo) {
        case ALPHANUMERICO:
          strConjunto = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
          break;
        case NUMERICO:
          strConjunto = "1234567890";
          break;
        default:
          strConjunto = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
          break;
      }

      intQtd = strConjunto.length();
      strResultado = STR_VAZIA;

      for (int i = 0; i < intTamanho; i++) {

        dblIndex = Math.random() * intQtd;
        strResultado += strConjunto.charAt((int) dblIndex);
      }
      
      return strResultado;
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(111), ex);
    }
    finally {
    }

    return null;
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

  public static String getStrDataFormatada(Calendar dtt, EnmDataFormato enmDataFormato) {

    SimpleDateFormat objSimpleDateFormat = null;
    String strDataFormato;
    String strResultado;

    try {

      if (dtt == null) {

        return Utils.STR_VAZIA;
      }

      strDataFormato = Utils.enmDataFormatoToString(enmDataFormato);
      objSimpleDateFormat = new SimpleDateFormat(strDataFormato, LOCAL_BRASIL);

      strResultado = objSimpleDateFormat.format(dtt.getTime());

      if (Utils.getBooStrVazia(strResultado)) {

        return null;
      }

      strResultado = strResultado.replace("00:00 ", Utils.STR_VAZIA);

      return strResultado;
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return null;
  }

  public static String getStrFixa(int intNumero, int intQtd) {

    String str;

    try {

      str = String.valueOf(intNumero);

      if (str == null) {

        str = Utils.STR_VAZIA;
      }

      while (str.length() < intQtd) {

        str = "0" + str;
      }

      if (str.length() > intQtd) {

        str = str.substring(0, intQtd);
      }

      return str;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  public static String getStrFixa(String str, int intQuantidade) {

    try {

      if (str == null) {

        str = Utils.STR_VAZIA;
      }

      while (str.length() < intQuantidade) {

        str = str.concat(" ");
      }

      if (str.length() > intQuantidade) {

        str = str.substring(0, intQuantidade);
      }

      return str;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
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

  /**
   * Retorna o valor em uma lista no padrão "key=valor,key2=valor2;...".
   *
   * @param strLista
   * @param strKey
   * @return
   */
  public static String getStrValor(String strLista, String strKey) {

    String strKey2;
    String[] arrStrTermo;

    try {

      if (Utils.getBooStrVazia(strLista)) {

        return Utils.STR_VAZIA;
      }

      if (Utils.getBooStrVazia(strKey)) {

        return Utils.STR_VAZIA;
      }

      arrStrTermo = strLista.split(";");

      for (String strTermo : arrStrTermo) {

        if (Utils.getBooStrVazia(strTermo)) {

          continue;
        }

        strKey2 = strTermo.split("=")[0];

        if (!strKey.equals(strKey2)) {

          continue;
        }

        return strTermo.split("=")[1];
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return Utils.STR_VAZIA;
  }

  public static String getStrValorMonetario(double dblValor) {

    NumberFormat objNumberFormat;
    String strResultado;

    try {

      if (dblValor == 0) {

        return "R$ 0,00";
      }

      objNumberFormat = NumberFormat.getCurrencyInstance(LOCAL_BRASIL);

      strResultado = objNumberFormat.format(dblValor);
      strResultado = strResultado.replace("R$", "R$ ");
      strResultado = strResultado.replace("R$  ", "R$ ");

      return strResultado;
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return "R$ 0,00";
  }

  public static String getStrValorNumerico(double dblValor) {

    NumberFormat objNumberFormat;
    String strResultado;

    try {

      if (dblValor == 0) {

        return "0";
      }

      objNumberFormat = NumberFormat.getNumberInstance(LOCAL_BRASIL);

      strResultado = objNumberFormat.format(dblValor);
      strResultado = (!strResultado.endsWith(",0")) ? strResultado : Utils.STR_VAZIA;

      return strResultado;
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return "0";
  }

  public static String getStrValorPercentual(double dblValor) {

    String strResultado;

    try {

      if (dblValor == 0) {

        return "0,00 %";
      }

      strResultado = String.valueOf(dblValor);

      strResultado = strResultado.replace(".", ",");
      strResultado = strResultado.concat(" %");
      strResultado = strResultado.replace(",0 %", " %");

      return strResultado;
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return "0,00 %";
  }

  /**
   * "Pinga" um host e retorna true caso haja resposta deste.
   */
  public static boolean ping(String url) {

    boolean booResultado;
    HttpURLConnection objHttpURLConnection;

    try {

      if (Utils.getBooStrVazia(url)) {

        return false;
      }

      if (!url.startsWith("http://")) {

        url = "http://".concat(url);
      }

      objHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();

      objHttpURLConnection.setRequestMethod("HEAD");

      return (objHttpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK);
    }
    catch (Exception ex) {

      booResultado = false;
    }
    finally {
    }

    return booResultado;
  }

  public static String removerMascara(String str) {

    try {

      if (Utils.getBooStrVazia(str)) {

        return null;
      }

      return str.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", "");
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  public static String removerUltimaLetra(String str) {

    return Utils.removerUltimaLetra(str, 1);
  }

  public static String removerUltimaLetra(String str, int intQuantidadeCaracter) {

    try {

      if (Utils.getBooStrVazia(str)) {

        return str;
      }

      str = str.substring(0, str.length() - intQuantidadeCaracter);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return str;
  }

  public static String simplificar(String strComplexa) {

    String[] arrChrAcentos;
    String[] arrChrCaracteresEspeciais;
    String[] arrChrSemAcento;

    try {

      if (Utils.getBooStrVazia(strComplexa)) {

        return Utils.STR_VAZIA;
      }

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

  public static GregorianCalendar strToDtt(String strDtt) {

    GregorianCalendar dttResultado;
    SimpleDateFormat sdf;

    try {

      if (Utils.getBooStrVazia(strDtt)) {

        return null;
      }

      strDtt = strDtt.toLowerCase();

      strDtt = strDtt.replace("/", "-");
      strDtt = strDtt.replace("t", " ");

      dttResultado = new GregorianCalendar();
      sdf = new SimpleDateFormat(Utils.getStrDttFormato(strDtt));

      dttResultado.setTime(sdf.parse(strDtt));

      return dttResultado;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  public static double toDouble(String str) {

    try {

      if (Utils.getBooStrVazia(str)) {

        return 0;
      }

      return Double.valueOf(str.replace(",", "."));
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return 0;
  }

  private static String enmDataFormatoToString(EnmDataFormato enmDataFormato) {

    String strResultado = null;

    try {

      switch (enmDataFormato) {
        case DD:
          strResultado = "dd";
          break;
        case DD_MM:
          strResultado = "dd/MM";
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
        case HH_MM_DD_MM_YY:
          strResultado = "HH:mm dd/MM/yy";
          break;
        case HH_MM_DD_MM_YYYY:
          strResultado = "HH:mm dd/MM/yyyy";
          break;
        case HH_MM_SS_DD_MM_YYYY:
          strResultado = "HH:mm:ss dd/MM/yyyy";
          break;
        case YYYY:
          strResultado = "yyyy";
          break;
        case YYYY_MM_DD:
          strResultado = "yyyy-MM-dd";
          break;
        case YYYY_MM_DD_HH_MM_SS:
          strResultado = "yyyy-MM-dd HH:mm:ss";
          break;
        case MM:
          strResultado = "MM";
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

  private static String getStrDttFormato(String strDtt) {

    try {

      if (Utils.getBooStrVazia(strDtt)) {

        return null;
      }

      strDtt = strDtt.toLowerCase();
      strDtt = strDtt.replace("/", "-");
      strDtt = strDtt.replace("t", " ");

      if (strDtt.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d")) {

        return "dd-MM-yyyy HH:mm:ss.SSS";
      }

      if (strDtt.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d \\d\\d:\\d\\d:\\d\\d")) {

        return "dd-MM-yyyy HH:mm:ss";
      }

      if (strDtt.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d \\d\\d:\\d\\d")) {

        return "dd-MM-yyyy HH:mm";
      }

      if (strDtt.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d")) {

        return "dd-MM-yyyy";
      }

      if (strDtt.matches("\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d \\d\\d-\\d\\d-\\d\\d\\d\\d")) {

        return "HH:mm:ss.SSS dd-MM-yyyy";
      }

      if (strDtt.matches("\\d\\d:\\d\\d:\\d\\d \\d\\d-\\d\\d-\\d\\d\\d\\d")) {

        return "HH:mm:ss dd-MM-yyyy";
      }

      if (strDtt.matches("\\d\\d:\\d\\d \\d\\d-\\d\\d-\\d\\d\\d\\d")) {

        return "HH:mm dd-MM-yyyy";
      }

      if (strDtt.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d")) {

        return "dd-MM-yyyy";
      }

      if (strDtt.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d")) {

        return "yyyy-MM-dd HH:mm:ss.SSS";
      }

      if (strDtt.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d")) {

        return "yyyy-MM-dd HH:mm:ss";
      }

      if (strDtt.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d")) {

        return "yyyy-MM-dd HH:mm";
      }

      if (strDtt.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d")) {

        return "yyyy-MM-dd";
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }
}