
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ClaseFiltroUrl {
    
    public static Map<String, Integer>rutaDe3Paginas(String archivo, String separador, int fondo){
        Map<String, Integer> cuentaPaginaVisitada = new HashMap<String, Integer>();
        if(archivo == null || "".equals(archivo)){
            return cuentaPaginaVisitada;
        }
        try{
            Archivo a = new Archivo(archivo);
            LectorArchivo la = new LectorArchivo(a);
            LectorBuffer lb = new LectorBuffer(la);
            
            Map<String, List<String>> usuarioUrls = new HashMap<String, List<String>>();
            String LineaActual = "";
            while((LineaActual = lb.readLine())!= null){
                String[] lineArr = LineaActual.split(separador);
                if(lineArr == null || lineArr.length != (fondo - 1)){
                    continue;
                }
                String usuario = lineArr[0];
                String pagina = lineArr[1];
                List<String> urlLinkedList = null;
                if(usuarioUrls.get(usuario) == null){
                    urlLinkedList = new LinkedList<String>();
                }
                else{
                    urlLinkedList = usuarioUrls.get(usuario);
                    String paginas = "";
                    if(urlLinkedList.size()== (fondo - 1)){
                        paginas = urlLinkedList.get(0).trim()+ separador + urlLinkedList.get(1).trim() + separador + pagina;  
                    }
                    else if (urlLinkedList.size() > (fondo - 1)){
                        urlLinkedList.remove(0);
                        paginas = urlLinkedList.get(0).trim()+ separador + urlLinkedList.get(1).trim() + separador + pagina;
                    }
                    if(!"".equals(paginas) && null != paginas){
                        Integer c = (cuentaPaginaVisitada.get(paginas) == null ? 0 : cuentaPaginaVisitada.get(paginas)) + 1;
                        cuentaPaginaVisitada.put(paginas, c);
                    }
                }
                urlLinkedList.add(pagina);
                System.out.println("usuario:"+ usuario + ", urlLinkedList:" + urlLinkedList);
                usuarioUrls.put(usuario, urlLinkedList);
            }
            lb.Close();
            la.Close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return cuentaPaginaVisitada;
    }
    
    public static void main(String[] args){
        String archivo = "/home/ieee754/Desktop/test-access.log";
        String separador = ",";
        Map<String, Integer> cuentaPaginaVisitada = rutaDe3Paginas(archivo, separador, 3);
        System.out.println(cuentaPaginaVisitada.size());
        Map<String, Integer> resultado = MapUtil.sortByValueDescendOrder(cuentaPaginaVisitada);
        System.out.println(resultado);
    }
    
}
