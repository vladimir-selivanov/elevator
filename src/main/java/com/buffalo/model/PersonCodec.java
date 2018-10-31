package com.buffalo.model;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class PersonCodec implements MessageCodec<Person, Person> {
    @Override
    public void encodeToWire(Buffer buffer, Person person) {
        JsonObject jsonToEncode = new JsonObject();
        jsonToEncode.put("name", person.getName());
        jsonToEncode.put("currentPlace", person.getCurrentPlace().getName());
        jsonToEncode.put("targetPlace", person.getTargetPlace().getName());
        jsonToEncode.put("createTime", person.getCreateTime());

        String jsonToStr = jsonToEncode.encode();

        int length = jsonToStr.getBytes().length;

        buffer.appendInt(length);
        buffer.appendString(jsonToStr);
    }

    @Override
    public Person decodeFromWire(int pos, Buffer buffer) {
        int _pos = pos;

        int length = buffer.getInt(_pos);

        String jsonStr = buffer.getString(_pos += 4, _pos += length);
        JsonObject contentJson = new JsonObject(jsonStr);

        // Get fields
        String name = contentJson.getString("name");
        int currentFloor = contentJson.getInteger("currentPlace");
        int targetFloor = contentJson.getInteger("targetPlace");
        int createTime = contentJson.getInteger("createTime");

        // We can finally create custom message object
        Person person = new Person(name, currentFloor, targetFloor);
        person.setCreateTime(createTime);
        return person;
    }

    @Override
    public Person transform(Person person) {
        return person;
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
