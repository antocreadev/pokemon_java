package cli;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import lib.Pair;
import server.ObjectStream;
import server.Server;
import server.Server.Commands;

public class Client implements Runnable {

  private Trainer trainer;
  private Pokemon currentPokemon;
  private Socket client;
  private ObjectOutputStream out;
  private ObjectInputStream in;

  public Client(Trainer trainer) {
    try {
      this.trainer = trainer;
      client = new Socket(Main.SERVER_IP, Server.PORT);
      out = new ObjectOutputStream(client.getOutputStream());
      in = new ObjectInputStream(client.getInputStream());
    } catch (Exception e) {
      Main.log("Error: " + e.getMessage());
      disconnect();
    }
  }

  @Override
  public void run() {
    try {
      while (true) {
        // Send trainer information to the server
        sendToServer(new ObjectStream(Commands.INIT_TRAINER, trainer));

        ObjectStream object;
        while ((object = (ObjectStream) in.readObject()) != null) {
          // Main.log(object, "MSG");
          Commands cmd = object.getCmd();
          Object data = object.getO();

          if (cmd.equals(Commands.INIT_TRAINER)) {
            if ((boolean) data) {
              System.out.println("Connected to server!");
              currentPokemon = trainer.choosePokemon();

              sendToServer(new ObjectStream(Commands.IN_QUEUE, currentPokemon));
            }
          }

          if (cmd.equals(Commands.IN_QUEUE)) {
            int queueSize = (int) data;
            System.out.println("Waiting for opponent ... (" + queueSize + "/2)");
          }

          if (cmd.equals(Commands.BATTLE_STARTING)) {
            Main.log("Opponent found!", "SERVER");
            displayMenuBattle();
          }

          if (cmd.equals(Commands.BATTLE_END)) {
            if ((boolean) data) {
              System.out.println("You won!");
              if (currentPokemon != null) {
                RareCandy rareCandyRandomly = trainer.getRareCandyRandomly(100, currentPokemon.getType());
                if (rareCandyRandomly != null) {
                  System.out.println("You got a rare candy of type " + rareCandyRandomly.getType() + "!");
                }
              }
            } else {
              System.out.println("You lost!");
            }
            disconnect();
          }

          if (cmd.equals(Commands.SET_ACTION)) {
            if ((boolean) data) {
              System.out.println("Waiting for opponent's action ...");
              sendToServer(new ObjectStream(Commands.EOT, null));
            }
          }

          if (cmd.equals(Commands.ATTACK)) {
            @SuppressWarnings("unchecked")
            Pair<Integer, Integer> newPokemonPair = (Pair<Integer, Integer>) data;
            Pokemon localPokemon = trainer.getPokemons().get(newPokemonPair.getFirst().intValue());
            int damage_taken = newPokemonPair.getSecond().intValue();
            // Update your pokemon's hp
            localPokemon.setHp(localPokemon.getHp() - damage_taken);
            System.out.println("You took " + damage_taken + " damage!");
            System.out.println(localPokemon.getName() + " has " + localPokemon.getHp() + " hp left!");
            // if your pokemon fainted, release it
            if (localPokemon.getHp() <= 0) {
              System.out.println(localPokemon.getName() + " fainted!");
              trainer.getPokemons().remove(localPokemon);
            }

            sendToServer(new ObjectStream(Commands.NEXT_TURN, true));

          }

          if (cmd.equals(Commands.NEXT_TURN)) {
            if ((boolean) data) {
              displayMenuBattle();
            }
          }

          System.out.println();
        }
      }
    } catch (EOFException e) {
      Main.log("Server closed", "SERVER");
    } catch (IOException e) {
      Main.log("Error: " + e.getMessage(), "SERVER");
      // e.printStackTrace();
      disconnect();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void displayMenuBattle() {
    System.out.println("Choose your action :");
    ObjectStream battleAction = trainer.getBattleAction();
    sendToServer(new ObjectStream(Commands.SET_ACTION, battleAction));
  }

  public void disconnect() {
    try {
      Game.saveGame(trainer, true);

      if (!client.isClosed()) {
        client.close();
      }
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
    } catch (Exception e) {
    }
    throw new RuntimeException("Disconnected from server!");
  }

  private void sendToServer(ObjectStream object) {
    try {
      out.writeObject(object);
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
