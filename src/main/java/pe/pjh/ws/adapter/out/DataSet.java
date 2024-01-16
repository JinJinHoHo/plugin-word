package pe.pjh.ws.adapter.out;

public enum DataSet {
    word("word"),
    topic("topic");

    final String name;

    DataSet(String collection) {
        this.name = collection;

    }

    public String getName() {
        return name;
    }
}
