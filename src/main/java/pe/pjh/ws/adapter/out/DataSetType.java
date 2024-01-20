package pe.pjh.ws.adapter.out;

public enum DataSetType {
    word("word"),
    topic("topic");

    final String name;

    DataSetType(String collection) {
        this.name = collection;

    }

    public String getName() {
        return name;
    }
}
