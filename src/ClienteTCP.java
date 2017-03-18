import com.google.gson.Gson;

import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteTCP {

    private static Socket socket = null;
    static List<Carta> maoAtual = new ArrayList<>();

    static ObjectInputStream sServIn;
    static ObjectOutputStream sSerOut;

    // Envia mensagem para o servidor
    public static void enviarMsg( String msg ){
        try {
            if(sSerOut == null){
                sSerOut = new ObjectOutputStream(socket.getOutputStream());
            }
            sSerOut.writeObject(msg);
            sSerOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Recebe mensagem do servidor
    public static String receberMsg( ){
        String strMsg = "";
        try {
            if(sServIn == null){
                sServIn = new ObjectInputStream(socket.getInputStream());
            }
            strMsg = sServIn.readObject().toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return strMsg;
    }

    // Conecta ao servidor
    public static void conectar( ){
        boolean sucesso = false;
        while(!sucesso) {
            try {
                // Endereço do servidor
                String IPServidor = "localhost";
                int PortaServidor = 7000;

                // Iniciar conexão
                socket = new Socket(IPServidor, PortaServidor);
                sucesso = true;
                System.out.println(" -C- Conectando ao servidor ->" + IPServidor + ":" + PortaServidor);
            } catch (IOException e) {
                System.out.println("Servidor ainda não esta aberto, tendando novamente...");
            }
        }
    }

    public static void main(String args[]) throws IOException {
        Gson gson = new Gson();
        Scanner ler = new Scanner(System.in);
        System.out.println("O jogo já vai começar, entre com o seu nome: ");
        String nome = ler.next();
        conectar();
        enviarMsg(nome);
        loopPrincipal:
        while(true) {
            String ms = receberMsg();
            Dados d = gson.fromJson(ms, Dados.class);
            switch (d.getComando()) {
                case 0: // Receber carta
                    maoAtual.add(gson.fromJson(d.getMsg(), Carta.class));
                    break;
                case 1: // Receber uma mensagem qualquer
                    System.out.println(d.getMsg());
                    break;
                case 2: // Enviar carta para o servidor
                    System.out.println("Sua mão é: ");
                    int i = 0;
                    for (Carta c : maoAtual) {
                        System.out.print(" | [" + i + "] - " + c.getValor() + " de " + c.getNipe());
                        i++;
                    }
                    System.out.println("\n-- Escolha uma carta!... ou [3] Peça Truco!");
                    int opcao = ler.nextInt( );
                    while(opcao < 0 || (opcao > maoAtual.size()-1 && opcao < 3 )  || opcao > 3){
                        System.out.println("-- Opção invalida! [0] ou [1] ou [2] ou [3]");
                        opcao = ler.nextInt();
                    }
                    if(opcao == 3) { // Verifica se o jogador escolheu truco
                        enviarMsg(gson.toJson(new Carta("", "TRUCO")));
                        System.out.println("Agora escolha uma carta!");
                        opcao = ler.nextInt();
                        while(opcao < 0 || opcao > maoAtual.size()-1) {
                            System.out.println("Opção invalida! [0] ou [1] ou [2]");
                            opcao = ler.nextInt();
                        }
                    }
                    enviarMsg(gson.toJson(maoAtual.get(opcao)));
                    maoAtual.remove(opcao);
                    break;
                case 3: // Esvaziar mão
                    maoAtual = new ArrayList<>();
                    break;
                case 4: // Aceitar truco
                    System.out.println("\nVai aceitar o truco? [0] - Não | [1] - Sim");
                    int op = ler.nextInt( );
                    while(op != 0 && op != 1) {
                        System.out.println("Opção invalida! [0] ou [1]");
                        op = ler.nextInt();
                    }
                    enviarMsg(gson.toJson(op));
                    System.out.println("Aguarde seu parceiro decidir...");
                    break;
                case 5: // Desconectar do servidor
                    System.out.println(d.getMsg());
                    System.out.println("Desconectando...");
                    break loopPrincipal;
            }
        }
    }
}
