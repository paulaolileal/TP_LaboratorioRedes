import com.google.gson.Gson;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.net.ServerSocket;
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Carta{
    String nipe;
    String valor;

    public Carta( ){nipe = ""; valor = ""; }

    public Carta( String nipe, String valor){
        this.nipe = nipe;
        this.valor = valor;
    }

    public String getNipe( ){
        return nipe;
    }

    public String getValor( ){
        return valor;
    }

    public void setValor( String nipe ){
        this.nipe = nipe;
    }

    public void setNipe( String valor ){
        this.valor = valor;
    }

    public static Carta compararCartas( Carta a, Carta b) {
        if( a.getValor().equals("") ){
            return b;
        }else if(a.getNipe().equals("Paus") && a.getValor().equals("4")){
            return a;
        }else if(b.getNipe().equals("Paus") && b.getValor().equals("4")){
            return b;
        }else if(a.getNipe().equals("Copas") && a.getValor().equals("7")){
            return a;
        }else if(b.getNipe().equals("Copas") && b.getValor().equals("7")){
            return b;
        }else if(a.getNipe().equals("Espada") && a.getValor().equals("A")){
            return a;
        }else if(b.getNipe().equals("Espada") && b.getValor().equals("A")){
            return b;
        }else if(a.getNipe().equals("Ouro") && a.getValor().equals("7")){
            return a;
        }else if(b.getNipe().equals("Ouro") && b.getValor().equals("7")){
            return b;
        }else if(a.getValor().equals("3")){
            return a;
        }else if(b.getValor().equals("3")){
            return b;
        }else if(a.getValor().equals("2")){
            return a;
        }else if(b.getValor().equals("2")){
            return b;
        }else if(a.getValor().equals("A")){
            return a;
        }else if(b.getValor().equals("A")){
            return b;
        }else if(a.getValor().equals("K")){
            return a;
        }else if(b.getValor().equals("K")){
            return b;
        }else if(a.getValor().equals("Q")){
            return a;
        }else if(b.getValor().equals("Q")){
            return b;
        }else if(a.getValor().equals("J")){
            return a;
        }else if(b.getValor().equals("J")){
            return b;
        }else if(a.getValor().equals("7")){
            return a;
        }else if(b.getValor().equals("7")){
            return b;
        }else if(a.getValor().equals("6")){
            return a;
        }else if(b.getValor().equals("6")){
            return b;
        }else if(a.getValor().equals("5")){
            return a;
        }else if(b.getValor().equals("5")){
            return b;
        }else if(a.getValor().equals("4")){
            return a;
        }else if(b.getValor().equals("4")){
            return b;
        }
        return (new Carta("",""));
    }
}

class Baralho{

    private static List<Carta> baralho = new ArrayList<Carta>( );

    private static void montarBaralho( ){
        baralho.add(new Carta("Ouro", "A"));
        baralho.add(new Carta("Ouro", "2"));
        baralho.add(new Carta("Ouro", "3"));
        baralho.add(new Carta("Ouro", "4"));
        baralho.add(new Carta("Ouro", "5"));
        baralho.add(new Carta("Ouro", "6"));
        baralho.add(new Carta("Ouro", "7"));
        baralho.add(new Carta("Ouro", "J"));
        baralho.add(new Carta("Ouro", "Q"));
        baralho.add(new Carta("Ouro", "K"));

        baralho.add(new Carta("Copas", "A"));
        baralho.add(new Carta("Copas", "2"));
        baralho.add(new Carta("Copas", "3"));
        baralho.add(new Carta("Copas", "4"));
        baralho.add(new Carta("Copas", "5"));
        baralho.add(new Carta("Copas", "6"));
        baralho.add(new Carta("Copas", "7"));
        baralho.add(new Carta("Copas", "J"));
        baralho.add(new Carta("Copas", "Q"));
        baralho.add(new Carta("Copas", "K"));

        baralho.add(new Carta("Paus", "A"));
        baralho.add(new Carta("Paus", "2"));
        baralho.add(new Carta("Paus", "3"));
        baralho.add(new Carta("Paus", "4"));
        baralho.add(new Carta("Paus", "5"));
        baralho.add(new Carta("Paus", "6"));
        baralho.add(new Carta("Paus", "7"));
        baralho.add(new Carta("Paus", "J"));
        baralho.add(new Carta("Paus", "Q"));
        baralho.add(new Carta("Paus", "K"));

        baralho.add(new Carta("Espada", "A"));
        baralho.add(new Carta("Espada", "2"));
        baralho.add(new Carta("Espada", "3"));
        baralho.add(new Carta("Espada", "4"));
        baralho.add(new Carta("Espada", "5"));
        baralho.add(new Carta("Espada", "6"));
        baralho.add(new Carta("Espada", "7"));
        baralho.add(new Carta("Espada", "J"));
        baralho.add(new Carta("Espada", "Q"));
        baralho.add(new Carta("Espada", "K"));
    }

    public static void embaralhar( ){
        if(baralho.size() != 40){
            montarBaralho();
        }
        Collections.shuffle(baralho);
    }

