package br.gov.es.infoplan.dto.deserializer;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.gov.es.infoplan.dto.NomeValorObject;
import br.gov.es.infoplan.dto.NomeValorObjectArray;

public class NomeValorObjectArrayDeserializer extends StdDeserializer<NomeValorObjectArray> {

    public NomeValorObjectArrayDeserializer(Class<?> vc) {
        super(vc);
    }

    public NomeValorObjectArrayDeserializer() {
        this(null);
    }

    @Override
    public NomeValorObjectArray deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ArrayList<NomeValorObject> list = new ArrayList<>();
        JsonNode node = p.getCodec().readTree(p);
        ArrayNode resultset = (ArrayNode) node.get("resultset");
        for(int i = 0; i < resultset.size(); i++){
            
            ArrayNode data = (ArrayNode) resultset.get(i);

            String name = data.get(0).asText("");
            double value = data.get(1).asDouble(0);

            list.add(new NomeValorObject(name, value));

        }
        
        return new NomeValorObjectArray(list);
    }

    
}