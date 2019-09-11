/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metodosnumericos;



import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
/**
*
* @author Alejandro Morales
*/
public class funcion {
private String  operacion;
private String   resultadoConversion;
private String   resultadoOperacion;
private int  indiceIni;
private int  indiceFin;
private String  valor;

public funcion() {
}
public String  evaluar( String  operacion, String  valor){
  this.operacion=operacion;
  this.valor=valor;
  this.resultadoConversion=analizaCadena(this.operacion);
  this.resultadoConversion=reemplazaOperacionJS(this.resultadoConversion);
  return this.resultadoOperacion=calculo(this.resultadoConversion);
}

private String  analizaCadena(String  cadena){
  cadena=cadena.replaceAll("x", this.valor);
  cadena=quitarEspacios(cadena);
  cadena="-"+cadena;
  char[] vectorCadena=cadena.toCharArray();
  if(cadena.contains("^")){
  cadena=reemplazaPotencia(vectorCadena, cadena);
  }
  vectorCadena=cadena.toCharArray();
 if(cadena.contains("cos")){
   cadena=reemplazaTrigonometrica(vectorCadena, cadena, ".co",'c');
  }
  vectorCadena=cadena.toCharArray();
 if(cadena.contains("sen")){
   cadena=reemplazaTrigonometrica(vectorCadena, cadena, ".si",'s');
  }
  vectorCadena=cadena.toCharArray();
 if(cadena.contains("tan")){
   cadena=reemplazaTrigonometrica(vectorCadena, cadena, ".ta",'t');
  }
 if (cadena.contains("log")){
     cadena=reemplazaTrigonometrica(vectorCadena, cadena, ".lo",'l');
 }

  return cadena;
}
private String  reemplazaParIzq(char[] cadena,int indice){
  ArrayList <Character > lista1=new ArrayList <Character >();
  ArrayList <Character > lista2=new ArrayList <Character >();
  String  res="";
  int i;
  for ( i = indice-1; i >=0; i--) {
    if(cadena[i]==')'){
      lista1.add(cadena[i]);
    }
    else
    {
     if(cadena[i]=='('){
      lista2.add(cadena[i]);
    }
    }
    res+=cadena[i];
    if((lista1.size()==lista2.size())&&(i!=(indice-1))){
      this.indiceIni=i;
      return invertir(res);
    }
  }
  return null;
}
  private String  reemplazaParDer(char[] cadena,int indice ){
  ArrayList <Character > lista1=new ArrayList <Character >();
  ArrayList <Character > lista2=new ArrayList <Character >();
  String  res="";
  int i;
  for ( i = indice+1; i <cadena.length ; i++) {
    if(cadena[i]=='('){
      lista1.add(cadena[i]);
    }
    else
    {
     if(cadena[i]==')'){
      lista2.add(cadena[i]);
    }
    }
    res+=cadena[i];
    if((lista1.size()==lista2.size())&&(i!=(indice+1))){
      this.indiceFin=i+1;
      return res;
    }
  }
  return null;
}
private String  reemplazaNumIzq(char[] cadena,int indice){
  //Pattern  exp= Pattern .compile("\\\\d|\\\\.");
  //Matcher  expReg;
  String  resultadoBase ="";
  String  res = "";
  for (int i = indice-1; i >=1; i--) {
    res="";
    res+=cadena[i];
          //expReg=exp.matcher(res);
    //  if (expReg.find()){
        resultadoBase+=cadena[i];
        this.indiceIni=i;
    //    }
      //  else
        //  {
        //  break;
        // }
  }
  return invertir(resultadoBase);
}
private String  reemplazaNumDer(char[] cadena,int indice){
  //Pattern  exp= Pattern .compile("\\\\d|\\\\.");
 // Matcher  expReg;
  String  resultadoBase ="";
  String  res = "";
  for (int i = indice+1; i<cadena.length; i++) {
    res="";
    res+=cadena[i];
        //  expReg=exp.matcher(res);
    //  if (expReg.find()){
        resultadoBase+=cadena[i];
      //  this.indiceFin=i+1;
     //   }
     //   else
      //    {
      //    break;
      //   }
  }
  return resultadoBase;
}
private String  invertir(String  cadena){
  char[] vector=cadena.toCharArray();
  String  res="";
  for (int i = vector.length-1; i >=0; i--) {
          res+=vector[i];
  }
  return res;
}
private String  reemplazaPotencia(char[] vectorCadena,String  cadena){
      String  resIzq="",resDer="";
for (int indice = 0; indice < vectorCadena.length; indice++) {
    if (vectorCadena[indice]=='^') {
      if (vectorCadena[indice - 1] == ')') {
        resIzq =reemplazaParIzq(vectorCadena, indice);
        }
      else{
        resIzq =reemplazaNumIzq(vectorCadena, indice);
          }
      if (vectorCadena[indice + 1] == '(') {
        resDer =reemplazaParDer(vectorCadena, indice);
        }
          else{
        resDer =reemplazaNumDer(vectorCadena, indice);
          }

         vectorCadena=(cadena.substring(0,this.indiceIni)+".po("+resIzq+","+resDer+")").toCharArray();
                // +resIzq+","+resDer+")" +(cadena.substring(this.indiceFin,cadena.length()))).toCharArray();
         cadena=(cadena.substring(0,this.indiceIni)+".po("+resIzq+","+resDer+")");
                // +resIzq+","+resDer+")"+(cadena.substring(this.indiceFin,cadena.length())) );
         indice=0;
    }
  }
      return cadena;
}
private String  reemplazaTrigonometrica(char[] vectorCadena,String  cadena,String  operacion,char caracter){
  String  resDer="";
  for (int indice = 0; indice < vectorCadena.length; indice++) {
    if ((vectorCadena[indice]==caracter)&&((vectorCadena)[indice-1]!='.')&&(indice!=0)) {
      if (vectorCadena[indice + 3] == '(') {
        resDer =reemplazaParDer(vectorCadena, indice+2);
        }
         else{
        resDer =reemplazaNumDer(vectorCadena, indice+2);
          }

         vectorCadena=(cadena.substring(0,indice)+operacion+"("
                 +resDer+")"+(cadena.substring(this.indiceFin,cadena.length())) ).toCharArray();
         cadena=(cadena.substring(0,indice)+operacion+"("
                 +resDer+")"+(cadena.substring(this.indiceFin,cadena.length())) );
         indice=0;
    }
  }
  return  cadena;
}
public String  getResultadoConversion() {
  return this.resultadoConversion;
}

public String  getResultadoOperacion() {
  return resultadoOperacion;
}

private String   quitarEspacios(String  sTexto){
  String  sCadenaSinBlancos="";
  for (int x=0; x < sTexto.length(); x++) {
if (sTexto.charAt(x) != ' ')
  sCadenaSinBlancos += sTexto.charAt(x);
  }
  return sCadenaSinBlancos;
}
private String  reemplazaOperacionJS(String  operacion){
  this.resultadoConversion=operacion.replaceAll(".po","Math.pow");
  this.resultadoConversion=this.resultadoConversion.replaceAll(".co","Math.cos");
  this.resultadoConversion=this.resultadoConversion.replaceAll(".si","Math.sin");
  this.resultadoConversion=this.resultadoConversion.replaceAll(".ta","Math.tan");
  this.resultadoConversion=this.resultadoConversion.replaceAll(".lo","Math.log");
  return this.resultadoConversion.substring(1, this.resultadoConversion.length());
}

private String  calculo(String  cadena) {
  ScriptEngineManager script = new ScriptEngineManager();
      ScriptEngine js = script.getEngineByName("JavaScript");
      try{
     return js.eval(cadena).toString();
      }catch(Exception  e){
        return e.toString();
      }
}
}
 