package com.buffalo.transport;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class OfferCodec implements MessageCodec<Offer, Offer> {
    @Override
    public void encodeToWire(Buffer buffer, Offer offer) {
        JsonObject jsonToEncode = new JsonObject();
        jsonToEncode.put("number", offer.getNumber());
        jsonToEncode.put("cost", offer.getCost());

        String jsonToStr = jsonToEncode.encode();

        int length = jsonToStr.getBytes().length;

        buffer.appendInt(length);
        buffer.appendString(jsonToStr);
    }

    @Override
    public Offer decodeFromWire(int pos, Buffer buffer) {
        int _pos = pos;

        int length = buffer.getInt(_pos);

        String jsonStr = buffer.getString(_pos += 4, _pos += length);
        JsonObject contentJson = new JsonObject(jsonStr);

        // Get fields
        int number = contentJson.getInteger("number");
        int cost = contentJson.getInteger("cost");

        // We can finally create custom message object
        return new Offer(number, cost);
    }

    @Override
    public Offer transform(Offer offer) {
        return offer;
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
