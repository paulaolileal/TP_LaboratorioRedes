import com.google.gson.Gson;

import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteTCP {

    private static Socket socket = null;
    static List<Carta> maoAtual = new ArrayList<>();

    public static void enviarMsg( String msg ){
        try {
            ObjectOutputStream sCliOut = new ObjectOutputStream(socket.getOutputStream());
            sCliOut.writeObject(msg);
            sCliOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String receberMsg( ){
        String strMsg = "";
        try {
            ObjectInputStream sCliIn = new ObjectInputStream(socket.getInputStream());
            System.out.println(" -C- Recebendo mensagem...");
            strMsg = sCliIn.readObject().toString(); //ESPERA (BLOQUEADO) POR UM PACOTE
        }catch (Exception e){
            e.printStackTrace();
        }
        return strMsg;
    }

    public static void conectar( ){
        try {
            //ENDERE�O DO SERVIDOR
            String IPServidor = /*"10.2.11.11";"127.0.0.1";*/"localhost";
            int PortaServidor = 7000;

            //ESTABELECE CONEX�O COM SERVIDOR
            System.out.println(" -C- Conectando ao servidor ->" + IPServidor + ":" +PortaServidor);
            socket = new Socket (IPServidor,PortaServidor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //M�TODO PRINCIPAL DA CLASSE
    public static void main(String args[]){
        Gson gson = new Gson();
        Scanner ler = new Scanner(System.in);
        conectar();
        enviarMsg("Nome do Jogador");
        while(true){
            for(int i = 0; i<3; i++) {
                maoAtual.add(gson.fromJson(receberMsg(), Carta.class));
            }
            System.out.println("Suas cartas são: ");
            for ( Carta c : maoAtual){
                int i = 0;
                System.out.print("["+i+"] - " + c.getValor() + " de " + c.getNipe());
                i++;
            }
            enviarMsg(gson.toJson(maoAtual.get(ler.nextInt())));
            receberMsg();
            receberMsg();
            receberMsg();


        }
    }
}		