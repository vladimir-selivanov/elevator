package com.buffalo.model;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class CommandCodec implements MessageCodec<Command, Command> {
    @Override
    public void encodeToWire(Buffer buffer, Command command) {
        JsonObject jsonToEncode = new JsonObject();
        jsonToEncode.put("from", command.getFrom());
        jsonToEncode.put("to", command.getTo());

        String jsonToStr = jsonToEncode.encode();

        int length = jsonToStr.getBytes().length;

        buffer.appendInt(length);
        buffer.appendString(jsonToStr);
    }

    @Override
    public Command decodeFromWire(int pos, Buffer buffer) {
        int _pos = pos;

        int length = buffer.getInt(_pos);

        String jsonStr = buffer.getString(_pos += 4, _pos += length);
        JsonObject contentJson = new JsonObject(jsonStr);

        // Get fields
        int from = contentJson.getInteger("from");
        int to = contentJson.getInteger("to");

        // We can finally create custom message object
        return new Command(from, to);
    }

    @Override
    public Command transform(Command command) {
        return command;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
