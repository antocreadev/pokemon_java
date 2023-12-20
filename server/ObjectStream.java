package server;

import java.io.Serializable;

import server.Server.Commands;

public class ObjectStream implements Serializable {
  private Commands cmd;
  private Object o;

  public ObjectStream(Commands cmd, Object o) {
    this.cmd = cmd;
    this.o = o;
  }

  @Override
  public String toString() {
    if (cmd == null)
      return "ObjectStream [o=" + o + "]";
    return "ObjectStream [cmd=" + cmd.getCmd() + ", o=" + o + "]";
  }

  public Commands getCmd() {
    return cmd != null ? cmd : null;
  }

  public Object getO() {
    return o;
  }
}
