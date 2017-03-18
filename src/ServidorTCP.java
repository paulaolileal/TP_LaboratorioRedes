import com.google.gson.Gson;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.net.ServerSocket;
import java.io.*;

class Carta{
    String nipe;
    String valor;

    public Carta( ){
        nipe = ""; valor = "";
    }

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

    // Compara qual carta é maior
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

    // Inicia o baralho
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

    // Embaralha as cartas
    public static void embaralhar( ){
        if(baralho.size() != 40){
            montarBaralho();
        }
        Collections.shuffle(baralho);
    }

    // Saca 3 cartas para o jogador
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

    ObjectInputStream sServIn;
    ObjectOutputStream sSerOut;

    public Jogador(Socket socket, String time) throws IOException{
        this.socket = socket;
        this.time = time;
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

    public void enviarMsg(String msg) {
        try {
            sSerOut.writeObject(msg);
            sSerOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String receberMsg( ){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Dados{

    int comando;
    String msg;

    public Dados( int cmd, String msg ){
        this.comando = cmd;
        this.msg = msg;
    }

    public int getComando() {
        return comando;
    }

    public void setComando(int comando) {
        this.comando = comando;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}

public class ServidorTCP{

    private static List<Jogador> jogadores;

    // Envia mensagem para todos os jogadores
    public static void espalharMsg(String msg){
        Gson gson = new Gson();
        for ( Jogador j: jogadores ) {
            j.enviarMsg( gson.toJson(new Dados(1, msg)));
        }
    }

    public static void main (String args[])	{
        jogadores = new ArrayList<Jogador>( );
        Gson gson = new Gson();
        int PortaServidor = 7000;

        try{
            System.out.println(" Esperando por jogadores... (P:"+PortaServidor+")...");

            // ABRE O SERVIDOR
            ServerSocket socktServ = new ServerSocket(PortaServidor);

            // CONECTA OS 4 JOGADORES
            for(int i = 0; i<4; i++){
                Socket conSer = socktServ.accept();
                System.out.println(" -S- Conectado ao cliente ->" + conSer.toString());
                Jogador jogador = new Jogador(conSer, i % 2 == 0 ? "Time A" : "Time B" );
                new Thread( jogador ).start( );
                jogador.enviarMsg(gson.toJson(new Dados(1, "Você está no "+jogador.getTime())));
                jogadores.add(jogador);
            }

            Baralho.embaralhar();


            Carta maiorCarta = new Carta();
            int valorDaRodada = 2;
            int pontosTimeA = 0;
            int pontosTimeB = 0;

            String[] rodada = new String[3];

            String vencedor = "";

            boolean estaTrucado = false;

            // INICIA O JOGO
            while(pontosTimeA < 12 && pontosTimeB < 12){
                // Distribui as 3 cartas para cada jogador
                for( Jogador j : jogadores){
                    j.enviarMsg(gson.toJson(new Dados(3, gson.toJson(new Carta( )))));
                    List<Carta> cartas = Baralho.sacarCartas();
                    System.out.println("Enviando cartas para o jogador: "+j.getNome());
                    for (Carta c: cartas) {
                        Dados h = new Dados(0, gson.toJson(c));
                        j.enviarMsg(gson.toJson(h));
                    }
                }
                espalharMsg("TIME A = "+pontosTimeA + " | TIME B = "+pontosTimeB);

                // Inicia a rodada
                loopPrincipal:
                for(int i = 0; i<3; i++) {
                    // Inicia o turno de cada jogador
                    for(int vez = 0; vez<4; vez++) {
                        Jogador j = jogadores.get(vez);

                        // Pede o jogador para escolher uma carta
                        System.out.println("Solicitando carta do jogador: " + j.getNome());
                        j.enviarMsg(gson.toJson(new Dados(2, gson.toJson(new Carta()))));

                        // Recebe a carta do jogador
                        Carta carta = gson.fromJson(j.receberMsg(), Carta.class);

                        // Verifica se propôs truco
                        if(carta.getValor().equals("TRUCO")){
                            carta = gson.fromJson(j.receberMsg(), Carta.class);
                            if(!estaTrucado) {
                                estaTrucado = true;
                                espalharMsg("JOGADOR: " + j.getNome() + " do " + j.getTime() + " PROPÔS TRUCO");
                                int acc = 0;

                                // Recebe dos jogadores do time oposto se vai aceitar o truco ou não
                                for (Jogador jj : jogadores) {
                                    if (!jj.getTime().equals(j.getTime())) {
                                        jj.enviarMsg(gson.toJson(new Dados(4, gson.toJson(new Carta()))));
                                        acc = acc + Integer.parseInt(jj.receberMsg());
                                    }
                                }

                                // Se os dois não aceitarem é encerrado o turno e quem trucou ganha
                                if (acc != 2) {
                                    vencedor = j.getTime();
                                    espalharMsg("OS PONTOS DESSA RODADA VÃO PARA: " + vencedor);
                                    break loopPrincipal;
                                } else { // Se não, aumenta os pontos em 2
                                    valorDaRodada = valorDaRodada + 2;
                                    espalharMsg("A RODADA AGORA VALE " + valorDaRodada);
                                }
                            }else{
                                j.enviarMsg(gson.toJson(new Dados(1, "-- O Jogo já está trucado! --")));
                            }
                        }
                        System.out.println("Jogador: " + j.getNome() + " jogou " + carta.getValor() + " de " + carta.getNipe());

                        // Mostra ela para todos os jogadores
                        espalharMsg("JOGADOR: " + j.getNome() + " JOGOU " + carta.getValor() + " DE " + carta.getNipe());

                        // Compara com a carta da rodada atual
                        maiorCarta = Carta.compararCartas(maiorCarta, carta);
                        if(maiorCarta.getValor().equals(carta.getValor()) && maiorCarta.getNipe().equals(carta.getNipe())) {
                            rodada[i] = j.getTime();
                        }
                    }
                    // Ponto para o primeiro turno
                    if( i == 0){
                        espalharMsg("PONTO DO " + rodada[0] + " COM A CARTA " + maiorCarta.getValor() + " DE " + maiorCarta.getNipe());
                    }else if (i == 1){
                        if( rodada[0].equals(rodada[1])) { // Vencedor do turno se ganhar a 1ª e a 2ª
                            espalharMsg("OS PONTOS DESSA RODADA VÃO PARA " + rodada[1]);
                            vencedor = rodada[1];
                            break loopPrincipal;
                        }else{ // Ponto para o segundo turno
                            espalharMsg("PONTO DO " + rodada[1] + " COM A CARTA " + maiorCarta.getValor() + " DE " + maiorCarta.getNipe());
                        }
                    }else if( i == 2){ // Vencedor se ganhar a 1ª e o 3ª ou 2ª e o 3ª
                        if(rodada[0].equals(rodada[2]) || rodada[1].equals(rodada[2])){
                            vencedor = rodada[2];
                        }
                        espalharMsg("OS PONTOS DESSA RODADA VÃO PARA "+rodada[2]);
                    }

                    // Reseta a maior carta
                    maiorCarta = new Carta( );
                }

                // Reseta o truco
                estaTrucado = false;

                // Soma os pontos ao placar
                if (vencedor.equals("Time A")) {
                    pontosTimeA = pontosTimeA + valorDaRodada;
                }else{
                    pontosTimeB = pontosTimeB + valorDaRodada;
                }

                // Valor da rodada se alguem propos Truco
                valorDaRodada = 2;

                // Re-embaralha
                Baralho.embaralhar();

            }

            // Encerra a conexão dos jogadores
            for (Jogador j : jogadores) {
                j.enviarMsg(gson.toJson(new Dados(5, "Obrigado por jogar!\nO vencedor foi "+ (pontosTimeA > pontosTimeB ? "Time A" : "Time B") )));
                j.socket.close();
            }

            // Fecha o servidor
            socktServ.close();
            System.out.println("Conexao finalizada...");

        }
        catch(Exception e) // Caso aconteça algum erro
        {
            System.out.println(" -S- O seguinte problema ocorreu : \n" + e.toString());
        }
    }
}