    public static List<Carta> sacarCartas( ){
        List<Carta> maoDoJogador = new ArrayList<Carta>( );
        for(int i =0; i<3; i++){
            maoDoJogador.add(baralho.get(i));
            baralho.remove(i);
        }
        return maoDoJogador;
    }
}

class Jogador implements Runnable{
    String nome;
    Socket socket;
    String time;
    protected BlockingQueue<Integer> inf = null;

    ObjectInputStream sServIn;
    ObjectOutputStream sSerOut;

    public Jogador(Socket socket, String time, BlockingQueue<Integer> inf) throws IOException{
        this.socket = socket;
        this.time = time;
        this.inf = inf;
        sServIn = new ObjectInputStream(socket.getInputStream());
        sSerOut = new ObjectOutputStream(socket.getOutputStream());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void enviarCartaParaJogador(String msg) {
        try {
            sSerOut.writeObject(msg);
            sSerOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMsgParaJogador(String msg) {
        try {
            sSerOut.writeObject(msg);
            sSerOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receberCartaDoJogador( ){
        String msg = "";
        try {
            msg = sServIn.readObject().toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public void run() {
        Object msgIn = null;
        try {
            msgIn = sServIn.readObject();
            setNome(msgIn.toString());


            while( true ){

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


public class ServidorTCP{

    private static List<Jogador> jogadores;

    private static int PONTOS = 0;

    public static void espalharMensagemParaTodosJogadores(String msg){
        for ( Jogador j: jogadores ) {
            j.enviarMsgParaJogador(msg);
        }
    }

    //M�TODO PRINCIPAL DA CLASSE
    public static void main (String args[])	{
        jogadores = new ArrayList<Jogador>( );
        int PortaServidor = 7000;
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        try{

            System.out.println(" -S- Aguardando conexao (P:"+PortaServidor+")...");

            // ABRE O SERVIDOR
            ServerSocket socktServ = new ServerSocket(PortaServidor);

            // CONECTA OS 4 JOGADORES
            for(int i = 0; i<4; i++){
                Socket conSer = socktServ.accept();
                System.out.println(" -S- Conectado ao cliente ->" + conSer.toString());

                Jogador jogador = new Jogador(conSer, i > 1 ? "Time A" : "Time B", queue );
                new Thread( jogador ).start( );

                jogadores.add(jogador);
            }

            Baralho.embaralhar();

            Gson gson = new Gson();
            int vez = 0;
            Carta maiorCarta = new Carta();
            String timeDaVez = "";
            int valorDaRodada = 2;
            int pontosTimeA = 0;
            int pontosTimeB = 0;
            int truco = 2; // 2 valor normal, 4

            String[] rodada = new String[3];

            // INICIA O JOGO
            while(PONTOS != 12){

                for( Jogador j : jogadores){
                    List<Carta> cartas = Baralho.sacarCartas();
                    for (Carta c: cartas) {
                        j.enviarCartaParaJogador(gson.toJson(c));
                    }
                }

                for(int i = 0; i<3; i++) {
                    jogadores.get(vez).enviarMsgParaJogador("Escolha uma carta!");
                    Carta carta = gson.fromJson(jogadores.get(vez).receberCartaDoJogador(), Carta.class);
                    espalharMensagemParaTodosJogadores("Jogador: " + jogadores.get(vez).getNome() + "jogou " + carta.getValor() + "de " + carta.getNipe());
                    maiorCarta = Carta.compararCartas(maiorCarta, carta);
                    timeDaVez = jogadores.get(vez).getTime();
                    rodada[i] = timeDaVez;
                    if( i == 0){
                        espalharMensagemParaTodosJogadores("Ponto do " + timeDaVez + " com a carta " + maiorCarta.getValor() + " de " + maiorCarta.getNipe());
                    }else if (i == 1){
                        if( rodada[0].equals(rodada[1])) {
                            espalharMensagemParaTodosJogadores("Os pontos dessa rodada vão para o " + timeDaVez);
                            break;
                        }else{
                            espalharMensagemParaTodosJogadores("Ponto do " + timeDaVez + " com a carta " + maiorCarta.getValor() + " de " + maiorCarta.getNipe());
                        }
                    }else if( i == 2){
                            timeDaVez = rodada[0];
                        espalharMensagemParaTodosJogadores("Os pontos dessa rodada vão para o "+timeDaVez);

                    }
                }
                if (timeDaVez.equals("Time A")) {
                    pontosTimeA = pontosTimeA + valorDaRodada;
                }else{
                    pontosTimeB = pontosTimeB + valorDaRodada;
                }
                valorDaRodada = 2;
                maiorCarta = new Carta( );
                Baralho.embaralhar();
                vez++;
                vez = vez % 4;
            }

            //FINALIZA A CONEXÃO
            for (Jogador j : jogadores) {
                j.socket.close();
            }
            socktServ.close();
            System.out.println(" -S- Conexao finalizada...");

        }
        catch(Exception e) //SE OCORRER ALGUMA EXCESS�O, ENT�O DEVE SER TRATADA (AMIGAVELMENTE)
        {
            System.out.println(" -S- O seguinte problema ocorreu : \n" + e.toString());
        }
    }
}